package edu.gettysburg.cs.jumpmaze;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Random;

import android.app.Application;
import android.content.Context;


public class Maze extends Application{
	Node player;

	public static Context c;
	
	public void onCreate() {
		c = getApplicationContext();
	}

	//Code gotten from http://stackoverflow.com/questions/14801876/android-accessing-file-from-internal-storage-using-randomaccessfile
	//used to access a file
	public File createCacheFile(Context context, String filename) throws IOException{
		File cacheFile = new File(context.getCacheDir(), filename);

		if (cacheFile.exists()) {
			return cacheFile ;
		}

		InputStream inputStream = context.getAssets().open(filename);
		FileOutputStream fileOutputStream = new FileOutputStream(cacheFile);

		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int length = -1;
		while ( (length = inputStream.read(buffer)) > 0) {
			fileOutputStream.write(buffer,0,length);
		}

		fileOutputStream.close();
		inputStream.close();

		return cacheFile;
	}

	//creates a maze using a random number generator
	//Given:void
	//Return:Node[][]        Returns the maze
	public Node[][] getMaze() throws IOException{
		Random rand = new Random();
		//There are 10000 mazes to choose from, so generate a number between 0 - 9999 to decide what maze to use
		int randomNum = rand.nextInt(10000);
		File cacheFile = createCacheFile(c, "mazes.txt");
		RandomAccessFile raf = new RandomAccessFile(cacheFile, "r");

		Node[][] maze = new Node[5][5];
		//need to set the pointer to the correct position.
		int byteNum = 6 + (randomNum * 26);
		raf.seek((long)byteNum);
		char content;
		//initialize a maze starting at a certain byte in the file
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				content = (char) raf.readByte();
				Node node = new Node();
				int num = Character.getNumericValue(content);
				node.key = num;
				node.rowPos = i;
				node.colPos = j;
				maze[i][j] = node;
				if (num == 0){
					node.isGoal = true;
					System.out.println("Goal!!");
				}
			}
		}
		raf.close();
		LegalMoves.allLegalMoves(maze);
		player = maze[0][0];
		player.visited = true;
		return maze;
	}
}
