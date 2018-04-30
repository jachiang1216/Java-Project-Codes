/*Please see the Ultimate Match Read.Me File to see a complete description
 * of the code.
 * Written by Jian An Chiang
 */

import static java.lang.System.out; //Import Print Method
import java.util.Scanner; //Import Scanner Class
import java.lang.Math; //Import Math Class (in order to use Abs())

public class UltimateZodiacMatch {
	
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		out.printf("How many pairs do you want to enter? ");
		int num = input.nextInt();
		input.nextLine();
		
		ZodiacMatch obj = new ZodiacMatch(num);
		obj.inputYears();
		for (int i=0;i<num;++i){
			obj.displayZodiac(i*2);
			out.println(obj.Compatibility(i*2));
		}
	}
}
class ZodiacMatch{
	private
		int n; //Number of Pairs
	    String allLines[]; //Stores the Years.
	    String zodiac[]; //Stores the Zodiac corresponding to the Years.
	ZodiacMatch(int num){ //Constructor
		n=num;
	}
	public
	//Takes in User Input.
	void inputYears(){
		Scanner input = new Scanner(System.in);
		String[]line = new String[n];
		int num=n;
		int ctr=0;
		this.allLines = new String[n*2];
		while (num!=0){
			out.println("Enter Pair "+(ctr+1)+" of Years separated by a Space: ");
			String lines = input.nextLine();
			line=lines.split(" ");
			allLines[ctr*2]=line[0];
			allLines[ctr*2+1]=line[1];
			num--;
			ctr++;
		}
		IntegerMap();
		
	}
	//Maps Years into Integers and Creates an associated String array containing the matched Zodiac
	void IntegerMap(){
		int intLines[] = new int[n*2]; //Integer Mapped Array
		this.zodiac = new String[n*2]; //Zodiac Array
		for (int i=0;i<n*2;++i){
			intLines[i] = Integer.parseInt(allLines[i])%12;
			switch (intLines[i]){
			case 0: zodiac[i] = "Monkey";
					break;
			case 1: zodiac[i] = "Rooster";
					break;	
			case 2: zodiac[i] = "Dog";
				break;	
			case 3: zodiac[i] = "Pig";
				break;	
			case 4: zodiac[i] = "Rat";
				break;	
			case 5: zodiac[i] = "Ox";
				break;	
			case 6: zodiac[i] = "Tiger";
				break;	
			case 7: zodiac[i] = "Rabbit";
				break;	
			case 8: zodiac[i] = "Dragon";
				break;
			case 9: zodiac[i] = "Snake";
				break;	
			case 10: zodiac[i] = "Horse";
				break;	
			case 11: zodiac[i] = "Goat";
			}
		}
	}
	
	//Prints Zodiac Pairs
	void displayZodiac(int i){
		out.println(zodiac[i]+" "+zodiac[i+1]); 
	}
	
	//Returns Compatibility between the Two Zodiacs
	String Compatibility(int i){
		int n = Math.abs((Integer.parseInt(allLines[i+1]))-(Integer.parseInt(allLines[i])));
		if (n==4){
			return "Compatible"; //If difference is 4, then it's compatible.
		}
		else if (n==6){
			return "Incompatible"; //If difference is 6, then it's compatible.
		} else{
			return "Not Determined"; //If difference is something else, then it's Undetermined.
		}
	}
}
