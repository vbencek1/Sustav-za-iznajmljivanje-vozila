package vbencek.items;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa izrađena po Builder uzorku dizajna. Instanca klase je objekt kapacitet
 * pojednine lokacije.
 *
 * @author vbencek
 */
public class LokacijaKapacitet {

    private int vozilo;
    private int lokacija;
    private int brojMjesta;
    private int brojVozila;
    private int brojNeispravnih;
    /**
     * lista vozila na lokaciji
     */
    private List<VoziloLokacije> vozilaLokacije = new ArrayList<>();

    public int getVozilo() {
        return vozilo;
    }

    public int getLokacija() {
        return lokacija;
    }

    public int getBrojMjesta() {
        return brojMjesta;
    }

    public int getBrojVozila() {
        return brojVozila;
    }

    public int getBrojNeispravnih() {
        return brojNeispravnih;
    }

    public List<VoziloLokacije> getVozilaLokacije() {
        return vozilaLokacije;
    }

    public void ispisiVozilaLokacije() {
        System.out.println("Dostupna vozila odabrane vrste na lokaciji: ");
        System.out.println("ID - Vrsta - Vrijeme pune baterije - Ukupni km - Stanje - Broj najmova");
        System.out.println("--------------------------------------------");
        for (VoziloLokacije vl : vozilaLokacije) {
            System.out.println(vl.toString());
        }
    }

    private LokacijaKapacitet(LokacijaKapacitetBuilder builder) {
        this.vozilo = builder.vozilo;
        this.lokacija = builder.lokacija;
        this.brojMjesta = builder.brojMjesta;
        this.brojVozila = builder.brojVozila;
        this.vozilaLokacije = builder.vozilaLokacije;
        this.brojNeispravnih = builder.brojNeispravnih;
    }

    public static class LokacijaKapacitetBuilder {

        private int vozilo;
        private int lokacija;
        private int brojMjesta;
        private int brojVozila;
        private int brojNeispravnih;
        private List<VoziloLokacije> vozilaLokacije = new ArrayList<>();

        public LokacijaKapacitetBuilder(int vozilo, int lokacija) {
            this.vozilo = vozilo;
            this.lokacija = lokacija;
        }

        public LokacijaKapacitetBuilder(LokacijaKapacitet lk) {
            this.vozilo = lk.vozilo;
            this.lokacija = lk.lokacija;
            this.brojMjesta = lk.brojMjesta;
            this.brojVozila = lk.brojVozila;
            this.vozilaLokacije = lk.vozilaLokacije;
            this.brojNeispravnih = lk.brojNeispravnih;
        }

        public LokacijaKapacitetBuilder setBrojMjesta(int brojMjesta) {
            this.brojMjesta = brojMjesta;
            return this;
        }

        public LokacijaKapacitetBuilder setBrojVozila(int brojVozila) {
            this.brojVozila = brojVozila;
            return this;
        }

        public LokacijaKapacitetBuilder setBrojNeispravnih(int brojNeispravnih) {
            this.brojNeispravnih = brojNeispravnih;
            return this;
        }

        public LokacijaKapacitetBuilder setVozilaLokacije(List<VoziloLokacije> vozilaLokacije) {
            this.vozilaLokacije = vozilaLokacije;
            return this;
        }

        public LokacijaKapacitet build() {
            return new LokacijaKapacitet(this);
        }

    }
}
