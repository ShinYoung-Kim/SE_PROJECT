package se.tetris.component.itemboardui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import se.tetris.blocks.*;
import se.tetris.component.Board.PlayerKeyListener;
import se.tetris.component.ScoreItem;
import se.tetris.component.Sizeable;
import se.tetris.component.boardlogic.BoardLocator;
import se.tetris.component.boardlogic.BoardTimer;
import se.tetris.component.boardlogic.RandomBlock;
import se.tetris.component.boardui.BoardLevelPanel;
import se.tetris.component.itemboardlogic.ItemBoardLocator;
import se.tetris.component.itemboardlogic.ItemBoardTimer;
import se.tetris.component.itemboardlogic.ItemBoardValues;
import se.tetris.data.*;
import se.tetris.setting.SettingCode;
import se.tetris.setting.SettingValues;

import static se.tetris.setting.SettingCode.screenHeight;
import static se.tetris.setting.SettingCode.screenWidth;

public class SeperatingItemBoard extends JFrame implements Sizeable {

    public static SeperatingItemBoard seperatingItemBoardMain;
    private static final long serialVersionUID = 2434035659171694595L;

    ScoreItem scoreItem = new ScoreItem();

    double min;
    double max;
    double percentage;
    double weighted;
    Random rnd;
    int block;

    DBCalls dataCalls = new DBCalls();

    private ItemBoardTetrisTextPane tetrisArea;
    private ItemBoardNextTextPane nextArea;
    private ItemBoardScorePanel scorePanel;
    private ItemBoardLevelPanel levelPanel;

    private JPanel panel;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private KeyListener playerKeyListener;
    private ItemBoardTimer boardTimer;
    int eraseCnt = 0;

    public static int mode = 0;

    //initInterval 난이도에 따라 조절
    //public static int initEasyInterval = 2000;
    //public static int initNormalInterval = 1000;
    //public static int initHardInterval = 500;
    public final SettingValues setting = SettingValues.getInstance();
    public int intervalByMode = setting.intervalNumber;
    public int intervalByModeForChange = setting.intervalNumber;
    RandomBlock randomBlock = new RandomBlock();

    private static int blockNumber = 0;

