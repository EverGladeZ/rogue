package rogue;

import java.awt.Point;

public class Clothing extends Item implements Wearable {

  /**
  * Default Constructor.
  */
  public Clothing() {
    super();
  }

  /**
  * Item constructor that initalizes all fields with the passed in parameters.
  * @param newId represents the numerical id of the number
  * @param newName represents the name of the Item
  * @param newType represents the type of the item
  * @param newXYLocation represents the location of the item in the room (x,y)
  */
  public Clothing(int newId, String newName, String newType, Point newXYLocation) {
    super(newId, newName, newType, newXYLocation);
  }

  @Override
  public String wear() {
      return getDescription();
  }
}
