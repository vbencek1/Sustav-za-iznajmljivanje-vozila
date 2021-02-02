package vbencek.composite;

import java.util.List;
import vbencek.Sustav;
import vbencek.items.ListaLokacija;
import vbencek.items.ListaLokacijeKapaciteti;
import vbencek.items.ListaOsoba;
import vbencek.items.ListaRacuna;
import vbencek.items.ListaVozila;
import vbencek.items.Osoba;
import vbencek.items.Racun;
import vbencek.items.Vozilo;

/**
 * List u Composite uzorku dizajna
 * @author vbencek
 */
public class Lokacija implements Tvrtka {

    private int id;

    private String naziv;
    private String adresa;
    private String gps;
    
    private int dt;
    private int dc;
    private int dd;
    
    private void dodijeliVrijednosti(){
        dt=Sustav.getInstanca().getTekst();
        dc=Sustav.getInstanca().getCijeli();
        dd=Sustav.getInstanca().getDecimala();
    }

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getGps() {
        return gps;
    }

    private Lokacija(LokacijaBuilder builder) {
        this.id = builder.id;
        this.naziv = builder.naziv;
        this.adresa = builder.adresa;
        this.gps = builder.gps;
    }



    public static class LokacijaBuilder {

        private int id;
        private String naziv;
        private String adresa;
        private String gps;

        public LokacijaBuilder(int id) {
            this.id = id;
        }

        public LokacijaBuilder setNaziv(String naziv) {
            this.naziv = naziv;
            return this;
        }

        public LokacijaBuilder setAdresa(String adresa) {
            this.adresa = adresa;
            return this;
        }

        public LokacijaBuilder setGps(String gps) {
            this.gps = gps;
            return this;
        }

        public Lokacija build() {
            return new Lokacija(this);
        }
    }
    @Override
    public String ispisiStrukturuStanje() {
        return "";
    }

    @Override
    public String ispisiStrukturuZarade(String datumPocetni, String datumDrugi) {
        return "";
    }

    @Override
    public String ispisiStrukturuNajmova(String datumPocetni, String datumDrugi) {
        return "";
    }
    
    @Override
    public String ispisiStrukturuZaradeINajmova(String datumPocetni, String datumDrugi) {
        return "";
    }

    @Override
    public String ispisiStrukturuRacuna(String datumPocetni, String datumDrugi) {
        return "";
    }
    
    @Override
    public String ispisiZaraduINajmove(String datumPocetni, String datumDrugi) {
        ispisiNajmove(datumPocetni, datumDrugi);
        ispisiZaradu(datumPocetni, datumDrugi);
        return "";
    }
    @Override
    public String ispisiStrukturu() {
        return ("-(" + id + ") " + naziv);
    }

    @Override
    public String ispisiStanje() {
        dodijeliVrijednosti();
        ListaLokacijeKapaciteti kapaciteti = ListaLokacijeKapaciteti.getInstanca();
        int mjestaRomobil = kapaciteti.pretraziPoLokacijiIVozilu(id, 1).getBrojMjesta();
        int mjestaBicikl = kapaciteti.pretraziPoLokacijiIVozilu(id, 2).getBrojMjesta();
        int mjestaSkuter = kapaciteti.pretraziPoLokacijiIVozilu(id, 3).getBrojMjesta();
        int mjestaAuto = kapaciteti.pretraziPoLokacijiIVozilu(id, 4).getBrojMjesta();

        int raspRomobil = kapaciteti.pretraziPoLokacijiIVozilu(id, 1).getBrojVozila();
        int raspBicikl = kapaciteti.pretraziPoLokacijiIVozilu(id, 2).getBrojVozila();
        int raspSkuter = kapaciteti.pretraziPoLokacijiIVozilu(id, 3).getBrojVozila();
        int raspAuto = kapaciteti.pretraziPoLokacijiIVozilu(id, 4).getBrojVozila();

        int neispRomobil = kapaciteti.pretraziPoLokacijiIVozilu(id, 1).getBrojNeispravnih();
        int neispBicikl = kapaciteti.pretraziPoLokacijiIVozilu(id, 2).getBrojNeispravnih();
        int neispSkuter = kapaciteti.pretraziPoLokacijiIVozilu(id, 3).getBrojNeispravnih();
        int neispAuto = kapaciteti.pretraziPoLokacijiIVozilu(id, 4).getBrojNeispravnih();
        System.out.println("(" + id + ") " + naziv);
        System.out.println("----------------------");
        System.out.format("|%-"+dt+"s|%"+dc+"s|%"+dc+"s|%"+dc+"s|%"+dc+"s| %n","Vrsta","1","2","3","4");
        System.out.format("|%-"+dt+"s|%"+dc+"d|%"+dc+"d|%"+dc+"d|%"+dc+"d| %n","Mjesta" , mjestaRomobil , mjestaBicikl , mjestaSkuter, mjestaAuto);
        System.out.format("|%-"+dt+"s|%"+dc+"d|%"+dc+"d|%"+dc+"d|%"+dc+"d| %n","Raspolozivo" , raspRomobil , raspBicikl , raspSkuter, raspAuto);
        System.out.format("|%-"+dt+"s|%"+dc+"d|%"+dc+"d|%"+dc+"d|%"+dc+"d| %n","Neispravno" , neispRomobil , neispBicikl , neispSkuter, neispAuto);
        System.out.println("----------------------");
        System.out.println(" *Legenda: 1-romobil, 2-bicikl, 3-skuter, 4-auto\n");
        return "";
    }

