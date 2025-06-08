public class Kabal{
    
    public Kort[] rad(KortStokk s,int rNr){
        Kort rad[] = new Kort[rNr];
        for (int i = 0; i < rNr; i++) {
            rad[i] = s.trekkKort();
        }


        return rad;
    }
    public Kort[][] spill(KortStokk s){
        Kort spill[][] = new Kort[7][];
        for (int i = 0; i < 7; i++) {
            spill[i] = rad(s, i+1);
        }
        return spill;
    }
    
    public static void main(String[] args) {
        KortStokk k = new KortStokk();
        Kabal kabal = new Kabal();

        Kort s[][] = kabal.spill(k);
        for(Kort[] kort : s){
            for (Kort m : kort){
                System.out.print(m.toString() +" ");
            }
            System.out.println("\n");
        }

    }
       
}