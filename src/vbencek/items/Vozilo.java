package vbencek.items;

/**
 * Klasa izrađena po Builder uzorku dizajna.
 * Instanca klase je objekt vozila.
 * Zapravo definira vrstu vozila
 * @author vbencek
 */
public class Vozilo {

    private int id;

    private String naziv;
    private int vrijemePunjenja;
    private int domet;

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public int getVrijemePunjenja() {
        return vrijemePunjenja;
    }

    public int getDomet() {
        return domet;
    }

    private Vozilo(VoziloBuilder builder) {
            this.id=builder.id;
            this.naziv=builder.naziv;
            this.vrijemePunjenja=builder.vrijemePunjenja;
            this.domet=builder.domet;
    }

    public static class VoziloBuilder {

        private int id;
        private String naziv;
        private int vrijemePunjenja;
        private int domet;
        
        public VoziloBuilder(int id){
            this.id=id;
        }
        
        public VoziloBuilder setNaziv(String naziv){
            this.naziv=naziv;
            return this;
        }
        public VoziloBuilder setVrijemePunjenja(int vrijemePunjenja){
            this.vrijemePunjenja=vrijemePunjenja;
            return this;
        }
        public VoziloBuilder setDomet(int domet){
            this.domet=domet;
            return this;
        }
        
        public Vozilo build(){
            return new Vozilo(this);
        }
    }
}
