package treci;

import javafx.scene.control.Button;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class ServerThread3 extends Thread {
    Socket socket;
    ObjectInputStream objIn;
    ObjectOutputStream objOut;
    PrintWriter out;


    Button[][] dugmad;
    String pocetni="Q";
    boolean provera=true;
    int index1=0;
    int index2=0;
    String[][] lavirint;
    String response;

    public ServerThread3(Socket socket) throws IOException {
        this.socket = socket;
        objIn = new ObjectInputStream(socket.getInputStream());
        objOut = new ObjectOutputStream(socket.getOutputStream());
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        start();
    }


    @Override
    public void run() {
        try {
            lavirint=(String[][]) objIn.readObject();
            for (int i = 0; i < lavirint.length; i++) {
                for (int j = 0; j < lavirint[i].length; j++) {
                    if(lavirint[i][j].equals("R"))
                    {
                        pocetni=lavirint[i][j];
                        break;
                    }
                }
            }

            for (int i = 0; i < 10; i++){
                int[][] pozicije = {{index1 - 1, index2}, {index1, index2 - 1}, {index1, index2}, {index1, index2 + 1}, {index1 + 1, index2}};
                Random random = new Random();
                int rndI = random.nextInt(pozicije.length);

                try{
                    if(lavirint[pozicije[rndI][0]][pozicije[rndI][1]].equals("")){
                        pocetni= lavirint[pozicije[rndI][0]][pozicije[rndI][1]];
                        response+=odgovor(rndI);
                        pocetni="*";
                    }
                }catch (Exception e){
                    continue;
                }
            }

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(lavirint[i][j]+" ");
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


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
