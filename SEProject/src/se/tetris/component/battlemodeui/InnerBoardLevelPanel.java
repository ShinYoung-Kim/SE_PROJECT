package se.tetris.component.battlemodeui;

import se.tetris.blocks.Block;
import se.tetris.component.ScoreItem;
import se.tetris.component.Sizeable;
import se.tetris.component.battlemodelogic.BattleBoardLocator;
import se.tetris.component.battlemodelogic.ObserveInterface;
import se.tetris.component.boardui.BoardLevelPanel;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;

public class InnerBoardLevelPanel extends BoardLevelPanel implements Sizeable, ObserveInterface {
    private int level = 0;

    private JPanel levelPanel;
    private JLabel levelLb1 = new JLabel("Level");
    private JLabel levelLb2 = new JLabel(Integer.toString(level));

    public final SettingValues setting = SettingValues.getInstance();

    private BattleBoardLocator battleBoardLocator;

    public InnerBoardLevelPanel() {
        levelPanel = new JPanel();
        EtchedBorder scoreBorder = new EtchedBorder();
        levelPanel.setBorder(scoreBorder);
        levelPanel.setPreferredSize(new Dimension(150, 50));

        levelLb1.setForeground(Color.darkGray);
        levelLb2.setForeground(Color.BLUE);
        levelLb1.setAlignmentX(CENTER_ALIGNMENT);
        levelLb2.setAlignmentX(CENTER_ALIGNMENT);

        levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.Y_AXIS));
        levelPanel.add(levelLb1);
        levelPanel.add(Box.createVerticalStrut(5));
        levelPanel.add(levelLb2);
    }

    public void setRtSize(int xSize, int ySize) {
        levelPanel.setPreferredSize(new Dimension(xSize, ySize));
    }

    //max - 17, default - nothing,
    public void setLbSize(int size) {
        levelLb1.setFont(new Font(null, Font.BOLD, size));
        levelLb2.setFont(new Font(null, Font.BOLD, size));
    }

    @Override
    public void changeSize(int sizeNumber) {
        switch (sizeNumber) {
            case 1:
                setRtSize(40, 50);
                setLbSize(8);
                break;
            case 2:
                setRtSize(120, 50);
                setLbSize(13);
                break;
            case 3:
                setRtSize(200, 55);
                setLbSize(17);
                break;
            default:
                setRtSize(120, 50);
                setLbSize(10);
                break;
        }
    }

    @Override
    public void updateBattleBoardLocator(BattleBoardLocator battleBoardLocator) {
        this.battleBoardLocator = battleBoardLocator;
    }
}
