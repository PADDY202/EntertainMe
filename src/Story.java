import java.util.Random;

import java.util.Vector;

import twitterbotics.KnowledgeBaseModule;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import sun.net.www.protocol.http.HttpURLConnection;

public class Story {
	KnowledgeBaseModule initialEnd = new KnowledgeBaseModule("Scealextric/DATA/TSV/Veale's initial bookend actions.txt", 1); //key value on column 1
	KnowledgeBaseModule closingEnd = new KnowledgeBaseModule("Scealextric/DATA/TSV/Veale's closing bookend actions.txt", 1); //key value on column 1	
	KnowledgeBaseModule idomatic = new KnowledgeBaseModule("Scealextric/DATA/TSV/Veale's idiomatic actions.txt", 1); //key value on column 1	
	private Vector<Triple> tripleStory  = new Vector(); 
	private Vector<String> stringStory  = new Vector();
	private String Protagonist;
	private String Antagonist;

	public void setProtagonist (String name)
	{
		Protagonist = name;
	}
	
	public void setAntagonist (String name)
	{
		Antagonist = name;
	}
	
	public void addToStory (Triple t)
	{
		tripleStory.add(t);
	}
	

	public int numberOfTriples()
	{
		return tripleStory.size();
	}
	
	public void clearStory()
	{
		tripleStory = new Vector();
	}
	
	public int GetSize ()
	{
		return tripleStory.size();
	}

	private String formatChars (String pre)
	{
		if (pre.contains("A ")){
		pre = pre.replace("A ", "%s ");
		}
		if (pre.contains("A'")){
		pre = pre.replace("A'", "%s'");
		}
		if (pre.contains("A\"")){
		pre = pre.replace("A\"", "%s");
		}
		if(pre.endsWith("A"))
		{
			pre = pre.substring(0, pre.length()-1)+"%s";
		}
		pre=String.format(pre, Antagonist, Antagonist);
		if (pre.contains("B ")){
		pre = pre.replace("B ", "%s ");
		}
		if (pre.contains("B'")){
		pre = pre.replace("B'", "%s'");
		}
		if (pre.contains("B\"")){
		pre = pre.replace("B\"", "%s");
		}
		if(pre.endsWith("B"))
		{
			pre = pre.substring(0, pre.length()-1)+"%s";
		}
		pre=String.format(pre, Protagonist, Protagonist);
		return pre;
	}
 	
	private String getLastAction()
	{
		return tripleStory.lastElement().getsocket();
	}
	
	private String getFirstAction()
	{
		return tripleStory.firstElement().getplug();
	}
	
	private String capitaliseFirstLetter(String sentence)
	{
		String first= String.valueOf(sentence.charAt(0)).toUpperCase();
		sentence = first + sentence.substring(1, sentence.length());
		return sentence;		
	}
	
	private String removeQoutesAtStart(String sentence)
	{
		if (sentence.charAt(0)=='"')
		{
			return sentence.substring(1); 
		}
		else
		{
			return sentence;
		}
	}
	private String removeQoutesAtEnd(String sentence)
	{
		if (sentence.charAt(sentence.length()-1)=='"')
		{
			return sentence.substring(0, sentence.length()-1); 
		}
		else
		{
			return sentence;
		}
	}
	
	private String getEnding()
	{
		Vector closingEndKey = closingEnd.getAllKeysWithFieldValue("Final Action", getLastAction());	
		if (!(closingEndKey.isEmpty()))
		{
			Random r = new Random();
			int i =r.nextInt(closingEnd.getFieldValues("Closing Action", (String) closingEndKey.firstElement()).size());
			String end = (String) closingEnd.getFieldValues("Closing Action", (String) closingEndKey.firstElement()).get(i);		
			end = removeQoutesAtStart(end);
			end =removeQoutesAtEnd(end);
			end = capitaliseFirstLetter(end);	
			end =formatChars(end);
			return end;
		}
		else
		{
			return "The End";
		}
	}
	
	private String renderActions (String action)
	{
		Boolean normalOrder = true;
		if (action.contains("*"))
		{
			normalOrder = false;
			action = action.substring(1);
		}
		Vector renderings = idomatic.getAllKeysWithFieldValue("Action", action);	
		if (!(renderings.isEmpty()))
		{
			Random r = new Random();
			int i =r.nextInt(idomatic.getFieldValues("Idiomatic Forms", (String) renderings.firstElement()).size());
			String rendering = (String) idomatic.getFieldValues("Idiomatic Forms", (String) renderings.firstElement()).get(i);


			if (normalOrder)
			{
				if(rendering.contains(" its "))
				{
				//	System.out.println(rendering);
				//	System.out.println("_____________B_______________");
					rendering = rendering.replaceAll(" its ", " B's ");
				//	System.out.println(rendering);

				}
				if(rendering.contains(" it "))
				{
				//	System.out.println(rendering);
				//	System.out.println("_____________A_______________");
					rendering = rendering.replaceAll(" it ", " A ");
				//	System.out.println(rendering);

				}
				rendering = formatChars(rendering);
			}
			else
			{
				if(rendering.contains(" its "))
				{
				//	System.out.println(rendering);
				//	System.out.println("_____________B_______________");
					rendering = rendering.replaceAll(" its ", " A's ");
				//	System.out.println(rendering);

				}
				if(rendering.contains(" it "))
				{
				//	System.out.println(rendering);
				//	System.out.println("_____________A_______________");
					rendering = rendering.replaceAll(" it ", " B ");
				//	System.out.println(rendering);

				}
				rendering = formatChars(rendering);
			}
			
			return rendering;
		}
		else
		{
			return action;
		}
	}
	
	private String getStart()
	{

		Vector initialEndKey = initialEnd.getAllKeysWithFieldValue("Initial Action", getLastAction());	
		if(!(initialEndKey.isEmpty()))
		{
			Random r = new Random();
			int i = r.nextInt(initialEnd.getFieldValues("Establishing Action", (String) initialEndKey.firstElement()).size());
			String start =  (String) initialEnd.getFieldValues("Establishing Action", (String) initialEndKey.firstElement()).get(i);		
			start =removeQoutesAtStart(start);
			start =removeQoutesAtEnd(start);
			start = formatChars(start);
			return start;
		}
		else
		{
			return "In A and B's world"; //when no start
		}
	}
	
	public Vector getStory()
	{
		stringStory.add(getStart());
		for (int i=0; i < tripleStory.size(); i++)
		{
				stringStory.add(renderActions(tripleStory.get(i).getplug()));
				stringStory.add(renderActions(tripleStory.get(i).getMiddle()));
				if (i==tripleStory.size()-1)
				{
					stringStory.add( renderActions(tripleStory.get(i).getsocket()));
				}
		}
		stringStory.add(getEnding());
		return stringStory;
	}	
	public void printStory() throws IOException
	{
		
		getStart();
		stringStory = this.getStory();
		String lines= "";
		for (int i=0; i < stringStory.size(); i++)
		{
		     // System.out.println(stringStory.get(i));
			lines = lines+ stringStory.get(i)+ "\n";
		}

	}	}

