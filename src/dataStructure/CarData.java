package dataStructure;

import java.io.Serializable;

public class CarData implements Serializable {
	
	static public final String WAITING = "WAITING";
	static public final String DRIVING_TO_GET = "DRIVING_TO_GET";
	static public final String DRIVING_TO_SEND = "DRIVING_TO_SEND";
	
	public String id;
	public HouseData from;
	public HouseData to;
	public int distance;
	public String insidePassengerId;
	
	public CarData(String id, HouseData from) {
		this.id = id;
		this.from = from;
		this.to = null;
		this.distance = 0;
		this.insidePassengerId = null;
	}
	
	public CarData(String id, HouseData from, HouseData to, int distance, int distanceToDo) {
		this.id = id;
		this.from = from;
		this.to = from;
		this.distance = distance;
		this.insidePassengerId = null;
	}
}
