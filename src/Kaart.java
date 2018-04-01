import java.util.Arrays;
import java.util.Objects;

public class Kaart {

    private String tugevus;
    private char sümbol;

    private void tugevuseSobivus(String tugevus) {
        if (!Arrays.asList("A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2").contains(tugevus)) {
            throw new RuntimeException("Sisestatud tugevus ei ole korrektne!");
        }
    }

    private void sümboliSobivus(char sümbol) {
        if (!Arrays.asList('♣', '♦', '♠', '♥').contains(sümbol)) {
            throw new RuntimeException("Sisestatud mast ei ole korrektne!");
        }
    }

    // Kasutasin teist (2. JDK 7) viisi soovitatud leheküljelt: https://www.mkyong.com/java/java-how-to-overrides-equals-and-hashcode/
    public boolean equals(Object võrreldav) {
        if (võrreldav == this) return true;
        if (!(võrreldav instanceof Kaart)) {
            return false;
        }
        Kaart kaart = (Kaart) võrreldav;
        return Objects.equals(tugevus, kaart.tugevus) && Objects.equals(sümbol, kaart.sümbol);
    }

    public int hashCode() {
        return Objects.hash(tugevus, sümbol);
    }

    public Kaart(String tugevus, char sümbol) {
        tugevuseSobivus(tugevus);
        sümboliSobivus(sümbol);

        this.tugevus = tugevus;
        this.sümbol = sümbol;
    }

    public Kaart(String koos) {
        tugevuseSobivus(koos.substring(0, koos.length() - 1));
        sümboliSobivus(koos.charAt(koos.length() - 1));

        this.tugevus = koos.substring(0, koos.length() - 1);
        this.sümbol = koos.charAt(koos.length() - 1);
    }

    public String getTugevus() {
        return tugevus;
    }

    public char getMast() {
        return sümbol;
    }

    @Override
    public String toString() {
        return tugevus + sümbol;
    }

}
