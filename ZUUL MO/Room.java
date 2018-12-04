import java.util.HashMap;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */
public class Room 
{
    public String description;
    
    private HashMap<String,Room> exits;
    
    boolean hasLooked = false;
    public String detailedDescription ;
    
    private Inventory roomInventory;
    public String itemsDescription;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        this.detailedDescription = description;
        
        this.exits = new HashMap<String,Room>();
        
        this.roomInventory = new Inventory(false);
        itemsDescription = "";
    }
    
    /**
     * @return return roomInventory
     */
    public Inventory getInventory()
    {
        return roomInventory;
    }
    
    /**
     * @return return description for items
     */
    public String getItemsDescription()
    {
        return itemsDescription;
    }
    
    /**
     * @return set description for items
     */
    public void setItemsDescription()
    {
        itemsDescription = roomInventory.toString();
    }
    
    public HashMap<String,Room> getExits()
    {
        return exits;
    }
    
    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExit(String direction, Room whereToGo) 
    {
       exits.put(direction,whereToGo);
    }
    
    public String getExitsAvailable(){
        String result = "";
        for(String key : exits.keySet() ){
            result += key + " ";
        }
        return result;
    }
    
    public void setDetailedDescription(String text)
    {
        detailedDescription += "\n" + text;
    }
    
    public void setLooked()
    {
        hasLooked = true;
    }
    
    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        if(hasLooked)
        {
            return detailedDescription;
        }
        return description;
    }
}
