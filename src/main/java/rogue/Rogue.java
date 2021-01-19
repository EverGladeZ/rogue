package rogue;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.io.Serializable;

import java.awt.Point;

public class Rogue implements Serializable {
public static final char UP = 'i';
public static final char DOWN = 'k';
public static final char LEFT = 'j';
public static final char RIGHT = 'l';
private static final long serialVersionUID = 1234L;
private ArrayList<Item> items;
private ArrayList<Room> listRooms;
private transient RogueParser parser;
private Player player;
private Map<String, Character> symbolsMap;
private Map<String, String> doorOpposites;

    /**
    * Default Constructor.
    */
    public Rogue() {
      this.items = new ArrayList<Item>();
      this.listRooms = new ArrayList<Room>();
      this.symbolsMap = new HashMap<String, Character>();
      this.doorOpposites = new HashMap<String, String>();
      this.parser = new RogueParser();
    }

/**
* Constructor for the Rogue class, it parses information using the RogueParser class and initalizes necessary variables.
* @param dungeonParser a RogueParser that parses and passes in all the necessary info from the necessary files
*/
    public Rogue(RogueParser dungeonParser) {
       this.items = new ArrayList<Item>();
       this.listRooms = new ArrayList<Room>();
       this.symbolsMap = new HashMap<String, Character>();
       this.doorOpposites = new HashMap<String, String>();
       this.parser = dungeonParser;
       this.player = new Player();
       doorOpposites.put("N", "S");
       doorOpposites.put("S", "N");
       doorOpposites.put("E", "W");
       doorOpposites.put("W", "E");
       symbolsMap = parser.getSymbols();
       roomInfoManager();
       itemInfoManager();
       roomVerification();
    }

    private void roomInfoManager() {
      Map roomInfo = parser.nextRoom();
      while (roomInfo != null) {
          addRoom(roomInfo);
          roomInfo = parser.nextRoom();
      }
    }

    private void itemInfoManager() {
      Map itemInfo = parser.nextItem();
      while (itemInfo != null) {
          addItem(itemInfo);
          itemInfo = parser.nextItem();
      }
    }

    private void roomVerification() {
      for (Room tempRoom : listRooms) {
        setDoors(tempRoom);
        try {
          if (!tempRoom.verifyRoom()) {
          System.out.println("BIG ERROR!");
        }
      } catch (NotEnoughDoorsException e) {
        handleDoors(tempRoom);
      }
      }
    }
/**
* a function that creates a room with the passed in information and adds it to the list of rooms.
* @param toAdd a Map<String, String> that holds all necessary information for creating a room
*/
    public void addRoom(Map<String, String> toAdd) {
        Room tempRoom = new Room();
        tempRoom.setPlayer(this.player);
        tempRoom.setRogue(this);
        tempRoom.setId(Integer.parseInt((String) toAdd.get("id")));
        tempRoom.setWidth(Integer.parseInt((String) toAdd.get("width")));
        tempRoom.setHeight(Integer.parseInt((String) toAdd.get("height")));
        if (Boolean.parseBoolean((String) toAdd.get("start"))) {
          tempRoom.getPlayer().setCurrentRoom(tempRoom);
          tempRoom.setPlayerRoom();
          this.player.setCurrentRoom(tempRoom);
        }
        setRoomDoorCons(tempRoom, toAdd);
        listRooms.add(tempRoom);
    }

    private void setRoomDoorCons(Room tempRoom, Map<String, String> toAdd) {
      if (toAdd.containsKey("nPos")) {
        tempRoom.setDoor("nPos", Integer.parseInt((String) toAdd.get("nPos")));
        tempRoom.setDoor("sPos", Integer.parseInt((String) toAdd.get("sPos")));
        tempRoom.setDoor("ePos", Integer.parseInt((String) toAdd.get("ePos")));
        tempRoom.setDoor("wPos", Integer.parseInt((String) toAdd.get("wPos")));
        tempRoom.setDoor("nCon", Integer.parseInt((String) toAdd.get("nCon")));
        tempRoom.setDoor("sCon", Integer.parseInt((String) toAdd.get("sCon")));
        tempRoom.setDoor("eCon", Integer.parseInt((String) toAdd.get("eCon")));
        tempRoom.setDoor("wCon", Integer.parseInt((String) toAdd.get("wCon")));
      }
    }

