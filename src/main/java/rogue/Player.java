package rogue;

import java.util.ArrayList;
import java.awt.Point;
import java.io.Serializable;
/**
 * The player character.
 */
public class Player implements Serializable {
private String name;
private ArrayList<Item> inventory;
private ArrayList<Item> equipped;
private Point xyLocation;
private Room currentRoom;
private static final long serialVersionUID = 2222L;


    /**
    * Default Player constructor that initalizes all fields.
    */
    public Player() {
      inventory = new ArrayList<Item>();
      equipped = new ArrayList<Item>();
      setName(null);
      setXyLocation(new Point(0, 0));
      setCurrentRoom(null);
    }

    /**
    * Player constructor that initalizes all fields and name with the passed in parameters.
    * @param newName represents the player's selected name
    */
    public Player(String newName) {
      inventory = new ArrayList<Item>();
      equipped = new ArrayList<Item>();
      setName(newName);
      setXyLocation(new Point(0, 0));
      setCurrentRoom(null);
    }

    /**
    * Getter for the name of the player.
    * @return (String) the player's name
    */
    public String getName() {
      return this.name;
    }

    /**
    * Adds item to player's inventory.
    * @param toAdd the item that will be added to the inventory.
    */
    public void addItemInventory(Item toAdd) {
      inventory.add(toAdd);
    }

    /**
    * Removes item from player's inventory.
    * @param toRemove the item that will be removed from the inventory.
    */
    public void removeItemInventory(Item toRemove) {
      inventory.remove(toRemove);
    }

    /**
    * Adds item to player's equipped slot.
    * @param toAdd the item that will be added to the equipped slot.
    */
    public void addItemEquipped(Item toAdd) {
      equipped.add(toAdd);
    }

    /**
    * Getter for the player's inventory.
    * @return (ArrayList<Item>) the player's inventory.
    */
    public ArrayList<Item> getInventory() {
      return this.inventory;
    }

    /**
    * Getter for the player's equipment.
    * @return (ArrayList<Item>) the player's equipment.
    */
    public ArrayList<Item> getEquipped() {
      return this.equipped;
    }

    /**
    * Setter for the name of the player.
    * @param newName the name that will be set for the player
    */
    public void setName(String newName) {
      this.name = newName;
    }

    /**
    * Getter for the player's location in the room.
    * @return (Point) the location of the player (x,y)
    */
    public Point getXyLocation() {
      return this.xyLocation;
    }

    /**
    * Setter for the player's location in the room.
    * @param newXyLocation the new location of the player (x,y)
    */
    public void setXyLocation(Point newXyLocation) {
      this.xyLocation = newXyLocation;
    }

    /**
    * Getter for the current room the player is in.
    * @return (Room) the room the player is in
    */
    public Room getCurrentRoom() {
      return this.currentRoom;
    }

    /**
    * Setter for the player's current room.
    * @param newRoom the room the player is in
    */
    public void setCurrentRoom(Room newRoom) {
      this.currentRoom = newRoom;
    }
}
