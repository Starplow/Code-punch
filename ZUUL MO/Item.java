
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    private String ItemDescription;
    private double ItemWeight;

    /**
     * Constructor for objects of class Item
     */
    public Item(String description, double weight)
    {
        ItemDescription = description;
        ItemWeight = weight;
    }

    public String getItemDescription()
    {
        return ItemDescription;
    }
    
    public double getItemWeight()
    {
        return ItemWeight;
    }
}
