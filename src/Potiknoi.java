import java.util.*;

public class Potiknoi {

    public static void main(String[] args) {
        mängTööle();
    }

    public static void mängTööle() {

        Scanner sc = new Scanner(System.in);

        Kaardipakk uusPakk = new Kaardipakk();
        List<Kaart> risti = uusPakk.getRisti();
        List<Kaart> ruutu = uusPakk.getRuutu();
        List<Kaart> poti = uusPakk.getPoti();
        List<Kaart> ärtu = uusPakk.getÄrtu();
        List<Kaart> pakk = uusPakk.getKaardipakk();
        Collections.shuffle(pakk);

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

        //Teen mängulaua
        Laud mäng = new Laud(pakk.get(pakk.size()-1));    //Nüüd on trump valitud
        TestTrump tugevused = new TestTrump();
        tugevused.TrumbiAktiveerimine(pakk.get(pakk.size()-1));
        //System.out.println(pakk.get(pakk.size()-1)); //Testin, et näha trumpi
        //System.out.println(tugevused.pakk); //Testimiseks

        //Lisasin mängijad koos kaartidega ning loosin alustaja
        System.out.println("Sisesta enda nimi: "); //<--- Küsin mängija nime
        Random random = new Random();
        int r = random.nextInt(10) + 1;
        //System.out.println(r);

        Mängija inimene = new Mängija(mängija1Kaardid, sc.nextLine(), 0);
        Mängija arvuti = new Mängija(mängija2Kaardid, "BOT", 0);

        if (r%2 == 0) {
            inimene.setKasAlustab(1);
        }
        else{
            arvuti.setKasAlustab(1);
        }

        if (arvuti.getKasAlustab() == 1) {
            for (int i = tugevused.koopia.size()-1; i>=0; i--) {
                Kaart kaart = tugevused.koopia.get(i);
                //System.out.println(kaart);
                if (arvuti.getKäesOlevadKaardid().contains(kaart)) {
                    System.out.println(arvuti.getKäesOlevadKaardid());
                    mäng.setKaartLauale(kaart);
                    arvuti.setEemaldaKäestKaart(kaart);
                    System.out.println(tugevused.koopia);
                    break;
                }
            }
        }
        System.out.println(mäng.getLaualOlevadKaardid());
    }
}
