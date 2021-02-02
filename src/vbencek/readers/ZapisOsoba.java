package vbencek.readers;

import java.util.ArrayList;
import java.util.List;
import vbencek.Sustav;
import vbencek.items.ListaOsoba;
import vbencek.items.Osoba;

/**
 * Concrete class odnosno ConcreteProduct u Factory method uzorku dizajna.
 * Klasa služi za spremanje objekta osoba u listu osoba uz provjeru ispravnosti
 * @author vbencek
 */
public class ZapisOsoba implements Zapis {
    
     /**
     * Metoda provjerava da li proslijeđeni redak ima ispravan broj argumenata
     * @param redak redak iz datoteke
     * @return 
     */
    private boolean isIspravnaDuljina(String redak) {
        String[] polje = redak.split(";");
        if (polje.length == 3) {
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
            List<Osoba> lista = ListaOsoba.getInstanca().getOsobe();
            for (Osoba o : lista) {
                if (o.getId() == id) {
                    System.out.println("Greska: " + redak);
                    System.out.println(" -Zapis sa navedenim id-em vec postoji");
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isBinary(String redak){
        
        String[] polje = redak.split(";");
        try {
            if (Integer.parseInt(polje[2].trim()) >1 && Integer.parseInt(polje[2].trim()) <0) {
                System.out.println("Greska: " + redak);
                System.out.println(" -Ugovor sadrži vrijednost koja nije 0 ili 1");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Greska: " + redak);
            System.out.println(" -Ugovor sadrži vrijednost nije integer");
            return false;

        }
        return true;
    }

    /**
     * Metoda koja provjerava ispravnost podataka te pomoću argumenata iz proslijeđenog retka kreira objekt koji sprema u listu
     * @param redak redak iz datoteke
     */
    @Override
    public void spremi(String redak) {
        if (isIspravnaDuljina(redak) && isInteger(redak) && isRazlicitId(redak) &&isBinary(redak)) {
            String[] polje = redak.split(";");
            boolean ugovor=true;
            if("0".equals(polje[2].trim())) ugovor=false;  
            Osoba osoba = new Osoba.OsobaBuilder(Integer.parseInt(polje[0].trim()))
                    .setImePrezime(polje[1].trim())
                    .setVozilaUNajmu(new ArrayList<>())
                    .setUgovor(ugovor)
                    .setDugovanje(0)
                    .setBrojVracenihNeispravnih(0)
                    .setVrijemeZadnjegNajma(null)
                    .build();
            ListaOsoba.getInstanca().unosOsobe(osoba);
        }else{
            Sustav.getInstanca().getTrenutniPogled().ispisiPogresku(redak);
        }
    }

}
