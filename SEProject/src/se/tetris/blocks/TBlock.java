package se.tetris.blocks;

import java.awt.Color;

public class TBlock extends Block {

	public TBlock() {
		shape = new int[][] {
				{0, 6, 0},
				{6, 6, 6}
		};
		color = Color.MAGENTA;
		colorBlind = new Color(154, 127, 112);
	}
}