package se.tetris.component.boardlogic;

import se.tetris.blocks.Block;
import se.tetris.component.boardui.BoardLevelPanel;
import se.tetris.component.boardui.BoardScorePanel;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

public class StrategyPattern {
    private ColorBlindnessStrategy colorBlindnessStrategy;
    private DifficultyStrategy difficultyStrategy;

    public void colorBlindMode(SimpleAttributeSet styleSet, Block block) {
        colorBlindnessStrategy.colorBlindMode(styleSet, block);
    }

    public void colorBlock(int block, SimpleAttributeSet stylesetCur, StyledDocument boardDoc, int offset, int j) {
        colorBlindnessStrategy.colorBlock(block, stylesetCur, boardDoc, offset, j);
    }

    public void colorBlindModeCurrent(int offset, SimpleAttributeSet stylesetCur, StyledDocument boardDoc, Block curr) {
        colorBlindMode(stylesetCur, curr);
        boardDoc.setCharacterAttributes(offset, 1, stylesetCur, true);
    }

    public int getInterval(int blockNumber, int eraseCnt, BoardLevelPanel boardLevelPanel, BoardScorePanel boardScorePanel) {
        return difficultyStrategy.getInterval(blockNumber, eraseCnt, boardLevelPanel, boardScorePanel);
    }

    public void setColorBlindnessStrategy(ColorBlindnessStrategy colorBlindnessStrategy) {
        this.colorBlindnessStrategy = colorBlindnessStrategy;
    }

    public void setDifficultyStrategy(DifficultyStrategy difficultyStrategy) {
        this.difficultyStrategy = difficultyStrategy;
    }
}
