package treciZadatak;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Lavirint extends Application {
    Button[][] dugmad=new Button[9][9];
    HBox[] hBoxes=new HBox[9];
    String[][] lavirint={
            {"@","@","@","@","@","@","@","@","@"},
            {"@", "", "", "", "", "","", "R","@"},
            {"@", "","@","@","@","@","@", "","@"},
            {"@", "","@","", "", "", "", "", "E"},
            {"@","@", "","@", "","@","@","@","@"},
            {"@", "", "", "", "", "", "", "","@"},
            {"@", "","@","@","@","@","@", "","@"},
            {"@", "", "", "", "", "", "", "","@"},
            {"@","@","@","@","@","@","@","@","@"}
    };
    Label odgovor=new Label("");
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox=new VBox();
        Label naslov=new Label("Lavirint");
        naslov.setFont(new Font(33));
        naslov.setPadding(new Insets(10,0,20,0));
        naslov.setTextFill(Color.DARKCYAN);
        naslov.setAlignment(Pos.CENTER);
        vBox.getChildren().add(naslov);
        for (int i = 0; i < hBoxes.length; i++) {
            for (int j = 0; j < hBoxes.length; j++) {
                dugmad[i][j]=new Button();
                Button btn=dugmad[i][j];
                btn.setFont(new Font(20));
                btn.setMinWidth(50);
                btn.setMaxWidth(50);
                btn.setMaxHeight(50);
                btn.setMinHeight(50);
                btn.setText(lavirint[i][j]);
                //Postavljanje boja za odrenjene karaktere u lavirintu
                btn.setTextFill(btn.getText().equals("@")?Color.DARKRED:btn.getText().equals("R")?Color.DARKBLUE:Color.GREEN);

                
                btn.setOnAction(event -> btnChange(btn));

            }

            hBoxes[i]=new HBox();
            hBoxes[i].setAlignment(Pos.CENTER);
            hBoxes[i].getChildren().addAll(dugmad[i]);
            vBox.getChildren().add(hBoxes[i]);
        }
        Button btnPokreni=new Button("Pokreni");
        btnPokreni.setFont(new Font(18));
        btnPokreni.setDefaultButton(true);
        btnPokreni.setOnAction(e->{
            pokreni();
        });
        HBox hBoxBtn=new HBox();
        hBoxBtn.getChildren().addAll(btnPokreni);
        hBoxBtn.setPadding(new Insets(20,0,20,0));
        hBoxBtn.setAlignment(Pos.CENTER);

        vBox.getChildren().add(hBoxBtn);
        odgovor.setAlignment(Pos.CENTER);
        odgovor.setFont(new Font(18));
        vBox.getChildren().add(odgovor);

        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY,Insets.EMPTY)));
        Scene scene=new Scene(vBox,900,700);
        primaryStage.setMinWidth(600);
        primaryStage.setMaxHeight(800);
        primaryStage.setMinHeight(700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void btnChange(Button btn) {
        boolean proveraRobot=true;
        boolean proveraIzlaz=true;

        //provera da li postoji robot ili izlaz
        for (int i = 0; i < dugmad.length; i++) {
            for (int j = 0; j < dugmad[i].length; j++) {
                if(dugmad[i][j].getText().equals("R"))
                    proveraRobot=false;

                if(dugmad[i][j].getText().equals("E"))
                    proveraIzlaz=false;
            }
        }

        switch (btn.getText()){
            case "":
                btn.setText("@");
                btn.setTextFill(Color.DARKRED);
                break;
            case "@":
                if(proveraRobot){
                    btn.setText("R");
                    btn.setTextFill(Color.DARKBLUE);
                }
                else{
                    if(proveraIzlaz){
                        btn.setText("E");
                        btn.setTextFill(Color.GREEN);
                    }
                    else{
                        btn.setText("");
                    }
                }
                break;
            case "R":
                if(proveraIzlaz){
                    btn.setText("E");
                    btn.setTextFill(Color.GREEN);
                }
                else
                    btn.setText("");
                break;
            case "E":
                btn.setText("");
                return;
            case "*":
                btn.setText("");
                break;

        }

    }

    private void pokreni() {
        String[][] maze=new String[dugmad.length][dugmad[0].length];
        boolean provera1=false;
        boolean provera2=false;
        for (int i = 0; i < dugmad.length; i++) {
            for (int j = 0; j < dugmad[i].length; j++) {
                if(dugmad[i][j].getText().equals("*"))
                    dugmad[i][j].setText("");

                if(dugmad[i][j].getText().equals("R"))
                    provera1=true;

                if(dugmad[i][j].getText().equals("E"))
                    provera2=true;

                maze[i][j]=dugmad[i][j].getText();
            }
        }

        if(!provera1 || !provera2){
            System.out.println("UDJOH");
            odgovor.setText("Lavirint mora da sadrzi robota (R) i izlaz (E)");
            odgovor.setTextFill(Color.RED);
            return;
        }

        try {
            InetAddress address=InetAddress.getByName("127.0.0.1");
            Socket socket=new Socket(address,3000);
            ObjectOutputStream objOut=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objIn=new ObjectInputStream(socket.getInputStream());
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            objOut.writeObject(maze);
            String[][] response= (String[][]) objIn.readObject();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    dugmad[i][j].setText(response[i][j]);
                    if(dugmad[i][j].getText().equals("*"))
                        dugmad[i][j].setTextFill(Color.GREEN);
                }
            }

            String[] res=in.readLine().split("%");
            odgovor.setText(Boolean.parseBoolean(res[1])?res[0]:"Nema pronadjenih, pokusajte ponovo");
            odgovor.setTextFill(Boolean.parseBoolean(res[1])?Color.GREEN:Color.RED);

            //AKO NIJE DOSAO DO KRAJA, TRAZI SE OPET
            if(!Boolean.parseBoolean(res[1]))
                pokreni();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        String[] strArray = {"orange", "red", "green'"};
        String[] copiedArray = Arrays.stream(strArray).toArray(String[]::new);
        strArray[1]="ASD";
        System.out.println(copiedArray[1]);
        launch(args);
    }
}
