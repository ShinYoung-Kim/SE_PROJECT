package se.tetris.blocks;

import java.awt.Color;

public class SBlock extends Block {

    public SBlock() {
        shape = new int[][]{
                {0, 5, 5},
                {5, 5, 0}
        };
        color = Color.GREEN;
        colorBlind = new Color(247, 193, 121);
    }
}