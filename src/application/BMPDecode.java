package application;

public final class BMPDecode {
	public static BMPImage read(byte[] stream) {
		long startTime = System.nanoTime();
		
		byte[] header;
		byte[] data;
		int fileSize;
		int headerSize;
		int width;
		int height;
		int colourDepth;
		BMPImage image = new BMPImage();
		
		if (stream[0] != 'B' && stream[1] != 'M') { //se verifica daca fisierul are extensia .bmp
			System.out.println("Nu este fisier .bmp! Inserati alta imagine cu extensia .bmp!");
		}else {
			fileSize = read4bytes(stream[2], stream[3], stream[4], stream[5]); //se citeste dimensiunea fisierului
			headerSize = read4bytes(stream[14], stream[15], stream[16], stream[17]); //se citeste dimensiunea header-ului
			
			//se desparte header-ul de datele imaginii
			header = new byte[headerSize];
			data = new byte[stream.length - headerSize];
			
			for(int i = 0; i < headerSize; i++) {
				header[i] = stream[i];
			}
			
			for (int i = headerSize; i < stream.length - headerSize; i++) {
				data[i - headerSize] = stream[i];
			}
			
			//se citesc dimensiunile imaginii
			width = read4bytes(header[18], header[19], header[20], header[21]);
			height = read4bytes(header[22], header[23], header[24], header[25]);
			colourDepth = read2bytes(header[28], header[29]); //se citeste formatul unui pixel
			
			if (colourDepth != 24) {
				System.out.println("Aplicatia nu suporta decat imagini cu bitmap pe 24 bits (RGB24)! Inserati alta imagine!");
			}else {
				//se seteaza toate campurile imaginii
				image.setX(width);
				image.setY(height);
				image.setHeader(header);
				image.setAllData(stream);
				image.setImgData(data);
				image.setFileSize(fileSize);
				image.setColourDepth(colourDepth);
				
				System.out.println("Filesize: " + fileSize + " bytes" + "\n" + "Headersize: " + headerSize + " bytes" + "\n" + "Width: " + width + " bytes" + "\n" + "Height: " + height + " bytes" + "\n" + "BitsPerPixel: " + colourDepth + " bytes");
				
				long endTime = System.nanoTime();
				
				System.out.println(String.format("Read time: %.3f ms", (endTime - startTime) * 0.000001));
				return image;
			}
		}
		return null;
	}
	
	//functii pentru citire din header (2 bytes respectiv 4 bytes sunt dimensiunile folosite pentru stocare a informatiilor din header)
	public static int read2bytes(byte s1, byte s2) { //folosita pentru a citi 2 bytes din header
		return ((s2 & 0xFF) << 8) | (s1 & 0xFF);
	}
	
	public static int read4bytes(byte s1, byte s2, byte s3, byte s4) { //folosita pentru a citi 4 bytes din header
		int tmp = (s4 << 24) & 0xFF000000 |
			       (s3 << 16) & 0x00FF0000 |
			       (s2 << 8) & 0x0000FF00 |
			       (s1 << 0) & 0x000000FF;
		return tmp;
	}
}
