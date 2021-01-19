package rogue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.awt.Point;
import java.util.HashMap;
import java.io.Serializable;

/**
 * A room within the dungeon - contains monsters, treasure,
 * doors out, etc.
 */
public class Room implements Serializable {
private static final long serialVersionUID = 2341L;
private int id;
private int width;
private int height;
private ArrayList<Item> roomItems;
private Player player;
private Rogue rogue;
private Map<String, Integer> doorLocations;
private Map<String, Door> doors;
private boolean playerInRoom;


  /**
  * Default Room constructor that initalizes all fields.
  */
  public Room() {
    setId(0);
    setWidth(2);
    setHeight(2);
    setRoomItems(new ArrayList<Item>());
    setPlayer(new Player());
    this.doors = new HashMap<String, Door>();
    this.doorLocations = new HashMap<String, Integer>();
    this.playerInRoom = false;
  }

// Required getter and setters below

  /**
  * Sets the current room as the Player's Room when called.
  */
  public void setPlayerRoom() {
    if (player.getCurrentRoom() == this) {
       this.playerInRoom = true;
    } else {
       this.playerInRoom = false;
    }
  }

  /**
  * Getter of the width of the room.
  * @return (int) the width of the room
  */
   public int getWidth() {
     return this.width;
   }

   /**
   * Setter for the width field of the room.
   * @param newWidth the specified width of the given room
   */
   public void setWidth(int newWidth) {
      this.width = newWidth;
   }

   /**
   * Setter for the rogue field of the room.
   * @param newRogue the Rogue game object the room is linked to
   */
   public void setRogue(Rogue newRogue) {
      this.rogue = newRogue;
   }

   /**
   * Getter of the height of the room.
   * @return (int) the height of the room
   */
   public int getHeight() {
     return this.height;
   }

   /**
   * Setter for the height field of the room.
   * @param newHeight the specified height of the given room
   */
   public void setHeight(int newHeight) {
     this.height = newHeight;
   }

   /**
   * Getter of the id of the room.
   * @return (int) the id value of the room
   */
   public int getId() {
      return this.id;
   }

   /**
   * Setter for the id field of the room.
   * @param newId the new id of the given room
   */
   public void setId(int newId) {
     this.id = newId;
   }

   /**
   * Getter of the items in the room.
   * @return (ArrayList) an ArrayList of all of the Items in the given room
   */
   public ArrayList<Item> getRoomItems() {
      return this.roomItems;
   }

   /**
   * Setter of all the items in the room.
   * @param newRoomItems an ArrayList of every Item in the room
   */
   public void setRoomItems(ArrayList<Item> newRoomItems) {
     this.roomItems = newRoomItems;
   }
   /**
   * Adds the item to the list of rooms if it is present in the room.
   * @param toAdd the item that is going to be added
   * @exception ImpossiblePositionException thrown if the item is in an impossible location
   * @exception NoSuchItemException thrown if the item doesn't exist in the room
   */
   public void addItem(Item toAdd) throws ImpossiblePositionException, NoSuchItemException {
          if (!isValidItemPos(toAdd)) {
            throw new ImpossiblePositionException();
          } else if (!isItemInList(toAdd)) {
            throw new NoSuchItemException();
          } else {
            toAdd.setCurrentRoom(this);
            roomItems.add(toAdd);
          }
   }

   private boolean isItemInList(Item toCheck) {
     ArrayList<Item> rogueItems = rogue.getItems();
       for (Item item : rogueItems) {
          if (toCheck.getId() == item.getId()) {
            return true;
          }
     }
     return false;
   }

   private boolean isValidItemPos(Item toCheck) {
     int locX = (int) toCheck.getXyLocation().getX();
     int locY = (int) toCheck.getXyLocation().getY();
     Point playerLocation = this.player.getXyLocation();
     if (locX <= 0 || locX >= this.width - 1 || locY <= 0 || locY >= this.height - 1) {
       return false;
     } else if (playerLocation.getX() + 1 == locX && playerLocation.getY() + 1 == locY) {
       return false;
     }
     for (int i = 0; i < roomItems.size() - 1; i++) {
      Point roomItemLocation = roomItems.get(i).getXyLocation();
       if ((int) roomItemLocation.getX() == locX && (int) roomItemLocation.getY() == locY) {
         return false;
       }
     }
     return true;
   }
   /**
   * Getter of the player in the room.
   * @return (Player) the player in the room.
   */
   public Player getPlayer() {
      return this.player;
   }

   /**
   * Setter of the player in the room.
   * @param newPlayer the player of the current room
   */
   public void setPlayer(Player newPlayer) {
      this.player = newPlayer;
   }

   /**
   * Gets the location of the door on the wall.
   * @param direction one of N,E,S,W that represent the wall the door is on
   * @return     (int) the location the door is on a wall at a given direction
   */
   public int getDoorLocation(String direction) {
      if (doorLocations.get(direction) == null) {
        return -1;
      } else {
        return doorLocations.get(direction);
      }
   }

   /**
   * Takes in the direction of the door spits out the associated door object.
   * @param direction the direction of the wall the door is on
   * @return the door object on the given wall
   */
   public Door getDoor(String direction) {
      return doors.get(direction);
   }

