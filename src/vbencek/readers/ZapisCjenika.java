package vbencek.readers;

import java.io.PrintStream;
import java.util.List;
import vbencek.GlavnaKlasa;
import vbencek.Sustav;
import vbencek.items.Cjenik;
import vbencek.items.ListaCjenika;
import vbencek.mvc.SkupniDatotekaView;

/**
 * Concrete class odnosno ConcreteProduct u Factory method uzorku dizajna. Klasa
 * služi za spremanje objekta cjenika u listu cjenika uz provjeru ispravnosti
 *
 * @author vbencek
 */
public class ZapisCjenika implements Zapis {

    /**
     * Metoda provjerava da li proslijeđeni redak ima ispravan broj argumenata
     *
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
     * Metoda koja provjerava da li su proslijeđene vrijednosti pozitivni
     * integeri
     *
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
            System.out.println(" -Redak sadrži vrijednost koja nije pozitivni integer");
            return false;

        }
        return true;
    }

    private boolean isDouble(String redak) {
        String[] polje = redak.split(";");
        try {
            if (Double.parseDouble(polje[1].trim().replace(",", ".")) < 0
                    || Double.parseDouble(polje[2].trim().replace(",", ".")) < 0
                    || Double.parseDouble(polje[3].trim().replace(",", ".")) < 0) {
                System.out.println("Greska: " + redak);
                System.out.println(" -Redak sadrži vrijednost koja nije pozitivni broj");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Greska: " + redak);
            System.out.println(" -Redak ne sadrži numericke vrijednost");
            return false;

        }
        return true;
    }

    /**
     * Metoda koja provjerava da li već postoji zapis sa istim id-em u listi
     *
     * @param redak proslijeđeni redak
     * @return
     */
    private boolean isRazlicitId(String redak) {
        String[] polje = redak.split(";");
        if (isInteger(redak)) {
            int id = Integer.parseInt(polje[0].trim());
            List<Cjenik> lista = ListaCjenika.getInstanca().getCjenici();
            for (Cjenik cj : lista) {
                if (cj.getId() == id) {
                    System.out.println("Greska: " + redak);
                    System.out.println(" -Zapis sa navedenim id-em vec postoji");
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Metoda koja provjerava ispravnost podataka te pomoću argumenata iz
     * proslijeđenog retka kreira objekt koji sprema u listu
     *
     * @param redak redak iz datoteke
     */
    @Override
    public void spremi(String redak) {
        if (isIspravnaDuljina(redak) && isInteger(redak) && isRazlicitId(redak) && isDouble(redak)) {
            String[] polje = redak.split(";");
            Cjenik cjenik = new Cjenik.CjenikBuilder(Integer.parseInt(polje[0].trim()))
                    .setNajam(Double.parseDouble(polje[1].trim().replace(",", ".")))
                    .setPoKm(Double.parseDouble(polje[3].trim().replace(",", ".")))
                    .setPoSatu(Double.parseDouble(polje[2].trim().replace(",", ".")))
                    .build();
            ListaCjenika.getInstanca().unosCjenika(cjenik);
        } else{
            Sustav.getInstanca().getTrenutniPogled().ispisiPogresku(redak);
        }

    }

}
