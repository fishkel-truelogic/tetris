package ar.com.finit.tetris.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import ar.com.finit.tetris.ui.blocks.Block;

/**
 * @author leo
 */
public class SideBar extends JPanel {

	private static final long serialVersionUID = -7656674675908441599L;

	public static final int SIDE_BAR_WIDTH = Block.PIXEL_SIZE * 6;

	private static final int NEXT_X = Block.PIXEL_SIZE;

	private static final int NEXT_Y = 40;

	private static final int POINTS_Y = NEXT_Y + Block.PIXEL_SIZE * 6;

	private static final int LEVEL_Y = POINTS_Y + Block.PIXEL_SIZE * 2;

	private static final int HOLD_Y = LEVEL_Y + Block.PIXEL_SIZE * 4;

	private static final int HOLD_X = Block.PIXEL_SIZE;
;

	private Block next;

	private Block hold;
	
	private Block holdBuffer;
	
	private int points;
	
	private int level;
	
	private boolean lockHold;
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintNext(g);
		paintPointsAndLevel(g);
		paintHold(g);
	}
	
	private void paintHold(Graphics g) {
		int x = 0;
		int y = 0;
		String msg = "Hold";
		Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (SIDE_BAR_WIDTH - metr.stringWidth(msg)) / 2, HOLD_Y - 2 * Block.PIXEL_SIZE);
        if (hold != null) {
        	for (int i = 0; i < hold.getMatrixLength(); i++) {
        		for (int j = 0; j < hold.getMatrixLength(); j++) {
        			if (hold.getMatrix()[i][j]) {
        				x = HOLD_X + (j + 1) * Block.PIXEL_SIZE;
        				y = HOLD_Y + (i + 1) * Block.PIXEL_SIZE;
        				g.drawImage(hold.getColor(), x, y, this);
        			}
        		}
        	}
        }
	}

	private void paintPointsAndLevel(Graphics g) {
		String msg = "Points: " + this.getPoints();
		Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (SIDE_BAR_WIDTH - metr.stringWidth(msg)) / 2, POINTS_Y);
        msg = "Level: " + this.getLevel();
        g.drawString(msg, (SIDE_BAR_WIDTH - metr.stringWidth(msg)) / 2, LEVEL_Y);
		
	}

	private void paintNext(Graphics g) {
		int x = 0;
		int y = 0;
		Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
		String msg = "Next";
		g.drawString(msg, (SIDE_BAR_WIDTH - metr.stringWidth(msg)) / 2, 20);
		for (int i = 0; i < next.getMatrixLength(); i++) {
			for (int j = 0; j < next.getMatrixLength(); j++) {
				if (next.getMatrix()[i][j]) {
					x = NEXT_X + (j + 1) * Block.PIXEL_SIZE;
					y = NEXT_Y + (i + 1) * Block.PIXEL_SIZE;
					g.drawImage(next.getColor(), x, y, this);
				}
			}
		}
		
	}

	public SideBar() {
		super(new FlowLayout(FlowLayout.RIGHT));
		this.points = 0;
		this.level = 0;
		this.next = nextBlock();
		setBackground(Color.BLACK);
		setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.PINK));
		setPreferredSize(new Dimension(SIDE_BAR_WIDTH, Game.GAME_HEIGHT + 7));
        setVisible(true);
		repaint();
	}

	public Block nextBlock() {
		try {
			return Block.getRandomBlock(); 
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public Block getNext() {
		return next;
	}

	public void setNext(Block next) {
		this.next = next;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Block getHold() {
		return hold;
	}

	public void setHold(Block hold) {
		this.hold = hold;
	}

	public boolean isLockHold() {
		return lockHold;
	}

	public void setLockHold(boolean blockHold) {
		this.lockHold = blockHold;
	}

	public Block getHoldBuffer() {
		return holdBuffer;
	}

	public void setHoldBuffer(Block holdBuffer) {
		this.holdBuffer = holdBuffer;
	}
	
	
}
