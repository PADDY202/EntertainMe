import java.util.ArrayList;
import java.util.Hashtable;

public class Category {
	public Hashtable<String, String> pairs = new Hashtable<String, String>();
	private ArrayList<String> actions = new ArrayList<String>();
	private ArrayList<String> characters = new ArrayList<String>();

	private String categoryName;
	//hashtable action to category
	public void addActionCat(String action, String category)
	{
		actions.add(action);
		pairs.put(action,category);
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
	public void addCharacter(String name)
	{
		characters.add(name);
	}
	
	
	
	public ArrayList<String> getCharacters()
	{
		return characters;
	}

}
