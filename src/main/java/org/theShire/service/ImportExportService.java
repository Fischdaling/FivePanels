package org.theShire.service;

import org.theShire.domain.exception.MedicalCaseException;
import org.theShire.domain.exception.MedicalDoctorException;
import org.theShire.domain.exception.MessengerException;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.messenger.Chat;

import java.io.*;
import java.rmi.RemoteException;

import static org.theShire.service.CaseService.caseRepo;
import static org.theShire.service.ChatService.messengerRepo;
import static org.theShire.service.UserService.userRepo;

public class ImportExportService {

    public static void exportDataToCSV(String filepath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            // Export users
            try {
                writer.write("Users\n");
                for (User user : userRepo.findAll()) {
                    writer.write(user.toCSVString());
                    writer.newLine();
                }
            }catch (IOException e){
                throw new MedicalDoctorException(e.getMessage());
            }
            // Export cases
            try {
                writer.write("Cases\n");
                for (Case medCase : caseRepo.findAll()) {
                    writer.write(medCase.toCSVString());
                    writer.newLine();
                }
            }catch (IOException e){
                throw new MedicalCaseException(e.getMessage());
            }
            // Export chats
            try {

                writer.write("Chats\n");
                for (Chat chat : messengerRepo.findAll()) {
                    writer.write(chat.toCSVString());
                    writer.newLine();
                }
            }catch (IOException e){
                throw new MessengerException(e.getMessage());
            }
            System.out.println("Data exported to " + filepath);
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void importDataFromCSV(String filepath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            String section = "";

            while ((line = reader.readLine()) != null) {
                if (line.equals("Users")) {
                    section = "Users";
                } else if (line.equals("Cases")) {
                    section = "Cases";
                } else if (line.equals("Chats")) {
                    section = "Chats";
                } else {
                    if (section.equals("Users")) {
                        User user = User.fromCSVString(line);
                        userRepo.save(user);
                    } else if (section.equals("Cases")) {
                        Case medCase = Case.fromCSVString(line);
                        caseRepo.save(medCase);
                    } else if (section.equals("Chats")) {
                        Chat chat = Chat.fromCSVString(line);
                        messengerRepo.save(chat);
                    }
                }
            }
        }catch (IOException e){
            throw new RemoteException(e.getMessage());
        }
    }

}
