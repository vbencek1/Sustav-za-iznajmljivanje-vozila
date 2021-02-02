package vbencek.readers;

/**
 * Sučelje aktivnosti koje za zapravo Product u Factory method uzorku dizajna.
 * Daje sučelje za ConcreteProduct-e. Radi se o zapisima koje je potrebno učitati iz datoteke te pohraniti u listu.
 * @author vbencek
 */
public interface Zapis {

    void spremi(String redak);
}
