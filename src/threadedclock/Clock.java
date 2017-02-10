package threadedclock;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author joomlah
 */
public class Clock extends Application {
    
    double xOffset;
    double yOffset;
    
    Label timeLbl;
    Timer t;
    
    @Override
    public void start(Stage primaryStage) {
        
        StackPane root = new StackPane();
        
        Scene scene = new Scene(root, 100, 75);
        
        
        Rectangle r = new Rectangle(135, 100, Color.SLATEGRAY);
        
        r.setOnMousePressed((MouseEvent e) -> {
            xOffset = primaryStage.getX() - e.getScreenX();
            yOffset = primaryStage.getY() - e.getScreenY();
        });
        
        r.setOnMouseDragged((MouseEvent e) -> {
            primaryStage.setX(e.getScreenX() + xOffset);
            primaryStage.setY(e.getScreenY() + yOffset);
        });
        
        root.getChildren().add(r);
        
        
        
        timeLbl = new Label("timeLbl");
        timeLbl.setScaleX(1.5);
        timeLbl.setScaleY(1.5);
        timeLbl.setTextFill(Color.ALICEBLUE);
        root.getChildren().add(timeLbl);
        
        startClock();
        
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);
        //primaryStage.setTitle("ZT Clock");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
    }
    
    @Override
    public void stop() {
        t.cancel();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    private void startClock() {
        
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    LocalTime lt = LocalTime.now();
                    String s = lt.format(DateTimeFormatter.ofPattern("h:mm:ss a"));
                    
//                    int hour = lt.getHour();
//                    int min = lt.getMinute();
//                    int sec = lt.getSecond();

//                    String timeString = hour + ":" + min + ":" + sec;
                    timeLbl.setText(s);
                });
            }
        }, 0, 1000);
    }
}
