package se.tetris.component.boardlogic;

import se.tetris.data.DBCalls;

public class BoardValues {
    DBCalls dataCalls = new DBCalls();
    public final int mode = dataCalls.getLevelSetting();
    int eraseCnt = 0;
    private static BoardValues instance = new BoardValues();

    public static BoardValues getInstance() {
        return instance;
    }

    public void increaseEraseCnt() {
        eraseCnt++;
    }

    public int getEraseCnt() {
        return this.eraseCnt;
    }
}
