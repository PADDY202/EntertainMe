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


	Random rand = new Random();
	
	public  CatergoryManager () throws TwitterException
	{	
		
		//List<User> infriends
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
				//System.out.println(twitter.searchUsers(line, 1).get(0).getScreenName());
				//System.out.println(currentCat.pairs.get("are_worshipped_by"));
			}
			BufferedReader binput2 = new BufferedReader(new InputStreamReader(new FileInputStream(filename2), "UTF-8"));
			while ( (line = binput2.readLine()) != null)  // Read a line at a time
			{	
				StringTokenizer lineT = new StringTokenizer(line, "\t", true);
				String name =lineT.nextToken();
				lineT.nextToken();
				String handle =lineT.nextToken();
				while (lineT.hasMoreTokens())
				{
					String category = lineT.nextToken();
					if(!(category.equals("\t")))
					{
						//if(categories.contains(category))
						//{
							Category currentCat = categories.get(category);
							currentCat.addCharacter(name);
						//}
						
						
					}
				}

				
			}
			System.out.println(categories.get("Media").getCharacters());

			
			
		}
		
		catch (IOException e)
		{
			System.out.println("Cannot find/load knowledge file: " + "Scealextric/DATA/Veale's script midpoints.xlsx");		
			e.printStackTrace();
		}
	}

	
	//Go through all the users in each category to if the user follows any of them if soo start a story
	
	
	
	
//	check for user matches()
	
	
	
//	find user's category
	public static void main(String[] args) throws TwitterException
	{
		new CatergoryManager();
		
	}
	

}
