package vbencek.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vbencek.Sustav;
import vbencek.composite.OrganizacijskaJedinica;

/**
 * Concrete class odnosno ConcreteProduct u Factory method uzorku dizajna.
 * Poziva se kad korisnik želi prikazati strukturu tvrtke te broj najmova i zaradu od najmova po organizacijskoj jedinici
 * @author vbencek
 */
public class IspisNajamZarada implements Aktivnost {

    private boolean provjeri(String ulaz) {
        String datum = "((0[1-9]|[12][0-9]|3[01]).(1[012]|0[1-9]).(19|20)\\d\\d)";
        String sintaksa = "^7;(?:((struktura|zarada|najam))(?!\\1))+"+datum+datum+"(|[0-9]+)";
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
    
    private void generirajNajam(String komanda, OrganizacijskaJedinica root) {
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
            root.ispisiNajmove(datumPrvi, datumDrugi);
        } else {
            jedinica = root.pronadiPodredenu(idOJ);
            if (jedinica != null) {
                jedinica.ispisiNajmove(datumPrvi, datumDrugi);
            } else {
                System.out.println("Ne postoji organizacijska jedinica sa Id: " + idOJ);
            }
        }
    }
    private void generirajZaradu(String komanda, OrganizacijskaJedinica root) {
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
            root.ispisiZaradu(datumPrvi, datumDrugi);
        } else {
            jedinica = root.pronadiPodredenu(idOJ);
            if (jedinica != null) {
                jedinica.ispisiZaradu(datumPrvi, datumDrugi);
            } else {
                System.out.println("Ne postoji organizacijska jedinica sa Id: " + idOJ);
            }
        }
    }
    private void generirajStrukturuNajama(String komanda, OrganizacijskaJedinica root) {
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
            root.ispisiStrukturuNajmova(datumPrvi, datumDrugi);
        } else {
            jedinica = root.pronadiPodredenu(idOJ);
            if (jedinica != null) {
                jedinica.ispisiStrukturuNajmova(datumPrvi, datumDrugi);
            } else {
                System.out.println("Ne postoji organizacijska jedinica sa Id: " + idOJ);
            }
        }
    }
    private void generirajStrukturuZarade(String komanda, OrganizacijskaJedinica root) {
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
            root.ispisiStrukturuZarade(datumPrvi, datumDrugi);
        } else {
            jedinica = root.pronadiPodredenu(idOJ);
            if (jedinica != null) {
                jedinica.ispisiStrukturuZarade(datumPrvi, datumDrugi);
            } else {
                System.out.println("Ne postoji organizacijska jedinica sa Id: " + idOJ);
            }
        }
    }
    
    private void generirajStrukturuZaradeINajma(String komanda, OrganizacijskaJedinica root) {
        String[] polje = komanda.split(" ");
        String datumPrvi=polje[3];
        String datumDrugi=polje[4];
        System.out.println("Datum od: "+datumPrvi+" Datum do: "+datumDrugi+"\n");
        OrganizacijskaJedinica jedinica;
        int idOJ = 0;
        if (polje.length == 6) {
            idOJ = Integer.parseInt(polje[5]);
        }
        if (idOJ == 0) {
            root.ispisiStrukturuZaradeINajmova(datumPrvi, datumDrugi);
        } else {
            jedinica = root.pronadiPodredenu(idOJ);
            if (jedinica != null) {
                jedinica.ispisiStrukturuZaradeINajmova(datumPrvi, datumDrugi);
            } else {
                System.out.println("Ne postoji organizacijska jedinica sa Id: " + idOJ);
            }
        }
    }
    private void generirajZaraduINajam(String komanda, OrganizacijskaJedinica root) {
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
            root.ispisiZaraduINajmove(datumPrvi, datumDrugi);
        } else {
            jedinica = root.pronadiPodredenu(idOJ);
            if (jedinica != null) {
                jedinica.ispisiZaraduINajmove(datumPrvi, datumDrugi);
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
            if (komanda.contains("struktura") && komanda.contains("najam") && komanda.contains("zarada")) {
                System.out.println("Pregled strukture, najma i zarade\n");
                generirajStrukturuZaradeINajma(komanda, root);
            } else if (komanda.contains("struktura") && komanda.contains("najam") && !komanda.contains("zarada")) {
                System.out.println("Pregled strukture i najma\n");
                generirajStrukturuNajama(komanda, root);
            } else if (komanda.contains("struktura") && !komanda.contains("najam") && komanda.contains("zarada")) {
                System.out.println("Pregled strukture i zarade\n");
                generirajStrukturuZarade(komanda, root);
            }else if (!komanda.contains("struktura") && komanda.contains("najam") && komanda.contains("zarada")) {    
                System.out.println("Pregled najma i zarade\n");
                generirajZaraduINajam(komanda, root);
            } else if (komanda.contains("struktura")) {    
                System.out.println("Pregled strukture\n");
                generirajStrukturu(komanda, root);
            } else if (komanda.contains("najam")) {
                System.out.println("Pregled najma\n");
                generirajNajam(komanda, root);
            } else if (komanda.contains("zarada")) {
                System.out.println("Pregled zarade\n");
                generirajZaradu(komanda, root);
            }
        }
    }

}
