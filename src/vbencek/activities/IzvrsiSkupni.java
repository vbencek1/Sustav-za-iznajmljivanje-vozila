package vbencek.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vbencek.Sustav;

/**
 * Concrete class odnosno ConcreteProduct u Factory method uzorku dizajna.
 * Poziva se kad korisnik želi prijeđi u skupni način rada
 *
 * @author vbencek
 */
public class IzvrsiSkupni implements Aktivnost {

    private boolean provjeri(String ulaz) {
        String sintaksa = "^(([5]); .+\\.txt)$";
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(ulaz);
        boolean status = m.matches();
        if (!status) {
            System.out.println(" -Neispravna naredba!");
        }
        return status;
    }

    @Override
    public void izvrsi(String operacija) {
        if (provjeri(operacija)) {
            String datoteka = operacija.split(";")[1].trim();
            AktivnostiFactory aF = new AktivnostiFactory();
            try {
                File file = new File(datoteka);
                if (file.exists()) {
                    Sustav.getInstanca().promjeniPogled(true);
                    System.out.println("Učitavam datoteku: " + datoteka);
                }
                Scanner myReader = new Scanner(file, "UTF-8");
                if (myReader.hasNextLine()) {
                    myReader.nextLine();
                }
                while (myReader.hasNextLine()) {
                    String dataP = myReader.nextLine();
                    String data = dataP.replace("č", "c").replace("„", "").replace("“", "").replace("\"", "").replaceAll("[^a-zA-Z0-9.;: -_]+", "").trim();
                    System.out.println(data);
                    try {
                        int akt = Integer.parseInt(data.split(";")[0].trim());
                        Aktivnost aktivnost = aF.getAktivnost(akt);
                        aktivnost.izvrsi(data.trim());
                    } catch (Exception e) {
                        System.out.println(" --Greska: Nije moguce izvrsiti naredbu");
                    }
                }
                Sustav.getInstanca().setSkupni(false);
                Sustav.getInstanca().promjeniPogled(false);
                myReader.close();
            } catch (FileNotFoundException e) {
                Sustav.getInstanca().setSkupni(false);
                Sustav.getInstanca().promjeniPogled(false);
                System.out.println("Nije moguce procitati datoteku: " + datoteka);
            }
        }
    }

}
