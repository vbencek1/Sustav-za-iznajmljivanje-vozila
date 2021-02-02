package vbencek.readers;

import java.util.ArrayList;
import java.util.List;
import vbencek.Sustav;
import vbencek.items.ListaLokacijeKapaciteti;
import vbencek.items.LokacijaKapacitet;
import vbencek.items.VoziloLokacije;

/**
 * Concrete class odnosno ConcreteProduct u Factory method uzorku dizajna.
 * Klasa služi za spremanje objekta kapaciteta u listu kapaciteta uz provjeru ispravnosti
 * @author vbencek
 */
public class ZapisKapaciteta implements Zapis {
    
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
        for (int i = 0; i < polje.length; i++) {
            try {
                if (Integer.parseInt(polje[i].trim()) < 0) {
                    System.out.println("Greska: " + redak);
                    System.out.println(" -Redak sadrži vrijednost koja nije pozitivni integer");
                    return false;
                }
            } catch (Exception e) {
                System.out.println("Greska: " + redak);
                System.out.println(" -Redak sadrži vrijednost koja nije pozitivni integer");
                return false;

            }
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
            int idLok = Integer.parseInt(polje[0].trim());
            int idVoz = Integer.parseInt(polje[1].trim());
            List<LokacijaKapacitet> lista = ListaLokacijeKapaciteti.getInstanca().getListaKapaciteta();
            for (LokacijaKapacitet lk : lista) {
                if (lk.getVozilo() == idVoz && lk.getLokacija() == idLok) {
                    System.out.println("Greska: " + redak);
                    System.out.println(" -Zapis sa navedenim id-em(vozilo - lokacija) vec postoji");
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Metoda koja provjerava da li je broj vozila veći od broja raspoloživog mjesta za njihovo punjenje
     * @param redak proslijeđeni redak
     * @return vraća true ako je sve ispravno
     */
    private boolean isDostupnoMjesto(String redak) {
        String[] polje = redak.split(";");
        if (isInteger(redak)) {
            int brMjesta = Integer.parseInt(polje[2].trim());
            int brVozila = Integer.parseInt(polje[3].trim());
            if (brMjesta < brVozila) {
                System.out.println("Greska: " + redak);
                    System.out.println(" -Zapis sadrži veći broj vozila od dopuštenog");
                return false;
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
        if (isIspravnaDuljina(redak) && isInteger(redak) && isRazlicitId(redak) && isDostupnoMjesto(redak)) {
            String[] polje = redak.split(";");
            
            List<VoziloLokacije> listaVozila=new ArrayList<>();
            for(int i=0;i<Integer.parseInt(polje[3].trim());i++){
                VoziloLokacije vl= new VoziloLokacije();
                vl.setUkupniKm(0);
                vl.setVrstaVozila(Integer.parseInt(polje[1].trim()));
                vl.setBrojNajma(0);
                listaVozila.add(vl);
                
            }
            LokacijaKapacitet lk = new LokacijaKapacitet.LokacijaKapacitetBuilder(Integer.parseInt(polje[1].trim()), Integer.parseInt(polje[0].trim()))
                    .setBrojMjesta(Integer.parseInt(polje[2].trim()))
                    .setBrojVozila(Integer.parseInt(polje[3].trim()))
                    .setVozilaLokacije(listaVozila)
                    .setBrojNeispravnih(0)
                    .build();
            ListaLokacijeKapaciteti.getInstanca().unosKapaciteta(lk);
        }else{
            Sustav.getInstanca().getTrenutniPogled().ispisiPogresku(redak);
        }
    }

}
