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

    Random rnd;

    DBCalls dataCalls = new DBCalls();

    public static BoardTetrisArea tetrisArea;
    public static BoardNextArea nextArea;
    private JPanel panel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private static BoardScorePanel scorePanel;
    private static BoardLevelPanel levelPanel;
    //private static int[][] board;
    //private static int[][] nextBoard;
    private KeyListener playerKeyListener;
    public static Timer timer;
    static int x = 3; //Default Position.
    static int y = 0;
    int nextX = 1;
    int nextY = 1;
    public static int score = 0;
    public static int level = 0;

    public static int mode = 0;
    int eraseCnt = 0;

    final SettingValues setting = SettingValues.getInstance();
    int intervalByMode = setting.intervalNumber;

    //만들어진 블럭 개수 세기
    private static int blockNumber = 0;

    public static ScoreItem scoreItem = new ScoreItem();

    static JLabel scoreLb1 = new JLabel("Scores");
    static JLabel scoreLb2 = new JLabel(Integer.toString(score));
    static JLabel levelLb1 = new JLabel("Level");
    static JLabel levelLb2 = new JLabel(Integer.toString(level));

    public Board() {

        super("SeoulTech SE Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Board display setting.
        tetrisArea = new BoardTetrisArea();
        nextArea = new BoardNextArea();
        scorePanel = new BoardScorePanel();
        levelPanel = new BoardLevelPanel();

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

        add(panel);

        //Set timer for block drops.
        timer = new Timer(getInterval(blockNumber, eraseCnt), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tetrisArea.moveDown();
            }
        });

        //Initialize board for the game.

        playerKeyListener = new PlayerKeyListener();
        addKeyListener(playerKeyListener);
        setFocusable(true);
        requestFocus();

        //인터페이스 세팅
        //Create the first block and draw

        //Document default style.

        tetrisArea.placeBlock();
        tetrisArea.drawBoard();
        nextArea.placeNext();
        nextArea.drawNext();

        timer.start();
    }

    //인터페이스 세팅


    //blockNumber 증가 + timer 변경

    //interval 함수
    public int getInterval(int blockNumber, int eraseCnt) {
        //생성
        if (blockNumber == 30 || blockNumber == 60 || blockNumber == 80 || blockNumber == 100 || blockNumber == 120) {
            if (intervalByMode == 1000) {
                getScore(5 * eraseCnt, "std");
                setScore();
            } else if (intervalByMode == 2000) {
                getScore(11 * eraseCnt, "std");
                setScore();
            } else if (intervalByMode == 800) {
                getScore(20 * eraseCnt, "std");
                setScore();
            }
        }
        //삭제
        if (intervalByMode == 1000) {
            if (eraseCnt < 5 && eraseCnt >= 0) {
                setting.intervalNumber = 1000;
                level = 1;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 10 && eraseCnt >= 5) {
                setting.intervalNumber = (int) (1000 * 0.9);
                level = 2;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 15 && eraseCnt >= 10) {
                setting.intervalNumber = (int) (1000 * 0.9 * 0.9);
                level = 3;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 20 && eraseCnt >= 15) {
                setting.intervalNumber = (int) (1000 * 0.9 * 0.9 * 0.9);
                level = 4;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 25 && eraseCnt >= 20) {
                setting.intervalNumber = (int) (1000 * 0.9 * 0.9 * 0.9 * 0.9);
                level = 5;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 30 && eraseCnt >= 25) {
                setting.intervalNumber = (int) (1000 * 0.9 * 0.9 * 0.9 * 0.9 * 0.9);
                level = 6;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt >= 30) {
                setting.intervalNumber = (int) (1000 * 0.9 * 0.9 * 0.9 * 0.9 * 0.9 * 0.9);
                level = 7;
                levelLb2.setText(Integer.toString(level));
            }
        } else if (intervalByMode == 2000) {
            if (eraseCnt < 5 && eraseCnt >= 0) {
                setting.intervalNumber = 2000;
                level = 1;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 10 && eraseCnt >= 5) {
                setting.intervalNumber = (int) (2000 * 0.92);
                level = 2;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 15 && eraseCnt >= 10) {
                setting.intervalNumber = (int) (2000 * 0.92 * 0.92);
                level = 3;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 20 && eraseCnt >= 15) {
                setting.intervalNumber = (int) (2000 * 0.92 * 0.92 * 0.92);
                level = 4;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 25 && eraseCnt >= 20) {
                setting.intervalNumber = (int) (2000 * 0.92 * 0.92 * 0.92 * 0.92);
                level = 5;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 30 && eraseCnt >= 25) {
                setting.intervalNumber = (int) (2000 * 0.92 * 0.92 * 0.92 * 0.92 * 0.92);
                level = 6;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt >= 30) {
                setting.intervalNumber = (int) (2000 * 0.92 * 0.92 * 0.92 * 0.92 * 0.92 * 0.92);
                level = 7;
                levelLb2.setText(Integer.toString(level));
            }
        } else if (intervalByMode == 800) {
            if (eraseCnt < 5 && eraseCnt >= 0) {
                setting.intervalNumber = 800;
                level = 1;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 10 && eraseCnt >= 5) {
                setting.intervalNumber = (int) (800 * 0.88);
                level = 2;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 15 && eraseCnt >= 10) {
                setting.intervalNumber = (int) (800 * 0.88 * 0.88);
                level = 3;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 20 && eraseCnt >= 15) {
                setting.intervalNumber = (int) (800 * 0.88 * 0.88 * 0.88);
                level = 4;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 25 && eraseCnt >= 20) {
                setting.intervalNumber = (int) (800 * 0.88 * 0.88 * 0.88 * 0.88);
                level = 5;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 30 && eraseCnt >= 25) {
                setting.intervalNumber = (int) (800 * 0.88 * 0.88 * 0.88 * 0.88 * 0.88);
                level = 6;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt >= 30) {
                setting.intervalNumber = (int) (800 * 0.88 * 0.88 * 0.88 * 0.88 * 0.88 * 0.88);
                level = 7;
                levelLb2.setText(Integer.toString(level));
            }
        }
        System.out.println("Created : " + blockNumber + "   Removed : " + eraseCnt + "   intervalByMode" + intervalByMode + "   interval Number : " + setting.intervalNumber);
        return setting.intervalNumber;
    }

    public void reset() {
        tetrisArea.board = new int[HEIGHT][WIDTH];
        nextArea.nextBoard = new int[4][5];
        x = 3;
        y = 0;
        tetrisArea.curr = tetrisArea.getRandomBlock(setting.modeChoose);
        nextArea.next = nextArea.getRandomBlock(setting.modeChoose);
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
                                y++;
                                tetrisArea.hey();
                            }
                            //placeBlock();
                            tetrisArea.drawBoard();
                        }
                        break;
                    case KeyEvent.VK_ESCAPE:
                        timer.stop();
                        String[] stopOption = {"재시작", "계속", "종료"};
                        int choice = JOptionPane.showOptionDialog(null, "무엇을 선택하시겠습니까?", "일시정지", 0, 0, null, stopOption, stopOption[1]);
                        switch (choice) {
                            case 0:
                                int confirm1 = JOptionPane.showConfirmDialog(null, "정말 게임을 재시작 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
                                if (confirm1 == 0) {
                                    reset();
                                    score = 0;
                                    level = 0;
                                    timer.restart();
                                } else {
                                    timer.start();
                                }
                                break;
                            case 1:
                                timer.start();
                                break;
                            case 2:
                                int confirm2 = JOptionPane.showConfirmDialog(null, "정말 게임을 종료하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
                                if (confirm2 == 0) {
                                    dispose(); //or save score and move to score board.
                                } else {
                                    timer.start();
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
                                y++;
                                tetrisArea.hey();
                            }
                            //placeBlock();
                            tetrisArea.drawBoard();
                        }
                        break;
                    case KeyEvent.VK_ESCAPE:
                        timer.stop();
                        String[] stopOption = {"Restart", "Play", "Exit"};
                        int choice = JOptionPane.showOptionDialog(null, "What Do You Want?", "Stop", 0, 0, null, stopOption, stopOption[1]);
                        switch (choice) {
                            case 0:
                                int confirm1 = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
                                if (confirm1 == 0) {
                                    reset();
                                    score = 0;
                                    level = 0;
                                    timer.restart();
                                } else {
                                    timer.start();
                                }
                                break;
                            case 1:
                                timer.start();
                                break;
                            case 2:
                                int confirm2 = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
                                if (confirm2 == 0) {
                                    dispose(); //or save score and move to score board.
                                } else {
                                    timer.start();
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


    //max - (200, 60), default - (150, 50)

    //max - 17, default - nothing,



    public static Board getBoard() {
        return boardMain;
    }

    public void setScore() {
        String scoretxt = Integer.toString(score);
        String prescoretxt = scoreLb2.getText();
        scoreLb2.setText(scoretxt);
    }

    public void getScore(int lines, String mode) {
        int scorePre = lines;
        if (mode == "line") {
            updateSroce(scorePre, mode);
        } else if (mode == "block") {
            updateSroce(1, mode);
        }

    }

    public int getNowScore() {
        int score = this.score;
        return score;
    }

    public int updateSroce(int sc, String mode) {
        if (mode == "line") {
            if (sc > 0 && sc <= 5) {
                this.score += 10;
            } else if (sc > 5 && sc <= 10) {
                this.score += 15;
            } else {
                this.score += 20;
            }
            if (sc % 3 == 0) {
                this.score += 3 * sc;
            }
            if (sc % 11 == 0) {
                this.score += 11;
            }
        } else if (mode == "block") {
            this.score += sc;
        }

        setScore();
        return score;
    }

    @Override
    public void changeSize(int sizeNumber) {
        switch (sizeNumber) {
            case 1:
                setSize(400, 600);
                break;
            case 2:
                setSize(800, 800);
                break;
            case 3:
                setSize(screenWidth, screenHeight);
                break;
            default:
                setSize(400, 600);
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
}