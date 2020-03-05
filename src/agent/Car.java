package agent;

import java.util.ArrayList;

import behavior.Drive;
import behavior.HandleRequest;
import behavior.SendCarData;
import dataStructure.CarData;
import dataStructure.HouseData;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Car extends Agent {
	
	final int SEND_DATA_TICK = 100;
	final public static int CAR_DRIVING_TICK = 100;
	final public static int CAR_SPEED = 5;
	
	final public static String WAITING = "WAITING";
	final public static String WAITING_RESPONSE = "WAITING_RESPONSE";
	
	public CarData carData;
	
	public ArrayList<HouseData> drivingPath = new ArrayList<HouseData>();
	
	public String state = WAITING;
	
	public AID engagedWith = null;
	
	protected void setup(){
		CarData carData = (CarData) this.getArguments()[0];
		this.carData = carData;

		// register
		register();
		
		// behaviors
		this.addBehaviour(new SendCarData(this, SEND_DATA_TICK));
		this.addBehaviour(new Drive(this, CAR_DRIVING_TICK));
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
}
