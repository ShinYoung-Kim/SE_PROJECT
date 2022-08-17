package se.tetris.component.boardlogic;

import se.tetris.component.boardui.BoardLevelPanel;
import se.tetris.component.boardui.BoardScorePanel;
import se.tetris.component.boardui.BoardTetrisArea;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardTimer {
    private Timer timer;
    private int blockNumber;
    private int eraseCnt;
    private int level;

    SettingValues setting = SettingValues.getInstance();
    int intervalByMode = setting.intervalNumber;

    public BoardTimer() {
        timer = new Timer(getInterval(this.blockNumber, this.eraseCnt), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardLocator.getInstance().getBoardTetrisArea().moveDown();
            }
        });
    }

    public int getInterval(int blockNumber, int eraseCnt) {
        BoardLevelPanel boardLevelPanel = BoardLocator.getInstance().getLevelPanel();
        BoardScorePanel boardScorePanel = BoardLocator.getInstance().getScorePanel();
        //생성
        if (blockNumber == 30 || blockNumber == 60 || blockNumber == 80 || blockNumber == 100 || blockNumber == 120) {
            if (intervalByMode == 1000) {
                boardScorePanel.getScore(5 * eraseCnt, "std");
                boardScorePanel.setScore();
            } else if (intervalByMode == 2000) {
                boardScorePanel.getScore(11 * eraseCnt, "std");
                boardScorePanel.setScore();
            } else if (intervalByMode == 800) {
                boardScorePanel.getScore(20 * eraseCnt, "std");
                boardScorePanel.setScore();
            }
        }
        //삭제
        if (intervalByMode == 1000) {
            if (eraseCnt < 5 && eraseCnt >= 0) {
                setting.intervalNumber = 1000;
                level = 1;
            } else if (eraseCnt < 10 && eraseCnt >= 5) {
                setting.intervalNumber = (int) (1000 * 0.9);
                level = 2;
            } else if (eraseCnt < 15 && eraseCnt >= 10) {
                setting.intervalNumber = (int) (1000 * 0.9 * 0.9);
                level = 3;
            } else if (eraseCnt < 20 && eraseCnt >= 15) {
                setting.intervalNumber = (int) (1000 * 0.9 * 0.9 * 0.9);
                level = 4;
            } else if (eraseCnt < 25 && eraseCnt >= 20) {
                setting.intervalNumber = (int) (1000 * 0.9 * 0.9 * 0.9 * 0.9);
                level = 5;
            } else if (eraseCnt < 30 && eraseCnt >= 25) {
                setting.intervalNumber = (int) (1000 * 0.9 * 0.9 * 0.9 * 0.9 * 0.9);
                level = 6;
            } else if (eraseCnt >= 30) {
                setting.intervalNumber = (int) (1000 * 0.9 * 0.9 * 0.9 * 0.9 * 0.9 * 0.9);
                level = 7;
            }
        } else if (intervalByMode == 2000) {
            if (eraseCnt < 5 && eraseCnt >= 0) {
                setting.intervalNumber = 2000;
                level = 1;
            } else if (eraseCnt < 10 && eraseCnt >= 5) {
                setting.intervalNumber = (int) (2000 * 0.92);
                level = 2;
            } else if (eraseCnt < 15 && eraseCnt >= 10) {
                setting.intervalNumber = (int) (2000 * 0.92 * 0.92);
                level = 3;
            } else if (eraseCnt < 20 && eraseCnt >= 15) {
                setting.intervalNumber = (int) (2000 * 0.92 * 0.92 * 0.92);
                level = 4;
            } else if (eraseCnt < 25 && eraseCnt >= 20) {
                setting.intervalNumber = (int) (2000 * 0.92 * 0.92 * 0.92 * 0.92);
                level = 5;
            } else if (eraseCnt < 30 && eraseCnt >= 25) {
                setting.intervalNumber = (int) (2000 * 0.92 * 0.92 * 0.92 * 0.92 * 0.92);
                level = 6;
            } else if (eraseCnt >= 30) {
                setting.intervalNumber = (int) (2000 * 0.92 * 0.92 * 0.92 * 0.92 * 0.92 * 0.92);
                level = 7;
            }
        } else if (intervalByMode == 800) {
            if (eraseCnt < 5 && eraseCnt >= 0) {
                setting.intervalNumber = 800;
                level = 1;
            } else if (eraseCnt < 10 && eraseCnt >= 5) {
                setting.intervalNumber = (int) (800 * 0.88);
                level = 2;
            } else if (eraseCnt < 15 && eraseCnt >= 10) {
                setting.intervalNumber = (int) (800 * 0.88 * 0.88);
                level = 3;
            } else if (eraseCnt < 20 && eraseCnt >= 15) {
                setting.intervalNumber = (int) (800 * 0.88 * 0.88 * 0.88);
                level = 4;
            } else if (eraseCnt < 25 && eraseCnt >= 20) {
                setting.intervalNumber = (int) (800 * 0.88 * 0.88 * 0.88 * 0.88);
                level = 5;
            } else if (eraseCnt < 30 && eraseCnt >= 25) {
                setting.intervalNumber = (int) (800 * 0.88 * 0.88 * 0.88 * 0.88 * 0.88);
                level = 6;
            } else if (eraseCnt >= 30) {
                setting.intervalNumber = (int) (800 * 0.88 * 0.88 * 0.88 * 0.88 * 0.88 * 0.88);
                level = 7;
            }
        }
        boardLevelPanel.changeLevelLb(level);
        System.out.println("Created : " + blockNumber + "   Removed : " + eraseCnt + "   intervalByMode" + intervalByMode + "   interval Number : " + setting.intervalNumber);
        return setting.intervalNumber;
    }

    public void boardTimerStop() {
        timer.stop();
    }

    public void boardTimerStart() {
        timer.start();
    }

    public void boardTimerRestart() {
        timer.restart();
    }

    public void boardTimerSetDelay() {
        timer.setDelay(getInterval(blockNumber, eraseCnt));
    }

    public void blockNumberIncrease() {
        blockNumber++;
    }
}
