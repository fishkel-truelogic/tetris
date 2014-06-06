package ar.com.finit.tetris;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ar.com.finit.tetris.ui.Game;
import ar.com.finit.tetris.ui.SideBar;

/**
 * Hello world!
 * 
 */
public class App extends JFrame {

	private static final long serialVersionUID = -4105406273868764193L;

	public App(Socket client) throws IOException {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
		SideBar sideBar = new SideBar();
		panel.setVisible(true);
		add(panel);
		panel.add(new Game(sideBar, client));
		panel.add(sideBar);
		setTitle("Tetris Leo Fini");
		pack();
		setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		final Socket client;
		if (args[1] != null && !args[1].equals("client")) {
			ServerSocket server = new ServerSocket(8888);
			client = server.accept();
		} else {
			client = new Socket(args[0], 8888);
		}
		
		
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				JFrame frame;
				try {
					frame = new App(client);
					frame.setVisible(true);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			
		});
	}
}
