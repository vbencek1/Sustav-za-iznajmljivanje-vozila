package vbencek.composite;

/**
 * Sučelje Composite uzorka dizajna
 * @author vbencek
 */
public interface Tvrtka {
    String ispisiStanje();
    String ispisiStrukturu();
    String ispisiStrukturuStanje();
    String ispisiZaradu(String datumPocetni, String datumDrugi);
    String ispisiNajmove(String datumPocetni, String datumDrugi);
    String ispisiStrukturuZarade(String datumPocetni, String datumDrugi);
    String ispisiStrukturuNajmova(String datumPocetni, String datumDrugi);
    String ispisiStrukturuZaradeINajmova(String datumPocetni, String datumDrugi);
    String ispisiZaraduINajmove(String datumPocetni, String datumDrugi);
    String ispisiRacune(String datumPocetni, String datumDrugi);
    String ispisiStrukturuRacuna(String datumPocetni, String datumDrugi);
}
