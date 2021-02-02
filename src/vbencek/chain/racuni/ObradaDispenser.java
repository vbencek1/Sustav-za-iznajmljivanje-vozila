package vbencek.chain.racuni;

import java.util.List;
import vbencek.items.ListaRacuna;
import vbencek.items.Racun;

public class ObradaDispenser implements RacuniChain {

    private RacuniChain chain;

    @Override
    public void setNextChain(RacuniChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public Racun dispense(PodaciRacuna podaci) {
        if (podaci.getOpcija() == 1) {
            List<Racun> listaRacuna = ListaRacuna.getInstanca().getListaRacuna();
            Racun racunZaObradit = null;
            for (Racun rac : listaRacuna) {
                if (rac.getKorisnik() == podaci.getKorisnik() && rac.getVrstaVozila() == podaci.getVrstaVozila() && "".equals(rac.getVrijemePodmirenja())) {
                    racunZaObradit = rac;
                }
            }
            Racun azuriranRacun = new Racun(racunZaObradit, podaci.getLokacijaVracanja(), podaci.getTrajanjeNajma(),
                    podaci.getPrijedeniKm(), podaci.getVrijemeVracanja(), podaci.getZarada(), podaci.isPlacen());
            return azuriranRacun;
        } else {
            return this.chain.dispense(podaci);
        }
    }

}
