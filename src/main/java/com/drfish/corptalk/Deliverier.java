package com.drfish.corptalk;

import java.util.Scanner;

import com.drfish.corptalk.command.Command_speak;

public enum Deliverier {
	INSTANCE;
	
	private static Client client;
	private static String account;
	
	static{
		client = new Client();
		client.start();
	}
	
	private void send(Object object){
		client.sendObject(object);
	}
	
	public void startConversation(){
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("please log in with your account:");
		account = scanner.nextLine();
		String message;
		while(true){
			message = scanner.nextLine();
			System.out.print("Me: ");
			Command_speak command = new Command_speak();
			command.setFromAccount(account);
			command.setMessage(message);
			send(command);
			System.out.println(message);
		}
	}
}
