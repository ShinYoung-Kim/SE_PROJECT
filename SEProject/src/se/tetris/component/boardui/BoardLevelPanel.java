package se.tetris.component.boardui;

import se.tetris.common.LifeCycleManager;
import se.tetris.component.Sizeable;
import se.tetris.component.boardlogic.BoardValues;
import se.tetris.data.DBCalls;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

import static se.tetris.setting.SettingCode.screenHeight;
import static se.tetris.setting.SettingCode.screenWidth;

public class BoardLevelPanel extends JPanel implements Sizeable, LifeCycleManager {

    private JPanel levelPanel;
    private int level = 0;
    private JLabel levelLb1 = new JLabel("Level");
    private JLabel levelLb2 = new JLabel(Integer.toString(level));

    public BoardLevelPanel() {
        levelLb1.setAlignmentX(CENTER_ALIGNMENT);
        levelLb2.setAlignmentX(CENTER_ALIGNMENT);

        levelPanel = new JPanel();
        EtchedBorder levelBorder = new EtchedBorder();
        levelPanel.setBorder(levelBorder);
        levelPanel.setPreferredSize(new Dimension(150, 50));

        //인터페이스 setting

        levelLb1.setForeground(Color.darkGray);
        levelLb1.setAlignmentX(CENTER_ALIGNMENT);

        levelLb2.setForeground(Color.BLUE);
        levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.Y_AXIS));
        levelPanel.add(levelLb1);
        levelPanel.add(Box.createVerticalStrut(5));
        levelPanel.add(levelLb2);

        add(levelPanel);
    }

    @Override
    public void changeSize(int sizeNumber) {
        switch (sizeNumber) {
            case 1:
                setRtSize(110, 50);
                setLbSize(10);
                break;
            case 2:
                setRtSize(250, 55);
                setLbSize(15);
                break;
            case 3:
                setRtSize(250, 60);
                setLbSize(17);
                break;
            default:
                setRtSize(120, 50);
                setLbSize(10);
                break;
        }
    }

    public void setRtSize(int xSize, int ySize) {
        setSize(new Dimension(xSize, ySize));
    }

    public void setLbSize(int size) {
        levelLb1.setFont(new Font(null, Font.BOLD, size));
        levelLb2.setFont(new Font(null, Font.BOLD, size));
    }

    public void changeLevelLb (int level) {
        levelLb2.setText(Integer.toString(level));
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
