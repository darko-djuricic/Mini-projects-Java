package drugiZadatak;
import dodatneKlase.MessageBox;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;



public class JednacineKalkulator extends Application {
    ComboBox[][] plusMinus=new ComboBox[3][3];
    ComboBox[] plusMinusJednakost=new ComboBox[3];
    TextField[][] koeficijenti=new TextField[3][3];
    TextField[] zbir=new TextField[3];
    Label[] jednako=new Label[3];
    HBox[] hboxes=new HBox[3];
    Label rezultatX=new Label("");
    Label rezultatY=new Label("");
    Label rezultatZ=new Label("");

    @Override
    public void start(Stage primaryStage){
        for (int i = 0; i < plusMinus.length; i++) {
            hboxes[i]=new HBox(10);
            hboxes[i].setAlignment(Pos.CENTER);

            zbir[i]=new TextField();
            zbir[i].setMinWidth(40);
            zbir[i].setMaxWidth(40);
            zbir[i].setFont(new Font(16));

            jednako[i]=new Label("=");
            jednako[i].setFont(new Font(18));

            plusMinusJednakost[i]=new ComboBox();
            plusMinusJednakost[i].getItems().addAll("+","-");
            plusMinusJednakost[i].setValue("+");
            plusMinusJednakost[i].setStyle("-fx-font-size: 16");

            for (int j = 0; j < plusMinus[i].length; j++) {
                plusMinus[i][j]=new ComboBox();
                plusMinus[i][j].getItems().addAll("+","-");
                plusMinus[i][j].setValue("+");
                plusMinus[i][j].setStyle("-fx-font-size: 16");

                koeficijenti[i][j]=new TextField();
                koeficijenti[i][j].setMinWidth(40);
                koeficijenti[i][j].setMaxWidth(40);
                koeficijenti[i][j].setFont(new Font(16));

            }
            Label x=new Label("X");
            x.setFont(new Font(16));
            Label y=new Label("Y");
            y.setFont(new Font(16));
            Label z=new Label("Z");
            z.setFont(new Font(16));

            hboxes[i].getChildren().addAll(plusMinus[i][0],koeficijenti[i][0], x, plusMinus[i][1], koeficijenti[i][1], y, plusMinus[i][2], koeficijenti[i][2], z, jednako[i], plusMinusJednakost[i], zbir[i]);
        }

        Label lblNaslov=new Label("Kalkulator sistema jednacina sa 3 nepoznate");
        lblNaslov.setFont(new Font(24));
        lblNaslov.setTextFill(Color.DARKCYAN);
        HBox naslov=new HBox(lblNaslov);
        naslov.setPadding(new Insets(20,0,20,0));
        naslov.setAlignment(Pos.CENTER);

        Button btn=new Button("Izracunaj");
        btn.setOnAction(e-> btnIzracunaj());
        HBox hBoxBtn=new HBox(btn);
        hBoxBtn.setAlignment(Pos.CENTER);
        btn.setMinWidth(600);
        btn.setFont(new Font(18));
        btn.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY,Insets.EMPTY)));

        VBox vBox=new VBox(20);
        HBox rezultat=new HBox(50);

        rezultatX.setPadding(new Insets(0,50,0,0));
        rezultatY.setPadding(new Insets(0,50,0,0));
        rezultatZ.setPadding(new Insets(0,50,0,0));
        rezultatX.setFont(new Font(18));
        rezultatY.setFont(new Font(18));
        rezultatZ.setFont(new Font(18));
        rezultatX.setTextFill(Color.GREEN);
        rezultatY.setTextFill(Color.GREEN);
        rezultatZ.setTextFill(Color.GREEN);

        Label lblx=new Label("X: ");
        lblx.setFont(new Font(18));
        Label lbly=new Label("Y: ");
        lbly.setFont(new Font(18));
        Label lblz=new Label("Z: ");
        lblz.setFont(new Font(18));

        rezultat.setAlignment(Pos.CENTER);
        rezultat.setPadding(new Insets(20,0,0,0));
        rezultat.getChildren().addAll(lblx,rezultatX,lbly,rezultatY,lblz,rezultatZ);
        vBox.getChildren().addAll(naslov,hboxes[0],hboxes[1],hboxes[2], hBoxBtn,rezultat);
        Scene scene=new Scene(vBox);
        primaryStage.setMaxHeight(500);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(700);
        primaryStage.setMaxWidth(700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void btnIzracunaj() {
        try{
            InetAddress address=InetAddress.getByName("127.0.0.1");
            Socket socket=new Socket(address,6000);
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);

            double[][] matrica=new double[4][3];
            String request="";
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if(i==3)
                        matrica[i][j]=Double.parseDouble(zbir[j].getText())*(plusMinus[2][j].getValue().equals("-")?-1:1);
                    else
                        matrica[i][j]=Double.parseDouble(koeficijenti[i][j].getText())*(plusMinus[i][j].getValue().equals("-")?-1:1);

                    request+=matrica[i][j]+(i==3 && j==2?"":"#");
                }
                request+=i==3?"":"%";
            }

            out.println(request);
            String[] response=in.readLine().split("%");
            rezultatX.setText(response[0]);
            rezultatY.setText(response[1]);
            rezultatZ.setText(response[2]);
        }catch (Exception e1){
            MessageBox.show("Greska","Morate uneti sva polja. Polja ne smeju da sadrze karaktere");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
