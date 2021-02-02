package vbencek.readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Creator u Factory method uzorku dizajna. 
 * Klasa koja ima posao procitati redove iz datoteke te pozivati Concrete Creator-a kojem proslijeđuje opcije te od dalje instancira potrebnu klasu
 * @author vbencek
 */
public class CitacDatoteka {
    
    /**
     * Metoda koja prima nazive datoteka kao ulazne argumente te ih redom učitava i prosljeđuje njihove retke dalje na obradu
     * @param argumentiDat datoteke koje je potrebno pročitati
     */
    public void procitaj(String argumentiDat) {
        String[] parDat = argumentiDat.trim().split(" ");
        ZapisiFactory zF = new ZapisiFactory();
        for (int i = 0; i < parDat.length; i = i + 2) {
            String opcija = parDat[i];
            String datIme = parDat[i + 1];
            Zapis zapis = zF.getZapis(opcija);
            try {
                File file = new File(datIme);
                Scanner myReader = new Scanner(file,"UTF-8");
                if(myReader.hasNextLine()){
                    myReader.nextLine();
                }else{
                    System.out.println("Datoteka: "+file.getName()+" je prazna!");
                }
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    zapis.spremi(data);
                    if(!myReader.hasNextLine()){
                        System.out.println("Procitana datoteka: "+file.getName());
                    }
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("Nije moguce procitati datoteku: "+datIme);
            }

        }
    }
}
