package drugiZadatak;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(6000);
            int i=0;
            System.out.println("Server2 pokrenut...");
            while (true){
                Socket socket=ss.accept();
                System.out.println("Prihvacen zahtev "+(++i)+".");
                ServerThread2 st2=new ServerThread2(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
