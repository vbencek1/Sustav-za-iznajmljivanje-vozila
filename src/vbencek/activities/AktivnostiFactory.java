package vbencek.activities;

/**
 * Concrete Creator u  Factory method uzorku dizajna.
 * Kreira objekt Concrete klase ovisno o danoj informaciji.
 * @author vbencek
 */
public class AktivnostiFactory {

    public Aktivnost getAktivnost(int idAktivnosti) {
        if (idAktivnosti == 0) {
            return new KrajPrograma();
        }
        if (idAktivnosti == 1) {
            return new PregledVozila();
        }
        if (idAktivnosti == 2) {
            return new NajamVozila();
        }
        if (idAktivnosti == 3) {
            return new PregledMjesta();
        }
        if (idAktivnosti == 4) {
            return new VracanjeVozila();
        }
        if (idAktivnosti == 5) {
            return new IzvrsiSkupni();
        }
        if (idAktivnosti == 6) {
            return new IspisStanja();
        }
        if (idAktivnosti == 7) {
            return new IspisNajamZarada();
        }
        if (idAktivnosti == 8) {
            return new IspisRacuna();
        }
        if (idAktivnosti == 9) {
            return new KorisnikStanje();
        }
        if (idAktivnosti == 10) {
            return new KorisnikRacuni();
        }
        if (idAktivnosti == 11) {
            return new KorisnikDugovanja();
        }
        if (idAktivnosti == 12) {
            return new DF_SklopiUgovor();
        }
        return null;
    }
}
