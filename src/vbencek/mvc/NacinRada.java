package vbencek.mvc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import vbencek.GlavnaKlasa;
import vbencek.Sustav;

public class NacinRada {

    /**
     * 0-interaktivni 1-skupni 2-datoteka
     */
    private final int opcija;

    /**
     * 0-interaktivni 1-skupni 2-datoteka
     *
     * @param opcija
     */
    public NacinRada(int opcija) {
        this.opcija = opcija;
    }

    public void primjeniNacinRada() {
        switch (opcija) {
            case 0:
                System.setOut(GlavnaKlasa.ps_console);
                break;
            case 1:
                // Za sad nije potrebno napravit
                break;
            case 2:
                String imeDat = Sustav.getInstanca().getIzlaznaDat();
                File file = new File(imeDat);
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(file,true);
                    PrintStream ps = new PrintStream(fos);
                    System.setOut(ps);
                } catch (Exception ex) {
                    System.out.println("Ne postoji izlazna datoteka: " + imeDat);
                }
                break;
        }
    }

}
