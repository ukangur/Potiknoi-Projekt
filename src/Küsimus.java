import java.util.List;
import java.util.Scanner;

public class Küsimus {


    private Scanner sc = new Scanner(System.in);


    // Sisestatakse kaartide list ja meetod annab igale kaardile järjekorra numbri
    // Siis küsitakse mängijalt, millist kaarti ta nendest sooib kasutada ja valiku numbri
    // Tagastatakse kaart, mille mängija valis
    public Kaart MillineKaart(List<Kaart> kaardid) {
        StringBuilder küsimus = new StringBuilder("Millist kaarti soovid käia?\n   Valikud:");
        for (int i = 1; i <= kaardid.size(); i++) {
            küsimus.append(" ").append(i).append(".").append(kaardid.get(i - 1).toString());
        }
        küsimus.append("\nSisesta valiku number!");
        System.out.println(küsimus);

        for (; ; ) {
            if (sc.hasNextLine()) {
                String vastus = sc.nextLine();
                try {
                    int valitud = Integer.parseInt(vastus);
                    return kaardid.get(valitud - 1);
                }
                catch (Exception e) {
                    System.out.println("Polnud valiku number, sisesta vastus uuesti!");
                }
            }
        }
    }

    // Sisestatakse sõnede list ja meetod annab igale sõnele järjekorra numbri
    // Siis küsitakse mängijalt, millist käiku ta soovib teha
    // Tagastatakse mängija valitud käik (sõnena)
    public String MillineKäik(List<String> käigud) {
        StringBuilder küsimus = new StringBuilder("Kas soovid:");
        for (int i = 1; i <= käigud.size(); i++) {
            küsimus.append(" ").append(i).append(".").append(käigud.get(i - 1));
        }
        küsimus.append("\nSisesta valiku number!");
        System.out.println(küsimus);

        for (; ; ) {
            if (sc.hasNextLine()) {
                String vastus = sc.nextLine();
                try {
                    int valitud = Integer.parseInt(vastus);
                    return käigud.get(valitud - 1);
                }
                catch (Exception e) {
                    System.out.println("Polnud valiku number, sisesta vastus uuesti!");
                }
            }
        }
    }


    // Sisestatakse sõnede list ja meetod annab igale sõnele järjekorra numbri
    // Siis küsitakse mängijalt, millist taset ta soovib
    // Tagastatakse mängija valitud tase (sõnena)
    public String MillineTase(List<String> tase) {
        StringBuilder küsimus = new StringBuilder("Millise raskustasemega soovid mängida:");
        for (int i = 1; i <= tase.size(); i++) {
            küsimus.append(" ").append(i).append(".").append(tase.get(i - 1));
        }
        küsimus.append("\nSisesta raskusastme number!");
        System.out.println(küsimus);

        for (; ; ) {
            if (sc.hasNextLine()) {
                String vastus = sc.nextLine();
                try {
                    int valitud = Integer.parseInt(vastus);
                    return tase.get(valitud - 1);
                }
                catch (Exception e) {
                    System.out.println("Polnud valiku number, sisesta vastus uuesti!");
                }
            }
        }
    }

    // Küsitakse mängija nime kuni mängija oma nime kirjutab
    // Tagastatakse mängija sisestatud nimi
    public String Nimi() {
        String vastus = "";
        while(vastus.length() == 0) {
            System.out.println("Sisesta enda nimi: ");
            if (sc.hasNextLine()) {
                vastus = sc.nextLine();
            }
        }
        return vastus;
    }


}
