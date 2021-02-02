package vbencek.items;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import vbencek.args.provjera.NaredbaInteraktivni;
import vbencek.chain.racuni.PodaciRacuna;
import vbencek.chain.racuni.RacuniDispenseChain;

public class ListaRacuna {

    private List<Racun> listaRacuna = new ArrayList<>();

    private static ListaRacuna instanca;

    private ListaRacuna() {

    }

    static {
        instanca = new ListaRacuna();
    }

    public static ListaRacuna getInstanca() {
        return instanca;
    }

    public void setListaRacuna(List<Racun> listaRacuna) {
        this.listaRacuna = listaRacuna;
    }

    public List<Racun> getListaRacuna() {
        return listaRacuna;
    }

    public void dodajRacun(Racun racun) {
        listaRacuna.add(racun);
    }
    
    
    private Date formatirajVrijeme(String datum,String format){
        String def="yyyy-MM-dd HH:mm:ss";
        if(!"".equals(format)) def=format;
        SimpleDateFormat formatter=new SimpleDateFormat(def);
        Date date= new Date();
        try {
            date=formatter.parse(datum);
        } catch (ParseException ex) {
            Logger.getLogger(NaredbaInteraktivni.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }
    private boolean izmeduVremena(String vrijeme,String odDat, String doDat){
        Date defVrijeme=formatirajVrijeme(vrijeme, "");
        Date odVrijeme=formatirajVrijeme(odDat, "dd.MM.yyyy");
        Date doVrijeme=formatirajVrijeme(doDat, "dd.MM.yyyy");
        return (defVrijeme.after(odVrijeme) && defVrijeme.before(doVrijeme)) || defVrijeme.equals(odVrijeme) || defVrijeme.equals(doVrijeme);
    }
    
    public int vratiUkupnoNajmova(int idLokNajma, int idVrste, String odDat, String doDat){
        int brojNajmova=0;
        for(Racun rac: listaRacuna){
            if(rac.getLokacijaNajma()==idLokNajma &&rac.getVrstaVozila()==idVrste && izmeduVremena(rac.getVrijemeIzdavanja(), odDat, doDat)){
                brojNajmova++;
            }
        }
        return brojNajmova;
    }
    
    public long vratiUkupnoTrajanjeNajmova(int idLokNajma, int idVrste, String odDat, String doDat){
        long trajanje=0;
        for(Racun rac: listaRacuna){
            if(rac.getLokacijaNajma()==idLokNajma &&rac.getVrstaVozila()==idVrste && izmeduVremena(rac.getVrijemeIzdavanja(), odDat, doDat)){
                trajanje+=rac.getTrajanjeNajma();
            }
        }
        return trajanje;
    }
    
    public double vratiZaradu(int idLokVracanja, int idVrste, String odDat, String doDat){
        double zarada=0;
        for(Racun rac: listaRacuna){
            String vrijeme=rac.getVrijemePodmirenja();
            if(rac.getLokacijaVracanja()==idLokVracanja && rac.getVrstaVozila()==idVrste && !"".equals(vrijeme) && izmeduVremena(vrijeme, odDat, doDat)){
                zarada+=rac.getIznos();
            }
        }
        return zarada;
    }
    
    public Racun pronadiAktivanRacun(int idKorisnik, int idVrsta){
        for(Racun rac: listaRacuna){
            if(rac.getKorisnik()==idKorisnik && rac.getVrstaVozila()==idVrsta && "".equals(rac.getVrijemePodmirenja())){
                return rac;
            }
        }return null;
    }
    
    public Racun pronadiRacun(int idRac){
        for(Racun rac: listaRacuna){
            if(rac.getId()==idRac){
                return rac;
            }
        }return null;
    }
    
    public void kreirajRacun(int idOsoba, int idLok, int idVrsta, String vrijeme){
        RacuniDispenseChain rdc= new RacuniDispenseChain();
        PodaciRacuna pR= new PodaciRacuna();
        pR.setOpcija(0);
        pR.setKorisnik(idOsoba);
        pR.setVrstaVozila(idVrsta);
        pR.setLokacijaPosudbe(idLok);
        pR.setVrijemePosudbe(vrijeme);
        Racun racun=rdc.c1.dispense(pR);
        listaRacuna.add(racun);
    }
    
    public Racun azurirajRacun(int idKor, int idVrste, int lokacijaVracanja, long trajanjeNajma, int prijedeniKm, String vrijemePodmirenja, double zarada,boolean placen){
        RacuniDispenseChain rdc= new RacuniDispenseChain();
        PodaciRacuna podaci= new PodaciRacuna();
        podaci.setOpcija(2);
        podaci.setKorisnik(idKor);
        podaci.setVrstaVozila(idVrste);
        podaci.setLokacijaVracanja(lokacijaVracanja);
        podaci.setTrajanjeNajma(trajanjeNajma);
        podaci.setPrijedeniKm(prijedeniKm);
        podaci.setVrijemeVracanja(vrijemePodmirenja);
        podaci.setZarada(zarada);
        podaci.setPlacen(placen);
        
        Racun racun= rdc.c1.dispense(podaci);
        podaci.setOpcija(1);
        Racun azuriranRacun=rdc.c1.dispense(podaci);
        
        listaRacuna.remove(racun);
        listaRacuna.add(azuriranRacun);
        return azuriranRacun;
    }
    
    public List<Racun> racuniLokacija(int idLokNajma, String datumPocetni, String datumDrugi){
        List<Racun> tempRacuni= new ArrayList<>();
        for(Racun rac: listaRacuna){
            String vrijeme=rac.getVrijemeIzdavanja();
            if(rac.getLokacijaNajma()==idLokNajma && izmeduVremena(vrijeme, datumPocetni, datumDrugi))
                tempRacuni.add(rac);
        }
        return tempRacuni;
    }
    public List<Racun> racuniOsobe(int idOsobe, String datumPocetni, String datumDrugi){
        List<Racun> tempRacuni= new ArrayList<>();
        listaRacuna.sort(Comparator.comparing(Racun::getVrijemeIzdavanja));
        for(Racun rac: listaRacuna){
            String vrijeme=rac.getVrijemeIzdavanja();
            if(rac.getKorisnik()==idOsobe && izmeduVremena(vrijeme, datumPocetni, datumDrugi))
                tempRacuni.add(rac);
        }
        return tempRacuni;
    }
    
    public List<Racun> placanjeRacuna(int idOsobe, double iznos){
        List<Racun> placeniRacuni= new ArrayList<>();
        listaRacuna.sort(Comparator.comparing(Racun::getVrijemeIzdavanja));
        double preostaliIznos=iznos;
        for(Racun rac: listaRacuna){
            if(!rac.isPlacen() && rac.getIznos()<=preostaliIznos){
                rac.setPlacen(true);
                preostaliIznos=preostaliIznos-rac.getIznos();
                placeniRacuni.add(rac);
            }
        }
        return placeniRacuni;
    }
}
