package behavior;

import java.io.IOException;

import agent.Car;
import dataStructure.Performatifs;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class SendCarData extends TickerBehaviour {
	Car car;
	
	public SendCarData(Car c, long period) {
		super(c, period);
		this.car = c;
	}
	
	protected void onTick() {
		ACLMessage myMessage = new ACLMessage(Performatifs.SEND_CAR_DATA);
		try {
			myMessage.setContentObject(car.carData);
		} catch (IOException e) { e.printStackTrace(); }
		myMessage.addReceiver(new AID("map", false));
		((Car) myAgent).send(myMessage);
	}
}
