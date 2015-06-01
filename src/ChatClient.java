import java.awt.*;

public class ChatClient extends Frame
{
	TextLine tl = new TextLine();
	
	public static void main(String[] args) 
	{
		new ChatClient().launchFrame();
	}
	public void launchFrame()
	{
		this.setLocation(100,100);
		this.setSize(300, 300);
		this.setVisible(true);
		this.setBackground(Color.blue);
	}
}
