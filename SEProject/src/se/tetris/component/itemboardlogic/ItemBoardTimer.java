package se.tetris.component.itemboardlogic;

import se.tetris.component.boardlogic.BoardLocator;
import se.tetris.component.boardui.BoardLevelPanel;
import se.tetris.component.boardui.BoardScorePanel;
import se.tetris.component.itemboardui.ItemBoardLevelPanel;
import se.tetris.component.itemboardui.ItemBoardScorePanel;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemBoardTimer {
    private Timer timer;
    private int blockNumber;
    private int level;

    SettingValues setting = SettingValues.getInstance();
    private int intervalByMode = setting.intervalNumber;
    ItemBoardValues boardValues = ItemBoardValues.getInstance();

    public ItemBoardTimer() {
        timer = new Timer(getInterval(this.blockNumber, boardValues.getEraseCnt()), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ItemBoardLocator.getInstance().getBoardTetrisArea().moveDown();
            }
        });
    }

    private int getInterval(int blockNumber, int eraseCnt) {
        ItemBoardLevelPanel boardLevelPanel = ItemBoardLocator.getInstance().getLevelPanel();
        ItemBoardScorePanel boardScorePanel = ItemBoardLocator.getInstance().getScorePanel();

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
        timer.setDelay(getInterval(blockNumber, boardValues.getEraseCnt()));
    }

    public void blockNumberIncrease() {
        blockNumber++;
    }
}
