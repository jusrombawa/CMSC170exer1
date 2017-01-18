/**
 *ROMBAWA, JUSTIN AARON S.
 *CMSC 170 U-3L
 *EXERCISE 1: LIGHS OUT
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class LightsOutGame extends JFrame implements ActionListener
{
	JPanel lightPanel;
	JButton[][] lights;
	boolean[][] switches;
	


	public LightsOutGame()
	{
		//set frame properties
		this.setTitle("Lights Out");
		this.setSize(500,500);
		
		//initialize panel and layout
		lightPanel = new JPanel();
		lightPanel.setLayout(new GridLayout(5,5));
		
		//construct lights buttons
		switchInit("lightsout.in");
		
		//init button array
		lights = new JButton[5][5];
	
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<5;j++)
			{
				//instantiate button then add action listener
				lights[i][j] = new JButton();
				lights[i][j].addActionListener(this);
				
				//color for on
				if(switches[i][j] == true)
					lights[i][j].setBackground(Color.WHITE);
				//color for off
				else if (switches[i][j] == false)
					lights[i][j].setBackground(Color.BLACK);
				//add to panel
				lightPanel.add(lights[i][j]);
			}
		}
		
		//add panel to frame then additional properties
		this.add(lightPanel);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
	}
	
	//file reading
	private void switchInit(String fileName)
	{
		switches = new boolean[5][5];
	
		BufferedReader buffread = null;
		FileReader fileread = null;		
		String curr;
		char[] line;
		int y = 0;
		
		try
		{
			fileread = new FileReader(fileName);
			buffread = new BufferedReader(fileread);
			
			//read line, convert to character array then base boolean line on char line
			while((curr =  buffread.readLine()) != null)
			{
				line = curr.toCharArray();
				for(int x=0;x<line.length;x++)
				{
					if(line[x] == '0')
						switches[x][y] = false;
					else if(line[x] == '1')
						switches[x][y] = true;
					else if(line[x] == '\n');
				}
				y++;
			}
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}

		finally
		{
			try
			{
				if(buffread != null)
					buffread.close();
				if(fileread != null)
					fileread.close();
			}
			
			catch(IOException ie)
			{
				ie.printStackTrace();
			}
		}
		
		//print out initial switches array on console
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<5;j++)
			{
				if(switches[i][j]) System.out.print("1");
				else System.out.print("0");
			}
			System.out.println();
		}
	}
	
	//update lights after changes
	private void lightUpdate()
	{
		//change colors
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<5;j++)
			{
			if(switches[i][j] == true)
						lights[i][j].setBackground(Color.WHITE);

					else if (switches[i][j] == false)
						lights[i][j].setBackground(Color.BLACK);
			}
		}
	}
	
	//button reversals on button click
	private void buttonClicked(int i, int j)
	{
		switches[i][j] = !switches[i][j];//reverse clicked
		
		//switches[i+1][j] = !switches[i+1][j]; //reverse bottom
		//switches[i-1][j] = !switches[i-1][j]; //reverse top
		//switches[i][j+1] = !switches[i][j+1];//reverse right
		//switches[i][j-1] = !switches[i][j-1];//reverse left		
		
		//reverse top and bottom
		//case1: topmost
		if(i==0)
		{
			switches[i+1][j] = !switches[i+1][j]; //reverse bottom		
		}
		//case2: bottom...most?
		else if(i==4)
		{
			switches[i-1][j] = !switches[i-1][j]; //reverse top	
		}
		//case3:mid
		else if(i>0 && i<4)
		{
			switches[i+1][j] = !switches[i+1][j]; //reverse bottom
			switches[i-1][j] = !switches[i-1][j]; //reverse top
		}
		
		//reverse left and right
		//case1: leftmost
		if(j==0)
		{
			switches[i][j+1] = !switches[i][j+1];//reverse right
		}
		//case2: rightmost
		else if(j==4)
		{
			switches[i][j-1] = !switches[i][j-1];//reverse left					
		}
		//case 3:mid
		else if(j>0 && j<4)
		{
		switches[i][j+1] = !switches[i][j+1];//reverse right
		switches[i][j-1] = !switches[i][j-1];//reverse left
		}
	}
	//check if win
	private boolean winCheck()
	{
		boolean win = false;
	
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<5;j++)
			{
				if(switches[i][j] == false)
				{
					win = true;
				}
				else
				{
					//still not winning if at least one on switch
					win = false;
					return win;
				}
			}
		}
		
		return win;
	}
		
	public void actionPerformed(ActionEvent e)
	{
		boolean win;
		
		//search for matching lights button with source of ActionEvent e
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<5;j++)
			{
				if(e.getSource() == lights[i][j])
				{
					buttonClicked(i,j); //switch reversals
					lightUpdate(); //button color update
					win = winCheck(); //check win status
					if(win) //display win message if win
					{
						System.out.println("You win!");
						JOptionPane.showMessageDialog(this, "You win!");
					}
				}
			}
		}
	}
}
