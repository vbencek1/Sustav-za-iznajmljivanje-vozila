package vbencek.readers;

import java.util.List;
import vbencek.Sustav;
import vbencek.items.ListaVozila;
import vbencek.items.Vozilo;

/**
 * Concrete class odnosno ConcreteProduct u Factory method uzorku dizajna.
 * Klasa služi za spremanje objekta vozila u listu vozila uz provjeru ispravnosti
 * @author vbencek
 */
public class ZapisVozila implements Zapis{
    
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
            if (Integer.parseInt(polje[0].trim()) < 0 
                    || Integer.parseInt(polje[2].trim())<0 
                    || Integer.parseInt(polje[3].trim())<0) {
                System.out.println("Greska: " + redak);
                System.out.println(" -Redak sadrži vrijednost koja nije pozitivni integer");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Greska: " + redak);
            System.out.println(" -Redak sadrži vrijednost koja nije pozitivni integer");
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
            List<Vozilo> lista = ListaVozila.getInstanca().getVozila();
            for (Vozilo v : lista) {
                if (v.getId() == id) {
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
            Vozilo vozilo= new Vozilo.VoziloBuilder(Integer.parseInt(polje[0].trim()))
                .setNaziv(polje[1].trim())
                .setVrijemePunjenja(Integer.parseInt(polje[2].trim()))
                .setDomet(Integer.parseInt(polje[3].trim()))
                .build();
            ListaVozila.getInstanca().unosVozila(vozilo);
        }else{
            Sustav.getInstanca().getTrenutniPogled().ispisiPogresku(redak);
        }
    }
    
}