    private void handleDoors(Room r) {
      Room r2 = null;
      for (Room room : listRooms) {
      Boolean nDoor = room.getDoorLocation("nPos") != -1;
      Boolean eDoor = room.getDoorLocation("ePos") != -1;
      Boolean sDoor = room.getDoorLocation("sPos") != -1;
      Boolean wDoor = room.getDoorLocation("wPos") != -1;

        if ((!nDoor || !eDoor || !sDoor || !wDoor) && !(!nDoor && !eDoor && !sDoor && !wDoor)) {
          r2 = room;
        }
      }
      if (r2 == null) {
        System.out.println("Dungeon File Cannot be Used.");
        System.exit(1);
      }
      doorManagement(r, r2);
    }

    private void doorManagement(Room r, Room r2) {
      if (r2.getDoorLocation("nPos") == -1) {
        doorSetting(r, r2, "s", "n");
      } else if (r2.getDoorLocation("ePos") == -1) {
        doorSetting(r, r2, "w", "e");
      } else if (r2.getDoorLocation("sPos") == -1) {
        doorSetting(r, r2, "n", "s");
      } else if (r2.getDoorLocation("wPos") == -1) {
        doorSetting(r, r2, "e", "w");
      }
      setDoors(r);
      setDoors(r2);
    }

    private void doorSetting(Room r, Room r2, String dir1, String dir2) {
      Random rand = new Random();
      r.setDoor(dir1 + "Pos", rand.nextInt(r.getHeight() - 2));
      r2.setDoor(dir2 + "Pos", rand.nextInt(r.getHeight() - 2));
      r.setDoor(dir1 + "Con", r2.getId());
      r2.setDoor(dir2 + "Con", r.getId());
    }

    private void setDoors(Room r) {
      HashMap<String, Door> doorMap = new HashMap<String, Door>();
      doorMap.put("N", setIndividualDoor("n", r));
      doorMap.put("E", setIndividualDoor("e", r));
      doorMap.put("S", setIndividualDoor("s", r));
      doorMap.put("W", setIndividualDoor("w", r));
      r.setDoorLocation(doorMap);
    }

    private Door setIndividualDoor(String direction, Room r) {
      Door d = new Door();
      d.connectRoom(r);
      if (r.getDoorLocation(direction + "Pos") != -1) {
        int id = r.getDoorLocation(direction + "Con");
        d.setIdConRoom(id);
        d.connectRoom(listRooms.get(id - 1));
      }
        return d;
    }

    private void setProperLocation(Point p, Room r)  {
      int locX = (int) r.getPlayer().getXyLocation().getX() + 1;
      int locY = (int) r.getPlayer().getXyLocation().getY() + 1;
      Point plyr = new Point(locX, locY);
      ArrayList<Item> roomItems = r.getRoomItems();
      boolean invalidLocation = true;
      while (invalidLocation) {
        invalidLocation = setValidLocation(invalidLocation, p, plyr, r, roomItems);
      }
    }

    private Boolean setValidLocation(Boolean invalidLocation, Point p, Point plyr, Room r, ArrayList<Item> roomItems) {
      if (p.getX() > r.getWidth() - 2 || p.getX() < 1) {
        p.setLocation(1, p.getY());
      }
      if (p.getY() > r.getHeight() - 2 || p.getY() < 1) {
        p.setLocation(p.getX(), 1);
      }
      for (Item item : roomItems) {
       Point itemLoc = item.getXyLocation();
        if (itemLoc.equals(p)) {
          p.translate(1, 0);
        }
      }
      if (plyr.equals(p)) {
        p.translate(1, 0);
      } else {
        return false;
      }
      return true;
    }

    private int getInitialX(Room room, Item item) {
      int x = (int) item.getXyLocation().getX();
      if (x > room.getWidth() - 2 || x < 1) {
        x = 1;
      }
      return x;
    }

    private int getInitialY(Room room, Item item) {
      int y = (int) item.getXyLocation().getY();
      if (y > room.getHeight() - 2 || y < 1) {
        y = 1;
      }
      return y;
    }

