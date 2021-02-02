package vbencek.iterator;

import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import vbencek.composite.OrganizacijskaJedinica;
import vbencek.composite.Tvrtka;

/**
 * Konkretna implementacija iteratora 
 * @author vbencek
 */
public class TvrtkaIterator implements IteratorSucelje<Tvrtka> {

    Deque<Tvrtka> stack = new LinkedList<>();

    public TvrtkaIterator(Tvrtka root) {
        stack.add(root);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Tvrtka next() {
        if (stack.isEmpty()) {
            throw new NoSuchElementException();
        }
        Tvrtka node = stack.pop();
        if (node != null) {
            if (node instanceof OrganizacijskaJedinica) {
                OrganizacijskaJedinica oj = (OrganizacijskaJedinica) node;
                for (Tvrtka tvr : oj.dajPodredene()) {
                    stack.addFirst(tvr);
                }
            }
        }
        return node;
    }
}
