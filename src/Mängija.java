import java.util.List;

public class Mängija {

    private List<Kaart> käesOlevadKaardid;
    private String mänigjaNimi;

    public Mängija(List<Kaart> käesOlevadKaardid, String mänigjaNimi) {
        this.käesOlevadKaardid = käesOlevadKaardid;
        this.mänigjaNimi = mänigjaNimi;
    }

    public List<Kaart> getKäesOlevadKaardid() {
        return käesOlevadKaardid;
    }

    public String getMänigjaNimi() {
        return mänigjaNimi;
    }

    //setJuurdeAntavadKaardid võtab argumendiks List<Kaart> tüüpi listi, sest tihti võetakse laualt üles rohkem kui 1 kaart.
    //Seega laual olevad kaardid võiksid samuti olla List<Kaart> tüüpi.
    public void setJuurdeAnravadKaardid(Kaart juurdeAntavad) {
        this.käesOlevadKaardid.add(juurdeAntavad);
    }

    //setEemaldaKäestKaart on selleks, et peale käimist kaart käest eemaldada.
    public void setEemaldaKäestKaart(Kaart kaart) {
        this.käesOlevadKaardid.remove(kaart);
    }

    @Override
    public String toString() {
        return "Mängija{" +
                "käesOlevadKaardid=" + käesOlevadKaardid +
                ", mänigjaNimi='" + mänigjaNimi + '\'' +
                '}';
    }
}
