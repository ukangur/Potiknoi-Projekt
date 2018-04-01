import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        Kaardipakk uusPakk = new Kaardipakk();
        List<Kaart> pakk = uusPakk.getKaardipakk();
        System.out.println(pakk);
        System.out.println(uusPakk.getRisti());

        Kaardipakk väikePakk = new Kaardipakk("väike");
        System.out.println(väikePakk.getKaardipakk());
        System.out.println(väikePakk.getÄrtu());
    }

}
