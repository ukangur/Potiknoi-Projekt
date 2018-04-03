import java.util.List;

public class Testklass {

    public static void main(String[] args) {
        Kaardipakk uusPakk = new Kaardipakk();
        List<Kaart> pakk = uusPakk.getKaardipakk();

        TestTrump testTrump = new TestTrump();

        Kaart trump = pakk.get(pakk.size()-1);
        System.out.println("Trump on: " + trump);

        testTrump.TrumbiAktiveerimine(trump);
        testTrump.prindiMastid();

    }
}
