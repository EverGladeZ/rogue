| Method Signature | Responsibility | Instance Variables Used | Other Class Methods Called | Objects Used With Method Calls | Lines of Code |
|-|-|-|-|-|-|
| public Rogue() | Default constructor for the Rogue class. | items, listRooms, symbolsMap, doorOpposites, parser, player | N/A | N/A | 7 |
| public Rogue(RogueParser dungeonParser) | Constructor for the Rogue class that sets all parsed information. | items, listRooms, symbolsMap, doorOpposites, parser, player | RogueParser.getSymbols(), roomInfoManager(), itemInfoManager(), roomVerification() | RogueParser | 16 |
| private void roomInfoManager() | Creates all the rooms in a game. | parser | RogueParser.nextRoom(), addRoom() | RogueParser | 7 |
| private void itemInfoManager() | Creates all the items in a game. | parser | RogueParser.nextItem(), addItem() | RogueParser | 7 |
| private void roomVerification() | Handles verifying every room in the game. | listRooms | Room.verifyRoom(), setDoors(), handleDoors() | Room | 12 |
| public void addRoom(Map<String, String> toAdd) | Creates a single room with passed in information. | player, listRooms | Room.setPlayer(), Room.setRogue(), Room.setId(), Room.setWidth(), Room.setHeight(), Room.setPlayerRoom(), Player.setCurrentRoom(), setRoomDoorCons() | Player, Rogue, Room | 15 |
| private void setRoomDoorCons(Room tempRoom, Map <String, String> toAdd) | Sets the Door value map in a single room. | N/A | Room.setDoor() | Room | 12 |
| private void handleDoors(Room r) | Handles the NotEnoughDoorsException by adding a door to an appropriate room. | listRooms | Room.getDoorLocation(), doorManagement() | Room | 18 |
| private void doorManagement(Room r, Room r2) | Links a room with no doors to another room. | N/A | Room.getDoorLocation(), doorSetting(), setDoors() | Room | 13 |
| private void doorSetting(Room r, Room r2, String dir1, String dir2) | Sets Door value map values of each room. | N/A | Room.setDoor(), Room.getId(), Room.getHeight() | Room | 11 |
| private void setDoors(Room r) | Sets the Door map (Map<String, Door>) of each direction in the room. | N/A | Room.setDoorLocation(), setIndividualDoor() | Room | 8 |
| private Door setIndividualDoor(String direction, Room r) | Sets an single door at a given direction in a room. | listRooms | Room.getDoorLocation, Door.connectRoom(), Door.setIdConRoom(), Door.connectRoom() | Room, Door | 10 |
| private void setProperLocation(Point p, Room r) | Sets an Item to a proper location in the room. | N/A | Room.getPlayer(), Room.getRoomItems(), Player.getXyLocation(), setValidLocation() | Room, Player | 10 |
| private Boolean setValidLocation(Boolean invalidLocation, Point p, Point plyr, Room r, ArrayList<Item> roomItems) | Computes a valid location for an item. | N/A | Room.getWidth(), Room.getHeight(), Item.getXyLocation() | Room, Item | 20 |
| private int getInitialX(Room room, Item item) | Computes the initial X value of the relocated item. | N/A | Room.getWidth(), Item.getXyLocation() | Room, Item | 7 |
| private int getInitialY(Room room, Item item) | Computes the initial Y value of the relocated item. | N/A | Room.getHeight(), Item.getXyLocation() | Room, Item | 7 |
| public void addItem(Map<String, String> toAdd) | Creates a single item with the passed in information. | items, listRooms | Item.setXyLocation(), Item.setRoomId(), Room.addItem(), setItemBasics(), setProperLocation() | Item, Room | 20 |
| private Item setItemBasics(Map<String, String> toAdd) | Creates an item with basic information. | N/A | Item.setId(), Item.setName(), Item.setType(), Item.setDescription(), Item.setDisplayCharacter(), setSubClassType(), getSymbols() | Item | 9 |
| private Item setSubClassType(String type) | Sets the subclass of an item. | N/A | N/A | Potion, Food, Magic, SmallFood, Clothing, Ring, Item | 18 |
| public String eatItem(String itemName) | Attempts to eat an item. | player | Player.removeItemInventory(), Food.eat(), Potion.eat(), getItemByName() | Item, Food, Potion | 13 |
| public String wearItem(String itemName) | Attempts to wear an item. | player | Player.removeItemInventory(), Player.addItemEquipped(), Clothing.wear(), Ring.wear(), getItemByName() | Item, Player, Clothing, Ring | 15 |
| public String tossItem(String itemName) | Attempts to toss an item. | player | Player.removeItemInventory(), SmallFood.toss(), Potion.toss(), getItemByName() | Item, Player, SmallFood, Potion | 15 |
| private Item getItemByName(String name) | Gets the Item of a specified name. | player | Item.getName(), Player.getInventory() | Item, Player | 9 |
| private void addTossedItem(Item item) | Adds a tossed item to the room. | items, player | Player.getCurrentRoom(), Room.getPlayer(), Room.getId(), Room.addItem(), Item.setXyLocation(), Item.setRoomId(), Item.setCurrentRoom(), Item.getXyLocation(), setProperLocation() | Item, Room, Player | 20 |
| public String getPlayerInventoryDisplay() | Gets a displayable string of the player's inventory screen. | player | Player.getInventory(), Player.getEquipped(), Item.getDisplayCharacter(), Item.getName() | Player, Item | 13 |
| public ArrayList<String> getPlayerInventory() | Get a list of all item names in the player's inventory. | player | Player.getInventory(), Item.getName() | Player, Item | 8 |
| public String makeMove(char input) throws InvalidMoveException | Make a move based on the input character. | listRooms, player | Room.isPlayerInRoom(), Player.setCurrentRoom() | Room, Player | 20 |
| public String moveNorth(Room currentRoom) throws InvalidMoveException | Move the player up if possible. | N/A | Player.setXyLocation, Room.getPlayer(), Room.getDoorLocation(), moveRoom(), pickupItem() | Room, Player | 13 |
| private String moveSouth(Room currentRoom) throws InvalidMoveException | Move the player down if possible. | N/A | Player.setXyLocation, Room.getPlayer(), Room.getDoorLocation(), moveRoom(), pickupItem() | Room, Player | 13 |
| private String moveWest(Room currentRoom) throws InvalidMoveException | Move the player right if possible. | N/A | Player.setXyLocation, Room.getPlayer(), Room.getDoorLocation(), moveRoom(), pickupItem() | Room, Player | 13 |
| private String moveEast(Room currentRoom) throws InvalidMoveException | Move the player left if possible. | N/A | Player.setXyLocation, Room.getPlayer(), Room.getDoorLocation(), moveRoom(), pickupItem() | Room, Player | 13 |
| private void moveRoom(Room r, String dir) | Moves the player into the specified room. | player | Player.setCurrentRoom(), Player.getXyLocation(), Room.getPlayer(), Room.getDoor(), Door.getOtherRoom(), setNewPlayerLoc() | Player, Room, Door | 9 |
| private void setNewPlayerLoc(Player curPlayer, Room otherRoom, String dir) | Set the player's new location in the new room. | doorOpposites | Room.getDoorLocation(), Room.getHeight(), Room.getWidth(), Player.setXyLocation() | Room, Player | 17 |
| private String pickupItem(Room r) | Picks up an item from the ground. | player | Room.getRoomItems(), Room.getPlayer(), Room.setRoomItems(), Player.getXyLocation(), Player.addItemInventory(), Item.getXyLocation() | Room, Player, Item | 17 |
| public void setPlayer(Player thePlayer) | Sets the player of the game. | player | N/A | N/A | 3 |
| public void setRogueParser(String filename) | Sets the game's parser. | parser | N/A | N/A | 3 |
| public Character getSymbols(String name) | Gets a character for a symbol. | symbolsMap | N/A | N/A | 3 |
| public ArrayList<Room> getRooms() | Gets a list of all rooms in the game. | listRooms | N/A | N/A | 3 |
| public ArrayList<Item> getItems() | Gets a list of all items in the game. | items | N/A | N/A | 3 |
| public Player getPlayer() | Gets the player of the game. | player | N/A | N/A | 3 |
| public Map<String, Character> getSymbolsMap() | Gets the symbols map of the game. | symbolsMap | N/A | N/A | 3 |
| public String getNextDisplay() | Creates a visual representation of a room. | listRooms | Room.setPlayerRoom(), Room.isPlayerInRoom(), Room.displayRoom() | Room | 10 |