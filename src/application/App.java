package application;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.*;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Hyperlink;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App {
	@FXML private ImageView imgFirst, imgSecond;
    @FXML private TextField txtOffset;
    @FXML private Label lblRead, lblProc, lblWrite, lblInfo;
    @FXML private ProgressBar pBar;
    @FXML private ProgressIndicator pInd;

    private String[] args = null;
    private BMPImage image = new BMPImage();
    private File file = null;
    
    @FXML
    private void initialize() {
    	 Platform.runLater(() -> {
    		if (args.length > 2) {
    			String path = args[0];
        		String brightnessValue = args[1];
        		file = new File(path);
        		 
        		try {
        			long startTime = System.nanoTime();
        			image = BMPDecode.read(Files.readAllBytes(file.toPath()));
        			long endTime = System.nanoTime();
        			lblRead.setText(String.format("Read time: %.3f ms", (endTime - startTime) * 0.000001));
        			lblInfo.setText("FileSize: " + image.getFileSize() + " bytes\n" + "Width: " + image.getX() + "px\n" + "Height: " + image.getY() + "px");
        			
    				imgFirst.setImage(new Image(file.toURI().toString()));
    				
    				startTime = System.nanoTime();
    				image = ImageBrightness.changeBrightness(image, Integer.parseInt(brightnessValue));
    				endTime = System.nanoTime();
    				lblProc.setText(String.format("Processing time: %.3f ms", (endTime - startTime) * 0.000001));
    				
    				imgSecond.setImage(new Image(new ByteArrayInputStream(image.getAllData())));
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	    });
    }

    @FXML
    private void btnExit(ActionEvent event) {
    	Platform.exit();
    }

    @FXML
    private void pBar() {
    		
    }
    
    @FXML
    private void pInd() {
    		
    }
    
    
    @FXML
    public void hyperGo(ActionEvent event)   {
    	
        Hyperlink hp1 = new Hyperlink("www.github.com");
        hp1.setOnAction((ActionEvent e) -> {
        	});
    	
    }
    

    @FXML
    private void btnApply(ActionEvent event) {
    	ProgressBar pbar = new ProgressBar();
        ProgressIndicator pind = new ProgressIndicator(0);

    	long startTime = System.nanoTime();
    	image = ImageBrightness.changeBrightness(image, Integer.parseInt(txtOffset.getText()));
    	long endTime = System.nanoTime();
        //Create new Task and Thread -  Bind Progress Property to Task Progress
        Task task = taskCreator(Integer.parseInt(txtOffset.getText()));
        pbar.progressProperty().unbind();
        pbar.progressProperty().bind(task.progressProperty());
        pind.progressProperty().unbind();
        pind.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    	lblProc.setText(String.format("Processing time: %.3f ms", (endTime - startTime) * 0.000001));
    	imgSecond.setImage(new Image(new ByteArrayInputStream(image.getAllData())));
    	pbar.progressProperty().unbind();
    }

    @FXML
    private void btnLoad(ActionEvent event) throws IOException {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Import BMP file");
    	Stage stage = (Stage)((Node)((ActionEvent) event).getSource()).getScene().getWindow();
    	file = fileChooser.showOpenDialog(stage);
        if (file != null) {
      
            long startTime = System.nanoTime();
            image = BMPDecode.read(Files.readAllBytes(file.toPath()));
            long endTime = System.nanoTime();
            lblRead.setText(String.format("Read time: %.3f ms", (endTime - startTime) * 0.000001));
            imgFirst.setImage(new Image(file.toURI().toString()));
            lblInfo.setText("FileSize: " + image.getFileSize() + " bytes\n" + "Width: " + image.getX() + "px\n" + "Height: " + image.getY() + "px");
        }
    }
    

    
    
    
    
    
    @FXML
    private void btnSave(ActionEvent event) throws IOException {
    	DirectoryChooser directoryChooser = new DirectoryChooser();
    	Stage stage = (Stage)((Node)((ActionEvent) event).getSource()).getScene().getWindow();
    	File selectedDirectory = directoryChooser.showDialog(stage);

    	if(selectedDirectory != null){
    		long startTime = System.nanoTime();
    		BMPWrite.write(image, selectedDirectory.getAbsolutePath() + File.separator + "Modified" + file.getName());
    		long endTime = System.nanoTime();
    		lblWrite.setText(String.format("Write time: %.3f ms", (endTime - startTime) * 0.000001));
    	}
    }
    

    
    

    
    @FXML
    private void btnReset(ActionEvent event) throws IOException {
        image = BMPDecode.read(Files.readAllBytes(file.toPath()));
        imgSecond.setImage(new Image(file.toURI().toString()));
    }
    
    public void setArgs(String... arg) {
    	args = arg;
    }
    
    
    
    //Create a New Task
    private Task taskCreator(int seconds){
        return new Task() {

                   @Override
                   protected Object call() throws Exception {
                       for(int i=0; i<seconds;i++){
                        Thread.sleep(1000);
                        updateProgress(i+1, seconds);
                       
                       }
                       return true;
                   }
               };
    }
}
