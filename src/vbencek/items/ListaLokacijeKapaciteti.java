package vbencek.items;

import java.util.ArrayList;
import java.util.List;
import vbencek.Sustav;
import vbencek.chain.vozila.VozilaDispenseChain;
import vbencek.state.PunjenjeStanje;
import vbencek.state.SlobodnoStanje;
import vbencek.state.UnajmljenoStanje;

/**
 * Singleton uzorak dizajna
 * Klasa koja sadrži listu za objekte Kapaciteta lokacija.
 * Mora imati samo jednu instancu.
 * @author vbencek
 */
public class ListaLokacijeKapaciteti {

    private List<LokacijaKapacitet> listaKapaciteta = new ArrayList<>();
    public VoziloLokacije najboljiKandidat;

    private static ListaLokacijeKapaciteti instanca;

    private ListaLokacijeKapaciteti() {

    }

    static {
        instanca = new ListaLokacijeKapaciteti();
    }

    public static ListaLokacijeKapaciteti getInstanca() {
        return instanca;
    }

    public List<LokacijaKapacitet> getListaKapaciteta() {
        return listaKapaciteta;
    }

    public void setListaKapaciteta(List<LokacijaKapacitet> listaKapaciteta) {
        this.listaKapaciteta = listaKapaciteta;
    }
    
    /**
     * Metoda za dodavanje novog kapaciteta
     * @param kapacitet 
     */
    public void unosKapaciteta(LokacijaKapacitet kapacitet) {
        listaKapaciteta.add(kapacitet);
    }
    
    /**
     * Pretraga kapaciteta po lokaciji i vozilu
     * @param idLok
     * @param idVoz
     * @return kapacitet
     */
    public LokacijaKapacitet pretraziPoLokacijiIVozilu(int idLok, int idVoz) {
        for (LokacijaKapacitet lk : listaKapaciteta) {
            if (lk.getVozilo() == idVoz && lk.getLokacija() == idLok) {

                return lk;
            }
        }
        return null;
    }
    
    /**
     * Metoda koja provjerava da li postoji slobodno vozilo za unajmiti
     * @param idLok
     * @param idVoz
     * @return true ako postoji inaće vraća false
     */
    public boolean postojiSlobodnoVozilo(int idLok, int idVoz) {
        try {
            return pretraziPoLokacijiIVozilu(idLok, idVoz).getBrojVozila() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Metoda koja vraća broj raspoloživih mjesta za punjenje vozila
     * @param idLok
     * @param idVoz
     * @return 
     */
    public int vratiBrojRaspolozivihMjesta(int idLok, int idVoz) {
        try {
            LokacijaKapacitet lk = pretraziPoLokacijiIVozilu(idLok, idVoz);
            return lk.getBrojMjesta() - lk.getBrojVozila();

        } catch (Exception e) {
            return -1;
        }
    }
    
    /**
     * Metoda koja vraća ukupni broj mjesta na lokaciji bez obzira na popunjenost mjesta za punjenje vozila
     * @param idLok
     * @param idVoz
     * @return 
     */
    public int UkupniBrojMjesta(int idLok, int idVoz) {
        try {
            LokacijaKapacitet lk = pretraziPoLokacijiIVozilu(idLok, idVoz);
            return lk.getBrojMjesta();

        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Metoda koja vraća broj vozila koja su trenutno na punjenu ili čekaju na najam
     * @param idLok
     * @param idVoz
     * @return 
     */
    public int brojVozilaNaPunjenju(int idLok, int idVoz) {
        int brojacVozila = 0;
        try {
            LokacijaKapacitet lk = pretraziPoLokacijiIVozilu(idLok, idVoz);
            for (VoziloLokacije vl : lk.getVozilaLokacije()) {
                if (vl.getStanje() instanceof PunjenjeStanje) {
                    brojacVozila++;
                }
            }
        } catch (Exception e) {
            return 0;
        }
        return brojacVozila;
    }

    
    /**
     * Metoda kojom se smanjuje broj odabranog vozila na lokaciji
     * @param idLok
     * @param idVoz 
     */
    public void smanjiBrojVozila(int idLok, int idVoz) {
        LokacijaKapacitet lk = pretraziPoLokacijiIVozilu(idLok, idVoz);
        int brojVozila = lk.getBrojVozila() - 1;
        LokacijaKapacitet azuriranaLk = new LokacijaKapacitet.LokacijaKapacitetBuilder(lk).setBrojVozila(brojVozila).build();
        listaKapaciteta.remove(lk);
        listaKapaciteta.add(azuriranaLk);

    }
    
    private VoziloLokacije strategijaPridruzivanja(List<VoziloLokacije> kandidati, LokacijaKapacitet lk){
        VoziloLokacije voziloZaNajam;
        int brojNajma;
        VozilaDispenseChain vdc= new VozilaDispenseChain();
        VoziloLokacije dohvacenoVozilo=vdc.c1.dispense(kandidati);
        
        voziloZaNajam=dohvacenoVozilo;
        
        brojNajma=voziloZaNajam.getBrojNajma()+1;
        voziloZaNajam.setVrijemePosudbe(Sustav.getInstanca().getVrijeme());
        voziloZaNajam.setBrojNajma(brojNajma);
        voziloZaNajam.setStanje(new UnajmljenoStanje());
        lk.getVozilaLokacije().remove(dohvacenoVozilo);
        
        return voziloZaNajam;
    }
    
    /**
     * Metoda kojom se vrši najam vozila, oduzima vozilo sa lokacije i pridružuje ga korisniku
     * @param idLok
     * @param idVoz
     * @return 
     */
    public VoziloLokacije pridruziVoziloKorisniku(int idLok, int idVoz) {
        List<VoziloLokacije> kandidati= new ArrayList<>();
        VoziloLokacije voziloZaNajam=null;
        LokacijaKapacitet lk = pretraziPoLokacijiIVozilu(idLok, idVoz);
        for (VoziloLokacije vl : lk.getVozilaLokacije()) {
            if (vl.getVrstaVozila() == idVoz 
                    && vl.getStanje() instanceof SlobodnoStanje) {
                kandidati.add(vl);
            }
        }
        if(kandidati.size()>0){
            voziloZaNajam=strategijaPridruzivanja(kandidati,lk);
            System.out.println("ID unajmljenog vozila: "+voziloZaNajam.getId());
            smanjiBrojVozila(idLok, idVoz);
        }
        return voziloZaNajam;
    }
    
    /**
     * metoda koja vrši vraćanje vozila na lokaciju.Oduzima vozilo korisniku i pridružuje ga lokaciji
     * @param idLok
     * @param idVoz
     * @param voziloUNajmu 
     * @param problem 
     */
    public void oduzmiVoziloKorisniku(int idLok, int idVoz, VoziloLokacije voziloUNajmu, String problem) {
        LokacijaKapacitet lk = pretraziPoLokacijiIVozilu(idLok, idVoz);
        List<VoziloLokacije> lista = lk.getVozilaLokacije();
        lista.add(voziloUNajmu);
        int brojVozila = lk.getBrojVozila() + 1;
        int brojNeispravnih=lk.getBrojNeispravnih();
        if(problem!=null) brojNeispravnih+=1;
        LokacijaKapacitet azuriranaLk = new LokacijaKapacitet.LokacijaKapacitetBuilder(lk)
                .setVozilaLokacije(lista)
                .setBrojNeispravnih(brojNeispravnih)
                .setBrojVozila(brojVozila)
                .build();
        listaKapaciteta.remove(lk);
        listaKapaciteta.add(azuriranaLk);
    }
    
}
