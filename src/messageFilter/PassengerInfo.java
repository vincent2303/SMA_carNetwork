package messageFilter;
import dataStructure.Performatifs;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PassengerInfo implements MessageTemplate.MatchExpression {
	
	@Override
	public boolean match(ACLMessage message) {
		if(message.getPerformative() == Performatifs.SEND_PASSENGER_DATA) {
			return true;
		}
		return false;
	}

}
