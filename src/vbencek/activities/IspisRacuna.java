package vbencek.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vbencek.Sustav;
import vbencek.composite.OrganizacijskaJedinica;


/**
 * Concrete class odnosno ConcreteProduct u Factory method uzorku dizajna.
 * Poziva se kad korisnik želi ispisati račune tvrtke ili račune po lokacijama
 * @author vbencek
 */
public class IspisRacuna implements Aktivnost{
    
    private boolean provjeri(String ulaz) {
        String datum = "((0[1-9]|[12][0-9]|3[01]).(1[012]|0[1-9]).(19|20)\\d\\d)";
        String sintaksa = "^8;(?:((struktura|racuni))(?!\\1))+"+datum+datum+"(|[0-9]+)";
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(ulaz.replaceAll(" ", ""));
        boolean status = m.matches();
        if (!status) {
            System.out.println(" -Neispravna naredba!");
        }
        return status;
    }
    
    private void generirajStrukturu(String komanda, OrganizacijskaJedinica root) {
        String[] polje = komanda.split(" ");
        OrganizacijskaJedinica jedinica;
        int idOJ = 0;
        if (polje.length == 4) {
            idOJ = Integer.parseInt(polje[3]);
        }
        if (idOJ == 0) {
            root.ispisiStrukturu();
        } else {
            jedinica = root.pronadiPodredenu(idOJ);
            if (jedinica != null) {
                jedinica.ispisiStrukturu();
            } else {
                System.out.println("Ne postoji organizacijska jedinica sa Id: " + idOJ);
            }
        }
    }
    
    private void generirajStrukturuRacuni(String komanda, OrganizacijskaJedinica root){
        String[] polje = komanda.split(" ");
        String datumPrvi=polje[2];
        String datumDrugi=polje[3];
        System.out.println("Datum od: "+datumPrvi+" Datum do: "+datumDrugi+"\n");
        OrganizacijskaJedinica jedinica;
        int idOJ = 0;
        if (polje.length == 5) {
            idOJ = Integer.parseInt(polje[4]);
        }
        if (idOJ == 0) {
            root.ispisiStrukturuRacuna(datumPrvi, datumDrugi);
        } else {
            jedinica = root.pronadiPodredenu(idOJ);
            if (jedinica != null) {
                jedinica.ispisiStrukturuRacuna(datumPrvi, datumDrugi);
            } else {
                System.out.println("Ne postoji organizacijska jedinica sa Id: " + idOJ);
            }
        }
    }
    private void generirajRacune(String komanda, OrganizacijskaJedinica root){
        String[] polje = komanda.split(" ");
        String datumPrvi=polje[1];
        String datumDrugi=polje[2];
        System.out.println("Datum od: "+datumPrvi+" Datum do: "+datumDrugi+"\n");
        OrganizacijskaJedinica jedinica;
        int idOJ = 0;
        if (polje.length == 4) {
            idOJ = Integer.parseInt(polje[3]);
        }
        if (idOJ == 0) {
            root.ispisiRacune(datumPrvi, datumDrugi);
        } else {
            jedinica = root.pronadiPodredenu(idOJ);
            if (jedinica != null) {
                jedinica.ispisiRacune(datumPrvi, datumDrugi);
            } else {
                System.out.println("Ne postoji organizacijska jedinica sa Id: " + idOJ);
            }
        }
    }
    
    @Override
    public void izvrsi(String operacija) {
        if (provjeri(operacija)) {
            String komanda = operacija.split(";")[1].trim();
            OrganizacijskaJedinica root = Sustav.getInstanca().getTvrtka();
            if (komanda.contains("struktura") && komanda.contains("racuni")) {
                System.out.println("Pregled strukture i podmirenih računa\n");
                generirajStrukturuRacuni(komanda, root);
            } else if (!komanda.contains("struktura") && komanda.contains("racuni")) {
                System.out.println("Pregled podmirenih računa\n");
                generirajRacune(komanda, root);
            } else if (komanda.contains("struktura") && !komanda.contains("racuni")) {
                System.out.println("Pregled strukture\n");
                generirajStrukturu(komanda, root);
            }
        }
    }
    
}
