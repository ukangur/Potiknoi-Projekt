import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {


    public static void main(String[] args) {
        // Kaardipaki testimine
        Kaardipakk uusPakk = new Kaardipakk();
        List<Kaart> pakk = uusPakk.getKaardipakk();
        System.out.println(pakk);
        System.out.println(uusPakk.getRisti());

        Kaardipakk väikePakk = new Kaardipakk("väike");
        System.out.println(väikePakk.getKaardipakk());
        System.out.println(väikePakk.getÄrtu());

        //Mängija - getKäesOlevadKaardid testimine ja setKäesOlevadKaardid testimine
        List<Kaart> ristid = väikePakk.getRisti();
        List<Kaart> potid = väikePakk.getPoti();
        Mängija mängija1 = new Mängija(ristid);
        System.out.println("Mängija nr1 käes on: " + mängija1.getKäesOlevadKaardid());
        mängija1.setKäesOlevadKaardid(potid); //Siin kirjutad set meetod lihtsalt käes olevad kaardid üle.
        System.out.println("Peale ristide lisamist on mängijal nr1 käes: " + mängija1.getKäesOlevadKaardid());

        //Küsimus - MillineKaart testimine
        Küsimus küsimus = new Küsimus();
        List<Kaart> ärtud = väikePakk.getÄrtu();
        Kaart millineKaart = küsimus.MillineKaart(ärtud);
        System.out.println("Valitud kaart on " + millineKaart.toString());

        //Küsimus - MillineKäik testimine
        List<String> erinevadKäigud = Arrays.asList("tapa", "saada");
        String millineKäik = küsimus.MillineKäik(erinevadKäigud);
        System.out.println("Valitud käik on " + millineKäik);



    }

}
