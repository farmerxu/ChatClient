import java.io.*;
import java.net.*;

public class ChatServer 
{
	ServerSocket ss=null;
	boolean start= 
			false;
	public static void main(String[] args) 
	{
		new ChatServer().start();
	}
	
	public void start()
	{
		try {
			 ss = new ServerSocket(5555);
			start=true;
			while(start) 
			{
				Socket s = ss.accept();
				Client c = new Client(s);
System.out.println("a client connected!");
				new Thread(c).start();
			}
			
		}catch (IOException e) 
		{
			e.printStackTrace();	
		}finally
		{
			try 
			{
				ss.close();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	class Client implements Runnable
	{
		private boolean flag = false;
		private Socket s;
		private DataInputStream dis=null;
		
		Client(Socket s)
		{
			this.s=s;
			try 
			{
				dis=new DataInputStream(s.getInputStream());
				flag =true;
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run()
		{
			//DataInputStream dis = new DataInputStream(s.getInputStream());
			try
			{
				while(flag)
				{
					String str;
					str = dis.readUTF();
					
	System.out.println(str);
				}	
			}catch (IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(dis!=null)
					{
						dis.close();
					}
					if(s!=null)
					{
						s.close();
					}
				}catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
		
	}
}