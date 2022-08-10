package se.tetris.component.boardlogic;

import se.tetris.component.boardui.BoardLevelPanel;
import se.tetris.component.boardui.BoardNextArea;
import se.tetris.component.boardui.BoardScorePanel;
import se.tetris.component.boardui.BoardTetrisArea;

public class BoardLocator {
    private static BoardLocator instance;

    private final BoardNextArea boardNextArea = new BoardNextArea();
    private final BoardTetrisArea boardTetrisArea = new BoardTetrisArea();
    private final BoardScorePanel scorePanel = new BoardScorePanel();
    private final BoardLevelPanel levelPanel = new BoardLevelPanel();

    private final BoardTimer boardTimer = new BoardTimer();

    public static BoardLocator getInstance() {
        if (instance == null) {
            instance = new BoardLocator();
        }
        return instance;
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

    public BoardTimer getBoardTimer() {
        return boardTimer;
    }
    /*
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

    public void setBoardTimer(BoardTimer boardTimer) {
        this.boardTimer = boardTimer;
    }

     */
}
