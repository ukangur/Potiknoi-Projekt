import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Testklass {

    public static void main(String[] args) {
        Kaardipakk uusPakk = new Kaardipakk();
        List<Kaart> pakk = uusPakk.getKaardipakk();

        TestTrump testTrump = new TestTrump();

        Kaart trump = pakk.get(pakk.size()-1);
        System.out.println("Trump on: " + trump);

        testTrump.TrumbiAktiveerimine(trump);
        //testTrump.prindiMastid();


        //Testin meetodi Tapa
        List<Kaart> käesOlevadKaardid = Arrays.asList(pakk.get(12), pakk.get(16), pakk.get(4), pakk.get(29), pakk.get(39));
        System.out.println("Käes olevad kaardid on: " + käesOlevadKaardid);
        Kaart tapetav1 = pakk.get(20);
        System.out.println("Tapetav on: " + tapetav1);
        List<Kaart> tapanSellega1 = testTrump.Tapa(tapetav1, käesOlevadKaardid);
        System.out.println("Tapan selle kaardiga: " + tapanSellega1);

        Kaart tapetav2 = pakk.get(1);
        System.out.println("Tapetav on: " + tapetav2);
        List<Kaart> tapanSellega2 = testTrump.Tapa(tapetav2, käesOlevadKaardid);
        System.out.println("Tapan selle kaardiga: " + tapanSellega2);

        Kaart tapetav3 = pakk.get(19);
        System.out.println("Tapetav on: " + tapetav3);
        List<Kaart> tapanSellega3 = testTrump.Tapa(tapetav3, käesOlevadKaardid);
        System.out.println("Tapan selle kaardiga: " + tapanSellega3); // Kuna tappa polnud võimalik, tagastati tühi list


        // Testin meetodi Käi
        Kaart käidavKaart = testTrump.Käi(käesOlevadKaardid);
        System.out.println("Käidav kaart on: " + käidavKaart); // Käidav kaart on õige, sest 5♥ on trump
    }
}
