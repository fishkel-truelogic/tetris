package ar.com.finit.tetris.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import ar.com.finit.tetris.ui.blocks.Block;
import ar.com.finit.tetris.ui.blocks.special.HoldBlock;

/**
 * @author leo
 */
public class Game extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	public static final int GAME_WIDTH = 10 * Block.PIXEL_SIZE;

	public static final int GAME_HEIGHT = 22 * Block.PIXEL_SIZE;

	private int speed = 1000;

	private Timer timer;

	private Block block;

	private Image[][] lines;

	private SideBar sideBar;

	private boolean pause;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (!pause) {
			paintLines(g);
			paintShadow(g);
			paintBlock(g);
			timer.start();
		} else {
			paintPause(g);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	private void paintPause(Graphics g) {
		String msg = "-- PAUSE --";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);
		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (GAME_WIDTH - metr.stringWidth(msg)) / 2, GAME_HEIGHT / 2 - 2 * Block.PIXEL_SIZE);

	}

	private void paintShadow(Graphics g) {
		int x = 0;
		int y = 0;
		while (canShadowMoveDown()) {
			block.moveShadowDown();
		}
		for (int i = 0; i < block.getMatrixLength(); i++) {
			for (int j = 0; j < block.getMatrixLength(); j++) {
				if (block.getMatrix()[i][j]) {
					x = block.getShadowX() + (j + 1) * Block.PIXEL_SIZE;
					y = block.getShadowY() + (i + 1) * Block.PIXEL_SIZE;
					g.drawImage(block.getShadowColor(), x, y, this);
				}
			}
		}
	}

	private void paintLines(Graphics g) {
		int x = 0;
		int y = 0;
		for (int i = 0; i < 22; i++) {
			for (int j = 0; j < 10; j++) {
				x = j * Block.PIXEL_SIZE;
				y = (i + 2) * Block.PIXEL_SIZE;
				g.drawImage(lines[i][j], x, y, this);
			}
		}

	}

	private void paintBlock(Graphics g) {
		int x = 0;
		int y = 0;
		for (int i = 0; i < block.getMatrixLength(); i++) {
			for (int j = 0; j < block.getMatrixLength(); j++) {
				if (block.getMatrix()[i][j]) {
					x = block.getX() + (j + 1) * Block.PIXEL_SIZE;
					y = block.getY() + (i + 1) * Block.PIXEL_SIZE;
					g.drawImage(block.getColor(), x, y, this);
				}
			}
		}
	}

	private void clearLines() {
		boolean isLine = true;
		for (int i = 21; i >= 0; i--) {
			isLine = true;
			for (int j = 0; j < 10; j++) {
				if (lines[i][j] == null) {
					isLine = false;
					break;
				}
			}
			if (isLine) {
				sideBar.setPoints(sideBar.getPoints() + 10);
				if (sideBar.getPoints() % 100 == 0) {
					sideBar.setLevel(sideBar.getLevel() + 1);
					speed = speed - 100;
					timer.setInitialDelay(speed);
					sideBar.repaint();
				}

				for (int k = i - 1; k >= 0; k--) {
					for (int j = 0; j < 10; j++) {
						lines[k + 1][j] = lines[k][j];
					}
				}
				for (int j = 0; j < 10; j++) {
					lines[0][j] = null;
				}
				i++;
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		timer.stop();
		if (canMoveDown()) {
			block.moveDown();
		} else {
			putBlockInLines();
			clearLines();
			nextBlock();
		}
		repaint();
	}

	private void nextBlock() {
		block = sideBar.getNext();
		sideBar.setNext(sideBar.nextBlock());
		sideBar.repaint();
	}

	public Game(SideBar sideBar) {
		super(new FlowLayout(FlowLayout.LEFT));
		lines = initializeLines();
		this.sideBar = sideBar;
		nextBlock();
		addKeyListener(new TetrisKeyListener(this));
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(Game.GAME_WIDTH + 1, Game.GAME_HEIGHT + 41));
		setFocusable(true);
		timer = new Timer(speed, this);
		timer.start();
	}

	private Image[][] initializeLines() {
		Image[][] ret = new Image[22][10];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				ret[i][j] = null;
			}
		}
		return ret;
	}

	private class TetrisKeyListener extends KeyAdapter {

		private static final int SPACE_BAR = 32;
		private static final int SHIFT = 16;
		private Game game;

		public TetrisKeyListener(Game game) {
			this.game = game;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();

			if (key == KeyEvent.VK_ENTER) {
				game.pauseUnpause();
			} else {
				if (game.isPause())
					game.pauseUnpause();

				if (key == KeyEvent.VK_LEFT)
					game.onKeyLeft();

				if (key == KeyEvent.VK_RIGHT)
					game.onKeyRight();

				if (key == KeyEvent.VK_UP)
					game.onKeyUp();

				if (key == KeyEvent.VK_DOWN)
					game.onKeyDown();

				if (key == SPACE_BAR)
					game.onKeySpaceBar();

				if (key == SHIFT)
					game.onKeyShift();
			}
		}
	}

	public void onKeyLeft() {
		block.setShadowY(block.getY());
		if (canMoveLeft()) {
			block.moveLeft();
		}
		repaint();
	}

	public void pauseUnpause() {
		if (pause) {
			timer.start();
		} else {
			timer.stop();
		}
		pause = !pause;
		repaint();
	}

	public void onKeyShift() {
		timer.stop();
		if (sideBar.getHold() != null) {
			if (!sideBar.isLockHold()) {
				sideBar.setHoldBuffer(new HoldBlock(block.getMatrixLength(), block.getMatrix(), block.getColor()));
				block = sideBar.getHold();
				sideBar.setHold(sideBar.getHoldBuffer());
			}
		} else {
			sideBar.setHold(new HoldBlock(block.getMatrixLength(), block.getMatrix(), block.getColor()));
			nextBlock();
		}
		sideBar.setLockHold(true);
		sideBar.repaint();
		repaint();
	}

	public void onKeySpaceBar() {
		timer.stop();
		block.setShadowY(block.getY());
		while (canMoveDown()) {
			block.moveDown();
		}
		putBlockInLines();
		clearLines();
		nextBlock();
		repaint();
	}

	public void onKeyDown() {
		timer.stop();
		block.setShadowY(block.getY());
		if (canMoveDown()) {
			block.moveDown();
		}
		repaint();
	}

	public void onKeyUp() {
		block.setShadowY(block.getY());
		boolean[][] rotated = block.rotate();
		if (fitsInLines(rotated)) {
			block.setMatrix(rotated);
		}
		repaint();
	}

	private boolean fitsInLines(boolean[][] rotated) {
		int x = 0;
		int y = 0;
		for (int i = 0; i < block.getMatrixLength(); i++) {
			for (int j = 0; j < block.getMatrixLength(); j++) {
				if (rotated[i][j]) {
					if (block.getX() + j * Block.PIXEL_SIZE < 0) {
						return forceFitsLeft(rotated);
					}
					if (block.getX() + j * Block.PIXEL_SIZE > 160) {
						return forceFitsRight(rotated);
					}
					y = block.getY() / Block.PIXEL_SIZE + i;
					x = (block.getX() / Block.PIXEL_SIZE) + j + 1;
					if (lines[y][x] != null) {
						if (linesAreDown(x, y)) {
							return forceFitsDown(rotated);
						} else {
							return false;
						}
					}
				}
			}
		}
		return true;
	}


	private boolean linesAreDown(int x, int y) {
		for (int i = 0; i < block.getMatrixLength(); i++) {
			for (int j = 0; j < block.getMatrixLength(); j++) {
				if (block.getMatrix()[i][j]) {
					if (y < block.getY() / Block.PIXEL_SIZE + i) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean forceFitsDown(boolean[][] rotated) {
		int y = block.getY();
		int x = block.getX();
		boolean fits = false;
		while (!fits) {
			fits = true;
			y -= Block.PIXEL_SIZE;
			for (int i = 0; i < block.getMatrixLength(); i++) {
				for (int j = 0; j < block.getMatrixLength(); j++) {
					if (rotated[i][j]) {
						int yy = ((y + i * Block.PIXEL_SIZE) / Block.PIXEL_SIZE);
						int xx = (x / Block.PIXEL_SIZE) + j + 1;
						if (lines[yy][xx] != null) {
							fits = false;
						}
					}
				}
			}
		}
		if (fits) {
			block.setY(y + Block.PIXEL_SIZE);
			block.setShadowY(y + Block.PIXEL_SIZE);
		}
		return fits;
	}

	private boolean forceFitsRight(boolean[][] rotated) {
		int x = block.getX();
		boolean fits = false;
		while (block.getX() + block.getMatrixLength() * Block.PIXEL_SIZE  > 160 && !fits) {
			fits = true;
			x -= Block.PIXEL_SIZE;
			for (int i = 0; i < block.getMatrixLength(); i++) {
				for (int j = 0; j < block.getMatrixLength(); j++) {
					if (rotated[i][j]) {
						if (x + j * Block.PIXEL_SIZE > 160) {
							fits = false;
						}
						int y = ((block.getY() + i * Block.PIXEL_SIZE) / Block.PIXEL_SIZE);
						int xx = (x / Block.PIXEL_SIZE) + j + 1;
						if (lines[y][xx] != null) {
							return false;
						}
					}
				}
			}
		}
		if (fits) {
			block.setX(x);
			block.setShadowX(x);
		}
		return fits;
	}

	private boolean forceFitsLeft(boolean[][] rotated) {
		int x = block.getX();
		boolean fits = false;
		while (block.getX() < 0 && !fits) {
			fits = true;
			x += Block.PIXEL_SIZE;
			for (int i = 0; i < block.getMatrixLength(); i++) {
				for (int j = 0; j < block.getMatrixLength(); j++) {
					if (rotated[i][j]) {
						if (x + j * Block.PIXEL_SIZE < 0) {
							fits = false;
						}
						int y = ((block.getY() + i * Block.PIXEL_SIZE) / Block.PIXEL_SIZE);
						int xx = (x / Block.PIXEL_SIZE) + j + 1;
						if (lines[y][xx] != null) {
							return false;
						}
					}
				}
			}
		}
		if (fits) {
			block.setX(x - Block.PIXEL_SIZE);
			block.setShadowX(x - Block.PIXEL_SIZE);
		}
		return fits;
	}

	public void onKeyRight() {
		block.setShadowY(block.getY());
		if (canMoveRight()) {
			block.moveRight();
		}
		repaint();
	}

	private boolean canMoveLeft() {
		int x = 0;
		int y = 0;
		for (int i = 0; i < block.getMatrixLength(); i++) {
			for (int j = 0; j < block.getMatrixLength(); j++) {
				if (block.getMatrix()[i][j]) {
					if (block.getX() + j * Block.PIXEL_SIZE < 0) {
						return false;
					}
					y = ((block.getY() + i * Block.PIXEL_SIZE) / Block.PIXEL_SIZE) - 1;
					x = (block.getX() / Block.PIXEL_SIZE) + j;
					if (x >= 0 && y >= 0) {
						if (lines[y][x] != null) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private boolean canMoveRight() {
		int x = 0;
		int y = 0;
		for (int i = 0; i < block.getMatrixLength(); i++) {
			for (int j = 0; j < block.getMatrixLength(); j++) {
				if (block.getMatrix()[i][j]) {
					if (block.getX() + j * Block.PIXEL_SIZE >= 160) {
						return false;
					}
					y = ((block.getY() + i * Block.PIXEL_SIZE) / Block.PIXEL_SIZE) - 1;
					x = (block.getX() / Block.PIXEL_SIZE) + j + 2;
					if (x >= 0 && y >= 0) {
						if (lines[y][x] != null) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private boolean canMoveDown() {
		int x = 0;
		int y = 0;
		for (int i = 0; i < block.getMatrixLength(); i++) {
			for (int j = 0; j < block.getMatrixLength(); j++) {
				if (block.getMatrix()[i][j]) {
					if (block.getY() + i * Block.PIXEL_SIZE >= GAME_HEIGHT - 1) {
						return false;
					}
					y = ((block.getY() + i * Block.PIXEL_SIZE) / Block.PIXEL_SIZE);
					x = (block.getX() / Block.PIXEL_SIZE) + j + 1;
					if (x >= 0 && y >= 0) {
						if (lines[y][x] != null) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private boolean canShadowMoveDown() {
		int x = 0;
		int y = 0;
		for (int i = 0; i < block.getMatrixLength(); i++) {
			for (int j = 0; j < block.getMatrixLength(); j++) {
				if (block.getMatrix()[i][j]) {
					if (block.getShadowY() + i * Block.PIXEL_SIZE >= GAME_HEIGHT - 1) {
						return false;
					}
					y = ((block.getShadowY() + i * Block.PIXEL_SIZE) / Block.PIXEL_SIZE);
					x = (block.getShadowX() / Block.PIXEL_SIZE) + j + 1;
					if (x >= 0 && y >= 0) {
						if (lines[y][x] != null) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void putBlockInLines() {
		int x = 0;
		int y = 0;
		for (int i = 0; i < block.getMatrixLength(); i++) {
			for (int j = 0; j < block.getMatrixLength(); j++) {
				if (block.getMatrix()[i][j]) {
					y = ((block.getY() + i * Block.PIXEL_SIZE) / Block.PIXEL_SIZE) - 1;
					x = (block.getX() / Block.PIXEL_SIZE) + j + 1;
					lines[y][x] = block.getColor();
				}
			}
		}
		sideBar.setLockHold(false);
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

}
