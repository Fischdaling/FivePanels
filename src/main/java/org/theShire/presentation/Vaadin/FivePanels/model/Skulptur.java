package org.theShire.presentation.Vaadin.FivePanels.model;

import java.io.Serial;
import java.util.Objects;

public class Skulptur extends Kunstwerk {
    @Serial
    private static final long serialVersionUID = 1L;
    private int hoehe;
    private String material;
    private boolean sehrSelten;

    public Skulptur() {
        super();
    }

    public Skulptur(String kuenstler, String titel, int laenge, int breite, double ekPreis, int hoehe, String material)
            throws GalerieException {
        super(kuenstler, titel, laenge, breite, ekPreis);
        setHoehe(hoehe);
        setMaterial(material);
    }

    // -------------------------------------- getter -----------------------------
    public int getHoehe() {
        return hoehe;
    }

    public String getMaterial() {
        return material;
    }

    public boolean isSehrSelten() {
        return sehrSelten;
    }

    // -------------------------------------- setter -----------------------------
    public void setHoehe(int hoehe) throws GalerieException {
        if (hoehe > 0 && hoehe <= 2000) // 20 Meter
            this.hoehe = hoehe;
        else
            throw new GalerieException("Falscher Parameterwert fuer Hoehe (" + hoehe + ")!");
    }

    public void setLaenge(int laenge) throws GalerieException {
        if (laenge > 0 && laenge <= 2000)   //  20 Meter
            super.setLaenge(laenge);
        else {
            throw new GalerieException("Falscher Parameterwert fuer Laenge (" + laenge + ") bei " +
                    getKuenstler() + "'s  \"" + getTitel() + "\" !");
        }
    }

    public void setBreite(int breite) throws GalerieException {
        if (breite > 0 && breite <= 10000)    //  100 Meter
            super.setBreite(breite);
        else {
            throw new GalerieException("Falscher Parameterwert fuer Breite (" + breite + ")!");
        }
    }

    public void setMaterial(String material) throws GalerieException {
        if (material != null)
            this.material = material;
        else
            throw new GalerieException("null-Referenz f. Material !!!");
    }

    public void setSehrSelten(boolean sehrSelten) {
        this.sehrSelten = sehrSelten;
    }

    public void setAllFields(String[] zeilenTeile) throws GalerieException {
        if (zeilenTeile != null && zeilenTeile.length > 0)
            if (zeilenTeile[0] != null) {
                // model.Skulptur;Auguste Rodin;Wasserspiel;500;500;100000.0;false;500;Eisen u. Stahl;false
                //      [0]         [1]             [2]     [3] [4]  [5]     [6]    [7]   [8]          [9]
                super.setAllFields(zeilenTeile);
                if (zeilenTeile[7] != null)
                    setHoehe(Integer.parseInt((zeilenTeile[7]).trim()));
                else
                    throw new GalerieException("Fehler bei Skulptur setAllFields(): die eingelesene Information fuer hoehe ist falsch\n" +
                            zeilenTeile[7]);
                if (zeilenTeile[8] != null)
                    setMaterial((zeilenTeile[8]).trim());
                else
                    throw new GalerieException("Fehler bei Skulptur setAllFields(): die eingelesene Information fuer material ist falsch\n" +
                            zeilenTeile[8]);
                if (zeilenTeile[9] != null)
                    setSehrSelten(Boolean.parseBoolean((zeilenTeile[9]).trim()));
                else
                    throw new GalerieException("Fehler bei Skulptur setAllFields(): die eingelesene Information fuer sehrSelten ist falsch\n" +
                            zeilenTeile[9]);
            } else
                throw new GalerieException("Fehler bei Skulptur setAllFields(): die eingelesenen Informationen fuer die Skulptur sind falsch");
        else
            throw new GalerieException("Fehler bei Skulptur setAllFields(): null-Referenz fuer zeilenTeile erhalten oder eine leere Zeile");
    }

    // -------------------------------------- andere -----------------------------
    public double berechneVkWert() {
        if (sehrSelten)
            return getEkPreis() * 2;
        else
            return getEkPreis() * 1.5;
    }

    // -------------------- equals / hashCode -------------------------


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Skulptur skulptur = (Skulptur) o;
        return getHoehe() == skulptur.getHoehe() && Objects.equals(getMaterial(), skulptur.getMaterial());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getHoehe(), getMaterial());
    }

    // ----------------------------- toString -------------------------
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" - ").append(berechneVkWert()).append(" - ").append(material);
        return sb.toString();
    }

    public String toStringCsv() {
        String sep = ";";
        StringBuilder sb = new StringBuilder(super.toStringCsv());
        sb.append(sep).append(hoehe).append(sep).append(material).append(sep).append(sehrSelten);
        return sb.toString();
    }
}
