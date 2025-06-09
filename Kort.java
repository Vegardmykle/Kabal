import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Kort {
    private HashMap<String, Integer> rankene = new HashMap<>();
    private String denneRank;
    private String denneFarge;
    private ImageIcon bilde;
    private String bakside= "#";

    public Kort(String rank, String farge) {
        this.denneFarge = farge.toLowerCase();
        this.denneRank = rank;
        
        // Initialize card values
        for (int i = 2; i <= 10; i++) {
            rankene.put(String.valueOf(i), i);
        }
        rankene.put("J", 11);
        rankene.put("Q", 12);
        rankene.put("K", 13);
        rankene.put("A", 1);
        
        // Load image
        this.bilde = hentBilde();
    }

    public int verdi() {
        return rankene.get(denneRank);
    }
    public String hentRank(){
        return denneRank;
    }
    public String hentSort(){
        return denneFarge;
    }

    public ImageIcon hentBilde() {
        try {
            String filnavn;
            switch (denneRank) {
                case "J":
                    filnavn = "jack_of_" + denneFarge + "2.png";
                    break;
                case "Q":
                    filnavn = "queen_of_" + denneFarge + "2.png";
                    break;
                case "K":
                    filnavn = "king_of_" + denneFarge + "2.png";
                    break;
                case "A":
                    filnavn = "ace_of_" + denneFarge + ".png";
                    break;
                default:
                    filnavn = denneRank + "_of_" + denneFarge + ".png";
                    break;
            }
            
            // Try to load from resources first
            BufferedImage img;
            try {
                img = ImageIO.read(getClass().getResource("/PNG-cards/" + filnavn));
            } catch (Exception e) {
                // Fall back to file system
                img = ImageIO.read(new File("PNG-cards/" + filnavn));
            }
            
            return new ImageIcon(img);
        } catch (IOException e) {
            System.err.println("Kunne ikke laste bilde for " + denneRank + " " + denneFarge);
            return null;
        }
    }

    @Override
    public String toString() {
        return denneRank + " " + denneFarge;
    }
    public String getBakside(){
        return bakside;
    }
    
    public ImageIcon getBilde() {
        return bilde;
    }
}