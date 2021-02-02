/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vbencek.items;

import java.util.List;

/**
 * Klasa izrađena po Builder uzorku dizajna.
 * Instanca klase je objekt osobe.
 * @author vbencek
 */
public class Osoba {
   private int id;

    private String imePrezime;
    private boolean ugovor; //true postoji, false ne postoji
    private double dugovanje;
    private String vrijemeZadnjegNajma;
    private int brojVracenihNeispravnih;
    private List<VoziloLokacije> vozilaUNajmu;

    public int getId() {
        return id;
    }

    public String getImePrezime() {
        return imePrezime;
    }
    
    public int getBrojVracenihNeispravnih(){
        return brojVracenihNeispravnih;
    }
    
    public List<VoziloLokacije> getVozilaUNajmu(){
        return vozilaUNajmu;
    }

    public boolean isUgovor() {
        return ugovor;
    }
    
    public double getDugovanje(){
        return dugovanje;
    }

    public String getVrijemeZadnjegNajma() {
        return vrijemeZadnjegNajma;
    }
    
    private Osoba(OsobaBuilder builder) {
            this.id=builder.id;
            this.imePrezime=builder.imePrezime;
            this.vozilaUNajmu =builder.vozilaUNajmu;
            this.brojVracenihNeispravnih=builder.brojVracenihNeispravnih;
            this.ugovor=builder.ugovor;
            this.dugovanje=builder.dugovanje;
            this.vrijemeZadnjegNajma=builder.vrijemeZadnjegNajma;
    }

    public static class OsobaBuilder {

        private int id;
        private String imePrezime;
        private boolean ugovor;
        private double dugovanje;
        private String vrijemeZadnjegNajma;
        private int brojVracenihNeispravnih;
        private List<VoziloLokacije> vozilaUNajmu;
        
        public OsobaBuilder(int id){
            this.id=id;
        }
        public OsobaBuilder(Osoba osoba){
            this.id=osoba.id;
            this.imePrezime=osoba.getImePrezime();
            this.vozilaUNajmu=osoba.getVozilaUNajmu();
            this.ugovor=osoba.isUgovor();
            this.dugovanje=osoba.getDugovanje();
            this.vrijemeZadnjegNajma=osoba.getVrijemeZadnjegNajma();
        }
        
        public OsobaBuilder setImePrezime(String imePrezime){
            this.imePrezime=imePrezime;
            return this;
        }
        
        public OsobaBuilder setVozilaUNajmu(List<VoziloLokacije> vozila){
            this.vozilaUNajmu=vozila;
            return this;
        }
        public OsobaBuilder setBrojVracenihNeispravnih(int brojVracenihNeispravnih){
            this.brojVracenihNeispravnih=brojVracenihNeispravnih;
            return this;
        }
        public OsobaBuilder setUgovor(boolean ugovor){
            this.ugovor=ugovor;
            return this;
        }
        public OsobaBuilder setDugovanje(double dugovanje){
            this.dugovanje=dugovanje;
            return this;
        }
        public OsobaBuilder setVrijemeZadnjegNajma(String vrijemeZadnjegNajma) {
            this.vrijemeZadnjegNajma = vrijemeZadnjegNajma;
            return this;
        }
        public Osoba build(){
            return new Osoba(this);
        }

        
        
    } 
}