    /**
    * a function that creates an item with the passed in information and adds it to the list of items.
    * @param toAdd a Map<String, String> that holds all necessary information for creating an item
    */
    public void addItem(Map<String, String> toAdd) {
      Item i = setItemBasics(toAdd);
      if (toAdd.containsKey("room")) {
        i.setRoomId(Integer.parseInt((String) toAdd.get("room")));
        Room tempRoom = listRooms.get(Integer.parseInt((String) toAdd.get("room")) - 1);
      i.setXyLocation(new Point(Integer.parseInt((String) toAdd.get("x")), Integer.parseInt((String) toAdd.get("y"))));
        boolean itemNotPlaced = true;
        items.add(i);
        while (itemNotPlaced) {
          try {
            tempRoom.addItem(i);
            itemNotPlaced = false;
          } catch (ImpossiblePositionException e) {
            setProperLocation(i.getXyLocation(), tempRoom);
          } catch (NoSuchItemException e) {
            items.remove(i);
          }
      }
      }
    }

    private Item setItemBasics(Map<String, String> toAdd) {
      Item tempItem = setSubClassType((String) toAdd.get("type"));
      tempItem.setId(Integer.parseInt((String) toAdd.get("id")));
      tempItem.setName((String) toAdd.get("name"));
      tempItem.setType((String) toAdd.get("type"));
      tempItem.setDescription((String) toAdd.get("description"));
      tempItem.setDisplayCharacter(getSymbols(tempItem.getType().toUpperCase()));
      return tempItem;
    }

    private Item setSubClassType(String type) {
      type = type.toLowerCase();
      if (type.equals("potion")) {
        return (new Potion());
      } else if (type.equals("food")) {
        return (new Food());
      } else if (type.equals("smallfood")) {
        return (new SmallFood());
      } else if (type.equals("clothing")) {
        return (new Clothing());
      } else if (type.equals("ring")) {
        return (new Ring());
      } else if (type.equals("magic")) {
        return (new Magic());
      } else {
        return (new Item());
      }
    }

    /**
    * Takes in the name of an item and tries to eat it.
    * @param itemName the name of the item to attempt to eat
    * @return String the message if the item was eaten
    */
    public String eatItem(String itemName) {
      Item item = getItemByName(itemName);
      if (item instanceof Food) {
        Food food = (Food) item;
        this.player.removeItemInventory(item);
        return (food.eat());
      } else if (item instanceof Potion) {
        Potion potion = (Potion) item;
        this.player.removeItemInventory(item);
        return (potion.eat());
      }
      return (null);
    }

    /**
    * Takes in the name of an item and tries to wear it.
    * @param itemName the name of the item to attempt to wear
    * @return String the message if the item was worn
    */
    public String wearItem(String itemName) {
      Item item = getItemByName(itemName);
      if (item instanceof Clothing) {
        Clothing clothing = (Clothing) item;
        this.player.removeItemInventory(item);
        this.player.addItemEquipped(item);
        return (clothing.wear());
      } else if (item instanceof Ring) {
        Ring ring = (Ring) item;
        this.player.removeItemInventory(item);
        this.player.addItemEquipped(item);
        return (ring.wear());
      }
      return (null);
    }

    /**
    * Takes in the name of an item and tries to toss it.
    * @param itemName the name of the item to attempt to toss
    * @return String the message if the item was tossed
    */
    public String tossItem(String itemName) {
      Item item = getItemByName(itemName);
      if (item instanceof SmallFood) {
        SmallFood smallfood = (SmallFood) item;
        addTossedItem(item);
        this.player.removeItemInventory(item);
        return (smallfood.toss());
      } else if (item instanceof Potion) {
        Potion potion = (Potion) item;
        addTossedItem(item);
        this.player.removeItemInventory(item);
        return (potion.toss());
      }
      return (null);
    }

    private Item getItemByName(String name) {
      ArrayList<Item> playerInv = this.player.getInventory();
      for (Item item : playerInv) {
        if (item.getName() == name) {
          return item;
        }
      }
      return null;
    }

    private void addTossedItem(Item item) {
      Room r = this.player.getCurrentRoom();
      boolean itemNotPlaced = true;
      int locX = (int) r.getPlayer().getXyLocation().getX();
      int locY = (int) r.getPlayer().getXyLocation().getY();
      Point temp = new Point(locX + 1, locY + 1);
      item.setXyLocation(temp);
      item.setRoomId(r.getId());
      items.add(item);
      item.setCurrentRoom(r);
      while (itemNotPlaced) {
      try {
        r.addItem(item);
        itemNotPlaced = false;
      } catch (ImpossiblePositionException e) {
        setProperLocation(item.getXyLocation(), r);
      } catch (NoSuchItemException e) {
      }
    }
    }

