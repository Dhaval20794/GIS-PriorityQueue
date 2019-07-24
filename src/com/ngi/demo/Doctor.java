package com.ngi.demo;

public class Doctor extends Thread {

    private String doctorName;
    private Patient currentPatient;
    private static final int DOCTOR_CURE_TIME = 15000;

    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Patient getCurrentPatient() {
        return currentPatient;
    }
    public void setCurrentPatient(Patient currentPatient) {
        this.currentPatient = currentPatient;
    }

    @Override
    public void run() {
        super.run();
        try {
            while (true) {
                
                if(PatientCheckInUtility.quit)
                    break;
                
                if (this.currentPatient == null) {
                    System.out.println("Please send patient to doctor");
                    synchronized (this) {
                        wait();
                    }
                    continue;
                }
                Thread.sleep(DOCTOR_CURE_TIME);
                setCurrentPatient(null);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
