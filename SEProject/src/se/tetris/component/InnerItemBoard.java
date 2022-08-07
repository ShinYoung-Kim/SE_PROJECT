package se.tetris.component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import se.tetris.blocks.*;
import se.tetris.data.DBCalls;
import se.tetris.setting.SettingValues;
import se.tetris.data.*;

import static se.tetris.setting.SettingCode.screenHeight;
import static se.tetris.setting.SettingCode.screenWidth;

public class InnerItemBoard extends JPanel implements Sizeable {

    public static Board innerItemBoardMain;
    protected static final long serialVersionUID = 2434035659171694595L;

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
    private JPanel scoreLevelPanel;
    public int[][] board;
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
    public Block curr;
    public Block next;
    private Block lastBlock;
    private int lastX;
    public int lastY;
    public int attackY = 9;
    public int x = 3; //Default Position.
    public int y = 0;
    public int nextX = 1;
    public int nextY = 0;
    private int score = 0;
    private int level = 0;
    private String name = "player";
    public ArrayList<Integer> attackLine;
    boolean itemFlag = false;
    boolean itemDrop = false;
    boolean itemApplied = false;
    boolean blockFix = false;
    boolean notMove = false;
    int itemX = 0;
    int itemY = 0;
    int itemType;

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
    public int attackLineCount = 0;

    StringBuffer sbByAttack;
    boolean alreadyAttacked = false;
    boolean attackBoardFull = false;

