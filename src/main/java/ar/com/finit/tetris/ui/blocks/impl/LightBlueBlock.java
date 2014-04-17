package ar.com.finit.tetris.ui.blocks.impl;

import java.awt.Image;

import javax.swing.ImageIcon;

import ar.com.finit.tetris.ui.blocks.Block;

/**
 * @author leo
 */
public class LightBlueBlock extends Block {

	public LightBlueBlock() {
		super.matrixLength = 4;
		super.matrix = new boolean[super.matrixLength][super.matrixLength];
		super.matrix[0][1] = true;
		super.matrix[1][1] = true;
		super.matrix[2][1] = true;
		super.matrix[3][1] = true;
	}
	
	@Override
	public Image getColor() {
		if (color == null) {
			ImageIcon i = new ImageIcon("lightBlue.png");
			color = i.getImage();
		}
		return color;
	}

}
