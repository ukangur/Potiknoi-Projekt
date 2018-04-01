import java.util.ArrayList;
import java.util.List;

public class Kaardipakk {

    /*Kuidas kasutada:
            1) Kui tahad kasutada: Kaardipakk()
            2) Kui tahad kasutada väikest pakki: Kaardipakk("väike")

    Kaardipakid saab kätte get-meetoditega:
            getKaardipakk()
            getRisti()
            getRuutu()
            getPoti()
            getÄrtu()*/

    private List<Kaart> pakk;
    private String[] tugevused = {"A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2"};
    private char[] mastid = {'♣', '♦', '♠', '♥'};
    private List<Kaart> risti;
    private List<Kaart> ruutu;
    private List<Kaart> poti;
    private List<Kaart> ärtu;

    public Kaardipakk() {
        this.pakk = new ArrayList<Kaart>();
        for (String tugevus: tugevused) {
            this.risti.add(new Kaart(tugevus, mastid[0]));
            this.ruutu.add(new Kaart(tugevus, mastid[1]));
            this.poti.add(new Kaart(tugevus, mastid[2]));
            this.ärtu.add(new Kaart(tugevus, mastid[3]));

            for (char mast: mastid) {
                this.pakk.add(new Kaart(tugevus, mast));
            }
        }
    }

    public Kaardipakk(String väike) {
        this.pakk = new ArrayList<Kaart>();
        for (int i = 0; i < 9; i++) {
            this.risti.add(new Kaart(tugevused[i], mastid[0]));
            this.ruutu.add(new Kaart(tugevused[i], mastid[1]));
            this.poti.add(new Kaart(tugevused[i], mastid[2]));
            this.ärtu.add(new Kaart(tugevused[i], mastid[3]));

            for (char mast: mastid) {
                this.pakk.add(new Kaart(tugevused[i], mast));
            }
        }
    }

    public List<Kaart> getKaardipakk() {
        return pakk;
    }

    public List<Kaart> getRisti() {
        return risti;
    }

    public List<Kaart> getRuutu() {
        return ruutu;
    }

    public List<Kaart> getPoti() {
        return poti;
    }

    public List<Kaart> getÄrtu() {
        return ärtu;
    }
}
