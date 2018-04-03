import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestKaks {
    public static void main(String[] args) {

        //Koostan paki ja segan ning trükin välja.
        Kaardipakk uusPakk = new Kaardipakk();
        List<Kaart> pakk = uusPakk.getKaardipakk();
        Collections.shuffle(pakk);
        System.out.println("See on segatud pakk: " + pakk);
        System.out.println("Potid: " + uusPakk.getPoti());
        System.out.println("Ärtud: " + uusPakk.getÄrtu());
        System.out.println("Ristid: " + uusPakk.getRisti());
        System.out.println("Ruutud: " + uusPakk.getRuutu());

        //Koostan kahe mängija kaartide listid, et need Mängija konstruktorile ette anda.
        List<Kaart> mängija1Kaardid= new ArrayList<>();
        List<Kaart> mängija2Kaardid = new ArrayList<>();

        //Jagan kaardid Listidesse
        while (true) {
            if (mängija2Kaardid.size() != 6) {
                mängija1Kaardid.add(pakk.get(0));
                pakk.remove(0);
                mängija2Kaardid.add(pakk.get(0));
                pakk.remove(0);
            }
            else {
                break;
            }
        }

        //Prindin vahelduseks välja, millised on kaardid käes ja pakis.
        System.out.println("See on segatud pakk peale kaartide jagamist: " + pakk);
        System.out.println("Mängija 1 kaardid: " + mängija1Kaardid);
        System.out.println("Mängija 2 kaardid: " + mängija2Kaardid);

        //Koostan mängulaua. Trump on esimene kaart, mis peale jagamist tõmamtakse ja eemaldatakse pakist.
        Laud testlaud = new Laud(pakk.get(0));
        pakk.remove(0);

        //Loon esimese ja ainukese mängija testiks.
        Mängija testmängija = new Mängija(mängija1Kaardid, "Testimees", 1);
        //Kontrolin, kas on korras sellega.
        System.out.println(testmängija);

        //Kirjutan välja trumbi
        System.out.println("Trump on: " + testlaud.getTrump());

        //Käin esimese kaardi
        String mängijaNimi = testmängija.getMänigjaNimi();
        List<Kaart> käes = testmängija.getKäesOlevadKaardid();

        Küsimus küsimus = new Küsimus();
        Kaart millineKaart = küsimus.MillineKaart(käes);
        testlaud.setKaartLauale(millineKaart);
        System.out.println(mängijaNimi + " käib kaardi: " + millineKaart);
        testmängija.setEemaldaKäestKaart(millineKaart);
        System.out.println("Laual on nüüd kaardid: " + testlaud.getLaualOlevadKaardid());
        System.out.println("Kätte jäid kaardid: " + käes);

        //Käik
        List<String> erinevadKäigud = Arrays.asList("tapa", "saada");
        String millineKäik = küsimus.MillineKäik(erinevadKäigud);
        System.out.println("Valitud käik on " + millineKäik);

        millineKaart = küsimus.MillineKaart(käes);
        System.out.println(mängijaNimi + " käib kaardi: " + millineKaart);
        testmängija.setEemaldaKäestKaart(millineKaart);
        testlaud.setKaartLauale(millineKaart);
        System.out.println("Laual on nüüd kaardid: " + testlaud.getLaualOlevadKaardid());
        System.out.println("Kätte jäid kaardid: " + käes);

        //Siin võtan kaarte juurde
        while (käes.size() < 6) {
            testmängija.võtaKaarteJuurde(pakk);
        }
        System.out.println("Peale juurde võtmist on käsi: " + käes);
    }
}
