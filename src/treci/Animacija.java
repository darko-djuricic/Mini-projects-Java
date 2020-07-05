package treci;

import javafx.scene.control.Button;

import java.io.*;
import java.net.Socket;

public class Animacija extends Thread {
    Socket socket;
    ObjectInputStream objIn;
    ObjectOutputStream objOut;
    PrintWriter out;
    String pocetni;

    Button[][] dugmad;
    boolean provera=true;
    int index1=0;
    int index2=0;
    String[][] lavirint;
    String response;
    String[][] lavirintResponse;

    public Animacija(Socket socket) throws IOException {
        this.socket=socket;
        objIn=new ObjectInputStream(socket.getInputStream());
        objOut=new ObjectOutputStream(socket.getOutputStream());
        out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
        start();
    }



//    public void run() {
//        try {
//            lavirint=(String[][])objIn.readObject();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        while (true) {
//
//            try {
//                //ISPISIVANJE
////                Platform.runLater(new Runnable() {
////                    @Override
////                    public void run() {
////                        if (pocetno.getText().equals("E")) {
////                            provera = false;
////                            pocetno.setTextFill(Color.GREEN);
////                        }
////
////
////                        if (pocetno.getText().equals(""))
////                            pocetno.setText("*");
////
////                        if (pocetno.getText().equals("*"))
////                            pocetno.setTextFill(Color.RED);
////                    }
////                });
//
//                //PROVERA DA LI POSTOJI MESTA ZA DALJI RAD
//
//
//                for (int i = 0; i < lavirint.length; i++) {
//                    for (int j = 0; j < lavirint.length; j++) {
//                        if (pocetno.equals(lavirint[i][j])) {
//                            index1 = i;
//                            index2 = j;
//                        }
//                    }
//                }
//
//                if (!provera)
//                    break;
//
//
//                int[][] pozicije = {{index1 - 1, index2}, {index1, index2 - 1}, {index1, index2}, {index1, index2 + 1}, {index1 + 1, index2}};
//
//                Random random = new Random();
//                int rndI = random.nextInt(pozicije.length);
//
//                if(lavirint[pozicije[rndI][0]][pozicije[rndI][1]].equals("")){
//                    pocetno= lavirint[pozicije[rndI][0]][pozicije[rndI][1]];
//                    response+=odgovor(rndI);
//                }
//
////                pocetno = (!dugmad[pozicije[rndI][0]][pozicije[rndI][1]].getText().equals("")) ? pocetno :
//
//                int spavanje = pocetno.equals("@") ? 0 : 100;
//                Thread.sleep(spavanje);
//
//            } catch (Exception e) {
//                continue;
//            }
//        }
//        out.println(response);
//    }

    private String odgovor(int rndI) {
        switch (rndI){
            case 0:
                return "Gore ";
            case 1:
                return "Levo ";
            case 3:
                return "Desno ";
            case 4:
                return "Dole ";
        }
        return "";
    }


}
