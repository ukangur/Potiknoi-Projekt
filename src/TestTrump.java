import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestTrump {

    Kaardipakk uusPakk = new Kaardipakk();
    List<Kaart> pakk = uusPakk.getKaardipakk();
    List<Kaart> risti = uusPakk.getRisti();
    List<Kaart> ruutu = uusPakk.getRuutu();
    List<Kaart> poti = uusPakk.getPoti();
    List<Kaart> ärtu = uusPakk.getÄrtu();

    List<Kaart> koopia = new ArrayList<>(pakk); // loob koopia listist pakk nimega koopia

    // Anna parameetriks trump-kaart ja meetod lisab trumbi masti kaardid ülejäänud mastide listidesse
    // NB! pakk, koopia, risti, ruutu, poti ärtu listid peavad varasemalt olemas olema!
    // NB! meetod SuurTugevuslist on vaja, sest TrumbiAktiveerimine kasutab seda!
    public void TrumbiAktiveerimine(Kaart trump) {
        List<Kaart> lisatav = new ArrayList<>();
        char mast = trump.getMast();
        switch (mast) {
            case '♣':
                lisatav.addAll(risti);
                SuurTugevuslist('♣');
                koopia.addAll(0, risti);
                break;
            case '♦':
                lisatav.addAll(ruutu);
                SuurTugevuslist('♦');
                koopia.addAll(0, ruutu);
                break;
            case '♠':
                lisatav.addAll(poti);
                SuurTugevuslist('♠');
                koopia.addAll(0, poti);
                break;
            case '♥':
                lisatav.addAll(ärtu);
                SuurTugevuslist('♥');
                koopia.addAll(0, ärtu);
                break;
        }
        if (!(mast == '♣')) {
            risti.addAll(0, lisatav);
        }
        if (!(mast == '♦')) {
            ruutu.addAll(0, lisatav);
        }
        if (!(mast == '♠')) {
            poti.addAll(0, lisatav);
        }
        if (!(mast == '♥')) {
            ärtu.addAll(0, lisatav);
        }
    }

    private void SuurTugevuslist(char mast) {
        for (int i = 0; i < koopia.size(); i++) {
            if (koopia.get(i).getMast() == mast) {
                koopia.remove(i);
            }
        }
    }

    public void prindiMastid() {
        System.out.println("Risti pakk: " + risti);
        System.out.println("Ruutu pakk: " + ruutu);
        System.out.println("Poti pakk: " + poti);
        System.out.println("Ärtu pakk: " + ärtu);

        System.out.println("Suur tugevuslist on: " + koopia);
    }

    // Meetod, millega arvuti kaarte tappa saab. Sisestada tuleb tapetav kaart ja käes olevad kaardid.
    // Tagastatakse üheelemendiline list, mille sees on kaart, millega saab tappa või tühi list kui tappa ei saa.
    // Klass kasutab liste risti, ruutu, poti ärtu
    public List<Kaart> Tapa(Kaart tapetav, List<Kaart> käesOlevadKaardid, int tase) {
        List<Kaart> võimalikudVastused = new ArrayList<>();
        List<Kaart> vastus = new ArrayList<>();
        List<Kaart> kasutatavPakk = new ArrayList<>();
        char mast = tapetav.getMast();
        switch (mast) {
            case '♣':
                kasutatavPakk.addAll(risti);
                break;
            case '♦':
                kasutatavPakk.addAll(ruutu);
                break;
            case '♠':
                kasutatavPakk.addAll(poti);
                break;
            case '♥':
                kasutatavPakk.addAll(ärtu);
                break;
        }

        if (tase == 2) {
            for(int i = kasutatavPakk.size() - 1; i >= 0; i--) {
                if (i < kasutatavPakk.indexOf(tapetav)) {
                    if (käesOlevadKaardid.contains(kasutatavPakk.get(i))) {
                        vastus.add(kasutatavPakk.get(i));
                        return vastus;
                    }
                }
            }
            return vastus;
        }

        else if (tase == 1) {
            for(int i = kasutatavPakk.size() - 1; i >= 0; i--) {
                if (i < kasutatavPakk.indexOf(tapetav)) {
                    if (käesOlevadKaardid.contains(kasutatavPakk.get(i))) {
                        võimalikudVastused.add(kasutatavPakk.get(i));
                    }
                }
            }

            if (võimalikudVastused.size() == 0) {
                return vastus;
            }

            Random rand = new Random();
            int suvalineArv = rand.nextInt(võimalikudVastused.size());
            vastus.add(võimalikudVastused.get(suvalineArv));

            return vastus;
        }
        return vastus;
    }

    // Meetod, millega arvuti käib alati kõige nõrgema kaardi. Sisestatakse arvuti käes olevate kaartide list.
    // Tagastatakse kaart, mille arvuti käib
    // Meetod kasutab tugevuslisti nimega koopia
    public Kaart Käi(List<Kaart> käesOlevadKaardid, int tase) {
        if (tase == 2) {
            for(int i = koopia.size() - 1; i >= 0; i--) {
                if (käesOlevadKaardid.contains(koopia.get(i))) {
                    return koopia.get(i);
                }
            }
        }
        else if (tase == 1) {
            Random rand = new Random();
            int suvalineArv = rand.nextInt(käesOlevadKaardid.size());

            return käesOlevadKaardid.get(suvalineArv);
        }
        return käesOlevadKaardid.get(0); // Seda return'i kunagi ei kasutata, aga selle peab siia panema, muidu pole võimalik meetodit kirja panna
    }

    public List<Kaart> MängijaTapab(Kaart tapetav, List<Kaart> MängijakäesOlevadKaardid) {
        List<Kaart> vastus = new ArrayList<>();
        List<Kaart> kasutatavPakk = new ArrayList<>();
        char mast = tapetav.getMast();
        switch (mast) {
            case '♣':
                kasutatavPakk.addAll(risti);
                break;
            case '♦':
                kasutatavPakk.addAll(ruutu);
                break;
            case '♠':
                kasutatavPakk.addAll(poti);
                break;
            case '♥':
                kasutatavPakk.addAll(ärtu);
                break;
        }

        for(int i = kasutatavPakk.size() - 1; i >= 0; i--) {
            if (i < kasutatavPakk.indexOf(tapetav)) {
                if (MängijakäesOlevadKaardid.contains(kasutatavPakk.get(i))) {
                    vastus.add(kasutatavPakk.get(i));
                }
            }
        }
        return vastus;
    }

    // Arvuti tapab suvalise kaardiga, millega on võimalik tappa
    // Meetod tagastab üheelemendilise listi, mille sees on kaart, millega arvuti tahab tappa
    public List<Kaart> TapaRandom(Kaart tapetav, List<Kaart> käesOlevadKaardid) {
        List<Kaart> võimalikudVastused = new ArrayList<>();
        List<Kaart> vastus = new ArrayList<>();
        List<Kaart> kasutatavPakk = new ArrayList<>();
        char mast = tapetav.getMast();
        switch (mast) {
            case '♣':
                kasutatavPakk.addAll(risti);
                break;
            case '♦':
                kasutatavPakk.addAll(ruutu);
                break;
            case '♠':
                kasutatavPakk.addAll(poti);
                break;
            case '♥':
                kasutatavPakk.addAll(ärtu);
                break;
        }

        for(int i = kasutatavPakk.size() - 1; i >= 0; i--) {
            if (i < kasutatavPakk.indexOf(tapetav)) {
                if (käesOlevadKaardid.contains(kasutatavPakk.get(i))) {
                    võimalikudVastused.add(kasutatavPakk.get(i));
                }
            }
        }

        if (võimalikudVastused.size() == 0) {
            return vastus;
        }

        Random rand = new Random();
        int suvalineArv = rand.nextInt(võimalikudVastused.size());
        vastus.add(võimalikudVastused.get(suvalineArv));

        return vastus;
    }

    // Arvuti käib suvalise kaardi.
    // Meetod tagastab suvalise kaardi mängija käes olevatest kaartidest
    public Kaart KäiRandom(List<Kaart> käesOlevadKaardid) {
        Random rand = new Random();
        int suvalineArv = rand.nextInt(käesOlevadKaardid.size());

        return käesOlevadKaardid.get(suvalineArv);
    }


}
