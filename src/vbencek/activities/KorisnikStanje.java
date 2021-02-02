package vbencek.activities;

import java.util.List;
import vbencek.Sustav;
import vbencek.items.ListaOsoba;
import vbencek.items.Osoba;

public class KorisnikStanje implements Aktivnost {

    private boolean provjeri(String ulaz) {
        String sintaksa = "9";
        if (!ulaz.equals(sintaksa)) {
            System.out.println(" -Neispravna naredba!");
            return false;
        }
        return true;
    }

    @Override
    public void izvrsi(String operacija) {
        if (provjeri(operacija)) {
            System.out.println("\nIspis financijskog stanja korisnika koji su imali najam vozila\n");
            int dt = Sustav.getInstanca().getTekst();
            int dc = Sustav.getInstanca().getCijeli();
            int dd = Sustav.getInstanca().getDecimala();
            List<Osoba> osobeLista = ListaOsoba.getInstanca().dohvatiOsobeSaNajmovima();
            System.out.format("|%" + dc + "s|%-" + dt + "s|%" + dc + "s|%-" + dt + "s| %n", "ID", "Ime i prezime", "Stanje", "Zadnji najam");
            for (Osoba o : osobeLista) {
                System.out.format("|%" + dc + "d|%-" + dt + "s|%"+dc+"."+dd+"f|%-" + dt + "s| %n", o.getId(), o.getImePrezime(), o.getDugovanje(), o.getVrijemeZadnjegNajma());
            }
            System.out.println("\n");
        }
    }

}
