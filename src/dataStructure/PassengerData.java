package dataStructure;

import java.io.Serializable;
import java.util.ArrayList;

import agent.Passenger;

public class PassengerData implements Serializable {
	
	public String id;
	public String passengerState = Passenger.WAITING;
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
