/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vbencek.mvc;

import vbencek.Sustav;

/**
 *
 * @author NWTiS_2
 */
public class InteraktivniView implements View{

    @Override
    public void ispisi(NacinRada nacinRada) {
        nacinRada.primjeniNacinRada();
        System.out.println("------Trenutni pogled: Interaktivni nacin rad------");
        Sustav.getInstanca().setTrenutniPogled(this);
    }

    @Override
    public void ispisiPogresku(String redak) {
        //TO DO ispisuje crveno
    }
    
}
