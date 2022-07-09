package se.tetris.blocks;

import java.awt.Color;

public class OBlock extends Block {

	public OBlock() {
		shape = new int[][] {
				{4, 4},
				{4, 4}
		};
		color = Color.YELLOW;
		colorBlind = new Color(187, 190, 242);
	}
}