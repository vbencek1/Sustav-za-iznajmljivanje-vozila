package vbencek.chain.vozila;

import java.util.List;
import vbencek.items.VoziloLokacije;

/**
 * Sučelje koje predstavlja Handler u Chain of responsibility uzorku dizajna.
 * Cilj uzorka uzeti listu mogućih vozila za iznajmljivanje i dati korisniku ono najpovoljnje
 * @author vbencek
 */
public interface DispenseChain {

    void setNextChain(DispenseChain nextChain);

    VoziloLokacije dispense(List<VoziloLokacije> kandidati);
}
