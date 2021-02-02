package vbencek.composite;

import java.util.ArrayList;
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
import vbencek.iterator.TvrtkaIterator;

/**
 * Component u Composite uzorku dizajna
 * @author vbencek
 */
public class OrganizacijskaJedinica implements Tvrtka {

    private int id;
    private String naziv;

    private List<Tvrtka> childLokacije;
    private List<Tvrtka> podredeneJedinice;
    private int dt;
    private int dc;
    private int dd;
    
    public OrganizacijskaJedinica(int id, String naziv) {
        this.id = id;
        this.naziv = naziv;
        childLokacije = new ArrayList<>();
        podredeneJedinice = new ArrayList<>();
    }
    
    
    private void dodijeliVrijednosti(){
        dt=Sustav.getInstanca().getTekst();
        dc=Sustav.getInstanca().getCijeli();
        dd=Sustav.getInstanca().getDecimala();
    }

    public int getId() {
        return id;
    }

    public void dodajListuLokacija(List<Tvrtka> lokacije) {
        childLokacije = lokacije;
    }

    public void dodajLokaciju(Lokacija lokacija) {
        childLokacije.add(lokacija);
    }

    public void dodajPodredenuJedinicu(OrganizacijskaJedinica oJ) {
        podredeneJedinice.add(oJ);
    }

    public List<Tvrtka> dajLokacije() {
        return childLokacije;
    }

    public List<Tvrtka> dajPodredene() {
        return podredeneJedinice;
    }

    private boolean isNadredena(OrganizacijskaJedinica prijasnja, OrganizacijskaJedinica sadasnja) {
        if (prijasnja.dajPodredene().contains(sadasnja)) {
            return true;
        } else {
            return false;
        }
    }
    
    private OrganizacijskaJedinica dajNadredenu(OrganizacijskaJedinica root){
        TvrtkaIterator iterator = new TvrtkaIterator(root);
        while(iterator.hasNext()){
            OrganizacijskaJedinica jedinica = (OrganizacijskaJedinica) iterator.next();
            if(isNadredena(jedinica, this)){
                return jedinica;
            }
        }
        return null;
    }

    /**
     * Metoda koja vrsi kalkulacije ukupnog broja mjesta po lokacijama
     * organizacijske jedninice
     *
     * @param oj
     * @return polje sa vrijednostima 0-romobil, 1-bicikl, 2-skuter, 3-auto
     */
    private Integer[] UkupnoMjestaPoVrsti(OrganizacijskaJedinica oj) {
        Integer[] kumuliraneVrijednosti = new Integer[4];
        ListaLokacijeKapaciteti kapaciteti = ListaLokacijeKapaciteti.getInstanca();
        int ukupnoRomobil = 0;
        int ukupnoBicikl = 0;
        int ukupnoSkuter = 0;
        int ukupnoAuto = 0;
        TvrtkaIterator iterator = new TvrtkaIterator(oj);
        while (iterator.hasNext()) {
            OrganizacijskaJedinica jedinica = (OrganizacijskaJedinica) iterator.next();
            for (Tvrtka lok : jedinica.dajLokacije()) {
                Lokacija lokacija = (Lokacija) lok;
                ukupnoRomobil += kapaciteti.pretraziPoLokacijiIVozilu(lokacija.getId(), 1).getBrojMjesta();
                ukupnoBicikl += kapaciteti.pretraziPoLokacijiIVozilu(lokacija.getId(), 2).getBrojMjesta();
                ukupnoSkuter += kapaciteti.pretraziPoLokacijiIVozilu(lokacija.getId(), 3).getBrojMjesta();
                ukupnoAuto += kapaciteti.pretraziPoLokacijiIVozilu(lokacija.getId(), 4).getBrojMjesta();
            }
        }
        kumuliraneVrijednosti[0] = ukupnoRomobil;
        kumuliraneVrijednosti[1] = ukupnoBicikl;
        kumuliraneVrijednosti[2] = ukupnoSkuter;
        kumuliraneVrijednosti[3] = ukupnoAuto;
        return kumuliraneVrijednosti;

    }

