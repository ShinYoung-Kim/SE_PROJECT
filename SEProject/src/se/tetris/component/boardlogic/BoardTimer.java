package se.tetris.component.boardlogic;

import se.tetris.component.boardui.BoardLevelPanel;
import se.tetris.component.boardui.BoardScorePanel;
import se.tetris.component.boardui.BoardTetrisArea;
import se.tetris.component.itemboardlogic.ItemBoardLocator;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardTimer {
    private Timer timer;
    private int blockNumber;
    private int level;

    SettingValues setting = SettingValues.getInstance();
    int intervalByMode = setting.intervalNumber;

    private final StrategyPattern strategyPattern;

    public BoardTimer() {
        strategyPattern = new StrategyPattern();
        if (intervalByMode == 2000) {
            strategyPattern.setDifficultyStrategy(new DifficultyEasyStrategy());
        } else if (intervalByMode == 1000) {
            strategyPattern.setDifficultyStrategy(new DifficultyNormalStrategy());
        } else if (intervalByMode == 800) {
            strategyPattern.setDifficultyStrategy(new DifficultyHardStrategy());
        }
        timer = new Timer(strategyPattern.getInterval(this.blockNumber, BoardValues.getInstance().getEraseCnt(),
                BoardLocator.getInstance().getLevelPanel(), BoardLocator.getInstance().getScorePanel()), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardLocator.getInstance().getBoardTetrisArea().moveDown();
            }
        });
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
        timer.setDelay(strategyPattern.getInterval(this.blockNumber, BoardValues.getInstance().getEraseCnt(),
                BoardLocator.getInstance().getLevelPanel(), BoardLocator.getInstance().getScorePanel()));
    }

    public void blockNumberIncrease() {
        blockNumber++;
    }
}
