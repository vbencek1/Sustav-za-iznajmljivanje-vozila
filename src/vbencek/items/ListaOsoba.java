package vbencek.items;

import java.util.ArrayList;
import java.util.List;
import vbencek.Sustav;

/**
 * Singleton uzorak dizajna
 * Klasa koja sadrži listu za objekte Osoba.
 * Mora imati samo jednu instancu.
 * @author vbencek
 */
public class ListaOsoba {
    private List<Osoba> listOsoba= new ArrayList<>();
    
    private static ListaOsoba instanca;
    
    private ListaOsoba(){
        
    }
    
    static{
        instanca= new ListaOsoba();
    }
    
    public static ListaOsoba getInstanca(){
        return instanca;
    }
    
    public List<Osoba> getOsobe(){
        return listOsoba;
    }
    public void setOsobe(List<Osoba> novaLista){
        listOsoba=novaLista;
    }
    
    /**
     * Metoda koja dodaje osobu u listu
     * @param osoba 
     */
    public void unosOsobe(Osoba osoba){
        listOsoba.add(osoba);
    }
    
    /**
     * Metoda koja vraća osobu ovisno o id-u
     * @param id
     * @return 
     */
    public Osoba pretraziOsobu(int id){
        for(Osoba o: listOsoba){
            if(o.getId()==id){
                return o;
            }
        }return null;
    }
    
    /**
     * Metoda koja provjerava da li korisnik ima vozilo u najmu
     * @param idOsobe
     * @param idVrsteVozila
     * @return 
     */
    public boolean imaOsobaVozilo(int idOsobe,int idVrsteVozila){
        List<VoziloLokacije> vozilaOsobe=pretraziOsobu(idOsobe).getVozilaUNajmu();
        try{
            if(vozilaOsobe.isEmpty()){
                return false;
            }else{
                for(VoziloLokacije vl: vozilaOsobe){
                    if(vl.getVrstaVozila()==idVrsteVozila){
                        return true;
                    }
                }
                return false;
            }
        }catch(Exception e){
            return true;
        }
    }
    
    public boolean osobaNemaVelikDug(int idOsobe){
        Osoba osoba = pretraziOsobu(idOsobe);
        float maxDug=Sustav.getInstanca().getMaksDugovanje();
        if(osoba.getDugovanje()>maxDug){
            System.out.println("Osoba ima dug koji iznosi: "+osoba.getDugovanje());
            return false;
        }
        return true;
    }
    
    /**
     * Metoda koja pridružuje odabrano vozilo korisniku
     * @param id
     * @param vl objekt VozliLokacije
     */
    public void pridruziVozilo(int id, VoziloLokacije vl){
        Osoba osoba= pretraziOsobu(id);
        List<VoziloLokacije> vozilaOsobe=osoba.getVozilaUNajmu();
        vozilaOsobe.add(vl);
        Osoba azuriranaOsoba= new Osoba.OsobaBuilder(osoba)
                .setVozilaUNajmu(vozilaOsobe)
                .setVrijemeZadnjegNajma(Sustav.getInstanca().getVrijeme().toString())
                .build();
        listOsoba.remove(osoba);
        listOsoba.add(azuriranaOsoba);
        
    }
    
    /**
     * Metoda koja oduzima vozilo od korisnika
     * @param id 
     * @param vl 
     * @param problem 
     */
    public void vratiVozilo(int id,VoziloLokacije vl,String problem,double cijena){
        Osoba osoba= pretraziOsobu(id);
        int brojNeispravnih=osoba.getBrojVracenihNeispravnih();
        double dugovanje=osoba.getDugovanje();
        boolean ugovor=osoba.isUgovor();
        if(problem!=null) brojNeispravnih++;
        if(ugovor) dugovanje=dugovanje+cijena;
        List<VoziloLokacije> vozilaOsobe=osoba.getVozilaUNajmu();
        vozilaOsobe.remove(vl);
        Osoba azuriranaOsoba= new Osoba.OsobaBuilder(osoba)
                .setVozilaUNajmu(vozilaOsobe)
                .setBrojVracenihNeispravnih(brojNeispravnih)
                .setDugovanje(dugovanje)
                .build();
        listOsoba.remove(osoba);
        listOsoba.add(azuriranaOsoba);
    }
    
    /**
     * Metoda koja dohvaća vozilo koje korisnik ima u najmu
     * @param id
     * @param idVrsteVozila
     * @return 
     */
    public VoziloLokacije dohvatiVoziloOsobe(int id,int idVrsteVozila){
        Osoba osoba= pretraziOsobu(id);
        List<VoziloLokacije> vozilaOsobe=osoba.getVozilaUNajmu();
        for(VoziloLokacije vl: vozilaOsobe){
                    if(vl.getVrstaVozila()==idVrsteVozila){
                        return vl;
                    }
                }
        return null;
    }
    
    public List<Osoba> dohvatiOsobeSaNajmovima(){
        List<Osoba> osobe=new ArrayList<>();
        for(Osoba o: listOsoba){
            if(o.getVrijemeZadnjegNajma()!=null){
                osobe.add(o);
            }
        }
        
        return osobe;
        
    }
    
    public void sklopiUgovor(Osoba osoba){
        Osoba azuriranaOsoba= new Osoba.OsobaBuilder(osoba)
                .setUgovor(true)
                .build();
        listOsoba.remove(osoba);
        listOsoba.add(azuriranaOsoba);
    }
    
}
