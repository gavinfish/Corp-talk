package com.drfish.corptalk;

import java.util.Scanner;

import com.drfish.corptalk.command.Command_login;
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
		//login before start conversation with others
		System.out.print("please log in with your account: ");
		account = scanner.nextLine();
		Command_login command_login = new Command_login();
		command_login.setAccount(account);
		send(command_login);
		String message;
		//send message
		while(true){
			message = scanner.nextLine();
			System.out.print("Me: ");
			Command_speak command_speak = new Command_speak();
			command_speak.setFromAccount(account);
			command_speak.setMessage(message);
			send(command_speak);
			System.out.println(message);
		}
	}
}
