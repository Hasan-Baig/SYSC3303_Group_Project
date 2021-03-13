# SYSC3303_Group_Project

Group 1 Members
  - Abeer Rafiq
  - Alden Wan Yeung Ng
  - Emma Boulay
  - Hasan Baig
  - Rutvik Shah

Project Iteration 3 - Multiple Cars and System Distribution.

- Purpose

The purpose of this iteration is to split the system up into three separate programs that can run on 
three separate computers and communicate with each other using UDP. The Scheduler is now be used to coordinate 
the movement of cars such that each car carries roughly the same number of passengers as all of the others and 
so that the waiting time for passengers at floors is minimized. The state machines for each car executes
independently of each other, but they share their position with the scheduler. The scheduler will choose which 
elevator is used to service a given request.

- Breakdown of Responsibilities

  ALL
    -> 
  Abeer 
  -> Class Diagram
  Emma
    -> Creating UDP communication between subsystems
    -> Implemented the algorithm in the scheduler to determine which elevator will serivce which request
    -> Implemented the setup coordination between the ElevatorSubsystem, FloorSubsystem and Scheduler
    -> Modified SchedulerStateMachineTest JUnit test to send mock data over UDP to scheduler
    -> Creat ElevatorSubsystem to control multiple elevators
    -> Creat FloorSubsystem to control multiple floors and send request to scheduler with required delay
  Alden + Hasan + Rutvik
    -> elevatorSubsystem and related classes
    -> Information.java
    -> ElevatorState.java and related classes
    -> Overall State Diagram
    -> Elevator State Diagram   
    -> floorSubsystem and related classes
    
- File Names

ElevatorSystem.java
	-> The main class which initializes the floorSubsystem(client), elevatorSubsystem(client) and the 
     Scheduler(server) threads and then starts them.
     
Elevator.java
	-> The Elevator thread will try to get requests from the scheduler while it is stationary. 
    -> It will then handle those requests and pass an acknowledgment to the Scheduler to be passed back to the floorSubsystem.
    -> It also changes the states of the state machine.

ElevatorState.java
	-> This interface holds all the methods that the Elevator States use to implement the logic.
	
IdleES.java
	-> This class implements the ElevatorState interface to set the State to Idle and change characteristics of the Elevator.

MovingES.java
	-> This class implements the ElevatorState interface to set the State to Moving and change characteristics of the Elevator.

ArrivedES.java
	-> This class implements the ElevatorState interface to set the State to Arrived and change characteristics of the Elevator.

ElevatorTest.java
	-> A jUNIT test class to test the methods of the Elevator class.

Floor.java
  -> The Floor thread will read in events from an input file and will try to put a request in the scheduler 
     while the previous request has been acknowledged.
	
FloorTest.java
	-> A jUNIT test class to test the methods of the Floor class. Currently empty
	
Information.java
	-> A class that stores the constants of Iteration1 and the enumerators for the lamp state, motor direction, 
     door state and direction lamp.

Scheduler.java
	-> The scheduler thread is a shared resource between the floor and elevator subsystems. The floorSubsystem 
     will place requests into the Scheduler and the elevatorSubsystem will get and handle those requests.

SchedulerTest.java
	-> A jUNIT test class to test the methods of the Scheduler class.

SchedulerStateMachine.java
	-> The scheduler statemachine implements runnable and is in charge of implementing the state machine for the
	scheduler
	
SchedulerStateMachine.java
	-> This test class tests the state transistions of the state machine with how it interprets test data.
	
floorRequests.txt
  -> A text file containing 100 event requests in the form of 
          "TIME FLOOR FLOORBUTTON CARBUTTON"

- Set-up Instructions

	1. Unzip project folder and import into Eclipse
	2. Run ElevatorSystem.java file (main program)
	3. Run SchedulerStateMachineTest.java JUnit file to run unit test
 