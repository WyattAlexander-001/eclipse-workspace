import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Calculator implements ActionListener
{
	
	private JFrame frame;
	private JTextField textField;
	private JButton[] numberButtons = new JButton[10];
	private JButton[] functionButtons = new JButton[8];
	private JButton addButton,subButton,mulButton,divButton;
	private JButton decButton, equButton, delButton, clrButton;
	private JPanel panel;
	private Font myFont = new Font("Ink Free", Font.BOLD, 30);
	private double num1=0, num2=0, result=0;
	private char operator;
	//Constructor
	
	Calculator()
	{
		
	}

	public static void main(String[] args) 
	{
		Calculator calc = new Calculator();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
