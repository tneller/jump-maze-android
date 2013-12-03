package edu.gettysburg.cs.jumpmaze;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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
	private Node[][] currentMaze;
	
	/**
	 * A variable that keeps track of the players progress and moves in current game
	 */
	private Move game;
	
	/**
	 * keeps track of the current node the player is on
	 */
	private Node playerNode;
	
	/**
	 * A button that resets the current maze
	 */
	private ImageButton resetButton;
	
	/**
	 * A button that moves on to the next maze
	 */
	private ImageButton nextMazeButton;
	
	/**
	 * TextView that keeps track of the number of moves
	 */
	TextView moveView;
	
	/**
	 * variable that keeps track of number of moves
	 */
	int moves;

	
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
		
		resetButton = (ImageButton) findViewById(R.id.reset_maze);
		nextMazeButton = (ImageButton) findViewById(R.id.next_maze_button);
		
		resetButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				game.reset();
            	playerNode = game.player;
            	drawMaze();
            	moves = game.count;
        		moveView.setText("Moves: " + moves);
			}
		});
		
		nextMazeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				makeNewMaze();
                game = new Move(currentMaze, currentMaze[0][0]);
                moves = game.count;
        		moveView.setText("Moves: " + moves);
			}
		});
		
		moveView = (TextView) findViewById(R.id.moves);
	}
	
	private void makeMove(int row, int col)
	{
		boolean moved = game.movePlayer(row, col);
		
		if(moved)
		{
			Toast.makeText(MazeActivity.this, "Valid Move", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(MazeActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
		}
		
		playerNode = game.player;
		
		moves = game.count;
		
		moveView.setText("Moves: " + moves);
		
		drawMaze();
		
		if(game.isFinished())
		{
			AlertDialog.Builder won = new AlertDialog.Builder(MazeActivity.this);
			int shortest = BreadthFirstSearch.getShortestPath(currentMaze);
			if(moves != shortest)
			{
				won.setMessage("You solved the puzzle in " + moves + " moves. The shortest route was " + shortest +".");
			}
			else
			{
				won.setMessage("You solved the puzzle in " + moves + " moves. That is the shortest route! Good job!");
			}
			won.setPositiveButton("Next Maze", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    makeNewMaze();
                    game = new Move(currentMaze, currentMaze[0][0]);
                    moves = game.count;
            		moveView.setText("Moves: " + moves);
                }
            });
			won.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	game.reset();
                	playerNode = game.player;
                	drawMaze();
                	moves = game.count;
            		moveView.setText("Moves: " + moves);
                }
            });
			won.create();
			won.show();
		}
		
		
	}



	/**
	 * Generates the maze and sets the text on the buttons to be of values generated.
	 *                 Currently not using the Maze class because of issues with finding mazes.txt
	 */
	private void makeNewMaze()
	{
		try {
			//Sets a maze to the new maze being generated.
			currentMaze = maze.getMaze();
			playerNode = currentMaze[0][0];

			drawMaze();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private void drawMaze()
	{
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
				
				if(currNode == playerNode)
				{
					if ((i+j) % 2 == 0) 
					{
						currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_black));
					}
					else
					{
						currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_gray));
					}
				}
				else if(currNode.visited == true)
				{
					if ((i+j) % 2 == 0) 
					{
						currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.hint_black));
					}
					else
					{
						currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.hint_gray));
					}
				}
				else
				{
					if ((i+j) % 2 == 0) 
					{
						currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.black));
					}
					else
					{
						currButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray));
					}
				}

				if(currNode.key == 1)
				{
					currButton.setText("1");
					currButton.setTextColor(Color.YELLOW);
					currButton.setTextSize(getResources().getDimension(R.dimen.text_size));
				}
				else if(currNode.key == 2)
				{
					currButton.setText("2");
					currButton.setTextColor(Color.rgb(255, 150, 0));
					currButton.setTextSize(getResources().getDimension(R.dimen.text_size));
				}
				else if(currNode.key == 3)
				{
					currButton.setText("3");
					currButton.setTextColor(Color.rgb(30, 100, 255));
					currButton.setTextSize(getResources().getDimension(R.dimen.text_size));
				}
				else if(currNode.key == 4)
				{
					currButton.setText("4");
					currButton.setTextColor(Color.GREEN);
					currButton.setTextSize(getResources().getDimension(R.dimen.text_size));
				}
				else
				{
					if ((i+j) % 2 == 0) {
						currButton.setText("");
						Drawable d = getResources().getDrawable(R.drawable.g_black);
						currButton.setBackgroundDrawable(d);
						currButton.setTextSize(getResources().getDimension(R.dimen.text_size));
					}
					else {
						currButton.setText("");
						Drawable d = getResources().getDrawable(R.drawable.g_gray);
						currButton.setBackgroundDrawable(d);
						currButton.setTextSize(getResources().getDimension(R.dimen.text_size));
					}
				}

			}
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
				int lastClickedRow=0;
				int lastClickedCol=0;
				makeMove(lastClickedRow, lastClickedCol);				
			}
		});
		buttons[0][1].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=0;
				int lastClickedCol=1;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[0][2].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=0;
				int lastClickedCol=2;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[0][3].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=0;
				int lastClickedCol=3;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[0][4].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=0;
				int lastClickedCol=4;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});

		buttons[1][0].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=1;
				int lastClickedCol=0;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[1][1].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=1;
				int lastClickedCol=1;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[1][2].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=1;
				int lastClickedCol=2;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[1][3].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=1;
				int lastClickedCol=3;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[1][4].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=1;
				int lastClickedCol=4;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});

		buttons[2][0].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=2;
				int lastClickedCol=0;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[2][1].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=2;
				int lastClickedCol=1;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[2][2].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=2;
				int lastClickedCol=2;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[2][3].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=2;
				int lastClickedCol=3;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[2][4].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=2;
				int lastClickedCol=4;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});

		buttons[3][0].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=3;
				int lastClickedCol=0;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[3][1].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=3;
				int lastClickedCol=1;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[3][2].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=3;
				int lastClickedCol=2;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[3][3].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=3;
				int lastClickedCol=3;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[3][4].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=3;
				int lastClickedCol=4;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});

		buttons[4][0].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=4;
				int lastClickedCol=0;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[4][1].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=4;
				int lastClickedCol=1;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[4][2].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=4;
				int lastClickedCol=2;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[4][3].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=4;
				int lastClickedCol=3;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
		buttons[4][4].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int lastClickedRow=4;
				int lastClickedCol=4;
				makeMove(lastClickedRow, lastClickedCol);
			}
		});
	}

}