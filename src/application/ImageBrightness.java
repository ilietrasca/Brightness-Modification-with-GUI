package application;

public class ImageBrightness {
	public static BMPImage changeBrightness(BMPImage image, int offset) {
		long startTime = System.nanoTime();
		
		byte[] imgData = image.getImgData();
		
		//in functie de offset creste/scade luminozitatea imaginii
		//se prelucreaza byte cu byte si se modifica valoarile RGB
		//in vector se gasesc valorile in ordinea Blue -> Green -> Red
		if (offset > 0) {
			for (int i = 0; i < image.getImgData().length; i++) {
				int newValue = ((int) (imgData[i] & 0xFF) + (int) (offset & 0xFF)); //valoarea noua pentru fiecare culoare verificata pentru a nu avea overflow deoarece adunam valorile
				if (newValue > 255){
					imgData[i] = (byte) 255;
				}else {
					imgData[i] = (byte) newValue;
				}
			}
		}else {
			for (int i = 0; i < image.getImgData().length; i++) {
				int newValue = ((int) (imgData[i] & 0xFF) + (int) (offset)); //valoarea noua pentru fiecare culoare verificata pentru a nu avea underflow deoarece scadem valorile
				if (newValue < 0){
					imgData[i] = (byte) 0;
				}else {
					imgData[i] = (byte) newValue;
				}
			}
		}	
		
		//se seteaza campurile noi ale imaginii
		image.setImgData(imgData);
		image.setAllData(imgData);
		
		long endTime = System.nanoTime();
		
		System.out.println(String.format("Processing time: %.3f ms", (endTime - startTime) * 0.000001));
		return image;
	}
}
