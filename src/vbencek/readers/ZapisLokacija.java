package vbencek.readers;

import java.util.List;
import vbencek.Sustav;
import vbencek.items.ListaLokacija;
import vbencek.composite.Lokacija;

/**
 * Concrete class odnosno ConcreteProduct u Factory method uzorku dizajna.
 * Klasa služi za spremanje objekta lokacije u listu lokacija uz provjeru ispravnosti
 * @author vbencek
 */
public class ZapisLokacija implements Zapis{
    
     /**
     * Metoda provjerava da li proslijeđeni redak ima ispravan broj argumenata
     * @param redak redak iz datoteke
     * @return 
     */
    private boolean isIspravnaDuljina(String redak) {
        String[] polje = redak.split(";");
        if (polje.length == 4) {
            return true;
        }
        System.out.println("Greska: " + redak);
        System.out.println(" -Redak nema ispravni broj podataka");
        return false;
    }

    /**
     * Metoda koja provjerava da li su proslijeđene vrijednosti pozitivni integeri
     * @param redak proslijeđeni redak
     * @return 
     */
    private boolean isInteger(String redak) {
        String[] polje = redak.split(";");
        try {
            if (Integer.parseInt(polje[0].trim()) < 0) {
                System.out.println("Greska: " + redak);
                System.out.println(" -Id sadrži vrijednost koja nije pozitivni integer");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Greska: " + redak);
            System.out.println(" -Id sadrži vrijednost koja nije pozitivni integer");
            return false;

        }
        return true;
    }
    
    /**
     * Metoda koja provjerava da li već postoji zapis sa istim id-em u listi
     * @param redak proslijeđeni redak
     * @return 
     */
    private boolean isRazlicitId(String redak) {
        String[] polje = redak.split(";");
        if (isInteger(redak)) {
            int id = Integer.parseInt(polje[0].trim());
            List<Lokacija> lista = ListaLokacija.getInstanca().getLokacije();
            for (Lokacija l : lista) {
                if (l.getId() == id) {
                    System.out.println("Greska: " + redak);
                    System.out.println(" -Zapis sa navedenim id-em vec postoji");
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Metoda koja provjerava ispravnost podataka te pomoću argumenata iz proslijeđenog retka kreira objekt koji sprema u listu
     * @param redak redak iz datoteke
     */
    @Override
    public void spremi(String redak) {
        if (isIspravnaDuljina(redak) && isInteger(redak) && isRazlicitId(redak)) {
            String[] polje = redak.split(";");
        Lokacija lokacija = new Lokacija.LokacijaBuilder(Integer.parseInt(polje[0].trim()))
                .setNaziv(polje[1].trim())
                .setAdresa(polje[2].trim())
                .setGps(polje[3].trim())
                .build();
        ListaLokacija.getInstanca().unosLokacije(lokacija);
        }else{
            Sustav.getInstanca().getTrenutniPogled().ispisiPogresku(redak);
        }
    }
    
}
