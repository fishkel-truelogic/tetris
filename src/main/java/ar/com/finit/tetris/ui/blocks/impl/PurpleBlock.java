package ar.com.finit.tetris.ui.blocks.impl;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import ar.com.finit.tetris.ui.blocks.Block;

/**
 * @author leo
 */
public class PurpleBlock extends Block {

	public PurpleBlock() {
		super.matrixLength = 3;
		super.matrix = new boolean[super.matrixLength][super.matrixLength];
		super.matrix[0][0] = true;
		super.matrix[0][1] = true;
		super.matrix[0][2] = true;
		super.matrix[1][1] = true;
	}

	@Override
	public Image getColor() {
		if (color == null) {
			URL imgURL = getClass().getResource("/purple.png");
			ImageIcon i = new ImageIcon(imgURL);
			color = i.getImage();
		}
		return color;
	}

}
