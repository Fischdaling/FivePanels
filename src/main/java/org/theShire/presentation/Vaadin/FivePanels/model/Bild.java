package org.theShire.presentation.Vaadin.FivePanels.model;

import java.io.Serial;

public class Bild extends Kunstwerk {
    @Serial
    private static final long serialVersionUID = 1L;
    private boolean sehrGefragt;

    public Bild() {
        super();
    }

    public Bild(String kuenstler, String titel, int laenge, int breite, double ekPreis,
                boolean sehrGefragt) throws GalerieException {
        super(kuenstler, titel, laenge, breite, ekPreis);
        setSehrGefragt(sehrGefragt);
    }

    public Bild(String[] zeilenTeile) throws GalerieException {
        setAllFields(zeilenTeile);
    }

    //---------------------------------- getter ----------------------
    public boolean isSehrGefragt() {
        return sehrGefragt;
    }

    //---------------------------------- setter sonstige ----------------------
    public void setSehrGefragt(boolean sehrGefragt) {
        this.sehrGefragt = sehrGefragt;
    }

    public void setLaenge(int laenge) throws GalerieException {
        if (laenge > 0 && laenge <= 1000)   //  10 Meter
        {
            super.setLaenge(laenge);
        } else {
            throw new GalerieException("Falscher Parameterwert bei Laenge (" + laenge + ") bei " +
                    getKuenstler() + "'s  \"" + getTitel() + "\" !");
        }
    }

    public void setBreite(int breite) throws GalerieException {
        if (breite > 0 && breite <= 5000)    //  5 Meter
        {
            super.setBreite(breite);
        } else {
            throw new GalerieException("Falscher Parameterwert bei Breite (" + breite + ")!");
        }
    }

    public void setAllFields(String[] zeilenTeile) throws GalerieException {
        if (zeilenTeile != null && zeilenTeile.length > 0)
            if (zeilenTeile[0] != null) {
                // model.Bild;Klimt;Judith;70;80;50000.0;false;true
                //      [0]    [1]   [2]  [3] [4]  [5]   [6]    [7]
                super.setAllFields(zeilenTeile);
                if (zeilenTeile[7] != null)
                    setSehrGefragt(Boolean.parseBoolean((zeilenTeile[7]).trim()));
                else
                    throw new GalerieException("Fehler bei Bild setAllFields(): die eingelesene Information fuer sehrGefragt ist falsch\n" +
                            zeilenTeile[7]);
            } else
                throw new GalerieException("Fehler bei Bild setAllFields(): die eingelesenen Informationen fuer das Bild sind falsch");
        else
            throw new GalerieException("Fehler bei Bild setAllFields(): null-Referenz fuer zeilenTeile erhalten oder eine leere Zeile");
    }

    //--------------------------------- andere ---------------------
    public double berechneVkWert() {
        if (sehrGefragt)
            return getEkPreis() * 1.5;
        else
            return getEkPreis() * 1.25;
    }


    // -------------------- equals / hashCode -------------------------
    // wie Superklasse

    // ----------------------------- toString -------------------------
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" - ").append(berechneVkWert()).append(" - ").append(getLaenge()).append("x").append(getBreite());
        return sb.toString();
    }

    public String toStringCsv() {
        String sep = ";";
        StringBuilder sb = new StringBuilder(super.toStringCsv());
        sb.append(sep).append(sehrGefragt);
        return sb.toString();
    }
}
