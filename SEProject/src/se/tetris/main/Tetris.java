package se.tetris.main;

import se.tetris.component.Start;
import se.tetris.data.*;
import se.tetris.component.Score;
import se.tetris.setting.*;

public class Tetris {
	protected String path = System.getProperty("user.dir");
    protected String url = "jdbc:sqlite:./lib/tetris.db";

	public static void main(String[] args) {
		DBConnectionManager data = new DBConnectionManager();

		DBCalls dataCalls = new DBCalls();

		Start startView = new Start();

		int Window = dataCalls.getWindowSetting();

		if(Window == 0) {
			startView.setSize(400, 600);
		}else if(Window == 1) {
			startView.setSize(800, 800);
		}else {
			startView.setSize(SettingCode.screenWidth, SettingCode.screenHeight);
		}


		startView.setVisible(true);



		data.connect();
		data.createNewTable();


//		SettingCode setting = new SettingCode();
//		int Window = dataCalls.getWindowSetting();
//		SettingValues.getInstance().sizeNumber = Window - 1;
//
//		Start.start.setVisible(true);
//
//		data.connect();
//		data.createNewTable();

//
//		dataCalls.getWindowSetting();
//		dataCalls.getLevelSetting();
//		dataCalls.getColorSetting();

//
//		dataCalls.get10ItemScoreData();

	}
}