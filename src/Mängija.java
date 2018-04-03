import java.util.List;

public class Mängija {

    private List<Kaart> käesOlevadKaardid;

    public Mängija(List<Kaart> käesOlevadKaardid) {
        this.käesOlevadKaardid = käesOlevadKaardid;
    }

    public List<Kaart> getKäesOlevadKaardid() {
        return käesOlevadKaardid;
    }

    public void setKäesOlevadKaardid(List<Kaart> käesOlevadKaardid) {
        this.käesOlevadKaardid = käesOlevadKaardid;
    }
}
