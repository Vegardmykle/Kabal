public class Kabal{
    
    public Kort[] rad(KortStokk s,int rNr){
        Kort rad[] = new Kort[rNr];
        for (int i = 0; i < rNr; i++) {
            rad[i] = s.trekkKort();
        }


        return rad;
    }
    public Kort[][] start(KortStokk s){
        Kort spill[][] = new Kort[7][];
        for (int i = 0; i < 7; i++) {
            spill[i] = rad(s, i+1);
        }

        return spill;
    }

    public void skjulK(KortStokk s){
        Kort[][] start = start(s);
        for(int i = 0; i<start.length;i++){
            for (int z = 0;z<start[i].length;z++){
                if(start[i].length-1!=z){
                    System.out.print(start[i][z].getBakside() +" ");

                }
                else
                    System.out.print(start[i][z].toString() +" ");
                
            }
            System.out.println("\n");
        }
    }

    public static void main(String[] args) {
        KortStokk k = new KortStokk();
        Kabal kabal = new Kabal();

        kabal.skjulK(k);

    }
       
}