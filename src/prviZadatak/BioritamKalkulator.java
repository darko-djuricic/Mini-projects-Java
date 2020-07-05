package prviZadatak;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class BioritamKalkulator extends Application {
    Text naslov;
    Text poruka;
    DatePicker datePicker;
    Button btnIzracunaj;
    TextField fiz;
    TextField emoc;
    TextField intel;
    @Override
    public void start(Stage primaryStage) throws Exception {
        naslov=new Text("Izracunajte svoj bioritam za danasnji dan");
        naslov.setFont(new Font(26));
        naslov.setFill(Color.DARKBLUE);

        HBox naslovHbox=new HBox();
        naslovHbox.setAlignment(Pos.CENTER);
        naslovHbox.setPadding(new Insets(20,0,20,0));
        naslovHbox.getChildren().add(naslov);

        datePicker=new DatePicker();
        datePicker.setMinWidth(400);
        datePicker.setMaxWidth(400);

        btnIzracunaj=new Button("Izracunaj");

        btnIzracunaj.setOnAction(event -> {
            try {
                InetAddress address=InetAddress.getByName("127.0.0.1");
                Socket socket=new Socket(address,9000);
                BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                out.println(datePicker.getValue());
                String[] response=in.readLine().split("%");

                if(response.length==1){
                    poruka.setText(response[0]);
                    datePicker.setStyle("-fx-border-color: red");
                }
                else {
                    fiz.setText(response[0]);
                    emoc.setText(response[1]);
                    intel.setText(response[2]);
                    datePicker.setStyle("-fx-border-color: green");
                    poruka.setText("");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        HBox hBoxIzracunaj=new HBox(20);
        hBoxIzracunaj.getChildren().addAll(datePicker,btnIzracunaj);
        hBoxIzracunaj.setAlignment(Pos.CENTER);
        hBoxIzracunaj.setPadding(new Insets(20,0,0,0));

        HBox hboxPoruka=new HBox();
        poruka=new Text();
        poruka.setFill(Color.RED);
        poruka.setFont(new Font(14));
        hboxPoruka.getChildren().addAll(poruka);
        hboxPoruka.setAlignment(Pos.BASELINE_LEFT);
        hboxPoruka.setPadding(new Insets(10,0,20,50));

        HBox hBoxPrikazi=new HBox(30);
        fiz=new TextField();
        emoc=new TextField();
        intel=new TextField();
        fiz.setStyle("-fx-text-fill: green; -fx-font-size: 14");
        emoc.setStyle("-fx-text-fill: green; -fx-font-size: 14");
        intel.setStyle("-fx-text-fill: green; -fx-font-size: 14");
        hBoxPrikazi.setAlignment(Pos.CENTER);
        hBoxPrikazi.setPadding(new Insets(0,10,0,10));
        hBoxPrikazi.getChildren().addAll(fiz,emoc,intel);

        HBox hBoxLabels=new HBox(110);
        hBoxLabels.getChildren().addAll(new Label("Fizicki ritam:"),new Label("Emocijalni ritam:"),new Label("Intelektualni ritam:"));
        hBoxLabels.setAlignment(Pos.CENTER);
        hBoxLabels.setPadding(new Insets(50,0,10,0));

        VBox vBox=new VBox();
        vBox.getChildren().addAll(naslovHbox,hBoxIzracunaj,hboxPoruka,hBoxLabels,hBoxPrikazi);
        vBox.setStyle("-fx-background-color: #ffe88b");

        Scene scene=new Scene(vBox);
        primaryStage.setMinWidth(600);
        primaryStage.setMaxWidth(600);
        primaryStage.setMinHeight(350);
        primaryStage.setMaxHeight(350);
        primaryStage.setTitle("Biortiram kalkulator");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