    public SeperatingItemBoard() {
        super("SeoulTech SE Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Board display setting.
        tetrisArea = new ItemBoardTetrisTextPane();
        nextArea = new ItemBoardNextTextPane();
        scorePanel = new ItemBoardScorePanel();
        levelPanel = new ItemBoardLevelPanel();

        ItemBoardLocator.init(new ItemBoardLocator(nextArea, tetrisArea, scorePanel, levelPanel));

        leftPanel = new JPanel();
        leftPanel.add(tetrisArea);
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(nextArea);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(scorePanel);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(levelPanel);

        panel = new JPanel();
        panel.add(leftPanel);
        panel.add(rightPanel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        add(panel);

        mode = dataCalls.getLevelSetting();

        //Set timer for block drops.
        boardTimer = new ItemBoardTimer();
        ItemBoardLocator.getInstance().setBoardTimer(boardTimer);

        //Initialize board for the game.
        playerKeyListener = new PlayerKeyListener();
        addKeyListener(playerKeyListener);
        setFocusable(true);
        requestFocus();

        //Create the first block and draw

        //Document default style.

        tetrisArea.placeBlock();
        tetrisArea.drawBoard();
        nextArea.placeNext();
        nextArea.drawNext();

        boardTimer.boardTimerStart();
    }

    //interval 함수

    public void reset() {
        tetrisArea.resetBoard();
        nextArea.resetNextBoard();
        tetrisArea.setX(3);
        tetrisArea.setY(0);
        tetrisArea.changeCurr();
        nextArea.setNext(randomBlock.getRandomBlock(setting.modeChoose));
        tetrisArea.placeBlock();
        tetrisArea.drawBoard();
        nextArea.placeNext();
        nextArea.drawNext();
    }

    public class PlayerKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (SettingValues.getInstance().keyChoose == 1) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DOWN:
                        tetrisArea.moveDown();
                        tetrisArea.drawBoard();
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (tetrisArea.getNotMove() == false) {
                            tetrisArea.moveRight();
                        }
                        tetrisArea.drawBoard();
                        break;
                    case KeyEvent.VK_LEFT:
                        if (tetrisArea.getNotMove() == false) {
                            tetrisArea.moveLeft();
                        }
                        tetrisArea.drawBoard();
                        break;
                    case KeyEvent.VK_UP:
                        if (tetrisArea.getBlockFix() == false)
                            tetrisArea.blockRotate();
                        tetrisArea.drawBoard();
                        break;
                    case KeyEvent.VK_SPACE:
                        while (true) {
                            tetrisArea.eraseCurr();
                            if (tetrisArea.getItemDrop() && ItemBoardValues.getInstance().getItemType() == 12) {
                                for (int i = tetrisArea.getY(); i < 20; i++) {
                                    for (int j = tetrisArea.getX(); j < tetrisArea.getX() + tetrisArea.getCurr().width(); j++) {
                                        tetrisArea.makeBoardZero(i, j);
                                    }
                                }
                                tetrisArea.setX(3);
                                tetrisArea.setY(0);
                                tetrisArea.setCurr(nextArea.getNext());
                                nextArea.eraseNext();
                                nextArea.setNext(randomBlock.getRandomBlock(setting.modeChoose));
                                nextArea.placeNext();
                                nextArea.drawNext();
                                tetrisArea.placeBlock();
                                tetrisArea.drawBoard();
                                tetrisArea.setNotMove(false);
                                tetrisArea.setBlockFix(false);
                                ItemBoardValues.getInstance().setItemType(0);
                                tetrisArea.setItemFlag(false);
                                tetrisArea.setItemDrop(false);
                                break;
                            } else {
                                if (tetrisArea.collisionBottom()) {
                                    tetrisArea.collisionOccur();
                                    tetrisArea.lineRemove();
                                    if (!tetrisArea.isGameOver()) {
                                        tetrisArea.placeBlock();
                                        tetrisArea.drawBoard();
                                    }
                                    break;
                                } else {
                                    tetrisArea.increaseY();
                                }
                                tetrisArea.lineRemove();
                                tetrisArea.placeBlock();
                                tetrisArea.drawBoard();
                            }
                        }
                        break;
                    case KeyEvent.VK_ESCAPE:
                        boardTimer.boardTimerStop();
                        String[] stopOption = {"재시작", "계속", "종료"};
                        int choice = JOptionPane.showOptionDialog(null, "무엇을 선택하시겠습니까?", "일시정지", 0, 0, null, stopOption, stopOption[1]);
                        switch (choice) {
                            case 0:
                                int confirm1 = JOptionPane.showConfirmDialog(null, "정말 게임을 재시작 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
                                if (confirm1 == 0) {
                                    reset();
                                    scorePanel.changeScore(0);
                                    levelPanel.changeLevel(0);
                                    boardTimer.boardTimerRestart();
                                } else {
                                    boardTimer.boardTimerStart();
                                }
                                break;
                            case 1:
                                boardTimer.boardTimerStart();
                                break;
                            case 2:
                                int confirm2 = JOptionPane.showConfirmDialog(null, "정말 게임을 종료하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
                                if (confirm2 == 0) {
                                    dispose(); //or save score and move to score board.
                                } else {
                                    boardTimer.boardTimerStart();
                                }
                                break;
                        }
                        break;
                }
            } else if (SettingValues.getInstance().keyChoose == 2) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_S:
                        tetrisArea.moveDown();
                        tetrisArea.drawBoard();
                        break;
                    case KeyEvent.VK_D:
                        if (tetrisArea.getNotMove() == false) {
                            tetrisArea.moveRight();
                        }
                        tetrisArea.drawBoard();
                        break;
                    case KeyEvent.VK_A:
                        if (tetrisArea.getNotMove() == false) {
                            tetrisArea.moveLeft();
                        }
                        tetrisArea.drawBoard();
                        break;
                    case KeyEvent.VK_W:
                        if (tetrisArea.getBlockFix() == false)
                            tetrisArea.blockRotate();
                        tetrisArea.drawBoard();
                        break;
                    case KeyEvent.VK_SPACE:
                        while (true) {
                            tetrisArea.eraseCurr();
                            if (tetrisArea.getItemDrop() && ItemBoardValues.getInstance().getItemType() == 12) {
                                for (int i = tetrisArea.getY(); i < 20; i++) {
                                    for (int j = tetrisArea.getX(); j < tetrisArea.getX() + tetrisArea.getCurr().width(); j++) {
                                        tetrisArea.makeBoardZero(i, j);
                                    }
                                }
                                tetrisArea.setX(3);
                                tetrisArea.setY(0);
                                tetrisArea.setCurr(nextArea.getNext());
                                nextArea.eraseNext();
                                nextArea.setNext(randomBlock.getRandomBlock(setting.modeChoose));
                                nextArea.placeNext();
                                nextArea.drawNext();
                                tetrisArea.placeBlock();
                                tetrisArea.drawBoard();
                                tetrisArea.setNotMove(false);
                                tetrisArea.setBlockFix(false);
                                ItemBoardValues.getInstance().setItemType(0);
                                tetrisArea.setItemFlag(false);
                                tetrisArea.setItemDrop(false);
                                break;
                            } else {
                                if (tetrisArea.collisionBottom()) {
                                    tetrisArea.collisionOccur();
                                    tetrisArea.lineRemove();
                                    if (!tetrisArea.isGameOver()) {
                                        tetrisArea.placeBlock();
                                        tetrisArea.drawBoard();
                                    }
                                    break;
                                } else {
                                    tetrisArea.increaseY();
                                }
                                tetrisArea.lineRemove();
                                tetrisArea.placeBlock();
                                tetrisArea.drawBoard();
                            }
                        }
                        break;
                    case KeyEvent.VK_ESCAPE:
                        boardTimer.boardTimerStop();
                        String[] stopOption = {"Restart", "Play", "Exit"};
                        int choice = JOptionPane.showOptionDialog(null, "What Do You Want?", "Stop", 0, 0, null, stopOption, stopOption[1]);
                        switch (choice) {
                            case 0:
                                int confirm1 = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
                                if (confirm1 == 0) {
                                    reset();
                                    scorePanel.changeScore(0);
                                    levelPanel.changeLevel(0);
                                    tetrisArea.setItemFlag(false);
                                    tetrisArea.setItemDrop(false);
                                    tetrisArea.setBlockFix(false);
                                    tetrisArea.setNotMove(false);
                                    boardTimer.boardTimerRestart();
                                } else {
                                    boardTimer.boardTimerStart();
                                }
                                break;
                            case 1:
                                boardTimer.boardTimerStart();
                                break;
                            case 2:
                                int confirm2 = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
                                if (confirm2 == 0) {
                                    dispose(); //or save score and move to score board.
                                } else {
                                    boardTimer.boardTimerStart();
                                }
                                break;
                        }
                        break;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    //max - 30, default - 20,


    //score


    @Override
    public void changeSize(int sizeNumber) {
        switch (sizeNumber) {
            case 1:
                setSize(400, 600);
                tetrisArea.changeSize(1);
                nextArea.changeSize(1);
                scorePanel.changeSize(1);
                levelPanel.changeSize(1);
                break;
            case 2:
                setSize(800, 800);
                tetrisArea.changeSize(2);
                nextArea.changeSize(2);
                scorePanel.changeSize(2);
                levelPanel.changeSize(2);
                break;
            case 3:
                setSize(screenWidth, screenHeight);
                tetrisArea.changeSize(3);
                nextArea.changeSize(3);
                scorePanel.changeSize(3);
                levelPanel.changeSize(3);
                break;
            default:
                setSize(400, 600);
                tetrisArea.changeSize(1);
                nextArea.changeSize(1);
                scorePanel.changeSize(1);
                levelPanel.changeSize(1);
                break;
        }
    }

    public static SeperatingItemBoard getseperatingItemBoard() {
        return seperatingItemBoardMain;
    }

    public void gameStop() {
        boardTimer.boardTimerStop();
    }

    public static void main(String[] args) {
        SeperatingItemBoard seperatingItemBoard = new SeperatingItemBoard();
        seperatingItemBoard.changeSize(1);
        seperatingItemBoard.setVisible(true);
    }

}