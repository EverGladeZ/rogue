package rogue;

public class NotEnoughDoorsException extends Exception {
  /**
  * Default Exception.
  */
  public NotEnoughDoorsException() {
  System.out.println("Not Enough Doors: The room does not have a door. ");
 }

}
