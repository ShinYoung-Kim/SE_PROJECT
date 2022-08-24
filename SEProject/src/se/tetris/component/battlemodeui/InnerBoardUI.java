package se.tetris.component.battlemodeui;

import se.tetris.component.Board;
import se.tetris.component.Sizeable;
import se.tetris.component.battlemodelogic.BattleBoardLocator;
import se.tetris.component.battlemodelogic.InnerBoardUIInterface;
import se.tetris.component.battlemodelogic.ObserveInterface;
import se.tetris.component.battlemodelogic.ObservedInterface;
import se.tetris.component.boardlogic.BoardLocator;
import se.tetris.component.boardlogic.BoardTimer;
import se.tetris.component.boardlogic.RandomBlock;
import se.tetris.data.DBCalls;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class InnerBoardUI extends Board implements Sizeable, InnerBoardUIInterface, ObservedInterface {
    private String BattleMode;
    private String name = "player";

    private JPanel panel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel scoreLevelPanel;
    private InnerBoardTetrisPanel tetrisArea;
    private InnerBoardNextPanel nextArea;
    private InnerBoardScorePanel scoreArea;
    private InnerBoardLevelPanel levelArea;
    private InnerBoardAttackPanel attackArea;

    private List<ObserveInterface> panelList = new ArrayList<>();

    StringBuffer sbByAttack;
    boolean alreadyAttacked = false;
    boolean attackBoardFull = false;

    DBCalls dataCalls = new DBCalls();
    private int mode = dataCalls.getLevelSetting();
    RandomBlock randomBlock = new RandomBlock();
    public final SettingValues setting = SettingValues.getInstance();

    private BattleBoardLocator battleBoardLocator;

    BoardTimer boardTimer;

    public InnerBoardUI() {
        tetrisArea = new InnerBoardTetrisPanel();
        nextArea = new InnerBoardNextPanel();
        scoreArea = new InnerBoardScorePanel();
        levelArea = new InnerBoardLevelPanel();
        attackArea = new InnerBoardAttackPanel();

        battleBoardLocator = new BattleBoardLocator(nextArea, tetrisArea, scoreArea, levelArea, attackArea);
        addPanel(nextArea);
        addPanel(tetrisArea);
        addPanel(scoreArea);
        addPanel(levelArea);
        addPanel(attackArea);

        scoreLevelPanel = new JPanel();
        scoreLevelPanel.setLayout(new BoxLayout(scoreLevelPanel, BoxLayout.Y_AXIS));
        scoreLevelPanel.add(scoreArea);
        scoreLevelPanel.add(levelArea);

        leftPanel = new JPanel();
        leftPanel.add(tetrisArea);
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
        rightPanel.add(nextArea);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(scoreLevelPanel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(attackArea);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(leftPanel);
        panel.add(rightPanel);

        add(panel);

        //초기 세팅
        boardTimer = new BoardTimer();
        BoardLocator.getInstance().setBoardTimer(boardTimer);

        tetrisArea.placeBlock();
        tetrisArea.drawBoard();
        nextArea.placeNext();
        nextArea.drawNext();

        boardTimer.boardTimerStart();

        battleBoardLocator.setInnerBoardUI(this);

        notifyPanel();
    }

    public void reset() {
        tetrisArea.resetBoard();
        nextArea.resetNextBoard();
        tetrisArea.setX(3);
        tetrisArea.setY(0);
        tetrisArea.setCurr(randomBlock.getRandomBlock(setting.modeChoose));
        nextArea.setNext(randomBlock.getRandomBlock(setting.modeChoose));
        tetrisArea.placeBlock();
        tetrisArea.drawBoard();
        nextArea.placeNext();
        nextArea.drawNext();
        scoreArea.changeScore(0);
        scoreArea.setScore();
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void changeSize(int sizeNumber) {
        switch (sizeNumber) {
            case 1:
                tetrisArea.changeSize(1);
                nextArea.changeSize(1);
                scoreArea.changeSize(1);
                levelArea.changeSize(1);
                attackArea.changeSize(1);
                break;
            case 2:
                tetrisArea.changeSize(2);
                nextArea.changeSize(2);
                scoreArea.changeSize(2);
                levelArea.changeSize(2);
                attackArea.changeSize(2);
                break;
            case 3:
                tetrisArea.changeSize(3);
                nextArea.changeSize(3);
                scoreArea.changeSize(3);
                levelArea.changeSize(3);
                attackArea.changeSize(3);
                break;
            default:
                tetrisArea.changeSize(1);
                nextArea.changeSize(1);
                scoreArea.changeSize(1);
                levelArea.changeSize(1);
                attackArea.changeSize(1);
                break;
        }
    }

    @Override
    public String getBattleMode() {
        return BattleMode;
    }

    @Override
    public String getName() {
        return name;
    }

    public BattleBoardLocator getBattleBoardLocator() {
        return battleBoardLocator;
    }

    @Override
    public void notifyPanel() {
        panelList.forEach(panel -> panel.updateBattleBoardLocator(this.getBattleBoardLocator()));
    }

    @Override
    public void addPanel(ObserveInterface panel) {
        panelList.add(panel);
    }
}
