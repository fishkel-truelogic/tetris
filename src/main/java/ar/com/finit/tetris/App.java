package ar.com.finit.tetris;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.io.IOException;

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

	public App() throws IOException {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
		SideBar sideBar = new SideBar();
		panel.setVisible(true);
		add(panel);
		panel.add(new Game(sideBar));
		panel.add(sideBar);
		setTitle("Tetris Leo Fini");
		pack();
		setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				JFrame frame;
				try {
					frame = new App();
					frame.setVisible(true);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			
		});
	}
}
