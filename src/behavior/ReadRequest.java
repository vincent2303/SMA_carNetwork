package behavior;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import messageFilter.PassengerRequest;

public class ReadRequest extends CyclicBehaviour {
	
	@Override
	public void action() {
		MessageTemplate mt = new MessageTemplate(new PassengerRequest());
		ACLMessage m = myAgent.receive(mt);
		if (m==null) {
			block();
		} else {
			System.out.println("Une voiture " + myAgent.getAID() + " a recus request");
		}
	}
}
