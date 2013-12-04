package edu.gettysburg.cs.jumpmaze;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TutorialActivity3 extends Activity {
	
	//Buttons that move back and forth in the tutorial
	private ImageButton homeButton;
	private ImageButton backButton;
	private ImageButton nextButton;
	
	//Keeps track of which page of the tutorial you're on
	private final static int thisPage = 3;
	private int nextPage = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial3);

		homeButton = (ImageButton) findViewById(R.id.home);
		backButton = (ImageButton) findViewById(R.id.back_tutorial);
		nextButton = (ImageButton) findViewById(R.id.next_tutorial);
		
		
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				nextPage--;
				toPage();
			}
		});
		
		homeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				toHomePage();
			}
		});
		
		nextButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				nextPage++;
				toPage();
			}
		});
		
		//https://github.com/tneller/jump-maze-android.git
	}
	
	private void toHomePage(){
		Intent intent = new Intent(this, ActivitySplash.class);
		startActivity(intent);
	}
	
	private void toPage(){
		if(nextPage < thisPage){
			Intent intent = new Intent(this, TutorialActivity2.class);
			startActivity(intent);
		}
		else if(nextPage > thisPage){
			Intent intent = new Intent(this, TutorialActivityNote.class);
			startActivity(intent);
		}
	}
}