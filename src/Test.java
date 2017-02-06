import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import twitterbotics.KnowledgeBaseModule;

public class Test {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub	
		TripleManager tm = new TripleManager();
		Story s =tm.MakeStoryLen(3);
		s.setAntagonist("Guénolé");
		s.setProtagonist("David");
		System.out.println(s.getStory());
		
	
	}
}
