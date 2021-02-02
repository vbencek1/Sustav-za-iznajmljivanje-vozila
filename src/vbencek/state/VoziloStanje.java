/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vbencek.state;

import vbencek.items.VoziloLokacije;

/**
 *
 * @author NWTiS_2
 */
public interface VoziloStanje {
    
    public void doAction(VoziloLokacije vozilo);
}
