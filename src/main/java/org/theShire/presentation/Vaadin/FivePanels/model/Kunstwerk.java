package org.theShire.presentation.Vaadin.FivePanels.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

public abstract class Kunstwerk implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String kuenstler;
    private String titel;
    private int laenge, breite;
    private double ekPreis;
    private boolean verkauft;

    public Kunstwerk() {

    }

    public Kunstwerk(String kuenstler, String titel, int laenge, int breite, double ekPreis) throws GalerieException {
        setKuenstler(kuenstler);
        setTitel(titel);
        setLaenge(laenge);
        setBreite(breite);
        setEkPreis(ekPreis);
    }

    public Kunstwerk(String[] zeilenTeile) throws GalerieException {
        setAllFields(zeilenTeile);
    }

    //---------------------------------- getter ----------------------
    public double getEkPreis() {
        return ekPreis;
    }

    public String getKuenstler() {
        return kuenstler;
    }

    public String getTitel() {
        return titel;
    }

    public int getBreite() {
        return breite;
    }

    public int getLaenge() {
        return laenge;
    }

    public boolean isVerkauft() {
        return verkauft;
    }

    //---------------------------------- setter ----------------------
    public void setEkPreis(double ekPreis) throws GalerieException {
        if (ekPreis > 0. && ekPreis < 100000000.)
            this.ekPreis = ekPreis;
        else
            throw new GalerieException("Falscher Parameterwert f. Einkaufs-Preis bei setEkPreis (" + ekPreis + ") !!!");
    }

    public void setKuenstler(String kuenstler) throws GalerieException {
        if (kuenstler != null)
            this.kuenstler = kuenstler;
        else
            throw new GalerieException("Null-Referenz f. Kuenstler !!!");
    }

    public void setTitel(String titel) throws GalerieException {
        if (titel != null)
            this.titel = titel;
        else
            throw new GalerieException("Null-Referenz f. Titel !!!");

    }

    public void setLaenge(int laenge) throws GalerieException {
        if (laenge > 0 && laenge <= 10000)   //  100 Meter
            this.laenge = laenge;
        else
            throw new GalerieException("Falscher Parameterwert bei Laenge (" + laenge + ") bei " +
                    getKuenstler() + "'s  \"" + getTitel() + "\" !");
    }

    public void setBreite(int breite) throws GalerieException {
        if (breite > 0 && breite <= 10000)    //  100 Meter
            this.breite = breite;
        else
            throw new GalerieException("Falscher Parameterwert bei Breite (" + breite + ")!");
    }

    public void setVerkauft(boolean verkauft) {
        this.verkauft = verkauft;
    }

    public void setAllFields(String[] zeilenTeile) throws GalerieException {
        if (zeilenTeile != null && zeilenTeile.length > 0)
            if (zeilenTeile.length > 0 && zeilenTeile[0] != null) {
                // String kuenstler, String titel, int laenge, int breite, double ekPreis, boolean verkauft
                //          [1]              [2]         [3]         [4]            [5]             [6]
                setKuenstler(zeilenTeile[1].trim());
                setTitel(zeilenTeile[2].trim());
                if (zeilenTeile[3] != null)
                    setLaenge(Integer.parseInt((zeilenTeile[3]).trim()));
                else
                    throw new GalerieException("Fehler bei Kunstwerk setAllFields(): die eingelesene Information fuer laenge ist falsch\n" +
                            zeilenTeile[3]);
                if (zeilenTeile[4] != null)
                    setBreite(Integer.parseInt((zeilenTeile[4]).trim()));
                else
                    throw new GalerieException("Fehler bei Kunstwerk setAllFields(): die eingelesene Information fuer breite ist falsch\n" +
                            zeilenTeile[4]);
                if (zeilenTeile[5] != null)
                    setEkPreis(Float.parseFloat((zeilenTeile[5]).trim()));
                else
                    throw new GalerieException("Fehler bei Kunstwerk setAllFields(): die eingelesene Information fuer ekPreis ist falsch\n" +
                            zeilenTeile[5]);
                if (zeilenTeile[6] != null)
                    setVerkauft(Boolean.parseBoolean((zeilenTeile[6]).trim()));
                else
                    throw new GalerieException("Fehler bei Kunstwerk setAllFields(): die eingelesene Information fuer verkauft ist falsch\n" +
                            zeilenTeile[5]);
            } else
                throw new GalerieException("Fehler bei Kunstwerk setAllFields(): die eingelesenen Informationen fuer das Kunstwerk sind falsch");
        else
            throw new GalerieException("Fehler bei Kunstwerk setAllFields(): null-Referenz fuer zeilenTeile erhalten oder eine leere Zeile");
    }

    // -------------------------------- abstracts ---------------------
    public abstract double berechneVkWert();
    // -------------------------------- andere ------------------------

    // -------------------- equals / hashCode -------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kunstwerk kunstwerk = (Kunstwerk) o;
        return getLaenge() == kunstwerk.getLaenge() && getBreite() == kunstwerk.getBreite() && Double.compare(getEkPreis(), kunstwerk.getEkPreis()) == 0 && Objects.equals(getKuenstler(), kunstwerk.getKuenstler()) && Objects.equals(getTitel(), kunstwerk.getTitel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKuenstler(), getTitel(), getLaenge(), getBreite(), getEkPreis());
    }

    // ----------------------------- toString -------------------------
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(kuenstler).append(" - ").append(titel).append(" - ")
                .append(String.format(Locale.UK, "%.1f", ekPreis)).append(" - "); // Locale.UK bringt bei float/double einen Dezimal.PUNKT statt Komma
        sb.append(verkauft?"verkauft":"verfuegbar");
        return sb.toString();
    }

    public String toStringCsv() {
        String sep = ";";
        StringBuilder sb = new StringBuilder();
        // Class, String kuenstler, String titel, int laenge, int breite, double ekPreis, boolean verkauft
        //   [0]       [1]              [2]         [3]         [4]            [5]             [6]
        sb.append(getClass().getSimpleName()) //[0]
                .append(sep).append(kuenstler).append(sep).append(titel).append(sep) // [1] [2]
                .append(laenge).append(sep).append(breite).append(sep) // [3] [4]
                .append(String.format(Locale.UK, "%.1f", ekPreis)).append(sep)     // [5] Locale.UK bringt bei float/double
                .append(verkauft); // [6]											// einen Dezimal.PUNKT statt Komma
        return sb.toString();
    }

    //  --------------------------------- Comparatoren ------------------------------
    public static class WertComparator implements Comparator<Kunstwerk> {
        public int compare(Kunstwerk kw1, Kunstwerk kw2) {
            if (kw1.berechneVkWert() > kw2.berechneVkWert())  // weil double
                return 1;
            else if (kw1.berechneVkWert() < kw2.berechneVkWert())
                return -1;
            else
                return 0;
        }
    }

    public static class KuenstlerComparator implements Comparator<Kunstwerk> {
        public int compare(Kunstwerk kw1, Kunstwerk kw2) {
            return kw1.getKuenstler().compareTo(kw2.getKuenstler());
        }
    }
}
