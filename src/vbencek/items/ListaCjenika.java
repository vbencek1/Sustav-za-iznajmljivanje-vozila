package vbencek.items;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton uzorak dizajna
 * Klasa koja sadrži listu za objekte Cjenika.
 * Mora imati samo jednu instancu.
 * @author vbencek
 */
public class ListaCjenika {

    private List<Cjenik> listCjenik = new ArrayList<>();

    private static ListaCjenika instanca;

    private ListaCjenika() {

    }

    static {
        instanca = new ListaCjenika();
    }

    public static ListaCjenika getInstanca() {
        return instanca;
    }

    public List<Cjenik> getCjenici() {
        return listCjenik;
    }

    public void setCjenici(List<Cjenik> novaLista) {
        listCjenik = novaLista;
    }
    
    /**
     * Metoda dodaje novi cjenik u listu
     * @param cjenik novi cjenik
     */
    public void unosCjenika(Cjenik cjenik) {
        listCjenik.add(cjenik);
    }
    
    /**
     * Pretraga cjenika po id-u
     * @param idVoz
     * @return cjenik
     */
    public Cjenik pretraziCjenik(int idVoz) {
        for (Cjenik cj : listCjenik) {
            if (cj.getId() == idVoz) {
                return cj;
            }
        }
        return null;
    }
    
    /**
     * Izračun cijene najma vozila
     * @param idVoz id vozila
     * @param vrijeme vrijeme u najmu
     * @param km prijedeni kilometri
     * @return izračunata cijena
     */
    public double izracunajCijenu(int idVoz, double vrijeme, int km) {
        try {
            Cjenik cj = pretraziCjenik(idVoz);
            double najam = cj.getNajam();
            double najamVr = cj.getPoSatu() * vrijeme;
            double najamKm = cj.getPoKm() * km;
            return najam + najamVr + najamKm;
        } catch (Exception e) {
            return -1;
        }

    }
}
