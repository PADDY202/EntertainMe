import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

//Find who following
//Find story base

public class CatergoryManager {
	private final static String CONSUMER_KEY = "MKYGJWiFNkfog14jm8ucpvLhM";
    private final static String CONSUMER_KEY_SECRET =
     "bew0obL3uAtQzqSIJHYUOqRjkJQrkfQjiYQQKwryO6Tre524I0";
    private final static String AccessToken = "787938126404747264-ZghL5a1QTscrqKfV0g9renK5Vvu0UQ0";
    private final static String AccessTokenSecret = "PcaFAyPZHw7gHHvXKVaB7PaBlc8MwBTUiu4JWWFhH0w5g";
    private static long storystatusID =0;
	
    private  List<User> friends;
    private   Twitter twitter = new TwitterFactory().getInstance();
    
	ArrayList<String> categoryNames = new ArrayList<String>();
	Hashtable<String,Category> categories = new  Hashtable<String,Category>();
	ArrayList<String> characterHanles = new ArrayList<String>();
	String targetUsersCategory ="";

	Random rand = new Random();
	
	public  CatergoryManager (List<User> infriends) throws TwitterException
	{	
		friends = infriends;
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
    	AccessToken oathAccessToken = new AccessToken(AccessToken,AccessTokenSecret);
    	twitter.setOAuthAccessToken(oathAccessToken);

		String filename1 = "Scealextric/DATA/TSV/Mitchells Releationships";
		String filename2 = "Scealextric/DATA/TSV/Mitchell's Top 100 Twiter Users";

		String line = null;
		try {
			BufferedReader binput1 = new BufferedReader(new InputStreamReader(new FileInputStream(filename1), "UTF-8"));
			while ( (line = binput1.readLine()) != null)  // Read a line at a time
			{
				
				StringTokenizer lineT = new StringTokenizer(line, "\t", true);
				Category currentCat = new Category();
				currentCat.setName(lineT.nextToken());
				categoryNames.add(currentCat.getName());
				categories.put(currentCat.getName(),currentCat);
				while (lineT.hasMoreTokens())
				{
					String releation= lineT.nextToken(); 
					
					if(!(releation.equals("\t")))
					{
						if(!(categoryNames.contains(releation)))
						{
							lineT.nextToken();
							currentCat.addActionCat(releation,lineT.nextToken());
						}
							
					}
				}

			}
			BufferedReader binput2 = new BufferedReader(new InputStreamReader(new FileInputStream(filename2), "UTF-8"));
			while ( (line = binput2.readLine()) != null)  // Read a line at a time
			{	
				StringTokenizer lineT = new StringTokenizer(line, "\t", true);
				String name =lineT.nextToken();
				lineT.nextToken();
				String handle =lineT.nextToken();
				Character character = new Character(name,handle);
				while (lineT.hasMoreTokens())
				{
					String category = lineT.nextToken();
					if(!(category.equals("\t")))
					{
						//if(categories.contains(category))
						//{
							Category currentCat = categories.get(category);
							currentCat.addCharacter(character);
						//}												
					}
				}				
			}
			targetUsersCategory = categoriseUser();
		}
		
		catch (IOException e)
		{
			System.out.println("Cannot find/load knowledge file: " + "Scealextric/DATA/Veale's script midpoints.xlsx");		
			e.printStackTrace();
		}
	}

	
	//Go through all the users in each category to if the user follows any of them if soo start a story
	
	private String categoriseUser()
	{
		TargetUser T = new TargetUser(categoryNames);
		for (int i =0; i < friends.size(); i++)
		{
			User friendI = friends.get(i);
			String handle = friendI.getScreenName();
			for (int c=0; c<categoryNames.size(); c++)
			{
				Category currentCat = categories.get(categoryNames.get(c));
				if(currentCat.contains(handle))
				{
					T.categoryScore(currentCat.getName());
				}
			}		
		}
		targetUsersCategory = T.getTop();
		return targetUsersCategory;
		
		
	}
	public Character getA()
	{
		return (categories.get(targetUsersCategory)).getRandomCharacter();
	}
	public Character getB()
	{
		 return categories.get(categories.get(targetUsersCategory).getneighbour()).getRandomCharacter();
	}
	public String getKey()

	{
		Category targetCat = categories.get(targetUsersCategory);
		String targetAction = targetCat.getAction();
		return targetAction;
	}
	
	//score by number of each category-> then pick characters from that category highest ranked category 
	//pick random with the possibility of adding sentiment
	
	
//	check for user matches()
	
	
	
//	find user's category
	public static void main(String[] args) throws TwitterException
	{
	//	new CatergoryManager();
		
		
	}
	

}
