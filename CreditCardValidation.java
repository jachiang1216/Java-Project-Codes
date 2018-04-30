/*
Credit Card Validation Program 

Background:
Credit cards use a system of blocked numbers similar to the ISBN. 
1st digit of the number is the Major Industry Identifier (MII). It identifies which group issued the card. 
Next 5 numbers is the Issue Identifier. 
7th digit to the next-to-last digit is the Account Number. 
Last digit is the Check Digit.
***This Program only identifies Visas and MasterCards (4 and 5, respectively)***
Validation Algorithm:
---Double all even-positioned digits when counting from right to left.
---Determine the sum of the undoubled digits from the products (Step 1) and each of the unaffected (odd-positioned) digits in the original number.
---Verify the account number by determining if the sum from step 2 is a multiple of 10.

Written by Jian An Chiang
*/

import static java.lang.System.out; //Import Print Method
import java.util.Scanner; //Import Scanner Class

public class CreditCardValidation {
	public static void main(String[] args) {
		Scanner input2 = new Scanner(System.in);
		String yes_no = "Y";
		while (yes_no.equalsIgnoreCase("Y")){ //Keep running the program until User inputs 'N'.
			Verification obj = new Verification();
			obj.inputNo(); //Method receives User Input
			obj.displayResult(); //Method outputs result after computation.
			
			out.println("Do you wish to enter another Credit Card Number (Y/N)?");
			yes_no = input2.nextLine();
		}
		out.println("Good-Bye!");
		input2.close();
	}
}

class Verification{
	private
		String cardNo;
		String cardType;
	public
	Verification(){ //Constructor. Leave Empty.
	}
	
	//Receive User Input
	void inputNo(){
		
		Scanner input = new Scanner(System.in);
		out.println("Enter your Credit Card number (16 Digits) for Validation: ");
		cardNo = input.nextLine(); //Assume card number entered is of String type (for fun).
		cardNo = cardNo.replaceAll("\\s+",""); //Gets rid of White Spaces.
		
		// -'0' is used to convert character into integer type.
		while (cardNo.length()!=16 || ((cardNo.charAt(0)-'0'!=4) && (cardNo.charAt(0)-'0'!=5))){
			out.println("This is an Invalid Card. Try Again.");
			cardNo = input.nextLine();
			cardNo = cardNo.replaceAll("\\s+",""); 
		}
		
		//Determine Card Type
		if (cardNo.charAt(0)==4){
			cardType = "VisaCard";
		}else{
			cardType = "MasterCard";
		}
		
	}
	
	//Summing up the Digits
	int sum_digits(int n){
		return n==0 ? 0 : n%10 + sum_digits(n/10); //Recursive Implementation
	}
	
	//Validation Process
	boolean validateNo(){
		int sum=0;
		int array[] = new int[16];
		for (int i=0;i<16;++i){
			//1st Step of the Validation Process
			if (i%2==0){
				array[i]=Character.getNumericValue(cardNo.charAt(i))*2; 
				if (array[i]>9){
					array[i]=sum_digits(array[i]); //If double digit, sum the digits.
				}
			}else{
				array[i]=Character.getNumericValue(cardNo.charAt(i));
			}
			//Second Step of the Validation Process
			sum += array[i];
		}
		//3rd Step of the Validation Process
		if (sum%10==0){
			return true; 
		}else{
			return false;
		}
	}
	
	//Prints out the Validation Result to the User.
	void displayResult(){
		if (validateNo()==true){
			out.println(cardType+" "+cardNo+" is Valid");
		}else{
			out.println(cardType+" "+cardNo+" is Invalid");
		}
	}
}
