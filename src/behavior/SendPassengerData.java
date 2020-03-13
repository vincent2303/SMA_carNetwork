package behavior;

import java.io.IOException;

import agent.Passenger;
import dataStructure.PassengerData;
import dataStructure.Performatifs;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class SendPassengerData extends TickerBehaviour {

	Passenger passenger;
	
	public SendPassengerData(Passenger p, long period) {
		super(p, period);
		this.passenger = p;
	}
	
	protected void onTick() {
		PassengerData data = passenger.getPassengerData();
		ACLMessage myMessage = new ACLMessage(Performatifs.SEND_PASSENGER_DATA);
		try {
			myMessage.setContentObject(data);
		} catch (IOException e) { e.printStackTrace(); }
		myMessage.addReceiver(new AID("map", false));
		((Passenger) myAgent).send(myMessage);
	}
}
