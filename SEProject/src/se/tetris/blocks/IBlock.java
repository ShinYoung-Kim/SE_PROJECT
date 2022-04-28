package se.tetris.blocks;

import java.awt.Color;

public class IBlock extends Block {

	public IBlock() {
		this.shape = new int[][] {
				{1, 1, 1, 1}
		};
		color = Color.CYAN;
		colorBlind = new Color(0, 58, 97);
	}

}