    @Override
    public String ispisiZaradu(String datumPocetni, String datumDrugi) {
        dodijeliVrijednosti();
        ListaRacuna racuni= ListaRacuna.getInstanca();
        double zaradaRomobil = racuni.vratiZaradu(id, 1, datumPocetni, datumDrugi);
        double zaradaBicikl = racuni.vratiZaradu(id, 2, datumPocetni, datumDrugi);
        double zaradaSkuter = racuni.vratiZaradu(id, 3, datumPocetni, datumDrugi);
        double zaradaAuto = racuni.vratiZaradu(id, 4, datumPocetni, datumDrugi);
        System.out.println("(" + id + ") " + naziv);
        System.out.println("----------------------");
        System.out.format("|%-"+dt+"s|%"+dc+"s|%"+dc+"s|%"+dc+"s|%"+dc+"s| %n","Vrsta","1","2","3","4");
        System.out.format("|%-"+dt+"s|%"+dc+"."+dd+"f|%"+dc+"."+dd+"f|%"+dc+"."+dd+"f|%"+dc+"."+dd+"f| %n","Zarada" , zaradaRomobil , zaradaBicikl , zaradaSkuter, zaradaAuto);
        System.out.println("----------------------");
        System.out.println(" *Legenda: 1-romobil, 2-bicikl, 3-skuter, 4-auto\n");
        return "";
    }

    @Override
    public String ispisiNajmove(String datumPocetni, String datumDrugi) {
        dodijeliVrijednosti();
        ListaRacuna racuni= ListaRacuna.getInstanca();
        int najamRomobil = racuni.vratiUkupnoNajmova(id, 1, datumPocetni, datumDrugi);
        int najamBicikl = racuni.vratiUkupnoNajmova(id, 2, datumPocetni, datumDrugi);
        int najamSkuter = racuni.vratiUkupnoNajmova(id, 3, datumPocetni, datumDrugi);
        int najamAuto = racuni.vratiUkupnoNajmova(id, 4, datumPocetni, datumDrugi);
        
        long trajanjeNajamRomobil = racuni.vratiUkupnoTrajanjeNajmova(id, 1, datumPocetni, datumDrugi);
        long trajanjeNajamBicikl = racuni.vratiUkupnoTrajanjeNajmova(id, 2, datumPocetni, datumDrugi);
        long trajanjeNajamSkuter = racuni.vratiUkupnoTrajanjeNajmova(id, 3, datumPocetni, datumDrugi);
        long trajanjeNajamAuto = racuni.vratiUkupnoTrajanjeNajmova(id, 4, datumPocetni, datumDrugi); 
        System.out.println("(" + id + ") " + naziv);
        System.out.println("----------------------");
        System.out.format("|%-"+dt+"s|%"+dc+"s|%"+dc+"s|%"+dc+"s|%"+dc+"s| %n","Vrsta","1","2","3","4");
        System.out.format("|%-"+dt+"s|%"+dc+"d|%"+dc+"d|%"+dc+"d|%"+dc+"d| %n","Br. najmova" , najamRomobil , najamBicikl , najamSkuter, najamAuto);
        System.out.format("|%-"+dt+"s|%"+dc+"d|%"+dc+"d|%"+dc+"d|%"+dc+"d| %n","Tr. najmova" , trajanjeNajamRomobil , trajanjeNajamBicikl , trajanjeNajamSkuter, trajanjeNajamAuto);
        System.out.println("----------------------");
        System.out.println(" *Legenda: 1-romobil, 2-bicikl, 3-skuter, 4-auto\n");
        return "";
    }

    @Override
    public String ispisiRacune(String datumPocetni, String datumDrugi) {
        dodijeliVrijednosti();
        ListaRacuna racuni= ListaRacuna.getInstanca();
        List<Racun> listaRacuni=racuni.racuniLokacija(id,datumPocetni,datumDrugi);
        System.out.println("(" + id + ") " + naziv);
        System.out.println("----------------------");
        System.out.format("|%"+dc+"s|%-"+dt+"s|%-"+dt+"s|%-"+dt+"s|%-"+dt+"s|%-"+dt+"s|%-"+dc+"s|%-"+dc+"s|%-"+(dc+dd)+"s| %n",
                "ID","Vrijeme izdavanja","Osoba","Vrsta","Lokacija najma","Lokacija vracanja","Km","Sati","Iznos");
        for(Racun rac:listaRacuni){
            
        Osoba osoba=ListaOsoba.getInstanca().pretraziOsobu(rac.getKorisnik());
        Lokacija lokacijaNajma=ListaLokacija.getInstanca().pretragaLokacija(rac.getLokacijaNajma());   
        Lokacija lokacijaVracanja=ListaLokacija.getInstanca().pretragaLokacija(rac.getLokacijaVracanja());
        Vozilo vrstaVozila=ListaVozila.getInstanca().pretragaVozila(rac.getVrstaVozila());
        String strOsoba="("+osoba.getId()+") "+osoba.getImePrezime();
        String strLokNajma="("+lokacijaNajma.getId()+") "+lokacijaNajma.getNaziv();
        String strLokVrac="Nije jos placeno";
        if(lokacijaVracanja!=null){
            strLokVrac="("+lokacijaVracanja.getId()+") "+lokacijaVracanja.getNaziv();
        }
        String strVrstaVoz="("+vrstaVozila.getId()+") "+vrstaVozila.getNaziv();
            System.out.format("|%"+dc+"d|%-"+dt+"s|%-"+dt+"s|%-"+dt+"s|%-"+dt+"s|%-"+dt+"s|%"+dc+"d|%"+dc+"d|%"+(dc+dd)+"."+dd+"f| %n",
                rac.getId(),rac.getVrijemeIzdavanja(),strOsoba,strVrstaVoz,strLokNajma,
                strLokVrac,rac.getPrijedeniKm(),rac.getTrajanjeNajma(),rac.getIznos());
        }
        return "";
    }

}
