/* Cash Register Program: Please Refer to the READ_ME file for an explanation
 * of how the Cash Register Program works.
 * Written by Jian An Chiang
 */
import static java.lang.System.out; //Import Print Method

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList; //Imports ArrayList
import java.lang.Math;
import java.time.format.DateTimeFormatter; //Imports Date
import java.time.LocalDateTime;  

public class CashRegisterProgram {
	//Main Method
	public static void main(String[] args) {	
		CashRegister obj = new CashRegister();
		obj.getReceipt(); 
	}
}

//Dairy Q Store Interface
interface DQ_Store{
	//Constants
	public final double tax = 0.13; //Government Tax
	public final String store_name = "Dairy Queen Dessert Shoppe"; //Store Name
	public final  int MAX_TOPPINGS = 3;  //Max Number of Toppings.
	public static final int SCOOP_COST = 35;  //Scoop Cost.
	//Methods
	String centsConverter(double n); //Round total cost to the nearest nickel.
	void clear(); //Clear data items in cash register and reset it to its defaults.
	double calcSubTotal(double n); //Calculates sub total cost before tax.
	double calcTax(double n); //Calculates tax on items purchased based off the provincial constant.
	double calcTotalCost(double n); //Calculates total cost in cents.
}

//Cash Register: Uses the Dairy Q Store Interface.
class CashRegister implements DQ_Store{
	private
	int num_of_items=0; //Number of Items per Order
	int receipt_number=1;
	public 
	CashRegister(){ //Default Constructor
	}
	public void getReceipt(){
		try{
			FileReader in = new FileReader("items.txt");
			Scanner read = new Scanner(in).useDelimiter("\r\n|,"); //Line/Space/Comma Delimiter
			String dashes = "--------------------------";
			String equals = "==========================";
			
			PrintWriter writer = new PrintWriter("Receipt.txt","utf-8");
			
			
			while (read.hasNextLine()){
				writer.println(dashes+dashes+dashes+dashes);
				writer.printf("\t\t\t\t**********>%s<**********\n",DQ_Store.store_name);
				writer.println("");
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); //Print out Local Time
				LocalDateTime now = LocalDateTime.now();
				writer.println(dtf.format(now));
				writer.println(dashes+dashes+dashes+dashes);
				writer.println("Receipt Number "+receipt_number+"\t\t\t\t\t\t   item");
				writer.println(equals+equals+equals+equals);
				
				double sub_totalcost=0;
				//Read in Receipt Contents
				num_of_items=Integer.parseInt(read.nextLine());
				for (int i=0;i<num_of_items;++i){
					String dessert_type = read.next(); //Reads in Dessert Type
					if (dessert_type.equalsIgnoreCase("Candy")){
						writer.printf("%s******\t",read.next()); //Prints the Name
						Candy candy = new Candy(read.nextInt(),read.nextInt());
						sub_totalcost+=candy.cost();
						writer.printf("%d grams@%d cents/each  Item Cost = $ %.2f",candy.getWeight(),candy.getPrice(),(double) candy.cost()/100);
						writer.println(" ");
					}
					else if (dessert_type.equalsIgnoreCase("Cookie")){
						writer.printf("%s******\t$",read.next()); //Prints the Name
						Cookie cookie = new Cookie(read.nextInt(),read.nextInt());
						writer.printf("%d cookies@%d cents/each  Item Cost = $ %.2f",cookie.getNumber(),cookie.getPrice(),(double) cookie.cost()/100);
						writer.println(" ");
						sub_totalcost+=cookie.cost();
					
					}
					else if (dessert_type.equalsIgnoreCase("Icecream")){
						writer.printf("%s******\t$",read.next()); //Prints the Name
						Icecream icecream = new Icecream(read.nextInt(),read.nextInt());
						writer.printf("%.2f with %dscoops@%d cents/each  Item Cost = $ %.2f",(double) icecream.getPrice()/100,icecream.getScoops(),SCOOP_COST,(double) icecream.cost()/100);
						writer.println(" ");
						sub_totalcost+=icecream.cost();
					}
					else{
						ArrayList topping_names = new ArrayList(MAX_TOPPINGS); //Array List to Hold a Maximum of 3 Topping Names.
						ArrayList topping_prices = new ArrayList(MAX_TOPPINGS); //Array List to Hold a Maximum of 3 Topping Prices.
						int toppingCount = read.nextInt(); //Read in Topping Count.
						writer.printf("%s******\t$",read.next()); //Prints Sundae Name
						int base_price=read.nextInt(); //Read in Base Price
						writer.printf("%.2f",(double) base_price/100);
						writer.println("");
						writer.println("with toppings(s):");
						for (int j=0;j<toppingCount;++j){ //Depending on how many toppings, read in each topping systematically.
							topping_names.add(read.next()); 
							topping_prices.add(read.nextInt());
							writer.printf("%s @$ %.2f",(String)topping_names.get(j),(double)((int)topping_prices.get(j))/100);
							writer.println("");
						}
						DessertItem sundae = new Sundae(toppingCount,base_price,topping_names,topping_prices);
						writer.printf("");
						sub_totalcost+=sundae.cost();
					}
					read.nextLine(); //Consumes current line.
				}
				writer.println(equals+equals+equals+equals);
				//Printing out Total Costs
				writer.printf("Sub Total Cost = $%.2f",calcSubTotal(sub_totalcost));
				writer.println("");
				writer.printf("Tax = $%.2f",calcTax(calcSubTotal(sub_totalcost)));
				writer.println("");
				writer.printf(equals+equals+equals+equals);
				writer.println("");
				writer.printf("Total Cost = $%.2f",Double.parseDouble(centsConverter(calcTotalCost(sub_totalcost))));
				writer.println("");
				clear(); //Reset to 0.
				receipt_number++; //Increment Receipt Number
			}
			read.close(); //Close I/O
			writer.close();
			}catch (IOException e){e.getMessage();}
		}
		//Rounds Total Cost to the nearest Nickel.
		public String centsConverter(double n){
			return Double.toString(Math.round(n*20.0)/20.0);
	}
	//Clear Data
	public void clear(){
		num_of_items=0;
	}
	public double calcSubTotal(double n){
		return n/100; //Returns the total cost before taxes. Divide by 100 to get Dollars
	}
	public double calcTax(double n){
		return n*tax;
	}
	public double calcTotalCost(double n){
		return (double)Math.round(calcTax(calcSubTotal(n))*100)/100+(double)Math.round(calcSubTotal(n)*100)/100;
	}
	
}


