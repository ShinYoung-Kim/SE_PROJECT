package se.tetris.main;

import java.awt.Dimension;
import java.awt.Toolkit;

import se.tetris.common.Navigation;
import se.tetris.component.Board;
import se.tetris.component.Start;
import se.tetris.data.*;
import se.tetris.component.Score;
import se.tetris.setting.*;

public class Tetris {
	protected String path = System.getProperty("user.dir");
	protected String url = "jdbc:sqlite:./lib/tetris.db";

	public static void main(String[] args) {
		DBConnectionManager data = new DBConnectionManager();

		Navigation.getInstance().navigate(Navigation.START_SCREEN);

		data.connect();
		data.createNewTable();
	}
}