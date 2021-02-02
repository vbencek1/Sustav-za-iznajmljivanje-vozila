package vbencek.readers;

import java.util.ArrayList;
import java.util.List;
import vbencek.Sustav;
import vbencek.items.ListaTvrtke;
import vbencek.items.Tvrtka;


/**
 * Concrete class odnosno ConcreteProduct u Factory method uzorku dizajna. Klasa
 * služi za spremanje objekta tvrtke u odgovarajucu listu uz provjeru ispravnosti
 * @author vbencek
 */
public class ZapisTvrtke implements Zapis{
    
    /**
     * Metoda provjerava da li proslijeđeni redak ima ispravan broj argumenata
     * @param redak redak iz datoteke
     * @return 
     */
    private boolean isIspravnaDuljina(String redak) {
        redak=redak+" ";
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
            List<Tvrtka> lista = ListaTvrtke.getInstanca().getListaOrgJedinica();
            for (Tvrtka oj: lista) {
                if (oj.getId() == id) {
                    System.out.println("Greska: " + redak);
                    System.out.println(" -Zapis sa navedenim id-em vec postoji");
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Metoda koja provjerava da li su proslijeđene vrijednosti pozitivni integeri
     * @param redak proslijeđeni redak
     * @return 
     */
    private boolean isNadredena(String redak) {
        String[] polje = redak.split(";");
        try {
            if(polje[2].trim().isEmpty()) return true;
            if (Integer.parseInt(polje[2].trim()) < 0) {
                System.out.println("Greska: " + redak);
                System.out.println(" -Nadredena jedinica mora biti pozitivni integer");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Greska: " + redak);
            System.out.println(" -Nadredena jedinica mora biti pozitivni integer");
            return false;

        }
        return true;
    }
    
    /**
     * Metoda koja provjerava da li su proslijeđene vrijednosti pozitivni integeri
     * @param redak proslijeđeni redak
     * @return 
     */
    private boolean isIspravneLokacije(String redak) {       
        redak=redak+" ";
        String[] polje = redak.split(";");
        try {
            if(polje[3].trim().isEmpty()) return true;
            String[] lokacije=polje[3].trim().split(",");
            for(int i=0;i<lokacije.length;i++){
               if (Integer.parseInt(lokacije[i].trim()) < 0) {
                   System.out.println(" -Neispravan unos lokacija. Lokacija mora biti pozitivan broj!");
                return false;
            }}
        } catch (Exception e) {
            System.out.println("Greska: " + redak);
            System.out.println(" -Neispravan unos lokacija. Lokacija mora biti prazna ili formata oblika: 1, 2, 3");
            return false;

        }
        return true;
    }
    
    private List<Integer> kreirajListuLokacija(String strLokacija){
        List<Integer> lokacije = new ArrayList<>();
        if(strLokacija.trim().isEmpty()) return lokacije;
        String[] poljeLokacije=strLokacija.trim().split(",");
        for(int i=0;i<poljeLokacije.length;i++){
            lokacije.add(Integer.parseInt(poljeLokacije[i].trim()));
        }
        return lokacije;
    }
    
    private int setNadredena(String strNadredena){
        if(strNadredena.trim().isEmpty()) return 0;
        return Integer.parseInt(strNadredena);
    }
    
    
    @Override
    public void spremi(String redak) {
        //ispravi razlicti id provjera i implementiraj
        if (isIspravnaDuljina(redak) && isInteger(redak) && isRazlicitId(redak) && isNadredena(redak) && isIspravneLokacije(redak)) {
            redak=redak+" ";
            String[] polje = redak.split(";");
            Tvrtka tvrtka = new Tvrtka.TvrtkaBuilder(Integer.parseInt(polje[0].trim()))
                    .setNaziv(polje[1].trim())
                    .setNadredenaJednica(setNadredena(polje[2].trim()))
                    .setLokacije(kreirajListuLokacija(polje[3].trim()))
                    .build();
            ListaTvrtke.getInstanca().unosTvrtke(tvrtka);
            
        }else{
            Sustav.getInstanca().getTrenutniPogled().ispisiPogresku(redak);
        }
    }
    
}
