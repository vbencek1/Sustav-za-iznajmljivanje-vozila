package vbencek.mvc;

import java.io.PrintStream;
import vbencek.GlavnaKlasa;
import vbencek.Sustav;

public class SkupniDatotekaView implements View {

    @Override
    public void ispisi(NacinRada nacinRada) {
        System.out.println("------Trenutni pogled: Skupni nacin rada sa Datotekom------");
        nacinRada.primjeniNacinRada();
        Sustav.getInstanca().setTrenutniPogled(this);
    }

    @Override
    public void ispisiPogresku(String redak) {
        PrintStream console = GlavnaKlasa.ps_console;
        console.println(redak);
        String linija = "";
        for (int i = 0; i < redak.length(); i++) {
            linija = linija + "X";
        }
        console.println(linija);
    }

}
