package vbencek.chain.vozila;

import java.util.ArrayList;
import java.util.List;
import vbencek.items.VoziloLokacije;

/**
 * Concrete Handler u Chain of responsibility uzorku dizajna
 * Ova klasa je konkretno zadužena da primi listu vozila te vrati vozilo koje ima manji ID
 * @author vbencek
 */
public class IdDispenser implements DispenseChain{
    private DispenseChain chain;
    
    @Override
    public void setNextChain(DispenseChain nextChain) {
        this.chain= nextChain;
    }

    @Override
    public VoziloLokacije dispense(List<VoziloLokacije> kandidati) {
        List<VoziloLokacije> noviKandidati=new ArrayList<>();
        int najmanjiid=999999999;
        VoziloLokacije najboljeVozilo=null;
        for(VoziloLokacije vl: kandidati){
            if(vl.getId()<najmanjiid){
                najmanjiid=vl.getId();
                najboljeVozilo=vl;
            }
        }
        noviKandidati.add(najboljeVozilo);
        if(noviKandidati.size()>1){
            return this.chain.dispense(noviKandidati);
        }
        return najboljeVozilo;
    }
}
