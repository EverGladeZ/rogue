Name: Jakub Wozniak\
Email: jakub@uoguelph.ca\
ID: 1090034

--------- Declaration of Original Work ---------\
All of the work I have done for this assignment is entirely my own and I did not copy work for any other students or external sources.

--------- Running and Compiling ---------\
In order to compile the program use "gradle build".

In order to run the .jar file, please use "java -jar build/libs/A3.jar".

All information in A3_rooms.json and symbols-map.json can be changed to the users liking.

--------- Controls ---------\
When inputting controls make sure CapsLock is off. A summary of your last move will appear in the top left beside your player name.

Arrow Up / I    : Move Up\
Arrow Down / K  : Move Down\
Arrow Left / J  : Move Left\
Arrow Right / L : Move Right\
E               : Eat Item\
W               : Wear Item\
T               : Toss Item\
Q               : Quit Game

--------- Item Management ---------\
Items will be picked up and added to inventory when walked over.\

Edible Items: Food, SmallFood, Potion\
Tossable Items: SmallFood, Potion\
Wearable Items: Clothing, Ring

--------- Changing Name ---------\
In order to change player name: File > Change Name\
You should be prompted to input a name.

--------- Saving Game ---------\
In order to save your game: File > Save Game\
You should be prompted to input a name for your save file and choose a location. The file will have an extension of ".rgx"

--------- Loading JSON ---------\
In order to load a Room JSON: File > Load JSON\
You should be prompted to open a file. Please open a Rooms JSON with the extension ".json"

--------- Loading Save ---------\
In order to load a game save file: File > Load Game\
You should be prompted to open a file. Please open a previously made save file with the extension ".rgx"

--------- Other Information ---------\
Walls cannot be passed through.\
Items in invalid positions will get placed in the first available space.\
A room without doors will be automatically assigned a door and linked to an available room, if a link is not possible then the program will exit.
