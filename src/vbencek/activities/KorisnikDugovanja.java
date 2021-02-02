package vbencek.activities;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vbencek.Sustav;
import vbencek.items.ListaOsoba;
import vbencek.items.ListaRacuna;
import vbencek.items.Osoba;
import vbencek.items.Racun;

public class KorisnikDugovanja implements Aktivnost {

    int dt = 30;
    int dc = 5;
    int dd = 2;

    private boolean provjeri(String ulaz) {
        String sintaksa = "^11; [0-9]+ ([0-9]+|[0-9]+,[0-9]+)";
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(ulaz);
        boolean status = m.matches();
        if (!status) {
            System.out.println(" -Neispravna naredba!");
        }
        return status;
    }

    private double platiRacune(Osoba osoba, double iznos) {
        List<Racun> placeniRacuni = ListaRacuna.getInstanca().placanjeRacuna(osoba.getId(), iznos);
        double iznosZaVratit = iznos;
        if (placeniRacuni.isEmpty()) {
            System.out.println("Iznos: " + iznos + " nije dovoljan za placanje racuna!");
        } else {
            for (Racun rac : placeniRacuni) {
                iznosZaVratit = iznos - rac.getIznos();
                System.out.format("|%" + dc + "s|%-" + dt + "s|%-" + (dc + dd) + "s| %n", rac.getId(), rac.getVrijemeIzdavanja(), rac.getIznos());

            }
        }
        return iznosZaVratit;
    }

    @Override
    public void izvrsi(String operacija) {
        if (provjeri(operacija)) {
            int idKorisnika = Integer.parseInt(operacija.split(";")[1].trim().split(" ")[0].trim());
            Osoba osoba = ListaOsoba.getInstanca().pretraziOsobu(idKorisnika);
            if (osoba != null) {
                System.out.println("\nPlacanje dugovanja osobe: "+osoba.getImePrezime()+"\n");
                dt = Sustav.getInstanca().getTekst();
                dc = Sustav.getInstanca().getCijeli();
                dd = Sustav.getInstanca().getDecimala();
                double iznos = Double.parseDouble(operacija.split(";")[1].trim().split(" ")[1].trim().replace(",", "."));
                System.out.println("Placeni racuni:");
                System.out.format("|%" + dc + "s|%-" + dt + "s|%-" + (dc + dd) + "s| %n", "ID", "Datum", "Iznos");
                double vracenIznos=platiRacune(osoba, iznos);
                System.out.println("Osobi: "+osoba.getImePrezime()+" vraćeno je: "+vracenIznos+" Kn\n");
            } else {
                System.out.println(" -Osoba sa ID: " + idKorisnika + " ne postoji!");
            }
        }
    }

}
