package com.ngi.demo;

import java.time.LocalDateTime;
import java.util.Comparator;

public class Patient implements Comparator<Patient>{
    private String patientName;
    private int priority;
    private LocalDateTime checkInTime;

    public Patient() {}

    public Patient(String patientName, int priority) {
        this.patientName = patientName;
        this.priority = priority;
    }

    public String getPatientName() {
        return patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }
    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    @Override
    public int compare(Patient o1, Patient o2) {
        if(o1.getPriority() < o2.getPriority())
            return -1;
        else if(o1.getPriority() > o2.getPriority())
            return 1;
        else
            if(o1.getCheckInTime().isAfter(o2.getCheckInTime()))
                return 1;
            else if(o1.getCheckInTime().isBefore(o2.getCheckInTime()))
                return -1;
        
        return 0;
                
    }
}