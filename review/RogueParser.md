| Method Signature | Responsibility | Instance Variables Used | Other Class Methods Called | Objects Used with Method Calls | Lines of Code |
|-|-|-|-|-|-|
| public RogueParser() | Default constructor of the RogueParser class. | numOfRooms, numOfItems | N/A | N/A | 3 |
| public RogueParser(String filename) | RogueParser constructor when using FileLocations.json | N/A | parse() | N/A | 3 |
| public RogueParser(File file, Map newSymbols) | RogueParser constructor when using Rooms.json (loading json) | N/A | parseDirect() | N/A | 3 |
| public HashMap getSymbols() | Get the symbols map that was parsed. | symbols | N/A | N/A | 3 |
| public Map nextRoom() | Gets mapped information of a single room. | roomIterator | N/A | N/A | 7 |
| public Map nextItem() | Gets mapped information of a single item. | itemIterator | N/A | N/A | 7 |
| public Character getSymbol(String symbolName) | Gets the corresponding symbol character. | symbols | N/A | N/A | 6 |
| public int getNumOfItems() | Gets the number of items. | numOfItems | N/A | N/A | 3 |
| public int getNumOfRooms() | Gets the number of rooms. | numOfRooms | N/A | N/A | 3 |
| private void parse(String filename) | Parses the passed in file using the file locations. | N/A | extractRoomInfo(), extractItemInfo(), extractSymbolInfo(), setIterators() | N/A | 20 |
| private void parseDirect(String filename, Map newSymbols) | Parses the passed in Rooms JSON. | symbols | extractRoomInfo(), extractItemInfo(), setIterators() | N/A | 16 |
| private void setIterators() | Sets the instance iterators. | roomIterator, itemIterator, rooms, items | N/A | N/A | 4 |
| private void extractSymbolInfo(JSONObject symbolsJSON) | Extracts the symbol info from the JSON. | symbols | N/A | N/A | 7 |
| private void extractRoomInfo(JSONObject roomsJSON) | Extracts the room info from the JSON. | rooms, numOfRooms | singleRoom() | Room | 7 |
| private Map singleRoom(JSONObject roomJSON) | Gets a single room's information. | N/A | itemPosition(), makeHashMap() | Room | 15 |
| private HashMap makeHashMap(JSONObject roomJSON) | Makes the HashMap of all room information. | N/A | N/A | N/A | 16 |
| private Map<String, String> itemPosition(JSONObject lootJSON, String roomID) | Sets the location information of an item. | N/A | N/A | N/A | 8 |
| private void extractItemInfo(JSONObject roomsJSON) | Extracts the item info from the JSON. | items, numOfItems | singleItem() | Item | 7 |
| private Map<String, String>  singleItem(JSONObject itemsJSON) | Creates a map of information for a single item. | N/A | N/A | N/A | 16 |