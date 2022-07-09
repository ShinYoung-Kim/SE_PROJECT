package se.tetris.blocks;

import java.awt.Color;

public class ZBlock extends Block {

	public ZBlock() {
		this.shape = new int[][] {
				{7, 7, 0},
				{0, 7, 7}
		};
		color = Color.RED;
		colorBlind = new Color(99, 106, 141);
	}
}