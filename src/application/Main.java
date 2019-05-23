package application;
	
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Main extends Application {
	String[] args = null;
	
	@Override
    public void init() throws Exception {
        super.init();
         
        Parameters parameters = getParameters();
        
        if (parameters != null) {
        	List<String> rawArguments = parameters.getRaw();
           
            args = new String[rawArguments.size()];
            args = rawArguments.toArray(args);
            
            long startTime = System.nanoTime();
           
    		if (args.length < 3) {
    			System.out.println("Nu ati specificat argumentele [String PATH] [Integer BRIGHTNESS_VALUE] [OPTIONAL String nogui]");
    		}else {
    			if (args[2].equals("nogui")) { //se realizeaza procesarea de imagine fara a mai accesa interfata grafica pentru utilizator
    				String path = args[0]; //primul argument este calea catre imaginea de procesat
        			Integer brightness = Integer.valueOf(args[1]); //al doilea argument este valoare cu care se modifica luminozitatea imaginii
        			File file = new File(path); //se introduce fisierul de procesat in aplicatie
        			BMPImage image = new BMPImage(); //se creeaza un obiect de tipul BMPImage
        			String newPath = path.substring(0, path.length() - file.getName().length());
        			newPath = newPath.concat("Modified" + file.getName()); //se extrage path-ul imaginii de modificat pentru a pune noua imagine tot acolo
        			
        			System.out.println("Image name: " + file.getName());
        			
        			image.setFilePath(file.getPath());
        			try {
        				image = BMPDecode.read(Files.readAllBytes(file.toPath())); //citire imagine si decoder header
        				
        				image = ImageBrightness.changeBrightness(image, brightness); //schimbare brightness al imaginii
        				
        				BMPWrite.write(image, newPath); //scriere imagine in noul path
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        			
        			long endTime = System.nanoTime();
        			
        			System.out.println(String.format("Total time: %.3f ms", (endTime - startTime) * 0.000001));
    				Platform.exit();
    			}
    		}
        }
    }
	
	@Override
	public void start(Stage primaryStage) {
		//Se deschide prima fereastra din aplicatie
		try {
			
			Image image = new Image("file:image.png");
			ImageView iv = new ImageView();
			iv.setImage(image);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
			Pane startPane = loader.load();
			startPane.getChildren().add(iv);
			App controller = loader.getController();
			
			controller.setArgs(args);
			primaryStage.setScene(new Scene(startPane));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String... args) {
		launch(args);
	}
	

	
}