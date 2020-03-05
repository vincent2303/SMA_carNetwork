package messageFilter;

import dataStructure.Performatifs;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AskNearestCar implements MessageTemplate.MatchExpression {
	@Override
	public boolean match(ACLMessage message) {
		if(message.getPerformative() == Performatifs.ASK_NEAREST) {
			return true;
		}
		return false;
	}
}
