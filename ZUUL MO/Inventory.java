import java.util.ArrayList;
/**
 * Write a description of class Inventory here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Inventory
{
    private ArrayList<Item> items;
    private double capacity;

    /**
     * Constructor for objects of class Inventory
     * 
     * defines the capacity of the inventory of the player
     */
    public Inventory(boolean playerInventory)
    {
        items = new ArrayList<Item>();
        if(playerInventory)
        {
            capacity = 20.0;
        }
        else
        {
            capacity = Double.MAX_VALUE;
        }
    }
    
    /**
     * add item to inventory (ArrayList)
     */
    public boolean add(Item i)
    {
        if(capacity >= i.getItemWeight() )
        {
            items.add(i);
            return true;
        }
        return false;
    }
    
    public String toString()
    {
        //Apple Diary Key
        String s = "";
        for(Item i: items)
        {
            s = s + i.getItemDescription() + "c, ";
        }
        return s;
    }
}
