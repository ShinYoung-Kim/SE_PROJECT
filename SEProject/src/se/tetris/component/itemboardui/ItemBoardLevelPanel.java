package se.tetris.component.itemboardui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class ItemBoardLevelPanel extends JPanel {
    private JPanel levelPanel;
    private int level = 0;

    ItemBoardLevelPanel() {
        JLabel levelLb1 = new JLabel("Level");
        JLabel levelLb2 = new JLabel(Integer.toString(level));

        levelLb1.setAlignmentX(CENTER_ALIGNMENT);
        levelLb2.setAlignmentX(CENTER_ALIGNMENT);

        levelPanel = new JPanel();
        EtchedBorder scoreBorder = new EtchedBorder();
        levelPanel.setBorder(scoreBorder);
        levelPanel.setPreferredSize(new Dimension(150, 50));

        levelLb1.setForeground(Color.darkGray);
        levelLb1.setAlignmentX(CENTER_ALIGNMENT);

        levelLb2.setForeground(Color.BLUE);
        levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.Y_AXIS));
        levelPanel.add(levelLb1);
        levelPanel.add(Box.createVerticalStrut(5));
        levelPanel.add(levelLb2);

        levelLb1.setAlignmentX(CENTER_ALIGNMENT);
        levelLb2.setAlignmentX(CENTER_ALIGNMENT);

        add(levelPanel);
    }
}
