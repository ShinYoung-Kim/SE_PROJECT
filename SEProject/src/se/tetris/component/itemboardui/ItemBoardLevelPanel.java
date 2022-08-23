package se.tetris.component.itemboardui;

import se.tetris.component.Sizeable;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.text.StyleConstants;
import java.awt.*;

import static se.tetris.setting.SettingCode.screenHeight;
import static se.tetris.setting.SettingCode.screenWidth;

public class ItemBoardLevelPanel extends JPanel implements Sizeable {
    private JPanel levelPanel;
    private int level = 0;
    private JLabel levelLb1 = new JLabel("Level");
    private JLabel levelLb2 = new JLabel(Integer.toString(level));

    public ItemBoardLevelPanel() {

        levelLb1.setAlignmentX(CENTER_ALIGNMENT);
        levelLb2.setAlignmentX(CENTER_ALIGNMENT);

        levelPanel = new JPanel();
        EtchedBorder levelBorder = new EtchedBorder();
        levelPanel.setBorder(levelBorder);
        levelPanel.setPreferredSize(new Dimension(150, 50));

        levelLb1.setForeground(Color.darkGray);
        levelLb1.setAlignmentX(CENTER_ALIGNMENT);

        levelLb2.setForeground(Color.BLUE);
        levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.Y_AXIS));
        levelPanel.add(levelLb1);
        levelPanel.add(Box.createVerticalStrut(5));
        levelPanel.add(levelLb2);

        add(levelPanel);
    }

    //max - (200, 60), default - (150, 50)
    private void setRtSize(int xSize, int ySize) {
        levelPanel.setPreferredSize(new Dimension(xSize, ySize));
    }

    //max - 17, default - nothing,
    private void setLbSize(int size) {
        levelLb1.setFont(new Font(null, Font.BOLD, size));
        levelLb2.setFont(new Font(null, Font.BOLD, size));
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

    public void changeLevelLb (int level) {
        levelLb2.setText(Integer.toString(level));
    }

    public void changeLevel(int level) {
        this.level = level;
    }
}
