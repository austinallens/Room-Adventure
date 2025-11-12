import java.util.Scanner;

class RoomAdventure {

    // class variables
    private static Room currentRoom;
    private static String[] inventory = {null, null, null, null, null};
    private static String status;
    private static boolean doorFlag = false;
    private static boolean coatFlag = false;


    final private static String DEFAULT_STATUS = "Sorry, I do not understand. Try [verb] [noun]. Valid verbs include 'go', 'look', 'take', and 'use'.";

    public static void main(String[] args){
         
        setupGame();
        Scanner s = new Scanner(System.in);

        // while loops
        while (true){
            // outputting
            System.out.println(currentRoom.toString());
            System.out.print("Inventory: ");

            // for loops
            for (int i = 0; i < inventory.length; i++){
                System.out.print(inventory[i] + " ");
            }


            System.out.println("\nWhat would you like to do? ");
        

            // taking input
            String input = s.nextLine();
            
            // .split() takes a string and turns it into an array
            // each item is the array is divided by the arg in split()
            String[] words = input.split(" ");

            if (words.length < 2){
                status = DEFAULT_STATUS;
            }

            String verb = words[0];
            String noun = words[1];
            String noun2 = words.length > 2 ? words[2] : "";

            switch (verb){
                case "go":
                    handleGo(noun);
                    break;
                case "look":
                    handleLook(noun);
                    break;
                case "take":
                    handleTake(noun);
                    break;
                case "use":
                    handleUse(noun, noun2);
                    break;
                default: status = DEFAULT_STATUS;
            }
            
            System.out.println(status);
            
                    
            
        }

    }

    private static void handleGo(String noun){
        status = "I don't see that room";
        String[] directions = currentRoom.getExitDirections();
        Room[] rooms = currentRoom.getExitDestinations();

        for (int i=0; i < directions.length; i++){
            // for strings we use .equals() to compare 
            if (noun.equals(directions[i])){
                currentRoom = rooms[i];
                status = "Changed Room";
            }
        }
    }

    private static void handleLook(String noun){
        status = "I don't see that item.";
        String[] items = currentRoom.getItems();
        String[] descriptions = currentRoom.getItemDescriptions();

        for (int i=0; i < items.length; i++){
            if (noun.equals(items[i])){
                status = descriptions[i];
            }
        }
    }

    private static void handleTake(String noun){
        status = "I can't grab that.";
        String[] grabs = currentRoom.getGrabbables();

        for (int i = 0; i < grabs.length; i++){
            if (noun.equals(grabs[i])){

                // maybe make a addToInventory() func?
                // maybe expand the inventory to any number of items
                for (int j=0; j < inventory.length; j++){
                    if (inventory[j] == null){
                        inventory[j] = noun;
                        // maybe say what item was added?
                        status = "Added item to inventory";
                        break;
                    }
                }

            }
        }
    }

    private static void handleUse(String grabNoun, String itemNoun){
        status = "I can't use that.";
    
        // Check if we have the item in inventory
        boolean hasItem = false;
        for (String item : inventory) {
            if (grabNoun.equals(item)) {
                hasItem = true;
                break;
            }
        }
    
        if (!hasItem) {
            status = "I don't have one of those.";
            return;
        }
    
        if (grabNoun.equalsIgnoreCase("key") && itemNoun.equalsIgnoreCase("door")) {
            status = "The door has been unlocked!";
            doorFlag = true;
        } else if (grabNoun.equalsIgnoreCase("coat") && itemNoun.equalsIgnoreCase("self")){
            status = "While wearing the fashionable coat you feel a lot swankier.";
            coatFlag = true;
        } else {
            status = "I can't use those items together.";
        }
    }

