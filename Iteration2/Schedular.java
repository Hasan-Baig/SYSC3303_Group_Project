/**
 * Schedular.java
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

public class Scheduler implements Runnable {

	private byte[] ack = null;
	private ArrayList<byte[]> workRequests = new ArrayList<>(); 

	/**
	 * These are the states that the scheduler will go through.
	 * 
	 */
	public enum State {
		WAIT_FOR_REQUEST, SEND_ELEVATOR_TO_FLOOR, WAIT_FOR_ELEVATOR, UPDATE_ARRIVALS
	}

	/**
	 * This variable is used to keep track of the current state of the scheduler.
	 * Initial state is to wait for a request.
	 */
	private static State current_state = States.WAIT_FOR_REQUEST;


	/**
	 * The default constructor.
	 */
	public Scheduler() { }

	/* 
	 * Used to receive current state of scheduler.
   */
	public States getState() {
		return current_state;
	}

	/**
	 * This method puts a floor request into the scheduler. This method will return
	 * when there is space for the floor request in the scheduler.
	 * 
	 * @param elevatorRequests An object representing the elevator request from the floor subsystem
	 */
	public synchronized void putRequest(byte[] elevatorRequests) {
			//Add the work requests passed in and notify all
			this.workRequests.add(elevatorRequests);
			notifyAll();
			return;
	}

	/**
	 * This synchronized method returns the first floor request (position 0) and removes it from the scheduler. 
	 * 
	 * @return floorRequest	the floor request that should be completed next (the first floor request)
	 */
	public synchronized byte[] getRequest() {
		//wait if there are no work requests
		while (!isWork()) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		}
		
		//return the request to be completed next, remove from workRequests ArrayList and notify all
		byte[] floorRequest = workRequests.remove(0);
		notifyAll();
		return floorRequest;
	}
	
  /**
	 * This synchronized method sets the acknowledgement private variable to byte[] ack passed in and notifies all.
	 * 
	 * @param ack The acknowledgement to be put into byte ack
	 */
	public synchronized void acknowledgeRequest(byte[] ack) {
		this.ack = ack;
		notifyAll();
	}
	
		
  /**
	 * This synchronized method is used to send an acknowledgement and reset private ack variable to null.
	 * 
	 * @return ackReturn The acknowledgement to be sent 
	 */	
	public synchronized byte[] getAcknowledgemnt() {	
		//wait if acknowledgement is null
		while (this.ack == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		}
		//return acknowledgement and set private acknowledgement variable to null
		byte[] ackReturn = this.ack;
		this.ack = null;
		notifyAll();
		return ackReturn;
	}

	/**
	 * This synchronized method is used to determine if there are any work requests pending.
	 * 
	 * @return false  if there are no work requests pending
	 * @return true   if there are pending work requests
	 */	
	public synchronized boolean isWork() {
		return !workRequests.isEmpty();
	}

	/**
	 * This method returns the arrayList containing all the requests.
	 * 
	 * @return workRequests  arrayList containing all requests (type byte[])
	 */	
	public ArrayList<byte[]> getAllRequest(){
		return workRequests;
	}

	/**
	 * This method will override the Runnable interface's run method.
   * It contains a switch statement to implement the state machine.
	 */

	@Override
	public void run() {
		while (true) {
			switch (current_state) {
				case WAIT_FOR_REQUEST: {
					break;
				}
				case SEND_ELEVATOR_TO_FLOOR: {
					break;
				}	
				case WAIT_FOR_ELEVATOR: {
					break;
				}
				case UPDATE_ARRIVALS: {	
					break;
				}
			}

		}
	}
}
