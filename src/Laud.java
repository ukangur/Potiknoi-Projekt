import java.util.*;

public class Laud {

    private Kaart trump;
    private List<Kaart> laualOlevadKaardid;

    public Laud(Kaart trump) {
        this.trump = trump;
        this.laualOlevadKaardid = new ArrayList<Kaart>();
    }

    public List<Kaart> getLaualOlevadKaardid() {
        return laualOlevadKaardid;
    }

    public Kaart getTrump() {
        return trump;
    }

    public void setKaartLauale(Kaart käidudKaart) {
        this.laualOlevadKaardid.add(käidudKaart);
    }

    @Override
    public String toString() {
        return "Laud{" +
                "laualOlevadKaardid=" + laualOlevadKaardid +
                '}';
    }
}
