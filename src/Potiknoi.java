import java.util.*;

public class Potiknoi {

    Scanner sc = new Scanner(System.in);
    Kaardipakk uusPakk = new Kaardipakk();
    List<Kaart> risti = uusPakk.getRisti();
    List<Kaart> ruutu = uusPakk.getRuutu();
    List<Kaart> poti = uusPakk.getPoti();
    List<Kaart> ärtu = uusPakk.getÄrtu();
    List<Kaart> pakk = uusPakk.getKaardipakk();
    List<Kaart> koopia = new ArrayList<>(pakk); // loob koopia listist pakk nimega koopia
    List<Kaart> mängija1Kaardid = new ArrayList<>();
    List<Kaart> mängija2Kaardid = new ArrayList<>();

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
        Kaart trump = pakk.get(pakk.size()-1);
        Laud mäng = new Laud(trump);    //Nüüd on trump valitud
        TrumbiAktiveerimine(trump);
        //System.out.println(pakk.get(pakk.size()-1)); //Testin, et näha trumpi
        //System.out.println(tugevused.pakk); //Testimiseks

        //Lisasin mängijad koos kaartidega
        System.out.println("Sisesta enda nimi: "); //<--- Küsin mängija nime
        Mängija inimene = new Mängija(mängija1Kaardid, sc.nextLine());
        Mängija arvuti = new Mängija(mängija2Kaardid, "BOT");

        Küsimus küsimus = new Küsimus();
        List<Kaart> inimeseKäes = inimene.getKäesOlevadKaardid();
        List<Kaart> arvutiKäes = arvuti.getKäesOlevadKaardid();
        String inimeseNimi = inimene.getMänigjaNimi();
        List<Kaart> laualOlevadKaardid = mäng.getLaualOlevadKaardid();
        int kesKäib = 1; //Kui on 1, käib inimene, kui 0, siis arvuti

        System.out.println(inimeseKäes);
        System.out.println(arvutiKäes);
        System.out.println(trump);

        while (true) {
            if (pakk.size() > 0 && (inimeseKäes.size() > 0 && arvutiKäes.size() > 0)) {

                //Kui on inimese kord käia, siis kesKäib == 1
                if (kesKäib == 1) {
                    Kaart millineKaart = küsimus.MillineKaart(inimeseKäes);
                    mäng.setKaartLauale(millineKaart);
                    System.out.println(inimeseNimi + " käib kaardi: " + millineKaart);
                    inimene.setEemaldaKäestKaart(millineKaart);
                    System.out.println(laualOlevadKaardid);
                    List<String> erinevadKäigud = Arrays.asList("käi juurde", "anna edasi");
                    String millineKäik = küsimus.MillineKäik(erinevadKäigud);
                    while (millineKäik == "käi juurde") {
                        millineKaart = küsimus.MillineKaart(inimeseKäes);
                        mäng.setKaartLauale(millineKaart);
                        System.out.println(inimeseNimi + " käib kaardi: " + millineKaart);
                        inimene.setEemaldaKäestKaart(millineKaart);
                        System.out.println(laualOlevadKaardid);
                        millineKäik = küsimus.MillineKäik(erinevadKäigud);
                    }

                    //Siin hakkab arvuti tapma. Kui arvuti korjab, siis kesKäib = 1 ehk inimene käib uuesti.
                    for (Kaart tapetav : laualOlevadKaardid) {
                        System.out.println(tapetav);
                        List<Kaart> tappevKaart = Tapa(tapetav, arvutiKäes);
                        if (tappevKaart.size() > 0) {
                            mäng.setTapvadKaardid(tappevKaart);
                            arvuti.setEemaldaKäestKaart(tappevKaart.get(0));
                            kesKäib = 0;
                            //System.out.println(arvutiKäes);
                        }
                        else {
                            kesKäib = 1;
                        }
                    }
                    if (kesKäib == 1) { //Ehk arvuti korjas, sest uuesti on inimese kord. Seega peab kaardid üles võtma.
                        arvuti.võtaÜles(mäng.getLaualOlevadKaardid());
                        arvuti.võtaÜles(mäng.getTapvadKaardid());
                    }

                    while (inimeseKäes.size()<6) {
                        inimene.võtaKaarteJuurde(pakk);
                    }
                    while (arvutiKäes.size()<6) {
                        arvuti.võtaKaarteJuurde(pakk);
                    }
                    System.out.println(inimeseKäes);
                    System.out.println(arvutiKäes);
                    System.out.println("Tihi on mängitud!");
                    //Teen laua ja tapvad kaardid tühjaks.
                    mäng.teeTapvadTühjaks();
                    mäng.teeLaudTühjaks();
                    System.out.println(mäng.getTapvadKaardid());
                    System.out.println(mäng.getLaualOlevadKaardid());
                    }
                }

            else {
                break;
            }
        }
    }

    // Meetod, millega arvuti kaarte tappa saab. Sisestada tuleb tapetav kaart ja käes olevad kaardid.
    // Tagastatakse üheelemendiline list, mille sees on kaart, millega saab tappa või tühi list kui tappa ei saa.
    // Klass kasutab liste risti, ruutu, poti ärtu
    public List<Kaart> Tapa(Kaart tapetav, List<Kaart> käesOlevadKaardid) {
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
                if (käesOlevadKaardid.contains(kasutatavPakk.get(i))) {
                    vastus.add(kasutatavPakk.get(i));
                    return vastus;
                }
            }
        }
        return vastus;
    }

    // Meetod, millega arvuti käib alati kõige nõrgema kaardi. Sisestatakse arvuti käes olevate kaartide list.
    // Tagastatakse kaart, mille arvuti käib
    // Meetod kasutab tugevuslisti nimega koopia
    public Kaart Käi(List<Kaart> käesOlevadKaardid) {
        for(int i = koopia.size() - 1; i >= 0; i--) {
            if (käesOlevadKaardid.contains(koopia.get(i))) {
                return koopia.get(i);
            }
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
}
