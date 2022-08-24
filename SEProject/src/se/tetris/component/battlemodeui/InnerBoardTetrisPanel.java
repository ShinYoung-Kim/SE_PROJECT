package se.tetris.component.battlemodeui;

import se.tetris.blocks.Block;
import se.tetris.component.BattleBoard;
import se.tetris.component.ScoreItem;
import se.tetris.component.Sizeable;
import se.tetris.component.TimeBattleBoard;
import se.tetris.component.battlemodelogic.BattleBoardLocator;
import se.tetris.component.battlemodelogic.ObserveInterface;
import se.tetris.component.battlemodelogic.ProxyInnerBoardUI;
import se.tetris.component.boardlogic.BoardLocator;
import se.tetris.component.boardlogic.BoardValues;
import se.tetris.component.boardlogic.RandomBlock;
import se.tetris.component.boardui.BoardTetrisArea;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class InnerBoardTetrisPanel extends BoardTetrisArea implements Sizeable, ObserveInterface {
    private final int HEIGHT = 20;
    private final int WIDTH = 10;
    private final char BORDER_CHAR = 'X';

    private JTextPane tetrisPanel;
    private int[][] board;

    private SimpleAttributeSet stylesetBr;
    private SimpleAttributeSet stylesetCur;
    private StyledDocument boardDoc;

    private double min;
    private double max;
    private double percentage;
    private Random rnd;
    private int block;

    private Block curr;
    private int x = 3; //Default Position.
    private int y = 0;

    private int mode = 0;
    private RandomBlock randomBlock = new RandomBlock();

    public final SettingValues setting = SettingValues.getInstance();
    public int intervalByMode = setting.intervalNumber;
    public int intervalByModeForChange = setting.intervalNumber;
    private final BoardValues values = BoardValues.getInstance();

    private boolean whoIs = false;
    private boolean whoAttacked = false;
    private int attackLineCount = 0;

    private boolean alreadyAttacked = false;
    private boolean attackBoardFull = false;

    private BattleBoardLocator battleBoardLocator;
    private ProxyInnerBoardUI proxyInnerBoardUI = new ProxyInnerBoardUI(battleBoardLocator.getInnerBoardUI());

    public InnerBoardTetrisPanel() {
        //UI
        tetrisPanel = new JTextPane();
        tetrisPanel.setEditable(false);
        tetrisPanel.setBackground(Color.BLACK);
        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        tetrisPanel.setBorder(border);
        tetrisPanel.setAlignmentX(CENTER_ALIGNMENT);
        tetrisPanel.setAlignmentY(CENTER_ALIGNMENT);

        add(tetrisPanel);

        //초기 세팅
        board = new int[HEIGHT][WIDTH];
        curr = randomBlock.getRandomBlock(setting.modeChoose);

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

        boardDoc = tetrisPanel.getStyledDocument();
    }

    public void placeBlock() {
        for (int j = 0; j < curr.height(); j++) {
            for (int i = 0; i < curr.width(); i++) {
                if (curr.getShape(i, j) > 0)
                    board[y + j][x + i] = curr.getShape(i, j);
            }
        }
    }

    public void eraseCurr() {
        for (int i = x; i < x + curr.width(); i++) {
            for (int j = y; j < y + curr.height(); j++) {
                if (curr.getShape(i - x, j - y) > 0)
                    board[j][i] = 0;
            }
        }
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
        String name = proxyInnerBoardUI.getName();
        String BattleMode = proxyInnerBoardUI.getBattleMode();

        InnerBoardAttackPanel attackPanel = battleBoardLocator.getAttackPanel();
        InnerBoardNextPanel nextPanel = battleBoardLocator.getBoardNextArea();

        saveBoard();
        attackPanel.setLastBlock(curr);
        attackPanel.setLastX(x);
        attackPanel.setLastY(y);
        curr = nextPanel.getNext();
        x = 3;
        y = 0;
        if (isGameOver() == true) {
            String winner;
            if (name == "Player1") {
                winner = "Player2";
            } else
                winner = "Player1";

            if (BattleMode == "Battle") {
                BattleBoard.gameStop();
            } else if (BattleMode == "TimeBattle") {
                TimeBattleBoard.gameStop();
                TimeBattleBoard.collisionStop();
                TimeBattleBoard.ColPlayer = winner;
                return;
            }

            String[] overOption = {"종료하기", "다시하기"};

            int over = JOptionPane.showOptionDialog(null, winner + "이(가) 게임에서 승리했습니다!", "종료", 0, 0, null, overOption, overOption[0]);

            if (BattleMode == "Battle") {
                if (over == 0) {
                    BattleBoard.gameClose();
                }
                if (over == 1) {
                    BattleBoard.gameReset();
                }
            }
        } else {
            nextPanel.eraseNext();
            nextPanel.setNext(randomBlock.getRandomBlock(setting.modeChoose));
            nextPanel.placeNext();
            nextPanel.drawNext();
        }
    }

    public void lineRemove() {
        InnerBoardAttackPanel attackPanel = battleBoardLocator.getAttackPanel();
        InnerBoardScorePanel scorePanel = battleBoardLocator.getScorePanel();

        line = lineCheck();
        if (line.size() > 1) {
            whoIs = true;
            BattleBoard.placeAttack(line);
            attackPanel.eraseLast();
            BattleBoard.drawAttack();
            attackPanel.attackLineClear();
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
            values.increaseEraseCnt();
            scorePanel.getScore(values.getEraseCnt(), "line");
            scorePanel.setScore();
        }
    }

    public boolean collisionBottom() {
        for (int i = 0; i < curr.height(); i++) {
            for (int j = 0; j < curr.width(); j++) {
                if (y >= HEIGHT - curr.height())
                    return true;
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
        InnerBoardScorePanel scorePanel = battleBoardLocator.getScorePanel();

        eraseCurr();

        scorePanel.getScore(values.getEraseCnt(), "block");
        scorePanel.setScore();

        if (collisionBottom()) {
            lineRemove();
            collisionOccur();
            if (whoAttacked) {
                attackedFunction();
                BattleBoard.drawEmptyAttack();
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
                if (board[i][j] > 0) {
                    sb.append("■");
                } else {
                    sb.append(" ");
                }
            }
            sb.append(BORDER_CHAR);
            sb.append("\n");
        }
        for (int t = 0; t < WIDTH + 2; t++) sb.append(BORDER_CHAR);
        tetrisPanel.setText(sb.toString());
        boardDoc.setParagraphAttributes(1, boardDoc.getLength() - 1, stylesetBr, false);
        boardDoc.setCharacterAttributes(1, boardDoc.getLength() - 1, stylesetBr, false);

        for (int j = 0; j < curr.height(); j++) {
            int rows = y + j == 0 ? 1 : y + j + 1;
            int offset = rows * (WIDTH + 3) + x + 1;
            for (int i = 0; i < curr.width(); i++) {
                if (curr.getShape(i, j) > 0) {
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

    public void colorBlindMode(SimpleAttributeSet styleSet, Block block) {
        if (setting.colorBlindModeCheck == 1) {
            StyleConstants.setForeground(styleSet, block.getColorBlind());
        } else {
            StyleConstants.setForeground(styleSet, block.getColor());
        }
    }

    public void colorBlindModeCurrent(int offset) {
        colorBlindMode(stylesetCur, curr);
        boardDoc.setCharacterAttributes(offset, 1, stylesetCur, true);
    }

    public boolean startCheck() {
        for (int i = 0; i < curr.height(); i++) {
            for (int j = 0; j < curr.width(); j++)
                if (curr.getShape(j, i) > 0 && board[y + i][x + j] > 0)
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

    public int[][] getBoard() {
        return board;
    }

    public void setAttackLineCount(int attackLineCount) {
        this.attackLineCount = attackLineCount;
    }

    public int getAttackLineCount() {
        return attackLineCount;
    }

    public void setStylesetSize(int size) {
        StyleConstants.setFontSize(stylesetBr, size);
        StyleConstants.setFontSize(stylesetCur, size);
        drawBoard();
    }

    @Override
    public void changeSize(int sizeNumber) {
        switch (sizeNumber) {
            case 1:
                setStylesetSize(20);
                tetrisPanel.setPreferredSize(new Dimension(180, 330));
                break;
            case 2:
                setStylesetSize(35);
                tetrisPanel.setPreferredSize(new Dimension(300, 550));
                break;
            case 3:
                setStylesetSize(35);
                tetrisPanel.setPreferredSize(new Dimension(300, 550));
                break;
            default:
                setStylesetSize(25);
                tetrisPanel.setPreferredSize(new Dimension(220, 400));
                break;
        }
    }

    @Override
    public void updateBattleBoardLocator(BattleBoardLocator battleBoardLocator) {
        this.battleBoardLocator = battleBoardLocator;
    }
}
