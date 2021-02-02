package vbencek.args.provjera;

/**
 * Klasa koja predstavlja Concrete Factory za kreiranje objekata konzolnih naredbi
 * @author vbencek
 */
public class NaredbaKLFactory{
    public NaredbaKL getNaredbaKL(String opcija) {
        if(opcija.contains("-s ")){
            return new NaredbaUvjekFalse();}
        else if(!opcija.trim().contains(" ")){
            return new NaredbaKonfig();
        }else{
            return new NaredbaUvjekFalse();
        }
    
    }
    
}
