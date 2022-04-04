package se.tetris.main;

import se.tetris.component.Board;
import se.tetris.component.Start;

public class Tetris {

	public static void main(String[] args) {
		
		Start startView = new Start();
		startView.setSize(400, 600);
		startView.setVisible(true);
		
		//Board main = new Board();
		//main.setSize(400, 600);
		//main.setVisible(true);
	}
}