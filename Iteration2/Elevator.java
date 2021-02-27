/**
 * 
 * @author Alden Ng
 *
 */
public class Elevator implements Runnable{

	// Current Elevator State
	private ElevatorState elevatorState;
	
	// All Concrete Elevator States
	private ElevatorState idle;
	private ElevatorState moving;
	private ElevatorState arrived;
	
	/*
	 * Default Constructor when Elevator is created
	 * Initial State is "Doors Closed" by default
	 */
	public Elevator() {
		
		// Creating all concrete state objects
		this.idle = new IdleES(this);
		this.moving = new MovingES(this);
		this.arrived = new ArrivedES(this);
		
		// Default State to Idle
		elevatorState = idle;
	}
	
	/*
	 * Set the State of the Elevator
	 */
	public void SetState(ElevatorState newElevatorState) {
		elevatorState = newElevatorState;
	}
	
	/*
	 * Press Button depending on elevatorState
	 */
	public void buttonPress() {
		elevatorState.ButtonPress();
	}
	
	/*
	 * Close Doors depending on elevatorState
	 */
	public void CloseDoors() {
		elevatorState.CloseDoors();
	}
	
	/*
	 * Open Doors depending on elevatorState
	 */
	public void OpenDoors() {
		elevatorState.OpenDoors();
	}
	/*
	 * ReceiveRequest and process based on elevatorState
	 */
	public void ReceiveRequest() {
		elevatorState.ReceivedRequest();
	}
	
	/*
	 * Run Function TODO
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
