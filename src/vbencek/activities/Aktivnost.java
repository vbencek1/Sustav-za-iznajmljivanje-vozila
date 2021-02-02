package vbencek.activities;

/**
 * Sučelje aktivnosti koje za zapravo Product u Factory method uzorku dizajna.
 * Daje sučelje za ConcreteProduct-e. Radi se o aktivnostima koje su dane korisnicima na odabir.
 * Creator u dizajnu je GlavnaKlasa.
 * @author vbencek
 */
public interface Aktivnost {
    void izvrsi(String operacija);
}
