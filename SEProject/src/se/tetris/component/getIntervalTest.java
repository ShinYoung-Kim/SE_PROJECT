package se.tetris.component;

import se.tetris.setting.SettingValues;

import static org.junit.Assert.*;

public class getIntervalTest {

    @org.junit.Test
    public void getInterval() {
        Board board = new Board();

        assertEquals(board.getInterval(30, 0), 900);
        assertEquals(board.getInterval(35, 3), 900);
        assertEquals(board.getInterval(45, 5), 810);
        assertEquals(board.getInterval(60, 10), 656);

    }
}