import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class Category {
	public Hashtable<String, String> actionAndCat = new Hashtable<String, String>();
	private ArrayList<String> actions = new ArrayList<String>();
	private ArrayList<Character> characters = new ArrayList<Character>();
	
	//Implement add when parsing
	private ArrayList<Category> nearestCategories = new ArrayList<Category>();
	private String categoryName;
	public void addActionCat(String action, String category)
	{
		actions.add(action);
		actionAndCat.put(action,category);
	}
	//list of handles in this category
	//add user
	public void setName(String name)
	{
		categoryName= name;
	}	
	public String getName()
	{
		return categoryName;
	}
	public void addCharacter(Character name)
	{
		
		characters.add(name);
	}
	public ArrayList<Character> getCharacters()
	{	
		return characters;
	}
	public Boolean contains(String name)
	{
		return characters.contains(name);		
	}
	public Character getRandomCharacter()
	{
    	Random rand = new Random();
 		int randomi =rand.nextInt(characters.size()-1); 
 		return characters.get(randomi);
	}

	public String getneighbour()
	{
		return actionAndCat.get(actions.get(0));
	}
	public String getAction()
	{

 		return actions.get(0);
	}
}
