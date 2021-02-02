package vbencek.chain.racuni;


public class PodaciRacuna {
    private int opcija; //0-kreiranje 1-obrada 2-pretraga
    private int korisnik;
    private int lokacijaPosudbe;
    private int vrstaVozila;
    private String vrijemePosudbe;
    private int lokacijaVracanja;
    private long trajanjeNajma;
    private int prijedeniKm;
    private String vrijemeVracanja;
    private double zarada;
    private boolean placen; // 0-ne 1-da
    private int idRacuna;
    private String odVrijeme;
    private String doVrijeme;

    public void setOpcija(int opcija) {
        this.opcija = opcija;
    }

    public void setKorisnik(int korisnik) {
        this.korisnik = korisnik;
    }

    public void setLokacijaPosudbe(int lokacijaPosudbe) {
        this.lokacijaPosudbe = lokacijaPosudbe;
    }

    public void setVrstaVozila(int vrstaVozila) {
        this.vrstaVozila = vrstaVozila;
    }

    public void setVrijemePosudbe(String vrijemePosudbe) {
        this.vrijemePosudbe = vrijemePosudbe;
    }

    public void setLokacijaVracanja(int lokacijaVracanja) {
        this.lokacijaVracanja = lokacijaVracanja;
    }

    public void setTrajanjeNajma(long trajanjeNajma) {
        this.trajanjeNajma = trajanjeNajma;
    }

    public void setPrijedeniKm(int prijedeniKm) {
        this.prijedeniKm = prijedeniKm;
    }

    public void setVrijemeVracanja(String vrijemeVracanja) {
        this.vrijemeVracanja = vrijemeVracanja;
    }

    public void setZarada(double zarada) {
        this.zarada = zarada;
    }

    public void setPlacen(boolean placen) {
        this.placen = placen;
    }

    public int getIdRacuna() {
        return idRacuna;
    }

    public void setIdRacuna(int idRacuna) {
        this.idRacuna = idRacuna;
    }

    public String getOdVrijeme() {
        return odVrijeme;
    }

    public void setOdVrijeme(String odVrijeme) {
        this.odVrijeme = odVrijeme;
    }

    public String getDoVrijeme() {
        return doVrijeme;
    }

    public void setDoVrijeme(String doVrijeme) {
        this.doVrijeme = doVrijeme;
    }

    
    
    public int getOpcija() {
        return opcija;
    }

    public int getKorisnik() {
        return korisnik;
    }

    public int getLokacijaPosudbe() {
        return lokacijaPosudbe;
    }

    public int getVrstaVozila() {
        return vrstaVozila;
    }

    public String getVrijemePosudbe() {
        return vrijemePosudbe;
    }

    public int getLokacijaVracanja() {
        return lokacijaVracanja;
    }

    public long getTrajanjeNajma() {
        return trajanjeNajma;
    }

    public int getPrijedeniKm() {
        return prijedeniKm;
    }

    public String getVrijemeVracanja() {
        return vrijemeVracanja;
    }

    public double getZarada() {
        return zarada;
    }

    public boolean isPlacen() {
        return placen;
    }
    
    
    
    
}
