package ar.com.finit.tetris.ui.blocks;

import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;

import org.reflections.Reflections;

import ar.com.finit.tetris.ui.Game;

/**
 * @author leo
 */
public abstract class Block {

	public static int PIXEL_SIZE = 20;

	private int x = Game.GAME_WIDTH / 2 - PIXEL_SIZE * 2;

	private int y = 0;
	
	private int shadowX = Game.GAME_WIDTH / 2 - PIXEL_SIZE * 2;
	
	private int shadowY = 0;

	protected int matrixLength;
	
	protected Image color;

	private Image shadowColor;
	
	protected boolean matrix[][];
	
	public boolean[][] rotate() {
		boolean[][] ret = new boolean[matrixLength][matrixLength];
		for (int i = 0; i < matrixLength; ++i) {
	        for (int j = 0; j < matrixLength; ++j) {
	            ret[i][j] = matrix[matrixLength - j - 1][i];
	        }
	    }
		return ret;
	}

	public void moveDown() {
		y += PIXEL_SIZE;
	}
	
	public void moveShadowDown() {
		shadowY += PIXEL_SIZE;
	}
	

	public void moveLeft() {
		x -= PIXEL_SIZE;
		shadowX -= PIXEL_SIZE;
	}

	public void moveRight() {
		x += PIXEL_SIZE;
		shadowX += PIXEL_SIZE;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public abstract Image getColor();
	
	public static Block getRandomBlock() throws InstantiationException, IllegalAccessException {
		Reflections reflections = new Reflections("ar.com.finit.tetris.ui.blocks.impl");
		Set<Class<? extends Block>> allClasses = reflections.getSubTypesOf(Block.class);
		List<Class<? extends Block>> classes = new ArrayList<Class<? extends Block>>(allClasses);
		Class<? extends Block> blockClass = classes.get(randomNumber(0, classes.size()));
		Block block = blockClass.newInstance();
		for (int i = 0; i < randomNumber(1, 5); i++) {
			block.setMatrix(block.rotate());
		}
		return block;
	}
	
	private static int randomNumber(int from, int to) {
	    if (from < to)
	        return from + new Random().nextInt(Math.abs(to - from));
	    return from - new Random().nextInt(Math.abs(to - from));
	}

	public int getMatrixLength() {
		return matrixLength;
	}

	public void setMatrixLength(int matrixLength) {
		this.matrixLength = matrixLength;
	}

	public boolean[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(boolean[][] matrix) {
		this.matrix = matrix;
	}

	public Image getShadowColor() {
		if (shadowColor == null) {
			URL imgURL = getClass().getResource("/grey.png");
			ImageIcon i = new ImageIcon(imgURL);
			shadowColor = i.getImage();
		}
		return shadowColor;
	}

	public int getShadowX() {
		return shadowX;
	}

	public void setShadowX(int shadowX) {
		this.shadowX = shadowX;
	}

	public int getShadowY() {
		return shadowY;
	}

	public void setShadowY(int shadowY) {
		this.shadowY = shadowY;
	}
	
}
