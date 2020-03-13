package messageFilter;

import dataStructure.Performatifs;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AnswerNearest implements MessageTemplate.MatchExpression {
	
	@Override
	public boolean match(ACLMessage message) {
		if(message.getPerformative() == Performatifs.ANSWER_NEAREST_CAR) {
			return true;
		}
		return false;
	}
}