    /**
     * Metoda koja vrsi kalkulacije ukupnog broja raspolozivnih vozila po
     * lokacijama organizacijske jedninice
     *
     * @param oj
     * @return polje sa vrijednostima 0-romobil, 1-bicikl, 2-skuter, 3-auto
     */
    private Integer[] UkupnoRaspolozivoPoVrsti(OrganizacijskaJedinica oj) {
        Integer[] kumuliraneVrijednosti = new Integer[4];
        ListaLokacijeKapaciteti kapaciteti = ListaLokacijeKapaciteti.getInstanca();
        int ukupnoRomobil = 0;
        int ukupnoBicikl = 0;
        int ukupnoSkuter = 0;
        int ukupnoAuto = 0;
        TvrtkaIterator iterator = new TvrtkaIterator(oj);
        while (iterator.hasNext()) {
            OrganizacijskaJedinica jedinica = (OrganizacijskaJedinica) iterator.next();
            for (Tvrtka lok : jedinica.dajLokacije()) {
                Lokacija lokacija = (Lokacija) lok;
                ukupnoRomobil += kapaciteti.pretraziPoLokacijiIVozilu(lokacija.getId(), 1).getBrojVozila();
                ukupnoBicikl += kapaciteti.pretraziPoLokacijiIVozilu(lokacija.getId(), 2).getBrojVozila();
                ukupnoSkuter += kapaciteti.pretraziPoLokacijiIVozilu(lokacija.getId(), 3).getBrojVozila();
                ukupnoAuto += kapaciteti.pretraziPoLokacijiIVozilu(lokacija.getId(), 4).getBrojVozila();
            }
        }
        kumuliraneVrijednosti[0] = ukupnoRomobil;
        kumuliraneVrijednosti[1] = ukupnoBicikl;
        kumuliraneVrijednosti[2] = ukupnoSkuter;
        kumuliraneVrijednosti[3] = ukupnoAuto;
        return kumuliraneVrijednosti;

    }

    /**
     * Metoda koja vrsi kalkulacije ukupnog broja raspolozivnih vozila po
     * lokacijama organizacijske jedninice
     *
     * @param oj
     * @return polje sa vrijednostima 0-romobil, 1-bicikl, 2-skuter, 3-auto
     */
    private Integer[] UkupnoNeispravnoPoVrsti(OrganizacijskaJedinica oj) {
        Integer[] kumuliraneVrijednosti = new Integer[4];
        ListaLokacijeKapaciteti kapaciteti = ListaLokacijeKapaciteti.getInstanca();
        int ukupnoRomobil = 0;
        int ukupnoBicikl = 0;
        int ukupnoSkuter = 0;
        int ukupnoAuto = 0;
        TvrtkaIterator iterator = new TvrtkaIterator(oj);
        while (iterator.hasNext()) {
            OrganizacijskaJedinica jedinica = (OrganizacijskaJedinica) iterator.next();
            for (Tvrtka lok : jedinica.dajLokacije()) {
                Lokacija lokacija = (Lokacija) lok;
                ukupnoRomobil += kapaciteti.pretraziPoLokacijiIVozilu(lokacija.getId(), 1).getBrojNeispravnih();
                ukupnoBicikl += kapaciteti.pretraziPoLokacijiIVozilu(lokacija.getId(), 2).getBrojNeispravnih();
                ukupnoSkuter += kapaciteti.pretraziPoLokacijiIVozilu(lokacija.getId(), 3).getBrojNeispravnih();
                ukupnoAuto += kapaciteti.pretraziPoLokacijiIVozilu(lokacija.getId(), 4).getBrojNeispravnih();
            }
        }
        kumuliraneVrijednosti[0] = ukupnoRomobil;
        kumuliraneVrijednosti[1] = ukupnoBicikl;
        kumuliraneVrijednosti[2] = ukupnoSkuter;
        kumuliraneVrijednosti[3] = ukupnoAuto;
        return kumuliraneVrijednosti;

    }

