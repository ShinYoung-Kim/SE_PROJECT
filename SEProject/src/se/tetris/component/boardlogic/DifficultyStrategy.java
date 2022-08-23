package se.tetris.component.boardlogic;

import se.tetris.component.boardui.BoardLevelPanel;
import se.tetris.component.boardui.BoardScorePanel;

public interface DifficultyStrategy {
    public int getInterval(int blockNumber, int eraseCnt, BoardLevelPanel boardLevelPanel, BoardScorePanel boardScorePanel);
}
