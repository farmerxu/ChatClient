import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer 
{
	ServerSocket ss=null;
	boolean start= false;
	List<Client> clients = new  ArrayList<Client>();
	
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
				clients.add(c);
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
		private DataOutputStream dos=null;
		
		Client(Socket s)
		{
			this.s=s;
			try 
			{
				dis=new DataInputStream(s.getInputStream());
				dos=new DataOutputStream(s.getOutputStream());
				flag =true;
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void send(String str)
		{
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
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
					for(int i=0;i<clients.size();i++)
					{
						Client c = clients.get(i);
						c.send(str);
						System.out.println(str);
					}
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
						dis=null;
					}
					if(dos!=null)
					{
						dos.close();
						dos=null;
					}
					if(s!=null)
					{
						s.close();
						s=null;
					}
				}catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
		
	}
}