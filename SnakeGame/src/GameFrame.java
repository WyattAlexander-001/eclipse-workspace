import javax.swing.*;

public class GameFrame extends JFrame{
	GameFrame(){
		/* 
		//More explicit way to add the panel to the frame
		GamePanel panel = new GamePanel();
		this.add(panel);
		*/
		
		//Shorthand to add panel to frames
		this.add(new GamePanel());
		this.setTitle("Snake Game Demo");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack(); // used instead of setSize or setBounds
		this.setLocationRelativeTo(null); //Opens app to center of screen
		this.setVisible(true);
		
	}

}
