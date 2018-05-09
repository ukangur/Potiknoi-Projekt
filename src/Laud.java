import java.util.*;

public class Laud {

    private Kaart trump;
    private List<Kaart> laualOlevadKaardid;
    private List<Kaart> tapvadKaardid;

    public Laud(Kaart trump) {
        this.trump = trump;
        this.laualOlevadKaardid = new ArrayList<>();
        this.tapvadKaardid = new ArrayList<>();
    }

    public List<Kaart> getLaualOlevadKaardid() {
        return laualOlevadKaardid;
    }

    public Kaart getTrump() {
        return trump;
    }

    public void teeLaudTühjaks() {
        this.laualOlevadKaardid = new ArrayList<>();
    }

    public void teeTapvadTühjaks() {
        this.tapvadKaardid = new ArrayList<>();
    }

    public void setKaartLauale(Kaart käidudKaart) {
        this.laualOlevadKaardid.add(käidudKaart);
    }

    public void setTapvadKaardid(List<Kaart> käidavKaart) {
        this.tapvadKaardid.addAll(käidavKaart);
    }

    public List<Kaart> getTapvadKaardid() {
        return tapvadKaardid;
    }

    @Override
    public String toString() {
        return "Laud{" +
                "laualOlevadKaardid=" + laualOlevadKaardid +
                '}';
    }
}
