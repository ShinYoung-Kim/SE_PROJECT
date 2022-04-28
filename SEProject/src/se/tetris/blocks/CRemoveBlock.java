package se.tetris.blocks;

public class CRemoveBlock extends ItemBlock {
    public CRemoveBlock() {
        itemType = 5;
    }

    public CRemoveBlock(Block input) {
        super(input);
        itemType = 5;
        setItemCoor();
    }
}