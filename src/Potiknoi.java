import java.util.*;

public class Potiknoi {

    Scanner sc = new Scanner(System.in);
    Kaardipakk uusPakk = new Kaardipakk("väike");
    List<Kaart> risti = uusPakk.getRisti();
    List<Kaart> ruutu = uusPakk.getRuutu();
    List<Kaart> poti = uusPakk.getPoti();
    List<Kaart> ärtu = uusPakk.getÄrtu();
    List<Kaart> pakk = uusPakk.getKaardipakk();
    List<Kaart> koopia = new ArrayList<>(pakk); // loob koopia listist pakk nimega koopia
    List<Kaart> mängija1Kaardid = new ArrayList<>();
    List<Kaart> mängija2Kaardid = new ArrayList<>();
    private Kaart trump;

    public void mängTööle() {
        Collections.shuffle(pakk);

        //Jagan kaardid Listidesse
        while (true) {
            if (mängija2Kaardid.size() != 6) {
                mängija1Kaardid.add(pakk.get(0));
                pakk.remove(0);
                mängija2Kaardid.add(pakk.get(0));
                pakk.remove(0);
            } else {
                break;
            }
        }

        //Teen mängulaua
        Kaart trump = TrumbiKoopia(pakk.get(pakk.size()-1));
        Laud mäng = new Laud(trump);    //Nüüd on trump valitud
        TrumbiAktiveerimine(trump);
        //System.out.println(pakk.get(pakk.size()-1)); //Testin, et näha trumpi
        //System.out.println(tugevused.pakk); //Testimiseks

        //Küsin raskusastet
        Küsimus küsimus = new Küsimus();
        String tasemeNimi = küsimus.MillineTase(Arrays.asList("lihtne", "keskmine"));

        int tase = 0;
        if (tasemeNimi == "lihtne") {
            tase = 1;
        }
        else {
            tase = 2;
        }


        //Lisasin mängijad koos kaartidega
        System.out.println("Sisesta enda nimi: "); //<--- Küsin mängija nime
        Mängija inimene = new Mängija(mängija1Kaardid, sc.nextLine());
        Mängija arvuti = new Mängija(mängija2Kaardid, "BOT");


        List<Kaart> inimeseKäes = inimene.getKäesOlevadKaardid();
        List<Kaart> arvutiKäes = arvuti.getKäesOlevadKaardid();
        String inimeseNimi = inimene.getMänigjaNimi();
        List<Kaart> laualOlevadKaardid = mäng.getLaualOlevadKaardid();
        int kesKäib = 1; //Kui on 1, käib inimene, kui 0, siis arvuti

        System.out.println(inimeseKäes + " - mängija " + inimeseNimi + " käsi.");
        System.out.println(trump + " -TRUMP.");
        System.out.println();
        System.out.println();

        while (true) {
            if (pakk.size() > 0 || (inimeseKäes.size() > 0 && arvutiKäes.size() > 0)) {

                //Kui on inimese kord käia, siis kesKäib == 1
                if (kesKäib == 1) {
                    System.out.println("Mängukord on mängija " + inimeseNimi + " käes.");
                    System.out.println("Trump on " + trump);
                    Kaart millineKaart = küsimus.MillineKaart(inimeseKäes);
                    mäng.setKaartLauale(millineKaart);
                    System.out.println(inimeseNimi + " käib kaardi: " + millineKaart);
                    inimene.eemaldaKäestKaart(millineKaart);
                    System.out.println(laualOlevadKaardid);
                    List<String> erinevadKäigud = Arrays.asList("käi juurde", "anna edasi");
                    String millineKäik = küsimus.MillineKäik(erinevadKäigud);
                    while (millineKäik == "käi juurde") {
                        millineKaart = küsimus.MillineKaart(inimeseKäes);
                        if (laualOlevadKaardid.size() > 0 && millineKaart.getTugevus() == laualOlevadKaardid.get(0).getTugevus()) {
                            mäng.setKaartLauale(millineKaart);
                            System.out.println(inimeseNimi + " käib kaardi: " + millineKaart);
                            inimene.eemaldaKäestKaart(millineKaart);
                            System.out.println(laualOlevadKaardid + "<-- Hetkel mängus olevad kaardid");
                            millineKäik = küsimus.MillineKäik(erinevadKäigud);
                        } else {
                            System.out.println("Seda kaarti ei saa lisada!");
                            millineKäik = küsimus.MillineKäik(erinevadKäigud);
                        }
                    }

                    //Siin hakkab arvuti tapma. Kui arvuti korjab, siis kesKäib = 1 ehk inimene käib uuesti.
                    for (Kaart tapetav : laualOlevadKaardid) {
                        System.out.println();
                        System.out.println();
                        System.out.println("BOT hakkab tapma");
                        System.out.println();
                        List<Kaart> tappevKaart = arvutiTapa(tapetav, arvutiKäes, tase);
                        if (tappevKaart.size() > 0) {
                            mäng.setTapvadKaardid(tappevKaart);
                            arvuti.eemaldaKäestKaart(tappevKaart.get(0));
                            System.out.println("BOTi käidud viimane kaart oli: " + tappevKaart.get(0));
                            System.out.println("Maha läksid kaardid: " + laualOlevadKaardid + " ja " + mäng.getTapvadKaardid());
                            kesKäib = 0;

                        } else {
                            arvuti.võtaÜles(mäng.getLaualOlevadKaardid());
                            arvuti.võtaÜles(mäng.getTapvadKaardid());
                            kesKäib = 1;
                            System.out.println("BOT korjab kaardid üles.");
                            System.out.println("Üles korjati: " + laualOlevadKaardid);
                            System.out.println();
                            System.out.println("-------------------------");
                            System.out.println();
                        }
                    }

                    while (inimeseKäes.size() < 6 && pakk.size() > 0) {
                        inimene.võtaKaarteJuurde(pakk);
                    }
                    while (arvutiKäes.size() < 6 && pakk.size() > 0) {
                        arvuti.võtaKaarteJuurde(pakk);
                    }
                    System.out.println("Tihi on mängitud!");
                    System.out.println();
                    //Teen laua ja tapvad kaardid tühjaks.
                    laualOlevadKaardid.clear();
                    mäng.teeTapvadTühjaks();

                }
                else {
                    System.out.println();
                    System.out.println("BOT käib.");
                    System.out.println();
                    List<String> erinevadKäigud = Arrays.asList("tapa", "korja üles");
                    Kaart käik = Käi(arvutiKäes, tase);
                    mäng.setKaartLauale(käik);
                    arvuti.eemaldaKäestKaart(käik);
                    System.out.println("Trump on " + trump);
                    System.out.println(laualOlevadKaardid + "<-- Hetkel mängus olevad kaardid");
                    for (Kaart tapetav : laualOlevadKaardid) {
                        String millineKäik = küsimus.MillineKäik(erinevadKäigud);
                            if (millineKäik == "korja üles") {
                                inimene.võtaÜles(laualOlevadKaardid);
                                inimene.võtaÜles(mäng.getTapvadKaardid());
                                kesKäib = 0;
                                break;
                            }
                            else {
                                List<Kaart> tappevKaart = MängijaTapab(tapetav, inimeseKäes);
                                if (tappevKaart.size() == 0) {
                                    System.out.println();
                                    System.out.println("Sa ei saa midagi käia ning korjad kaardid üles!");
                                    System.out.println("Korjasid üles: " + laualOlevadKaardid);
                                    System.out.println();
                                    inimene.võtaÜles(laualOlevadKaardid);
                                    inimene.võtaÜles(mäng.getTapvadKaardid());
                                    kesKäib = 0;
                                    break;

                                }
                                else {
                                    Kaart millineKaart = küsimus.MillineKaart(tappevKaart);
                                    List<Kaart> mingidKaardid = new ArrayList<>();
                                    mingidKaardid.add(millineKaart);
                                    mäng.setTapvadKaardid(mingidKaardid);
                                    inimene.eemaldaKäestKaart(millineKaart);
                                    kesKäib = 1;
                                    System.out.println();
                                    System.out.println(inimeseNimi + " käib kaardi: " + millineKaart);
                                    System.out.println("Sa tapsid kaardi!");
                                    System.out.println("Maha läksid kaardid: " + millineKaart + " ja " + tapetav);
                                    System.out.println();
                                }
                            }

                    }
                    while (inimeseKäes.size() < 6 && pakk.size() > 0) {
                        inimene.võtaKaarteJuurde(pakk);
                    }
                    while (arvutiKäes.size() < 6 && pakk.size() > 0) {
                        arvuti.võtaKaarteJuurde(pakk);
                    }

                    System.out.println("Tihi on mängitud!");
                    System.out.println();
                    //Teen laua ja tapvad kaardid tühjaks.
                    laualOlevadKaardid.clear();
                    mäng.teeTapvadTühjaks();
                }
            }
            else {
                System.out.println("Mäng on läbi!!! Keegi võitis ja keegi kaotas, aga hasartmängus mehed ei nuta!");
                break;
            }
        }

    }

