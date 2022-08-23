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
            if (eraseCnt < 30) {
                level = (int) (eraseCnt / 5) + 1;
            } else {
                level = 7;
            }
            interval = (int) (2000 * Math.pow(0.92, level - 1));
        boardLevelPanel.changeLevelLb(level);
        return interval;
    }
}
