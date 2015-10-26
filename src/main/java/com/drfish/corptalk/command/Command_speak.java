package com.drfish.corptalk.command;

import java.io.Serializable;

public class Command_speak extends Command implements Serializable {
	private static final long serialVersionUID = 235385515675463079L;
	
	private String fromAccount;
	private String toAccount;
	private String message;

	@Override
	public void execute() {
		System.out.println(fromAccount+": "+message);
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
