package se.tetris.component;

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
import javax.xml.crypto.Data;

import se.tetris.blocks.Block;
import se.tetris.blocks.IBlock;
import se.tetris.blocks.JBlock;
import se.tetris.blocks.LBlock;
import se.tetris.blocks.OBlock;
import se.tetris.blocks.SBlock;
import se.tetris.blocks.TBlock;
import se.tetris.blocks.ZBlock;

import se.tetris.component.boardlogic.BoardLocator;
import se.tetris.component.boardlogic.BoardTimer;
import se.tetris.component.boardlogic.RandomBlock;
import se.tetris.component.boardui.BoardLevelPanel;
import se.tetris.component.boardui.BoardNextArea;
import se.tetris.component.boardui.BoardScorePanel;
import se.tetris.component.boardui.BoardTetrisArea;
import se.tetris.setting.ISetting;
import se.tetris.setting.SettingValues;
import se.tetris.data.*;

import static se.tetris.setting.SettingCode.screenHeight;
import static se.tetris.setting.SettingCode.screenWidth;

public class Board extends JFrame implements Sizeable {
    public static Board boardMain;
    private static final long serialVersionUID = 2434035659171694595L;

    private BoardTetrisArea tetrisArea;
    private BoardNextArea nextArea;
    private JPanel panel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private BoardScorePanel scorePanel;
    private BoardLevelPanel levelPanel;
    BoardLocator boardLocator;
    RandomBlock randomBlock;
    BoardTimer boardTimer;
    private KeyListener playerKeyListener;
    public static int score = 0;
    public static int level = 0;

    public static int mode = 0;

    final SettingValues setting = SettingValues.getInstance();

    //만들어진 블럭 개수 세기

