import java.util.List;

public class Mängija {

    private List<Kaart> käesOlevadKaardid;
    private String mänigjaNimi;
    private int kasAlustab=0;

    public Mängija(List<Kaart> käesOlevadKaardid, String mänigjaNimi, int kasAlustab) {
        this.käesOlevadKaardid = käesOlevadKaardid;
        this.mänigjaNimi = mänigjaNimi;
        this.kasAlustab = kasAlustab;
    }

    public int getKasAlustab() {
        return kasAlustab;
    }

    public void setKasAlustab(int kasAlustab) {
        this.kasAlustab = kasAlustab;
    }

    public List<Kaart> getKäesOlevadKaardid() {
        return käesOlevadKaardid;
    }

    public String getMänigjaNimi() {
        return mänigjaNimi;
    }

    //setJuurdeAntavadKaardid võtab argumendiks List<Kaart> tüüpi listi, sest tihti võetakse laualt üles rohkem kui 1 kaart.
    //Seega laual olevad kaardid võiksid samuti olla List<Kaart> tüüpi.
    public void võtaÜles(List<Kaart> juurdeAntavad) {
        this.käesOlevadKaardid.addAll(juurdeAntavad);
    }

    //setEemaldaKäestKaart on selleks, et peale käimist kaart käest eemaldada.
    public void setEemaldaKäestKaart(Kaart kaart) {
        this.käesOlevadKaardid.remove(kaart);
    }

    //Võta kaarte juurde. Võtab pakist kaarte ja eemaldab pakist võetud kaardi.
    //Seda võiks kasutada while-tsükliga, kuni käes on vähem kui 6 kaarti, tee seda...
    public void võtaKaarteJuurde(List<Kaart> pakk) {
        käesOlevadKaardid.add(pakk.get(0));
        pakk.remove(pakk.get(0));
    }

    @Override
    public String toString() {
        return "Mängija{" +
                "käesOlevadKaardid=" + käesOlevadKaardid +
                ", mänigjaNimi='" + mänigjaNimi + '\'' +
                '}';
    }
}
