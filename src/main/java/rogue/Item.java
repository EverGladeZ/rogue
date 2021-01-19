package rogue;

import java.awt.Point;
import java.io.Serializable;

/**
 * A basic Item class; basic functionality for both consumables and equipment.
 */
public class Item implements Serializable {
private int id;
private int roomId;
private String name;
private String type;
private String description;
private Point xyLocation;
private Room currentRoom;
private Character displayCharacter;
private static final long serialVersionUID = 4123L;



    //Constructors

    /**
    * Default Item constructor that initalizes all fields.
    */
    public Item() {
      setId(-1);
      setRoomId(0);
      setName(null);
      setType(null);
      setDescription(null);
      setXyLocation(new Point(-1, -1));
      setCurrentRoom(null);
      setDisplayCharacter(null);
    }

    /**
    * Item constructor that initalizes all fields with the passed in parameters.
    * @param newId represents the numerical id of the number
    * @param newName represents the name of the Item
    * @param newType represents the type of the item
    * @param newXYLocation represents the location of the item in the room (x,y)
    */
    public Item(int newId, String newName, String newType, Point newXYLocation) {
      setId(newId);
      setName(newName);
      setType(newType);
      setXyLocation(newXYLocation);
    }

    // Getters and setters


    /**
    * Getter for the id field of item.
    * @return (int) the id of the given item
    */
    public int getId() {
      return this.id;
    }

    /**
    * Setter for the id field of item.
    * @param newId the id value that will be set for the id field in item
    */
    public void setId(int newId) {
      this.id = newId;
    }

    /**
    * Getter for the roomId field of item.
    * @return (int) the id of the room of the given item
    */
    public int getRoomId() {
      return this.roomId;
    }

    /**
    * Setter for the roomId field of item.
    * @param newRoomId  the id value that will be set for the roomId field in item
    */
    public void setRoomId(int newRoomId) {
      this.roomId = newRoomId;
    }

    /**
    * Getter for the name field of item.
    * @return (String) the name of the given item
    */
    public String getName() {
      return this.name;
    }

    /**
    * Setter for the name field of item.
    * @param newName the name that will be set for the name field in item
    */
    public void setName(String newName) {
      this.name = newName;
    }

    /**
    * Getter for the type field of item.
    * @return (String) the type of the given item
    */
    public String getType() {
      return this.type;
    }

    /**
    * Setter for the type field of item.
    * @param newType the type of the item that will be set for the type field in item
    */
    public void setType(String newType) {
      this.type = newType;
    }

    /**
    * Getter for the display character of item.
    * @return (Character) the character that the item is displayed as
    */
    public Character getDisplayCharacter() {
      return this.displayCharacter;
    }


    /**
    * Setter for the display character of item.
    * @param newDisplayCharacter the character that the item will be displayed as
    */
    public void setDisplayCharacter(Character newDisplayCharacter) {
      this.displayCharacter = newDisplayCharacter;
    }

    /**
    * Getter for the description of item.
    * @return (String) the description of the item
    */
    public String getDescription() {
      return this.description;
    }

    /**
    * Setter for the description of the item.
    * @param newDescription the new description of the item
    */
    public void setDescription(String newDescription) {
      this.description = newDescription;
    }

    /**
    * Getter for the item's location in the room.
    * @return (Point) the location of the item (x,y)
    */
    public Point getXyLocation() {
      return this.xyLocation;
    }

    /**
    * Setter for the item's location in the room.
    * @param newXyLocation the new location of the item (x,y)
    */
    public void setXyLocation(Point newXyLocation) {
      this.xyLocation = newXyLocation;
    }

    /**
    * Getter for the item's room.
    * @return (Room) the room the item is in
    */
    public Room getCurrentRoom() {
      return this.currentRoom;
    }

    /**
    * Setter for the item's room.
    * @param newCurrentRoom the room the item is in
    */
    public void setCurrentRoom(Room newCurrentRoom) {
      this.currentRoom = newCurrentRoom;
    }
}
