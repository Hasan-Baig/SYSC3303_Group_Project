/**
 * 
 * @author Alden Ng
 *
 */
public interface ElevatorState {
	
	public void ButtonPress();
	
	public void CloseDoors();
	
	public void OpenDoors();
	
	public void ReceivedRequest();
}
