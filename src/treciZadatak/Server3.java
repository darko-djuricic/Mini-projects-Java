package treciZadatak;

import treci.ServerThread3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server3 {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(3000);
        System.out.println("Server 3 pokrenut...");
        int i=1;
        while (true){
            Socket socket=ss.accept();
            System.out.println("Prihvacen "+(i++)+". zahtev");
            ServerThread st3=new ServerThread(socket);
        }
    }
}
