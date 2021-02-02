package vbencek.items;

import java.util.ArrayList;
import java.util.List;

public class Tvrtka {
    private int id;

    private String naziv;
    private int nadredenaJednica;
    private List<Integer> lokacije=new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public int getNadredenaJednica() {
        return nadredenaJednica;
    }

    public List<Integer> getLokacije() {
        return lokacije;
    }

    private Tvrtka(TvrtkaBuilder builder) {
            this.id=builder.id;
            this.naziv=builder.naziv;
            this.nadredenaJednica=builder.nadredenaJednica;
            this.lokacije=builder.lokacije;
    }

    public static class TvrtkaBuilder {
        private int id;

        private String naziv;
        private int nadredenaJednica;
        private List<Integer> lokacije=new ArrayList<>();
        
        public TvrtkaBuilder(int id){
            this.id=id;
        }
        
        public TvrtkaBuilder setNaziv(String naziv){
            this.naziv=naziv;
            return this;
        }
        public TvrtkaBuilder setNadredenaJednica(int nadredenaJednica){
            this.nadredenaJednica=nadredenaJednica;
            return this;
        }
        public TvrtkaBuilder setLokacije(List<Integer> lokacije){
            this.lokacije=lokacije;
            return this;
        }
        
        public Tvrtka build(){
            return new Tvrtka(this);
        }
    }
}
