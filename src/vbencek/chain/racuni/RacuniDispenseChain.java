package vbencek.chain.racuni;

public class RacuniDispenseChain {
    public RacuniChain c1;

    public RacuniDispenseChain() {
        this.c1 = new KreiranjeDispenser();
        RacuniChain c2= new ObradaDispenser();
        RacuniChain c3= new PretragaDispenser();
        
        c1.setNextChain(c2);
        c2.setNextChain(c3);
    }
}
