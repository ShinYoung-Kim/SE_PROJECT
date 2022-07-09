package se.tetris.blocks;

public class CRemoveBlock extends ItemBlock {
    public CRemoveBlock() {
        itemType = 11;
    }

    public CRemoveBlock(Block input) {
        super(input);
        itemType = 11;
        setItemCoor();
    }
}