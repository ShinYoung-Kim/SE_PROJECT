package se.tetris.main;

import se.tetris.component.Board;

public class Tetris {

	public static void main(String[] args) {
		Board main = new Board();
		main.setSize(400, 600);
		main.setVisible(true);
	}
}