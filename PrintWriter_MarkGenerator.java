/* This Program will read in a text file of 5 Students with 10 Marks.
 * It will randomize the marks and calculate the average of the new marks.
 * It will then output the name of each student and their marks/average
 * and write it to a new text file.
 * Written by Jian An Chiang
 */

import static java.lang.System.out; //Import Print Method
import java.io.IOException;
import java.util.Scanner;
import java.io.*;
import java.util.Random;

public class PrintWriter_MarkGenerator{

	public static void main(String[] args){
		Classroom obj = new Classroom(); //Create object to read file and auto randomize.
		obj.saveFileMethod(); //Write to new file with the new marks.
	}
}
class Student{
	private
		String surName; //Hold student last name
		String givenName; //Hold student first name
		int marks[]=new int[10]; //Holds 10 test marks
	public
	Student(){ //Default Constructor
		for (int i=0;i<10;++i){
			surName = null;
			givenName = null;
			marks[i]=-1;
		}
	}
	Student(String l_name, String f_name){ //Overload Constructor
		surName = l_name; 
		givenName = f_name;
	}
	//Accessors
	String getSurname(){
		return surName;
	}
	String getGivenName(){
		return givenName;
	}
	int getMark(int n){
		return marks[n];
	}
	//Mutators
	void setSurname(String newName){
		surName = newName;
	}
	void setGivenName(String newName){
		givenName = newName;
	}
	//Randomize 10 integer array elements of marks with numbers between 50-100 inclusively.
	void fillRandom(){
		Random rand = new Random();
		for (int i=0;i<10;++i){
			int n = rand.nextInt(50)+ 50; //0-0.9 *50 + 50 to get 50-100. 
			marks[i] = n;
		}
	};
	//Calculates the average of the student marks.
	double calcAverage(){
		double average=0;
		for (int i=0;i<10;++i){
			average+=marks[i];
		}
		average/=10;
		return average;
	}
}

class Classroom{
	private
		Student []objStud = new Student[5]; //Object containing 5 students
		String []first=new String[5];
		String []last=new String[5];
	public
	Classroom(){
		openFileMethod(); //Open the Text File using openFileMethod
		for (int i=0;i<5;++i){
			objStud[i] = new Student(last[i],first[i]); //Create objects for the 5 students
			objStud[i].fillRandom(); //Randomize the array of marks for each student.
		}
	}
	//Calculates the class average.
	double calcAverage(){
		double class_average=0;
		for (int i=0;i<5;++i){
			class_average+=objStud[i].calcAverage();
		}
		return class_average;
	}
	//Display class average.
	void display(){
		out.printf("Class Average: %.1f",calcAverage());
	}
	//Opens Text File and put it into Array.
	void openFileMethod(){
		try{
			FileReader in = new FileReader("marks.txt");
			String[] names = new String[5];
			Scanner input = new Scanner(in);
			Scanner string = new Scanner(in).useDelimiter(" |\r\n"); //Space or Line
			for (int i=0;i<5;++i){
				first[i]=string.next();
				last[i]=string.next();
				string.nextLine();
			}
		} catch(IOException e){e.getMessage();}
	}
	void saveFileMethod(){
		try{
		//Write to a File using PrintWriter
		PrintWriter writer = new PrintWriter("marks_out.txt","utf-8");
		for (int i=0;i<5;++i){
			writer.println("======================================");
			writer.println(objStud[i].getGivenName()+" "+objStud[i].getSurname());
			writer.print("Marks: [ ");
			for (int j=0;j<10;++j){
				writer.print(Double.toString(objStud[i].getMark(j))+" ");
			}
			writer.println("]");
			writer.printf("Class Average: %.1f",objStud[i].calcAverage());
			writer.println("");
		}
		writer.close(); //Closes I/O Writer
		} catch (IOException e){e.getMessage();}
	}
}
	