    /**
    * Gets the current inventory and equipment of the player.
    * @return a String that is formatted for display of the inventory and equipment
    */
    public String getPlayerInventoryDisplay() {
      String inventoryString = "<html>------- Inventory -------<br/>";
      ArrayList<Item> playerInv = this.player.getInventory();
      for (Item item : playerInv) {
        inventoryString = inventoryString + item.getDisplayCharacter() + " - " + item.getName() + "<br/>";
      }
      inventoryString = inventoryString + "------- Equipped -------<br/>";
      ArrayList<Item> playerEqu = this.player.getEquipped();
      for (Item item : playerEqu) {
        inventoryString = inventoryString + item.getDisplayCharacter() + " - " + item.getName() + "<br/>";
      }
      return inventoryString;
    }

    /**
    * Gets a the item names from the players inventory in an ArrayList.
    * @return the list of item names
    */
    public ArrayList<String> getPlayerInventory() {
      ArrayList<String> inventoryList = new ArrayList<String>();
      ArrayList<Item> playerInv = this.player.getInventory();
      for (Item item : playerInv) {
        inventoryList.add(item.getName());
      }
      return inventoryList;
    }
    /**
    * Makes a move of the player.
    * @param input the specified direction the player wants to movement
    * @exception InvalidMoveException is thrown when the move attempting to be made goes out of bounds
    * @return a string the summarizes the move
    */
    public String makeMove(char input) throws InvalidMoveException {
        Room currentRoom = new Room();
      for (Room tempRoom : listRooms) {
        if (tempRoom.isPlayerInRoom()) {
          this.player.setCurrentRoom(tempRoom);
          currentRoom = tempRoom;
        }
      }
      if (input == UP) {
        return moveNorth(currentRoom);
      } else if (input == DOWN) {
        return moveSouth(currentRoom);
      } else if (input == LEFT) {
        return moveWest(currentRoom);
      } else if (input == RIGHT) {
        return moveEast(currentRoom);
      } else {
        throw new InvalidMoveException();
      }
    }

    private String moveNorth(Room currentRoom) throws InvalidMoveException {
      int locX = (int) currentRoom.getPlayer().getXyLocation().getX();
      int locY = (int) currentRoom.getPlayer().getXyLocation().getY();
      if (locY - 1 == -1 && currentRoom.getDoorLocation("nPos") == locX + 1) {
        moveRoom(currentRoom, "N");
        return ("Room Moved.");
      } else if (locY - 1 != -1) {
        currentRoom.getPlayer().setXyLocation(new Point(locX, locY - 1));
      } else {
        throw new InvalidMoveException();
      }
      return pickupItem(currentRoom);
    }

    private String moveSouth(Room currentRoom) throws InvalidMoveException {
      int locX = (int) currentRoom.getPlayer().getXyLocation().getX();
      int locY = (int) currentRoom.getPlayer().getXyLocation().getY();
      if (locY + 1 == currentRoom.getHeight() - 2 && currentRoom.getDoorLocation("sPos") == locX + 1) {
        moveRoom(currentRoom, "S");
        return ("Room Moved.");
      } else if (locY + 1 != currentRoom.getHeight() - 2) {
        currentRoom.getPlayer().setXyLocation(new Point(locX, locY + 1));
      } else {
        throw new InvalidMoveException();
      }
      return pickupItem(currentRoom);
    }

    private String moveWest(Room currentRoom) throws InvalidMoveException {
      int locX = (int) currentRoom.getPlayer().getXyLocation().getX();
      int locY = (int) currentRoom.getPlayer().getXyLocation().getY();
      if (locX - 1 == -1 && currentRoom.getDoorLocation("wPos") == locY + 1) {
        moveRoom(currentRoom, "W");
        return ("Room Moved.");
      } else if (locX - 1 != -1) {
        currentRoom.getPlayer().setXyLocation(new Point(locX - 1, locY));
      } else {
        throw new InvalidMoveException();
      }
      return pickupItem(currentRoom);
    }