    public InnerItemBoard(int sizeNumber) {
        tetrisArea = new JTextPane();
        tetrisArea.setEditable(false);
        tetrisArea.setBackground(Color.BLACK);
        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        tetrisArea.setBorder(border);
        tetrisArea.setAlignmentX(CENTER_ALIGNMENT);
        tetrisArea.setAlignmentY(CENTER_ALIGNMENT);

        nextArea = new JTextPane();
        nextArea.setEditable(false);
        nextArea.setBackground(Color.BLACK);
        nextArea.setBorder(border);
        nextArea.setAlignmentX(CENTER_ALIGNMENT);
        nextArea.setAlignmentY(CENTER_ALIGNMENT);
        //nextArea.setPreferredSize(new Dimension(150, 150));

        scorePanel = new JPanel();
        EtchedBorder scoreBorder = new EtchedBorder();
        scorePanel.setBorder(scoreBorder);
        scorePanel.setPreferredSize(new Dimension(150, 50));

        scoreLb1.setForeground(Color.darkGray);
        //정렬
        scoreLb1.setAlignmentX(CENTER_ALIGNMENT);
        scoreLb2.setAlignmentX(CENTER_ALIGNMENT);
        levelLb1.setAlignmentX(CENTER_ALIGNMENT);
        levelLb2.setAlignmentX(CENTER_ALIGNMENT);

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

        scoreLevelPanel = new JPanel();
        scoreLevelPanel.setLayout(new BoxLayout(scoreLevelPanel, BoxLayout.Y_AXIS));
        scoreLevelPanel.add(scorePanel);
        scoreLevelPanel.add(levelPanel);

        attackArea = new JTextPane();
        attackArea.setEditable(false);
        attackArea.setBackground(Color.BLACK);
        CompoundBorder attackBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 3),
                BorderFactory.createLineBorder(Color.WHITE, 3));
        attackArea.setBorder(attackBorder);
        attackArea.setAlignmentX(CENTER_ALIGNMENT);
        attackArea.setAlignmentY(CENTER_ALIGNMENT);
        //attackArea.setPreferredSize(new Dimension(150, 150));

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
        attackBoard = new int[10][10];


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
        StyleConstants.setLineSpacing(stylesetBr, -0.45f);

        stylesetCur = new SimpleAttributeSet();
        StyleConstants.setFontSize(stylesetCur, 20);
        StyleConstants.setFontFamily(stylesetCur, "Courier New");
        StyleConstants.setBold(stylesetCur, true);
        StyleConstants.setAlignment(stylesetCur, StyleConstants.ALIGN_CENTER);
        //StyleConstants.setLineSpacing(stylesetCur, -0.45f);

        stylesetNx = new SimpleAttributeSet();
        StyleConstants.setFontSize(stylesetNx, 25);
        StyleConstants.setFontFamily(stylesetNx, "Courier New");
        StyleConstants.setBold(stylesetNx, true);
        StyleConstants.setAlignment(stylesetNx, StyleConstants.ALIGN_CENTER);
        StyleConstants.setLineSpacing(stylesetNx, -0.45f);

        stylesetAk = new SimpleAttributeSet();
        StyleConstants.setFontSize(stylesetAk, 10);
        StyleConstants.setFontFamily(stylesetAk, "Courier New");
        StyleConstants.setBold(stylesetAk, true);
        StyleConstants.setForeground(stylesetAk, Color.GRAY);
        StyleConstants.setAlignment(stylesetAk, StyleConstants.ALIGN_CENTER);
        StyleConstants.setLineSpacing(stylesetAk, -0.45f);

        boardDoc = tetrisArea.getStyledDocument();
        nextDoc = nextArea.getStyledDocument();
        attackDoc = attackArea.getStyledDocument();

        attackLine = new ArrayList<Integer>();

        placeBlock();
        drawBoard();
        placeNext();
        drawNext();

        timer.start();

        changeSize(sizeNumber);
        System.out.println(sizeNumber);
    }

    public Block getRandomBlock(int modeChoose) {
        switch (modeChoose) {
            case 1:
                min = 1;
                max = 100;
                percentage = Math.random() * (max - min) + min;
                if (percentage <= (double) 100 / 720 * 100 * 1.2)
                    return new IBlock();
                else {
                    rnd = new Random(System.currentTimeMillis());
                    block = rnd.nextInt(6);
                    switch (block) {
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
                block = (int) (Math.random() * 7);
                switch (block) {
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
                if (percentage <= (double) 100 / 680 * 100 * 0.8)
                    return new IBlock();
                else {
                    block = (int) (Math.random() * 6);
                    switch (block) {
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
        for (int j = 0; j < curr.height(); j++) {
            for (int i = 0; i < curr.width(); i++) {
                if (curr.getShape(i, j) > 0) {
                    board[y + j][x + i] = curr.getShape(i, j);
                }
            }
        }
    }

    public void placeNext() {
        for (int j = 0; j < next.height(); j++) {
            for (int i = 0; i < next.width(); i++) {
                nextBoard[nextY + j][nextX + i] = next.getShape(i, j);
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
        for (int i = x; i < x + curr.width(); i++) {
            for (int j = y; j < y + curr.height(); j++) {
                if (curr.getShape(i - x, j - y) > 0) {
                    board[j][i] = 0;
                }
            }
        }
    }

    public void eraseNext() {
        for (int i = nextX; i < nextX + next.width(); i++) {
            for (int j = nextY; j < nextY + next.height(); j++) {
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
            } else {
                notRemove++;
            }
        }
        attackY = 9;
    }

    ArrayList<Integer> line = new ArrayList<Integer>();

    public ArrayList<Integer> lineCheck() {
        ArrayList<Integer> Item = new ArrayList<Integer>();
        int count;
        for (int i = 0; i < HEIGHT; i++) {
            count = 0;
            for (int j = 0; j < WIDTH; j++)
                if (board[i][j] > 0) {
                    count++;
                }

            if (count == WIDTH) Item.add(i);
        }
        return Item;
    }


    public void collisionOccur() {
        saveBoard();
        lastBlock = curr;
        lastX = x;
        lastY = y;
        if (itemDrop == true) {
            itemX = x + getItemX();
            itemY = y + getItemY();
            switch (itemType) {
                case 8: //LR
                    lRItem();
                    break;
                case 9:
                    curr.getInitBlock(curr);
                    placeBlock();
                    drawBoard();
                    break;
                case 10:
                    blockFix = false;
                    curr.getInitBlock(curr);
                    placeBlock();
                    drawBoard();
                    break;
                case 11://CRI
                    cRItem();
                    break;
                case 12:
                    blockFix = false;
                    break;

            }
            itemDrop = false;
            itemFlag = false;
        }
        curr = next;
        x = 3;
        y = 0;
        if (isGameOver() == true) {
            ItemBattleBoard.gameStop();
            String[] overOption = {"종료하기", "다시하기"};
            String winner;
            if (name == "Player1") {
                winner = "Player2";
            } else
                winner = "Player1";
            int over = JOptionPane.showOptionDialog(null, winner + "이(가) 게임에서 승리했습니다!", "종료", 0, 0, null, overOption, overOption[0]);
            if (over == 0) {
                ItemBattleBoard.gameClose();
            }
            if (over == 1) {
                blockFix = false;
                notMove = false;
                itemFlag = false;
                itemDrop = false;
                itemApplied = false;
                ItemBattleBoard.gameReset();
            }
        } else {
            eraseNext();
            next = getRandomBlock(setting.modeChoose);
            placeNext();
            drawNext();
        }
        if (itemApplied == true) {
            itemDrop = true;
            switch (itemType) {
                case 10:
                    blockFix = true;
                    break;
                case 12:
                    blockFix = true;
                    break;
            }
            itemApplied = false;
        }
    }

    public void lineRemove() {
        itemFlag = false;
        line = lineCheck();
        if (line.size() > 1) {
            whoIs = true;
            ItemBattleBoard.placeAttack(line);
            eraseLast();
            ItemBattleBoard.drawAttack();
            attackLine.clear();
        }
        Iterator<Integer> iter = line.iterator();
        int index = 0;
        while (iter.hasNext()) {
            index = iter.next();
            for (int i = index; i > 1; i--) {
                for (int j = 0; j < WIDTH; j++) {
                    board[i][j] = board[i - 1][j];
                }
            }
            eraseCnt++;
            getScore(eraseCnt, "line");
            setScore();
            if ((eraseCnt != 0) && (eraseCnt % 10 == 0))
                itemFlag = true;
        }
        if (itemFlag == true) {
            eraseNext();
            itemSet();
            placeNext();
            drawNext();
            itemFlag = false;
            itemApplied = true;
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
                    if (checkRight > 0) {
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
                    if (checkLeft > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void moveDown() {
        eraseCurr();

        getScore(eraseCnt, "block");
        setScore();

        if (itemDrop && itemType == 12) {
            if (collisionLeft() || collisionRight() || collisionBottom()) {
                notMove = true;
            }
            if (y < 18) {
                y++;
            } else {
                for (int i = 18; i < 20; i++) {
                    for (int j = x; j < x + curr.width(); j++) {
                        board[i][j] = 0;
                    }
                }
                curr = next;
                eraseNext();
                next = getRandomBlock(setting.modeChoose);
                placeNext();
                drawNext();
                x = 3;
                y = 0;
                notMove = false;
                blockFix = false;
                itemType = 0;
                itemFlag = false;
                itemDrop = false;
            }
            eraseCurr();
            placeBlock();
            drawBoard();
        } else {
            if (collisionBottom()) {
                lineRemove();
                collisionOccur();
                if (whoAttacked) {
                    attackedFunction();
                    ItemBattleBoard.drawEmptyAttack();
                    placeBlock();
                    drawBoard();
                }
            } else {
                y++;
                lineRemove();
            }
            if (!isGameOver()) {
                placeBlock();
                drawBoard();
            }
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
        ItemBattleBoard.forAttack();
        attackBoardFull = false;
    }

    public void moveRight() {
        eraseCurr();
        if (x < WIDTH - curr.width() && collisionRight() == false) x++;
        placeBlock();
    }

    public void moveLeft() {
        eraseCurr();
        if (x > 0 && collisionLeft() == false) x--;
        placeBlock();
    }

    public void drawBoard() {
        StringBuffer sb = new StringBuffer();
        for (int t = 0; t < WIDTH + 2; t++) sb.append(BORDER_CHAR);
        sb.append("\n");
        for (int i = 0; i < board.length; i++) {
            sb.append(BORDER_CHAR);
            for (int j = 0; j < board[i].length; j++) {
                int blockType = board[i][j];
                switch (blockType) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7, 13:
                        sb.append("■");
                        break;
                    case 8:
                        sb.append("L");
                        break;
                    case 9:
                        sb.append("●");
                        break;
                    case 10:
                        sb.append("×");
                        break;
                    case 11:
                        sb.append("C");
                        break;
                    case 12:
                        sb.append("O");
                        break;
                    default:
                        sb.append(" ");

                }
            }
            sb.append(BORDER_CHAR);
            sb.append("\n");
        }

        for (int t = 0; t < WIDTH + 2; t++) sb.append(BORDER_CHAR);
        tetrisArea.setText(sb.toString());
        boardDoc.setParagraphAttributes(1, boardDoc.getLength() - 1, stylesetBr, false);
        boardDoc.setCharacterAttributes(0, boardDoc.getLength(), stylesetBr, false);

        for (int j = 0; j < curr.height(); j++) {
            int rows = y + j == 0 ? 1 : y + j + 1;
            int offset = rows * (WIDTH + 3) + x + 1;
            for (int i = 0; i < curr.width(); i++) {
                if (curr.getShape(i, j) > 0 && curr.getShape(i, j) < 8) {
                    colorBlindModeCurrent(offset + i);
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            int offset = (i + 1) * (WIDTH + 3) + 1;
            for (int j = 0; j < board[0].length; j++) {
                int block = board[i][j];
                switch (block) {
                    case 1:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(0, 58, 97));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        } else {
                            StyleConstants.setForeground(stylesetCur, Color.CYAN);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 2:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(126, 98, 61));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        } else {
                            StyleConstants.setForeground(stylesetCur, Color.BLUE);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 3:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(165, 148, 159));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        } else {
                            StyleConstants.setForeground(stylesetCur, Color.PINK);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 4:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(187, 190, 242));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        } else {
                            StyleConstants.setForeground(stylesetCur, Color.YELLOW);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 5:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(247, 193, 121));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        } else {
                            StyleConstants.setForeground(stylesetCur, Color.GREEN);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 6:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(154, 127, 112));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        } else {
                            StyleConstants.setForeground(stylesetCur, Color.MAGENTA);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                    case 7:
                        if (setting.colorBlindModeCheck == 1) {
                            StyleConstants.setForeground(stylesetCur, new Color(99, 106, 141));
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        } else {
                            StyleConstants.setForeground(stylesetCur, Color.RED);
                            boardDoc.setCharacterAttributes(offset + j, 1, stylesetCur, true);
                        }
                        break;
                }

            }
        }
    }

    public void drawNext() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        sb.append("\n");
        blockNumber++;
        timer.setDelay(getInterval(blockNumber, eraseCnt));
        for (int i = 0; i < nextBoard.length; i++) {
            for (int j = 0; j < nextBoard[i].length; j++) {
                int nextBlock = nextBoard[i][j];
                switch (nextBlock) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        sb.append("■");
                        break;
                    case 8:
                        sb.append("L");
                        break;
                    case 9:
                        sb.append("●");
                        break;
                    case 10:
                        sb.append("×");
                        break;
                    case 11:
                        sb.append("C");
                        break;
                    case 12:
                        sb.append("O");
                        break;
                    case 13:
                        sb.append("■");
                        break;
                    default:
                        sb.append(" ");
                }
            }
            sb.append("\n");
        }
        nextArea.setText(sb.toString());
        colorBlindModeNext();


        for (int i = 0; i < nextBoard.length; i++) {
            int offset = i * 6 + 2;
            for (int j = 0; j < nextBoard[0].length; j++) {
                int nextBlock = nextBoard[i][j];
                if (nextBlock > 7) {
                    StyleConstants.setForeground(stylesetNx, Color.WHITE);
                    nextDoc.setCharacterAttributes(offset + j, 1, stylesetNx, true);
                }
            }
        }
    }

    public void itemSet() {
        Random rnd = new Random(System.currentTimeMillis());
        itemType = rnd.nextInt(5) + 8;
        System.out.println(itemType);
        switch (itemType) {
            case 8://LRemoveBlock
                LRemoveBlock LR = new LRemoveBlock(next);
                next = LR.getItemBlock();
                break;
            case 9:
                next.setShape(new int[][]{{9}});
                OneBlock OB = new OneBlock(next);
                next = OB.getItemBlock();
                break;
            case 10:
                FixedBlock FR = new FixedBlock(next);
                next = FR.getItemBlock();
                break;
            case 11:
                CRemoveBlock CR = new CRemoveBlock(next);
                next = CR.getItemBlock();
                break;
            case 12:
                next.setShape(new int[][]{
                        {0, 12, 12, 0},
                        {12, 12, 12, 12}
                });
                WeightBlock WB = new WeightBlock(next);
                next = WB.getItemBlock();
                break;
        }
    }

    public void lRItem() {
        line = new ArrayList<Integer>() {{
            add(itemY);
        }};
        Iterator<Integer> iter = line.iterator();
        int index = 0;
        while (iter.hasNext()) {
            index = iter.next();
            for (int i = index; i > 1; i--) {
                for (int j = 0; j < WIDTH; j++) {
                    board[i][j] = board[i - 1][j];
                }
            }
            eraseCnt++;
            if ((eraseCnt != 0) && (eraseCnt % 10 == 0))
                itemFlag = true;
        }
        if (itemFlag == true) {
            eraseNext();
            itemSet();
            placeNext();
            drawNext();
            itemFlag = false;
            itemApplied = true;
        }
    }

    public void cRItem() {
        for (int i = 0; i < HEIGHT; i++) {
            board[i][itemX] = 0;
        }
        lRItem();
    }

    public int getItemX() {
        for (int i = 0; i < curr.height(); i++) {
            for (int j = 0; j < curr.width(); j++) {
                if (curr.getShape(j, i) > 7)
                    return j;
            }
        }
        return 0;
    }

    public int getItemY() {
        for (int i = 0; i < curr.height(); i++) {
            for (int j = 0; j < curr.width(); j++) {
                if (curr.getShape(j, i) > 7)
                    return i;
            }
        }
        return 0;
    }

    public int getBoardVal(int i, int j) {
        return board[i][j];
    }

    public void setBoardVal(int i, int j, int val) {
        board[i][j] = val;
    }

    public void colorBlindMode(SimpleAttributeSet styleSet, Block block) {
        if (setting.colorBlindModeCheck == 1) {
            StyleConstants.setForeground(styleSet, block.getColorBlind());
        } else {
            StyleConstants.setForeground(styleSet, block.getColor());
        }
    }

    public void colorBlindModeNext() {
        colorBlindMode(stylesetNx, next);
        nextDoc.setParagraphAttributes(0, nextDoc.getLength(), stylesetNx, false);
    }

    public void colorBlindModeCurrent(int offset) {
        colorBlindMode(stylesetCur, curr);
        boardDoc.setCharacterAttributes(offset, 1, stylesetCur, true);
    }

    //interval 함수
    public int getInterval(int blockNumber, int eraseCnt) {
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
        if (intervalByMode == 1000) {
            if (eraseCnt < 5 && eraseCnt >= 0) {
                intervalByModeForChange = 1000;
                level = 1;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 10 && eraseCnt >= 5) {
                intervalByModeForChange = (int) (1000 * 0.9);
                level = 2;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 15 && eraseCnt >= 10) {
                intervalByModeForChange = (int) (1000 * 0.9 * 0.9);
                level = 3;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 20 && eraseCnt >= 15) {
                intervalByModeForChange = (int) (1000 * 0.9 * 0.9 * 0.9);
                level = 4;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 25 && eraseCnt >= 20) {
                intervalByModeForChange = (int) (1000 * 0.9 * 0.9 * 0.9 * 0.9);
                level = 5;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 30 && eraseCnt >= 25) {
                intervalByModeForChange = (int) (1000 * 0.9 * 0.9 * 0.9 * 0.9 * 0.9);
                level = 6;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt >= 30) {
                intervalByModeForChange = (int) (1000 * 0.9 * 0.9 * 0.9 * 0.9 * 0.9 * 0.9);
                level = 7;
                levelLb2.setText(Integer.toString(level));
            }
        } else if (intervalByMode == 2000) {
            if (eraseCnt < 5 && eraseCnt >= 0) {
                intervalByModeForChange = 2000;
                level = 1;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 10 && eraseCnt >= 5) {
                intervalByModeForChange = (int) (2000 * 0.92);
                level = 2;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 15 && eraseCnt >= 10) {
                intervalByModeForChange = (int) (2000 * 0.92 * 0.92);
                level = 3;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 20 && eraseCnt >= 15) {
                intervalByModeForChange = (int) (2000 * 0.92 * 0.92 * 0.92);
                level = 4;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 25 && eraseCnt >= 20) {
                intervalByModeForChange = (int) (2000 * 0.92 * 0.92 * 0.92 * 0.92);
                level = 5;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 30 && eraseCnt >= 25) {
                intervalByModeForChange = (int) (2000 * 0.92 * 0.92 * 0.92 * 0.92 * 0.92);
                level = 6;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt >= 30) {
                intervalByModeForChange = (int) (2000 * 0.92 * 0.92 * 0.92 * 0.92 * 0.92 * 0.92);
                level = 7;
                levelLb2.setText(Integer.toString(level));
            }
        } else if (intervalByMode == 800) {
            if (eraseCnt < 5 && eraseCnt >= 0) {
                intervalByModeForChange = 800;
                level = 1;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 10 && eraseCnt >= 5) {
                intervalByModeForChange = (int) (800 * 0.88);
                level = 2;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 15 && eraseCnt >= 10) {
                intervalByModeForChange = (int) (800 * 0.88 * 0.88);
                level = 3;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 20 && eraseCnt >= 15) {
                intervalByModeForChange = (int) (800 * 0.88 * 0.88 * 0.88);
                level = 4;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 25 && eraseCnt >= 20) {
                intervalByModeForChange = (int) (800 * 0.88 * 0.88 * 0.88 * 0.88);
                level = 5;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt < 30 && eraseCnt >= 25) {
                intervalByModeForChange = (int) (800 * 0.88 * 0.88 * 0.88 * 0.88 * 0.88);
                level = 6;
                levelLb2.setText(Integer.toString(level));
            } else if (eraseCnt >= 30) {
                intervalByModeForChange = (int) (800 * 0.88 * 0.88 * 0.88 * 0.88 * 0.88 * 0.88);
                level = 7;
                levelLb2.setText(Integer.toString(level));
            }
        }
        System.out.println("Created : " + blockNumber + "   Removed : " + eraseCnt + "   intervalByMode" + intervalByMode + "   interval Number : " + intervalByModeForChange);
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
        this.score = 0;
        this.setScore();
        level = 0;
        blockNumber = 0;
        eraseCnt = 0;
        intervalByMode = setting.intervalNumber;
        this.board = new int[20][10];
        blockFix = false;
        notMove = false;
        itemFlag = false;
        itemDrop = false;
        itemApplied = false;
    }

    public boolean startCheck() {
        for (int i = 0; i < curr.height(); i++) {
            for (int j = 0; j < curr.width(); j++)
                if (curr.getShape(j, i) != 0 && board[y + i][x + j] > 0)
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


    public boolean rotateTest(int[][] shape, int inputX, int inputY) {
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

        int[][] testShape = curr.getRotateShape();
        int testX = (x + curr.width()) - testShape[0].length;
        int testY = (y + curr.height()) - testShape.length;

        if (!rotateTest(testShape, x, y)) {
            curr.rotate();
        } else if (testY >= 0 && !rotateTest(testShape, x, testY)) {
            y = testY;
            curr.rotate();
        } else if (testX >= 0 && !rotateTest(testShape, testX, y)) {
            x = testX;
            curr.rotate();
        }

        placeBlock();
        drawBoard();
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

    public void setStylesetSize(int size1, int size2, int size3) {
        StyleConstants.setFontSize(stylesetBr, size1);
        StyleConstants.setFontSize(stylesetCur, size1);
        StyleConstants.setFontSize(stylesetNx, size3);
        StyleConstants.setFontSize(stylesetAk, size2);
        drawBoard();
        drawNext();
    }

    //max - (200, 60), default - (150, 50)
    public void setRtSize(int xSize, int ySize, int ySize2) {
        scorePanel.setPreferredSize(new Dimension(xSize, ySize));
        levelPanel.setPreferredSize(new Dimension(xSize, ySize));
        nextArea.setPreferredSize(new Dimension(xSize, xSize));
        attackArea.setPreferredSize(new Dimension(ySize2, ySize2));
    }

    //max - 17, default - nothing,
    public void setLbSize(int size) {
        scoreLb1.setFont(new Font(null, Font.BOLD, size));
        scoreLb2.setFont(new Font(null, Font.BOLD, size));
        levelLb1.setFont(new Font(null, Font.BOLD, size));
        levelLb2.setFont(new Font(null, Font.BOLD, size));
    }

    @Override
    public void changeSize(int sizeNumber) {
        switch (sizeNumber) {
            case 1:
                setStylesetSize(20, 8, 8);
                setRtSize(40, 50, 50);
                setLbSize(8);
                tetrisArea.setPreferredSize(new Dimension(180, 330));
                break;
            case 2:
                setStylesetSize(35, 11, 20);
                setRtSize(120, 50, 120);
                setLbSize(13);
                tetrisArea.setPreferredSize(new Dimension(300, 550));
                break;
            case 3:
                setStylesetSize(35, 22, 36);
                setRtSize(200, 55, 200);
                setLbSize(17);
                tetrisArea.setPreferredSize(new Dimension(300, 550));
                break;
            default:
                setStylesetSize(25, 13, 20);
                setRtSize(120, 50, 120);
                setLbSize(10);
                tetrisArea.setPreferredSize(new Dimension(220, 400));
                break;
        }
    }
}