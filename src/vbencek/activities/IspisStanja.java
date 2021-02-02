package vbencek.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vbencek.Sustav;
import vbencek.composite.OrganizacijskaJedinica;


/**
 * Concrete class odnosno ConcreteProduct u Factory method uzorku dizajna.
 * Poziva se kad korisnik želi prikazati strukturu tvrtke te stanje organizacijskih jedinica
 * @author vbencek
 */
public class IspisStanja implements Aktivnost {
    
    private boolean provjeri(String ulaz) {
        String sintaksa = "^6;(?:((struktura|stanje))(?!\\1))+(|[0-9]+)";
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
        if (polje.length == 2) {
            idOJ = Integer.parseInt(polje[1]);
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
    
    private void generirajStrukturuStanje(String komanda, OrganizacijskaJedinica root) {
        String[] polje = komanda.split(" ");
        OrganizacijskaJedinica jedinica;
        int idOJ = 0;
        if (polje.length == 3) {
            idOJ = Integer.parseInt(polje[2]);
        }
        if (idOJ == 0) {
            root.ispisiStrukturuStanje();
        } else {
            jedinica = root.pronadiPodredenu(idOJ);
            if (jedinica != null) {
                jedinica.ispisiStrukturuStanje();
            } else {
                System.out.println("Ne postoji organizacijska jedinica sa Id: " + idOJ);
            }
        }
    }
     private void generirajStanje(String komanda, OrganizacijskaJedinica root) {
        String[] polje = komanda.split(" ");
        OrganizacijskaJedinica jedinica;
        int idOJ = 0;
        if (polje.length == 2) {
            idOJ = Integer.parseInt(polje[1]);
        }
        if (idOJ == 0) {
            root.ispisiStanje();
        } else {
            jedinica = root.pronadiPodredenu(idOJ);
            if (jedinica != null) {
                jedinica.ispisiStanje();
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
            if (komanda.contains("struktura") && !komanda.contains("stanje")) {
                System.out.println("Pregled strukture\n");
                generirajStrukturu(komanda, root);
            }
            if (komanda.contains("stanje") && komanda.contains("struktura")) {
                System.out.println("Pregled strukture i stanja\n");
                generirajStrukturuStanje(komanda, root);
                
            }
            if (komanda.contains("stanje") && !komanda.contains("struktura")) {
                System.out.println("Pregled stanja\n");
                generirajStanje(komanda, root);
                
            }
            
        }
    }
    
}
