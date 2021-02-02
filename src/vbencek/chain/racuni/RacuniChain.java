package vbencek.chain.racuni;

import vbencek.items.Racun;

public interface RacuniChain {
    
    void setNextChain(RacuniChain nextChain);
    Racun dispense(PodaciRacuna podaci);
}
