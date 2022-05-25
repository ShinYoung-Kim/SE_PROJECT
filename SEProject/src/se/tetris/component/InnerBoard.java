package se.tetris.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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

import se.tetris.blocks.Block;
import se.tetris.blocks.IBlock;
import se.tetris.blocks.JBlock;
import se.tetris.blocks.LBlock;
import se.tetris.blocks.OBlock;
import se.tetris.blocks.SBlock;
import se.tetris.blocks.TBlock;
import se.tetris.blocks.ZBlock;

import se.tetris.setting.SettingValues;
import se.tetris.data.*;

public class InnerBoard extends JPanel {

    public static Board innerBoardMain;
    private static final long serialVersionUID = 2434035659171694595L;

    public static final int HEIGHT = 20;
    public static final int WIDTH = 10;
    public static final char BORDER_CHAR = 'X';

    double min;
    double max;
    double percentage;
    double weighted;
    Random rnd;
    int block;

    DBCalls dataCalls = new DBCalls();

    private JTextPane tetrisArea;
    private JTextPane nextArea;
    public JTextPane attackArea;
    private JPanel panel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel scorePanel;
    private JPanel levelPanel;
    private int[][] board;
    private int[][] nextBoard;
    private int[][] attackBoard;
    private SimpleAttributeSet stylesetBr;
    private SimpleAttributeSet stylesetNx;
    private SimpleAttributeSet stylesetCur;
    public SimpleAttributeSet stylesetAk;
    private StyledDocument boardDoc;
    private StyledDocument nextDoc;
    public StyledDocument attackDoc;
    public Timer timer;
    private Block curr;
    private Block next;
    private Block lastBlock;
    private int lastX;
    public int lastY;
    public int attackY = 9;
    private int x = 3; //Default Position.
    public int y = 0;
    int nextX = 1;
    int nextY = 0;
    private int score = 0;
    private int level = 0;
    private String name = "player";
    public ArrayList<Integer> attackLine;

    public static int mode = 0;
    int eraseCnt = 0;

    //initInterval 난이도에 따라 조절
    //public static int initEasyInterval = 2000;
    //public static int initNormalInterval = 1000;
    //public static int initHardInterval = 500;
    public final SettingValues setting = SettingValues.getInstance();
    public int intervalByMode = setting.intervalNumber;
    public int intervalByModeForChange = setting.intervalNumber;

    //만들어진 블럭 개수 세기
    private int blockNumber = 0;

    ScoreItem scoreItem = new ScoreItem();

    private JLabel scoreLb1 = new JLabel("Scores");
    private JLabel scoreLb2 = new JLabel(Integer.toString(score));
    private JLabel levelLb1 = new JLabel("Level");
    private JLabel levelLb2 = new JLabel(Integer.toString(level));

    boolean whoIs = false;
    boolean whoAttacked = false;
    public int attackLineCount = 3;

    StringBuffer sbByAttack;

    boolean alreadyAttacked = false;
    boolean attackBoardFull = false;

