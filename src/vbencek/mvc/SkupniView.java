
package vbencek.mvc;

import vbencek.Sustav;


public class SkupniView implements View{

    @Override
    public void ispisi(NacinRada nacinRada) {
        System.out.println("------Trenutni pogled: Skupni nacin rada------");
        Sustav.getInstanca().setTrenutniPogled(this);
    }

    @Override
    public void ispisiPogresku(String redak) {
        // TO DO ispisuje crveno
    }
    
}
