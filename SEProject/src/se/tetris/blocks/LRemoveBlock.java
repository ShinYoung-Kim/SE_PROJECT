package se.tetris.blocks;

public class LRemoveBlock extends ItemBlock {

	public LRemoveBlock() {
		itemType = 2;
		setItemCoor();
	}

	public LRemoveBlock(Block input) {
		super(input);
		itemType = 2;
		setItemCoor();
	}

}