    public InnerBoard() {
        //Board display setting.
        tetrisArea = new JTextPane();
        tetrisArea.setEditable(false);
        tetrisArea.setBackground(Color.BLACK);
        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        tetrisArea.setBorder(border);

        nextArea = new JTextPane();
        nextArea.setEditable(false);
        nextArea.setBackground(Color.BLACK);
        nextArea.setBorder(border);
        nextArea.setPreferredSize(new Dimension(150, 150));

        scorePanel = new JPanel();
        EtchedBorder scoreBorder = new EtchedBorder();
        scorePanel.setBorder(scoreBorder);
        scorePanel.setPreferredSize(new Dimension(150, 50));


        scoreLb1.setForeground(Color.darkGray);
        scoreLb1.setAlignmentX(CENTER_ALIGNMENT);

        scoreLb2.setForeground(Color.RED);

        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.add(scoreLb1);
        scorePanel.add(Box.createVerticalStrut(5));
        scorePanel.add(scoreLb2);


        levelPanel = new JPanel();
        levelPanel.setBorder(scoreBorder);
        levelPanel.setPreferredSize(new Dimension(150, 50));

        mode = dataCalls.getLevelSetting();

        levelLb1.setForeground(Color.darkGray);
        levelLb1.setAlignmentX(CENTER_ALIGNMENT);

        levelLb2.setForeground(Color.BLUE);
        levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.Y_AXIS));
        levelPanel.add(levelLb1);
        levelPanel.add(Box.createVerticalStrut(5));
        levelPanel.add(levelLb2);

        attackArea = new JTextPane();
        attackArea.setEditable(false);
        attackArea.setBackground(Color.BLACK);
        CompoundBorder attackBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 3),
                BorderFactory.createLineBorder(Color.WHITE, 3));
        attackArea.setBorder(attackBorder);
        attackArea.setPreferredSize(new Dimension(150, 150));

        leftPanel = new JPanel();
        leftPanel.add(tetrisArea);
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(nextArea);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(scorePanel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(levelPanel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(attackArea);

        panel = new JPanel();
        panel.add(leftPanel);
        panel.add(rightPanel);

        add(panel);

        //Set timer for block drops.
        timer = new Timer(getInterval(blockNumber, eraseCnt), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDown();
            }
        });

        //Initialize board for the game.
        board = new int[HEIGHT][WIDTH];
        nextBoard = new int[3][5];
        attackBoard = new int [10][10];


        //Create the first block and draw
        curr = getRandomBlock(setting.modeChoose);
        next = getRandomBlock(setting.modeChoose);

        //Document default style.
        stylesetBr = new SimpleAttributeSet();
        StyleConstants.setFontSize(stylesetBr, 20);
        StyleConstants.setFontFamily(stylesetBr, "Courier New");
        StyleConstants.setBold(stylesetBr, true);
        StyleConstants.setForeground(stylesetBr, Color.WHITE);
        StyleConstants.setAlignment(stylesetBr, StyleConstants.ALIGN_CENTER);

        stylesetCur = new SimpleAttributeSet();
        StyleConstants.setFontSize(stylesetCur, 20);
        StyleConstants.setFontFamily(stylesetCur, "Courier New");
        StyleConstants.setBold(stylesetCur, true);
        StyleConstants.setAlignment(stylesetCur, StyleConstants.ALIGN_CENTER);

        stylesetNx = new SimpleAttributeSet();
        StyleConstants.setFontSize(stylesetNx, 25);
        StyleConstants.setFontFamily(stylesetNx, "Courier New");
        StyleConstants.setBold(stylesetNx, true);
        StyleConstants.setAlignment(stylesetNx, StyleConstants.ALIGN_CENTER);

        stylesetAk = new SimpleAttributeSet();
        StyleConstants.setFontSize(stylesetAk, 10);
        StyleConstants.setFontFamily(stylesetAk, "Courier New");
        StyleConstants.setBold(stylesetAk, true);
        StyleConstants.setForeground(stylesetAk, Color.GRAY);
        StyleConstants.setAlignment(stylesetAk, StyleConstants.ALIGN_CENTER);

        boardDoc = tetrisArea.getStyledDocument();
        nextDoc = nextArea.getStyledDocument();
        attackDoc = attackArea.getStyledDocument();

        attackLine = new ArrayList<Integer>();


        placeBlock();
        drawBoard();
        placeNext();
        drawNext();


        timer.start();
    }

    public Block getRandomBlock(int modeChoose) {
        switch (modeChoose) {
            case 1:
                min = 1;
                max = 100;
                percentage = Math.random() * (max - min) + min;
                if (percentage <= (double)100 / 720 * 100 * 1.2)
                    return new IBlock();
                else
                {
                    rnd = new Random(System.currentTimeMillis());
                    block = rnd.nextInt(6);
                    switch(block) {
                        case 0:
                            return new JBlock();
                        case 1:
                            return new LBlock();
                        case 2:
                            return new ZBlock();
                        case 3:
                            return new SBlock();
                        case 4:
                            return new TBlock();
                        case 5:
                            return new OBlock();
                    }
                }
            case 2:
                block = (int)(Math.random() * 7);
                switch(block) {
                    case 0:
                        return new IBlock();
                    case 1:
                        return new JBlock();
                    case 2:
                        return new LBlock();
                    case 3:
                        return new ZBlock();
                    case 4:
                        return new SBlock();
                    case 5:
                        return new TBlock();
                    case 6:
                        return new OBlock();
                }
            case 3:
                min = 1;
                max = 100;
                percentage = Math.random() * (max - min) + min;
                if (percentage <= (double)100 / 680 * 100 * 0.8)
                    return new IBlock();
                else
                {
                    block = (int)(Math.random() * 6);
                    switch(block) {
                        case 0:
                            return new JBlock();
                        case 1:
                            return new LBlock();
                        case 2:
                            return new ZBlock();
                        case 3:
                            return new SBlock();
                        case 4:
                            return new TBlock();
                        case 5:
                            return new OBlock();
                    }
                }
                break;
            default:
                break;
        }
        return new IBlock();
    }


    public void placeBlock() {
        for(int j=0; j<curr.height(); j++) {
            for(int i=0; i<curr.width(); i++) {
                if (curr.getShape(i, j) > 0)
                    board[y+j][x+i] = curr.getShape(i, j);
            }
        }
    }

    public void placeNext() {
        for(int j = 0; j < next.height(); j++) {
            for(int i=0; i<next.width(); i++) {
                nextBoard[nextY+j][nextX+i] = next.getShape(i, j);
            }
        }
    }

    public void placeAttack(ArrayList<Integer> attack) {
        for (int i = 0; i < attack.size(); i++) {
            attackLine.add(attack.get(i) - lastY);
        }
        int firstY = attackY;
        for (int i = firstY; i > firstY - attack.size(); i--, attackY--) {
            for (int j = 0; j < attackBoard[0].length; j++) {
                attackBoard[i][j] = 1;
            }
        }
    }

    public void eraseCurr() {
        for(int i=x; i<x+curr.width(); i++) {
            for(int j=y; j<y+curr.height(); j++) {
                if(curr.getShape(i-x,j-y) > 0)
                    board[j][i] = 0;
            }
        }
    }

    public void eraseNext() {
        for(int i = nextX; i < nextX + next.width(); i++) {
            for(int j=nextY; j< nextY + next.height(); j++) {
                nextBoard[j][i] = 0;
            }
        }
    }

    public void eraseLast() {
        int y = attackY + 1;
        int notRemove = 0;
        for (int i = y; i < y + lastBlock.height(); i++) {
            if (attackLine.contains(i - y)) {
                for (int j = lastX; j < lastX + lastBlock.width(); j++) {
                    if (lastBlock.getShape(j - lastX, i - y) > 0) {
                        attackBoard[i - notRemove][j] = 0;
                    }
                }
            }
            else {
                notRemove++;
            }
        }
        attackY = 9;
    }

    ArrayList<Integer> line = new ArrayList<Integer>();
    public ArrayList<Integer> lineCheck() {
        ArrayList<Integer> Item = new ArrayList<Integer>();
        int count;
        for(int i = 0; i < HEIGHT; i++) {
            count = 0;
            for(int j = 0; j < WIDTH; j++)
                if(board[i][j] > 0)
                {
                    count++;
                }

            if(count == WIDTH) Item.add(i);
        }
        return Item;
    }

    public void collisionOccur() {
        saveBoard();
        lastBlock = curr;
        lastX = x;
        lastY = y;
        curr = next;
        x = 3;
        y = 0;
        if (isGameOver() == true) {
            BattleBoard.gameStop();
            String[] overOption = {"종료하기", "다시하기"};
            String winner;
            if (name == "Player1") {
                winner = "Player2";
            }
            else
                winner = "Player1";
            int over = JOptionPane.showOptionDialog(null, winner + "이(가) 게임에서 승리했습니다!", "종료", 0, 0, null, overOption, overOption[0]);
            if (over == 0) {
                BattleBoard.gameClose();
            }
            if (over == 1) {
                BattleBoard.gameReset();
            }
        }
        else {
            eraseNext();
            next = getRandomBlock(setting.modeChoose);
            placeNext();
            drawNext();
        }
    }

    public void lineRemove() {
        line = lineCheck();
        if (line.size() > 1) {
            whoIs = true;
            BattleBoard.placeAttack(line);
            eraseLast();
            BattleBoard.drawAttack();
            attackLine.clear();
        }
        Iterator<Integer> iter = line.iterator();
        int index = 0;
        while(iter.hasNext()) {
            index = iter.next();
            for(int i = index; i > 1; i--) {
                for(int j = 0; j < WIDTH; j++) {
                    board[i][j] = board[i-1][j];
                }
            }
            index = 0;
            eraseCnt++;
            getScore(eraseCnt, "line");
            setScore();
        }


    }

    public boolean collisionBottom() {
        for (int i = 0; i < curr.height(); i++) {
            for (int j = 0; j < curr.width(); j++) {
                if (y >= HEIGHT - curr.height()) return true;
                if (curr.getShape(j, i) > 0 && i + y < 19) {
                    int checkBottom = board[i + y + 1][j + x];
                    if (checkBottom > 0) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean collisionRight() {
        for (int i = 0; i < curr.height(); i++) {
            for (int j = 0; j < curr.width(); j++) {
                if (curr.getShape(j, i) > 0 && j + x < 9) {
                    int checkRight = board[i + y][j + x + 1];
                    if(checkRight > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean collisionLeft() {
        for (int i = 0; i < curr.height(); i++) {
            for (int j = 0; j < curr.width(); j++) {
                if (curr.getShape(j, i) > 0 && j + x > 0) {
                    int checkLeft = board[i + y][j + x - 1];
                    if(checkLeft > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void moveDown() {
        eraseCurr();
        if (collisionBottom()) {
            collisionOccur();
            if (whoAttacked) {
                attackedFunction();
                BattleBoard.drawAttack();
                placeBlock();
                drawBoard();
            }
        }
        else y++;
        lineRemove();
        if (!isGameOver()) {
            placeBlock();
            drawBoard();
        }
    }

    public void attackedFunction() {
        System.out.println("Clear");
        for (int a = attackLineCount; a < HEIGHT; a++) {
            for (int b = 0; b < WIDTH; b++) {
                board[a - attackLineCount][b] = board[a][b];
            }
        }
        System.out.println(attackLineCount);
        BattleBoard.forAttack();
        attackBoardFull = false;
    }


    protected void moveRight() {
        eraseCurr();
        if(x < WIDTH - curr.width() && collisionRight() == false) x++;
        placeBlock();
    }

    protected void moveLeft() {
        eraseCurr();
        if(x > 0 && collisionLeft() == false) x--;
        placeBlock();
    }

    public void drawBoard() {
        StringBuffer sb = new StringBuffer();
        for(int t=0; t<WIDTH+2; t++) sb.append(BORDER_CHAR);
        sb.append("\n");
        for(int i=0; i < board.length; i++) {
            sb.append(BORDER_CHAR);
            for(int j=0; j < board[i].length; j++) {
                if(board[i][j] > 0) {
                    sb.append("■");
                } else {
                    sb.append(" ");
                }
            }
            sb.append(BORDER_CHAR);
            sb.append("\n");
        }
        for(int t=0; t<WIDTH+2; t++) sb.append(BORDER_CHAR);
        tetrisArea.setText(sb.toString());
        boardDoc.setCharacterAttributes(0, boardDoc.getLength(), stylesetBr, false);

        for(int j = 0; j < curr.height(); j++) {
            int rows = y+j == 0 ? 1 : y+j+1;
            int offset = rows * (WIDTH+3) + x + 1;
            for (int i = 0; i < curr.width(); i++) {
                if (curr.getShape(i, j) > 0) {
                    colorBlindModeCurrent(offset + i);
                }
            }
        }


        for (int i = 0; i < board.length; i++) {
            int offset = (i + 1) * (WIDTH + 3) + 1;
            for (int j = 0; j < board[0].length ; j++) {
                int block = board[i][j];
                switch(block) {
                    case 1:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(0, 58, 97));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        else {
                            StyleConstants.setForeground(stylesetCur, Color.CYAN);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 2:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(126, 98, 61));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        else {
                            StyleConstants.setForeground(stylesetCur, Color.BLUE);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 3:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(165, 148, 159));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        else {
                            StyleConstants.setForeground(stylesetCur, Color.PINK);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 4:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(187, 190, 242));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        else {
                            StyleConstants.setForeground(stylesetCur, Color.YELLOW);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 5:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(247, 193, 121));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        else {
                            StyleConstants.setForeground(stylesetCur, Color.GREEN);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 6:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(154, 127, 112));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        else {
                            StyleConstants.setForeground(stylesetCur, Color.MAGENTA);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 7:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(99, 106, 141));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        else {
                            StyleConstants.setForeground(stylesetCur, Color.RED);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                }
            }
        }


    }

    //blockNumber 증가 + timer 변경
    public void drawNext() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        blockNumber++;
        timer.setDelay(getInterval(blockNumber, eraseCnt));
        for(int i=0; i < nextBoard.length; i++) {
            for(int j=0; j < nextBoard[i].length; j++) {
                if(nextBoard[i][j] > 0) {
                    sb.append("■");
                } else {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        nextArea.setText(sb.toString());
        colorBlindModeNext();
    }

    public void colorBlindMode(SimpleAttributeSet styleSet, Block block) {
        if (setting.colorBlindModeCheck == 1) {
            StyleConstants.setForeground(styleSet, block.getColorBlind());
        } else {
            StyleConstants.setForeground(styleSet, block.getColor());
        }
    }
    public void colorBlindModeNext(){
        colorBlindMode(stylesetNx, next);
        nextDoc.setParagraphAttributes(0, nextDoc.getLength(), stylesetNx, false);
    }
    public void colorBlindModeCurrent(int offset){
        colorBlindMode(stylesetCur, curr);
        boardDoc.setCharacterAttributes(offset, 1, stylesetCur, true);
    }

    //interval 함수
    public int getInterval(int blockNumber, int eraseCnt) {
        if (blockNumber == 30 || blockNumber == 60 || blockNumber == 80 || blockNumber == 100 || blockNumber == 120) {
            if (intervalByMode == 1000) {
                getScore(5*eraseCnt, "std");
                setScore();
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (intervalByMode == 2000) {
                getScore(11*eraseCnt, "std");
                setScore();
                level++;                levelLb2.setText(Integer.toString(level));
            } else if (intervalByMode == 800) {
                getScore(20*eraseCnt, "std");
                setScore();
                level++;
                levelLb2.setText(Integer.toString(level));
            }
        }
        if (intervalByMode == 1000) {
            if (eraseCnt < 5 && eraseCnt >= 0) {
                intervalByModeForChange = 1000;
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 10 && eraseCnt >= 5) {
                intervalByModeForChange = (int) (1000 * 0.9);
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 15 && eraseCnt >= 10) {
                intervalByModeForChange = (int) (1000 * 0.9 * 0.9);
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 20 && eraseCnt >= 15) {
                intervalByModeForChange = (int) (1000 * 0.9 * 0.9 * 0.9);
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 25 && eraseCnt >= 20) {
                intervalByModeForChange = (int) (1000 * 0.9 * 0.9 * 0.9 * 0.9);
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 30 && eraseCnt >= 25) {
                intervalByModeForChange = (int) (1000 * 0.9 * 0.9 * 0.9 * 0.9 * 0.9);
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt >= 30) {
                intervalByModeForChange = (int) (1000 * 0.9 * 0.9 * 0.9 * 0.9 * 0.9 * 0.9);
                level++;
                levelLb2.setText(Integer.toString(level));
            }
        } else if (intervalByMode == 2000) {
            if (eraseCnt < 5 && eraseCnt >= 0) {
                intervalByModeForChange = 2000;
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 10 && eraseCnt >= 5) {
                intervalByModeForChange = (int) (2000 * 0.92);
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 15 && eraseCnt >= 10) {
                intervalByModeForChange = (int) (2000 * 0.92 * 0.92);
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 20 && eraseCnt >= 15) {
                intervalByModeForChange = (int) (2000 * 0.92 * 0.92 * 0.92);
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 25 && eraseCnt >= 20) {
                intervalByModeForChange = (int) (2000 * 0.92 * 0.92 * 0.92 * 0.92);
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 30 && eraseCnt >= 25) {
                intervalByModeForChange = (int) (2000 * 0.92 * 0.92 * 0.92 * 0.92 * 0.92);
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt >= 30) {
                intervalByModeForChange = (int) (2000 * 0.92 * 0.92 * 0.92 * 0.92 * 0.92 * 0.92);
                level++;
                levelLb2.setText(Integer.toString(level));
            }
        } else if (intervalByMode == 800) {
            if (eraseCnt < 5 && eraseCnt >= 0) {
                intervalByModeForChange = 800;
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 10 && eraseCnt >= 5) {
                intervalByModeForChange = (int) (800 * 0.88);
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 15 && eraseCnt >= 10) {
                intervalByModeForChange = (int) (800 * 0.88 * 0.88);
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 20 && eraseCnt >= 15) {
                intervalByModeForChange = (int) (800 * 0.88 * 0.88 * 0.88);
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 25 && eraseCnt >= 20) {
                intervalByModeForChange = (int) (800 * 0.88 * 0.88 * 0.88 * 0.88);
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 30 && eraseCnt >= 25) {
                intervalByModeForChange = (int) (800 * 0.88 * 0.88 * 0.88 * 0.88 * 0.88);
                level++;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt >= 30) {
                intervalByModeForChange = (int) (800 * 0.88 * 0.88 * 0.88 * 0.88 * 0.88 * 0.88);
                level++;
                levelLb2.setText(Integer.toString(level));
            }
        }
        System.out.println("Created : " + blockNumber + "   Removed : " + eraseCnt +"   intervalByMode" +intervalByMode + "   interval Number : " + intervalByModeForChange);
        return intervalByModeForChange;
    }

    public void reset() {
        board = new int[HEIGHT][WIDTH];
        nextBoard = new int[4][5];
        x = 3;
        y = 0;
        curr = getRandomBlock(setting.modeChoose);
        next = getRandomBlock(setting.modeChoose);
        placeBlock();
        drawBoard();
        placeNext();
        drawNext();
        this.board = new int[20][10];
    }

    public boolean startCheck() {
        for (int i = 0; i < curr.height(); i++) {
            for (int j = 0; j < curr.width(); j++)
                if(curr.getShape(j,i) > 0 && board[y + i][x + j] > 0)
                    return true;
        }
        return false;
    }

    public boolean isGameOver() {
        if (startCheck())
            return true;
        for (int i = 0; i < WIDTH; i++)
            if (board[0][i] > 0)
                return true;
        return false;
    }

    public void saveBoard() {
        for (int i = 0; i < curr.height(); i++)
            for (int j = 0; j < curr.width(); j++)
                if (curr.getShape(j, i) > 0)
                    board[y + i][j + x] = curr.getShape(j, i);
    }


    public boolean rotateTest(int [][] shape, int inputX, int inputY) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (inputY + i > 19) // HEIGHT 초과
                    return true;
                if (inputX + j > 9) // WIDTH 초과
                    return true;
                if (shape[i][j] != 0 && board[inputY + i][inputX + j] != 0) // 충돌
                    return true;
            }
        }
        return false;
    }

    public void blockRotate() {
        eraseCurr();

        int [][] testShape = curr.getRotateShape();
        int testX = (x + curr.width()) - testShape[0].length;
        int testY = (y + curr.height()) - testShape.length;

        if (!rotateTest(testShape, x, y)) {
            curr.rotate();
        }

        else if(testY >= 0 && !rotateTest(testShape, x, testY)) {
            y = testY;
            curr.rotate();
        }

        else if(testX >= 0 && !rotateTest(testShape, testX, y)) {
            x = testX;
            curr.rotate();
        }

        placeBlock();
        drawBoard();
    }

    public void setScore() {
        String scoretxt = Integer.toString(score);
//				String.valueOf(score);
        String prescoretxt = scoreLb2.getText();
        System.out.println("점수 변경" + prescoretxt+"...>"+ scoretxt);
        scoreLb2.setText(scoretxt);
    }

    public void getScore(int lines, String mode) {
        int scorePre = lines;
        updateSroce(scorePre, mode);
    }

    public int getNowScore() {
        int score = this.score;
        return score;
    }

    public int updateSroce(int sc, String mode) {
        if(mode =="line") {
            if(sc>0 && sc<=5) {
                this.score += 10;
            }else if(sc>5 && sc<=10) {
                this.score += 15;
            }else {
                this.score += 20;
            }
            if(sc%3 ==0) {
                this.score += 3*sc;
            }
            if(sc%11 ==0) {
                this.score += 11;
            }
        }else {
            this.score += sc;
        }

        setScore();
        return score;
    }

    public void gameStop() {
        timer.stop();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timer getTimer() {
        return timer;
    }

    public int[][] getAttackBoard() {
        return attackBoard;
    }

    public void setAttackBoard(int[][] attackBoard) {
        this.attackBoard = attackBoard;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setAttackLineCount(int attackLineCount) {
        this.attackLineCount = attackLineCount;
    }
    public int getAttackLineCount() {
        return attackLineCount;
    }

}