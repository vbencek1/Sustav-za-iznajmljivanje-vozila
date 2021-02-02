/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vbencek.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vbencek.items.ListaOsoba;
import vbencek.items.Osoba;

/**
 *DODATNA FUNKCIONALNOST: sklapanje ugovora
 */
public class DF_SklopiUgovor implements Aktivnost{
    
     private boolean provjeri(String ulaz) {
        String sintaksa = "^12; [0-9]+";
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
        if(provjeri(operacija)){
            int idKorisnika = Integer.parseInt(operacija.split(";")[1].trim());
            Osoba osoba = ListaOsoba.getInstanca().pretraziOsobu(idKorisnika);
            if (osoba != null) { 
                if(!osoba.isUgovor()){
                    ListaOsoba.getInstanca().sklopiUgovor(osoba);
                    System.out.println("Osoba : " + osoba.getImePrezime() + " je uspjesno sklopila ugovor!");
                }else{
                    System.out.println("Osoba : " + osoba.getImePrezime() + " vec ima sklopljen ugovor sa firmom!");
                }
            }else {
                System.out.println(" -Osoba sa ID: " + idKorisnika + " ne postoji!");
            }
        }
    }
    
}
