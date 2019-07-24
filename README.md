# NGI-PriorityQueue

## Requirements
To run this application you will be needing
* JDK 8 or higher

## Building and Running the application
compile all classes and run 'PriorityQueueDemo.java' java class

## Assumptions
- We are assuming that when one patient is in checkup at that time if another patient with critical priority comes at that time new patient will be arranged in priority queue by nurse. It will not remove the current patient and replace it with new patient.
- We are assuming that priority can be change in any manner by nurse. i.e from high to low and low to high.
- We are assuming that when 'Patient1' with priority with 2 is in queue. Meanwhile nurse changed 'Patient2' priority from 3 to 2 in that case( 2 patients with same priority) checkin time will be considered to arrange the queue.
- As per the requirement guideline of not using any other framework outside jdk, we didn't use junit or added maven build script.

## Notes
- Doctor thread will start from the time program initialize. Once Patient is assigned to Doctor, it will wait for 15 sec for checkup and will release the Patient and will ask nurse to send the next Patient.

## Testcases
to run the testcases compile all classes and run 'PatientCheckInUtilityTest.java' java class
'PatientCheckInUtilityTest.java' will read inputs from 'addUpdatePatient.txt' and will cover all the test cases.