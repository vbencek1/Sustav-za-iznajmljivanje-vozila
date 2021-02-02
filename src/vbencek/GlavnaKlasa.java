package vbencek;

import java.io.PrintStream;
import java.util.Scanner;
import vbencek.activities.Aktivnost;
import vbencek.activities.AktivnostiFactory;
import vbencek.readers.CitacDatoteka;
import vbencek.args.provjera.NaredbaKL;
import vbencek.args.provjera.NaredbaKLFactory;

/**
 * Glavna klasa programa koja preuzima argumente preko naredbenog retka. Poziva
 * sve ostale klase koje su joj potrebne za rad
 *
 * @author vbencek
 */
public class GlavnaKlasa {
    
    public static PrintStream ps_console = System.out;
    
    
    /**
     * Glavna klasa programa koja se poziva prilikom pokretanja programa
     * @param args argumenti s kojima se pokreće program
     */
    public static void main(String[] args) {
        AktivnostiFactory aF = new AktivnostiFactory();
        NaredbaKLFactory nF= new NaredbaKLFactory();
        String opcija = "";
        for (int i = 0; i < args.length; i++) {
            opcija += args[i] + " ";
        }
        NaredbaKL naredbaKL=nF.getNaredbaKL(opcija);
        Sustav.getInstanca().setSkupni(opcija.contains("-s "));
        if (naredbaKL.provjeri(args)) {
            System.out.println("Ucitavanje datoteka...");
            CitacDatoteka citacDatoteka = new CitacDatoteka();
            citacDatoteka.procitaj(Sustav.getInstanca().getDatoteke());
            System.out.println("Opcije formatiranja: dt-"+Sustav.getInstanca().getTekst()+", dc-"+Sustav.getInstanca().getCijeli()+", dd-"+Sustav.getInstanca().getDecimala());
            System.out.println("Maks dugovanje: "+Sustav.getInstanca().getMaksDugovanje());
            String naredbaP = "";
            if(Sustav.getInstanca().sloziStablo()){
            while (Sustav.getInstanca().isRadi()) {
                if (!Sustav.getInstanca().isSkupni()) {
                    ps_console.println("Unos: ");
                    Scanner sc = new Scanner(System.in);
                    if (sc.hasNextLine()) {
                        naredbaP = sc.nextLine();
                        String naredba = naredbaP.replace("č","c").replace("„", "").replace("“", "").replace("\"", "").replaceAll("[^a-zA-Z0-9.;: -_]+", "").trim();
                        System.out.println("Korisnicki unos: "+naredbaP);
                       try{
                            int akt = Integer.parseInt(naredba.split(";")[0].trim());
                            Aktivnost aktivnost = aF.getAktivnost(akt);
                            aktivnost.izvrsi(naredba.trim());
                        } catch (Exception e) {
                        System.out.println(" --Greska: Nije moguce izvrsiti naredbu");
                    }

                    }
                } else {
                    int akt = 5;
                    Aktivnost aktivnost = aF.getAktivnost(akt);
                    aktivnost.izvrsi("5; " + Sustav.getInstanca().getAktivnosti());
                }
            }
        }}
    }

}
