import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.IOException;
import java.net.*;

public class ChatClient extends Frame
{
	private TextField tf = new TextField();
	private TextArea  ta= new TextArea();
	private Socket s = null;
	
	public static void main(String[] args) 
	{
		new ChatClient().launchFrame();
	}
	
	public void launchFrame()
	{
		this.setLocation(100,100);
		this.setSize(300, 300);
		this.setVisible(true);
		this.setResizable(true);
		add(tf,BorderLayout.SOUTH);
		add(ta,BorderLayout.NORTH);
		pack();	
		tf.addActionListener(new  TfListener());
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		connect();
		
	}
	
	private class  TfListener  implements ActionListener
	{
		public void actionPerformed(ActionEvent e)	
		{
			String str = tf.getText().trim();
			ta.setText(str);
			tf.setText("");
			DataOutputStream dos=null;;
			try
			{
				dos = new DataOutputStream(s.getOutputStream());
				dos.writeUTF(str);
				dos.flush();
				//dos.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
System.out.println("write failure");	
			}
		}
	}
	
	public void connect()
	{
		try 
		{
			 s =  new Socket("127.0.0.1",5555);
			 System.out.println("client connected");		
		}  catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