    /**
     * Metoda koja vrsi kalkulacije ukupnog najmova vozila po lokacijama
     * organizacijske jedninice
     *
     * @param oj
     * @return polje sa vrijednostima 0-romobil, 1-bicikl, 2-skuter, 3-auto
     */
    private Integer[] UkupnoNajmovaPoVrsti(OrganizacijskaJedinica oj, String datumPocetni, String datumDrugi) {
        Integer[] kumuliraneVrijednosti = new Integer[4];
        ListaRacuna racuni = ListaRacuna.getInstanca();
        int ukupnoRomobil = 0;
        int ukupnoBicikl = 0;
        int ukupnoSkuter = 0;
        int ukupnoAuto = 0;
        TvrtkaIterator iterator = new TvrtkaIterator(oj);
        while (iterator.hasNext()) {
            OrganizacijskaJedinica jedinica = (OrganizacijskaJedinica) iterator.next();
            for (Tvrtka lok : jedinica.dajLokacije()) {
                Lokacija lokacija = (Lokacija) lok;
                ukupnoRomobil += racuni.vratiUkupnoNajmova(lokacija.getId(), 1, datumPocetni, datumDrugi);
                ukupnoBicikl += racuni.vratiUkupnoNajmova(lokacija.getId(), 2, datumPocetni, datumDrugi);
                ukupnoSkuter += racuni.vratiUkupnoNajmova(lokacija.getId(), 3, datumPocetni, datumDrugi);
                ukupnoAuto += racuni.vratiUkupnoNajmova(lokacija.getId(), 4, datumPocetni, datumDrugi);
            }
        }
        kumuliraneVrijednosti[0] = ukupnoRomobil;
        kumuliraneVrijednosti[1] = ukupnoBicikl;
        kumuliraneVrijednosti[2] = ukupnoSkuter;
        kumuliraneVrijednosti[3] = ukupnoAuto;
        return kumuliraneVrijednosti;

    }

    /**
     * Metoda koja vrsi kalkulacije ukupnog najmova vozila po lokacijama
     * organizacijske jedninice
     *
     * @param oj
     * @return polje sa vrijednostima 0-romobil, 1-bicikl, 2-skuter, 3-auto
     */
    private Long[] UkupnoTrajanjeNajmovaPoVrsti(OrganizacijskaJedinica oj, String datumPocetni, String datumDrugi) {
        Long[] kumuliraneVrijednosti = new Long[4];
        ListaRacuna racuni = ListaRacuna.getInstanca();
        long ukupnoRomobil = 0;
        long ukupnoBicikl = 0;
        long ukupnoSkuter = 0;
        long ukupnoAuto = 0;
        TvrtkaIterator iterator = new TvrtkaIterator(oj);
        while (iterator.hasNext()) {
            OrganizacijskaJedinica jedinica = (OrganizacijskaJedinica) iterator.next();
            for (Tvrtka lok : jedinica.dajLokacije()) {
                Lokacija lokacija = (Lokacija) lok;
                ukupnoRomobil += racuni.vratiUkupnoTrajanjeNajmova(lokacija.getId(), 1, datumPocetni, datumDrugi);
                ukupnoBicikl += racuni.vratiUkupnoTrajanjeNajmova(lokacija.getId(), 2, datumPocetni, datumDrugi);
                ukupnoSkuter += racuni.vratiUkupnoTrajanjeNajmova(lokacija.getId(), 3, datumPocetni, datumDrugi);
                ukupnoAuto += racuni.vratiUkupnoTrajanjeNajmova(lokacija.getId(), 4, datumPocetni, datumDrugi);
            }
        }
        kumuliraneVrijednosti[0] = ukupnoRomobil;
        kumuliraneVrijednosti[1] = ukupnoBicikl;
        kumuliraneVrijednosti[2] = ukupnoSkuter;
        kumuliraneVrijednosti[3] = ukupnoAuto;
        return kumuliraneVrijednosti;

    }

