package edu.gettysburg.cs.jumpmaze;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ActivitySplash extends Activity  {
	
	/**
	 * A button that starts the maze game
	 */
	private Button startMazeButton;
	private Button tutorialButton;
	private Button aboutButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		startMazeButton = (Button) findViewById(R.id.start_jump_maze_game);
		startMazeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				toMazePage();
			}
		});
		
		tutorialButton = (Button) findViewById(R.id.tutorial_splash);
		tutorialButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				toTutorial();
			}
		});
		
		aboutButton = (Button) findViewById(R.id.about_splash);
		aboutButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				toAbout();
			}
		});
	}
	
	private void toMazePage(){
		Intent intent = new Intent(this, MazeActivity.class);
		startActivity(intent);
	}
	
	private void toTutorial(){
		Intent intent = new Intent(this, TutorialActivity1.class);
		startActivity(intent);
	}
	
	private void toAbout(){
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}
}
