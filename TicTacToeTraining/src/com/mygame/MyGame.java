package com.mygame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//3. Created one more class to create GUI
//7. extend JFrame to import its properties
//30. implements Action listener to act as a listener
public class MyGame extends JFrame implements ActionListener{
	
	//15. Declaring variables for heading and font
	//17. Declaring variables for clock
	JLabel heading, clockLabel;
	Font font= new Font("", Font.BOLD, 40);
	
	//22. Declaring variables for Panel of 9 buttons
	JPanel mainPanel;
	
	//23. Creating an array to store 9 buttons
	JButton []btns= new JButton[9];
	
	//35. game instance variable
	int gameChances[]= {2,2,2,2,2,2,2,2,2};
	int activePlayer=0;
	
	//37. 2D array including all winning positions
	int wps[][]= {
			{0, 1, 2},
			{3, 4, 5},
			{6, 7, 8},
			{0, 3, 6},
			{1, 4, 7},
			{2, 5, 8},
			{0, 4, 8},
			{2, 4, 6}
	};
	
	//39. By default winner 2 means noone
	int winner=2;
	
	//41. Variable to check draw
	boolean gameOver=false;
	
	//5. created constructor of MyGame class
	MyGame(){
		
		//printing message to check if our constructor is working after calling the object 
		System.out.println("Creating instance of game");
		
		//8. set title and size
		setTitle("My Tic Tac Toe Game...");
		setSize(850, 850);
		
		//9. Import icon.png and setIconImage
		ImageIcon icon= new ImageIcon("src/img/icon.jpg");
		setIconImage(icon.getImage());
		
		//10. To close the frame after use
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//13. Called function createGUI
		createGUI();
		
		//11. To set visible
		setVisible(true);
	}
	
	//12. Created new function to create border
	private void createGUI() {
		
		//25. To set background of frame
		this.getContentPane().setBackground(Color.decode("#2196f3"));
		this.setLayout(new BorderLayout());
		
		//14. To set layout of frame
		this.setLayout(new BorderLayout());
		
		//16. north heading..
		heading= new JLabel("Tic tac Toe");
		
		//28. Copying the logo in the heading
		//heading.setIcon(new ImageIcon("src/img/icon.jpg"));
		
		heading.setFont(font);
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		
		//26. To set text color to white
		heading.setForeground(Color.white);
		
		//29. To align logo and text correctly in heading
		heading.setHorizontalTextPosition(SwingConstants.CENTER);
		heading.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		this.add(heading, BorderLayout.NORTH);
		
		//18. Creating object of JLabel for clock
		clockLabel= new JLabel("Clock");
		
		//27. To set clock color white 
		clockLabel.setForeground(Color.white);
		
		clockLabel.setFont(font);
		clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(clockLabel, BorderLayout.SOUTH);
		
		//19. Thread object to change clock time dynamically after every sec
		Thread t= new Thread() {
			public void run() {
				try {
					while(true) {
						
						//20. Pick current date and time using date class
						String datetime= new Date().toLocaleString();
						clockLabel.setText(datetime);
						
						Thread.sleep(1000);;
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		};
		
		//21. To start the Thread
		t.start();
		
		//24. Panel section
		mainPanel= new JPanel();
		mainPanel.setLayout(new GridLayout(3, 3));
		
		for(int i=1; i<=9; i++) {
			JButton btn= new JButton();
			//btn.setIcon(new ImageIcon("src/img/0.png"));
			btn.setBackground(Color.decode("#90caf9"));
			btn.setFont(font);
			mainPanel.add(btn);
			btns[i-1]= btn;
			
			//31. To add Action listener
			btn.addActionListener(this);
			btn.setName(String.valueOf(i-1));
		}
		
		this.add(mainPanel, BorderLayout.CENTER);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//32. To check if the function runs after clicking button
		//System.out.println("clicked");
		
		//33. To check which button is clicked
		JButton currentButton= (JButton)e.getSource();
		
		//34. To get name of current button
		String nameStr= currentButton.getName();
		System.out.println(nameStr);
		
		//36. To convert name to int
		int name= Integer.parseInt(nameStr.trim());
		
		//42. To check if game is over and return from this step only.
		if(gameOver==true) {
			JOptionPane.showMessageDialog(this, "Game already over.");
			return;
		}
		
		//37. To get which chance is played
		if(gameChances[name]==2) {
			if(activePlayer==1) {
				currentButton.setIcon(new ImageIcon("src/img/1.png"));
				gameChances[name]= activePlayer;
				activePlayer=0;
			}
			else {
				currentButton.setIcon(new ImageIcon("src/img/0.png"));
				gameChances[name]= activePlayer;
				activePlayer=1;
			}
			
			//38. To find the winner
			for(int []temp:wps)
			{
				if((gameChances[temp[0]] == gameChances[temp[1]]) && (gameChances[temp[1]] == gameChances[temp[2]]) && gameChances[temp[2]] != 2)
				{
					winner=gameChances[temp[0]];
					
					//43. We got a winner means the game is over
					gameOver= true;
					
					JOptionPane.showMessageDialog(null, "Player "+ winner+ " has won the game.");
					
					//40. To restart or exit the game
					int i= JOptionPane.showConfirmDialog(this, "do you want to play more??");
					if(i==0) {
						this.setVisible(false);
						new MyGame();
					}else if(i==1) {
						System.exit(34234);
					}else {
						
					}
					System.out.println(i);
					break;
				}
			}
			
			//44. Draw logic
			int c=0;
			for(int x:gameChances) {
				if(x == 2) {
					c++;
					break;
				}
			}
			
			if(c==0 && gameOver==false) {
				JOptionPane.showMessageDialog(null, "Its draw.");
				
				int i= JOptionPane.showConfirmDialog(this, "Play more?");
				if(i==0) {
					this.setVisible(false);
					
					new MyGame();
				}
				else if(i==1) {
					System.exit(1212);
				}
				else {
					
				}
				
				gameOver=true;
			}
			
		}
		else {
			JOptionPane.showMessageDialog(this, "Position Already Occupied..");
		}
	}

}

//6. Create img folder in src and download 0, 1, icon.png