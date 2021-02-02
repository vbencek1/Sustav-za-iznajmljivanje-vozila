package vbencek.chain.vozila;

/**
 * Klasa koja instancira klase lanca preko sučelja DispenseChain
 * Postavlja se i kretanje po lancu
 * @author vbencek
 */
public class VozilaDispenseChain {
    public DispenseChain c1;

    public VozilaDispenseChain() {
        this.c1 = new NajamDispenser();
        DispenseChain c2= new KilometriDispenser();
        DispenseChain c3= new IdDispenser();
        
        c1.setNextChain(c2);
        c2.setNextChain(c3);
    }
    
    
}