    private static void setupGame(){
        Room room1 = new Room("Room 1"); // instantiation of an object
        Room room2 = new Room("Room 2");
        Room room3 = new Room("Room 3");
        Room room4 = new Room("Room 4");

        // Room 1 - Bedroom
        String[] room1ExitDirections = {"east", "south"}; // declaring an array
        Room[]   room1ExitDestinations = {room2};
        String[] room1Items = {"chair", "desk", "bed"};
        String[] room1ItemDescriptions = {
            "It is a chair", 
            "Its a desk, there is a key on it.",
            "Its the bed you awoke on top of."
        };
        String[] room1Grabbables = {"key"};
        room1.setExitDirections(room1ExitDirections);
        room1.setExitDestinations(room1ExitDestinations);
        room1.setItems(room1Items);
        room1.setItemDescriptions(room1ItemDescriptions);
        room1.setGrabbables(room1Grabbables);

        // Room 2 - Living Room
        String[] room2ExitDirections = {"west"};
        Room[]   room2ExitDestinations = {room1};
        String[] room2Items = {"fireplace", "rug", "couch"};
        String[] room2ItemDescriptions = {
            "Its on fire", 
            "There is a lump of coal on the rug.",
            "The faux leather on the coach is peeling leaving behind little brown flecks that are annoying to clean."
        };
        String[] room2Grabbables = {"coal"};
        room2.setExitDirections(room2ExitDirections);
        room2.setExitDestinations(room2ExitDestinations);
        room2.setItems(room2Items);
        room2.setItemDescriptions(room2ItemDescriptions);
        room2.setGrabbables(room2Grabbables);

        // Room 3 - Bathroom
        String[] room3ExitDirections = {"north", "east"};
        Room[]   room3ExitDestinations = {room1, room4};
        String[] room3Items = {"toilet", "bathtub"};
        String[] room3ItemDescriptions = {
            "The most average and generic porcelain throne imaginable.", 
            "This bathtub has not been cleaned in at least a month. The reason you know this is unknown."
        };
        String[] room3Grabbables = {""};
        room3.setExitDirections(room3ExitDirections);
        room3.setExitDestinations(room3ExitDestinations);
        room3.setItems(room3Items);
        room3.setItemDescriptions(room3ItemDescriptions);
        room3.setGrabbables(room3Grabbables);

        // Room 4 - Entryway
        String[] room4ExitDirections = {"west", "north"};
        Room[]   room4ExitDestinations = {room3, room2};
        String[] room4Items = {"door", "rack"};
        String[] room4ItemDescriptions = {
            "The door is very well made, sturdy, mahogany... and locked tight.", 
            "There is a very fashionable tan coat hanging on the the coat rack."
        };
        String[] room4Grabbables = {"coat"};
        room4.setExitDirections(room4ExitDirections);
        room4.setExitDestinations(room4ExitDestinations);
        room4.setItems(room4Items);
        room4.setItemDescriptions(room4ItemDescriptions);
        room4.setGrabbables(room4Grabbables);

        currentRoom = room1;
    }
}

class Room {

    private String name;
    private String[] exitDirections;    // north, south, east, west
    private Room[] exitDestinations;
    private String[] items;
    private String[] itemDescriptions;
    private String[] grabbables;

    // constructors - function has same name as class
    public Room(String name){
        this.name = name; // use this to refer to the instance when it is unclear
    }

    // other methods
    public void setExitDirections(String[] exitDirections){
        this.exitDirections = exitDirections;
    }
    

    public String[] getExitDirections(){
        return exitDirections;
    }
    
    public void setExitDestinations(Room[] exitDestinations){
        this.exitDestinations = exitDestinations;
    }

    public Room[] getExitDestinations(){
        return exitDestinations;
    }

    public void setItems(String[] items){
        this.items = items;
    }

    public String[] getItems(){
        return items;
    }
    
    public void setItemDescriptions(String[] itemDescriptions){
        this.itemDescriptions = itemDescriptions;
    }

    public String[] getItemDescriptions(){
        return itemDescriptions;
    }


    public String[] getGrabbables(){
        return grabbables;
    }

    public void setGrabbables(String[] grabbables){
        this.grabbables = grabbables;
    }

    public String toString(){
        String result = "\n";
        result += "Location: " + name;

        result += "\nYou See: ";
        // for i loop
        for (int i = 0; i < items.length; i++){
            result += items[i] + " ";
        }

        result += "\nExits: ";
        // for each loop
        for (String direction : exitDirections){
            result += direction + " ";
        }

        return result + "\n";
    }
}