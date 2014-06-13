package ar.com.finit.tetris.lan;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import ar.com.finit.tetris.ui.Game;

public class Da extends Thread {

	private String ip;
	private String argServer;
	private Game game;

	public Da(String ip, String argServer, Game game) {
		this.ip = ip;
		this.argServer = argServer;
		this.game = game;
	}

	@SuppressWarnings("resource")
	public void run() {
		try {

			final Socket client;
			if (argServer != null && !argServer.equals("client")) {
				ServerSocket server = new ServerSocket(8888);
				client = server.accept();
			} else {
				client = new Socket(ip, 8888);
			}
			
			game.setOut(new DataOutputStream(client.getOutputStream()));

			while (true) {
				Scanner scan;
				scan = new Scanner(client.getInputStream());

				while (scan.hasNextLine()) {
					game.addGarbageLine();
					break;
				}

			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
