package application;

public class SimpleBMPImage extends ColoredPoint{
	private byte[] allData; //header + imgData
	private String filePath;
	
	SimpleBMPImage(){
		this(0,0,0,null,null);
	}
	
	SimpleBMPImage(int x, int y, int colour, byte[] data, String path){
		super(x,y,colour);
		setAllData(data);
		setFilePath(path);
	}

	public byte[] getAllData() {
		return allData;
	}

	public void setAllData(byte[] data) {
		this.allData = data;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
