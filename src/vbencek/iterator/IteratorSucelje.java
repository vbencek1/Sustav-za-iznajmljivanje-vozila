package vbencek.iterator;

/**
 * Sučelje Iterator uzorka dizajna
 * @author vbencek
 * @param <E> 
 */
public interface IteratorSucelje<E> {

    boolean hasNext();

    E next();
}
