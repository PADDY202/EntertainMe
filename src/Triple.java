import java.util.Vector;


public class Triple {
	private String plug  = new String();
	private String middle  = new String();
	private String socket  = new String();
	int size = 3;
	public Triple(String plug,String middle, String socket)
	{
		setplug(plug);
		setMiddle(middle);
		setsocket(socket);
	}
	public int getSize ()
	{
		return size;
	}
	public void setplug (String input)
	{
		plug = (input.intern());
	}
	public void setMiddle (String input)
	{
		middle = (input.intern());
	}
	public void setsocket (String input)
	{
		socket = (input.intern());
	}
	public String getplug ()
	{
		return plug;
	}
	public String getMiddle ()
	{
		
		return  middle;
	}
	public String getsocket ()
	{
		
		return socket;
	}


}
