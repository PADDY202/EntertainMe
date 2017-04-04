import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

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
	
public class TwitterBot {
	private final static String CONSUMER_KEY = "copFA2uJibOQSieLTtUPsQTZc";
    private final static String CONSUMER_KEY_SECRET =
     "8g1y7sVff0L8iuEnjMrpQmDs2RAHY4Zt7qsa5tKbEwEtFynG4T";
    private final static String AccessToken = "787938126404747264-HZE15LrSD3SpwnViIC9B1OAtbZejvvX";
    private final static String AccessTokenSecret = "F5mF3V4F0MToeJIiDkNGKShXMwExOdVAjhJba8aDJr45F";
    private static long storystatusID =0;
    private static Twitter twitter = new TwitterFactory().getInstance();
	public String a = null,b = null, key = "are_bored_by";

    
    public void start() throws TwitterException, IOException {

    	twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
    	AccessToken oathAccessToken = new AccessToken(AccessToken,AccessTokenSecret);
    	twitter.setOAuthAccessToken(oathAccessToken);
    //	new StrongestFollower();
 	
    }
    
    private static ConfigurationBuilder buildConfig()
    {
    	ConfigurationBuilder cb = new ConfigurationBuilder();
    	 cb.setOAuthConsumerKey(CONSUMER_KEY);
    	 cb.setOAuthConsumerSecret(CONSUMER_KEY_SECRET);
    	 cb.setOAuthAccessToken(AccessToken);
    	 cb.setOAuthAccessTokenSecret(AccessTokenSecret);
    	 return cb;
    }
    
    private static boolean consent (String text)
    {
    	if((text.contains("yes")||text.contains("Yes")|| text.contains("YES")))
    	{
    		System.out.print("Why You No Work!!!!!!!!!!");
    		return true;
    	}
    	else
    	{
        	return false;
    	}
    }
    private String getUserName(String searchName) throws TwitterException
    {
    	return twitter.searchUsers(searchName, 1).get(0).getScreenName();
    }

    
    public static void main(String[] args) throws Exception {
    	
    	final TwitterBot tb = new TwitterBot();
    	tb.start();
    	//Testing userList
    	
    	ConfigurationBuilder cb = buildConfig();
    	TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

    	StatusListener listener = new StatusListener() {
    		
    		public void onStatus(Status status) {
    		System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
    		long cursor = -1;
    		
	    		//Getting B
	    		String personalB = "@"+status.getUser().getScreenName();
	    	    		
    		//If Consent Granted
        	 if(consent(status.getText()))
        	 {
        		try { 
    			TripleManager tm = new TripleManager();
    			tm.setAssignedKey(tb.key);
    			Story s =tm.MakeStoryLen(3);
    			
    			//Setting A & B
    			s.setAntagonist(tb.a);
    			s.setProtagonist(tb.b);
    			
    			long replyTo=status.getId();  			
    			Vector<String> stringStory  = s.getStory();
    			for (int i=0; i <stringStory.size()-1; i++)
    			{
    			    StatusUpdate stat= new StatusUpdate("@" + status.getUser().getScreenName() + " "+ stringStory.get(i));   			   
    			    TimeUnit.SECONDS.sleep(3);
    			    stat.setInReplyToStatusId(replyTo);
    			    replyTo= twitter.updateStatus(stat).getId();  			      				
    			}
    			
    		} catch (TwitterException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}  
        	 }
        	 
        	 //Consent Manager
        	 if (status.getText().contains("#EntertainMe") || status.getText().contains("#Entertainme")||status.getText().contains("#entertainme") )
        	 {
     			//ask user if they are happy with character selection and @ the 2 characters
         		//Then tell story if user responds postively
 	    		//Getting A as random friend
 	        	List<User> users = null;
 				try {
 					users = twitter.getFriendsList(status.getUser().getId(), cursor);
 				} catch (TwitterException e1) {
 					e1.printStackTrace();
 				} 
 	    		/**StrongestFollower storyChars = null;
 				User aTopUsr = null;
 				try {
 					aTopUsr = storyChars.getA();
 				} catch (TwitterException e1) {
 					e1.printStackTrace();
 				}
 	    		User bTopUsr = null;
 				try {
 					bTopUsr = storyChars.getB();
 				} catch (TwitterException e1) {
 					e1.printStackTrace();
 				} 		
 	    		//String aTopStr = aTopUsr.getName();
 	    		//String bTopStr = bTopUsr.getName();
 	    		**/
 	        	Random rand = new Random();
 	    		int randomi = Math.abs(rand.nextInt(users.size()-1));    			
 	    		String randA = users.get(randomi).getScreenName();
 	    		randomi =rand.nextInt(users.size()-1); 
 	    		String randB = users.get(randomi).getScreenName();
 	    		
 	    		
 	    		//deciding character
 	    			CatergoryManager cm;
					try {
						cm = new CatergoryManager(twitter.getFriendsList(status.getUser().getId(),cursor));
		    			tb.a = cm.getA().getName();
	 	    			tb.b = cm.getB().getName();
	 	 			    tb.key = cm.getKey();
	
					} catch (TwitterException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
 	    			

					/**
 	    		if (aTopUsr==null)
 	    		{
 	    			tb.a = randA;
 	    		}
 	    		else
 	    		{
 	    			tb.a = aTopUsr.getName();
 	    		}
 	    		if (bTopUsr==null)
 	    		{
 	    			tb.b = randB;
 	    		}
 	    		else
 	    		{
 	    			tb.b = bTopUsr.getName();
 	    		}
 	    		**/
        		ConsentManager conm = new ConsentManager();
        		StatusUpdate stat= new StatusUpdate("@" + status.getUser().getScreenName() + " " + conm.getRandomConsent()+tb.a+" and "+tb.b+"?");
 			    stat.inReplyToStatusId(status.getId());
 			    try {
					twitter.updateStatus(stat);
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	 }
        	 
   		
     }


     public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
         System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
     }

     public void onScrubGeo(long userId, long upToStatusId) {
         System.out.println("Got scrub_geo event userId:" + "@" + userId + " upToStatusId:" + upToStatusId);
     }

     public void onException(Exception ex) {
         ex.printStackTrace();
     }

	public void onDeletionNotice(StatusDeletionNotice arg0) {
		// TODO Auto-generated method stub		
	}

	public void onStallWarning(StallWarning arg0) {
		// TODO Auto-generated method stub		
	}
 };
	User user = twitter.showUser("SimonCowell");
	System.out.println("Test: "+user.getName());	
 FilterQuery fq = new FilterQuery();
 String keywords[] = {"#EntertainMeTom", "#entertainmetom","#Entertainmetom", "FictionalBot","#EntertainmeTom"};

 fq.track(keywords);

 twitterStream.addListener(listener);
 twitterStream.filter(fq);      
}


    }