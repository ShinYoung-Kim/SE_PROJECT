package se.tetris.setting;

import javax.swing.*;

public class SettingPanel extends JPanel {

    private int KeyFoucus;
    private int firstPanelComponent;

    public void setFirstPanelComponent(int firstPanelComponent) {
        this.firstPanelComponent = firstPanelComponent;
    }

    public void setLastPanellComponent(int lastPanellComponent) {
        this.lastPanellComponent = lastPanellComponent;
    }

    public void setCanMoveLeft(boolean canMoveLeft) {
        this.canMoveLeft = canMoveLeft;
    }

    public void setCanMoveRight(boolean canMoveRight) {
        this.canMoveRight = canMoveRight;
    }

    public void setCanPanelMoveDown(boolean canPanelMoveDown) {
        this.canPanelMoveDown = canPanelMoveDown;
    }

    public void setCanPanelMoveUp(boolean canPanelMoveUp) {
        this.canPanelMoveUp = canPanelMoveUp;
    }

    private int lastPanellComponent;
    private boolean canMoveLeft;
    private boolean canMoveRight;
    private boolean canPanelMoveDown;
    private boolean canPanelMoveUp;

    public int getKeyFoucus() {
        return KeyFoucus - 1;
    }

    public void setKeyFoucus(int KeyFoucus) {
        this.KeyFoucus = KeyFoucus;
    }

    public void foucusMoveUp() {
        KeyFoucus -= 1;
    }

    public void foucusMoveDown() {
        KeyFoucus += 1;
    }

    public boolean canMoveUp() {
        foucusMoveUp();
        if (KeyFoucus < firstPanelComponent) {
            return false;
        }
        return true;
    }

    public boolean canMoveDown() {
        foucusMoveDown();
        if (KeyFoucus > lastPanellComponent) {
            return false;
        }
        return true;
    }

    public void panelFirstFoucus() {
        KeyFoucus = firstPanelComponent;
    }

    public void panelLastFoucus() {
        KeyFoucus = lastPanellComponent;
    }

    public boolean getCanMoveLeft() {
        return canMoveLeft;
    }

    public boolean getCanMoveRight() {
        return canMoveRight;
    }

    public void upAtTop() {
        if (!this.canMoveUp()) {
            this.panelFirstFoucus();
        }
    }

    public void downAtBottom() {
        this.panelLastFoucus();
    }

    public boolean getCanPanelMoveDown() {
        return canPanelMoveDown;
    }

    public boolean getCanPanelMoveUp() {
        return canPanelMoveUp;
    }
}
