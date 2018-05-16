import java.util.*;

public class Potiknoi {

    private Kaardipakk uusPakk = new Kaardipakk();
    private List<Kaart> risti = uusPakk.getRisti();
    private List<Kaart> ruutu = uusPakk.getRuutu();
    private List<Kaart> poti = uusPakk.getPoti();
    private List<Kaart> ärtu = uusPakk.getÄrtu();
    private List<Kaart> pakk = uusPakk.getKaardipakk();
    private List<Kaart> koopia = new ArrayList<>(pakk); // loob koopia listist pakk nimega koopia
    private List<Kaart> mängija1Kaardid = new ArrayList<>();
    private List<Kaart> mängija2Kaardid = new ArrayList<>();
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
        Laud mäng = new Laud(trump);    //Nüüd on trump valitud ja sellest tehtud koopia, sest kaardipakk saab millalgi otsa
        TrumbiAktiveerimine(trump);

        //Küsin raskusastet
        Küsimus küsimus = new Küsimus();
        String tasemeNimi = küsimus.MillineTase(Arrays.asList("lihtne", "keskmine"));

        int tase;
        if (Objects.equals(tasemeNimi, "lihtne")) {
            tase = 1;
        }
        else {
            tase = 2;
        }

        //Lisasin mängijad koos kaartidega
        Mängija inimene = new Mängija(mängija1Kaardid, küsimus.Nimi()); // Küsin ja saan mängija nime
        Mängija arvuti = new Mängija(mängija2Kaardid, "BOT");

        //Lisan inimesele ja arvutile kaardid
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
                    Kaart millineKaart = küsimus.MillineKaart(inimeseKäes);    //Küsin mängijalt, mis kaardi ta käib.
                    mäng.setKaartLauale(millineKaart);                         //Lisan selle kaardi lauale.
                    System.out.println(inimeseNimi + " käib kaardi: " + millineKaart);
                    inimene.eemaldaKäestKaart(millineKaart);                   //Võtan selle kaardi käest ära.
                    System.out.println(laualOlevadKaardid);                    //Prindin kaardid välja.
                    List<String> erinevadKäigud = Arrays.asList("käi juurde", "anna edasi");  //Küsin, mida mängija edasi tahab teha.
                    String millineKäik = küsimus.MillineKäik(erinevadKäigud);
                    while (Objects.equals(millineKäik, "käi juurde")) {
                        millineKaart = küsimus.MillineKaart(inimeseKäes);
                        if (laualOlevadKaardid.size() > 0 && Objects.equals(millineKaart.getTugevus(), laualOlevadKaardid.get(0).getTugevus())) { //Kontrollin, kas kaart sobib
                            mäng.setKaartLauale(millineKaart);                                                 //ehk kas laual on sama tugevusega (mitte mastiga) kaart
                            System.out.println(inimeseNimi + " käib kaardi: " + millineKaart);
                            inimene.eemaldaKäestKaart(millineKaart);
                            System.out.println(laualOlevadKaardid + "<-- Hetkel mängus olevad kaardid");
                            millineKäik = küsimus.MillineKäik(erinevadKäigud);                                //Küsin, mida mängija edasi tahab teha
                        } else {
                            System.out.println("Seda kaarti ei saa lisada!");  //Kui kaarti ei saa lisada, siis küsin uuesti, mdia mängija soovib teha
                            millineKäik = küsimus.MillineKäik(erinevadKäigud);
                        }
                    }

