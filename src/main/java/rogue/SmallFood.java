package rogue;

import java.awt.Point;

public class SmallFood extends Food implements Tossable {

  /**
  * Default Constructor.
  */
  public SmallFood() {
    super();
  }

  /**
  * Item constructor that initalizes all fields with the passed in parameters.
  * @param newId represents the numerical id of the number
  * @param newName represents the name of the Item
  * @param newType represents the type of the item
  * @param newXYLocation represents the location of the item in the room (x,y)
  */
  public SmallFood(int newId, String newName, String newType, Point newXYLocation) {
    super(newId, newName, newType, newXYLocation);
  }

  @Override
  public String toss() {
    String[]values = getDescription().split(":");
    if (values.length == 1) {
      return values[0];
    } else {
      return values[1];
    }
  }
}
