package ar.com.finit.tetris;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.io.IOException;
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

	
	
	public App(String ip, String argServer) throws IOException {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
		SideBar sideBar = new SideBar();
		panel.setVisible(true);
		add(panel);
		panel.add(new Game(sideBar, ip, argServer));
		panel.add(sideBar);
		setTitle("Tetris Leo Fini");
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		final String argServer = args[1];
		final String ip = args[0];
		
		
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				JFrame frame;
				try {
					frame = new App(ip, argServer);
					frame.setVisible(true);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			
		});
	}
}
