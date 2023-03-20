import java.util.Scanner;
// Jon Telleria
public class Main {
    public static void main(String args[]) {
        int areahand=0, kodebukaera=0;
        for (int kont=0;kont<4;kont++){
            Scanner sarrera = new Scanner(System.in);
            System.out.println("Jarri kodea");
            int kodea =sarrera.nextInt();
            System.out.println("Jarri probintziaren kodea");
            int probkode =sarrera.nextInt();
            System.out.println("Jarri herri kopurua");
            int herrikop =sarrera.nextInt();
            for (int i=0; i<herrikop;i++){
                System.out.println("Jarri kode identifikatzailea");
                int kodeidentif =sarrera.nextInt();
                System.out.println("Jarri area");
                int area =sarrera.nextInt();
                if (area>areahand){
                    areahand=area;
                    kodebukaera=kodeidentif;
                }
                System.out.println("Jarri famili kopurua");
                int familikop=sarrera.nextInt();
                for (int n=0; n<familikop; n++){
                    System.out.println("Jarri familiko ardunaduraren NAN-a");
                    int NAN =sarrera.nextInt();
                    System.out.println("Jarri soldata");
                    int soldata =sarrera.nextInt();
                    System.out.println("Jarri seme kopurua");
                    int semekop=sarrera.nextInt();

                }
            }
            System.out.println("area handiena duen probintziaren kodea hau da: "+kodebukaera);
        }

    }
}

