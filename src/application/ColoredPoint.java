package application;

public class ColoredPoint extends Point {
	protected int colourDepth; //numarul de bits pentru un pixel
	
	ColoredPoint(){
		this(0,0,0);
	}
	
	ColoredPoint(int x, int y, int colour){
		super(x,y);
		setColourDepth(colour);
	}

	public int getColourDepth() {
		return colourDepth;
	}

	public void setColourDepth(int colour) {
		this.colourDepth = colour;
	}
}
