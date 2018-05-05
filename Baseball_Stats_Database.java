/*BaseBall Player Stats Database
 * This program allows you to import a file containing baseball player information,
 * manipulate the data, and output it into a new text file.
 * Instructions and Commands are given in the console window.
 * Enter a number to perform the operation.
 * 
 * A sample file called "PlayerInfo.txt" is given for you to test.
 * In a sample run, I have named the save file as "NewPlayerInfo".
 * 
 * Written by Jian An Chiang
 */
import static java.lang.System.out; //Import Print Method

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;  
import java.util.ArrayList;
import java.io.PrintWriter;

public class Baseball_Stats_Database {
	static ArrayList playerList = new ArrayList(); //Stores all Player Objects
	public static void main(String[] args) {
		mainMenu();
		out.println("Program will now Exit.");

	}
	//Main Program Methods
	static void mainMenu(){
		out.println("What would you like to do? Enter Number associated with Command: ");
		out.println("1. Load stats info from a file.");
		out.println("9. Exit the program.");
		Scanner main = new Scanner(System.in);
		int stop=0;
		stop=main.nextInt();
		while (stop!=9 && stop!=1){
			out.println("You have entered an invalid number. Try again");
		}
		if (stop==1){
			LoadInformation();
			while(stop!=10){
				out.println("");
				out.println("Commands: ");
				out.println("2. Display all players.");
				out.println("3. Enter Player's Height.");
				out.println("4. Sort all players alphabetically by Surname.");
				out.println("5. Sort all players by batting average.");
				out.println("6. Delete a player by selecting the player's surname from a list.");
				out.println("7. Add a player to the stats.");
				out.println("8. Search for a player by Surname and display stats if found.");
				out.println("9. Save stats to a file.");
				out.println("10. Exit the program.");
				out.println("What do you want to do now? ");
				stop=main.nextInt();
				if (stop>1 && stop<10){ //All different things that the user can do.
					switch (stop){
					case 2: DisplayPlayerInfo(); break;
					case 3: AddDataHeight(); break;
					case 4: SortDataAlpha(); break;
					case 5: SortDataBattingAverage(); break;
					case 6: DeleteAPlayer(); break;
					case 7: AddAPlayer(); break;
					case 8: SearchPlayer(); break;
					case 9: SaveInformation();
					}
				}else if (stop==10){}
				else{
					out.println("That is an Invalid Number. Try Again");
				}
			}
		}
		else{}
		main.close();
	}
	static void LoadInformation(){ //Load player information from a text file.
		Scanner ask = new Scanner(System.in);
		out.print("What file would you like to load? ");
		try{
		String answer = ask.nextLine();
		FileReader in = new FileReader(answer);
		Scanner read = new Scanner(in).useDelimiter("\\s");
		while (read.hasNextLine()){
			ArrayList list = new ArrayList(); //Stores all components related to Player.
			String line = read.nextLine();
			Scanner across = new Scanner(line);
			for (int ctr=0;ctr!=3;++ctr){ //Read all Strings.
				list.add(across.next());
			}
			for (int ctr2=0;ctr2!=7;++ctr2){ //Read all Integers.
				list.add(across.nextInt());
			}
			BaseBallStats player = new BaseBallStats(list); //Create Player Object with components.
			playerList.add(player); //Add object onto the list.
		
		}
		out.println("File has been loaded into database.");
		}catch(IOException e){e.getMessage();}
	}
	static void DisplayPlayerInfo(){ //Lists and Displays information on Baseball Players.
		out.println("Stats for Each BaseBall Player");
		out.printf("%s\t\t%s\t\t%s\t\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
				"Surname","GivenName","Position","Height(cm)","Hits","AtBats","Singles",
				"Doubles","Triples","HomesRuns","Batting Average");
		out.println("");
		for (int i=0;i<playerList.size();++i){
			out.printf("%s\t\t%s\t\t\t%s\t\t%s\t\t%s\t%s\t%s\t%s\t%s\t%s\t\t%.4f",
				((Player)playerList.get(i)).getSurname(),((Player) playerList.get(i)).getGivenname(),
				((Player) playerList.get(i)).getPosition(),((Player) playerList.get(i)).getHeight(),
				((BaseBallStats) playerList.get(i)).getHits(),((BaseBallStats) playerList.get(i)).getAtBat(),
				((BaseBallStats) playerList.get(i)).getSingles(),((BaseBallStats) playerList.get(i)).getDoubles(),
				((BaseBallStats) playerList.get(i)).getTriples(),((BaseBallStats) playerList.get(i)).getHomeRuns(),
				((BaseBallStats) playerList.get(i)).calcBattingAvg());
			out.println("");
		}
	}
	static void AddDataHeight(){ //Allows user to enter the field Heights for each of the players 
		Scanner heightread = new Scanner(System.in);
		int h=0;
		for (int i=0;i<playerList.size();++i){
			out.printf("Please enter the height(cm) for %s %s. Must be between 125-240cm. ",((Player)playerList.get(i)).getGivenname(),((Player)playerList.get(i)).getSurname());
			h=heightread.nextInt();
			while (h<125 && h>240){
				out.print("Height not within boundaries. Please enter a height between 125-240cm. ");
				h=heightread.nextInt();
			}
			((Player)playerList.get(i)).setHeight(h);
		}
		out.println("Database updated with new heights.");
	}
	static void SortDataAlpha(){ //Sort data alphabetically by Player Surname. I chose to implement BUBBLE SORT for fun.
		if(playerList.size()<1){} //Nothing to sort if there's only one player.
		else{
			for (int i=0;i<playerList.size()-1;++i){
				int x=0;
				for (int j=playerList.size()-1;j>0;--j){
					//If the next name precedes the current name, make a swap.
					if (((Player)playerList.get(x)).getSurname().compareTo(((Player)playerList.get(x+1)).getSurname())>0){
						Player temp = (Player)playerList.get(x+1);
						playerList.set(x+1,playerList.get(x));
						playerList.set(x,temp);
					}
					x++;
				}
			}
		}
	}
	static void SortDataBattingAverage(){ //Sort all players by batting average (High to Low). I chose to implement INSERTION SORT for fun.
		if(playerList.size()<1){} //Nothing to sort if there's only one player.
		else{	
			for (int i=0;i<playerList.size()-1;++i){ //Move up an index every time.
				for (int j=i;j>=0;--j){ //Go down while checking if the current one needs to be swapped.
					if(((BaseBallStats)playerList.get(j+1)).calcBattingAvg()>(((BaseBallStats)playerList.get(j)).calcBattingAvg())){
						Player temp = (Player)playerList.get(j+1);
						playerList.set(j+1,playerList.get(j));
						playerList.set(j,temp);
					}
				}
			}
		}
	}
	static void DeleteAPlayer(){ //Delete Player and his/her information off of the Database.
		Scanner deleter = new Scanner(System.in);
		out.print("What player do you want to delete from the database? Enter his/her Surname. ");
		String sur_name = deleter.nextLine();
		for (int i=0;i<playerList.size();++i){ //Linear Search Method
			if (sur_name.equalsIgnoreCase(((Player)playerList.get(i)).getSurname())){
				((BaseBallStats) playerList.get(i)).initialize();
				((Player) playerList.get(i)).initializePlayer();
				playerList.remove(i);
			}
		}
	}
	static void AddAPlayer(){ //Allows user to add a player with his or her information onto the Database.
		BaseBallStats new_player = new BaseBallStats(); 
		new_player.assignValuePlayer(); //Allow user to set details about the player.
		new_player.setAll(); //Allow user to set the player stats.
		new_player.correctStats(); //Check if stats are set correctly.
		playerList.add(new_player); //Add the new player to the array list.
		out.println("Player added successfully.");
	}
	static void SearchPlayer(){ //Display stats of a player if the user enters an appropriate surname.
		Scanner searcher = new Scanner(System.in);
		out.print("What player do you search in the database? Enter his/her Surname. ");
		String sur_name = searcher.nextLine();
		out.println("All Info for Surname "+sur_name);
		out.printf("%s\t\t%s\t\t%s\t\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
				"Surname","GivenName","Position","Height(cm)","Hits","AtBats","Singles",
				"Doubles","Triples","HomesRuns","Batting Average");
		out.println("");
		boolean search=false;
		for (int i=0;i<playerList.size();++i){ //Linear Search Method
			if (sur_name.equalsIgnoreCase(((Player)playerList.get(i)).getSurname())){
				search=true;
				out.printf("%s\t\t%s\t\t\t%s\t\t%s\t\t%s\t%s\t%s\t%s\t%s\t%s\t\t%.4f",
					((Player)playerList.get(i)).getSurname(),((Player) playerList.get(i)).getGivenname(),
					((Player) playerList.get(i)).getPosition(),((Player) playerList.get(i)).getHeight(),
					((BaseBallStats) playerList.get(i)).getHits(),((BaseBallStats) playerList.get(i)).getAtBat(),
					((BaseBallStats) playerList.get(i)).getSingles(),((BaseBallStats) playerList.get(i)).getDoubles(),
					((BaseBallStats) playerList.get(i)).getTriples(),((BaseBallStats) playerList.get(i)).getHomeRuns(),
					((BaseBallStats) playerList.get(i)).calcBattingAvg());
				out.println("");
			}
		}
		if (search==false){
			out.println("Cannot find player with surname provided.");
		}
	}
	static void SaveInformation(){
		Scanner read = new Scanner(System.in);
		out.println("Name the file you want to save. ");
		String file_name = read.nextLine();
		try{
		PrintWriter writer = new PrintWriter(file_name+".txt","utf-8");
		writer.println("Stats for Each BaseBall Player");
		writer.printf("%s\t\t%s\t\t%s\t\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
				"Surname","GivenName","Position","Height(cm)","Hits","AtBats","Singles",
				"Doubles","Triples","HomesRuns","Batting Average");
		writer.println("");
		for (int i=0;i<playerList.size();++i){
			writer.printf("%s\t\t%s\t\t\t%s\t\t%s\t\t%s\t%s\t%s\t%s\t%s\t%s\t\t%.4f",
				((Player)playerList.get(i)).getSurname(),((Player) playerList.get(i)).getGivenname(),
				((Player) playerList.get(i)).getPosition(),((Player) playerList.get(i)).getHeight(),
				((BaseBallStats) playerList.get(i)).getHits(),((BaseBallStats) playerList.get(i)).getAtBat(),
				((BaseBallStats) playerList.get(i)).getSingles(),((BaseBallStats) playerList.get(i)).getDoubles(),
				((BaseBallStats) playerList.get(i)).getTriples(),((BaseBallStats) playerList.get(i)).getHomeRuns(),
				((BaseBallStats) playerList.get(i)).calcBattingAvg());
			writer.println("");
		}
		writer.close();
		}catch(IOException e){e.getMessage();}
		out.println("File saved successfully");
	}
}
class Player{
	private
	String sur_name;
	String given_name;
	String position;
	int height;
	BaseBallStats stats;
	public
	Player(){} //Default Constructor
	Player(String s,String g,String p,int h){
		sur_name=s;
		given_name=g;
		position=p;
		height=h;
	}
	//Accessors
	String getSurname(){
		return sur_name;
	}
	String getGivenname(){
		return given_name;
	}
	String getPosition(){
		return position;
	}
	int getHeight(){
		return height;
	}
	void setSurname(String n){
		sur_name=n;
	}
	//Mutators
	void setGivenname(String n){
		given_name=n;
	}
	void setPosition(String n){
		position=n;
	}
	void setHeight(int h){
		height=h;
	}
	void initializePlayer(){ //Set all Integer Fields to -1 and String Fields to null.
		sur_name=null;
		given_name=null;
		position=null;
		height=-1;
	}
	void assignValuePlayer(){
		Scanner assign = new Scanner(System.in);
		out.print("Enter Player's Surname: ");
		sur_name=assign.nextLine();
		out.print("Enter Player's Given Name: ");
		given_name=assign.nextLine();
		out.print("Enter Player's Position: ");
		position=assign.nextLine();
		out.print("Enter Player's Height: ");
		height=assign.nextInt();
	}
}
//Baseball Stats of Player
class BaseBallStats extends Player{
	int hits; //Number of hits that the Player made
	int atBat; //Number of times Player went up to bat.
	int singles;
	int doubles;
	int triples;
	int homeruns;
	BaseBallStats(){} //Default Constructor
	BaseBallStats(ArrayList list){
		//Call Parent Constructor to initialize Player variables.
		super((String)list.get(0),(String)list.get(1),(String)list.get(2),(int)list.get(3)); 
		hits=(int)list.get(4);
		atBat=(int)list.get(5);
		singles=(int)list.get(6);
		doubles=(int)list.get(7);
		triples=(int)list.get(8);
		homeruns=(int)list.get(9);
	}
	//Accessors
	int getHits(){
		return hits;
	}
	int getAtBat(){
		return atBat;
	}
	int getSingles(){
		return singles;
	}
	int getDoubles(){
		return doubles;
	}
	int getTriples(){
		return triples;
	}
	int getHomeRuns(){
		return homeruns;
	}
	//Mutators
	void setHits(int n){
		hits=n;
	}
	void setAtBat(int n){
		atBat=n;
	}
	void setSingles(int n){
		singles=n;
	}
	void setDoubles(int n){
		doubles=n;
	}
	void setTriples(int n){
		triples=n;
	}
	void setHomeRuns(int n){
		homeruns=n;
	}
	void initialize(){ //Sets all fields to -1.
		hits=-1;
		atBat=-1;
		singles=-1;
		doubles=-1;
		triples=-1;
		homeruns=-1;
	}
	void setAll(){ //Allow user to assign values to the fields all at once.
		Scanner correct = new Scanner(System.in);
		out.print("Enter number of hits Player got when batting: ");
		hits=correct.nextInt();
		out.println("");
		out.print("Enter number of times Player went up to bat: ");
		atBat=correct.nextInt();
		out.println("");
		out.print("Enter number of singles: ");
		singles=correct.nextInt();
		out.println("");
		out.print("Enter number of doubles: ");
		doubles=correct.nextInt();
		out.println("");
		out.print("Enter number of triples: ");
		triples=correct.nextInt();
		out.println("");
		out.print("Enter number of homeruns: ");
		homeruns=correct.nextInt();	
		out.println("");
	}
	double calcBattingAvg(){
		return (double)hits/atBat;
	}
	boolean checkStats(){ //Checks if the stats are correct.
		if (hits==(singles+doubles+triples+homeruns)){
			return true;
		}else{
			return false;
		}
	}
	void correctStats(){ //Checks if the stats are correct and tells user to correct them if they aren't.
		boolean result = checkStats();
		if (result==false){
			out.println("Stats are incorrect. Please enter the correct stats.");
			Scanner correct = new Scanner(System.in);
			out.print("Enter number of singles: ");
			singles=correct.nextInt();
			out.println("");
			out.print("Enter number of doubles: ");
			doubles=correct.nextInt();
			out.println("");
			out.print("Enter number of triples: ");
			triples=correct.nextInt();
			out.println("");
			out.print("Enter number of homeruns: ");
			homeruns=correct.nextInt();	
			out.println("");
		}
	}
}
