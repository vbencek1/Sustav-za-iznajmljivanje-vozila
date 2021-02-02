package vbencek;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import vbencek.items.ListaLokacija;
import vbencek.items.ListaTvrtke;
import vbencek.items.Tvrtka;
import vbencek.composite.OrganizacijskaJedinica;
import vbencek.items.ListaLokacijeKapaciteti;
import vbencek.items.LokacijaKapacitet;
import vbencek.items.VoziloLokacije;
import vbencek.mvc.InteraktivniView;
import vbencek.mvc.IspisController;
import vbencek.mvc.IspisModel;
import vbencek.mvc.NacinRada;
import vbencek.mvc.SkupniDatotekaView;
import vbencek.mvc.SkupniView;
import vbencek.mvc.View;
import vbencek.state.PunjenjeStanje;
import vbencek.state.SlobodnoStanje;

/**
 * Singleton Klasa koja pamti globalne varijable.Sadrži osnovne postavke sustava
 * i obavlja osnovne operacije sustava.
 *
 * @author vbencek
 */
public class Sustav {

    private Date virutalnoVrijeme;
    private String datoteke = "";
    private String aktivnosti = "";
    private int vozilaUniqueId = 0;
    private int racunUniqueId = 0;
    private OrganizacijskaJedinica tvrtka;
    private List<OrganizacijskaJedinica> ojLista = new ArrayList<>();
    private boolean radi = true;
    private boolean skupniRad = false;
    private int tekst = 30;
    private int cijeli = 5;
    private int decimala = 2;
    private float maksDugovanje = 0;
    private String izlaznaDat = "";
    private View trenutniPogled;

    private static Sustav instanca;

    private Sustav() {

    }

    static {
        instanca = new Sustav();
    }

    public static Sustav getInstanca() {
        return instanca;
    }

    public void setVrijeme(Date vrijeme) {
        virutalnoVrijeme = vrijeme;
    }

    public void setDatoteke(String dat) {
        datoteke = dat;
    }

    public void setAktivnosti(String akt) {
        aktivnosti = akt;
    }

    public void setTvrtka(OrganizacijskaJedinica tvrtka) {
        this.tvrtka = tvrtka;
    }

    public void setRadi(boolean radi) {
        this.radi = radi;
    }

    public void setSkupni(boolean skupniRad) {
        this.skupniRad = skupniRad;
    }

    public void setTekst(int tekst) {
        this.tekst = tekst;
    }

    public void setCijeli(int cijeli) {
        this.cijeli = cijeli;
    }

    public void setDecimala(int decimala) {
        this.decimala = decimala;
    }

    public float getMaksDugovanje() {
        return maksDugovanje;
    }

    public void setMaksDugovanje(float maksDugovanje) {
        this.maksDugovanje = maksDugovanje;
    }

    public String getIzlaznaDat() {
        return izlaznaDat;
    }

    public void setIzlaznaDat(String izlaznaDat) {
        this.izlaznaDat = izlaznaDat;
    }

    public View getTrenutniPogled() {
        return trenutniPogled;
    }

    public void setTrenutniPogled(View trenutniPogled) {
        this.trenutniPogled = trenutniPogled;
    }

    public Date getVrijeme() {
        return virutalnoVrijeme;
    }

    public String getDatoteke() {
        return datoteke.trim();
    }

    public String getAktivnosti() {
        return aktivnosti.trim();
    }

    public int getTekst() {
        return tekst;
    }

    public int getCijeli() {
        return cijeli;
    }

    public int getDecimala() {
        return decimala;
    }

    public boolean isRadi() {
        return radi;
    }

    public boolean isSkupni() {
        return skupniRad;
    }

    public int generateIdVozila() {
        vozilaUniqueId = vozilaUniqueId + 1;
        return vozilaUniqueId;
    }

    public int generateIdRacuna() {
        racunUniqueId = racunUniqueId + 1;
        return racunUniqueId;
    }

    public void promjeniPogled(boolean skupni) {
        IspisModel model = new IspisModel();
        View view;
        if (skupni) {
            if ("".equals(izlaznaDat)) {
                view = new SkupniView();
                model.setNacinRada(new NacinRada(1));
            } else {
                view = new SkupniDatotekaView();
                model.setNacinRada(new NacinRada(2));
            }
        } else {
            view = new InteraktivniView();
            model.setNacinRada(new NacinRada(0));
        }
        IspisController controller = new IspisController(model, view);
        controller.updateView();

    }

