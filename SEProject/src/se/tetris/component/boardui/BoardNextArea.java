package se.tetris.component.boardui;

import se.tetris.blocks.*;
import se.tetris.common.LifeCycleManager;
import se.tetris.component.Sizeable;
import se.tetris.component.boardlogic.BoardLocator;
import se.tetris.component.boardlogic.BoardTimer;
import se.tetris.component.boardlogic.BoardValues;
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

public class BoardNextArea extends JPanel implements Sizeable {
    private JTextPane nextArea;
    private SimpleAttributeSet stylesetNx;
    private StyledDocument nextDoc;

    private Block next;
    private int[][] nextBoard;
    private RandomBlock randomBlock = new RandomBlock();

    private int nextX = 1;
    private int nextY = 1;

    final SettingValues setting = SettingValues.getInstance();

    public BoardNextArea(){
        nextArea = new JTextPane();
        nextArea.setEditable(false);
        nextArea.setBackground(Color.BLACK);
        nextArea.setBorder(BoardValues.getInstance().getBorder());
        nextArea.setAlignmentX(CENTER_ALIGNMENT);
        nextArea.setAlignmentY(CENTER_ALIGNMENT);
        nextArea.setPreferredSize(new Dimension(150, 200));

        nextBoard = new int[4][5];
        next = randomBlock.getRandomBlock(setting.modeChoose);

        stylesetNx = new SimpleAttributeSet();
        StyleConstants.setFontSize(stylesetNx, 25);
        StyleConstants.setFontFamily(stylesetNx, "Courier New");
        StyleConstants.setBold(stylesetNx, true);
        StyleConstants.setAlignment(stylesetNx, StyleConstants.ALIGN_CENTER);
        StyleConstants.setLineSpacing(stylesetNx, -0.45f);

        nextDoc = nextArea.getStyledDocument();

        //placeNext();
        //drawNext();

        add(nextArea);
    }

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
                setStylesetSize(20);
                setRtSize(110);
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
        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        BoardLocator.getInstance().getBoardTimer().blockNumberIncrease();
        BoardLocator.getInstance().getBoardTimer().boardTimerSetDelay();
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

    private void setStylesetSize(int size) {
        StyleConstants.setFontSize(stylesetNx, size);
        drawNext();
    }

    private void setRtSize(int size) {
        nextArea.setPreferredSize(new Dimension(size, size));
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

    public Block getNext() {
        return next;
    }

    public void setNext(Block next) {
        this.next = next;
    }

    public void resetNextBoard() {
        nextBoard = new int[HEIGHT][WIDTH];
    }

    public static void main(String[] args) {
        /*
        BoardLocator boardLocator = BoardLocator.getInstance();

        BoardLocator.getInstance().setBoardTetrisArea(new BoardTetrisArea());
        BoardLocator.getInstance().setLevelPanel(new BoardLevelPanel());
        BoardLocator.getInstance().setBoardTimer(new BoardTimer());
        BoardNextArea boardNextArea = new BoardNextArea();
        BoardLocator.getInstance().setBoardNextArea(boardNextArea);
        BoardLocator.getInstance().setScorePanel(new BoardScorePanel());

        boardNextArea.changeSize(1);

        nextArea.setVisible(true);

         */
    }
}
