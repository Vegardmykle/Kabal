
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public final class Kabal{
    private KortStokk kortStokk = new KortStokk(); 
    private ArrayList<Kort>[] brett = start(kortStokk);
    private ArrayList<Kort> bunke = new ArrayList<>();

    private ArrayList<Kort> heartsM = new ArrayList<>();
    private ArrayList<Kort> clubsM = new ArrayList<>();
    private ArrayList<Kort> diamondsM = new ArrayList<>();
    private ArrayList<Kort> spadesM = new ArrayList<>();
    private final HashMap<String, ArrayList<Kort>> målStabler = new HashMap<>();
    private int bKNr = -1;
    private Kort kortFB;

    public Kabal(){
        målStabler.put("hearts", heartsM);
        målStabler.put("clubs", clubsM);
        målStabler.put("diamonds", diamondsM);
        målStabler.put("spades", spadesM);
        visKabal();
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



   public void visKabal() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    System.out.println("==================== K A B A L ====================");
    
    // Vis bunken
    //brukte gpt for å få fin visning
    System.out.println("Bunke: " + (bunke.isEmpty() ? "Tom" : bunke.size() + " kort igjen"));
    if (kortFB != null) {
        String fargeKode = (kortFB.hentSort().equals("hearts") || kortFB.hentSort().equals("diamonds")) 
            ? "\u001B[31m" : "\u001B[30m";
        System.out.println("Sist trukket: " + fargeKode + kortFB.toString() + "\u001B[0m");
    }
    System.out.println("--------------------------------------------------");
    
    System.out.println("Mål stabler" );
    System.out.print((heartsM.isEmpty() ? "Tom  " : heartsM.getLast()+ " "));
    System.out.print((clubsM.isEmpty() ? "Tom  " : clubsM.getLast()+ " "));
    System.out.print((diamondsM.isEmpty() ? "Tom  " : diamondsM.getLast()+ " "));
    System.out.print((spadesM.isEmpty() ? "Tom\n" : spadesM.getLast()+"\n"));
    System.out.println("--------------------------------------------------");
    System.out.println("Rad   Kort");
    System.out.println("--------------------------------------------------");
    //brukt gpt for fin fargevisning
    for(int i = 0; i < brett.length; i++) {
        System.out.printf("%-2d:  ", i);
        for (int z = 0; z < brett[i].size(); z++) {
            Kort k = brett[i].get(z);
            
            if(brett[i].size()-1 != z && !k.visesForside()) {
                System.out.print(k.getBakside() + " ");
            }
            else {
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
    public void brukerinput(){
        boolean sjekk = true;
        while(sjekk) {
            System.out.println("vil du trekke kort(ja/nei/plasser/flytteMellom//LeggeMål)");
            String neste = sc.next();
            
            switch (neste) {
                case "ja":
                    trekk3Kort();
                    break;
                case "plasser":
                    plasserKortB();
                    break;
                case "flytteMellom":
                    flyttKortMellomBunker();
                    break;
                case "LeggeMål":
                    målStabler();
                    break;
                case "nei":
                    sjekk = false;
                    continue;
                
                default:
                    System.out.println("Ugyldig valg!");
                    break;
            }
            visKabal();
            
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
    public void flyttKortMellomBunker(){
        System.out.println("hvilken rad vil du flytte fra(radNr 0-6)");
        int radG = sc.nextInt();
        while (radG < 0 || radG > 6 || brett[radG].isEmpty()) {
            System.out.println("Ugyldig rad!");
            System.out.println("hvilken rad vil du flytte fra(radNr 0-6)");
            radG = sc.nextInt();
        }

        System.out.println("hvilket kort vil du flytte fra (indeks 0-" + (brett[radG].size()-1) + ")");
        int kortIndeks = sc.nextInt();
        while (kortIndeks < 0 || kortIndeks >= brett[radG].size()) {
            System.out.println("Ugyldig indeks!");
            System.out.println("hvilket kort vil du flytte fra (indeks 0-" + (brett[radG].size()-1) + ")");
            kortIndeks = sc.nextInt();
        }
        for (int i = kortIndeks; i < brett[radG].size(); i++) {
            if (!brett[radG].get(i).visesForside()) {
                System.out.println("Kan ikke flytte skjulte kort!");
                return;
            }
        }
        System.out.println("hvor vil du plassere(radNr 0-6)");
        int radP = sc.nextInt();
        while (radP < 0 || radP > 6) {
            System.out.println("Ugyldig målrad!");
            System.out.println("hvor vil du plassere(radNr 0-6)");
            radP = sc.nextInt();
    }

        Kort førsteKort = brett[radG].get(kortIndeks);
        if (!sjekkPlassLov(førsteKort, radP)) {
        System.out.println("Ulovlig plassering!");
        return;
    }
        
        if(sjekkPlassLov(førsteKort, radP)&& førsteKort.visesForside()){
            ArrayList<Kort> flyttet = new ArrayList<>();
            while (kortIndeks < brett[radG].size()) {
            flyttet.add(brett[radG].remove(kortIndeks));
            }
            brett[radP].addAll(flyttet);
        }
    }
    
    public void målStabler(){
        System.out.println("hvilken rad?(0-6)");
        int rad = sc.nextInt();
        while (rad < 0 || rad > 6) {
            System.out.println("ugyldig rad!");
            System.out.println("hvilken rad?(0-6)");
            rad = sc.nextInt();
        }
        Kort kort = brett[rad].getLast();
        if(sjekkMålLov(kort)){
            målStabler.get(kort.hentSort()).add(brett[rad].removeLast());
        }

    }
    private boolean sjekkMålLov(Kort k) {
        if (k == null) return false;
    
        ArrayList<Kort> målStabel = målStabler.get(k.hentSort());
        if (målStabel == null) return false;
    
        if (målStabel.isEmpty()) {
            return k.verdi() == 1;
        }
    
        return målStabel.get(målStabel.size() - 1).verdi() == k.verdi() - 1;
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
        kabal.brukerinput();
        

    }
       
}