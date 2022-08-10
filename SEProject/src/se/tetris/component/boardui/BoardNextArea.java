package se.tetris.component.boardui;

import se.tetris.blocks.*;
import se.tetris.component.Sizeable;
import se.tetris.component.boardlogic.BoardLocator;
import se.tetris.component.boardlogic.BoardTimer;
import se.tetris.component.boardlogic.RandomBlock;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

import static se.tetris.setting.SettingCode.screenHeight;
import static se.tetris.setting.SettingCode.screenWidth;

public class BoardNextArea extends JTextPane implements Sizeable {
    private static JTextPane nextArea;
    private static SimpleAttributeSet stylesetNx;
    private static StyledDocument nextDoc;

    private Block next;
    private int[][] nextBoard;
    RandomBlock randomBlock = new RandomBlock();

    private int nextX = 1;
    private int nextY = 1;

    final SettingValues setting = SettingValues.getInstance();
    BoardTimer boardTimer;

    public BoardNextArea(){
        nextArea = new JTextPane();
        nextArea.setEditable(false);
        nextArea.setBackground(Color.BLACK);
        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        nextArea.setBorder(border);
        nextArea.setAlignmentX(CENTER_ALIGNMENT);
        nextArea.setAlignmentY(CENTER_ALIGNMENT);
        nextArea.setPreferredSize(new Dimension(150, 200));

        nextBoard = new int[4][5];

        stylesetNx = new SimpleAttributeSet();
        StyleConstants.setFontSize(stylesetNx, 25);
        StyleConstants.setFontFamily(stylesetNx, "Courier New");
        StyleConstants.setBold(stylesetNx, true);
        StyleConstants.setAlignment(stylesetNx, StyleConstants.ALIGN_CENTER);
        StyleConstants.setLineSpacing(stylesetNx, -0.45f);

        nextDoc = nextArea.getStyledDocument();

        next = randomBlock.getRandomBlock(setting.modeChoose);
    }

    @Override
    public void changeSize(int sizeNumber) {
        switch (sizeNumber) {
            case 1:
                setStylesetSize(30, 25, 20);
                setRtSize(110, 50);
                break;
            case 2:
                setStylesetSize(50, 50, 45);
                setRtSize(250, 55);
                break;
            case 3:
                setStylesetSize(50, 50, 45);
                setRtSize(250, 60);
                break;
            default:
                setStylesetSize(30, 25, 25);
                setRtSize(120, 50);
                break;
        }
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
        boardTimer = BoardLocator.getInstance().getBoardTimer();
        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        boardTimer.blockNumberIncrease();
        boardTimer.boardTimerSetDelay();
        for (int i = 0; i < nextBoard.length; i++) {
            for (int j = 0; j < nextBoard[i].length; j++) {
                if (nextBoard[i][j] > 0) {
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

    public void setStylesetSize(int size1, int size2, int size3) {
        StyleConstants.setFontSize(stylesetNx, size3);
        drawNext();
    }

    public void setRtSize(int xSize, int ySize) {
        setSize(new Dimension(xSize, xSize));
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

    public Block getNext() {
        return next;
    }

    public void setNext(Block next) {
        this.next = next;
    }

    public void resetNextBoard() {
        nextBoard = new int[HEIGHT][WIDTH];
    }
}
