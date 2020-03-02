package dataStructure;

import java.io.Serializable;

public class CarData implements Serializable {
	
	static public final String WAITING = "WAITING";
	static public final String DRIVING_TO_GET = "DRIVING_TO_GET";
	static public final String DRIVING_TO_SEND = "DRIVING_TO_SEND";
	
	public String id;
	public String from;
	public String to;
	public int distance;
	public int distanceToDo;
	public String carState;
	
	public CarData(String id, String from) {
		this.id = id;
		this.from = from;
		this.to = null;
		this.carState = WAITING;
		this.distance = 0;
	}
	
	public CarData(String id, String from, String to, int distance, int distanceToDo) {
		this.id = id;
		this.from = from;
		this.to = from;
		this.distance = distance;
	}
}
