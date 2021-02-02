package vbencek.chain.racuni;

import java.util.List;
import vbencek.items.ListaRacuna;
import vbencek.items.Racun;

public class PretragaDispenser implements RacuniChain {

    private RacuniChain chain;

    @Override
    public void setNextChain(RacuniChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public Racun dispense(PodaciRacuna podaci) {
        if (podaci.getOpcija() == 2) {
            List<Racun> listaRacuna = ListaRacuna.getInstanca().getListaRacuna();
            if (podaci.getVrstaVozila() == 0) {
                for (Racun rac : listaRacuna) {
                    if (rac.getId() == podaci.getIdRacuna()) {
                        return rac;
                    }
                }
            } else {
                for (Racun rac : listaRacuna) {
                    if (rac.getKorisnik() == podaci.getKorisnik() && rac.getVrstaVozila() == podaci.getVrstaVozila() && "".equals(rac.getVrijemePodmirenja())) {
                        return rac;
                    }
                }
                return null;
            }

            return null;
        } else {
            return this.chain.dispense(podaci);
        }
    }

}
