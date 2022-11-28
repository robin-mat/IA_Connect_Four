package model;

import java.util.Scanner;

import java.util.UUID;
import util.Constants;

public class Player {
	public String name;
	public String uuid;

	public Scanner scanner;

	public Player(String name, Scanner scanner){
		this.name = name;
		this.scanner = scanner;
		this.uuid = UUID.randomUUID().toString();
	}
	public Player(){
	}

	public int play(){
		System.out.println("TODO, choix (1 à 7):");
    	String choice = this.scanner.nextLine();
    	return Integer.parseInt(choice);
	}

	public String getName(){
		return this.name;
	}
	public String getUuid(){
		return this.uuid;
	}
}