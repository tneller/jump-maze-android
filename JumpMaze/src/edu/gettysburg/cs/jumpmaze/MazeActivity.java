package edu.gettysburg.cs.jumpmaze;

import java.io.IOException;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MazeActivity extends Activity {
	/**
	 * 2D array that holds the association of buttons for the maze.
	 */
	private Button[][] buttons;

	/**
	 * Allows for the access of new random mazes for the GUI
	 */
	private Maze maze;

	/**
	 * 2D array that holds the numbers for the maze.
	 */
	Node[][] currentMaze;
	/**
	 * Two variables that keep track of the row and column of the button clicked
	 */
	private int lastClickedRow;
	private int lastClickedCol;
	/**
	 * A variable that keeps track of the players progress and moves in current game
	 */
	Move game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maze);

		// Initializes Maze so that new mazes can be generated.
		maze = ((Maze) getApplication());

		// Initializes buttons with 5 rows and 5 columns.
		

		//sets up the buttons and their listeners
		setupButtonsArray();
		

		// Calls the method that will create new mazes
		makeNewMaze();

		game = new Move(currentMaze, currentMaze[0][0] );
		
		

	}
	


	/**
	 * Generates the maze and sets the text on the buttons to be of values generated.
	 * 		Currently not using the Maze class because of issues with finding mazes.txt
	 */
	@SuppressWarnings("deprecation")
	private void makeNewMaze()
	{
		try {
			//Sets a maze to the new maze being generated.
			currentMaze = maze.getMaze();

			// Current node value that is being set on the button.
			Node currNode;

			// Current button who's value is being set.
			Button currButton;

			// Iterates though each button and sets the text and color on it.
			for(int i = 0; i < buttons.length; i++)
			{
				for(int j = 0; j < buttons.length; j++)
				{
					currNode = currentMaze[i][j];
					currButton = buttons[i][j];

					if ((i+j) % 2 == 0) 
						currButton.setBackgroundColor(Color.BLACK);

					else
						currButton.setBackgroundColor(Color.GRAY);

					if(currNode.key == 1)
					{
						currButton.setText("1");
						currButton.setTextColor(Color.YELLOW);
						currButton.setTextSize(getResources().getDimension(R.dimen.textsize));
					}
					else if(currNode.key == 2)
					{
						currButton.setText("2");
						currButton.setTextColor(Color.rgb(255, 150, 0));
						currButton.setTextSize(getResources().getDimension(R.dimen.textsize));
					}
					else if(currNode.key == 3)
					{
						currButton.setText("3");
						currButton.setTextColor(Color.rgb(30, 100, 255));
						currButton.setTextSize(getResources().getDimension(R.dimen.textsize));
					}
					else if(currNode.key == 4)
					{
						currButton.setText("4");
						currButton.setTextColor(Color.GREEN);
						currButton.setTextSize(getResources().getDimension(R.dimen.textsize));
					}
					else
					{
						if ((i+j) % 2 == 0) {
							currButton.setText("");
							Drawable d = getResources().getDrawable(R.drawable.g_black);
							currButton.setBackgroundDrawable(d);
						}
						else {
							currButton.setText("");
							Drawable d = getResources().getDrawable(R.drawable.g_gray);
							currButton.setBackgroundDrawable(d);
						}
					}
				}
			}


			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maze, menu);
		return true;
	}
	
	public void setupButtonsArray()
	{
		buttons = new Button[5][5];
		/*
		 * Creates a button for each associated spot in the GUI (see activity_maze.xml). 
		 *   Looks up each button id and creates a button for it in the buttons array.
		 */
		buttons[0][0] = (Button) findViewById(R.id.Button1);
		buttons[0][1] = (Button) findViewById(R.id.Button2);
		buttons[0][2] = (Button) findViewById(R.id.Button3);
		buttons[0][3] = (Button) findViewById(R.id.Button4);
		buttons[0][4] = (Button) findViewById(R.id.Button5);

		buttons[1][0] = (Button) findViewById(R.id.Button6);
		buttons[1][1] = (Button) findViewById(R.id.Button7);
		buttons[1][2] = (Button) findViewById(R.id.Button8);
		buttons[1][3] = (Button) findViewById(R.id.Button9);
		buttons[1][4] = (Button) findViewById(R.id.Button10);

		buttons[2][0] = (Button) findViewById(R.id.Button11);
		buttons[2][1] = (Button) findViewById(R.id.Button12);
		buttons[2][2] = (Button) findViewById(R.id.Button13);
		buttons[2][3] = (Button) findViewById(R.id.Button14);
		buttons[2][4] = (Button) findViewById(R.id.Button15);

		buttons[3][0] = (Button) findViewById(R.id.Button16);
		buttons[3][1] = (Button) findViewById(R.id.Button17);
		buttons[3][2] = (Button) findViewById(R.id.Button18);
		buttons[3][3] = (Button) findViewById(R.id.Button19);
		buttons[3][4] = (Button) findViewById(R.id.Button20);

		buttons[4][0] = (Button) findViewById(R.id.Button21);
		buttons[4][1] = (Button) findViewById(R.id.Button22);
		buttons[4][2] = (Button) findViewById(R.id.Button23);
		buttons[4][3] = (Button) findViewById(R.id.Button24);
		buttons[4][4] = (Button) findViewById(R.id.Button25);
		
		buttons[0][0].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=0;
				lastClickedCol=0;
			}
		});
		buttons[0][1].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=0;
				lastClickedCol=1;
			}
		});
		buttons[0][2].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=0;
				lastClickedCol=2;
			}
		});
		buttons[0][3].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=0;
				lastClickedCol=3;
			}
		});
		buttons[0][4].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=0;
				lastClickedCol=4;
			}
		});

		buttons[1][0].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=1;
				lastClickedCol=0;
			}
		});
		buttons[1][1].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=1;
				lastClickedCol=1;
			}
		});
		buttons[1][2].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=1;
				lastClickedCol=2;
			}
		});
		buttons[1][3].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=1;
				lastClickedCol=3;
			}
		});
		buttons[1][4].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=1;
				lastClickedCol=4;
			}
		});

		buttons[2][0].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=2;
				lastClickedCol=0;
			}
		});
		buttons[2][1].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=2;
				lastClickedCol=1;
			}
		});
		buttons[2][2].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=2;
				lastClickedCol=2;
			}
		});
		buttons[2][3].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=2;
				lastClickedCol=3;
			}
		});
		buttons[2][4].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=2;
				lastClickedCol=4;
			}
		});

		buttons[3][0].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=3;
				lastClickedCol=0;
			}
		});
		buttons[3][1].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=3;
				lastClickedCol=1;
			}
		});
		buttons[3][2].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=3;
				lastClickedCol=2;
			}
		});
		buttons[3][3].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=3;
				lastClickedCol=3;
			}
		});
		buttons[3][4].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=3;
				lastClickedCol=4;
			}
		});

		buttons[4][0].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=4;
				lastClickedCol=0;
			}
		});
		buttons[4][1].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=4;
				lastClickedCol=1;
			}
		});
		buttons[4][2].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=4;
				lastClickedCol=2;
			}
		});
		buttons[4][3].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=4;
				lastClickedCol=3;
			}
		});
		buttons[4][4].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lastClickedRow=4;
				lastClickedCol=4;
			}
		});
	}

}
