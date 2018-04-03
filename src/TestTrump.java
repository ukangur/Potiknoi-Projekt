import java.util.ArrayList;
import java.util.List;

public class TestTrump {

    Kaardipakk uusPakk = new Kaardipakk();
    List<Kaart> pakk = uusPakk.getKaardipakk();
    List<Kaart> risti = uusPakk.getRisti();
    List<Kaart> ruutu = uusPakk.getRuutu();
    List<Kaart> poti = uusPakk.getPoti();
    List<Kaart> ärtu = uusPakk.getÄrtu();

    // Anna parameetriks trump-kaart ja meetod lisab trumbi masti kaardid ülejäänud mastide listidesse
    // NB! pakk, risti, ruutu, poti ärtu listid peavad varasemalt olemas olema!
    public void TrumbiAktiveerimine(Kaart trump) {
        List<Kaart> lisatav = new ArrayList<>();
        char mast = trump.getMast();
        if (mast == '♣') {
            lisatav.addAll(risti);
        }
        else if (mast == '♦') {
            lisatav.addAll(ruutu);
        }
        else if (mast == '♠') {
            lisatav.addAll(poti);
        }
        else if (mast == '♥') {
            lisatav.addAll(ärtu);
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

    public void prindiMastid() {
        System.out.println("Risti pakk: " + risti);
        System.out.println("Ruutu pakk: " + ruutu);
        System.out.println("Poti pakk: " + poti);
        System.out.println("Ärtu pakk: " + ärtu);
    }

}
