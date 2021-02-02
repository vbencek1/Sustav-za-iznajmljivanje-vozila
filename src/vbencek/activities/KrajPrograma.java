package vbencek.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vbencek.Sustav;

/**
 * Concrete class odnosno ConcreteProduct u Factory method uzorku dizajna.
 * Poziva se kada korisnik želi završiti program.
 *
 * @author vbencek
 */
public class KrajPrograma implements Aktivnost {

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
        String sintaksa = "^(([0]); (|.)(19|20)\\d\\d-(1[012]|0[1-9])-(0[1-9]|[12][0-9]|3[01])"
                + " ([01]\\d|2[0123]):([012345]\\d):([012345]\\d))(|.)$";
        
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(ulaz);
        boolean status = m.matches();
        if(!status) System.out.println(" -Neispravna naredba!");
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
     * Metoda za sad ne vrši nikakvu funkciju osim ispisa
     *
     * @param operacija Uzlazni argumenti koje je potrebno obraditi(id vozila,
     * id lokacije, vrijeme,..)
     */
    @Override
    public void izvrsi(String operacija) {
        String vrijeme = formirajVrijeme(operacija.split(";")[1].trim());
        if (provjeri(operacija) && provjeriIspravnoVrijeme(vrijeme)) {
            System.out.println("Kraj programa");
            Sustav.getInstanca().setRadi(false);
        }

    }

}
