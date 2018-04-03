import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {


    public static void main(String[] args) {
        // Kaardipaki testimine
        Kaardipakk uusPakk = new Kaardipakk();
        List<Kaart> pakk = uusPakk.getKaardipakk();
        Collections.shuffle(pakk);
        System.out.println("See on segatud pakk: " + pakk);
        System.out.println("Siin on ainult risti mast: " + uusPakk.getRisti());

        Kaardipakk väikePakk = new Kaardipakk("väike");
        System.out.println("Siin on kaardid A-6: " + väikePakk.getKaardipakk());
        System.out.println("Siin on ärtu A-6: " + väikePakk.getÄrtu());

        //Mängija - getKäesOlevadKaardid testimine ja kaartide "korjamine" setJuurdeAnravadKaardid meetodiga.
        List<Kaart> ristid = väikePakk.getRisti();
        Mängija mängija1 = new Mängija(ristid, "Erik");
        List<Kaart> käes = mängija1.getKäesOlevadKaardid();
        String mängijaNimi = mängija1.getMänigjaNimi();
        System.out.println("Mängijal " + mängijaNimi + " on käes : " + käes);
        //mängija1.setJuurdeAnravadKaardid(mingi kaart);
        //System.out.println("Peale kaardi lisamist mängijal " + mängijaNimi + " käes: " + käes);

        //Laud - trumbi test ja kaartide käimise test ning kaardi käest eemaldamise test
        Laud mäng1 = new Laud(pakk.get(1));
        System.out.println("Trump on: " + mäng1.getTrump());
        mäng1.setKaartLauale(käes.get(1));
        System.out.println(mängijaNimi + " käib kaardi: " + käes.get(1));
        mängija1.setEemaldaKäestKaart(käes.get(1));
        System.out.println("Laual on nüüd kaardid: " + mäng1.getLaualOlevadKaardid());
        System.out.println("Kätte jäid kaardid: " + käes);

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
