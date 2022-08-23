package se.tetris.component.boardui;

import se.tetris.blocks.*;
import se.tetris.component.Sizeable;
import se.tetris.component.boardlogic.*;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static se.tetris.setting.SettingCode.screenHeight;
import static se.tetris.setting.SettingCode.screenWidth;

public class BoardTetrisArea extends JPanel implements Sizeable {
    private JTextPane tetrisArea;
    private SimpleAttributeSet stylesetBr;
    private SimpleAttributeSet stylesetCur;
    private StyledDocument boardDoc;
    private final int HEIGHT = 20;
    private final int WIDTH = 10;
    private Block curr;
    private int[][] board;
    private int x = 3; //Default Position.
    private int y = 0;
    private final char BORDER_CHAR = 'X';
    //int eraseCnt = 0;
    private RandomBlock randomBlock = new RandomBlock();
    private final StrategyPattern strategyPattern;

    BoardValues boardValues = BoardValues.getInstance();
    int mode = boardValues.mode;

    final SettingValues setting = SettingValues.getInstance();

    public BoardTetrisArea() {
        tetrisArea = new JTextPane();
        tetrisArea.setEditable(false);
        tetrisArea.setBackground(Color.BLACK);
        tetrisArea.setBorder(boardValues.getBorder());
        tetrisArea.setAlignmentX(CENTER_ALIGNMENT);
        tetrisArea.setAlignmentY(CENTER_ALIGNMENT);

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
        StyleConstants.setLineSpacing(stylesetCur, -0.45f);

        boardDoc = tetrisArea.getStyledDocument();

        add(tetrisArea);

        strategyPattern = new StrategyPattern();
        if (setting.colorBlindModeCheck == 1) {
            strategyPattern.setColorBlindnessStrategy(new ColorBlindnessColorBlindStrategy());
        } else {
            strategyPattern.setColorBlindnessStrategy(new ColorBlindnessColorStrategy());
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void increaseY() {
        this.y++;
    }

    private void setStylesetSize(int size) {
        StyleConstants.setFontSize(stylesetBr, size);
        StyleConstants.setFontSize(stylesetCur, size);
        drawBoard();
    }

    @Override
    public void changeSize(int sizeNumber) {
        switch (sizeNumber) {
            case 1:
                this.setStylesetSize(30);
                tetrisArea.setPreferredSize(new Dimension(250, 460));
                break;
            case 2:
                this.setStylesetSize(50);
                tetrisArea.setPreferredSize(new Dimension(400, 750));
                break;
            case 3:
                this.setStylesetSize(50);
                tetrisArea.setPreferredSize(new Dimension(400, 750));
                break;
            default:
                this.setStylesetSize(30);
                tetrisArea.setPreferredSize(new Dimension(220, 400));
                break;
        }
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
            boardValues.increaseEraseCnt();
            BoardLocator.getInstance().getScorePanel().getScore(boardValues.getEraseCnt(), "line");
            BoardLocator.getInstance().getScorePanel().setScore();
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

    public void collisionOccur() {
        saveBoard();
        setCurr(BoardLocator.getInstance().getBoardNextArea().getNext());
        x = 3;
        y = 0;
        if (isGameOver() == true) {
            BoardLocator.getInstance().getBoardTimer().boardTimerStop();
            boolean result = BoardLocator.getInstance().getScorePanel().showDialog(BoardLocator.getInstance().getScorePanel().getNowScore(), 0, mode);
            setVisible(result);
            //종료 화면과 잇기
        } else {
            BoardLocator.getInstance().getBoardNextArea().eraseNext();
            BoardLocator.getInstance().getBoardNextArea().setNext(randomBlock.getRandomBlock(setting.modeChoose));
            BoardLocator.getInstance().getBoardNextArea().placeNext();
            BoardLocator.getInstance().getBoardNextArea().drawNext();
        }
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
        BoardLocator.getInstance().getScorePanel().getScore(boardValues.getEraseCnt(), "block");
        BoardLocator.getInstance().getScorePanel().setScore();
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
                    strategyPattern.colorBlindModeCurrent(offset + i, stylesetCur, boardDoc, curr);
                }
            }
        }
        for (int i = 0; i < board.length; i++) {
            int offset = (i + 1) * (WIDTH + 3) + 1;
            for (int j = 0; j < board[0].length; j++) {
                int block = board[i][j];
                strategyPattern.colorBlock(block, stylesetCur, boardDoc, offset, j);
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

    private void colorBlindMode(SimpleAttributeSet styleSet, Block block) {
        strategyPattern.colorBlindMode(styleSet, block);
    }

    private void colorBlindModeCurrent(int offset) {
        strategyPattern.colorBlindModeCurrent(offset, stylesetCur, boardDoc, curr);
    }

    public void setCurr(Block curr) {
        this.curr = curr;
    }

    public void resetBoard() {
        board = new int[HEIGHT][WIDTH];
    }

    public void changeCurr() {
        curr = randomBlock.getRandomBlock(setting.modeChoose);
    }

    public void defaultDocumentStyle() {
        boardDoc = tetrisArea.getStyledDocument();
    }
}
