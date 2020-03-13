package agent;

import java.util.ArrayList;

import behavior.DriveToGet;
import behavior.HandleRequest;
import behavior.SendCarData;
import dataStructure.CarData;
import dataStructure.DrivingInfo;
import dataStructure.HouseData;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Car extends Agent {
	
	final public static int SEND_DATA_TICK = 50;
	final public static int CAR_DRIVING_TICK = 50;
	final public static int CAR_SPEED = 5;
	
	final public static String WAITING = "WAITING";
	final public static String WAITING_RESPONSE = "WAITING_RESPONSE";
	final public static String DRIVING_TO_GET = "DRIVING_TO_GET";
	final public static String DRIVING_TO_SEND = "DRIVING_TO_SEND";
	final public static String WAIT_PASSENGER_TO_GO_INSIDE = "WAIT_PASSENGER_TO_GO_INSIDE";
	
	public CarData carData;
		
	public String state = WAITING;
	
	public DrivingInfo drivingInfo;
	
	public AID engagedWithAID = null;
	public String engagedWithId = null;
	
	protected void setup(){
		CarData carData = (CarData) this.getArguments()[0];
		this.carData = carData;

		// register
		register();
		
		// behaviors
		this.addBehaviour(new SendCarData(this, SEND_DATA_TICK));
		this.addBehaviour(new HandleRequest());
	}
	
	private void register() {
	    ServiceDescription serviceDescription = new ServiceDescription();
	    serviceDescription.setName(getLocalName());
	    serviceDescription.setType("car");
	    
	    DFAgentDescription description = new DFAgentDescription();
	    description.addServices(serviceDescription);
	    
	    try {
			DFService.register(this, description);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
	
	// called when it drops a passenger
	public void resetCar() {
		this.state = WAITING;
		this.engagedWithAID = null;
		this.engagedWithId = null;
		this.carData.insidePassengerId = null;
	}
}
