package se.tetris.component.boardui;

import se.tetris.component.Sizeable;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

import static se.tetris.setting.SettingCode.screenHeight;
import static se.tetris.setting.SettingCode.screenWidth;

public class BoardScorePanel extends JPanel implements Sizeable {

    private static JPanel scorePanel;
    public static int score = 0;
    static JLabel scoreLb1 = new JLabel("Scores");
    static JLabel scoreLb2 = new JLabel(Integer.toString(score));

    public BoardScorePanel() {
        scorePanel = new JPanel();
        EtchedBorder scoreBorder = new EtchedBorder();
        scorePanel.setBorder(scoreBorder);
        scorePanel.setPreferredSize(new Dimension(150, 50));

        scoreLb1.setForeground(Color.darkGray);
        //정렬
        scoreLb1.setAlignmentX(CENTER_ALIGNMENT);
        scoreLb2.setAlignmentX(CENTER_ALIGNMENT);

        scoreLb2.setForeground(Color.RED);

        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.add(scoreLb1);
        scorePanel.add(Box.createVerticalStrut(5));
        scorePanel.add(scoreLb2);
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

    public static void setRtSize(int xSize, int ySize) {
        scorePanel.setPreferredSize(new Dimension(xSize, ySize));
    }

    public static void setLbSize(int size) {
        scoreLb1.setFont(new Font(null, Font.BOLD, size));
        scoreLb2.setFont(new Font(null, Font.BOLD, size));
    }
}
