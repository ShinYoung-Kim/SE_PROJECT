package se.tetris.main;

import se.tetris.component.Start;
import se.tetris.setting.SettingCode;
import javax.swing.*;

public class Tetris extends JFrame {
	public static void main(String[] args) {
		Start.start.setSize(400, 600);
		Start.start.setVisible(true);

		//Board main = new Board();
		//main.setSize(400, 600);
		//main.setVisible(true);
	}
}