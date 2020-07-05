package prviZadatak;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ServerThread1 extends Thread{
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    public ServerThread1(Socket socket) throws IOException {
        this.socket=socket;
        in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
        start();
    }

    @Override
    public void run() {
        try {
            String request=in.readLine();
            String requestDatum=new SimpleDateFormat("dd.MM.yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(request));
            Date datumRodj=new SimpleDateFormat("dd.MM.yyyy").parse(requestDatum);

            long datum= new Date().getTime()-datumRodj.getTime();

            //Provera datuma ako je datum rodjenja ispred danasnjeg datuma
            if(datum<0){
                out.println("Greska prilikom unosa, pokusajte ponovo");
            }
            long dani= TimeUnit.DAYS.convert(datum,TimeUnit.MILLISECONDS);

            double fiz=Math.round(Math.sin(Math.toDegrees((dani%23)/(23*2*Math.PI)))*100);
            double emoc=Math.round(Math.sin(Math.toDegrees((dani%28)/(28*2*Math.PI)))*100);
            double intel=Math.round(Math.sin(Math.toDegrees((dani%33)/(33*2*Math.PI)))*100);

            String response=(int)fiz+"%"+(int)emoc+"%"+(int)intel;
            out.println(response);
            System.out.println("Trazeni datum: "+requestDatum);
            String[] odgovor=response.split("%");
            System.out.println("Odgovor = Fizicki: "+odgovor[0]+" ; Emocijalni ritam: "+odgovor[1]+" ; Intelektualni ritam: "+odgovor[2]);
        } catch (Exception e) {
            out.println("Datum mora biti formata 1/21/2001");
        }
    }
}
