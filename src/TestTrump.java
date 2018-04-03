import java.util.ArrayList;
import java.util.List;

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

    public void prindiMastid() {
        System.out.println("Risti pakk: " + risti);
        System.out.println("Ruutu pakk: " + ruutu);
        System.out.println("Poti pakk: " + poti);
        System.out.println("Ärtu pakk: " + ärtu);

        System.out.println("Suur tugevuslist on: " + koopia);
    }


    // Hakkan siia tapmismeetodit tegema
/*    public void tapa(Kaart tapetav, List<Kaart> käesOlevadKaardid) {
        List<Kaart> vastus = new ArrayList<>();
        List<Kaart> kasutatav = new ArrayList<>();

    }*/

}
