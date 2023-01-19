package fi.tuni.prog3.calc;

import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.color;
import javafx.stage.Stage;


/**
 * JavaFX Calculator
 */
public class Calculator extends Application {

    @Override
    public void start(Stage stage) {
        
        var grid = new GridPane();
        
        var textField1 = new TextField();
        textField1.setId("fieldOp1");
        grid.add(textField1,1,0);
        
        var label1 = new Label("First operand:");
        label1.setId("labelOp1");
        grid.add(label1,0,0);
        
        var textField2 = new TextField();
        textField2.setId("fieldOp2");
        grid.add(textField2,1,1);
        
        var label2 = new Label("Second operand:");
        label2.setId("labelOp2");
        grid.add(label2,0,1);
        
        // Buttons
        
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        
        var add = new Button();
        add.setId("btnAdd");
        add.setText("Add");
        hbox1.getChildren().add(add);
        
        var sub = new Button();
        sub.setId("btnSub");
        sub.setText("Subtract");
        hbox1.getChildren().add(sub);
        
        grid.add(hbox1, 0, 2);
        
        var mul = new Button();
        mul.setId("btnMul");
        mul.setText("Multiply");
        hbox2.getChildren().add(mul);

        var div = new Button();
        div.setId("btnDiv");
        div.setText("Divide");
        hbox2.getChildren().add(div);
        
        grid.add(hbox2, 1, 2);
        
        // Results        
        var result = new Label();
        result.setId("fieldRes");
        result.setBackground(new Background(new BackgroundFill(Color.WHITE, 
                CornerRadii.EMPTY, Insets.EMPTY)));
        result.setStyle("-fx-border-color: black;");
        result.setText("");
        result.setMaxWidth(150);
        grid.add(result, 1, 4);
        
        var resLabel = new Label();
        resLabel.setId("labelRes");
        resLabel.setText("Result:");
        grid.add(resLabel, 0, 4);
        
        add.setOnAction( new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                double val1 = Double.parseDouble(textField1.getText());
                double val2 = Double.parseDouble(textField2.getText());
                double res = val1+val2;
                DecimalFormat decimalFormat = new DecimalFormat("0.0####");
                result.setText(decimalFormat.format(res));
            }
        
        });
        
        sub.setOnAction( new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                double val1 = Double.parseDouble(textField1.getText());
                double val2 = Double.parseDouble(textField2.getText());
                double res = val1-val2;
                DecimalFormat decimalFormat = new DecimalFormat("0.0####");
                result.setText(decimalFormat.format(res));
            }
        
        });
        
        mul.setOnAction( new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                double val1 = Double.parseDouble(textField1.getText());
                double val2 = Double.parseDouble(textField2.getText());
                double res = val1*val2;
                DecimalFormat decimalFormat = new DecimalFormat("0.0####");
                result.setText(decimalFormat.format(res));
            }
        
        });
        
        div.setOnAction( new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                double val1 = Double.parseDouble(textField1.getText());
                double val2 = Double.parseDouble(textField2.getText());
                double res = val1/val2;
                DecimalFormat decimalFormat = new DecimalFormat("0.0####");
                result.setText(decimalFormat.format(res));
            }
        
        });
        
        var scene = new Scene(grid, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}