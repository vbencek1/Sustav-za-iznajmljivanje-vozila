package vbencek.args.provjera;

import vbencek.Sustav;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**@Depricated klasa,nije više potrebna za potrebe zadaće 3.
 * Predstavlja ConcreteProduct.
 * Klasa služi za provjeru sintakse argumenata dobivenih preko naredbenog retka kada se radi o interaktivnom  načinu rada
 * @author vbencek
 */
public class NaredbaInteraktivni implements NaredbaKL{
    
    /**
     * Metoda koja služi za inicijalizaciju virtualnog vremena
     * @param datum 
     */
    private void dodijeliVirtualnoVrijeme(String datum){
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date=formatter.parse(datum);
            Sustav.getInstanca().setVrijeme(date);
        } catch (ParseException ex) {
            Logger.getLogger(NaredbaInteraktivni.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metoda koja služi za provjeru postojanja svih potrebnih datoteka
     * @param ulaz
     * @param opcija
     * @return 
     */
    public boolean postojiDat(String ulaz, String opcija) {
        String sintaksa = "^(-" + opcija + " .+\\.txt)$";
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(ulaz);
        boolean status = m.matches();
        return status;
    }

    /**
     * Metoda koja služi za provjeru postojanja ispravnog formata vremena dobivenog preko konzole
     * @param ulaz
     * @return 
     */
    public boolean postojiVrijeme(String ulaz) {
        String sintaksa = "^(-t (19|20)\\d\\d-(1[012]|0[1-9])-(0[1-9]|[12][0-9]|3[01]) ([01]\\d|2[0123]):([012345]\\d):([012345]\\d))$";
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(ulaz);      
        boolean status = m.matches(); 
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

    /**
     * Metoda koja ispravlja sintaksu ukoliko se radi o neispravnim navodnicima: „ i “
     * @param argumenti
     * @return ispravni argumenti
     */
    public String[] ispraviSintaksu(String[] argumenti) {
        String strArg = "";
        for (int i = 0; i < argumenti.length; i++) {
            if (argumenti[i].contains("„")) {
                String prvi = argumenti[i].replace("„", "").trim();
                String drugi = argumenti[i + 1].replace("“", "").trim();
                strArg += prvi + ";" + drugi + " ";
                i++;
            } else {
                strArg += argumenti[i] + " ";
            }
        }
        String strArgObr=strArg.replaceAll("[\\p{So}]+", "");
        String[] poljeArg = strArgObr.split(" ");
        for (int i = 0; i < poljeArg.length; i++) {
            if (poljeArg[i].contains(";")) {
                poljeArg[i] = poljeArg[i].replace(";", " ");
            }
        }
        return poljeArg;

    }

    /**
     * Metoda koja provjerava postojanje svih argumenata
     * @param argumenti
     * @return 
     */
    public boolean provjeraArgumenata(String[] argumenti) {
        String[] opcije = {"v", "l", "c", "k", "o","os"};
        int brojac = 0;
        String datoteke="";
        for (int i = 0; i < argumenti.length; i = i + 2) {
            for (int j = 0; j < 6; j++) {
                if (postojiDat(argumenti[i] + " " + argumenti[i + 1], opcije[j])) {
                    brojac++;
                    datoteke+=argumenti[i]+" "+argumenti[i+1]+" ";
                }
            }
        }
        for (int i = 0; i < argumenti.length; i = i + 2) {
            if (postojiVrijeme(argumenti[i] + " " + argumenti[i + 1])) {
                brojac++;
                dodijeliVirtualnoVrijeme(argumenti[i+1]);
                break;
            }
        }
        for(int i=0;i<argumenti.length;i=i+2){
            if(argumenti[i].length()==3 && isInteger(argumenti[i+1])){
                switch(argumenti[i]){
                    case "-dt":
                        Sustav.getInstanca().setTekst(Integer.parseInt(argumenti[i+1]));
                        break;
                    case "-dc":
                        Sustav.getInstanca().setCijeli(Integer.parseInt(argumenti[i+1]));
                        break;
                    case "-dd":
                        Sustav.getInstanca().setDecimala(Integer.parseInt(argumenti[i+1]));
                        break;
                        
                }
            }
        }
        Sustav.getInstanca().setDatoteke(datoteke);
        return brojac == 7;
    }

    /**
     * Metoda koja služi za provjeru ispravnosti sintakse ovisno o broju ulaznih argumenata
     * @param argumenti
     * @return 
     */
    @Override
    public boolean provjeri(String[] argumenti) {
        boolean ispravno=false;
        try {
            switch (argumenti.length) {
                case 21: case 19: case 17: case 15: case 13: case 11: case 7: case 5: case 3:
                    String[] praviArgc = ispraviSintaksu(argumenti);
                    if (provjeraArgumenata(praviArgc)) {
                        ispravno=true;
                    }
                    break;
                case 20: case 18: case 16: case 14:
                    if (provjeraArgumenata(argumenti)) {
                        ispravno=true;
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        }
        if(!ispravno) System.out.println("Neispravna sintaska");
        return ispravno;
    }
}
