package se.tetris.component.boardlogic;

import se.tetris.component.battlemodeui.InnerBoardLevelPanel;
import se.tetris.component.battlemodeui.InnerBoardScorePanel;
import se.tetris.component.boardui.BoardLevelPanel;
import se.tetris.component.boardui.BoardScorePanel;

public interface DifficultyStrategy {
    public int getInterval(int blockNumber, int eraseCnt, BoardLevelPanel boardLevelPanel, BoardScorePanel boardScorePanel);
    public int getInterval(int blockNumber, int eraseCnt, InnerBoardLevelPanel boardLevelPanel, InnerBoardScorePanel boardScorePanel);
}
