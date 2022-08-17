package se.tetris.component.boardlogic;

import se.tetris.component.boardui.BoardLevelPanel;
import se.tetris.component.boardui.BoardNextArea;
import se.tetris.component.boardui.BoardScorePanel;
import se.tetris.component.boardui.BoardTetrisArea;

public class BoardLocator {
    private static BoardLocator instance;

    private BoardNextArea boardNextArea;
    private BoardTetrisArea boardTetrisArea;
    private BoardScorePanel scorePanel;
    private BoardLevelPanel levelPanel;

    private BoardTimer boardTimer;

    public static BoardLocator getInstance() {
        if (instance == null) {
            instance = new BoardLocator(new BoardNextArea(), new BoardTetrisArea(), new BoardScorePanel(), new BoardLevelPanel());
        }
        return instance;
    }

    public BoardLocator(BoardNextArea next, BoardTetrisArea tetris, BoardScorePanel score, BoardLevelPanel level) {
        this.boardNextArea = next;
        this.boardTetrisArea = tetris;
        this.scorePanel = score;
        this.levelPanel = level;
    }

    public static void init(BoardLocator boardLocator) {
        instance = boardLocator;
    }

    public BoardNextArea getBoardNextArea() {
        return boardNextArea;
    }

    public BoardTetrisArea getBoardTetrisArea() {
        return boardTetrisArea;
    }

    public BoardScorePanel getScorePanel() {
        return scorePanel;
    }

    public BoardLevelPanel getLevelPanel() {
        return levelPanel;
    }

    public void setBoardNextArea(BoardNextArea boardNextArea) {
        this.boardNextArea = boardNextArea;
    }

    public void setBoardTetrisArea(BoardTetrisArea boardTetrisArea) {
        this.boardTetrisArea = boardTetrisArea;
    }

    public void setScorePanel(BoardScorePanel scorePanel) {
        this.scorePanel = scorePanel;
    }

    public void setLevelPanel(BoardLevelPanel levelPanel) {
        this.levelPanel = levelPanel;
    }

    public BoardTimer getBoardTimer() {
        return boardTimer;
    }

    public void setBoardTimer(BoardTimer boardTimer) {
        this.boardTimer = boardTimer;
    }
}
