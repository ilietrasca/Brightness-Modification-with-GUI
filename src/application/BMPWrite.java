package application;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class BMPWrite {
	public static void write(BMPImage image, String path) throws IOException {
		long startTime = System.nanoTime();
		
		OutputStream out = null;
		try {
		    out = new BufferedOutputStream(new FileOutputStream(path)); //se seteaza calea imaginii modificate
		    out.write(image.getAllData()); //se scrie imaginea
		    System.out.println("Modified Image: " + path);
		} finally {
		    if (out != null) out.close();
		}
		
		long endTime = System.nanoTime();
		
		System.out.println(String.format("Write time: %.3f ms", (endTime - startTime) * 0.000001));
	}
}
