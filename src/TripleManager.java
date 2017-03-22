import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import com.sun.javafx.collections.MappingChange.Map;

public  class TripleManager {

	private Hashtable<String, ArrayList<Triple>> triples = new Hashtable<String, ArrayList<Triple>>();
	private String assignedKey = "are_bored_by";
	public  TripleManager ()
	{
		String filename = "Scealextric/DATA/TSV/Veale's script midpoints.txt";
		String line = null;
		try {
			BufferedReader binput = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
			binput.readLine();		
			while ( (line = binput.readLine()) != null)  // Read a line at a time
			{
				this.parseFieldsIntoTripleEntries(line);
			}
		}
		catch (IOException e)
		{
			System.out.println("Cannot find/load knowledge file: " + "Scealextric/DATA/Veale's script midpoints.xlsx");		
			e.printStackTrace();
		}
	}
	
	public void addTriple(String k, Triple t)
	{
		if (triples.contains(k))
		{
			ArrayList<Triple> currts = triples.get(k);
			currts.add(t);
			triples.put(k, currts);
		}
		else
		{
			ArrayList<Triple> ts = new ArrayList<Triple>();
			ts.add(t);
			triples.put(k, ts);
		}

	}
	public void addTriples(String k, ArrayList<Triple> ts)
	{
		if (triples.contains(k))
		{
			ArrayList<Triple> currts = triples.get(k);
			currts.addAll(currts);
			triples.put(k, currts);

		}
		else
		{
			triples.put(k, ts);
		}
	}

	
	public Triple getFirstTriple (ArrayList<Triple> ts)
	{
		return ts.get(0);
	}
	public ArrayList<Triple> getTriples (String k)
	{
		return triples.get(k);
	}
	public Triple getTriple (String k)
	{
		ArrayList<Triple> ts = getTriples(k);
		Triple t = getFirstTriple(ts);
		return t;
	}
	
	private String getSocket(String k)
	{
		String newsocket = this.getTriple(k).getsocket();
		return newsocket;
	}
	
	private Boolean hasPlugMatchingPlug (String k)
	{
		return this.triples.containsKey(k);
	}
	
	private void parseFieldsIntoTripleEntries( String line)
	{

		//where tripple entries is a list of all the plugs, sockets and middles
		StringTokenizer lineT = new StringTokenizer(line, "\t", true);
		ArrayList<ArrayList> tripleEntries = new ArrayList<ArrayList>();
		while (lineT.hasMoreTokens())
		{
			String tipleEntries= lineT.nextToken(); 
			if(!(tipleEntries.equals("\t")))
			{
				tripleEntries.add(getAllTripleEntries(tipleEntries));	
			}
		}
		parseTripleCombinations(tripleEntries);		
	}
	
	private void parseTripleCombinations(ArrayList<ArrayList> entryLists)
	{
		
		ArrayList<String> plugs = entryLists.get(0);
		ArrayList<String> middles = entryLists.get(1);
		ArrayList<String> sockets = entryLists.get(2);
	
			for(int plug=0; plug<plugs.size(); plug++)
			{
				for(int middle=0; middle<middles.size(); middle++)
				{
					for(int socket=0; socket<sockets.size(); socket++)
					{
						Triple t =new Triple(plugs.get(plug),middles.get(middle),sockets.get(socket));
						addTriple(t.getplug(),t);
					}
				}
			}
		}

	
	private ArrayList<String> getAllTripleEntries(String token)
	{

		ArrayList<String> tripleEntries = new ArrayList<String>();
		if (token.contains(",")) // more than one action to choose from
		{
			StringTokenizer tripleParts = new StringTokenizer(token, ", ", true);
			while(tripleParts.hasMoreTokens())
			{
				String triplePart = tripleParts.nextToken();
				/**
				if(!(triplePart.equals(",")))
				{		
					triplePart = triplePart.replace('_', ' ');
					token = token.replace("will ", "is ");
					tripleEntries.add(triplePart);

				}
				**/
			}
		}
		else 
		{
			/**
			token = token.replace('_', ' ');
			if (token.contains("are "))
			{
				token = token.replace("will ", "is ");
			}
			else
			{
				token ="will " + token;
			}
			**/
			tripleEntries.add(token);
		}
		return tripleEntries;
	}
	
	private Triple getRandomTriple()
	{
		Random random = new Random();
		List<String> keys = new ArrayList<String>(triples.keySet());
		String randomKey = keys.get( random.nextInt(keys.size()) );
		ArrayList<Triple> values = triples.get(randomKey);	
		return getRandomArrayElement(values);
	}
	public void setAssignedKey(String key)
	{
		assignedKey = key;
	}
	public Triple getAssignedTripple()
	{
		return getTriple(assignedKey);
	}
	private Triple getRandomArrayElement(ArrayList<Triple> ts)
	{
		Random random = new Random();
		return ts.get(random.nextInt(ts.size()));	
	}
	public Story MakeStoryLen(int len)
	{
		Story story = new Story(); 
		String plug= "";
		Triple cur =this.getAssignedTripple();
		story.addToStory(cur);
		while (story.numberOfTriples()<len)
		{
			for(int i=1; i<len; i++)
			{
				plug= this.getSocket(cur.getplug());
				if(this.hasPlugMatchingPlug(plug))
				{
					cur = this.getTriple(cur.getsocket());
					story.addToStory(cur);
				}
				else
				{
					cur =this.getRandomTriple();
					story.clearStory();
				}				
			}
		}	
		return story;		
	}

}
