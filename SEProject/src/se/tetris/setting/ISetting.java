package se.tetris.setting;

import se.tetris.blocks.Block;

import javax.swing.text.SimpleAttributeSet;
import java.awt.event.ActionEvent;

public interface ISetting {
    int modeChoose = 0;
    int colorBlindModeCheck = 1;
    int intervalNumber = 1000;
    public int sizeNumber = 1;
    public int keyChoose = 1;

    public void actionPerformed(ActionEvent e);
}
