package ar.com.finit.tetris.ui.blocks.special;

import java.awt.Image;

import ar.com.finit.tetris.ui.blocks.Block;

/**
 * @author leo
 */
public class HoldBlock extends Block {

	public HoldBlock(int matrixLength, boolean[][] matrix, Image color) {
		super.matrixLength = matrixLength;
		super.matrix = matrix;
		super.color = color;
		
	}

	@Override
	public Image getColor() {
		return super.color;
	}

}
