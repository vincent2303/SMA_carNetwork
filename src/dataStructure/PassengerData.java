package dataStructure;

import java.io.Serializable;
import java.util.ArrayList;

public class PassengerData implements Serializable {
	
	static public final String WAITING = "WAITING";
	static public final String INSIDE_CAR = "INSIDE_CAR";
	static public final String ARRIVED = "ARRIVED";
	
	public String id;
	public String passengerState = WAITING;
	public String fromId;
	public String toId;
	
	public PassengerData(String id, String fromId) {
		this.id = id;
		this.fromId = fromId;
	}
	
	public PassengerData(String id, String fromId, String toId, String passengerState) {
		this.id = id;
		this.fromId = fromId;
		this.toId = toId;
		this.passengerState = passengerState;
	}
}