abstract class DessertItem{
	private
	String dessert_name; //Name of the Dessert
	public
	DessertItem(){ //Default Constructor
	}
	DessertItem(String name){
		dessert_name=name;
	}
	public String getName(){
		return dessert_name; //Return Dessert Name
	}
	public abstract int cost(); //Each Dessert Type must Implement the Cost Function.
}
//Candy Dessert
class Candy extends DessertItem{
	private
	int weight; //Number of Grams
	int price; //Price per Gram
	public
	Candy(){} //Default Constructor
	Candy(int w,int p){
		weight=w;
		price=p;
	}
	public int getWeight(){
		return weight;
	}
	public int getPrice(){
		return price;
	}
	public int cost(){
		return weight*price;
	}
	public String toString(){
		return Integer.toString(cost());
	}
}
//Cookie Dessert
class Cookie extends DessertItem{
	private
	int number; //Number Purchased
	int price; //Price per Number
	public
	Cookie(){} //Default Constructor
	Cookie(int n,int p){
		number=n;
		price=p;
	}
	int getNumber(){
		return number;
	}
	int getPrice(){
		return price;
	}
	public int cost(){
		return number*price;
	}
	public String toString(){
		return Integer.toString(cost());
	}
}
//Icecream Dessert
class Icecream extends DessertItem{
	private
	int price;
	int scoops;
	public
	Icecream(){} //Default Constructor
	Icecream(int p,int s){
		price=p;
		scoops=s;
	}
	int getPrice(){
		return price;
	}
	int getScoops(){
		return scoops;
	}
	public int cost(){
		return price+scoops*35;
	}
	public String toString(){
		return Integer.toString(cost());
	}
}
//Sundae Dessert - Inherited Icecream
class Sundae extends Icecream{
	private
	ArrayList top_name = new ArrayList(3);
	ArrayList top_price = new ArrayList(3);
	int number_of_toppings;
	int base_price;
	public
	Sundae(){} //Default Constructor
	Sundae(int n,int p,ArrayList t_name,ArrayList t_price){
		base_price=p;
		number_of_toppings=n;
		top_name=t_name;
		top_price=t_price;
	}
	public int cost(){
		int totalcost=base_price;
		for (int i=0;i<number_of_toppings;++i){
			Toppings obj = new Toppings((String)top_name.get(i),(int)top_price.get(i));
			totalcost+=obj.getPrice();
		}
		return totalcost;
	}
}

class Toppings{
	private
	String toppingName;
	int toppingPrice;
	public
	Toppings(){} //Default Constructor
	Toppings(String name,int price){
		toppingName=name;
		toppingPrice=price;
	}
	String getName(){
		return toppingName;
	}
	int getPrice(){
		return toppingPrice;
	}
}
