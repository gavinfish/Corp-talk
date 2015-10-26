package com.drfish.corptalk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.drfish.corptalk.command.Command;
import com.drfish.corptalk.command.Command_alive;

public class Client {
	private String serverIp;
	private int port = 65432;
	private Socket socket;
	private boolean running;
	private long lastSendTime;
	ObjectOutputStream objectOut;

	private String getServerIp() {
		try {
			String encoding = "GBK";
			File file = new File("serverIp.txt");
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				lineTxt = bufferedReader.readLine();
				read.close();
				return lineTxt;
			} else {
				System.out.println("can't find the file!");
			}
		} catch (Exception e) {
			System.out.println("file reading error");
			e.printStackTrace();
		}
		return null;
	}

	public void start() {
		serverIp = getServerIp();
		if (running)
			return;
		try {
			socket = new Socket(serverIp, port);
			running = true;
			objectOut = new ObjectOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 new Thread(new SocketAction()).start();
		 new Thread(new HeartBeat()).start();
	}

	public void stop() {
		if (running)
			running = false;
	}

	public synchronized void sendObject(Object object) {
		try {
			objectOut.writeObject(object);
			objectOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class SocketAction implements Runnable {
		InputStream in;
		ObjectInputStream objectIn;

		public void run() {
			try {
				in = socket.getInputStream();
				objectIn = new ObjectInputStream(in);
				while (running) {
					if (in.available() > 0) {
						Object obj = null;
						try {
							obj = objectIn.readObject();
							Command command = (Command) obj;
							command.execute();
						} catch (Exception e) {

						}
					} else {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class HeartBeat implements Runnable {
		long checkDelay = 10;
		long keepAliveDelay = 2000;

		public void run() {
			while (running) {
				if (System.currentTimeMillis() - lastSendTime > keepAliveDelay) {
					Client.this.sendObject(new Command_alive());
					lastSendTime = System.currentTimeMillis();
				} else {
					try {
						Thread.sleep(checkDelay);
					} catch (InterruptedException e) {
						e.printStackTrace();
						Client.this.stop();
					}
				}
			}

		}
	}
}
