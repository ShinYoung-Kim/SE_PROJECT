package se.tetris.component.battlemodeui;

import se.tetris.blocks.Block;
import se.tetris.component.ScoreItem;
import se.tetris.component.Sizeable;
import se.tetris.component.boardui.BoardScorePanel;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;

public class InnerBoardScorePanel extends BoardScorePanel implements Sizeable {
    private int score = 0;

    private JPanel scorePanel;
    private JLabel scoreLb1 = new JLabel("Scores");
    private JLabel scoreLb2 = new JLabel(Integer.toString(score));

    public final SettingValues setting = SettingValues.getInstance();
    public int intervalByMode = setting.intervalNumber;
    public int intervalByModeForChange = setting.intervalNumber;

    ScoreItem scoreItem = new ScoreItem();

    public InnerBoardScorePanel() {
        scorePanel = new JPanel();
        EtchedBorder scoreBorder = new EtchedBorder();
        scorePanel.setBorder(scoreBorder);
        scorePanel.setPreferredSize(new Dimension(150, 50));

        scoreLb1.setForeground(Color.darkGray);
        scoreLb2.setForeground(Color.RED);
        scoreLb1.setAlignmentX(CENTER_ALIGNMENT);
        scoreLb2.setAlignmentX(CENTER_ALIGNMENT);

        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.add(scoreLb1);
        scorePanel.add(Box.createVerticalStrut(5));
        scorePanel.add(scoreLb2);

        add(scorePanel);
    }

    public void setScore() {
        String scoretxt = Integer.toString(score);
        String prescoretxt = scoreLb2.getText();
        scoreLb2.setText(scoretxt);
    }

    public void getScore(int lines, String mode) {
        int scorePre = lines;
        if (mode == "line") {
            updateSroce(scorePre, mode);
        } else if (mode == "block") {
            updateSroce(1, mode);
        }

    }

    public int getNowScore() {
        int score = this.score;
        return score;
    }

    public int updateSroce(int sc, String mode) {
        if (mode == "line") {
            if (sc > 0 && sc <= 5) {
                this.score += 10;
            } else if (sc > 5 && sc <= 10) {
                this.score += 15;
            } else {
                this.score += 20;
            }
            if (sc % 3 == 0) {
                this.score += 3 * sc;
            }
            if (sc % 11 == 0) {
                this.score += 11;
            }
        } else if (mode == "block") {
            this.score += sc;
        }

        setScore();
        return score;
    }

    public void changeScore(int score) {
        this.score = score;
    }

    public void setRtSize(int xSize, int ySize) {
        scorePanel.setPreferredSize(new Dimension(xSize, ySize));
    }

    //max - 17, default - nothing,
    public void setLbSize(int size) {
        scoreLb1.setFont(new Font(null, Font.BOLD, size));
        scoreLb2.setFont(new Font(null, Font.BOLD, size));
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
}
