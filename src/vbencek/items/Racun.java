package vbencek.items;

import vbencek.Sustav;

public class Racun {

    private int id;
    private int korisnik;
    private int lokacijaNajma;
    private int lokacijaVracanja;
    private int vrstaVozila;
    private long trajanjeNajma;
    private int prijedeniKm;
    private String vrijemeIzdavanja;
    private String vrijemePodmirenja;
    private double iznos;
    private boolean placen;

    public Racun() {
        id = Sustav.getInstanca().generateIdRacuna();
        lokacijaVracanja = 0;
        trajanjeNajma = 0;
        prijedeniKm = 0;
        vrijemePodmirenja = "";
        placen=false;
    }

    public Racun(Racun racun, int lokacijaVracanja, long trajanjeNajma, int prijedeniKm, String vrijemePodmirenja, double iznos,boolean placen) {
        this.id = racun.id;
        this.korisnik = racun.korisnik;
        this.lokacijaNajma = racun.lokacijaNajma;
        this.vrstaVozila = racun.vrstaVozila;
        this.vrijemeIzdavanja = racun.vrijemeIzdavanja;
        this.lokacijaVracanja = lokacijaVracanja;
        this.trajanjeNajma = trajanjeNajma;
        this.prijedeniKm = prijedeniKm;
        this.vrijemePodmirenja = vrijemePodmirenja;
        this.iznos=iznos;
        this.placen=placen;

    }

    public void setKorisnik(int korisnik) {
        this.korisnik = korisnik;
    }

    public void setLokacijaNajma(int lokacijaNajma) {
        this.lokacijaNajma = lokacijaNajma;
    }

    public void setLokacijaVracanja(int lokacijaVracanja) {
        this.lokacijaVracanja = lokacijaVracanja;
    }

    public void setVrstaVozila(int vrstaVozila) {
        this.vrstaVozila = vrstaVozila;
    }

    public void setTrajanjeNajma(long trajanjeNajma) {
        this.trajanjeNajma = trajanjeNajma;
    }

    public void setPrijedeniKm(int prijedeniKm) {
        this.prijedeniKm = prijedeniKm;
    }

    public void setVrijemeIzdavanja(String vrijemeIzdavanja) {
        this.vrijemeIzdavanja = vrijemeIzdavanja;
    }

    public void setVrijemePodmirenja(String vrijemePodmirenja) {
        this.vrijemePodmirenja = vrijemePodmirenja;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public void setPlacen(boolean placen) {
        this.placen = placen;
    }
    

    public int getId() {
        return id;
    }

    public int getKorisnik() {
        return korisnik;
    }

    public int getLokacijaNajma() {
        return lokacijaNajma;
    }

    public int getLokacijaVracanja() {
        return lokacijaVracanja;
    }

    public int getVrstaVozila() {
        return vrstaVozila;
    }

    public long getTrajanjeNajma() {
        return trajanjeNajma;
    }

    public int getPrijedeniKm() {
        return prijedeniKm;
    }

    public String getVrijemeIzdavanja() {
        return vrijemeIzdavanja;
    }

    public String getVrijemePodmirenja() {
        return vrijemePodmirenja;
    }

    public double getIznos() {
        return iznos;
    }

    public boolean isPlacen() {
        return placen;
    }
    

}
