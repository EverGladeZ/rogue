package rogue;

public class NoSuchItemException extends Exception {
  /**
  * Default Exception.
  */
  public NoSuchItemException() {
  System.out.println("Cannot Add Item: No Such Item With That ID Exists ");
 }

}
