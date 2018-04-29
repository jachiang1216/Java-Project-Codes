/*
Palindrome Detector Program
Description: 
--- 1. Returns the Word Length Inputted.
--- 2. Detects all palindromes (greater or equal to 4 char) within a single word.
--- 3. Determines the longest palindrome. If two palindromes are the same size, it will output both.
Written by Jian An Chiang
*/
import static java.lang.System.out; //Import Print Method
import java.util.Scanner; //Import Scanner Class

public class Palindrome {
	//Main Method
	public static void main(String[] args){
		
		//User Word Input
		Scanner input = new Scanner(System.in);
		out.println("Type a word greater or equal to 4 chars: ");
		String word = input.nextLine();
		
		while (word.length()<4){
			out.println("Word size is too small. Try again.");
			word = input.nextLine();
		}

		Palin obj = new Palin(word);
		obj.palindromeControl(); //Call Palindrome Method.
		out.println("Word Length: "+ obj.totalSize()); //Output Word Length to User.
		out.println("Palindromes located within Word: "); 
		obj.printAll();
		out.println("Longest Palindrome(s) in Word: ");
		obj.longestPalin();

		input.close(); //Closes IO Programs
	}
}
	
class Palin{
	private
		String word; //The Word	
		int total_size; //Length of Word
		int size; //Current Size Range
		int n; //Current Spread
		int lol; //Current Tracker
		int array_size=0; //Size of String array
		int array_ctr; //Determines where to store the String
		String arrayStr[]; //Stores all Palindromes detected
	
	public 
	
	Palin(String obj){ //Constructor
		word = obj.toLowerCase(); //Ignore cases.
		total_size = word.length();
		size=4; //Only look for palindromes that are equal or more than 4 char.
		lol=0; //Counter used to verify if substring is palindrome 
		int i=1;
		for (int ctr=total_size;ctr!=3;--ctr){
			array_size += i*ctr; //Determine the max number of palindromes that the word can have. Use as array size.
			i++;
		}
		this.arrayStr = new String[array_size]; //Initialize array with size.
		array_ctr=0;
	}
	
	//Return Word Length
	int totalSize(){
		return total_size; //Returns the length of the word.
	}
	
	//Dinstinguishes Even and Odd Palindromes
	void palindromeControl(){
		for (int i=size;i<=total_size;++i){
			if (i%2==0){//Even Palindrome
				evenpalindromeCounter(i); //Call Function used to detect Even Palindromes.
			}
			else { //Odd Palindrome
				oddpalindromeCounter(i); //Call Function used to detect Odd Palindromes.
			}
		}
	}
	
	//Detects Even Palindromes
	void evenpalindromeCounter(int n){
		for (int j=0;j<=total_size-n;++j){
			for (int k=0;k<=n/2-1;++k){
				if (word.charAt(j+k)!=word.charAt(n+j-k-1)){
				}
				else{
					lol++; //Increment if characters match.
				}
			}
			if (lol==n/2){ //If all characters match, store the substring in array.
				arrayStr[array_ctr] = word.substring(j,j+n);
				array_ctr++; //Increment number of palindromes.
			}
			lol=0;
		}
	}
	
	//Detects Odd Palindromes
	void oddpalindromeCounter(int n){
		for (int j=0;j<=total_size-n;++j){
			for (int k=0;k<(n-1)/2;++k){
				if (word.charAt(j+k)!=(word.charAt(n+j-k-1))){
				}
				else{
					lol++; //Increment if characters match.
				}
			}
			if (lol==((n-1)/2)){ //If all characters match, store the substring in array.
				arrayStr[array_ctr] = word.substring(j,j+n);
				array_ctr++; //Increment number of palindromes.
			}
			lol=0;
		}
	}
	
	//Prints all Palindromes
	void printAll(){
		for (int i=0;i<array_ctr;++i){
			out.println(arrayStr[i]); //Print all palindromes
		}
	}
	
	//Prints the longest Palindrome
	void longestPalin(){		
		int same_ctr = 0;
		try{
		//Start at back of array because array is sorted from shortest to largest.
		for (int i=array_ctr-1;i!=0;--i){
			if (arrayStr[i].length()>arrayStr[i-1].length()){
				break; //Break from for loop because we know all other palindrome lengths are less.
			} else if (arrayStr[i].length()==arrayStr[i-1].length()){
				same_ctr++;
			} else{}
		}
		for (int j=0;j<=same_ctr;++j){
			out.println(arrayStr[array_ctr-1-j]); //Print just the long palindromes
		}	
		}catch(Exception e){
			out.println("No Palindromes"); //If above code doesn't work, it means we do not have palindromes to compare.
		}
	}
}
