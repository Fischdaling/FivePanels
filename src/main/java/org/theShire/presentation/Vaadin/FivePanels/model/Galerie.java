package org.theShire.presentation.Vaadin.FivePanels.model;

import java.io.*;
import java.util.*;

public class Galerie {
    private String name;
    private ArrayList<Kunstwerk> kunstwerke;

    private static final Galerie INSTANCE = new Galerie();

    private Galerie() {
        this.name = "NewArts";
        kunstwerke = new ArrayList<>(100);
    }

    public static Galerie getInstance() {
        return INSTANCE;
    }

    //--------------------------------------- getters -----------------------
    public List<Kunstwerk> getKunstwerke() // Achtung: Aufrufer bekommt "Vollzugriff" auf die Kunstwerke "in unserer" Collection
    {
        return new ArrayList<>(kunstwerke);
    }

    public String getName() {
        return name;
    }

    //	--------------------------------------- setters -----------------------

    public void setName(String name) throws GalerieException {

        if (name != null) {
            this.name = name;
        } else {
            throw new GalerieException("Fehler bei setName(): null-Referenz fuer name erhalten");
        }
    }

    //  ------------------------------------ adders & removers --------------------------
    public void add(Kunstwerk kw) throws GalerieException {
        int maxKunstwerke = 100; // maximal 100 Kunstwerke
        if (kw != null)
            if (!kunstwerke.contains(kw))
                if (kunstwerke.size() < maxKunstwerke) // nicht mehr als maxKunstwerke moeglich
                    kunstwerke.add(kw);
                else
                    throw new GalerieException("Fehler bei add(): die Galerie ist voll, es sind bereits "
                            + maxKunstwerke + " Kunstwerke vorhanden\n" + kw);
            else
                throw new GalerieException("Fehler bei add(): kunstwerk bereits vorhanden\n" + kw);
        else
            throw new GalerieException("Fehler bei add(): null-Referenz fuer kunstwerk erhalten");
    }

    public void remove(int pos) throws GalerieException {
        if (pos >= 0 && pos < kunstwerke.size())
            kunstwerke.remove(pos);
        else
            throw new GalerieException("Falscher Index bei remove (int pos)!!");
    }

    public void remove(Kunstwerk kw) throws GalerieException {
        if (!kunstwerke.remove(kw))
            throw new GalerieException(
                    "Fehler beim Remove des Kunstwerks : " + kw + "\nKunstwerk nicht (mehr) in der Galerie");
    }

    public int remove(String kuenstler) throws GalerieException {
        int anz = 0;
        if (kuenstler != null) {
            Iterator<Kunstwerk> iter = kunstwerke.iterator();
            while (iter.hasNext()) {
                if ((iter.next()).getKuenstler().equals(kuenstler)) {
                    anz++;
                    iter.remove();
                }
            }
            return anz;
        } else
            throw new GalerieException("null-Referenz beim remove(kuenstler)");

    }

    public void removeAll() {
        kunstwerke.clear();
    }

    //  ------------------------------------ berechne --------------------------
    public double berechneGesamtVkWert() {
        double summe = 0.;
        for (Kunstwerk k : kunstwerke)
            summe += k.berechneVkWert();
        return summe;
    }

    public int berechneAnzSkulpturen() {
        int anz = 0;
        Iterator<Kunstwerk> it = kunstwerke.iterator();
        while (it.hasNext())
            if (it.next() instanceof Skulptur)
                anz++;
        return anz;
    }

    public int berechneAnzBilderVerkauft() {
        int anz = 0;
        Kunstwerk kw;
        Iterator<Kunstwerk> it = kunstwerke.iterator();
        while (it.hasNext()) {
            kw = it.next();
            if (kw instanceof Bild && kw.isVerkauft())
                anz++;
        }
        return anz;
    }

    // ------------------------------- Sort-Aufrufe ------------------------------
    public void sort(String kriterium) throws GalerieException {
        if (kriterium.equalsIgnoreCase("Kuenstler"))
            Collections.sort(kunstwerke, new Kunstwerk.KuenstlerComparator());
        else if (kriterium.equalsIgnoreCase("Verkaufswert")) {
            Collections.sort(kunstwerke, new Kunstwerk.WertComparator());
            Collections.reverse(kunstwerke); // weil absteigend
        } else if (kriterium.equalsIgnoreCase("Titel"))
            Collections.sort(kunstwerke, new Comparator<>() {
                public int compare(Kunstwerk kw1, Kunstwerk kw2) {
                    return kw1.getTitel().compareTo(kw2.getTitel());
                }
            });
        else
            throw new GalerieException("Falsches Sortier-Kriterium bei sort " + "(" + kriterium + ")");
    }

//  ------------------------------------  Dateien --------------------------

    //  ------------------------- serialisierte Dateien ------------------------
    public void saveKunstwerke() throws GalerieException {
        // TODO ergaenzen Sie hier Ihre Implementierung
    }

    public void loadKunstwerke() throws GalerieException {
        // TODO ergaenzen Sie hier Ihre Implementierung
    }

    //  --------------------------------- TextDateien --------------------------
    public void exportKunstwerkeCsv() throws GalerieException {
        // TODO ergaenzen Sie hier Ihre Implementierung
    }

    public void importKunstwerkeCsv(File file) throws GalerieException {
        if (file != null) {
            // TODO ergaenzen Sie hier Ihre Implementierung
        } else {
            throw new GalerieException("Fehler bei importKunstwerkeCsv(): null-Referenz fuer file erhalten");
        }
    }

    // ------------------------------- toStrings ------------------------------
    public String toString() {
        String trennZeile = "-----------------------------------------------------------------------------------------------\n";
        StringBuilder sb = new StringBuilder("Galerie ").append(name).append(" -> derzeit ").append(kunstwerke.size())
                .append(" Kunstwerke").append("\n");
        if (!kunstwerke.isEmpty()) {
            sb.append(trennZeile);
            for (Kunstwerk kw : kunstwerke)
                sb.append(kw).append("\n");
            sb.append(trennZeile).append("Derzeitiger Gesamt-Verkaufswert: EUR ").append(berechneGesamtVkWert());
        }
        return sb.toString();
    }

}
