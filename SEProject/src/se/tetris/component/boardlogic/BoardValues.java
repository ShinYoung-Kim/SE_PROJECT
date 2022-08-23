package se.tetris.component.boardlogic;

import se.tetris.data.DBCalls;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class BoardValues {
    DBCalls dataCalls = new DBCalls();
    public final int mode = dataCalls.getLevelSetting();
    private int eraseCnt = 0;
    private static BoardValues instance = new BoardValues();
    private CompoundBorder border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 10),
            BorderFactory.createLineBorder(Color.DARK_GRAY, 5));

    public static BoardValues getInstance() {
        return instance;
    }
    public void increaseEraseCnt() {
        eraseCnt++;
    }
    public int getEraseCnt() {
        return this.eraseCnt;
    }
    public CompoundBorder getBorder() {
        return border;
    }
}
