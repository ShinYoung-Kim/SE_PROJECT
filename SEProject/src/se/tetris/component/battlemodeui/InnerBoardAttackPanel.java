package se.tetris.component.battlemodeui;

import se.tetris.blocks.Block;
import se.tetris.component.ScoreItem;
import se.tetris.component.Sizeable;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;

public class InnerBoardAttackPanel extends JPanel implements Sizeable {
    private JTextPane attackPanel;
    private int[][] attackBoard;

    public SimpleAttributeSet stylesetAk;
    public StyledDocument attackDoc;

    private Block lastBlock;
    private int lastX;
    public int lastY;
    public int attackY = 9;

    public ArrayList<Integer> attackLine;

    public final SettingValues setting = SettingValues.getInstance();

    public InnerBoardAttackPanel() {
        //UI
        attackPanel = new JTextPane();
        attackPanel.setEditable(false);
        attackPanel.setBackground(Color.BLACK);
        CompoundBorder attackBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 3),
                BorderFactory.createLineBorder(Color.WHITE, 3));
        attackPanel.setBorder(attackBorder);
        attackPanel.setAlignmentX(CENTER_ALIGNMENT);
        attackPanel.setAlignmentY(CENTER_ALIGNMENT);

        add(attackPanel);

        //초기 세팅
        attackBoard = new int[10][10];

        stylesetAk = new SimpleAttributeSet();
        StyleConstants.setFontSize(stylesetAk, 10);
        StyleConstants.setFontFamily(stylesetAk, "Courier New");
        StyleConstants.setBold(stylesetAk, true);
        StyleConstants.setForeground(stylesetAk, Color.GRAY);
        StyleConstants.setAlignment(stylesetAk, StyleConstants.ALIGN_CENTER);
        StyleConstants.setLineSpacing(stylesetAk, -0.45f);

        attackDoc = attackPanel.getStyledDocument();

        attackLine = new ArrayList<Integer>();
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

    public int[][] getAttackBoard() {
        return attackBoard;
    }

    public void setAttackBoard(int[][] attackBoard) {
        this.attackBoard = attackBoard;
    }

    public void attackLineClear() {
        attackLine.clear();
    }

    public void setStylesetSize(int size) {
        StyleConstants.setFontSize(stylesetAk, size);
    }

    //max - (200, 60), default - (150, 50)
    public void setRtSize(int size) {
        attackPanel.setPreferredSize(new Dimension(size, size));
    }

    @Override
    public void changeSize(int sizeNumber) {
        switch (sizeNumber) {
            case 1:
                setStylesetSize(8);
                setRtSize(50);
                break;
            case 2:
                setStylesetSize(11);
                setRtSize(120);
                break;
            case 3:
                setStylesetSize(22);
                setRtSize(200);
                break;
            default:
                setStylesetSize(13);
                setRtSize(120);
                break;
        }
    }

    public void setLastBlock(Block lastBlock) {
        this.lastBlock = lastBlock;
    }

    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
    }
}
