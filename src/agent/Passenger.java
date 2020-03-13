package agent;

import java.io.IOException;

import behavior.SendPassengerData;
import behavior.Request;
import dataStructure.PassengerData;
import dataStructure.Performatifs;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import smaUtils.SmaUtils;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.domain.DFService;
import jade.domain.FIPAException;

public class Passenger extends Agent {
	
	final int SEND_DATA_TICK = 50;
	final int SEND_REQUEST_TICK = 500;
	
	static public final String WAITING = "WAITING";
	static public final String INSIDE_CAR = "INSIDE_CAR";
	static public final String ARRIVED = "ARRIVED";
	
	static String[] houseIds;
	
	public String id;
	public String fromId;
	public String toId;
	public String passengerState = Passenger.WAITING;
	
	protected void setup(){
		// init
		this.id = (String) this.getArguments()[0];
		this.fromId = (String) this.getArguments()[1];
		this.setRandomTo();
		
		// register
		register();
		
		// behaviors
		System.out.println("Passenger created From " + this.fromId + " TO:" + this.toId);
		this.addBehaviour(new SendPassengerData(this, SEND_DATA_TICK));
		
		this.addBehaviour(new Request());
	}
	
	private void setRandomTo() {
		int randomIndex = SmaUtils.randomInt(0, houseIds.length-1);
		while(houseIds[randomIndex]==this.fromId) {
			randomIndex = SmaUtils.randomInt(0, houseIds.length-1);
		}
		this.toId = houseIds[randomIndex];
	}
	
	static public void setHousesIds(String[] givenHousesIds) {
		houseIds = givenHousesIds;
	}
	
	public PassengerData getPassengerData() {
		PassengerData data = new PassengerData(this.id, this.fromId, this.toId, this.passengerState);
		return data;
	}
	
	private void register() {
	    ServiceDescription serviceDescription = new ServiceDescription();
	    serviceDescription.setName(getLocalName());
	    serviceDescription.setType("passenger");
	    
	    DFAgentDescription description = new DFAgentDescription();
	    description.addServices(serviceDescription);
	    
	    try {
			DFService.register(this, description);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
}
