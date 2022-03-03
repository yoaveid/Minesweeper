import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



public class MinesweeperFrame extends JFrame implements MouseListener{
	
	static final int NUM_OF_BOMBS= 99;
	static final int BOARD_ROW= 30;
	static final int BOARD_COL= 16;
	
	boolean running = true;
	boolean firstTurn = true;

	JButton buttons [][] = new JButton[BOARD_ROW][BOARD_COL];
	JButton newGame = new JButton("new Game");
	int board [][] = new int[BOARD_ROW][BOARD_COL];
	int bombsLeft = NUM_OF_BOMBS;
	ImageIcon flag,bomb;
	JPanel titlePanel; 
	JPanel buttonsPanel;
	JLabel title;
	
	
	public MinesweeperFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(925,800));
		this.getContentPane().setBackground(new Color(75,75,75));
		this.setLayout(new BorderLayout());
		
		titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		titlePanel.setBounds(0, 0, 950, 75);
		
		title = new JLabel("BombsLeft: " + NUM_OF_BOMBS);
		titlePanel.setBackground(new Color(20,20,20));
		title.setForeground(new Color(25,250,0));
		title.setFont(new Font("INk",Font.BOLD,60));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		newGame.setFont(new Font("INk",Font.TRUETYPE_FONT,30));
		
		newGame.setBackground(new Color(17, 123, 158));
		newGame.setFocusable(false);
		newGame.addMouseListener(this);
		
		titlePanel.add(title);
		titlePanel.add(newGame);
		
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(buttons.length,buttons[0].length));
		buttonsPanel.setBounds(0, 100, 950, 725);
		buttonsPanel.setBackground(new Color(20,20,20));

		
		
		flag = new ImageIcon("flag.jpg");
		bomb = new ImageIcon("bomb.jpg");
		for(int i = 0; i< buttons.length; i++)
			for(int j =0; j<  buttons[0].length; j++)
			{
				buttons[i][j] = new JButton();
				buttons[i][j].setBackground(Color.lightGray);
				buttons[i][j].setFont(new Font("INK pink",Font.BOLD,20));
				buttons[i][j].setFocusable(false);
				buttons[i][j].addMouseListener(this);

				buttonsPanel.add(buttons[i][j]);
			}

		this.add(titlePanel,BorderLayout.NORTH);
		this.add(buttonsPanel,BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
		}

	private void printBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				System.out.print(board[i][j]+" ");
				
			}
			System.out.println();
		}
	}

	private void initializeBoard(int saveI, int saveJ) {
		Random rand = new Random();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j]=0;
			}
		}
		for(int k = 0; k < NUM_OF_BOMBS;k++)
		{
			int i = rand.nextInt(board.length) ;
			int j = rand.nextInt(board[0].length) ;
			while (board[i][j] == -1 || (Math.abs(i-saveI) <= 1 && Math.abs(j-saveJ) <= 1))
			{
				i = rand.nextInt(board.length) ;
				j = rand.nextInt(board[0].length) ;
			}
			board[i][j] = -1;
			
		}
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = nearBombs(i,j);
				buttons[i][j].setForeground(ChooseColor(board[i][j]));
			}
		}
	}

	private Color ChooseColor(int i) {
		switch (i) {
		case 1: 
			return Color.blue;
		case 2:
			return new Color(0,204,0);
		case 3:
			return Color.red;
			
		
		}
		return null;
	}

	private int nearBombs(int i, int j) {
		if(board[i][j] == -1)
			return -1;
		int count =0;
		if(i != 0 && board[i-1][j] == -1)
			count++;
		if(i != board.length-1 && board[i+1][j] == -1)
			count++;
		if(j != 0 && board[i][j-1] == -1)
			count++;
		if(j != board[0].length-1 && board[i][j+1] == -1)
			count++;
		if(j != 0 && i!=0 && board[i-1][j-1] == -1)
			count++;
		if(j != 0 && i != board.length-1 && board[i+1][j-1] == -1)
			count++;
		if(i != 0 && j != board[0].length-1 && board[i-1][j+1] == -1)
			count++;
		if(i != board.length-1 && j != board[0].length-1 && board[i+1][j+1] == -1)
			count++;
		return count;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		
	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == newGame)
		{	
			running =true;
			firstTurn = true;
			for(int i = 0; i< buttons.length; i++)
				for(int j =0; j<  buttons[0].length; j++)
				{
					buttons[i][j].setIcon(null);
					buttons[i][j].setText("");
					buttons[i][j].setBackground(Color.lightGray);
				}
			bombsLeft = NUM_OF_BOMBS;
			title.setText("BombsLeft: " + NUM_OF_BOMBS);
		}
		else if(SwingUtilities.isLeftMouseButton(e) && running)
		{
			
			for (int i = 0; i < buttons.length; i++) {
				for (int j = 0; j < buttons[0].length; j++) {
					if (e.getSource() == buttons[i][j]) {
						if(buttons[i][j].getIcon() == null)
							if (firstTurn)
							{
								board[i][j] = 0;
								initializeBoard(i,j);
								firstTurn = false;
							}
							if(board[i][j] != 0 && board[i][j] != -1 && board[i][j] != -2  && buttons[i][j].getIcon() == null)
								buttons[i][j].setText(String.valueOf(board[i][j]));
							else if(board[i][j] == 0)
								showAreaZero(i,j);
							else if(board[i][j] == -1 && buttons[i][j].getIcon() == null)
							{
								buttons[i][j].setBackground(new Color(224, 41, 20));
								running= false;
								showBombs();
							}
						if(buttons[i][j].getText() != "")
							checkIfShowNear(i,j);
					}
				}
			}
		
		}
		else if(SwingUtilities.isRightMouseButton(e) && running)
		{
			for (int i = 0; i < buttons.length; i++) {
				for (int j = 0; j < buttons[0].length; j++) {
					if (e.getSource() == buttons[i][j] && board[i][j ]!= -2) {
						if(buttons[i][j].getIcon() == flag)
						{
							buttons[i][j].setIcon(null);
							bombsLeft++;

						}
						else if(buttons[i][j].getText() == "")
						{
							buttons[i][j].setIcon(flag);
							bombsLeft--;
						}
						title.setText("BombsLeft: " + bombsLeft);
							
					}
				}
			}
		}
		
	}

	

	private void checkIfShowNear(int i, int j) {
		int count = 0;
		if(i != 0 && buttons[i-1][j].getIcon() != null)
			if(board[i-1][j] == -1)
				count++;
			else
				showBombs();
		if(i != board.length-1 && buttons[i+1][j].getIcon() != null)
			if(board[i+1][j] == -1)
				count++;
			else
				showBombs();
		if(j != 0 && buttons[i][j-1].getIcon() != null)
			if(board[i][j-1] == -1)
				count++;
			else
				showBombs();
		if(j != board[0].length-1 && buttons[i][j+1].getIcon() != null)
			if(board[i][j+1] == -1)
				count++;
			else
				showBombs();
		if(j != 0 && i!=0 && buttons[i-1][j-1].getIcon() != null)
			if(board[i-1][j-1] == -1)
				count++;
			else
				showBombs();
		if(j != 0 && i != board.length-1 && buttons[i+1][j-1].getIcon() != null)
			if(board[i+1][j-1] == -1)
				count++;
			else
				showBombs();
		if(i != 0 && j != board[0].length-1 && buttons[i-1][j+1].getIcon() != null)
			if(board[i-1][j+1] == -1)
				count++;
			else
				showBombs();
		if(i != board.length-1 && j != board[0].length-1 && buttons[i+1][j+1].getIcon() != null)
			if(board[i+1][j+1] == -1)
				count++;
			else
				showBombs();
		if(count == board[i][j])
		{
			showNearNumber(i-1,j);
			showNearNumber(i+1,j);
			showNearNumber(i,j-1);
			showNearNumber(i,j+1);
			showNearNumber(i-1,j-1);
			showNearNumber(i-1,j+1);
			showNearNumber(i+1,j-1);
			showNearNumber(i+1,j+1);

		}
	}
	private void showNearNumber(int i, int j)
	{
		if(i < 0 || i >= board.length || j >= board[0].length || j < 0)
			return;
		else if(board[i][j] == -1 || board[i][j] == -2)
			return;
		else if(board[i][j] == 0)
			showAreaZero(i, j);
		else if(buttons[i][j].getText() == "")
			buttons[i][j].setText(String.valueOf(board[i][j]));

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	private void showAreaZero(int i, int j) {
		if(i<0 || i >= board.length || j <0 || j >= board[0].length)
			return;
		if(board[i][j] == -2)
			return;
		if(board[i][j] != 0	&& buttons[i][j].getIcon() == null)
		{
			buttons[i][j].setText(String.valueOf(board[i][j]));
			return;
		}
		buttons[i][j].setBackground(new Color(137, 143, 134));
		board[i][j] =-2;
		showAreaZero(i-1,j);
		showAreaZero(i+1,j);
		showAreaZero(i,j-1);
		showAreaZero(i,j+1);
		showAreaZero(i-1,j-1);
		showAreaZero(i-1,j+1);
		showAreaZero(i+1,j-1);
		showAreaZero(i+1,j+1);
	}
	private void showBombs() {
		title.setText("You lose ):");
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if(board[i][j] == -1)
				{
					buttons[i][j].setIcon(bomb);
				}
				else if(buttons[i][j].getIcon()!= null)
				{
					buttons[i][j].setIcon(null);
					buttons[i][j].setBackground(new Color(24, 28, 25));

				}

			}
		}
	}

}
