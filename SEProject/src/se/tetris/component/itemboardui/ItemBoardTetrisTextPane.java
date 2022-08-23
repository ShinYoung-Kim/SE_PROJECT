package se.tetris.component.itemboardui;

import se.tetris.blocks.Block;
import se.tetris.blocks.OBlock;
import se.tetris.component.Sizeable;
import se.tetris.component.boardlogic.BoardValues;
import se.tetris.component.boardlogic.RandomBlock;
import se.tetris.component.itemboardlogic.ItemBoardLocator;
import se.tetris.component.itemboardlogic.ItemBoardTimer;
import se.tetris.component.itemboardlogic.ItemBoardValues;
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

public class ItemBoardTetrisTextPane extends JPanel implements Sizeable {
    private final JTextPane tetrisTextPane;
    private final char BORDER_CHAR = 'X';
    private final int HEIGHT = 20;
    private final int WIDTH = 10;
    private int[][] board = new int[HEIGHT][WIDTH];
    private StyledDocument boardDoc;
    private Block curr;
    private SimpleAttributeSet stylesetBr;
    private SimpleAttributeSet stylesetCur;
    private int x = 3; //Default Position.
    private int y = 0;
    private boolean itemFlag = false;
    private boolean itemDrop = false;
    private boolean itemApplied = false;
    private boolean blockFix = false;
    private boolean notMove = false;
    private int itemX = 0;
    private int itemY = 0;

    private final SettingValues setting = SettingValues.getInstance();
    private RandomBlock randomBlock = new RandomBlock();
    ItemBoardValues boardValues = ItemBoardValues.getInstance();
    private int mode = boardValues.mode;

    public ItemBoardTetrisTextPane() {
        tetrisTextPane = new JTextPane();
        tetrisTextPane.setEditable(false);
        tetrisTextPane.setBackground(Color.BLACK);
        tetrisTextPane.setBorder(ItemBoardValues.getInstance().getBorder());
        tetrisTextPane.setAlignmentX(CENTER_ALIGNMENT);
        tetrisTextPane.setAlignmentY(CENTER_ALIGNMENT);
        tetrisTextPane.setPreferredSize(new Dimension(250, 460));

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

        boardDoc = tetrisTextPane.getStyledDocument();

        add(tetrisTextPane);
    }

