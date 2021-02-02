package vbencek.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vbencek.items.ListaCjenika;
import vbencek.items.ListaLokacijeKapaciteti;
import vbencek.items.ListaOsoba;
import vbencek.items.ListaVozila;
import vbencek.items.Vozilo;
import vbencek.items.VoziloLokacije;
import vbencek.Sustav;
import vbencek.items.ListaRacuna;
import vbencek.items.Osoba;
import vbencek.state.NeispravnoStanje;
import vbencek.state.PunjenjeStanje;

/**
 * Concrete class odnosno ConcreteProduct u Factory method uzorku dizajna.
 * Poziva se kada korisnik želi vratiti unajmljeno vozilo. Nakon vraćanja vozilo
 * se stavlja na punjenje i korisniku se ispisuje račun.
 *
 * @author vbencek
 */
public class VracanjeVozila implements Aktivnost {

    /**
     * Metoda koja provjerava ispravnost vremenskog slijeda aktivnosti. Svaka
     * aktivnost koja slijedi mora imati veće vrijeme od prijašnje aktivnosti.
     * Prva aktivnost mora imati veće vrijeme od vremena sustava koje je zadano
     * preko argumenta. Postavlja vrijeme sustava na vrijeme aktivnosti ukoliko
     * je vrijeme ispravno
     *
     * @param vrijemeUlazno
     * @return vraća true ako je sve u redu, inaće vraća false
     */
    private static boolean provjeriIspravnoVrijeme(String vrijemeUlazno) {
        Date vrijemeSustava = Sustav.getInstanca().getVrijeme();
        String strVrijeme = vrijemeUlazno
                .replace("„", "")
                .replace("“", "")
                .replace("\"", "")
                .replaceAll("\\p{So}", "")
                .trim();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = formatter.parse(strVrijeme);
            if (date.after(vrijemeSustava)) {
                Sustav.getInstanca().setVrijeme(date);
                return true;
            } else {
                System.out.println(" -Vrijeme mora biti vece od: " + vrijemeSustava);
            }
        } catch (ParseException ex) {
            System.out.println(" -Neispravan format vremena");
        }

