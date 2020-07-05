package dodatneKlase;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MessageBox {
    public static void show(String naslov, String tekst) {
        Stage stage=new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(naslov);
        VBox vBox=new VBox(10);
        Button btn=new Button("Zatvori");
        btn.setOnAction(e->{
            stage.close();
        });
        vBox.getChildren().addAll(new Label(tekst), btn);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(0,10,0,10));
        Scene scene=new Scene(vBox);
        stage.setScene(scene);
        stage.setMinWidth(500);
        stage.setMaxWidth(500);
        stage.setMinHeight(150);
        stage.showAndWait();
    }

}
