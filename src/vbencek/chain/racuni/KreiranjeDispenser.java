package vbencek.chain.racuni;

import vbencek.items.Racun;

public class KreiranjeDispenser implements RacuniChain {

    private RacuniChain chain;

    @Override
    public void setNextChain(RacuniChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public Racun dispense(PodaciRacuna podaci) {
        if (podaci.getOpcija() == 0) {
            Racun racun = new Racun();
            racun.setKorisnik(podaci.getKorisnik());
            racun.setLokacijaNajma(podaci.getLokacijaPosudbe());
            racun.setVrstaVozila(podaci.getVrstaVozila());
            racun.setVrijemeIzdavanja(podaci.getVrijemePosudbe());
            return racun;
        } else {
            return this.chain.dispense(podaci);
        }
    }

}
