package behavior;

import java.io.IOException;

import agent.Car;
import dataStructure.Performatifs;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import messageFilter.PassengerRequest;

public class HandleRequest extends CyclicBehaviour {
	
	@Override
	public void action() {
		MessageTemplate mt = new MessageTemplate(new PassengerRequest());
		ACLMessage request = myAgent.receive(mt);
		if (request==null) {
			block();
		} else {			
			if(!((Car) myAgent).state.equals(Car.WAITING) ) { return; } // the state must be waiting
						
			((Car) myAgent).state = Car.WAITING_RESPONSE;
			((Car) myAgent).engagedWith = request.getSender();
			ACLMessage proposition = new ACLMessage(Performatifs.CAR_PROPOSITION);
			try {
				proposition.setContentObject(((Car) myAgent).carData);
				proposition.addReceiver(request.getSender());
				((Car) myAgent).send(proposition);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
