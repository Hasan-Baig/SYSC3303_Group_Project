/**
 * 
 * @author Alden Ng, Rutvik Shah, Hasan Baig
 * 
 */
public class Elevator implements Runnable{

	
	private String currentState;
	private ElevatorState elevatorState;
	
	/*
	 * Default Constructor when Elevator is created
	 * Initial State is "Doors Closed" by default
	 */
	public Elevator() {
		this.currentState = "Doors Closed";
	}
	
	/*
	 * Constructor with String state
	 * Set the state of the elevator object with String state
	 */
	public Elevator(String state) {
		this.currentState = state;
		this.SetState(state);
	}
	
	/*
	 * Create State Object based on ElevatorState
	 */
	public void SetState(String EleState) {
		
		if(currentState == "Doors Closed") {
			elevatorState = new DoorsClosed();
		}
	}
	
	/*
	 * Run Function TODO
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