                    //Siin hakkab arvuti tapma. Kui arvuti korjab, siis kesKäib = 1 ehk inimene käib uuesti.
                    for (Kaart tapetav : laualOlevadKaardid) {   //Vaatan tapmiseks olevad kaardid läbi
                        System.out.println();
                        System.out.println();
                        System.out.println("BOT hakkab tapma");
                        System.out.println();
                        List<Kaart> tappevKaart = arvutiTapa(tapetav, arvutiKäes, tase);   //Sellest meetodist on all juttu
                        if (tappevKaart.size() > 0) {
                            mäng.setTapvadKaardid(tappevKaart);
                            arvuti.eemaldaKäestKaart(tappevKaart.get(0));
                            System.out.println("BOTi käidud viimane kaart oli: " + tappevKaart.get(0));
                            System.out.println("Maha läksid kaardid: " + laualOlevadKaardid + " ja " + mäng.getTapvadKaardid());
                            kesKäib = 0;     //Nagu eelnevalt oli kirjas on see selleks, et märkida, kes hakkab käima järgmisena. 0=arvuti, 1=mängija

                        } else {         //Kui arvutil ei õnenstu tappa, korjab ta laual olevad kaardid üles ning juba tapmiseks kasutatud kaardid samuti
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

                    while (inimeseKäes.size() < 6 && pakk.size() > 0) {   //Kui kaarte on vähem kui 6, tuleb neid juurde võtta
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
                else {   //Siin on justkui nii, et kesKäib=0, sest if lauses oli see võrdne 1-ga
                    System.out.println();
                    System.out.println("BOT käib.");
                    System.out.println();
                    List<String> erinevadKäigud = Arrays.asList("tapa", "korja üles");   //Siin on tulevane küsimus ette valmistatud
                    Kaart käik = Käi(arvutiKäes, tase);     //Meetod "Käi" on all pool lahti seletatud. See on meie AI (artificial intelligence), kes pole just kõige targem.
                    mäng.setKaartLauale(käik);              //Ta saab käia ainult ühe kaardi
                    arvuti.eemaldaKäestKaart(käik);         //Käidud kaart võetakse käest ära
                    System.out.println("Trump on " + trump);  //Meeldetuletuseks prindime mõned asjad välja
                    System.out.println(laualOlevadKaardid + "<-- Hetkel mängus olevad kaardid");
                    for (Kaart tapetav : laualOlevadKaardid) {   //Nüüd peab seda kaarti tapma. Siin on for-tsükkel, mis on valmis tulevikus ka mitut kaarti tapma.
                        String millineKäik = küsimus.MillineKäik(erinevadKäigud);
                            if (Objects.equals(millineKäik, "korja üles")) {   //Kui käsk on "korja üles", siis mängija korjab kaardid üles
                                inimene.võtaÜles(laualOlevadKaardid);
                                inimene.võtaÜles(mäng.getTapvadKaardid());
                                kesKäib = 0;    //for-tsükkel katkeb ning arvuti saab uuesti käia
                                break;
                            }
                            else {
                                List<Kaart> tappevKaart = MängijaTapab(tapetav, inimeseKäes);  //See tapmismeetod näitab mängijale, mis kaartidega saab tappa
                                if (tappevKaart.size() == 0) {                                 //ning annab need valikutena ette
                                    System.out.println();                                      //Kui soovituslikus Listis kaarte pole, tuleb kaardid üles korjata
                                    System.out.println("Sa ei saa midagi käia ning korjad kaardid üles!");
                                    System.out.println("Korjasid üles: " + laualOlevadKaardid);
                                    System.out.println();
                                    inimene.võtaÜles(laualOlevadKaardid);
                                    inimene.võtaÜles(mäng.getTapvadKaardid());
                                    kesKäib = 0;    //for-tsükkel katkeb ning arvuti saab uuesti käia
                                    break;

                                }
                                else {
                                    Kaart millineKaart = küsimus.MillineKaart(tappevKaart);  //Kui aga saab tappa
                                    List<Kaart> mingidKaardid = new ArrayList<>();
                                    mingidKaardid.add(millineKaart);
                                    mäng.setTapvadKaardid(mingidKaardid);                    //Panen kaardid tapvate kaartide alla ja eemaldan need käest
                                    inimene.eemaldaKäestKaart(millineKaart);
                                    kesKäib = 1;
                                    System.out.println();
                                    System.out.println(inimeseNimi + " käib kaardi: " + millineKaart);  //Siin toon välja, mis vahepeal toimus
                                    System.out.println("Sa tapsid kaardi!");
                                    System.out.println("Maha läksid kaardid: " + millineKaart + " ja " + tapetav);
                                    System.out.println();
                                }
                            }

                    }
                    while (inimeseKäes.size() < 6 && pakk.size() > 0) {  //Kaartide juurde võtmine
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
            else { //Mäng saab läbi, kui pakk on tühi ja ühel mängijal on kaardid otsas!
                System.out.println("Mäng on läbi!!! Keegi võitis ja keegi kaotas, aga hasartmängus mehed ei nuta!");
                if (arvutiKäes.size() == 0) {   //Uurin, kes on võitja, vaadates tema käe (kaartide) suurust ehk kaartide arvu käes
                    System.out.println("BOT on võitja!");
                }
                else {  //Kuna kokku on 2 mängijat (arvuti ja inimene), siis else viitabki koheselt inimesele, sest muud võimalust pole
                    System.out.println(inimeseNimi + " on võitja!");
                }
                break;
            }
        }
    }


    // Meetod, millega arvuti kaarte tappa saab. Sisestada tuleb tapetav kaart, käes olevad kaardid ja raskustakse (1 või 2).
    // Tagastatakse üheelemendiline list, mille sees on kaart, millega saab tappa või tühi list kui tappa ei saa.
    // Klass kasutab liste risti, ruutu, poti ärtu
    public List<Kaart> arvutiTapa(Kaart tapetav, List<Kaart> käesOlevadKaardid, int tase) {
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

        // Arvuti valib kõige nõrgema kaardi, millega tappa saab
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

        // Arvuti valib suvalise kaardi, millega tappa saab
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

    // Tagastab listi, mille sees on kõik kaardid, millega mängijal tapetavat kaarti tappa on võimalik
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

    // Meetod, millega arvuti käib alati kõige nõrgema kaardi (tase 2) või suvaline kaart (tase 1)
    // Sisestatakse arvuti käes olevate kaartide list.
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
    //Teen trumbist koopia
    public Kaart TrumbiKoopia(Kaart teineTrump) {
        this.trump = teineTrump;
        return trump;
    }
}
