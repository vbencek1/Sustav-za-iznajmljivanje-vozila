package vbencek.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vbencek.Sustav;
import vbencek.composite.Lokacija;
import vbencek.items.ListaLokacija;
import vbencek.items.ListaOsoba;
import vbencek.items.ListaRacuna;
import vbencek.items.ListaVozila;
import vbencek.items.Osoba;
import vbencek.items.Racun;
import vbencek.items.Vozilo;

public class KorisnikRacuni implements Aktivnost {

    private boolean provjeri(String ulaz) {
        String datum = "((0[1-9]|[12][0-9]|3[01]).(1[012]|0[1-9]).(19|20)\\d\\d)";
        String sintaksa = "^10; [0-9]+ " + datum + " " + datum;
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(ulaz);
        boolean status = m.matches();
        if (!status) {
            System.out.println(" -Neispravna naredba!");
        }
        return status;
    }
    
    private void ispisiRacune(Racun rac,Osoba osoba, int dt,int dc,int dd){
        Lokacija lokacijaNajma=ListaLokacija.getInstanca().pretragaLokacija(rac.getLokacijaNajma());   
        Lokacija lokacijaVracanja=ListaLokacija.getInstanca().pretragaLokacija(rac.getLokacijaVracanja());
        Vozilo vrstaVozila=ListaVozila.getInstanca().pretragaVozila(rac.getVrstaVozila());
        String strOsoba="("+osoba.getId()+") "+osoba.getImePrezime();
        String strLokNajma="("+lokacijaNajma.getId()+") "+lokacijaNajma.getNaziv();
        String strLokVrac="Vozilo još nije vraceno";
        if(lokacijaVracanja!=null){
            strLokVrac="("+lokacijaVracanja.getId()+") "+lokacijaVracanja.getNaziv();
        }
        String strVrstaVoz="("+vrstaVozila.getId()+") "+vrstaVozila.getNaziv();
        String placen="placen";
        if(!rac.isPlacen()) placen="nije placen";
        System.out.format("|%"+dc+"d|%-"+dt+"s|%-"+dt+"s|%-"+dt+"s|%-"+dt+"s|%-"+dt+"s|%"+(dc+dd)+"."+dd+"f|%-"+dt+"s| %n",
                rac.getId(),rac.getVrijemeIzdavanja(),strOsoba,strVrstaVoz,strLokNajma,
                strLokVrac,rac.getIznos(),placen);
    }
    
    @Override
    public void izvrsi(String operacija) {
        if (provjeri(operacija)) {
            int idKorisnika = Integer.parseInt(operacija.split(";")[1].trim().split(" ")[0]);
            Osoba osoba = ListaOsoba.getInstanca().pretraziOsobu(idKorisnika);
            if (osoba != null) {       
                System.out.println("\nPregled računa osobe: "+osoba.getImePrezime()+"\n");
                int dt = Sustav.getInstanca().getTekst();
                int dc = Sustav.getInstanca().getCijeli();
                int dd = Sustav.getInstanca().getDecimala();
                String vrijemeOd=operacija.split(";")[1].trim().split(" ")[1];
                String vrijemeDo=operacija.split(";")[1].trim().split(" ")[2];
                List<Racun> racuniOsobe=ListaRacuna.getInstanca().racuniOsobe(idKorisnika, vrijemeOd, vrijemeDo);
                System.out.format("|%" + dc + "s|%-" + dt + "s|%-" + dt + "s|%-" + dt + "s|%-" + dt + "s|%-" + dt + "s|%-" + (dc + dd) + "s|%-" + dt + "s| %n",
                        "ID", "Vrijeme izdavanja", "Osoba", "Vrsta", "Lokacija najma", "Lokacija vracanja", "Iznos", "Status");
                for(Racun rac: racuniOsobe){
                    if(!rac.isPlacen())
                    ispisiRacune(rac,osoba, dt, dc, dd);
                }
                for(Racun rac: racuniOsobe){
                    if(rac.isPlacen())
                    ispisiRacune(rac,osoba, dt, dc, dd);
                }
            } else {
                System.out.println(" -Osoba sa ID: " + idKorisnika + " ne postoji!");
            }
        }
    }
}
