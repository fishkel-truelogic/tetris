package ar.com.finit.tetris.lan;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import ar.com.finit.tetris.ui.Game;

public class Da extends Thread {

	private Socket socket;
	private Game game;

	public Da(Socket socket, Game game) {
		this.socket = socket;
		this.game = game;
	}

	public void run() {
		try {
			Scanner scan;
			scan = new Scanner(socket.getInputStream());
		
			while (scan.hasNextLine()) {
				game.addGarbageLines(1);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
