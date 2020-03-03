package messageFilter;

import dataStructure.Performatifs;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PassengerRequest implements MessageTemplate.MatchExpression {
	@Override
	public boolean match(ACLMessage message) {
		if(message.getPerformative() == Performatifs.PASSENGER_REQUEST) {
			return true;
		}
		return false;
	}
}
