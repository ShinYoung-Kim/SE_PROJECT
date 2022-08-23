package se.tetris.component.itemboardlogic;

import se.tetris.component.boardlogic.BoardTimer;
import se.tetris.component.boardui.BoardLevelPanel;
import se.tetris.component.boardui.BoardNextArea;
import se.tetris.component.boardui.BoardScorePanel;
import se.tetris.component.boardui.BoardTetrisArea;
import se.tetris.component.itemboardui.ItemBoardLevelPanel;
import se.tetris.component.itemboardui.ItemBoardNextTextPane;
import se.tetris.component.itemboardui.ItemBoardScorePanel;
import se.tetris.component.itemboardui.ItemBoardTetrisTextPane;

public class ItemBoardLocator {
    private static ItemBoardLocator instance;

    private ItemBoardNextTextPane boardNextArea;
    private ItemBoardTetrisTextPane boardTetrisArea;
    private ItemBoardScorePanel scorePanel;
    private ItemBoardLevelPanel levelPanel;

    private ItemBoardTimer boardTimer;

    public static ItemBoardLocator getInstance() {
        if (instance == null) {
            instance = new ItemBoardLocator(new ItemBoardNextTextPane(), new ItemBoardTetrisTextPane(), new ItemBoardScorePanel(), new ItemBoardLevelPanel());
        }
        return instance;
    }

    public ItemBoardLocator(ItemBoardNextTextPane next, ItemBoardTetrisTextPane tetris, ItemBoardScorePanel score, ItemBoardLevelPanel level) {
        this.boardNextArea = next;
        this.boardTetrisArea = tetris;
        this.scorePanel = score;
        this.levelPanel = level;
    }

    public static void init(ItemBoardLocator boardLocator) {
        instance = boardLocator;
    }

    public ItemBoardNextTextPane getBoardNextArea() {
        return boardNextArea;
    }

    public ItemBoardTetrisTextPane getBoardTetrisArea() {
        return boardTetrisArea;
    }

    public ItemBoardScorePanel getScorePanel() {
        return scorePanel;
    }

    public ItemBoardLevelPanel getLevelPanel() {
        return levelPanel;
    }

    public void setBoardNextArea(ItemBoardNextTextPane boardNextArea) {
        this.boardNextArea = boardNextArea;
    }

    public void setBoardTetrisArea(ItemBoardTetrisTextPane boardTetrisArea) {
        this.boardTetrisArea = boardTetrisArea;
    }

    public void setScorePanel(ItemBoardScorePanel scorePanel) {
        this.scorePanel = scorePanel;
    }

    public void setLevelPanel(ItemBoardLevelPanel levelPanel) {
        this.levelPanel = levelPanel;
    }

    public ItemBoardTimer getBoardTimer() {
        return boardTimer;
    }

    public void setBoardTimer(ItemBoardTimer boardTimer) {
        this.boardTimer = boardTimer;
    }
}
