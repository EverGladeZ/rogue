package rogue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RogueParser {

    private ArrayList<Map<String, String>> rooms = new ArrayList<>();
    private ArrayList<Map<String, String>> items = new ArrayList<>();
    private ArrayList<Map<String, String>> itemLocations = new ArrayList<>();
    private HashMap<String, Character> symbols = new HashMap<>();

    private Iterator<Map<String, String>> roomIterator;
    private Iterator<Map<String, String>> itemIterator;

    private int numOfRooms;
    private int numOfItems;

    /**
     * Default constructor.
     */
    public RogueParser() {
      this.numOfRooms = -1;
      this.numOfItems = -1;
    }

    /**
     * Constructor that takes filename and sets up the object.
     * @param filename  (String) name of file that contains file location for rooms and symbols
     */
    public RogueParser(String filename) {
        parse(filename);
    }

    /**
     * Constructor that takes file and map of symbols and sets up the object.
     * @param file (File) the Rooms file that contains file
     * @param newSymbols (Map) the map of symbols from initial game
     */
    public RogueParser(File file, Map<String, Character> newSymbols) {
      parseDirect(file.toString(), newSymbols);
    }

    /**
    * Getter for the map of symbols that was parsed.
    * @return (Map) that maps all symbol names to their displayCharacter
    */
    public HashMap<String, Character> getSymbols() {
       return this.symbols;
    }

    /**
     * Return the next room.
     * @return (Map) Information about a room
     */
    public Map nextRoom() {
        if (roomIterator.hasNext()) {
            return roomIterator.next();
        } else {
            return null;
        }
    }

    /**
     * Returns the next item.
     * @return (Map) Information about an item
     */
    public Map nextItem() {
        if (itemIterator.hasNext()) {
            return itemIterator.next();
        } else {
            return null;
        }
    }

    /**
     * Get the character for a symbol.
     * @param symbolName (String) Symbol Name
     * @return (Character) Display character for the symbol
     */
    public Character getSymbol(String symbolName) {
        if (symbols.containsKey(symbolName)) {
            return symbols.get(symbolName);
        }
        return null;
    }

    /**
     * Get the number of items.
     * @return (int) Number of items
     */
    public int getNumOfItems() {
        return numOfItems;
    }

    /**
     * Get the number of rooms.
     * @return (int) Number of rooms
     */
    public int getNumOfRooms() {
        return numOfRooms;
    }

    /**
     * Read the file containing the file locations.
     * @param filename (String) Name of the file
     */
    private void parse(String filename) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject configurationJSON = (JSONObject) parser.parse(new FileReader(filename));
            String roomsFileLocation = (String) configurationJSON.get("Rooms");
            String symbolsFileLocation = (String) configurationJSON.get("Symbols");
            JSONObject roomsJSON = (JSONObject) parser.parse(new FileReader(roomsFileLocation));
            JSONObject symbolsJSON = (JSONObject) parser.parse(new FileReader(symbolsFileLocation));
            extractRoomInfo(roomsJSON);
            extractItemInfo(roomsJSON);
            extractSymbolInfo(symbolsJSON);
            setIterators();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file named: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Error parsing JSON file");
        }
    }

    private void parseDirect(String filename, Map<String, Character> newSymbols) {
      JSONParser parser = new JSONParser();
      try {
        JSONObject roomsJSON = (JSONObject) parser.parse(new FileReader(filename));
        extractRoomInfo(roomsJSON);
        extractItemInfo(roomsJSON);
        setIterators();
        symbols = (HashMap) newSymbols;
      } catch (FileNotFoundException e) {
          System.out.println("Cannot find file named: " + filename);
      } catch (IOException e) {
          e.printStackTrace();
      } catch (ParseException e) {
          System.out.println("Error parsing JSON file");
      }
    }

    private void setIterators() {
      roomIterator = rooms.iterator();
      itemIterator = items.iterator();
    }
    /**
     * Get the symbol information.
     * @param symbolsJSON  (JSONObject) Contains information about the symbols
     */
    private void extractSymbolInfo(JSONObject symbolsJSON) {
        JSONArray symbolsJSONArray = (JSONArray) symbolsJSON.get("symbols");
        for (int i = 0; i < symbolsJSONArray.size(); i++) {
            JSONObject symbolObj = (JSONObject) symbolsJSONArray.get(i);
            symbols.put(symbolObj.get("name").toString(), String.valueOf(symbolObj.get("symbol")).charAt(0));
        }
    }

    /**
     * Get the room information.
     * @param roomsJSON (JSONObject) Contains information about the rooms
     */
    private void extractRoomInfo(JSONObject roomsJSON) {
        JSONArray roomsJSONArray = (JSONArray) roomsJSON.get("room");
        for (int i = 0; i < roomsJSONArray.size(); i++) {
            rooms.add(singleRoom((JSONObject) roomsJSONArray.get(i)));
            numOfRooms += 1;
        }
    }

    /**
     * Get a room's information.
     * @param roomJSON (JSONObject) Contains information about one room
     * @return (Map<String, String>) Contains key/values that has information about the room
     */
    private Map<String, String> singleRoom(JSONObject roomJSON) {
      HashMap<String, String> room = makeHashMap(roomJSON);
        JSONArray doorArray = (JSONArray) roomJSON.get("doors");
        for (int j = 0; j < doorArray.size(); j++) {
            JSONObject doorObj = (JSONObject) doorArray.get(j);
            String dir = String.valueOf(doorObj.get("dir"));
            room.replace(dir.toLowerCase() + "Pos", doorObj.get("wall_pos").toString());
            room.replace(dir.toLowerCase() + "Con", doorObj.get("con_room").toString());
        }
        JSONArray lootArray = (JSONArray) roomJSON.get("loot");
        for (int j = 0; j < lootArray.size(); j++) {
            itemLocations.add(itemPosition((JSONObject) lootArray.get(j), roomJSON.get("id").toString()));
        }
        return room;
    }

    private HashMap<String, String> makeHashMap(JSONObject roomJSON) {
      HashMap<String, String> room = new HashMap<>();
      room.put("id", roomJSON.get("id").toString());
      room.put("start", roomJSON.get("start").toString());
      room.put("height", roomJSON.get("height").toString());
      room.put("width", roomJSON.get("width").toString());
      room.put("ePos", "-1");
      room.put("nPos", "-1");
      room.put("sPos", "-1");
      room.put("wPos", "-1");
      room.put("eCon", "-1");
      room.put("nCon", "-1");
      room.put("sCon", "-1");
      room.put("wCon", "-1");
      return room;
    }
    /**
     * Create a map for information about an item in a room.
     * @param lootJSON (JSONObject) Loot key from the rooms file
     * @param roomID (String) Room id value
     * @return (Map<String, String>) Contains information about the item, where it is and what room
     */
    private Map<String, String> itemPosition(JSONObject lootJSON, String roomID) {
        HashMap<String, String> loot = new HashMap<>();
        loot.put("room", roomID);
        loot.put("id", lootJSON.get("id").toString());
        loot.put("x", lootJSON.get("x").toString());
        loot.put("y", lootJSON.get("y").toString());
        return loot;
    }

    /**
     * Get the Item information from the Item key.
     * @param roomsJSON (JSONObject) The entire JSON file that contains keys for room and items
     */
    private void extractItemInfo(JSONObject roomsJSON) {
        JSONArray itemsJSONArray = (JSONArray) roomsJSON.get("items");
        for (int i = 0; i < itemsJSONArray.size(); i++) {
            items.add(singleItem((JSONObject) itemsJSONArray.get(i)));
            numOfItems += 1;
        }
    }

    /**
     * Get a single item from its JSON object.
     * @param itemsJSON (JSONObject) JSON version of an item
     * @return (Map<String, String>) Contains information about a single item
     */
    private Map<String, String> singleItem(JSONObject itemsJSON) {
        HashMap<String, String> item = new HashMap<>();
        item.put("id", itemsJSON.get("id").toString());
        item.put("name", itemsJSON.get("name").toString());
        item.put("type", itemsJSON.get("type").toString());
        item.put("description", itemsJSON.get("description").toString());
        for (Map<String, String> itemLocation : itemLocations) {
            if (itemLocation.get("id").toString().equals(item.get("id").toString())) {
                item.put("room", itemLocation.get("room"));
                item.put("x", itemLocation.get("x"));
                item.put("y", itemLocation.get("y"));
                break;
            }
        }
        return item;
    }
}
