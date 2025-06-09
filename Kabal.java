
import java.util.ArrayList;
import java.util.Scanner;

public final class Kabal{
    private KortStokk kortStokk = new KortStokk(); 
    private ArrayList<Kort>[] brett = start(kortStokk);
    private ArrayList<Kort> bunke;

    private int bKNr = -1;
    private Kort kortFB;

    public Kabal(){
        skjulK();
        bunke = trekkKortBunke();
    }
    public ArrayList<Kort> rad(int rNr){
        ArrayList<Kort> rad= new ArrayList<>();

        for (int i = 0; i < rNr; i++) {
            rad.add(kortStokk.trekkKort()); 
        }
        return rad;
    }
    //skal bare kalles en gang
    @SuppressWarnings("unchecked")
    public ArrayList<Kort>[] start(KortStokk s){
        ArrayList<Kort> spill[] = new ArrayList[7];
        for (int i = 0; i < 7; i++) {
            spill[i] = rad(i+1);
        }

        return spill;
    }



    public void skjulK(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("==================== K A B A L ====================");
        System.out.println("Rad   Kort");
        System.out.println("--------------------------------------------------");
        
        for(int i = 0; i<brett.length;i++){

            System.out.printf("%-2d:  ", i);
            for (int z = 0;z<brett[i].size();z++){
                Kort k = brett[i].get(z);
                
                if(brett[i].size()-1!=z&&!k.visesForside()){
                    System.out.print(k.getBakside() +" ");
                }
                else{
                    String fargeKode = "";
                    if (k.hentSort().equals("hearts") || k.hentSort().equals("diamonds")) {
                        fargeKode = "\u001B[31m"; // Rød tekst
                    } else {
                        fargeKode = "\u001B[30m"; // Svart tekst
                    }
                    System.out.print(fargeKode + k.toString() + "\u001B[0m ");

                    k.settSynlig(true);
                }
                
            }
            System.out.println("\n");
        }
    }
    
    // må skje etter at kabal er lagt
    public ArrayList<Kort> trekkKortBunke(){
        ArrayList<Kort> bunke = new ArrayList<>();

        for(int i =0; i<24;i++){
            bunke.add(kortStokk.trekkKort());
        }
        return bunke;
    }
    public ArrayList<Kort> hentBunke(){
        return bunke;
    }
    
    public void trekk3Kort(){
        if (bunke.isEmpty()) {
            System.out.println("Bunken er tom!");
            kortFB = null;
            return;
        }

        if (bKNr >= bunke.size() - 1) {
            bKNr = -1;
        }
        

        bKNr = (bKNr + 3) % bunke.size();
        kortFB = bunke.get(bKNr);
        System.out.println("Trukket kort: " + kortFB.toString());
    }

    Scanner sc = new Scanner(System.in);
    public void trekk3KortB(){
        boolean sjekk = true;
        while(sjekk){
            System.out.println("vil du trekke kort(ja/nei/plasser)");
            String neste = sc.next();
            sjekk = neste.equals("ja");
            skjulK();
            if(sjekk) trekk3Kort();
            if(neste.equals("plasser")) {
                plasserKortB();
                sjekk= true;
            }
        }
    }
    public void plasserKortB(){
        System.out.println("hvor vil du plassere(radNr 0-6)");
        int hvor = sc.nextInt();
        if(!sjekkPlassLov(kortFB, hvor)){
            System.out.println("ulovlig plassering!");
            return;
        }
        kortFB.settSynlig(true);
        brett[hvor].add(kortFB);
        bunke.remove(kortFB);
        kortFB = null;


        bKNr = bunke.isEmpty() ? -1 : Math.min(bKNr, bunke.size() - 1);

    }

    String[] sortene = {"hearts","clubs","diamonds","spades"};
    public boolean sjekkPlassLov(Kort k, int rad){
        if(rad<0||rad>6) return false;

        if (brett[rad].isEmpty()) {
            return "K".equals(k.hentRank());
        }

        Kort siste = brett[rad].get(brett[rad].size() - 1);

        String farge1;
        String farge2;
        farge1 = switch (siste.hentSort()) {
            case "hearts","diamonds" -> "rød";
            case "clubs","spades" -> "sort";
            default -> "ukjent";
        };
        farge2 = switch (k.hentSort()) {
            case "hearts","diamonds" -> "rød";
            case "clubs","spades" -> "sort";
            default -> "ukjent";
        };

        boolean fargeOk =!farge1.equals(farge2); 
        boolean tallOk =(siste.verdi() - 1 == k.verdi());   
        return tallOk && fargeOk;
    } 

    

    public static void main(String[] args) {
        Kabal kabal = new Kabal();
        kabal.trekk3KortB();
        

    }
       
}