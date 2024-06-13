package org.theShire.presentation.Vaadin.FivePanels.service;

import org.theShire.presentation.Vaadin.FivePanels.model.Galerie;
import org.theShire.presentation.Vaadin.FivePanels.model.GalerieException;
import org.theShire.presentation.Vaadin.FivePanels.model.Kunstwerk;

import java.io.File;
import java.util.List;

public class GalerieService {

    private static final GalerieService INSTANCE = new GalerieService();

    private final Galerie galerie;

    private GalerieService() {
        galerie = Galerie.getInstance();
    }

    // ------------------------ getter -----------------------------
    public static GalerieService getInstance() {
        return INSTANCE;
    }

    public String getName() {
        return galerie.getName();
    }

    // ------------------- Service-Methoden ------------------------
    public List<Kunstwerk> findAll() {
        return galerie.getKunstwerke();
    }

    public boolean save(Kunstwerk kunstwerk) throws GalerieException {
        if(kunstwerk == null) {
            // LOGGING
            return false;
        }
        galerie.add(kunstwerk);
        return true;
    }

    public boolean delete(Kunstwerk kunstwerk) throws GalerieException {
        if (kunstwerk == null) {
            // LOGGING
            return false;
        }
        galerie.remove(kunstwerk);
        return true;
    }

    public int count() {
        return galerie.getKunstwerke().size();
    }

    public void deleteAll() {
        galerie.removeAll();
    }

    public void exportCsv() throws GalerieException {
        galerie.exportKunstwerkeCsv();
    }

    public void importCsv(File file) throws GalerieException {
        galerie.importKunstwerkeCsv(file);
    }
}
