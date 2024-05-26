package org.theShire.repository;

import org.theShire.domain.exception.MediaException;
import org.theShire.domain.exception.MedicalCaseException;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.messenger.Chat;

import java.io.*;

public class MessangerRepository extends AbstractRepository<Chat>{
    @Override
    public void saveEntryMap(String filepath) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
            for (Chat value : entryMap.values()) {
                String csvFile = value.toCSVString();
                bufferedWriter.write(csvFile);
            }
            System.out.println("Successfully saved " + filepath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("File %s is not found", filepath), e);

        } catch (IOException e) {
            throw new RuntimeException(String.format("File %s has a problem", filepath), e);
        }
    }
    @Override
    public void loadEntryMap(String filepath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(";");
                //TODO
            }
        } catch (FileNotFoundException e) {
            throw new MedicalCaseException(e.getMessage());
        } catch (IOException e) {
            throw new MediaException(e.getMessage());
        }
    }
}
