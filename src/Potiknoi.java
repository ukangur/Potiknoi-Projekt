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
        Laud mäng = new Laud(pakk.get(pakk.size() - 1));    //Nüüd on trump valitud
        TrumbiAktiveerimine(pakk.get(pakk.size() - 1));
        //System.out.println(pakk.get(pakk.size()-1)); //Testin, et näha trumpi
        //System.out.println(tugevused.pakk); //Testimiseks

        //Lisasin mängijad koos kaartidega ning loosin alustaja
        System.out.println("Sisesta enda nimi: "); //<--- Küsin mängija nime
        Random random = new Random();
        int r = random.nextInt(10) + 1;
        //System.out.println(r);

        Mängija inimene = new Mängija(mängija1Kaardid, sc.nextLine(), 0);
        Mängija arvuti = new Mängija(mängija2Kaardid, "BOT", 0);

        if (r % 2 == 0) {
            inimene.setKasAlustab(1);
        } else {
            arvuti.setKasAlustab(1);
        }

        //Kui arvuti valitakse alustajaks, siis ta käib enda kõige nõrgema kaardi
        if (arvuti.getKasAlustab() == 1) {
            for (int i = koopia.size() - 1; i >= 0; i--) {
                Kaart kaart = koopia.get(i);
                if (arvuti.getKäesOlevadKaardid().contains(kaart)) {
                    mäng.setKaartLauale(kaart);
                    arvuti.setEemaldaKäestKaart(kaart);
                    System.out.println(arvuti.getKäesOlevadKaardid());
                    System.out.println(koopia);
                    break;
                }
            }
        }
        System.out.println(mäng.getLaualOlevadKaardid());
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
