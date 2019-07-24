
package com.ngi.demo;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityQueueDemo {
    
    public static void main(String[] args) {
        
        PatientCheckInUtility checkInUtitlity = new PatientCheckInUtility();
        PriorityBlockingQueue<Patient> queue = checkInUtitlity.getPatientQueue();
        Patient patient;
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("GSI Hospital Check-in System");
        System.out.println("----------------------------------------");
        System.out.print("Priorities :- 0-Critical or Ad-hoc, 1-Gunshot Wound, 2-Broken Leg, 3-Paper Cut\n\n");
        int choice = 0;
        while(choice != 5) {
            try {
                checkInUtitlity.checkCurrentPatientQueue();
                System.out.print("1)New Patient 2)Edit Priority 3)Send to Doctor 4)Clear Queue 5)Quit :- ");
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter valid number.\n");
                scanner.nextLine();
                continue;
            }
            
            switch (choice) {
            case 1:
                patient = new Patient();
                System.out.print("Name : ");
                scanner.nextLine();
                patient.setPatientName(scanner.nextLine());
                Iterator<Patient> iterator = queue.iterator();
                boolean isPatientExists = false;
                while(iterator.hasNext()) {
                    Patient iteratorPatient = iterator.next();
                    if(patient.getPatientName().equals(iteratorPatient.getPatientName())) {
                        System.out.println("Patient with same name already exists.");
                        isPatientExists = true;
                        break;
                    }
                }
                if(isPatientExists)
                    break;
                try {
                    System.out.print("Priority : ");
                    patient.setPriority(scanner.nextInt());
                    if(patient.getPriority() < 0 || patient.getPriority() > 3) {
                        System.out.println("Please enter priority between 0 to 3.");
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Please enter valid priority.");
                    scanner.nextLine();
                    break;
                }
                patient.setCheckInTime(LocalDateTime.now());
                queue.add(patient);
                break;
            case 2:
                patient = new Patient();
                System.out.print("Name : ");
                scanner.nextLine();
                patient.setPatientName(scanner.nextLine());
                
                try {
                    System.out.print("New Priority : ");
                    patient.setPriority(scanner.nextInt());
                    if(patient.getPriority() < 0 || patient.getPriority() > 3) {
                        System.out.println("Please enter priority from 0 to 3");
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Please enter valid priority.");
                    scanner.nextLine();
                    break;
                }
                checkInUtitlity.editPriority(patient);
                break;
                
            case 3:
                checkInUtitlity.sendPatientToDoctor();
                break;
                
            case 4:
                checkInUtitlity.setPatientQueue(new PriorityBlockingQueue<>(10, new Patient()));
                queue = checkInUtitlity.getPatientQueue();
                break;
                
            case 5:    
                System.out.println("Thank you for using GIS Hospital check-in system.");
                PatientCheckInUtility.quit = true;
                break;
                
            default:
                System.out.println("Please enter valid number.");
                break;
            }
        }
        scanner.close();
    }
}
