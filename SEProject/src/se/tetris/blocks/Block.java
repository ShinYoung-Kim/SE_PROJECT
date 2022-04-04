package se.tetris.blocks;

import java.awt.Color;

public abstract class Block {
		
	protected int[][] shape;
	protected Color color;
	protected Color colorBlind;
	protected int r;
    protected int numOfBlockType;
	
	public Block() {
		shape = new int[][]{ 
			{1, 1}, 
			{1, 1},
		};
		color = Color.YELLOW;
	}
	
	public int getShape(int x, int y) {
		return shape[y][x];
	}
	
	public Color getColor() {
		return color;
	}
	
	public Color getColorBlind() {
		return colorBlind;
	}
	
	public void rotate() {
		r = (r+1) % numOfBlockType;
	}
	
	public int height() {
		return shape.length;
	}
	
	public int width() {
		if(shape.length > 0)
			return shape[0].length;
		return 0;
	}
}
