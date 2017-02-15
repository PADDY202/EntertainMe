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
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
	
public class TwitterBot {
	private final static String CONSUMER_KEY = "fy0bYavlMHXfqdbmaIOBQNIhm";
    private final static String CONSUMER_KEY_SECRET =
     "p5ipEkEF0FRZGXrID2MmKmgHH8wQraP76JhHa3O4TdbUE1n1Y0";
    private final static String AccessToken = "787938126404747264-dpHR0TOII0eiH4BVZ8yFWsKvIddIKx1";
    private final static String AccessTokenSecret = "16t3g3zFuzrGFBypnBe4l73nHWIYGIFFMYUVJvzpDS5Wo";
    private static long storystatusID =0;
    private static Twitter twitter = new TwitterFactory().getInstance();
    
    public void start() throws TwitterException, IOException {

    	twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
    	AccessToken oathAccessToken = new AccessToken(AccessToken,AccessTokenSecret);
    	twitter.setOAuthAccessToken(oathAccessToken);
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
    	if( text.contains("entertainmeucd")&&(text.contains("yes")||text.contains("Yes")|| text.contains("YES")))
    	{
    	
    		return true;
    	}
    	else
    	{
        	return false;
    	}
    }

    
    public static void main(String[] args) throws Exception {
    	new TwitterBot().start();
    	ConfigurationBuilder cb = buildConfig();
    	TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
    	StatusListener listener = new StatusListener() {
    		public void onStatus(Status status) {
    		System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
    		String p = "@"+status.getUser().getScreenName();
    		long cursor = -1;
        	 if(consent(status.getText()))
        	 {
        		try {
        		List<User> users= twitter.getFriendsList(status.getUser().getId(), cursor);
    			Random rand = new Random();
    			int randomi =rand.nextInt(users.size()-1);    			
    			long replyTo=status.getId();  			
    			String a = "@"+users.get(randomi).getScreenName();
    			TripleManager tm = new TripleManager();
    			Story s =tm.MakeStoryLen(3);
    			s.setAntagonist(a);
    			s.setProtagonist(p);
    			Vector<String> stringStory  = s.getStory();
    			for (int i=0; i <stringStory.size()-1; i++)
    			{
    			    StatusUpdate stat= new StatusUpdate("@" + status.getUser().getScreenName() + " "+ stringStory.get(i));   			   
    			    TimeUnit.SECONDS.sleep(1);
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
        	 if (status.getText().contains("#EntertainMe") || status.getText().contains("#Entertainme")||status.getText().contains("#entertainme") )
        	 {
 			    ConsentManager cm = new ConsentManager();
        		StatusUpdate stat= new StatusUpdate("@" + status.getUser().getScreenName() + " " + cm.getRandomConsent());
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

 FilterQuery fq = new FilterQuery();
 String keywords[] = {"#EntertainMe", "#entertainme","#Entertainme", "entertainmeucd"};

 fq.track(keywords);

 twitterStream.addListener(listener);
 twitterStream.filter(fq);      
}


    }