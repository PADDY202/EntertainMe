import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class ConsentManager {
	ArrayList<String> consentRequests = new ArrayList<String>();
	Random rand = new Random();
	public  ConsentManager ()
	{
		String filename = "Scealextric/DATA/TSV/Mitchell's Consents";
		String line = null;
		try {
			BufferedReader binput = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
			binput.readLine();		
			while ( (line = binput.readLine()) != null)  // Read a line at a time
			{
				consentRequests.add(line);
			}
		}
		catch (IOException e)
		{
			System.out.println("Cannot find/load knowledge file: " + "Scealextric/DATA/Veale's script midpoints.xlsx");		
			e.printStackTrace();
		}
	}
	
	public String getRandomConsent()
	{
		 int i = rand.nextInt(consentRequests.size());
		 return consentRequests.get(i);
	}


}
