package se.tetris.component.boardui;

import se.tetris.component.ScoreItem;
import se.tetris.component.Sizeable;
import se.tetris.component.boardlogic.BoardLocator;
import se.tetris.component.boardlogic.BoardTimer;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class BoardScorePanel extends JPanel implements Sizeable {

    private static JPanel scorePanel;
    public static int score = 0;
    static JLabel scoreLb1 = new JLabel("Scores");
    static JLabel scoreLb2 = new JLabel(Integer.toString(score));
    private ScoreItem scoreItem = new ScoreItem();

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

        add(scorePanel);
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
        scoreLb1.setFont(new Font(null, Font.BOLD, size));
        scoreLb2.setFont(new Font(null, Font.BOLD, size));
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

    public boolean showDialog(int sc, int mode, int level) {
        return scoreItem.showDialog(sc, mode, level);
    }

    public static void main(String[] args) {
        /*
        BoardScorePanel boardNextArea = new BoardScorePanel();

        BoardLocator boardLocator = BoardLocator.getInstance();

        boardLocator.getInstance().setBoardNextArea(new BoardNextArea());
        boardLocator.getInstance().setBoardTetrisArea(new BoardTetrisArea());
        boardLocator.getInstance().setScorePanel(new BoardScorePanel());
        boardLocator.getInstance().setLevelPanel(new BoardLevelPanel());
        boardLocator.getInstance().setBoardTimer(new BoardTimer());

        boardNextArea.changeSize(1);

        boardNextArea.scorePanel.setVisible(true);

         */
    }
}
