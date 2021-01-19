| Method Signature | Responsibility | Instance Variables Used | Other Class Methods Called | Objects Used with Method Calls | Lines of Code |
|-|-|-|-|-|-|
| public Player() | Default constructor for the Player class. | inventory, equipped | setName(), setXyLocation(), setCurrentRoom() | Room | 7 |
| public Player(String newName) | Constructor for the Player class with name passed in. | inventory, equipped | setName(), setXyLocation(), setCurrentRoom() | Room | 7 |
| public String getName() | Gets the name of the player. | name | N/A | N/A | 3 |
| public void addItemInventory(Item toAdd) | Adds the item to the player's inventory. | inventory | N/A | Item | 3 |
| public void removeItemInventory(Item toRemove) | Removes the item from the player's inventory. | inventory | N/A | Item | 3 |
| public void addItemEquipped(Item toAdd) | Adds the item to the player's equipped slot. | equipped | N/A | Item | 3 |
| public ArrayList<Item> getInventory() | Gets a list of items in the inventory. | inventory | N/A | N/A | 3 |
| public ArrayList<Item> getEquipped() | Gets a list of items equipped. | equipped | N/A | N/A | 3 |
| public void setName(String newName) | Sets the player's name. | name | N/A | N/A | 3 |
| public Point getXyLocation() | Gets the player's location. | xyLocation | N/A | N/A | 3 |
| public void setXyLocation(Point newXyLocation) | Sets the player's location. | xyLocation | N/A | N/A | 3 |
| public Room getCurrentRoom() | Gets the player's current room. | currentRoom | N/A | N/A | 3 |
| public void setCurrentRoom(Room newRoom) | Sets the player's current room. | currentRoom | N/A | N/A | 3 |