   /**
   * Sets the HashMap of door direction and door object for the Room class.
   * @param doorMap the map of doors that will be set as the Room door map.
   */
   public void setDoorLocation(HashMap<String, Door> doorMap) {
     this.doors = doorMap;
   }

/*
direction is one of NSEW
location is a number between 0 and the length of the wall
*/

/**
* Sets the location of the door on the wall by mapping a direction to a location on a HashMap.
* @param direction one of N,E,S,W that represent the wall the door is on
* @param location  the numerical value between 0 and the wall length that represents the location
*/
public void setDoor(String direction, int location) {
  if (this.doorLocations.containsKey(direction)) {
    this.doorLocations.replace(direction, location);
  } else {
    this.doorLocations.put(direction, location);
  }
}

/**
* Checks if the player is in the room.
* @return (boolean) returns true if the player is in the room
*/
public boolean isPlayerInRoom() {
  return (this.player.getCurrentRoom() == this);
}


private String setNSWall(String direction) {
  String nsWall = "";
  int i;
  int door = -1;
  if (this.getDoorLocation(direction) != -1) {
    door = this.getDoorLocation(direction);
  }
  for (i = 0; i < this.width; i++) {
    if (i == door) {
      nsWall = nsWall + this.rogue.getSymbols("DOOR");
    } else {
      nsWall = nsWall + this.rogue.getSymbols("NS_WALL");
    }
  }
  nsWall = nsWall + "\n";
  return nsWall;
}


private Character setEWWall(String direction, int i) {
  Character ewWall = null;
  int door = -1;
  if (this.getDoorLocation(direction) != -1) {
    door = this.getDoorLocation(direction);
  }
  if (i + 1 == door) {
    ewWall = this.rogue.getSymbols("DOOR");
  } else {
    ewWall = this.rogue.getSymbols("EW_WALL");
  }
  return ewWall;
}

   /**
    * Produces a string that can be printed to produce an ascii rendering of the room and all of its contents.
    * @return (String) String representation of how the room looks
    */
   public String displayRoom() {
       String roomDisplay = "";
       roomDisplay = roomDisplay + this.setNSWall("nPos");
       roomDisplay = setRoomFloor(roomDisplay);
       roomDisplay = roomDisplay + this.setNSWall("sPos");
     return roomDisplay;
   }

   private String setRoomFloor(String roomDisplay) {
     for (int i = 0; i < this.height - 2; i++) {
       roomDisplay = roomDisplay + this.setEWWall("wPos", i);
       for (int j = 0; j < this.width - 2; j++) {
         roomDisplay = setFloor(roomDisplay, i, j);
       }
       roomDisplay = roomDisplay + this.setEWWall("ePos", i);
       roomDisplay = roomDisplay + "\n";
     }
     return roomDisplay;
   }

   private String setFloor(String roomDisplay, int i, int j) {
     double locX = this.player.getXyLocation().getX();
     double locY = this.player.getXyLocation().getY();
     if (locX == j && locY == i && this.playerInRoom) {
      roomDisplay = roomDisplay + this.rogue.getSymbols("PLAYER");
     } else if (this.roomItems == null) {
      roomDisplay = roomDisplay + this.rogue.getSymbols("FLOOR");
     } else {
       roomDisplay = displayRoomItems(roomDisplay, i, j);
     }
     return roomDisplay;
   }

   private String displayRoomItems(String roomDisplay, int i, int j) {
     boolean setItem = false;
      Iterator<Item> itemIterator = this.roomItems.iterator();
      while (itemIterator.hasNext()) {
       Item tempItem = itemIterator.next();
       if (tempItem.getXyLocation().getX() == j + 1 && tempItem.getXyLocation().getY() == i + 1) {
       roomDisplay = roomDisplay + tempItem.getDisplayCharacter();
       setItem = true;
     }
    }
    if (!setItem) {
      roomDisplay = roomDisplay + this.rogue.getSymbols("FLOOR");
    } else {
      setItem = false;
    }
    return roomDisplay;
   }
   /**
   * Verifies the room is properly built with proper item locations, player locations and doors.
   * @return true if the room is properly built, false if it is not.
   * @throws NotEnoughDoorsException when the room has no doors
   */
   public boolean verifyRoom() throws NotEnoughDoorsException {
       boolean validPlayer = this.validatePlayer();
       boolean validItems = this.validateItems();
       boolean validDoors = this.validateDoors();
       boolean nDoor = checkDoor("nPos");
       boolean eDoor = checkDoor("ePos");
       boolean sDoor = checkDoor("sPos");
       boolean wDoor = checkDoor("wPos");
       if (!nDoor && !eDoor && !sDoor && !wDoor) {
         throw new NotEnoughDoorsException();
       }
       return (validPlayer && validItems && validDoors);
   }

   private boolean validatePlayer() {
     if (this.playerInRoom) {
       double x = this.player.getXyLocation().getX();
       double y = this.player.getXyLocation().getY();
       return (x > -1 && x < this.width - 2 && y > -1 && y < this.height - 2);
     } else {
       return true;
     }
   }

   private boolean validateItems() {
     if (roomItems.size() > 0) {
      for (int i = 0; i < roomItems.size(); i++) {
        int locX = (int) roomItems.get(i).getXyLocation().getX();
        int locY = (int) roomItems.get(i).getXyLocation().getY();
        if (locX <= 0 || locX >= this.width || locY <= 0 || locY >= this.height) {
          return false;
        }
      }
      return true;
     } else {
       return true;
     }
   }

   private boolean validateDoors() {
     boolean nDoor = checkDoor("nPos");
     boolean eDoor = checkDoor("ePos");
     boolean sDoor = checkDoor("sPos");
     boolean wDoor = checkDoor("wPos");
     return (doorHasCon("N", nDoor) && doorHasCon("E", eDoor) && doorHasCon("S", sDoor) && doorHasCon("W", wDoor));
    }

   private boolean checkDoor(String dir) {
     return (getDoorLocation(dir) != -1);
   }

   private boolean doorHasCon(String dir, boolean doorExists) {
     if (doorExists) {
         return (doors.get(dir).getConnectedRooms().size() > 1);
     } else {
       return true;
     }
   }

}
