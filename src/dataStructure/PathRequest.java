package dataStructure;

import java.io.Serializable;

public class PathRequest implements Serializable {
	public RequestData request;
	public CarData carData;
	
	public PathRequest(RequestData request, CarData carData) {
		this.request = request;
		this.carData = carData;
	}
}
