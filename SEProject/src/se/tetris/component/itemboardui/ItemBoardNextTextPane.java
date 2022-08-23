package se.tetris.component.itemboardui;

import se.tetris.blocks.*;
import se.tetris.component.Sizeable;
import se.tetris.component.boardlogic.BoardLocator;
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
import java.util.Random;

import static se.tetris.setting.SettingCode.screenHeight;
import static se.tetris.setting.SettingCode.screenWidth;

public class ItemBoardNextTextPane extends JPanel implements Sizeable {
    private final JTextPane nextTextPane;
    private int[][] nextBoard = new int[4][5];
    private SimpleAttributeSet stylesetNx;
    private StyledDocument nextDoc;
    private Block next;
    private int nextX = 1;
    private int nextY = 1;

    private RandomBlock randomBlock = new RandomBlock();
    ItemBoardValues itemboardValues = ItemBoardValues.getInstance();
    private int mode = itemboardValues.mode;
    final SettingValues setting = SettingValues.getInstance();

    public ItemBoardNextTextPane() {
        nextTextPane = new JTextPane();
        nextTextPane.setEditable(false);
        nextTextPane.setBackground(Color.BLACK);
        nextTextPane.setBorder(ItemBoardValues.getInstance().getBorder());
        nextTextPane.setAlignmentX(CENTER_ALIGNMENT);
        nextTextPane.setAlignmentY(CENTER_ALIGNMENT);
        nextTextPane.setPreferredSize(new Dimension(150, 200));

        next = randomBlock.getRandomBlock(setting.modeChoose);

        stylesetNx = new SimpleAttributeSet();
        StyleConstants.setFontSize(stylesetNx, 25);
        StyleConstants.setFontFamily(stylesetNx, "Courier New");
        StyleConstants.setBold(stylesetNx, true);
        StyleConstants.setAlignment(stylesetNx, StyleConstants.ALIGN_CENTER);
        StyleConstants.setLineSpacing(stylesetNx, -0.45f);

        nextDoc = nextTextPane.getStyledDocument();

        add(nextTextPane);
    }

    public void placeNext() {
        for (int j = 0; j < next.height(); j++) {
            for (int i = 0; i < next.width(); i++) {
                nextBoard[nextY + j][nextX + i] = next.getShape(i, j);
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

    public void drawNext() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        ItemBoardLocator.getInstance().getBoardTimer().blockNumberIncrease();
        ItemBoardLocator.getInstance().getBoardTimer().boardTimerSetDelay();
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
            sb.append("\n");
        }
        nextTextPane.setText(sb.toString());
        colorBlindModeNext();

        for (int i = 0; i < nextBoard.length; i++) {
            int offset = i * 6 + 1;
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
        itemboardValues.setItemType(rnd.nextInt(5) + 8);
        switch (itemboardValues.getItemType()) {
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

    public Block getNext() {
        return next;
    }

    public void setNext(Block next) {
        this.next = next;
    }

    public void resetNextBoard() {
        nextBoard = new int[HEIGHT][WIDTH];
    }

    private void colorBlindMode(SimpleAttributeSet styleSet, Block block) {
        if (setting.colorBlindModeCheck == 1) {
            StyleConstants.setForeground(styleSet, block.getColorBlind());
        } else {
            StyleConstants.setForeground(styleSet, block.getColor());
        }
    }

    private void colorBlindModeNext() {
        colorBlindMode(stylesetNx, next);
        nextDoc.setParagraphAttributes(0, nextDoc.getLength(), stylesetNx, false);
    }

    private void setStylesetSize(int size) {
        StyleConstants.setFontSize(stylesetNx, size);
        drawNext();
    }

    //max - (200, 60), default - (150, 50)
    private void setRtSize(int size) {
        setPreferredSize(new Dimension(size, size));
    }

    //max - 17, default - nothing,

    @Override
    public void changeSize(int sizeNumber) {
        switch (sizeNumber) {
            case 1:
                setStylesetSize(20);
                setRtSize(110);
                break;
            case 2:
                setStylesetSize(45);
                setRtSize(250);
                break;
            case 3:
                setStylesetSize(45);
                setRtSize(250);
                break;
            default:
                setStylesetSize(25);
                setRtSize(120);
                break;
        }
    }
}
