package se.tetris.component.battlemodelogic;

import se.tetris.component.battlemodeui.InnerBoardUI;

public class ProxyInnerBoardUI implements InnerBoardUIInterface{
    private InnerBoardUI innerBoardUI;

    public ProxyInnerBoardUI(InnerBoardUI innerBoardUI) {
        this.innerBoardUI = innerBoardUI;
    }
    @Override
    public String getName() {
        return innerBoardUI.getName();
    }

    @Override
    public String getBattleMode() {
        return innerBoardUI.getBattleMode();
    }
}