    /**
     * Metoda koja vrsi kalkulacije ukupne zarade po vrstama vozila po
     * lokacijama organizacijske jedninice
     *
     * @param oj
     * @return polje sa vrijednostima 0-romobil, 1-bicikl, 2-skuter, 3-auto
     */
    private double[] UkupnoZaradaPoVrsti(OrganizacijskaJedinica oj, String datumPocetni, String datumDrugi) {
        double[] kumuliraneVrijednosti = new double[4];
        ListaRacuna racuni = ListaRacuna.getInstanca();
        double ukupnoRomobil = 0;
        double ukupnoBicikl = 0;
        double ukupnoSkuter = 0;
        double ukupnoAuto = 0;
        TvrtkaIterator iterator = new TvrtkaIterator(oj);
        while (iterator.hasNext()) {
            OrganizacijskaJedinica jedinica = (OrganizacijskaJedinica) iterator.next();
            for (Tvrtka lok : jedinica.dajLokacije()) {
                Lokacija lokacija = (Lokacija) lok;
                ukupnoRomobil += racuni.vratiZaradu(lokacija.getId(), 1, datumPocetni, datumDrugi);
                ukupnoBicikl += racuni.vratiZaradu(lokacija.getId(), 2, datumPocetni, datumDrugi);
                ukupnoSkuter += racuni.vratiZaradu(lokacija.getId(), 3, datumPocetni, datumDrugi);
                ukupnoAuto += racuni.vratiZaradu(lokacija.getId(), 4, datumPocetni, datumDrugi);
            }
        }
        kumuliraneVrijednosti[0] = ukupnoRomobil;
        kumuliraneVrijednosti[1] = ukupnoBicikl;
        kumuliraneVrijednosti[2] = ukupnoSkuter;
        kumuliraneVrijednosti[3] = ukupnoAuto;
        return kumuliraneVrijednosti;

    }

    public OrganizacijskaJedinica pronadiPodredenu(int id) {
        TvrtkaIterator iterator = new TvrtkaIterator(this);
        while (iterator.hasNext()) {
            OrganizacijskaJedinica jedinica = (OrganizacijskaJedinica) iterator.next();
            if (jedinica.id == id) {
                return jedinica;
            }
        }
        return null;
    }

    @Override
    public String ispisiStrukturu() {
        TvrtkaIterator iterator = new TvrtkaIterator(this);
        String ispisNadredena="";
        while (iterator.hasNext()) {
            OrganizacijskaJedinica jedinica = (OrganizacijskaJedinica) iterator.next();
            OrganizacijskaJedinica nadredena=jedinica.dajNadredenu(this);
            if(nadredena!=null) ispisNadredena=" - dijete od: "+nadredena.naziv;
            System.out.println("-------------------");
            System.out.println("(" + jedinica.id + ") " + jedinica.naziv+ispisNadredena);
            System.out.println("Lokacije:");
            List<Tvrtka> lokacije=jedinica.dajLokacije();
            if(lokacije.isEmpty()) System.out.println("-Nema lokacija!");
            else{
            for (Tvrtka lok : lokacije) {
                System.out.println(lok.ispisiStrukturu());
            }}
        }
        return "";
    }

    @Override
    public String ispisiStanje() {
        dodijeliVrijednosti();
        System.out.println("(" + this.id + ") " + this.naziv);
        Integer[] poljeMjesta = UkupnoMjestaPoVrsti(this);
        Integer[] poljeRasp = UkupnoRaspolozivoPoVrsti(this);
        Integer[] poljeNeisp = UkupnoNeispravnoPoVrsti(this);
        System.out.println("\nKumulirani podaci stanja:");
        System.out.println("-------------------");
        System.out.format("|%-"+dt+"s|%"+dc+"s|%"+dc+"s|%"+dc+"s|%"+dc+"s| %n","Vrsta","1","2","3","4");
        System.out.format("|%-"+dt+"s|%"+dc+"d|%"+dc+"d|%"+dc+"d|%"+dc+"d| %n","Mjesta" , poljeMjesta[0] , poljeMjesta[1] , poljeMjesta[2], poljeMjesta[3]);
        System.out.format("|%-"+dt+"s|%"+dc+"d|%"+dc+"d|%"+dc+"d|%"+dc+"d| %n","Raspolozivo" , poljeRasp[0] , poljeRasp[1] , poljeRasp[2], poljeRasp[3]);
        System.out.format("|%-"+dt+"s|%"+dc+"d|%"+dc+"d|%"+dc+"d|%"+dc+"d| %n","Neispravno" , poljeNeisp[0] , poljeNeisp[1] , poljeNeisp[2], poljeNeisp[3]);
        System.out.println("----------------------");
        System.out.println(" *Legenda: 1-romobil, 2-bicikl, 3-skuter, 4-auto\n");
        return "";
    }

