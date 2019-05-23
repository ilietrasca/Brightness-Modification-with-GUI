package application;

public abstract class Point implements BMPInterface{
	protected int x; //width
	protected int y; //height
	
	Point(){
		this(0,0);
	}
	
	Point(int x, int y){
		setX(x);
		setY(y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public abstract int getColourDepth();
}
