/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vbencek.args.provjera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vbencek.Sustav;
import vbencek.mvc.InteraktivniView;
import vbencek.mvc.IspisController;
import vbencek.mvc.IspisModel;
import vbencek.mvc.NacinRada;
import vbencek.mvc.SkupniDatotekaView;
import vbencek.mvc.SkupniView;
import vbencek.mvc.View;

/**
 *
 * @author NWTiS_2
 */
public class NaredbaKonfig implements NaredbaKL {

    private int brojacDat = 0;
    private boolean ispravnoVrijeme = false;
    private boolean ispravnoDugovanje = false;
    private List<String> obvezneDat = Arrays.asList("vozila", "lokacije", "cjenik", "kapaciteti", "osobe", "struktura");
    private String datAktivnosti = "aktivnosti";
    private String datIzlaz = "izlaz";
    private List<String> opcije = Arrays.asList("tekst", "cijeli", "decimala");
    private String datoteke = "";
    private boolean postojiAkt = false;
    private boolean postojiIzl = false;

    private boolean provjeriSintaksu(String dat) {
        String sintaksa = "^(.+\\.txt)$";
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(dat);
        boolean status = m.matches();
        return status;
    }

    private boolean postojiVrijeme(String ulaz) {
        String sintaksa = "^((19|20)\\d\\d-(1[012]|0[1-9])-(0[1-9]|[12][0-9]|3[01]) ([01]\\d|2[0123]):([012345]\\d):([012345]\\d))$";
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(ulaz);
        boolean status = m.matches();
        return status;
    }

    private boolean ispravanFormatFloat(String ulaz) {
        try {
            if (Float.parseFloat(ulaz) < 0) {
                System.out.println("Vrijednost za dugovanje nije pozitivan broj!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Neispravan format vrijednosti za ključ: dugovanje");
            return false;
        }
        return true;
    }

    private void dodijeliVirtualnoVrijeme(String datum) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = formatter.parse(datum);
            Sustav.getInstanca().setVrijeme(date);
        } catch (ParseException ex) {
            Logger.getLogger(NaredbaInteraktivni.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isInteger(String vrijednost) {
        try {
            if (Integer.parseInt(vrijednost) < 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean spremiPodatke(String redak) {
        String red = redak + " ";
        String[] polje = red.split("=");
        if (polje.length == 2) {
            String kljuc = polje[0].trim();
            String vrijednost = polje[1].trim();
            if (obvezneDat.contains(kljuc) && provjeriSintaksu(vrijednost)) {
                brojacDat++;
                switch (kljuc) {
                    case "vozila":
                        datoteke += "-v" + " " + vrijednost + " ";
                        break;
                    case "lokacije":
                        datoteke += "-l" + " " + vrijednost + " ";
                        break;
                    case "cjenik":
                        datoteke += "-c" + " " + vrijednost + " ";
                        break;
                    case "kapaciteti":
                        datoteke += "-k" + " " + vrijednost + " ";
                        break;
                    case "osobe":
                        datoteke += "-o" + " " + vrijednost + " ";
                        break;
                    case "struktura":
                        datoteke += "-os" + " " + vrijednost + " ";
                        break;
                }
                return true;
            }
            if (datAktivnosti.equals(kljuc) && provjeriSintaksu(vrijednost)) {
                Sustav.getInstanca().setAktivnosti(vrijednost);
                Sustav.getInstanca().setSkupni(true);
                postojiAkt = true;
                return true;
            }
            if (datIzlaz.equals(kljuc) && provjeriSintaksu(vrijednost)) {
                Sustav.getInstanca().setIzlaznaDat(vrijednost);
                postojiIzl = true;
                return true;
            }
            if ("vrijeme".equals(kljuc) && postojiVrijeme(vrijednost)) {
                ispravnoVrijeme = true;
                dodijeliVirtualnoVrijeme(vrijednost);
                return true;
            }
            if ("dugovanje".equals(kljuc) && ispravanFormatFloat(vrijednost.replace(",", "."))) {
                ispravnoDugovanje = true;
                Sustav.getInstanca().setMaksDugovanje(Float.parseFloat(vrijednost.replace(",", ".")));
                return true;
            }
            if (opcije.contains(kljuc) && isInteger(vrijednost)) {
                switch (kljuc) {
                    case "tekst":
                        Sustav.getInstanca().setTekst(Integer.parseInt(vrijednost));
                        break;
                    case "cijeli":
                        Sustav.getInstanca().setCijeli(Integer.parseInt(vrijednost));
                        break;
                    case "decimala":
                        Sustav.getInstanca().setDecimala(Integer.parseInt(vrijednost));
                        break;
                }
                return true;
            }
        } else {
            System.out.println("Greska: krivi broj argumenata: " + redak);
            return false;
        }

        System.out.println("Greska: neispravan redak: " + redak);
        return false;
    }

    private void postaviPocetniPogled() {
        IspisModel model = new IspisModel();
        View view;
        if (postojiAkt && postojiIzl) {
            try {
                PrintWriter pw = new PrintWriter(Sustav.getInstanca().getIzlaznaDat());
                pw.close();
            } catch (Exception ex) {
                System.out.println("Ne postoji navedena datoteka za izlaz");
            }
            view = new SkupniDatotekaView();
            model.setNacinRada(new NacinRada(2));
        } else if (postojiAkt && !postojiIzl) {
            view = new SkupniView();
            model.setNacinRada(new NacinRada(1));
        } else {
            view = new InteraktivniView();
            model.setNacinRada(new NacinRada(0));
        }
        IspisController controller = new IspisController(model, view);
        controller.updateView();
    }

    private boolean procitajDat(String datoteka) {
        try {
            File file = new File(datoteka);
            Scanner myReader = new Scanner(file, "UTF-8");
            if (!myReader.hasNextLine()) {
                System.out.println("Datoteka: " + file.getName() + " je prazna!");
                return false;
            }
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                spremiPodatke(data);
                if (!myReader.hasNextLine()) {
                    System.out.println("Procitana datoteka: " + file.getName());
                    return true;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Nije moguce procitati datoteku: " + datoteka);
            return false;
        }
        return false;
    }

    @Override
    public boolean provjeri(String[] argumenti) {
        System.out.println("Ucitavanje konfiguracija...");
        if (argumenti.length == 1) {
            String datoteka = argumenti[0];
            if (provjeriSintaksu(datoteka)) {
                System.out.println("Ucitavanje datoteke: " + datoteka);
                if (procitajDat(datoteka)) {
                    if (brojacDat == 6 && ispravnoVrijeme && ispravnoDugovanje) {
                        Sustav.getInstanca().setDatoteke(datoteke);
                        postaviPocetniPogled();
                        return true;
                    }
                } else {
                    return false;
                }
            } else {
                System.out.println("Neispravna sintaksa");
            }
        }
        return false;
    }

}
