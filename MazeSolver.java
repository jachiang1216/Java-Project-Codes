/*Maze Solver Program
 * This Program imports a text file containing a maze and finds a way from Start (Top Left Corner) to Finish (Bottom Right Corner).
 * Display Legend:
 * "0" - Wall Block
 * "1" - Able to Walk Path
 * "4" - Wall Block discovered by Program
 * "8" - The Path that the User should take to reach the end of the Maze. 
 * Please be advised that this solution may not be the optimal solution but it is a viable solution.
 * Written by Jian An Chiang
 */

import static java.lang.System.out; //Import Print Method
import java.io.FileReader; //For Text File
import java.io.IOException;
import java.util.Scanner; //Read from Text File
import java.util.ArrayList;

public class MazeSolver {

	public static void main(String[] args) {
		Maze obj = new Maze();
		obj.displayResult(); //Display Solved Maze
	}

}
class Maze{
	private
	int m; //Number of Rows
	int n; //Number of Columns
	int []longArray; //Array storing all positions.
	boolean []visited; //Array storing list of unvisited/visited positions.
	public
	Maze(){
		m=0;
		n=0;
		mazeReader(); //Read in User Input for Dimensions.
		int current_position=0; //Start Position
		longArray[0]=8;
		solveMaze(current_position);
	}
	void mazeReader(){ //Reads in User Input
		try{
		FileReader in = new FileReader("maze.txt");
		Scanner read = new Scanner(in);
		//Determine Dimensions by using ArrayList;
		ArrayList temp = new ArrayList();
		int x=0;
		while (read.hasNextLine()){
			String line = read.nextLine();
			Scanner across = new Scanner(line);
			while (across.hasNextInt()){
				temp.add(across.nextInt());
				x++;
			}
			m++;
			n=temp.size()/m;
			across.close();
		}
		read.close();
		//Initialize arrays with dimensions determined.
		this.longArray=new int[x];
		this.visited=new boolean[x];
		out.println("This is your Maze: ");
		x=0;
		//Transfer ArrayList contents into the new Array.
		for (int i=0;i<m;++i){
			for (int j=0;j<n;++j){
				longArray[x]=(int)temp.get(x); 
				out.print(longArray[x]+" "); //Output the imported maze to the user.
				x++;
			}
			out.println("");
		}
		}catch(IOException e){e.getMessage();}
	}
	void displayResult(){ //Display Solved Maze
		out.println("Maze Solved: (The path containing '8' is the way)");
		int x=0;
		for (int i=0;i<m;++i){
			for (int j=0;j<n;++j){
				out.print(longArray[x]+" "); //Output the solution to the user.
				x++;
			}
			out.println("");
		}
	}
	boolean solveMaze(int current_position){ //Solves the Maze
		int direction=0; //Monitors how many directions are checked and how many are left to check.
		int new_position=current_position; //A new position will be determined based on current position
		visited[current_position]=true; //We visit the current position. Therefore, change to true.
		while (direction<4){//Stop only when everything is checked. Monitored by Direction Counter.
			if (direction==0){//Go Left
				new_position-=1;
				if (new_position<0){} //Position cannot be negative.
				//Modulo is so that we don't wrap around the maze. We also don't want to go to a position with "0". And we don't want to go to a position that's visited already
				else if ((new_position>=0 && (new_position+1)%n!=0) && longArray[new_position]==1 && visited[new_position]==false){
					boolean result = solveMaze(new_position); //Go to next position and solve again.
					if (result==true){
						longArray[new_position]=8; //Once we found the final position, it will return true and we can just keep returning true and reassign the positions to 8.
					}
				}else if (longArray[new_position]==0 && (new_position+1)%n!=0 && visited[new_position]==false){
					longArray[new_position]=4; //If the new position has "0", we can just label that as un-passable block, "4".
				}else{}
			}else if (direction==1){//Go Right
				new_position+=1;
				if (new_position>=m*n){} //Position cannot be greater than number of position available.
				else if (new_position<m*n && new_position%n!=0 && longArray[new_position]==1 && visited[new_position]==false){
				
					boolean result = solveMaze(new_position);
					if (result==true){
						longArray[new_position]=8;
					}
				}else if (longArray[new_position]==0 && (new_position+1)%n!=0 && visited[new_position]==false){
					longArray[new_position]=4;
				}else{}
			}else if (direction==2){//Go Top
				new_position-=n;
				if (new_position<0){} //Position cannot be negative.
				else if (new_position>=0 && longArray[new_position]==1 && visited[new_position]==false){
					
					boolean result = solveMaze(new_position);
					if (result==true){
						longArray[new_position]=8;
					}
				}else if (longArray[new_position]==0 && (new_position+1)%n!=0 && visited[new_position]==false){
					longArray[new_position]=4;
				}else{}
			}else if(direction==3){//Go Bottom
				new_position+=n;
				if (new_position>=m*n){} //Position cannot be greater than number of position available.
				else if (new_position<m*n && longArray[new_position]==1 && visited[new_position]==false){
					
					
					boolean result = solveMaze(new_position);
					if (result==true){
						longArray[new_position]=8;
					}
				}else if (longArray[new_position]==0 && (new_position+1)%n!=0 && visited[new_position]==false){
					longArray[new_position]=4;
				}else{}
			}else{}
			new_position=current_position; //Reassign new position to be current position since it was changed in the if/else statements.
			direction++; //Increment the direction counter.
			if (visited[m*n-1]==true){//If the current position is the finish position, we are done.	
				return true; //Check if we have reached the Finish.
			}
		}
		return false; //Return false if we didn't find the finish and we checked all possible paths from position.
	}
}
