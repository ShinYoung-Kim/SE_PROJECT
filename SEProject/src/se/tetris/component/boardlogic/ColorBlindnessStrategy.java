package se.tetris.component.boardlogic;

import se.tetris.blocks.Block;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

public interface ColorBlindnessStrategy {
    public void colorBlindMode(SimpleAttributeSet styleSet, Block block);
    public void colorBlock(int block, SimpleAttributeSet stylesetCur, StyledDocument boardDoc, int offset, int j);
}
