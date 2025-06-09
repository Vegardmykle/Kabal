
import java.util.ArrayList;

public final class Kabal{
    private KortStokk kortStokk = new KortStokk(); 
    private ArrayList[] brett = start(kortStokk);
    private Kort[] bunke;

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

    private int bKNr;
    public void skjulK(){
        for(int i = 0; i<brett.length;i++){
            ArrayList arr;
            for (int z = 0;z<brett[i].size();z++){
                if(brett[i].size()-1!=z){
                    arr= brett[i];
                    Kort k = (Kort) arr.get(z);
                    System.out.print(k.getBakside() +" ");
                }
                else
                    System.out.print(brett[i].get(z).toString() +" ");
                
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
    String[] sortene = {"hearts","clubs","diamonds","spades"};
    String[] tallene = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

    public boolean sjekkPlassLov(Kort k, int rad){
        Kort siste = (Kort) brett[rad].getLast();
        String farge1;
        String farge2;
        farge1 = switch (siste.hentSort()) {
            case "hearts" -> "rød";
            case "diamonds" -> "rød";
            default -> "sort";
        };
        farge2 = switch (k.hentSort()) {
            case "hearts" -> "rød";
            case "diamonds" -> "rød";
            default -> "sort";
        };
        boolean tall; 
        tall =siste.verdi()-1== k.verdi();
        boolean farge;
        farge =!farge1.equals(farge2); 

        return tall && farge;
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