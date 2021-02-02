package vbencek.chain.vozila;

import java.util.ArrayList;
import java.util.List;
import vbencek.items.VoziloLokacije;


/**
 * Concrete Handler u Chain of responsibility uzorku dizajna
 * Ova klasa je konkretno zadužena da primi listu vozila te vrati vozilo koje ima manji broj najma.
 * Ukoliko postoji više vozila sa istim brojem najma, zahtjev se dalje prosljeđuje u lanac.
 * @author vbencek
 */
public class NajamDispenser implements DispenseChain{
    
    private DispenseChain chain;
    
    @Override
    public void setNextChain(DispenseChain nextChain) {
        this.chain= nextChain;
    }

    @Override
    public VoziloLokacije dispense(List<VoziloLokacije> kandidati) {
        List<VoziloLokacije> noviKandidati=new ArrayList<>();
        int najmanjiNajam=10000000;
        for(VoziloLokacije vl: kandidati){
            if(vl.getBrojNajma()<najmanjiNajam){
                najmanjiNajam=vl.getBrojNajma();
            }
        }
        
        for(VoziloLokacije vl: kandidati){
            if(vl.getBrojNajma()==najmanjiNajam){
                noviKandidati.add(vl);
            }
        }
        if(noviKandidati.size()>1){
            return this.chain.dispense(noviKandidati);
        }else{
            return noviKandidati.get(0);
        }
        
    }
    
}
