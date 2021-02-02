package vbencek.items;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton uzorak dizajna
 * Klasa koja sadrži listu za objekte Vozila.
 * Mora imati samo jednu instancu.
 * @author vbencek
 */
public class ListaVozila {
    private List<Vozilo> listVozila= new ArrayList<>();
    
    private static ListaVozila instanca;
    
    private ListaVozila(){
        
    }
    
    static{
        instanca= new ListaVozila();
    }
    
    public static ListaVozila getInstanca(){
        return instanca;
    }
    
    public List<Vozilo> getVozila(){
        return listVozila;
    }
    public void setVozila(List<Vozilo> novaLista){
        listVozila=novaLista;
    }
    
    /**
     * Metoda koja dodaje vozilo u listu
     * @param vozilo 
     */
    public void unosVozila(Vozilo vozilo){
        listVozila.add(vozilo);
    }
    
    /**
     * Metoda koja pretražuje vozilo po id-u
     * @param id
     * @return objekt Vozilo
     */
    public Vozilo pretragaVozila(int id){
        for(Vozilo voz:listVozila ){
            if (voz.getId()==id)
                return voz;
        }
        return null;
    }
    
    /**
     * Metoda koja provjerava da li su prijeđeni kilometri manji ili jednaki od dometa koje vozilo ima
     * @param idVoz
     * @param km
     * @return 
     */
    public boolean isKmManjiOdDomet(int idVoz,int km){
        Vozilo unajmljenoVozilo=pretragaVozila(idVoz);
        int domet= unajmljenoVozilo.getDomet();
        return km<=domet;
    }
}
