package vbencek.args.provjera;

/**
 *Ova klasa napravljena je iz razloga kako bi se mogle očuvati klase za interaktivni i skupni način rada (učitavanje parametara)
 * Cilj ove klase je dati metodu provjere koja uvjek vraća false.
 * Zadaća 3 ne dozvoljava pokretanje preko parametara za skupni i interaktivni.
 * @author vbencek
 */
public class NaredbaUvjekFalse implements NaredbaKL{

    @Override
    public boolean provjeri(String[] argumenti) {
        System.out.println("Pogrešan unos paremetara.");
        System.out.println("Primjer pokretanja: java -jar vbencek_zadaca_3.jar DZ_3_konfiguracija.txt");
       return false;
    }
    
}
