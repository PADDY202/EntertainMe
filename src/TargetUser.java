import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class TargetUser {
	private TreeMap<String,Integer >  scores = new TreeMap<String ,Integer >();
	private int topScore=0;
	private String topCat="Media";
	public TargetUser(ArrayList<String> addCategories)
	{
		addCategories(addCategories);
	}
	private boolean contains(Category value)
	{
		return scores.containsKey(value);
	}
	private void addCategories(ArrayList<String> categoryNames)
	{
		for(int i=0; i<categoryNames.size(); i++)
		{
			String categoryI = categoryNames.get(i);
			scores.put(categoryI, 0);
		}
	}
	
	public int getCategoryScore(String category)
	{
		return scores.get(category);
	}
	public void categoryScore(String category)
	{
		int score = this.getCategoryScore(category) +1;
		if (score > topScore)
		{
			topCat = category;
		}
		scores.put(category, score);
	}
	public String getTop()
	{
		System.out.println(topScore);
		System.out.println(topCat);
		return topCat;
	}

}
