package se.tetris.component.battlemodelogic;

import se.tetris.component.battlemodeui.*;
import se.tetris.component.boardlogic.BoardLocator;
import se.tetris.component.boardlogic.BoardTimer;
import se.tetris.component.boardui.BoardLevelPanel;
import se.tetris.component.boardui.BoardNextArea;
import se.tetris.component.boardui.BoardScorePanel;
import se.tetris.component.boardui.BoardTetrisArea;

public class BattleBoardLocator {
    private static BattleBoardLocator instance;

    private InnerBoardNextPanel boardNextArea;
    private InnerBoardTetrisPanel boardTetrisArea;
    private InnerBoardScorePanel scorePanel;
    private InnerBoardLevelPanel levelPanel;
    private InnerBoardAttackPanel attackPanel;
    private InnerBoardUI innerBoardUI;

    private BattleTimer boardTimer;

    public static BattleBoardLocator getInstance() {
        if (instance == null) {
            instance = new BattleBoardLocator(new InnerBoardNextPanel(), new InnerBoardTetrisPanel(),
                    new InnerBoardScorePanel(), new InnerBoardLevelPanel(), new InnerBoardAttackPanel());
        }
        return instance;
    }

    public BattleBoardLocator(InnerBoardNextPanel next, InnerBoardTetrisPanel tetris, InnerBoardScorePanel score,
                              InnerBoardLevelPanel level, InnerBoardAttackPanel attack) {
        this.boardNextArea = next;
        this.boardTetrisArea = tetris;
        this.scorePanel = score;
        this.levelPanel = level;
        this.attackPanel = attack;
    }

    public static void init(BattleBoardLocator boardLocator) {
        instance = boardLocator;
    }

    public InnerBoardNextPanel getBoardNextArea() {
        return boardNextArea;
    }

    public InnerBoardTetrisPanel getBoardTetrisArea() {
        return boardTetrisArea;
    }

    public InnerBoardScorePanel getScorePanel() {
        return scorePanel;
    }

    public InnerBoardLevelPanel getLevelPanel() {
        return levelPanel;
    }

    public InnerBoardAttackPanel getAttackPanel() {return attackPanel;}

    public void setBoardNextArea(InnerBoardNextPanel boardNextArea) {
        this.boardNextArea = boardNextArea;
    }

    public void setBoardTetrisArea(InnerBoardTetrisPanel boardTetrisArea) {
        this.boardTetrisArea = boardTetrisArea;
    }

    public void setScorePanel(InnerBoardScorePanel scorePanel) {
        this.scorePanel = scorePanel;
    }

    public void setLevelPanel(InnerBoardLevelPanel levelPanel) {
        this.levelPanel = levelPanel;
    }

    public BattleTimer getBoardTimer() {
        return boardTimer;
    }

    public void setBoardTimer(BattleTimer boardTimer) {
        this.boardTimer = boardTimer;
    }

    public InnerBoardUI getInnerBoardUI() {
        return innerBoardUI;
    }

    public void setInnerBoardUI(InnerBoardUI innerBoardUI) {
        this.innerBoardUI = innerBoardUI;
    }
}