    public Board() {
        super("SeoulTech SE Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tetrisArea = new BoardTetrisArea();
        nextArea = new BoardNextArea();
        scorePanel = new BoardScorePanel();
        levelPanel = new BoardLevelPanel();

        BoardLocator.init(new BoardLocator(nextArea, tetrisArea, scorePanel, levelPanel));

        //Board display setting.

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
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(leftPanel);
        panel.add(rightPanel);

        add(panel);

        //Set timer for block drops.
        boardTimer = new BoardTimer();
        BoardLocator.getInstance().setBoardTimer(boardTimer);

        //Initialize board for the game.
        playerKeyListener = new PlayerKeyListener();
        addKeyListener(playerKeyListener);
        setFocusable(true);
        requestFocus();

        //인터페이스 세팅
        //Create the first block and draw
        tetrisArea.changeCurr();

        //Document default style.
        tetrisArea.defaultDocumentStyle();

        tetrisArea.placeBlock();
        tetrisArea.drawBoard();
        nextArea.placeNext();
        nextArea.drawNext();

        boardTimer.boardTimerStart();
    }

    //인터페이스 세팅


    //blockNumber 증가 + timer 변경

    //interval 함수

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
        //this.board = new int[20][10];
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
                        tetrisArea.moveRight();
                        tetrisArea.drawBoard();
                        break;
                    case KeyEvent.VK_LEFT:
                        tetrisArea.moveLeft();
                        tetrisArea.drawBoard();
                        break;
                    case KeyEvent.VK_UP:
                        tetrisArea.blockRotate();
                        tetrisArea.drawBoard();
                        break;
                    case KeyEvent.VK_SPACE:
                        while (true) {
                            tetrisArea.eraseCurr();
                            if (tetrisArea.collisionBottom()) {
                                tetrisArea.collisionOccur();
                                tetrisArea.hey();
                                //placeBlock();
                                tetrisArea.drawBoard();
                                break;
                            } else {
                                tetrisArea.increaseY();
                                tetrisArea.hey();
                            }
                            //placeBlock();
                            tetrisArea.drawBoard();
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
                                    score = 0;
                                    level = 0;
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
                        tetrisArea.moveRight();
                        tetrisArea.drawBoard();
                        break;
                    case KeyEvent.VK_A:
                        tetrisArea.moveLeft();
                        tetrisArea.drawBoard();
                        break;
                    case KeyEvent.VK_W:
                        tetrisArea.blockRotate();
                        tetrisArea.drawBoard();
                        break;
                    case KeyEvent.VK_SPACE:
                        while (true) {
                            tetrisArea.eraseCurr();
                            if (tetrisArea.collisionBottom()) {
                                tetrisArea.collisionOccur();
                                tetrisArea.hey();
                                //placeBlock();
                                tetrisArea.drawBoard();
                                break;
                            } else {
                                tetrisArea.increaseY();
                                tetrisArea.hey();
                            }
                            //placeBlock();
                            tetrisArea.drawBoard();
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
                                    score = 0;
                                    level = 0;
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

    public static Board getBoard() {
        return boardMain;
    }

    @Override
    public void changeSize(int sizeNumber) {
        switch (sizeNumber) {
            case 1:
                setSize(400, 600);
                tetrisArea.changeSize(1);
                nextArea.changeSize(1);
                scorePanel.changeSize(1);
                levelPanel.changeSize(1);
                System.out.println("Board : " + getSize() + "TetrisArea : " + tetrisArea.getSize() + "nextArea : " + nextArea.getSize() +
                        "scorePanel : " + scorePanel.getSize() + "levelPanel : " + levelPanel.getSize() );
                break;
            case 2:
                setSize(800, 800);
                tetrisArea.changeSize(2);
                nextArea.changeSize(2);
                scorePanel.changeSize(2);
                levelPanel.changeSize(2);
                System.out.println("Board : " + getSize() + "TetrisArea : " + tetrisArea.getSize() + "nextArea : " + nextArea.getSize() +
                        "scorePanel : " + scorePanel.getSize() + "levelPanel : " + levelPanel.getSize() );
                break;
            case 3:
                setSize(screenWidth, screenHeight);
                tetrisArea.changeSize(3);
                nextArea.changeSize(3);
                scorePanel.changeSize(3);
                levelPanel.changeSize(3);
                System.out.println("Board : " + getSize() + "TetrisArea : " + tetrisArea.getSize() + "nextArea : " + nextArea.getSize() +
                        "scorePanel : " + scorePanel.getSize() + "levelPanel : " + levelPanel.getSize() );
                break;
            default:
                setSize(400, 600);
                tetrisArea.changeSize(1);
                nextArea.changeSize(1);
                scorePanel.changeSize(1);
                levelPanel.changeSize(1);
                System.out.println("Board : " + getSize() + "TetrisArea : " + tetrisArea.getSize() + "nextArea : " + nextArea.getSize() +
                        "scorePanel : " + scorePanel.getSize() + "levelPanel : " + levelPanel.getSize() );
                break;
        }
    }

    public class MockSetting implements ISetting {
        int modeChoose = SettingValues.getInstance().modeChoose;
        int colorBlindModeCheck = SettingValues.getInstance().colorBlindModeCheck;
        int intervalNumber = SettingValues.getInstance().intervalNumber;
        public int sizeNumber = SettingValues.getInstance().sizeNumber;
        public int keyChoose = SettingValues.getInstance().keyChoose;

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public class MockStart implements IStart {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public static void main(String[] args) {
        Board board = new Board();

        BoardLocator boardLocator = BoardLocator.getInstance();

        BoardLocator.getInstance().setBoardTetrisArea(new BoardTetrisArea());
        BoardLocator.getInstance().setLevelPanel(new BoardLevelPanel());
        BoardLocator.getInstance().setBoardTimer(new BoardTimer());
        BoardLocator.getInstance().setBoardNextArea(new BoardNextArea());
        BoardLocator.getInstance().setScorePanel(new BoardScorePanel());

        board.changeSize(1);

        board.setVisible(true);
    }
}