    public OrganizacijskaJedinica getTvrtka() {
        return tvrtka;
    }

    public void azurirajBaterije(int idLok, int idVoz) {
        ListaLokacijeKapaciteti listaLK = ListaLokacijeKapaciteti.getInstanca();
        LokacijaKapacitet lk = listaLK.pretraziPoLokacijiIVozilu(idLok, idVoz);
        for (VoziloLokacije vl : lk.getVozilaLokacije()) {
            if (vl.getVrstaVozila() == idVoz) {
                if ((vl.getVrijemeDoPuneBaterije() == null || vl.getVrijemeDoPuneBaterije().before(Sustav.getInstanca().getVrijeme()))
                        && vl.getStanje() instanceof PunjenjeStanje) {
                    vl.setStanje(new SlobodnoStanje());
                }
            }
        }
    }

    private boolean dodajLokacijeOj(OrganizacijskaJedinica oj, List<Integer> idLokLista) {
        List<vbencek.composite.Tvrtka> listaLok = new ArrayList<>();
        for (Integer idLok : idLokLista) {
            try {
                listaLok.add(ListaLokacija.getInstanca().pretragaLokacija(idLok));
            } catch (Exception e) {
                System.out.println("SYSTEM: Greška prilikom građenja stabla - lokacija sa id: " + idLok + " ne postoji");
                return false;
            }
        }
        oj.dodajListuLokacija(listaLok);
        return true;
    }

    private OrganizacijskaJedinica vratiOj(int id) {
        for (OrganizacijskaJedinica oj : ojLista) {
            if (oj.getId() == id) {
                return oj;
            }
        }
        return null;
    }

    private void dodajNadredenojOj(Tvrtka tvr) {
        for (OrganizacijskaJedinica oj : ojLista) {
            if (oj.getId() == tvr.getNadredenaJednica()) {
                oj.dodajPodredenuJedinicu(vratiOj(tvr.getId()));
            }

        }
    }

    private boolean dodajSveOj(List<Tvrtka> listaOJ) {
        for (Tvrtka tvr : listaOJ) {
            OrganizacijskaJedinica tempOj = new OrganizacijskaJedinica(tvr.getId(), tvr.getNaziv());
            if (!dodajLokacijeOj(tempOj, tvr.getLokacije())) {
                return false;
            }
            ojLista.add(tempOj);
        }
        return true;
    }

    private boolean provjeriDuplikateLok(List<Integer> iskoristeneLokacije, List<Integer> idLok) {
        for (Integer iL : iskoristeneLokacije) {
            for (Integer iL2 : idLok) {
                if (Objects.equals(iL, iL2)) {
                    System.out.println("SYSTEM: Lokacija: " + iL2 + " je vec pridruzena drugoj organizacijskoj jedinici!");
                    return false;
                }
            }

        }
        return true;
    }

    public boolean sloziStablo() {
        List<Tvrtka> listaOJ = ListaTvrtke.getInstanca().getListaOrgJedinica();
        List<Integer> iskoristeneLokacije = new ArrayList<>();
        int ishodisna = 0;
        if (!dodajSveOj(listaOJ)) {
            return false;
        }
        for (Tvrtka tvr : listaOJ) {
            dodajNadredenojOj(tvr);
            if (tvr.getNadredenaJednica() == 0) {
                ishodisna++;
                tvrtka = vratiOj(tvr.getId());
            }
            if (!provjeriDuplikateLok(iskoristeneLokacije, tvr.getLokacije())) {
                return false;
            }
            iskoristeneLokacije.addAll(tvr.getLokacije());

        }
        if (ishodisna == 0) {
            System.out.println("SYSTEM: greska u izgradnji stabla! ne postoji ishodisna organizacijska jedinica!");
            return false;
        }
        if (ishodisna > 1) {
            System.out.println("SYSTEM: greska u izgradnji stabla! postoji više od jedne ishodisne organizacijske jedinice!");
            return false;
        }
        return true;
    }

}
