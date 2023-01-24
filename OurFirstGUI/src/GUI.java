//Grahpical User Interface "Goo-e" not G,U,I
//Outdated == swing
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class GUI implements ActionListener
{
	private int count = 0;
	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private JButton button;

	

	public static void main(String[] args) 
	{
		new GUI();
	}

	
	//Constructor
	public GUI() 
	{
		frame = new JFrame();
		button = new JButton("CLICK ME!");
		button.addActionListener(this);
		
		//Place holder text
		label = new JLabel("Number Of Clicks: 0");
		
		//Create all the features of the panel
		panel = new JPanel();
		
		panel.setBorder(BorderFactory.createEmptyBorder(30,30, 100, 300));
		panel.setLayout(new GridLayout(0,1));
		panel.add(button);
		panel.add(label);
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Title:
		frame.setTitle("Our GUI");
		frame.pack();
		frame.setVisible(true);		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		count++;
		label.setText("Number Of Clicks: " + count);
		
	}

}