    // Meetod, millega arvuti kaarte tappa saab. Sisestada tuleb tapetav kaart ja käes olevad kaardid.
    // Tagastatakse üheelemendiline list, mille sees on kaart, millega saab tappa või tühi list kui tappa ei saa.
    // Klass kasutab liste risti, ruutu, poti ärtu
    // Meetod, millega arvuti kaarte tappa saab. Sisestada tuleb tapetav kaart ja käes olevad kaardid.
    // Tagastatakse üheelemendiline list, mille sees on kaart, millega saab tappa või tühi list kui tappa ei saa.
    // Klass kasutab liste risti, ruutu, poti ärtu
    public List<Kaart> arvutiTapa(Kaart tapetav, List<Kaart> käesOlevadKaardid, int tase) {
        List<Kaart> võimalikudVastused = new ArrayList<>();
        List<Kaart> vastus = new ArrayList<>();
        List<Kaart> kasutatavPakk = new ArrayList<>();
        char mast = tapetav.getMast();
        if (mast == '♣') {
            kasutatavPakk.addAll(risti);
        }
        else if (mast == '♦') {
            kasutatavPakk.addAll(ruutu);
        }
        else if (mast == '♠') {
            kasutatavPakk.addAll(poti);
        }
        else if (mast == '♥') {
            kasutatavPakk.addAll(ärtu);
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

    public List<Kaart> MängijaTapab(Kaart tapetav, List<Kaart> MängijakäesOlevadKaardid) {
        List<Kaart> vastus = new ArrayList<>();
        List<Kaart> kasutatavPakk = new ArrayList<>();
        char mast = tapetav.getMast();
        if (mast == '♣') {
            kasutatavPakk.addAll(risti);
        }
        else if (mast == '♦') {
            kasutatavPakk.addAll(ruutu);
        }
        else if (mast == '♠') {
            kasutatavPakk.addAll(poti);
        }
        else if (mast == '♥') {
            kasutatavPakk.addAll(ärtu);
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

    public void TrumbiAktiveerimine(Kaart trump) {
        List<Kaart> lisatav = new ArrayList<>();
        char mast = trump.getMast();
        if (mast == '♣') {
            lisatav.addAll(risti);
            SuurTugevuslist('♣');
            koopia.addAll(0, risti);
        }
        else if (mast == '♦') {
            lisatav.addAll(ruutu);
            SuurTugevuslist('♦');
            koopia.addAll(0, ruutu);
        }
        else if (mast == '♠') {
            lisatav.addAll(poti);
            SuurTugevuslist('♠');
            koopia.addAll(0, poti);
        }
        else if (mast == '♥') {
            lisatav.addAll(ärtu);
            SuurTugevuslist('♥');
            koopia.addAll(0, ärtu);
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

    public Kaart TrumbiKoopia(Kaart teineTrump) {
        this.trump = teineTrump;
        return trump;
    }
}
