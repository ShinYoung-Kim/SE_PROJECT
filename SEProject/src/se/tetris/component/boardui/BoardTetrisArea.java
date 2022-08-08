package se.tetris.component.boardui;

import se.tetris.blocks.*;
import se.tetris.component.Sizeable;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static se.tetris.component.Board.nextArea;
import static se.tetris.component.Board.scoreItem;
import static se.tetris.setting.SettingCode.screenHeight;
import static se.tetris.setting.SettingCode.screenWidth;

public class BoardTetrisArea extends JTextPane implements Sizeable {
    private static JTextPane tetrisArea;
    private static SimpleAttributeSet stylesetBr;
    private static SimpleAttributeSet stylesetCur;
    private static StyledDocument boardDoc;
    public static final int HEIGHT = 20;
    public static final int WIDTH = 10;
    public static Block curr;
    public int[][] board;
    static int x = 3; //Default Position.
    static int y = 0;
    public static final char BORDER_CHAR = 'X';
    int eraseCnt = 0;

    double min;
    double max;
    double percentage;
    double weighted;
    int block;

    final SettingValues setting = SettingValues.getInstance();

    public BoardTetrisArea() {
        tetrisArea = new JTextPane();
        tetrisArea.setEditable(false);
        tetrisArea.setBackground(Color.BLACK);
        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        tetrisArea.setBorder(border);
        tetrisArea.setAlignmentX(CENTER_ALIGNMENT);
        tetrisArea.setAlignmentY(CENTER_ALIGNMENT);

        board = new int[HEIGHT][WIDTH];

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
        StyleConstants.setLineSpacing(stylesetCur, -0.45f);

        boardDoc = tetrisArea.getStyledDocument();

        curr = getRandomBlock(setting.modeChoose);
    }
    @Override
    public void changeSize(int sizeNumber) {
        switch (sizeNumber) {
            case 1:
                setStylesetSize(30, 25, 20);
                tetrisArea.setPreferredSize(new Dimension(250, 460));
                break;
            case 2:
                setStylesetSize(50, 50, 45);
                tetrisArea.setPreferredSize(new Dimension(400, 750));
                break;
            case 3:
                setStylesetSize(50, 50, 45);
                tetrisArea.setPreferredSize(new Dimension(400, 750));
                break;
            default:
                setStylesetSize(30, 25, 25);
                tetrisArea.setPreferredSize(new Dimension(220, 400));
                break;
        }
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

    ArrayList<Integer> lineCheck() {
        ArrayList<Integer> Item = new ArrayList<Integer>();
        int count;
        for (int i = 0; i < HEIGHT; i++) {
            count = 0;
            for (int j = 0; j < WIDTH; j++)
                if (board[i][j] > 0) {
                    count++;
                }

            if (count == WIDTH) {
                Item.add(i);
            }
        }
        return Item;
    }

    public void collisionOccur() {
        saveBoard();
        curr = nextArea.next;
        x = 3;
        y = 0;
        if (isGameOver() == true) {
            timer.stop();
            boolean result = scoreItem.showDialog(getNowScore(), 0, mode);
            setVisible(result);
            //종료 화면과 잇기
        } else {
            nextArea.eraseNext();
            nextArea.next = getRandomBlock(setting.modeChoose);
            nextArea.placeNext();
            nextArea.drawNext();
        }
    }

    public void hey() {
        line = lineCheck();
        Iterator<Integer> iter = line.iterator();
        int index = 0;

        while (iter.hasNext()) {
            index = iter.next();
            lineRemoveDelay(index);
            index = 0;
        }
        Timer delayTimer;
        int aniDelay = 20;
        delayTimer = new Timer(aniDelay, e -> placeBlock());
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    void lineRemove() {
        line = lineCheck();
        Iterator<Integer> iter = line.iterator();
        int index = 0;
        while (iter.hasNext()) {
            index = iter.next();
            for (int i = index; i > 1; i--) {
                for (int j = 0; j < WIDTH; j++) {
                    board[i][j] = board[i - 1][j];
                }
            }
            index = 0;
            eraseCnt++;
            getScore(eraseCnt, "line");
            setScore();
        }
    }

    private void lineRemoveDelay(int line) {
        Timer aniTimer;
        int aniDelay = 20;
        for (int cnt = 0; cnt < 10; cnt++) {
            if (cnt % 2 == 0)
                aniTimer = new Timer(cnt * aniDelay, e -> removedLinePaint(line, Color.ORANGE));
            else
                aniTimer = new Timer(cnt * aniDelay, e -> removedLinePaint(line, Color.BLACK));
            aniTimer.setRepeats(false);
            aniTimer.start();
        }
    }

    private void removedLinePaint(int line, Color color) {
        SimpleAttributeSet removedLineDoc = new SimpleAttributeSet();
        StyleConstants.setForeground(removedLineDoc, color);
        StyleConstants.setFontSize(removedLineDoc, 30);
        StyleConstants.setFontFamily(removedLineDoc, "Courier New");
        StyleConstants.setBold(removedLineDoc, true);
        StyleConstants.setAlignment(removedLineDoc, StyleConstants.ALIGN_CENTER);
        boardDoc.setCharacterAttributes((line + 1) * (WIDTH + 3) + 1, WIDTH, removedLineDoc, true);
        lineRemove();
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
        if (collisionBottom()) {
            collisionOccur();
        } else y++;
        if (!isGameOver()) {
            placeBlock();
            drawBoard();
        }
        hey();
        getScore(eraseCnt, "block");
        setScore();
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
        tetrisArea.setText(sb.toString());
        boardDoc.setParagraphAttributes(0, boardDoc.getLength(), stylesetBr, false);

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

    public void setStylesetSize(int size1, int size2, int size3) {
        StyleConstants.setFontSize(stylesetBr, size1);
        StyleConstants.setFontSize(stylesetCur, size1);
        drawBoard();
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
}