    @Override
    public String ispisiStrukturuStanje() {
        TvrtkaIterator iterator = new TvrtkaIterator(this);
        while (iterator.hasNext()) {
            OrganizacijskaJedinica jedinica = (OrganizacijskaJedinica) iterator.next();
            OrganizacijskaJedinica nadredena=jedinica.dajNadredenu(this);
            if(nadredena!=null)System.out.println("Nadredena: "+nadredena.naziv);
            jedinica.ispisiStanje();
            System.out.println("Lokacije ("+jedinica.naziv+"):\n");
            for (Tvrtka lok : jedinica.dajLokacije()) {
                System.out.println(lok.ispisiStanje());
            }
        }
        return "";
    }

    @Override
    public String ispisiZaradu(String datumPocetni, String datumDrugi) {
        dodijeliVrijednosti();
        System.out.println("(" + this.id + ") " + this.naziv);
        double[] poljeZarada = UkupnoZaradaPoVrsti(this, datumPocetni, datumDrugi);
        System.out.println("\nKumulirani podaci zarade:");
        System.out.println("-------------------");
        System.out.format("|%-"+dt+"s|%"+dc+"s|%"+dc+"s|%"+dc+"s|%"+dc+"s| %n","Vrsta","1","2","3","4");
        System.out.format("|%-"+dt+"s|%"+dc+"."+dd+"f|%"+dc+"."+dd+"f|%"+dc+"."+dd+"f|%"+dc+"."+dd+"f| %n","Zarada" , poljeZarada[0] , poljeZarada[1] , poljeZarada[2], poljeZarada[3]);
        System.out.println("----------------------");
        System.out.println(" *Legenda: 1-romobil, 2-bicikl, 3-skuter, 4-auto\n");
        return "";
    }

    @Override
    public String ispisiNajmove(String datumPocetni, String datumDrugi) {
        dodijeliVrijednosti();
        System.out.println("(" + this.id + ") " + this.naziv);
        Integer[] poljeNajmova = UkupnoNajmovaPoVrsti(this, datumPocetni, datumDrugi);
        Long[] poljeTrajanja = UkupnoTrajanjeNajmovaPoVrsti(this, datumPocetni, datumDrugi);
        System.out.println("\nKumulirani podaci najmova:");
        System.out.println("-------------------");
        System.out.format("|%-"+dt+"s|%"+dc+"s|%"+dc+"s|%"+dc+"s|%"+dc+"s| %n","Vrsta","1","2","3","4");
        System.out.format("|%-"+dt+"s|%"+dc+"d|%"+dc+"d|%"+dc+"d|%"+dc+"d| %n","Br. najmova" , poljeNajmova[0] , poljeNajmova[1] , poljeNajmova[2], poljeNajmova[3]);
        System.out.format("|%-"+dt+"s|%"+dc+"d|%"+dc+"d|%"+dc+"d|%"+dc+"d| %n","Tr. najmova" , poljeTrajanja[0] , poljeTrajanja[1] , poljeTrajanja[2], poljeTrajanja[3]);
        System.out.println("----------------------");
        System.out.println(" *Legenda: 1-romobil, 2-bicikl, 3-skuter, 4-auto\n");
        return "";
    }

