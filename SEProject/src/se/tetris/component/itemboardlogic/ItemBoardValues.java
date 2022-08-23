package se.tetris.component.itemboardlogic;

import se.tetris.component.boardlogic.BoardValues;
import se.tetris.data.DBCalls;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class ItemBoardValues {
    DBCalls dataCalls = new DBCalls();
    public final int mode = dataCalls.getLevelSetting();
    private int eraseCnt = 0;
    private int itemType;
    private static ItemBoardValues instance = new ItemBoardValues();
    CompoundBorder border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 10),
            BorderFactory.createLineBorder(Color.DARK_GRAY, 5));

    public static ItemBoardValues getInstance() {
        return instance;
    }

    public void increaseEraseCnt() {
        eraseCnt++;
    }

    public int getEraseCnt() {
        return this.eraseCnt;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int changeItemType) {
        itemType = changeItemType;
    }

    public CompoundBorder getBorder() {
        return border;
    }
}
