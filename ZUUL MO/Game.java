import java.util.HashMap;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room lastRoom;
    private Inventory playerInventory;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        
        playerInventory = new Inventory(true);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, hall, bedroom, diningroom, office;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        hall = new Room("in a hall");
        bedroom = new Room("in a dark and dusty bedroom");
        diningroom = new Room("in a huge dark room, which walls you cannot see.");
        office = new Room("in the office room");
        
        // initialise room exits
        /** north, east, south, west
         *  exits for each room */
         
        outside.setExit("north"  , hall );
        
        outside.setDetailedDescription("Further information for this room..");
        
        outside.getInventory().add(new Item("Apple", 0.3) );
        outside.getInventory().add(new Item("rusted oil lamp", 8.0) );
        outside.setItemsDescription();
        
        
        hall.setExit("south", outside);
        hall.setExit("west", bedroom);
        hall.setExit("east", office);
        hall.setExit("north", diningroom);
        
        hall.getInventory().add(new Item("Sword", 10.0) );
        hall.getInventory().add(new Item("small oil container", 0.5) );
        hall.setItemsDescription();
        
        
        bedroom.setExit("east", hall);
        
        bedroom.getInventory().add(new Item("Key", 1.0) );
        bedroom.setItemsDescription();
        
        
        diningroom.setExit("south", hall);
        
        diningroom.getInventory().add(new Item("diary lost page", 0.1) );
        diningroom.setItemsDescription();
        
        
        office.setExit("west", hall);
        
        office.getInventory().add(new Item("diary lost page", 0.1) );
        office.setItemsDescription();
        
        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        System.out.print( currentRoom.getExitsAvailable() ) ;
        System.out.println();
        
        System.out.print("Items can be found in the room: ");
        System.out.println(currentRoom.getItemsDescription() );
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go") )
            goRoom(command);
        else if (commandWord.equals("quit"))
            wantToQuit = quit(command);
        else if (commandWord.equals("back"))
            goBack(command);
            
        else if(commandWord.equals("look"))
        {
            currentRoom.setLooked();
            System.out.println(currentRoom.getDescription() );
        }
        else if(commandWord.equals("eat"))
        {
            System.out.println("You have eaten now and are not hungry any more");
        }
        else if(commandWord.equals("sleep"))
        {
             System.out.println("You're now sleeping");
        }
        
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void goBack(Command command)
    {
        
            currentRoom = lastRoom;
            
            
            System.out.println("You are " + currentRoom.getDescription());
            System.out.print("Exits: ");
            System.out.print( currentRoom.getExitsAvailable() ) ;
            System.out.println();
            
            System.out.print("Items can be found in the room: ");
            System.out.println(currentRoom.getItemsDescription() );
        
        
        
        
    }
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        System.out.print( currentRoom.getExitsAvailable() ) ;
        
        System.out.println("Your command words are:");
        parser.showAllCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        HashMap<String,Room> possibleExits = currentRoom.getExits();
        Room nextRoom = possibleExits.get(direction);
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            lastRoom = currentRoom;
            currentRoom = nextRoom;
            
            System.out.println("You are " + currentRoom.getDescription());
            System.out.print("Exits: ");
            System.out.print( currentRoom.getExitsAvailable() ) ;
            System.out.println();
            
            System.out.print("Items can be found in the room: ");
            System.out.println(currentRoom.getItemsDescription() );
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
