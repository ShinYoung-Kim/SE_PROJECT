package se.tetris.blocks;

import java.awt.Color;

public class ZBlock extends Block {

	public ZBlock() {
		this.shape = new int[][] {
				{1, 1, 0},
				{0, 1, 1}
		};
		color = Color.RED;
		colorBlind = new Color(99, 106, 141);
	}
}