        return false;

    }

    private boolean provjeri(String ulaz) {  
        String sintaksa = "^(([4]); (|.)(19|20)\\d\\d-(1[012]|0[1-9])-(0[1-9]|[12][0-9]|3[01])"
                + " ([01]\\d|2[0123]):([012345]\\d):([012345]\\d)(|.);"
                + " [0123456789]+; [0123456789]+; [0123456789]+; [0123456789]+(|;.+))$";

        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(ulaz);
        boolean status = m.matches();
        if (!status) {
            System.out.println(" -Neispravna naredba!");
        }
        return status;
    }
    
    private boolean isInteger(String broj){
        try {
            if (Integer.parseInt(broj) < 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    private String formirajVrijeme(String vrijeme){
        String str=vrijeme;
        if(!isInteger(String.valueOf(vrijeme.charAt(0)))){
            str=vrijeme.substring(1);
        }
        if(!isInteger(String.valueOf(vrijeme.charAt(vrijeme.length()-1)))){
            str=str.substring(0,str.length()-1);
        }
        return str;
    }

    /**
     * Metoda koja vraća broj sati najma vozila
     *
     * @param vrijemeNajma vrijeme kada je korisnik unajmio vozilo
     * @return broj sati koje je vozilo bilo u najmu
     */
    private long razlikaUVremenu(Date vrijemeNajma) {
        Date trenutno = Sustav.getInstanca().getVrijeme();
        long razlikaUMiliSek = Math.abs(trenutno.getTime() - vrijemeNajma.getTime());
        long razlikaUSatima = TimeUnit.HOURS.convert(razlikaUMiliSek, TimeUnit.MILLISECONDS);
        if (((double) razlikaUMiliSek / 3600000) != razlikaUSatima) {
            razlikaUSatima = razlikaUSatima + 1;
        }
        return razlikaUSatima;
    }

    /**
     * Metoda koja obraduje vozilo koje je korisnik vratio. Pridruzuje mu
     * vrijeme punjenja te iračunava ukupno prijeđene kilometre
     *
     * @param voziloLokacije posuđeno vozilo
     * @param idVoz id vozila
     * @param prijedeniKm broj kilometara koje je vozilo prošlo
     * @return obrađeno vozilo
     */
    private VoziloLokacije obradiVozilo(VoziloLokacije voziloLokacije, int idVoz, int prijedeniKm, String problem) {
        Vozilo vozilo = ListaVozila.getInstanca().pretragaVozila(idVoz);
        int domet = vozilo.getDomet();
        int vrijemePunjenja = vozilo.getVrijemePunjenja();
        double omjer = (double) prijedeniKm / (double) domet;
        if (omjer > 1) {
            omjer = 1;
        }
        double punjenje = (vrijemePunjenja * omjer) * 3600000;
        long vrijeme = Sustav.getInstanca().getVrijeme().getTime() + (long) punjenje;

        Date itemDate = new Date(vrijeme);
        String punjenjeDo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(itemDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = formatter.parse(punjenjeDo);
            voziloLokacije.setVrijemeDoPuneBaterije(date);
        } catch (ParseException ex) {
            System.out.println(ex.toString());
        }
        voziloLokacije.setUkupniKm(prijedeniKm);
        voziloLokacije.setStanje(new PunjenjeStanje());
        if (problem != null) {
            voziloLokacije.setStanje(new NeispravnoStanje());
        }
        return voziloLokacije;

    }
    
    private void azurirajRacun(int idKor, int idVrste, int lokacijaVracanja, long trajanjeNajma, int prijedeniKm, String vrijemePodmirenja, double zarada,boolean placen){
        ListaRacuna listaRacuna= ListaRacuna.getInstanca();
        listaRacuna.azurirajRacun(idKor, idVrste, lokacijaVracanja, trajanjeNajma, prijedeniKm, vrijemePodmirenja,zarada,placen);
    }

    /**
     * Metoda koja generira cijenik. Izračunava cijenu najma te vrši ispis
     *
     * @param idKor
     * @param idVoz
     * @param brKm
     */
    private double IzracunajIIspisi(int idKor, int idVoz, int brKm, String problem, int idLok,String vrijemePodmirenja) {
        VoziloLokacije voziloUNajmu = ListaOsoba.getInstanca().dohvatiVoziloOsobe(idKor, idVoz);
        Osoba osoba= ListaOsoba.getInstanca().pretraziOsobu(idKor);
        boolean ugovor=osoba.isUgovor();
        int prethodnoKm = voziloUNajmu.getUkupniKm();
        long vrijeme = razlikaUVremenu(voziloUNajmu.getVrijemePosudbe());
        int prijedeniKm = brKm - prethodnoKm;

        double cijena = ListaCjenika.getInstanca().izracunajCijenu(idVoz, vrijeme, prijedeniKm);
        double fiksniNajam = ListaCjenika.getInstanca().pretraziCjenik(idVoz).getNajam();
        double trajanjeNajam = ListaCjenika.getInstanca().pretraziCjenik(idVoz).getPoSatu();
        double cijenaKm = ListaCjenika.getInstanca().pretraziCjenik(idVoz).getPoKm();

        System.out.println("Vracanje vozila - Stavke racuna:");
        System.out.println("--------------------------------");
        System.out.println("Fiksna cijena najma: " + fiksniNajam + " kn");
        System.out.println("Trajanje najma: " + vrijeme + " * " + trajanjeNajam + " = " + trajanjeNajam * vrijeme + " kn");
        System.out.println("Cijena prijedenih kilometara: " + prijedeniKm + " * " + cijenaKm + " = " + cijenaKm * prijedeniKm + " kn");
        System.out.println("--------------------------------");
        System.out.println("Ukupna cijena: " + cijena + " kn");
        if (problem != null) {
            System.out.println("Korisik prijavio problem: " + problem);
        }
        azurirajRacun(idKor, idVoz, idLok, vrijeme, prijedeniKm, vrijemePodmirenja,cijena,!ugovor);
        return cijena;
    }

    /**
     * Metoda koja vrši operaciju vraćanja unajmljenog vozila. Vrši provjere
     * poput postojanja vozila, raspoloživih mjesta i slično.
     *
     * @param operacija Uzlazni argumenti koje je potrebno obraditi(id vozila,
     * id lokacije, vrijeme,..)
     */
    @Override
    public void izvrsi(String operacija) {
        String vrijeme = formirajVrijeme(operacija.split(";")[1].trim());
        if (provjeri(operacija) && provjeriIspravnoVrijeme(vrijeme)) {
            int korisnik = Integer.parseInt(operacija.split(";")[2].trim());
            int lokacija = Integer.parseInt(operacija.split(";")[3].trim());
            int vozilo = Integer.parseInt(operacija.split(";")[4].trim());
            int kilometri = Integer.parseInt(operacija.split(";")[5].trim());
            Sustav.getInstanca().azurirajBaterije(lokacija, vozilo);
            String problem = null;
            if (operacija.split(";").length == 7) {
                problem = operacija.split(";")[6].trim();
            }
            if (ListaOsoba.getInstanca().imaOsobaVozilo(korisnik, vozilo)) {
                if (ListaLokacijeKapaciteti.getInstanca().vratiBrojRaspolozivihMjesta(lokacija, vozilo) > 0) {
                    if (ListaVozila.getInstanca().isKmManjiOdDomet(vozilo, kilometri)) {
                        double cijena=IzracunajIIspisi(korisnik, vozilo, kilometri, problem,lokacija,vrijeme);
                        VoziloLokacije voziloKorisnika = ListaOsoba.getInstanca().dohvatiVoziloOsobe(korisnik, vozilo);
                        VoziloLokacije vl = obradiVozilo(voziloKorisnika, vozilo, kilometri, problem);
                        ListaLokacijeKapaciteti.getInstanca().oduzmiVoziloKorisniku(lokacija, vozilo, vl,problem);
                        ListaOsoba.getInstanca().vratiVozilo(korisnik, voziloKorisnika, problem,cijena);
                    } else {
                        System.out.println("Vracanje vozila: Broj kilometara ne moze biti veci od dometa");
                    }
                } else if (ListaLokacijeKapaciteti.getInstanca().vratiBrojRaspolozivihMjesta(lokacija, vozilo) == 0) {
                    System.out.println("Vracanje vozila: Na lokaciji nema slobodnog mjesta za vozilo");
                } else {
                    System.out.println("Vracanje vozila: neispravan unos id-eva");
                }
            } else {
                System.out.println("Vracanje vozila: Osoba nema vozilo navedene vrste!");
            }
        }
    }

}
