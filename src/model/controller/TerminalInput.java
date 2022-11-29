package controller;

import java.util.Scanner;

public class TerminalInput{
	public Scanner scanner;

	public TerminalInput(Scanner scanner){
		this.scanner = scanner;
	}

	public int askQuestionInt(String q){
		System.out.println(q);
    	String choice = this.scanner.nextLine();
    	return (Integer.parseInt(choice));
	}

	public String askQuestionString(String q){
		System.out.println(q);
    	return this.scanner.nextLine();
	}
}