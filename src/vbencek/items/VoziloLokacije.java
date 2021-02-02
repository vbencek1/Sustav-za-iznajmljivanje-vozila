package vbencek.items;

import java.util.Date;
import vbencek.Sustav;
import vbencek.state.NeispravnoStanje;
import vbencek.state.SlobodnoStanje;
import vbencek.state.VoziloStanje;

/**
 * Klasa služi za realizaciju mogućnosti da postoji više vozila na lokaciji koja se pune i imaju prijeđene kilometra
 * Instanca klase je vozilo koje sadrži aribute vremena posudbe, punjenja te prijeđenih kilometara
 * Zapravo je to konkretno vozilo.
 * @author vbencek
 */
public class VoziloLokacije {
    private int id;
    private int vrstaVozila;
    private Date vrijemeDoPuneBaterije;
    private Date vrijemePosudbe;
    private int ukupniKm;
    private int brojNajma;
    private VoziloStanje stanje; //popraviti brojac vozila na punjenju, ispravna vozila i eventualno ako je posudeno vozilo
    
    
    public VoziloLokacije(){
        id=Sustav.getInstanca().generateIdVozila();
        stanje=new SlobodnoStanje();
    }
    
    public int getId(){
        return id;
    }
    
    public int getVrstaVozila() {
        return vrstaVozila;
    }

    public Date getVrijemeDoPuneBaterije() {
        return vrijemeDoPuneBaterije;
    }

    public Date getVrijemePosudbe() {
        return vrijemePosudbe;
    }

    public int getUkupniKm() {
        return ukupniKm;
    }
    
    
    public int getBrojNajma(){
        return brojNajma;
    }
    
    public VoziloStanje getStanje(){
        return stanje;
    }
    
    public void setStanje(VoziloStanje stanje){
        this.stanje=stanje;
    }
    
    public void setVrstaVozila(int vrstaVozila) {
        this.vrstaVozila = vrstaVozila;
    }

    public void setVrijemeDoPuneBaterije(Date vrijemeDoPuneBaterije) {
        this.vrijemeDoPuneBaterije = vrijemeDoPuneBaterije;
    }

    public void setVrijemePosudbe(Date vrijemePosudbe) {
        this.vrijemePosudbe = vrijemePosudbe;
    }

    public void setUkupniKm(int UkupniKm) {
        this.ukupniKm = UkupniKm;
    }
    
    
    public void setBrojNajma(int brojNajma){
        this.brojNajma= brojNajma;
    }
    
    @Override
    public String toString() {
        String vrijeme="";
        String stanje="";
        if(vrijemeDoPuneBaterije==null){
            vrijeme="Nema informacije";
        }else{
            vrijeme=vrijemeDoPuneBaterije.toString();
        }
        if(this.stanje instanceof NeispravnoStanje){
            stanje="neispravno";
        }else{
            stanje="ispravno";
        }
        return id+" | "+vrstaVozila + " | " + vrijeme + " | " + ukupniKm+" | "+stanje+" | "+brojNajma;
    }
    
    
     
    
}