    private String moveEast(Room currentRoom) throws InvalidMoveException {
      int locX = (int) currentRoom.getPlayer().getXyLocation().getX();
      int locY = (int) currentRoom.getPlayer().getXyLocation().getY();
      if (locX + 1 == currentRoom.getWidth() - 2 && currentRoom.getDoorLocation("ePos") == locY + 1) {
        moveRoom(currentRoom, "E");
        return ("Room Moved.");
      } else if (locX + 1 != currentRoom.getWidth() - 2) {
        currentRoom.getPlayer().setXyLocation(new Point(locX + 1, locY));
      } else {
        throw new InvalidMoveException();
      }
      return pickupItem(currentRoom);
    }

    private void moveRoom(Room r, String dir)  {
      Player curPlayer = r.getPlayer();
      Room otherRoom = r.getDoor(dir).getOtherRoom(r);
      int locX = (int) curPlayer.getXyLocation().getX();
      int locY = (int) curPlayer.getXyLocation().getY();
      curPlayer.setCurrentRoom(otherRoom);
      this.player.setCurrentRoom(otherRoom);
      setNewPlayerLoc(curPlayer, otherRoom, dir);
    }

    private void setNewPlayerLoc(Player curPlayer, Room otherRoom, String dir) {
      int newLocXy = otherRoom.getDoorLocation(doorOpposites.get(dir).toLowerCase() + "Pos") - 1;
      int lower = otherRoom.getHeight() - 2;
      int side = otherRoom.getWidth() - 2;
      if (dir.equals("N")) {
        curPlayer.setXyLocation(new Point(newLocXy, lower - 1));
      }
      if (dir.equals("S")) {
        curPlayer.setXyLocation(new Point(newLocXy, 0));
      }
      if (dir.equals("E")) {
        curPlayer.setXyLocation(new Point(0, newLocXy));
      }
      if (dir.equals("W")) {
        curPlayer.setXyLocation(new Point(side - 1, newLocXy));
      }
    }
    private String pickupItem(Room r) {
      ArrayList<Item> itemList = r.getRoomItems();
      String nameItem = "Move Made.";
      int playerX = (int) r.getPlayer().getXyLocation().getX() + 1;
      int playerY = (int) r.getPlayer().getXyLocation().getY() + 1;
      for (int i = 0; i < itemList.size(); i++) {
        int itemX = (int) itemList.get(i).getXyLocation().getX();
        int itemY = (int) itemList.get(i).getXyLocation().getY();
        if (playerX == itemX && playerY == itemY) {
           nameItem = "You got " + itemList.get(i).getName() + "!";
          this.player.addItemInventory(itemList.get(i));
          itemList.remove(i);
        }
      }
      r.setRoomItems(itemList);
      return (nameItem);
    }
    /**
    * Setter for the player of the game.
    * @param thePlayer the player that will be playing the game
    */
    public void setPlayer(Player thePlayer) {
      this.player = thePlayer;
    }

    /**
    * Setter for the RogueParser.
    * @param filename the name of the file that information will be parsed from
    */
    public void setRogueParser(String filename) {
      this.parser = new RogueParser(filename);
    }

    /**
    * Gets a symbol for a desired key using a HashMap.
    * @param name the key of the desired symbol
    * @return     (String) the symbol that represents the passed in key
    */
    public Character getSymbols(String name) {
        return symbolsMap.get(name);
    }

    /**
    * Getter for all the rooms in the game.
    * @return (ArrayList) an ArrayList of every Room in the game
    */
    public ArrayList<Room> getRooms() {
        return this.listRooms;
    }

    /**
    * Getter for all the items in the game.
    * @return (ArrayList) an ArrayList of every Item in the game
    */
    public ArrayList<Item> getItems() {
        return this.items;
    }

    /**
    * Getter for the player of the game.
    * @return (Player) the player of the game
    */
    public Player getPlayer() {
        return this.player;
    }

    /**
    * Getter for the symbols map of the game.
    * @return (Map<String, Character>) the symbols map of the game
    */
    public Map<String, Character> getSymbolsMap() {
        return this.symbolsMap;
    }

    /**
    * Obtains the next display of the room after a move is made.
    * @return (String) that holds visual representaion of room after move is made
    */
    public String getNextDisplay() {
      String display = null;
      for (Room tempRoom : listRooms) {
        tempRoom.setPlayerRoom();
        if (tempRoom.isPlayerInRoom()) {
          display = tempRoom.displayRoom();
        }
      }
      return display;
    }
}
