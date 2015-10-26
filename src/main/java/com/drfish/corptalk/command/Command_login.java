package com.drfish.corptalk.command;

import java.io.Serializable;

public class Command_login extends Command implements Serializable {
	private static final long serialVersionUID = -8883128920277226769L;

	private String account;

	@Override
	public void execute() {

	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
