package se.tetris.component.boardlogic;

import se.tetris.component.boardui.BoardLevelPanel;
import se.tetris.component.boardui.BoardScorePanel;

public class DifficultyEasyStrategy implements DifficultyStrategy{
    private int interval;
    private int level;

    @Override
    public int getInterval(int blockNumber, int eraseCnt, BoardLevelPanel boardLevelPanel, BoardScorePanel boardScorePanel) {
        //생성
        if (blockNumber == 30 || blockNumber == 60 || blockNumber == 80 || blockNumber == 100 || blockNumber == 120) {
                boardScorePanel.getScore(11 * eraseCnt, "std");
                boardScorePanel.setScore();
        }
        //삭제
            if (eraseCnt < 5 && eraseCnt >= 0) {
                interval = 2000;
                level = 1;
            } else if (eraseCnt < 10 && eraseCnt >= 5) {
                interval = (int) (2000 * 0.92);
                level = 2;
            } else if (eraseCnt < 15 && eraseCnt >= 10) {
                interval = (int) (2000 * 0.92 * 0.92);
                level = 3;
            } else if (eraseCnt < 20 && eraseCnt >= 15) {
                interval = (int) (2000 * 0.92 * 0.92 * 0.92);
                level = 4;
            } else if (eraseCnt < 25 && eraseCnt >= 20) {
                interval = (int) (2000 * 0.92 * 0.92 * 0.92 * 0.92);
                level = 5;
            } else if (eraseCnt < 30 && eraseCnt >= 25) {
                interval = (int) (2000 * 0.92 * 0.92 * 0.92 * 0.92 * 0.92);
                level = 6;
            } else if (eraseCnt >= 30) {
                interval = (int) (2000 * 0.92 * 0.92 * 0.92 * 0.92 * 0.92 * 0.92);
                level = 7;
            }
            //interval = (int) (2000 * Math.pow(0.92, level - 1));
        boardLevelPanel.changeLevelLb(level);
        return interval;
    }
}
