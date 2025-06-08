
import java.util.ArrayList;
import java.util.HashMap;

public final class Kabal{
    private KortStokk kortStokk = new KortStokk(); 
    private Kort[][] start = start(kortStokk);
    private Kort[] bunke;
    private int bKNr;
    public Kabal(){
        skjulK();
        bunke = trekkKortBunke();
    }
    public ArrayList rad(int rNr){
        ArrayList<Kort> rad= new ArrayList<>();

        for (int i = 0; i < rNr; i++) {
            rad.add(kortStokk.trekkKort()); 
        }
        return rad;
    }
    //skal bare kalles en gang
    public ArrayList[] start(KortStokk s){
        ArrayList spill[] = new ArrayList[7];
        for (int i = 0; i < 7; i++) {
            spill[i] = rad(i+1);
        }

        return spill;
    }

    public void skjulK(){
        for(int i = 0; i<start.length;i++){
            for (int z = 0;z<start[i].length;z++){
                if(start[i].length-1!=z)
                    System.out.print(start[i][z].getBakside() +" ");
                else
                    System.out.print(start[i][z].toString() +" ");
                
            }
            System.out.println("\n");
        }
    }

    // må skje etter at kabal er lagt
    public Kort[] trekkKortBunke(){
        Kort[] bunke = new Kort[24];
        for(int i =0; i<24;i++){
            bunke[i] = kortStokk.trekkKort();
        }
        return bunke;
    }
    public Kort[] hentBunke(){
        return bunke;
    }

    public void trekk3Kort(){
        if(bKNr>=bunke.length-1){
            bKNr=0;
        }
        if(bKNr==0) bKNr+=2;
        else bKNr+=3;
        System.out.println(bunke[bKNr].toString());
    }

    

    public static void main(String[] args) {
        Kabal kabal = new Kabal();
        
        for (Kort kort : kabal.hentBunke()) {
            System.out.println(kort.toString());
        }
        System.out.println("++");
        kabal.trekk3Kort();
    }
       
}