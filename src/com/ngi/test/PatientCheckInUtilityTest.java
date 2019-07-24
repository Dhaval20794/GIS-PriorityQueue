package com.ngi.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

import com.ngi.demo.Doctor;
import com.ngi.demo.Patient;
import com.ngi.demo.PatientCheckInUtility;

public class PatientCheckInUtilityTest {

    public static void main(String[] args) throws Exception {

        PatientCheckInUtility checkInUtitlity = new PatientCheckInUtility();
        PriorityBlockingQueue<Patient> queue = checkInUtitlity.getPatientQueue();
        Doctor doctor = checkInUtitlity.getDoctor();
        Patient patient;

        File file = new File("src/com/ngi/test/addUpdatePatient.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        System.out.println("GSI Hospital Check-in System");
        System.out.println("----------------------------------------");
        System.out.print("Priorities :- 0-Critical or Ad-hoc, 1-Gunshot Wound, 2-Broken Leg, 3-Paper Cut");
        int choice = 0;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if(PatientCheckInUtility.quit)
                        break;
                    try {
                        checkInUtitlity.sendPatientToDoctor();
                        checkInUtitlity.checkCurrentPatientQueue();
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println("read line = " + line);
            try {
                choice = Integer.parseInt(line);
            } catch (Exception e) {
                System.out.println("Please enter valid number.\n");
                continue;
            }

            switch (choice) {
            case 1:
                patient = new Patient();
                line = bufferedReader.readLine();
                System.out.println("read line = " + line);
                patient.setPatientName(line);

                Iterator<Patient> iterator = queue.iterator();
                boolean isPatientExists = false;
                while (iterator.hasNext()) {
                    Patient iteratorPatient = iterator.next();
                    if (patient.getPatientName().equals(iteratorPatient.getPatientName())) {
                        System.out.println("Patient with same name already exists.");
                        isPatientExists = true;
                        break;
                    }
                }
                if (isPatientExists) {
                    line = bufferedReader.readLine();
                    break;
                }

                try {
                    line = bufferedReader.readLine();
                    System.out.println("read line = " + line);
                    patient.setPriority(Integer.parseInt(line));
                    if(patient.getPriority() < 0 || patient.getPriority() > 3) {
                        System.out.println("Please enter priority from 0 to 3");
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Please enter valid priority.");
                    break;
                }

                patient.setCheckInTime(LocalDateTime.now());
                queue.add(patient);
                break;
            case 2:
                patient = new Patient();
                line = bufferedReader.readLine();
                patient.setPatientName(line);
                System.out.println("read line = " + line);

                try {
                    line = bufferedReader.readLine();
                    System.out.println("read line = " + line);
                    patient.setPriority(Integer.parseInt(line));
                    if(patient.getPriority() < 0 || patient.getPriority() > 3) {
                        System.out.println("Please enter priority from 0 to 3");
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Please enter valid priority.");
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
            checkInUtitlity.checkCurrentPatientQueue();
            if (doctor.getCurrentPatient() == null) {
                if (queue.size() > 0) {
                    Patient currentPatient = queue.poll();
                    doctor.setCurrentPatient(currentPatient);
                    synchronized (doctor) {
                        doctor.notify();
                    }
                }
            }
        }
        bufferedReader.close();
    }
}
