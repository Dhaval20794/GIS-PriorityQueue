package com.ngi.demo;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PatientCheckInUtility {

    private PriorityBlockingQueue<Patient> patientQueue;
    private Doctor doctor;
    public volatile static boolean quit = false;

    public PatientCheckInUtility() {
        patientQueue = new PriorityBlockingQueue<>(10, new Patient());
        doctor = new Doctor();
        doctor.start();
    }

    public PriorityBlockingQueue<Patient> getPatientQueue() {
        return patientQueue;
    }
    public void setPatientQueue(PriorityBlockingQueue<Patient> patientQueue) {
        this.patientQueue = patientQueue;
    }

    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void checkCurrentPatientQueue() {
        if (patientQueue.isEmpty()) {
            System.out.println("(No Patient)\n");
            return;
        }
        PriorityQueue<Patient> patientQueueCopy = new PriorityQueue<>(new Patient());
        patientQueueCopy.addAll(patientQueue);

        System.out.print("\nPatients Queue : (");
        while (!patientQueueCopy.isEmpty()) {
            Patient patient = patientQueueCopy.poll();
            System.out.print(patient.getPriority() + "-" + patient.getPatientName());
            if (patientQueueCopy.size() > 0)
                System.out.print(", ");
        }
        System.out.print(")\n");
    }

    public void editPriority(Patient updatePatient) {
        Iterator<Patient> iterator = patientQueue.iterator();
        boolean priorityChanged = false;

        while (iterator.hasNext()) {
            Patient patient = iterator.next();
            if (patient.getPatientName().equalsIgnoreCase(updatePatient.getPatientName())) {
                patientQueue.remove(patient);
                patient.setPriority(updatePatient.getPriority());
                patientQueue.add(patient);
                priorityChanged = true;
                break;
            }
        }
        if(!priorityChanged)
            System.out.println("Patient not found.");
    }

    public void sendPatientToDoctor() {
        if (doctor.getCurrentPatient() != null) {
            System.out.println("Doctor already has patient.");
            return;
        }
        if(patientQueue.isEmpty())
            return;
        Patient currentPatient = patientQueue.poll();
        doctor.setCurrentPatient(currentPatient);
        synchronized (doctor) {
            doctor.notify();
        }
    }
}
