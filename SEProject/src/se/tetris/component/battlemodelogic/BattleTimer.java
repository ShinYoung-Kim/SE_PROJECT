package se.tetris.component.battlemodelogic;

import se.tetris.component.boardlogic.*;
import se.tetris.setting.SettingValues;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BattleTimer {
    private Timer timer;
    private int blockNumber;
    private int level;

    SettingValues setting = SettingValues.getInstance();
    int intervalByMode = setting.intervalNumber;

    private final StrategyPattern strategyPattern;

    public BattleTimer() {
        strategyPattern = new StrategyPattern();
        if (intervalByMode == 2000) {
            strategyPattern.setDifficultyStrategy(new DifficultyEasyStrategy());
        } else if (intervalByMode == 1000) {
            strategyPattern.setDifficultyStrategy(new DifficultyNormalStrategy());
        } else if (intervalByMode == 800) {
            strategyPattern.setDifficultyStrategy(new DifficultyHardStrategy());
        }
        timer = new Timer(strategyPattern.getInterval(this.blockNumber, BoardValues.getInstance().getEraseCnt(),
                BattleBoardLocator.getInstance().getLevelPanel(), BattleBoardLocator.getInstance().getScorePanel()), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BattleBoardLocator.getInstance().getBoardTetrisArea().moveDown();
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
                BattleBoardLocator.getInstance().getLevelPanel(), BattleBoardLocator.getInstance().getScorePanel()));
    }

    public void blockNumberIncrease() {
        blockNumber++;
    }
}
