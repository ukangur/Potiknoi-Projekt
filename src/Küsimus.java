import java.util.List;
import java.util.Scanner;

public class Küsimus {

    private Scanner sc = new Scanner(System.in);

    public Kaart MillineKaart(List<Kaart> kaardid) {
        String küsimus = "Millist kaarti soovid käia?\n   Valikud:";
        for (int i = 1; i <= kaardid.size(); i++) {
            küsimus += " " + i + "." + kaardid.get(i-1).toString();
        }
        küsimus += "\nSisesta valiku number!";
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

    public String MillineKäik(List<String> käigud) {
        String küsimus = "Kas soovid:";
        for (int i = 1; i <= käigud.size(); i++) {
            küsimus += " " + i + "." + käigud.get(i - 1);
        }
        küsimus += "\nSisesta valiku number!";
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
}
