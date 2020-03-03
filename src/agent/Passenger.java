package agent;

import behavior.SendPassengerData;
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
	
	final int SEND_DATA_TICK = 100;
	
	static String[] houseIds;
	
	public String id;
	private String fromId;
	private String toId;
	private String passengerState = PassengerData.WAITING;
	
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
		this.addBehaviour(new OneShotBehaviour(this){
			public void action(){
				ServiceDescription serviceDescription = new ServiceDescription();
			    serviceDescription.setType("car");
				DFAgentDescription description = new DFAgentDescription();
				description.addServices(serviceDescription);
				try {
					DFAgentDescription[] descriptionList = DFService.search(myAgent, description);
					System.out.println("Passager envoit message à tt les voitures");
					for (int i = 0; i < descriptionList.length; i++) {
						AID carAID = descriptionList[0].getName();
						ACLMessage request = new ACLMessage(Performatifs.PASSENGER_REQUEST);
						request.addReceiver(carAID);
						request.setContent("SALUT, je veux une course !");
						System.out.println("envoit à voiture " + carAID.toString());
						this.myAgent.send(request);
					}
				} catch (FIPAException e) {
					e.printStackTrace();
				}
		  }
	  });
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
