package vbencek.readers;

/**
 * Concrete Creator u  Factory method uzorku dizajna.
 * Kreira objekt Concrete klase ovisno o danoj informaciji.
 * @author vbencek
 */
public class ZapisiFactory {
    public Zapis getZapis(String opcija){
        if("-v".equals(opcija)){
            return new ZapisVozila();
        }
        if("-l".equals(opcija)){
            return new ZapisLokacija();
        }
        if("-c".equals(opcija)){
            return new ZapisCjenika();
        }
        if("-k".equals(opcija)){
            return new ZapisKapaciteta();
        }
        if("-o".equals(opcija)){
            return new ZapisOsoba();
        }
        if("-os".equals(opcija)){
            return new ZapisTvrtke();
        }
        return null;
    }
}
