package vbencek.items;

import java.util.ArrayList;
import java.util.List;
import vbencek.composite.Lokacija;

/**
 * Privremena lista u kojoj se spremuje lokacije nakon ucitanja iz datoteke!
 * Singleton uzorak dizajna
 * Klasa koja sadrži listu za objekte Lokacije.
 * Mora imati samo jednu instancu.
 * @author vbencek
 */
public class ListaLokacija {
    
    private List<Lokacija> listLokacija= new ArrayList<>();
    
    private static ListaLokacija instanca;
    
    private ListaLokacija(){
        
    }
    
    static{
        instanca= new ListaLokacija();
    }
    
    public static ListaLokacija getInstanca(){
        return instanca;
    }
    
    
    public List<Lokacija> getLokacije(){
        return listLokacija;
    }
    public void setLokacije(List<Lokacija> novaLista){
        listLokacija=novaLista;
    }
    
    /**
     * Metoda za unos nove lokacije u listu
     * @param lokacija 
     */
    public void unosLokacije(Lokacija lokacija){
        listLokacija.add(lokacija);
    }
    
    /**
     * Pretraga lokacija po id-u
     * @param id
     * @return lokacija
     */
    public Lokacija pretragaLokacija(int id){
        for(Lokacija lok:listLokacija ){
            if (lok.getId()==id)
                return lok;
        }
        return null;
    }
    
}
