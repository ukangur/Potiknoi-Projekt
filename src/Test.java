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

        //Mängija - getKäesOlevadKaardid testimine ja setKäesOlevadKaardid testimine ning kaartide "korjamine" setJuurdeAnravadKaardid meetodiga.
        List<Kaart> ristid = väikePakk.getRisti();
        List<Kaart> potid = väikePakk.getPoti();
        Mängija mängija1 = new Mängija(ristid, "Erik");
        List<Kaart> käes = mängija1.getKäesOlevadKaardid();
        String mängijaNimi = mängija1.getMänigjaNimi();
        System.out.println("Mängijal " + mängijaNimi + " on käes : " + käes);
        mängija1.setJuurdeAnravadKaardid(potid);
        System.out.println("Peale potide lisamist mängijal " + mängijaNimi + " käes: " + käes);

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
