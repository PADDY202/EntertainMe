import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import twitter4j.User;

public class StrongestFollower {
	
	private final static String CONSUMER_KEY = "MKYGJWiFNkfog14jm8ucpvLhM";
    private final static String CONSUMER_KEY_SECRET =
     "bew0obL3uAtQzqSIJHYUOqRjkJQrkfQjiYQQKwryO6Tre524I0";
    private final static String AccessToken = "787938126404747264-ZghL5a1QTscrqKfV0g9renK5Vvu0UQ0";
    private final static String AccessTokenSecret = "PcaFAyPZHw7gHHvXKVaB7PaBlc8MwBTUiu4JWWFhH0w5g";
    private static long storystatusID =0;
	
    private  List<User> friends;
	private  User a =null;
	private  User b = null;
    private  Twitter twitter = new TwitterFactory().getInstance();
	ArrayList<String> topUsers = new ArrayList<String>();
	Random rand = new Random();
	
	public  StrongestFollower (List<User> infriends) throws TwitterException
	{
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
    	AccessToken oathAccessToken = new AccessToken(AccessToken,AccessTokenSecret);
    	twitter.setOAuthAccessToken(oathAccessToken);
    	
		friends = infriends;
		String filename = "Scealextric/DATA/TSV/Mitchell's Top 100 Twiter Users";
		String line = null;
		try {
			BufferedReader binput = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
			binput.readLine();		
			while ( (line = binput.readLine()) != null)  // Read a line at a time
			{
				
				topUsers.add(line);
			}
		}
		catch (IOException e)
		{
			System.out.println("Cannot find/load knowledge file: " + "Scealextric/DATA/Veale's script midpoints.xlsx");		
			e.printStackTrace();
		}
		topAandB();
	}
	
	private User getRandomTopUser() throws TwitterException
	{
		 int i = rand.nextInt(topUsers.size());
		 String rname = topUsers.get(i);
		 User userR = twitter.showUser(rname);
		 return userR;
	}
	
	
	//both a & b will be null if they are not allocated
	public void topAandB() throws TwitterException
	{
			boolean aFound = false;
			for(int i=0; i< topUsers.size(); i++)
			{
			String userIstr = topUsers.get(i);
			//System.out.println("-"+userIstr+"-");
			User userItwt = twitter.showUser(userIstr);
				if (friends.contains(userItwt))
				{
					if(!aFound)
					{
						a = userItwt;
						aFound=true;
					}
					else
					{
						b = userItwt;
					}
					
				}
			
			
			}		
	}
		
	public User getA() throws TwitterException
	{
		if(a==null)
		{
			a = getRandomTopUser();
		}
		return a;
	}
	public User getB() throws TwitterException
	{
		if(b==null)
		{
			b = getRandomTopUser();
		}
		return b;
	}
	public static void main(String[] args) throws TwitterException
	{
		List<User> l = null;
		new StrongestFollower(l).topAandB();
	}

}
