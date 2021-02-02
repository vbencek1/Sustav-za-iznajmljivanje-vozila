package vbencek.items;

import java.util.ArrayList;
import java.util.List;

// Privremena lista u kojoj se spremuje tvrtke nakon ucitanja iz datoteke!
public class ListaTvrtke {
    private List<Tvrtka> listOrgJedinice= new ArrayList<>();
    
    private static ListaTvrtke instanca;
    
    private ListaTvrtke(){
        
    }
    
    static{
        instanca= new ListaTvrtke();
    }
    
    public static ListaTvrtke getInstanca(){
        return instanca;
    }
    
     public List<Tvrtka> getListaOrgJedinica(){
        return listOrgJedinice;
    }
    public void setListaOrgJedninica(List<Tvrtka> novaLista){
        listOrgJedinice=novaLista;
    }
    
    public void unosTvrtke(Tvrtka tvrtka){
        listOrgJedinice.add(tvrtka);
    }
}
