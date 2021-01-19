package rogue;

import java.awt.Point;

public class Magic extends Item {

    /**
    * Default Constructor.
    */
    public Magic() {
      super();
    }

    /**
    * Item constructor that initalizes all fields with the passed in parameters.
    * @param newId represents the numerical id of the number
    * @param newName represents the name of the Item
    * @param newType represents the type of the item
    * @param newXYLocation represents the location of the item in the room (x,y)
    */
    public Magic(int newId, String newName, String newType, Point newXYLocation) {
      super(newId, newName, newType, newXYLocation);
    }
}
