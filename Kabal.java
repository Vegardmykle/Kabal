public final class Kabal{
    private KortStokk kortStokk = new KortStokk(); 
    private Kort[][] start = start(kortStokk);
    private Kort[] bunke;
    public Kabal(){
        skjulK();
        bunke = trekkKortBunke();
    }
    public Kort[] rad(int rNr){
        Kort rad[] = new Kort[rNr];
        for (int i = 0; i < rNr; i++) {
            rad[i] = kortStokk.trekkKort();
        }


        return rad;
    }
    //skal bare kalles en gang
    public Kort[][] start(KortStokk s){
        Kort spill[][] = new Kort[7][];
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

    

    public static void main(String[] args) {
        Kabal k = new Kabal();
        for (Kort kort : k.hentBunke()) {
            System.out.println(kort.toString());
        }
    }
       
}