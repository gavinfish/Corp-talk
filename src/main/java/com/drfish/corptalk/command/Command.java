package com.drfish.corptalk.command;

import java.io.Serializable;

public abstract class Command implements Serializable{
	private static final long serialVersionUID = 6767476886371193000L;

	public abstract void execute();
}
