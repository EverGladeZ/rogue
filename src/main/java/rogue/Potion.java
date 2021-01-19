package rogue;

import java.awt.Point;

public class Potion extends Magic implements Edible, Tossable {

  /**
  * Default Constructor.
  */
  public Potion() {
    super();
  }

  /**
  * Item constructor that initalizes all fields with the passed in parameters.
  * @param newId represents the numerical id of the number
  * @param newName represents the name of the Item
  * @param newType represents the type of the item
  * @param newXYLocation represents the location of the item in the room (x,y)
  */
  public Potion(int newId, String newName, String newType, Point newXYLocation) {
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

  @Override
  public String eat() {
      return getDescription().split(":")[0];
  }
}
