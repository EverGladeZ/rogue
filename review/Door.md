| Method Signature | Responsibility | Instance Variables Used | Other Class Methods Called | Objects Used with Method Calls | Lines of Code |
|-|-|-|-|-|-|
| public Door() | Default constructor for the Door class. | connectedRooms | setIdConRoom() | N/A | 4 |
| public void setIdConRoom(int newIdConRoom) | Sets the id of the connected room. | idConRoom | N/A | N/A | 3 |
| public int getIdConRoom() | Gets the id of the connected room. | idConRoom | N/A | N/A | 3 |
| public void connectRoom(Room r) | Connects the room to the door. | connectedRooms | N/A | Room | 3 |
| public ArrayList<Room> getConnectedRooms() | Gets a list of all rooms connected to the door. | connectedRooms | N/A | N/A | 3 |
| public Room getOtherRoom(Room currentRoom) | Gets the other room connected to the door. | connectedRooms | N/A | N/A | 7 |