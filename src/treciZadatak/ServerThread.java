package treciZadatak;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ServerThread extends Thread {
    Socket socket;
    ObjectOutputStream objOut;
    ObjectInputStream objIn;
    PrintWriter out;
    BufferedReader in;
    int index1=0;
    int index2=0;
    int krajI=0;
    int krajJ=0;

    volatile boolean provera=false;
    public ServerThread(Socket socket) throws IOException {
        this.socket=socket;
        objIn=new ObjectInputStream(socket.getInputStream());
        objOut=new ObjectOutputStream(socket.getOutputStream());
        out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
//        in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        start();
    }

    int[] gore;
    int[] levo;
    int[] dole;
    int[] desno;

    @Override
    public void run() {
        try {
            String response="";
            String [][] lavirint= (String[][]) objIn.readObject();

                for (int i = 0; i < lavirint.length; i++) {
                    for (int j = 0; j < lavirint[i].length; j++) {
                        if(lavirint[i][j].equals("R")){
                            index1=i;
                            index2=j;
                        }
                        if(lavirint[i][j].equals("E")){
                            krajI=i;
                            krajJ=j;
                        }
                        if(lavirint[i][j].equals("*"))
                            lavirint[i][j]="";
                    }
                }

                for (int q = 0; q < 2000; q++) {


                    gore= new int[]{index1 - 1, index2};
                    levo= new int[]{index1, index2 - 1};
                    desno= new int[]{index1, index2 + 1};
                    dole= new int[]{index1 + 1, index2};


                    ArrayList<int[]> pozicije=new ArrayList<>();

                    for (int[] k : pozicije) {
                        pozicije.remove(k);
                    }

                    pozicije.add(levo);
                    pozicije.add(desno);
                    pozicije.add(dole);
                    pozicije.add(gore);

//                    PROVERA GDE SE NALAZI CILJ U ODNOSU NA TRENUTNU POZICIJU, I NA OSNOVU TOGA SE KRECEMO U ODREJENJIM SMEROVIMA

//                    if(index1<krajI){
//                        pozicije.remove(gore);
//                    }
//                    else{
//                        if(index1>krajI){
//                            pozicije.remove(dole);
//                        }
//                        else{
//                            if(index2<krajJ){
//                                pozicije.remove(levo);
//                                if(!lavirint[desno[0]][desno[1]].equals(""))
//                                    pozicije.remove(desno);
//                                else
//                                {
//                                    pozicije.remove(gore);
//                                    pozicije.remove(dole);
//                                }
//                            }
//                            else{
//                                pozicije.remove(desno);
//                                if(!lavirint[levo[0]][levo[1]].equals(""))
//                                    pozicije.remove(levo);
//                                else
//                                {
//                                    pozicije.remove(gore);
//                                    pozicije.remove(dole);
//                                }
//                            }
//                        }
//                    }
//
//                    if(index2==krajJ){
//                        if(index1<krajI){
//                            pozicije.remove(gore);
//                            if(!lavirint[dole[0]][dole[1]].equals(""))
//                                pozicije.remove(dole);
//                            else
//                            {
//                                pozicije.remove(levo);
//                                pozicije.remove(desno);
//                            }
//                        }
//                        else{
//                            pozicije.remove(dole);
//                            if(!lavirint[gore[0]][gore[1]].equals(""))
//                                pozicije.remove(gore);
//                            else
//                            {
//                                pozicije.remove(levo);
//                                pozicije.remove(desno);
//                            }
//                        }
//                    }



                    if(index1==0){
                        pozicije.remove(levo);
                    }else{
                        if(index1==lavirint.length-1){
                            pozicije.remove(desno);
                            pozicije.remove(gore);
                            pozicije.remove(dole);
                        }

                    }

                    if(lavirint[index1][index2].equals("E")){
                        provera=true;
                        break;
                    }
                    //*****************************************

                    Random random = new Random();
                    int rndI = random.nextInt(pozicije.size());

                    try{
                        if(lavirint[gore[0]][gore[1]].equals("E") || lavirint[levo[0]][levo[1]].equals("E") || lavirint[dole[0]][dole[1]].equals("E") || lavirint[desno[0]][desno[1]].equals("E")){

                            index1=krajI; index2=krajJ;
                            response+=odgovor(lavirint[gore[0]][gore[1]].equals("E")?gore:lavirint[levo[0]][levo[1]].equals("E")?levo:lavirint[dole[0]][dole[1]].equals("E")?dole:desno);
                        }
                        else
                        {
                            if(lavirint[pozicije.get(rndI)[0]][pozicije.get(rndI)[1]].equals("") || lavirint[pozicije.get(rndI)[0]][pozicije.get(rndI)[1]].equals("E")){
                                lavirint[pozicije.get(rndI)[0]][pozicije.get(rndI)[1]]=lavirint[pozicije.get(rndI)[0]][pozicije.get(rndI)[1]].equals("")?"*":"E";
                                index1=pozicije.get(rndI)[0];
                                index2=pozicije.get(rndI)[1];
                                response+=odgovor(pozicije.get(rndI));
                            }
                        }

                        //********************************************
                    }catch (ArrayIndexOutOfBoundsException e) {  continue; }

                }


                objOut.writeObject(lavirint);
                out.println(response+"%"+provera);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String odgovor(int[] ints) {
        if (ints == gore) {
            return "Gore ";
        } else if (ints == levo) {
            return "Levo ";
        } else if (ints == dole) {
            return "Dole ";
        } else if (ints == desno) {
            return "Desno ";
        }
        return "";
    }
}
