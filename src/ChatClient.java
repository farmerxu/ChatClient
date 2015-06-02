import java.awt.*;
import java.awt.event.*;

public class ChatClient extends Frame
{
	private TextField tf = new TextField();
	private TextArea  ta= new TextArea();
	
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
	}
	
	private class  TfListener  implements ActionListener
	{
		public void actionPerformed(ActionEvent e)	
		{
			String s = tf.getText().trim();
			ta.setText(s);
			tf.setText("");
		}
	}
}
