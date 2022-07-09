package se.tetris.blocks;
public class WeightBlock extends ItemBlock {

    public WeightBlock() {
        itemType = 12;
        setItemCoor();
    }

    public WeightBlock(Block input) {
        super(input);
        itemType = 12;
        setItemCoor();
    }

    public void setItemCoor() {
        for(int i = 0; i<coordinates.size(); i++) {
            blockShape.shape[coordinates.get(i)[0]][coordinates.get(i)[1]] = itemType;
        }
    }
}