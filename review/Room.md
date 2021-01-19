| Method Signature | Responsibility | Instance Variables Used | Other Class Methods Called | Objects Used with Method Calls | Lines of Code |
|-|-|-|-|-|-|
| public Room() | Default Constructor for the Room class. | doors, doorLocations, playerInRoom | setId(), setWidth(), setHeight(), setRoomItems(), setPlayer() | N/A | 10 |
| public void setPlayerRoom() | Sets whether the player is in the room or not. | playerInRoom, player | Player.getCurrentRoom() | Player | 7 |
| public int getWidth() | Gets the width of the room. | width | N/A | N/A | 3 |
| public void setWidth(int newWidth) | Sets the width of the room. | width | N/A | N/A | 3 |
| public void setRogue(Rogue newRogue) | Sets the linked Rogue game of the room. | rogue | N/A | N/A | 3 |
| public int getHeight() | Gets the height of the room. | height | N/A | N/A | 3 |
| public void setHeight(int newHeight) | Sets the height of the room. | height | N/A | N/A | 3 |
| public int getId() | Gets the id of the room. | id | N/A | N/A | 3 |
| public void setId(int newId) | Sets the id of the room. | id | N/A | N/A | 3 |
| public ArrayList<Item> getRoomItems() | Gets a list of all items in the room. | roomItems | N/A | N/A | 3 |
| public void setRoomItems(ArrayList<Item> newRoomItems) | Sets the list of all items in the room. | roomItems | N/A | N/A | 3 |
| public Player getPlayer() | Gets the player linked to the room. | player | N/A | N/A | 3 |
| public void setPlayer(Player newPlayer) | Sets the player of the room. | player | N/A | N/A | 3 |
| public int getDoorLocation(String direction) | Gets the numerical location of the door on a wall. | doorLocations | N/A | N/A | 7 |
| public Door getDoor(String direction) | Gets the Door object at a given direction. | doors | N/A | N/A | 3 |
| public void setDoorLocation(HashMap<String, Door> doorMap) | Sets the Door map of the doors for all the directions. | doorLocations | N/A | N/A | 3 |
| public void setDoor(String direction, int location) | Sets the numerical location of the door. | doors | N/A | N/A | 7 |
| public boolean isPlayerInRoom() | Returns whether the player is currently in the room or not. | playerInRoom | Player.getCurrentRoom() | Player | 3 |
| public void addItem(Item toAdd) throws ImpossiblePositionException, NoSuchItemException | Adds an item to the room. | roomItems | Item.setCurrentRoom(), isItemInList(), isValidItemPos() | Item | 10 |
| private boolean isItemInList(Item toCheck) | Checks if an item is in the list of items in the game. | rogue | Rogue.getItems(), Item.getId() | Rogue, Item | 9 |
| private boolean isValidItemPos(Item toCheck) | Checks if the item is in a valid position. | roomItems, player, width, height | Item.getXyLocation(), Player.getXyLocation() | Item, Player | 17 |
| private String setNSWall(String direction) | Sets the displayable wall for N/S direction. | width, rogue | Rogue.getSymbols(), getDoorLocation() | Rogue | 17 |
| private Character setEWWall(String direction, int i) | Sets a displayable character of the wall in the E/W direction. | rogue | Rogue.getSymbols(), getDoorLocation() | Rogue | 13 |
| public String displayRoom() | Creates a visual representation of the room. | N/A | setNSWall(), setRoomFloor() | N/A | 7 |
| private String setRoomFloor(String roomDisplay) | Sets the whole game area of the floor. | height, width | setEWWall(), setFloor() | N/A | 11 |
| private String setFloor(String roomDisplay, int i, int j) | Sets the floor of the displayable room. | player, playerInRoom, rogue, roomItems | Player.getXyLocation(), Rogue.getSymbols() | Player, Rogue | 12 |
| private String displayRoomItems(String roomDisplay, int i, int j) | Sets the items to be displayed in the room. | roomItems, rogue | Item.getXyLocation(), Item.getDisplayCharacter(), Rogue.getSymbols() | Item, Rogue | 17 |
| public boolean verifyRoom() throws NotEnoughDoorsException | Verifies if the room is correctly built. | N/A | validatePlayer(), validateItems(), validateDoors(), checkDoor() | N/A | 13 |
| private boolean validatePlayer() | Validates the player in the room. | player, playerInRoom, width, height | Player.getXyLocation() | Player | 9 |
| private boolean validateItems() | Validates all items in the room. | roomItems, width, height | Item.getXyLocation() | Item | 14 |
| private boolean validateDoors() | Validates all doors in the room. | N/A | checkDoor(), doorHasCon() | N/A | 7 |
| private boolean checkDoor(String dir) | Checks if a door exists at a given direction in the room. | N/A | getDoorLocation() | N/A | 3 |
| private boolean doorHasCon(String dir, boolean doorExists) | Checks if the door is connected to another room. | doors | Door.getConnectedRooms() | Door | 7 |
