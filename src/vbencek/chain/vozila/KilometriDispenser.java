package vbencek.chain.vozila;

import java.util.ArrayList;
import java.util.List;
import vbencek.items.VoziloLokacije;

/**
 * Concrete Handler u Chain of responsibility uzorku dizajna
 * Ova klasa je konkretno zadužena da primi listu vozila te vrati vozilo koje ima manji broj kilometara,
 * ako postoji više vozila s istim brojem kilometara, zahtjev se dalje prosljeđuje
 * @author vbencek
 */
public class KilometriDispenser implements DispenseChain {

    private DispenseChain chain;

    @Override
    public void setNextChain(DispenseChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public VoziloLokacije dispense(List<VoziloLokacije> kandidati) {
        List<VoziloLokacije> noviKandidati = new ArrayList<>();
        int najmanjikm = 999999999;
        for (VoziloLokacije vl : kandidati) {
            if (vl.getUkupniKm() < najmanjikm) {
                najmanjikm = vl.getUkupniKm();
            }
        }
        for (VoziloLokacije vl : kandidati) {
            if (vl.getUkupniKm() == najmanjikm) {
                noviKandidati.add(vl);
            }
        }
        if (noviKandidati.size() > 1) {
            return this.chain.dispense(noviKandidati);
        } else {
            return noviKandidati.get(0);
        }

    }

}
