package vbencek.items;

/**
 * Klasa izrađena po Builder uzorku dizajna.
 * Instanca klase je objekt cjenika.
 * @author vbencek
 */
public class Cjenik {

    private int id;

    private double najam;
    private double poSatu;
    private double poKm;

    public int getId() {
        return id;
    }

    public double getNajam() {
        return najam;
    }

    public double getPoSatu() {
        return poSatu;
    }

    public double getPoKm() {
        return poKm;
    }

    private Cjenik(CjenikBuilder builder) {
        this.id = builder.id;
        this.najam = builder.najam;
        this.poSatu = builder.poSatu;
        this.poKm = builder.poKm;
    }

    public static class CjenikBuilder {

        private int id;

        private double najam;
        private double poSatu;
        private double poKm;

        public CjenikBuilder(int id) {
            this.id = id;
        }

        public CjenikBuilder setNajam(double najam) {
            this.najam = najam;
            return this;
        }

        public CjenikBuilder setPoSatu(double poSatu) {
            this.poSatu = poSatu;
            return this;
        }

        public CjenikBuilder setPoKm(double poKm) {
            this.poKm = poKm;
            return this;
        }

        public Cjenik build() {
            return new Cjenik(this);
        }
    }
}