    public void placeBlock() {
        for (int j = 0; j < curr.height(); j++) {
            for (int i = 0; i < curr.width(); i++) {
                if (curr.getShape(i, j) > 0)
                    board[y + j][x + i] = curr.getShape(i, j);
            }
        }
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
                    case 7:
                    case 13:
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
        tetrisTextPane.setText(sb.toString());
        boardDoc.setParagraphAttributes(0, boardDoc.getLength(), stylesetBr, false);

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

            if (count == WIDTH) Item.add(i);
        }
        return Item;
    }

    public void lineRemove() {
        ItemBoardNextTextPane itemBoardNextTextPane = ItemBoardLocator.getInstance().getBoardNextArea();
        ItemBoardScorePanel itemBoardScorePanel = ItemBoardLocator.getInstance().getScorePanel();
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
            boardValues.increaseEraseCnt();
            itemBoardScorePanel.getScore(boardValues.getEraseCnt(), "line");
            itemBoardScorePanel.setScore();
            if ((boardValues.getEraseCnt() != 0) && (boardValues.getEraseCnt() % 10 == 0))
                itemFlag = true;
        }
        if (itemFlag == true) {
            itemBoardNextTextPane.eraseNext();
            itemBoardNextTextPane.itemSet();
            itemBoardNextTextPane.placeNext();
            itemBoardNextTextPane.drawNext();
            itemFlag = false;
            itemApplied = true;
        }
    }

    public void collisionOccur() {
        saveBoard();

        ItemBoardNextTextPane itemBoardNextTextPane = ItemBoardLocator.getInstance().getBoardNextArea();
        ItemBoardScorePanel itemBoardScorePanel = ItemBoardLocator.getInstance().getScorePanel();
        ItemBoardTimer itemBoardTimer = ItemBoardLocator.getInstance().getBoardTimer();

        if (itemDrop == true) {
            itemX = x + getItemX();
            itemY = y + getItemY();
            switch (ItemBoardValues.getInstance().getItemType()) {
                case 8: //LR
                    lRItem();
                    System.out.println("using lritem");
                    break;
                case 9:
                    curr.getInitBlock(curr);
                    placeBlock();
                    drawBoard();
                    System.out.println("using case9");
                    break;
                case 10:
                    blockFix = false;
                    curr.getInitBlock(curr);
                    placeBlock();
                    drawBoard();
                    System.out.println("using case10");
                    break;
                case 11://CRI
                    cRItem();
                    System.out.println("using case11");
                    break;
                case 12:
                    blockFix = false;
                    System.out.println("using case12");
                    break;
            }
            itemDrop = false;
            itemFlag = false;
        }
        curr = itemBoardNextTextPane.getNext();
        x = 3;
        y = 0;
        if (isGameOver() == true) {
            itemBoardTimer.boardTimerStop();
            boolean result = itemBoardScorePanel.showDialog(itemBoardScorePanel.getNowScore(), 1, mode);
            setVisible(result);
            //종료 화면과 잇기
        } else {
            itemBoardNextTextPane.eraseNext();
            itemBoardNextTextPane.setNext(randomBlock.getRandomBlock(setting.modeChoose));
            itemBoardNextTextPane.placeNext();
            itemBoardNextTextPane.drawNext();
        }
        if (itemApplied == true) {
            itemDrop = true;
            switch (ItemBoardValues.getInstance().getItemType()) {
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

    public void moveDown() {
        eraseCurr();
        ItemBoardScorePanel itemBoardScorePanel = ItemBoardLocator.getInstance().getScorePanel();
        ItemBoardNextTextPane itemBoardNextTextPane = ItemBoardLocator.getInstance().getBoardNextArea();
        itemBoardScorePanel.getScore(boardValues.getEraseCnt(), "block");
        itemBoardScorePanel.setScore();

        if (itemDrop && ItemBoardValues.getInstance().getItemType() == 12) {
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
                curr = itemBoardNextTextPane.getNext();
                itemBoardNextTextPane.eraseNext();
                itemBoardNextTextPane.setNext(randomBlock.getRandomBlock(setting.modeChoose));
                itemBoardNextTextPane.placeNext();
                itemBoardNextTextPane.drawNext();
                x = 3;
                y = 0;
                notMove = false;
                blockFix = false;
                ItemBoardValues.getInstance().setItemType(0);
                itemFlag = false;
                itemDrop = false;
            }
            eraseCurr();
            placeBlock();
            drawBoard();
        } else {
            if (collisionBottom()) {
                collisionOccur();
            } else y++;
            lineRemove();
            if (!isGameOver()) {
                placeBlock();
                drawBoard();
            }
        }
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
        for (int i = 0; i < WIDTH; i++) {
            if (board[0][i] == 1) {
                return true;
            }
        }
        return false;
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
                if (j + x > 9) {
                    return true;
                }
                if (curr.getShape(j, i) > 0 && j + x < 9 && i + y < 19) {
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

    public void saveBoard() {
        for (int i = 0; i < curr.height(); i++) {
            for (int j = 0; j < curr.width(); j++) {
                if (curr.getShape(j, i) > 0) {
                    board[y + i][j + x] = curr.getShape(j, i);
                }
            }
        }
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

    public void lRItem() {
        ItemBoardNextTextPane itemBoardNextTextPane = ItemBoardLocator.getInstance().getBoardNextArea();
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
            boardValues.increaseEraseCnt();
            if ((boardValues.getEraseCnt() != 0) && (boardValues.getEraseCnt() % 10 == 0))
                itemFlag = true;
        }
        if (itemFlag) {
            itemBoardNextTextPane.eraseNext();
            itemBoardNextTextPane.setNext(randomBlock.getRandomBlock(setting.modeChoose));
            itemBoardNextTextPane.placeNext();
            itemBoardNextTextPane.drawNext();
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
        boardDoc = tetrisTextPane.getStyledDocument();
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

    public Block getCurr() {
        return curr;
    }

    public void makeBoardZero(int i, int j) {
        board[i][j] = 0;
    }

    public boolean getNotMove() {
        return notMove;
    }

    public void setNotMove(boolean notMove) {
        this.notMove = notMove;
    }

    public boolean getBlockFix() {
        return blockFix;
    }

    public void setBlockFix(boolean blockFix) {
        this.blockFix = blockFix;
    }

    public boolean getItemDrop() {
        return itemDrop;
    }

    public void setItemDrop(boolean itemDrop) {
        this.itemDrop = itemDrop;
    }

    public void setItemFlag(boolean itemFlag) {
        this.itemFlag = itemFlag;
    }

    private void colorBlindModeCurrent(int offset) {
        colorBlindMode(stylesetCur, curr);
        boardDoc.setCharacterAttributes(offset, 1, stylesetCur, true);
    }

    private void colorBlindMode(SimpleAttributeSet styleSet, Block block) {
        if (setting.colorBlindModeCheck == 1) {
            StyleConstants.setForeground(styleSet, block.getColorBlind());
        } else {
            StyleConstants.setForeground(styleSet, block.getColor());
        }
    }

    private void changeFontSize(int size) {
        StyleConstants.setFontSize(stylesetBr, size);
        StyleConstants.setFontSize(stylesetCur, size);
    }

    private void setStylesetSize(int size) {
        changeFontSize(size);
        drawBoard();
    }

    //max - (200, 60), default - (150, 50)

    //max - 17, default - nothing,

    public static void main(String[] args) {
        ItemBoardTetrisTextPane itemBoardTetrisTextPane = new ItemBoardTetrisTextPane();
        JFrame main = new JFrame();
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.add(itemBoardTetrisTextPane);
        main.setPreferredSize(new Dimension(250, 460));
        main.setVisible(true);
    }

    @Override
    public void changeSize(int sizeNumber) {
        switch (sizeNumber) {
            case 1:
                setStylesetSize(30);
                setPreferredSize(new Dimension(250, 460));
                break;
            case 2:
                setStylesetSize(50);
                setPreferredSize(new Dimension(400, 750));
                break;
            case 3:
                setStylesetSize(50);
                setPreferredSize(new Dimension(400, 750));
                break;
            default:
                setStylesetSize(30);
                setPreferredSize(new Dimension(220, 400));
                break;
        }
    }
}
