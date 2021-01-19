package rogue;

import java.util.ArrayList;
import java.io.Serializable;

public class Door implements Serializable {
  private ArrayList<Room> connectedRooms;
  private int idConRoom;
  private static final long serialVersionUID = 3412L;

 /**
 * Default Constructor for Door.
 */
  public Door() {
    this.connectedRooms = new ArrayList<Room>();
    setIdConRoom(-1);
    }

  /**
  * Sets the id of the connected room.
  * @param newIdConRoom the id of the connected room that will be set
  */
  public void setIdConRoom(int newIdConRoom) {
    this.idConRoom = newIdConRoom;
  }

  /**
  * Gets the id of the connected room.
  * @return the id of the connected room
  */
  public int getIdConRoom() {
    return this.idConRoom;
  }

  /**
  * Adds the room to the list of rooms connected to the door.
  * @param r the room that will be added to the list
  */
  public void connectRoom(Room r) {
      connectedRooms.add(r);
  }

  /**
  * Gets the whole room list of all the rooms connected to the door.
  * @return a list of all rooms connected to the door
  */
  public ArrayList<Room> getConnectedRooms() {
        return this.connectedRooms;
  }

  /**
  * Gets the other room connected to the door.
  * @param currentRoom the room from which the door is being accessed
  * @return the room the is on the other side of the door
  */
  public Room getOtherRoom(Room currentRoom) {
    if (connectedRooms.get(0) == currentRoom && connectedRooms.size() > 1) {
      return connectedRooms.get(1);
    } else {
      return connectedRooms.get(0);
    }
  }
}
