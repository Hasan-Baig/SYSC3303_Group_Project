/**
 * Scheduler.java
 * 
 * The Scheduler is responsible for handling incoming requests from the floor Subsystem and passing requests
 * to the elevator subsystem. This is done using a shared resource between the three threads and with the 
 * implementation of a state machine. 
 *
 * @version 1.0
 * @author Emma Boulay, Abeer Rafiq
 *
 * (Group 1 - SYSC 3303 L2)
 *
 */
package ElevatorProject;

import java.util.ArrayList;
import java.util.HashMap;

public class Scheduler {
	/* Byte array used to acknowledge requests */
	private byte[] ack = null;
	
	/* ArrayList that records all the floor requests */
	private ArrayList<byte[]> workRequests = new ArrayList<>(); 
	
	/* Hashmap that keeps track of requests: key is elevator number and value is an arraylist of floor numbers */
	private HashMap<Integer, ArrayList<Integer>> arrivals = new HashMap<Integer, ArrayList<Integer>>();

	/**
	 * The default constructor.
	 */
	public Scheduler() { }

	/**
	 * This method puts a floor request into the scheduler. This method will return
	 * when there is space for the floor request in the scheduler.
	 * 
	 * @param request A byte array representing the elevator request from the floor subsystem or arrivalSensor request.
	 * Format of floor request:
	 * 1 - String - type of message
	 * 2 - String - the time that request was created
	 * 3 - int - the floor at which the request was made
	 * 4 - String - the direction the passenger will go (UP or DOWN)
	 * 5 - int - the floor the passenger wants to go to
	 * 
	 * Format for arrival Sensor:
	 * 1 - String - Type of message
	 * 2 - String - the time at which the request was made
	 * 3 - int - the floor where the request was made
	 * 4 - int - The elevator number that triggered arrival sensor
	 */
	public synchronized void putRequest(byte[] request) {
		//This is an elevatorRequest message, add to workRequests arrayList
		String[] data = parseData(request);
		if(data[0].equals("floorRequest")) {
			this.workRequests.add(request);
		}

		//This is a elevator destination floor message
		//Add to arrivals hashmap where key = elevator number and value = floor at which request was made
		if(data[0].equals("arrivalSensor")) {
			if (arrivals.get((Integer.parseInt(data[3]))) == null) {
			    arrivals.put(Integer.parseInt(data[3]), new ArrayList<Integer>());
			}
			arrivals.get(Integer.parseInt(data[3])).add(Integer.parseInt(data[2]));
		}
		//Notify all and return
		notifyAll();
		return;
	}
	
	/**
	 * This method is used to convert a byte array into a string array.
	 *
	 * @param data A byte array that will be converted to a String array.
	 */
	public String[] parseData(byte[] data) {
		//convert bytes into a string array by splitting on " "
		return new String(data).split(" ");
	}
	
	/**
	 * This synchronized method returns the first floor request (position 0) and removes it from the scheduler. 
	 * 
	 * @return floorRequest	the floor request that should be completed next (the first floor request).
	 */
	public synchronized byte[] getRequest() {
		//Wait if there are no work requests
		while (!isWork()) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		}
		
		//Return the request to be completed next, remove from workRequests ArrayList and notify all
		byte[] floorRequest = workRequests.remove(0);
		notifyAll();
		return floorRequest;
	}
	
  	/**
	 * This synchronized method sets the acknowledgement private variable to byte[] ack passed in and notifies all.
	 * 
	 * @param ack The acknowledgement to be put into byte ack.
	 */
	public synchronized void acknowledgeRequest(byte[] ack) {
		//Acknowledgement made
		this.ack = ack;
		notifyAll();
	}
		
  	/**
	 * This synchronized method is used to send an acknowledgement and reset private ack variable to null.
	 * 
	 * @return ackReturn The acknowledgement to be sent.
	 */	
	public synchronized byte[] getAcknowledgemnt() {	
		//Wait if acknowledgement is null
		while (this.ack == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		}
		//Return acknowledgement and set private acknowledgement variable to null
		byte[] ackReturn = this.ack;
		this.ack = null;
		notifyAll();
		return ackReturn;
	}

	/**
	 * This synchronized method is used to determine if there are any work requests pending.
	 * 
	 * @return false  if there are no work requests pending.
	 * @return true   if there are pending work requests.
	 */	
	public synchronized boolean isWork() {
		//Check if there are any requests pending
		return !workRequests.isEmpty();
	}

	/**
	 * This method returns the arrayList containing all the requests.
	 * 
	 * @return workRequests  arrayList containing all requests (type byte[]).
	 */	
	public ArrayList<byte[]> getAllRequest(){
		//Return list of work requests
		return workRequests;
	}
	
	/**
	 * Method that will be implemented to check for elevators that are available.
	 *
	 * @param floor The floor number of the elevator.
	 * @param direction The direction of the elevator.
	 */
	public int checkForAvailableElevator(int Floor, String direction) {
		//Logic to determine which elevator will service request will be implemented
		//later
		return 1;
	}
	
	/** 
	 * Synchronized method used to remove a floor request stored at a certain position i.
	 *
	 * @param i Position at which the workRequest is to be removed.
	 */
	public synchronized void removeRequest(int i) {
		//Remove request i from list of work requests
		workRequests.remove(i);
		notifyAll();
	}
	
	/*
	 * Method used to get the arrayList storing floor numbers (floors at which request was made) that is associated with the elevator number passed in.
	 *
	 * @param elevator The elevator number.
	 */
	public ArrayList<Integer> getArrivals(int elevator){
		//Get value (floor number) associated with the elevator number (key)
		return arrivals.get(elevator);
	}
}