    @Override
    public String ispisiStrukturuZarade(String datumPocetni, String datumDrugi) {
        TvrtkaIterator iterator = new TvrtkaIterator(this);
        while (iterator.hasNext()) {
            OrganizacijskaJedinica jedinica = (OrganizacijskaJedinica) iterator.next();
            OrganizacijskaJedinica nadredena=jedinica.dajNadredenu(this);
            if(nadredena!=null)System.out.println("Nadredena: "+nadredena.naziv);
            jedinica.ispisiZaradu(datumPocetni, datumDrugi);
            System.out.println("Lokacije ("+jedinica.naziv+"):\n");
            for (Tvrtka lok : jedinica.dajLokacije()) {
                System.out.println(lok.ispisiZaradu(datumPocetni, datumDrugi));
            }
        }
        return "";
    }

    @Override
    public String ispisiStrukturuNajmova(String datumPocetni, String datumDrugi) {
        TvrtkaIterator iterator = new TvrtkaIterator(this);
        while (iterator.hasNext()) {
            OrganizacijskaJedinica jedinica = (OrganizacijskaJedinica) iterator.next();
            OrganizacijskaJedinica nadredena=jedinica.dajNadredenu(this);
            if(nadredena!=null)System.out.println("Nadredena: "+nadredena.naziv);
            jedinica.ispisiNajmove(datumPocetni, datumDrugi);
            System.out.println("Lokacije ("+jedinica.naziv+"):\n");
            for (Tvrtka lok : jedinica.dajLokacije()) {
                System.out.println(lok.ispisiNajmove(datumPocetni, datumDrugi));
            }
        }
        return "";
    }

    @Override
    public String ispisiStrukturuZaradeINajmova(String datumPocetni, String datumDrugi) {
        TvrtkaIterator iterator = new TvrtkaIterator(this);
        while (iterator.hasNext()) {
            OrganizacijskaJedinica jedinica = (OrganizacijskaJedinica) iterator.next();
            OrganizacijskaJedinica nadredena=jedinica.dajNadredenu(this);
            if(nadredena!=null)System.out.println("Nadredena: "+nadredena.naziv);
            jedinica.ispisiZaraduINajmove(datumPocetni, datumDrugi);
            System.out.println("Lokacije ("+jedinica.naziv+"):\n");
            for (Tvrtka lok : jedinica.dajLokacije()) {
                System.out.println(lok.ispisiZaraduINajmove(datumPocetni, datumDrugi));
            }
        }
        return "";
    }

    @Override
    public String ispisiZaraduINajmove(String datumPocetni, String datumDrugi) {
        this.ispisiNajmove(datumPocetni, datumDrugi);
        this.ispisiZaradu(datumPocetni, datumDrugi);
        return "";
    }

    @Override
    public String ispisiRacune(String datumPocetni, String datumDrugi) {
       dodijeliVrijednosti();
        ListaRacuna racuni= ListaRacuna.getInstanca();
        List<Racun> listaRacuni=new ArrayList<>();
        TvrtkaIterator iterator = new TvrtkaIterator(this);
        while (iterator.hasNext()) {
            OrganizacijskaJedinica jedinica = (OrganizacijskaJedinica) iterator.next();
        for (Tvrtka lok : jedinica.dajLokacije()) {
            Lokacija lokacija= (Lokacija)lok;
            listaRacuni.addAll(racuni.racuniLokacija(lokacija.getId(),datumPocetni,datumDrugi));
            }}
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

    @Override
    public String ispisiStrukturuRacuna(String datumPocetni, String datumDrugi) {
        TvrtkaIterator iterator = new TvrtkaIterator(this);
        String ispisNadredena="";
        while (iterator.hasNext()) {
            OrganizacijskaJedinica jedinica = (OrganizacijskaJedinica) iterator.next();
            OrganizacijskaJedinica nadredena=jedinica.dajNadredenu(this);
            if(nadredena!=null) ispisNadredena=" - dijete od: "+nadredena.naziv;
            System.out.println("-------------------");
            System.out.println("(" + jedinica.id + ") " + jedinica.naziv+ispisNadredena+"\n");
            System.out.println("Lokacije ("+jedinica.naziv+"):\n");
            for (Tvrtka lok : jedinica.dajLokacije()) {
                System.out.println(lok.ispisiRacune(datumPocetni, datumDrugi));
            }
        }
        return"";
    }
}
