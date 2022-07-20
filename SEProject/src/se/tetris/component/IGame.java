package se.tetris.component;

import se.tetris.blocks.Block;

import javax.swing.text.SimpleAttributeSet;
import java.awt.event.ActionEvent;

public interface IGame {
    //public Block getRandomBlock(int modeChoose);
    //public void collisionOccur();
    //public void drawBoard();
    //public void colorBlindMode(SimpleAttributeSet styleSet, Block block);
    //public int getInterval(int blockNumber, int eraseCnt);
    //public void reset();
    //public void changeSize(int sizeNumber);

    public void actionPerformed(ActionEvent e);
}
