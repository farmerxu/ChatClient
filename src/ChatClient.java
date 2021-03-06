import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame
{
	TextField tf = new TextField(); 
	TextArea  ta= new TextArea();
	Socket s = null;
	private boolean flag = false ;
	DataOutputStream dos=null;
	DataInputStream dis=null;
	Thread tRecv = new Thread(new RecvThread());
	
	
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
				disconnect();
				System.exit(0);
				
			}
		});
		connect();
		tRecv.start();
		
	}
	
	private class  TfListener  implements ActionListener
	{
		public void actionPerformed(ActionEvent e)	
		{
			String str = tf.getText().trim();
			tf.setText("");
			try
			{
				dos.writeUTF(str);
				dos.flush();
			}	 
			catch (IOException e1)
			{
				e1.printStackTrace();
System.out.println("write failure");	
			}
		}
	}
	
	public void disconnect() {
		try {
			flag =false;//必须保证线程停掉
		
			dos.close();
			dis.close();//应该先关掉这个，不然会有阻塞错误，关掉时还在执行线程，应先把flag=false;
			
			s.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		/*
		finally
		{
			try {
				dos.close();
				dis.close();
				s.close();
				} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		*/
		
	}
	
	public void connect()
	{
		try 
		{
			 s =  new Socket("127.0.0.1",5555);
			 dos = new DataOutputStream(s.getOutputStream());
			 dis = new DataInputStream(s.getInputStream());
			 flag = true;
			 System.out.println("client connected");		
		}  
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
			catch (IOException e) 
		{
			
			e.printStackTrace();
		}
	}
	
	private class RecvThread  implements Runnable
	{
		
		@Override
		public void run() 
		{
			try{
				while (flag)
				{
						String str = dis.readUTF();
						ta.setText(ta.getText()+ str + '\n');
				}
			}
			//这是一种方法，catch到socketexception将他停止
			catch(SocketException e)
			{
				System.out.println("退出了");
			}
			catch (EOFException e) {
				System.out.println("bye - bye!");
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}

/*import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame {
	Socket s = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	private boolean bConnected = false;

	TextField tfTxt = new TextField();

	TextArea taContent = new TextArea();
	
	Thread tRecv = new Thread(new RecvThread()); 

	public static void main(String[] args) {
		new ChatClient().launchFrame(); 
	}

	public void launchFrame() {
		setLocation(400, 300);
		this.setSize(300, 300);
		add(tfTxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				disconnect();
				System.exit(0);
			}
			
		});
		tfTxt.addActionListener(new TFListener());
		setVisible(true);
		connect();
		
		tRecv.start();
	}
	
	public void connect() {
		try {
			s = new Socket("127.0.0.1", 5555);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
System.out.println("connected!");
			bConnected = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void disconnect() {
		try {
			dos.close();
			dis.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		try {
			bConnected = false;
			tRecv.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				dos.close();
				dis.close();
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private class TFListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String str = tfTxt.getText().trim();
			//taContent.setText(str);
			tfTxt.setText("");
			
			try {
//System.out.println(s);
				dos.writeUTF(str);
				dos.flush();
				//dos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
	
	private class RecvThread implements Runnable {

		public void run() {
			try {
				while(bConnected) {
					String str = dis.readUTF();
					//System.out.println(str);
					taContent.setText(taContent.getText() + str + '\n');
				}
			} catch (SocketException e) {
				System.out.println("Íbye¬bye!");
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		}
		
	}
}
	
*/