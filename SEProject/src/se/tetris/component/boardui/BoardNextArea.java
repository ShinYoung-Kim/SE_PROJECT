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

import static se.tetris.setting.SettingCode.screenHeight;
import static se.tetris.setting.SettingCode.screenWidth;

public class BoardNextArea extends JTextPane implements Sizeable {
    private static JTextPane nextArea;
    private static SimpleAttributeSet stylesetNx;
    private static StyledDocument nextDoc;
    public static Block next;
    public int[][] nextBoard;

    double min;
    double max;
    double percentage;
    double weighted;
    int block;
    int nextX = 1;
    int nextY = 1;

    final SettingValues setting = SettingValues.getInstance();

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

        next = getRandomBlock(setting.modeChoose);
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
        blockNumber++;
        timer.setDelay(getInterval(blockNumber, eraseCnt));
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

    public static void setRtSize(int xSize, int ySize) {
        nextArea.setPreferredSize(new Dimension(xSize, xSize));
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
}
