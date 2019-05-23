package application;

public final class BMPImage extends SimpleBMPImage{
	private int fileSize; //dimensiune in bytes
	private byte[] header;
	private byte[] imgData;
	
	BMPImage(){
		this(0,0,0,null,null,0,null,null);
	}
	
	BMPImage(int x, int y, int colour, byte[] data, String path, int size, byte[] head, byte[] imgDat){
		super(x,y,colour,data,path);
		setFileSize(size);
		setHeader(head);
		setImgData(imgDat);
	}
	
	public void setAllData(byte[] imgDat) { //override la functia setAllData din SimpleBMPImage
		if (imgDat != null ) {
			byte[] newAllData = new byte[getHeader().length + imgDat.length]; 
			for (int i = 0; i < getHeader().length; i++) {
				newAllData[i] = getHeader()[i];
			}
			for (int i = getHeader().length; i < imgDat.length + getHeader().length; i++) {
				newAllData[i] = imgDat[i - getHeader().length];
			}
			super.setAllData(newAllData);
		}
	}

	public byte[] getHeader() {
		return header;
	}

	public void setHeader(byte[] header) {
		this.header = header;
	}

	public byte[] getImgData() {
		return imgData;
	}

	public void setImgData(byte... imgDat) {
		if (imgDat != null) {
			byte[] newImgData = new byte[imgDat.length];
			int i = 0;
			for (byte s:imgDat) {
				newImgData[i] = s;
				i++;
			}
			
			this.imgData = newImgData;
		}
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
}
