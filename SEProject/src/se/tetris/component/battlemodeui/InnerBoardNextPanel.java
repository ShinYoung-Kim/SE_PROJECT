package se.tetris.component.battlemodeui;

import se.tetris.blocks.Block;
import se.tetris.component.ScoreItem;
import se.tetris.component.Sizeable;
import se.tetris.component.battlemodelogic.BattleBoardLocator;
import se.tetris.component.battlemodelogic.BattleTimer;
import se.tetris.component.battlemodelogic.ObserveInterface;
import se.tetris.component.boardlogic.RandomBlock;
import se.tetris.component.boardui.BoardNextArea;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;

public class InnerBoardNextPanel extends BoardNextArea implements Sizeable, ObserveInterface {
    private JTextPane nextPanel;
    private int[][] nextBoard;

    private SimpleAttributeSet stylesetNx;
    private StyledDocument nextDoc;
    private Block next;
    private int nextX = 1;
    private int nextY = 0;

    private int mode = 0;
    private RandomBlock randomBlock = new RandomBlock();

    private BattleBoardLocator battleBoardLocator;

    public final SettingValues setting = SettingValues.getInstance();
    public int intervalByMode = setting.intervalNumber;
    public int intervalByModeForChange = setting.intervalNumber;

    public InnerBoardNextPanel() {
        //UI
        nextPanel = new JTextPane();
        nextPanel.setEditable(false);
        nextPanel.setBackground(Color.BLACK);
        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        nextPanel.setBorder(border);
        nextPanel.setAlignmentX(CENTER_ALIGNMENT);
        nextPanel.setAlignmentY(CENTER_ALIGNMENT);

        add(nextPanel);

        //초기 세팅
        nextBoard = new int[3][5];
        next = randomBlock.getRandomBlock(setting.modeChoose);

        stylesetNx = new SimpleAttributeSet();
        StyleConstants.setFontSize(stylesetNx, 25);
        StyleConstants.setFontFamily(stylesetNx, "Courier New");
        StyleConstants.setBold(stylesetNx, true);
        StyleConstants.setAlignment(stylesetNx, StyleConstants.ALIGN_CENTER);
        StyleConstants.setLineSpacing(stylesetNx, -0.45f);

        nextDoc = nextPanel.getStyledDocument();
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
        battleBoardLocator.getBoardTimer().blockNumberIncrease();
        sb.append("\n");
        sb.append("\n");
        battleBoardLocator.getBoardTimer().boardTimerSetDelay();
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
        nextPanel.setText(sb.toString());
        colorBlindModeNext();
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

    public void setStylesetSize(int size) {
        StyleConstants.setFontSize(stylesetNx, size);
        drawNext();
    }

    //max - (200, 60), default - (150, 50)
    public void setRtSize(int size) {
        nextPanel.setPreferredSize(new Dimension(size, size));
    }

    @Override
    public void changeSize(int sizeNumber) {
        switch (sizeNumber) {
            case 1:
                setStylesetSize(8);
                setRtSize(40);
                break;
            case 2:
                setStylesetSize(20);
                setRtSize(120);
                break;
            case 3:
                setStylesetSize(36);
                setRtSize(200);
                break;
            default:
                setStylesetSize(20);
                setRtSize(120);
                break;
        }
    }

    @Override
    public void updateBattleBoardLocator(BattleBoardLocator battleBoardLocator) {
        this.battleBoardLocator = battleBoardLocator;
    }
}
