package se.tetris.blocks;
public class OneBlock extends ItemBlock {

    public OneBlock() {
        itemType = 3;
        setItemCoor();
    }

    public OneBlock(Block input) {
        super(input);
        itemType = 3;
        setItemCoor();
    }
}