package dataStructure;

import java.io.Serializable;

public class RequestData implements Serializable {
	public String fromId;
	public String toId;
	public String passengerId;
	
	public RequestData(String fromId, String toId, String passengerId) {
		this.fromId = fromId;
		this.toId = toId;
		this.passengerId = passengerId;
	}
}
