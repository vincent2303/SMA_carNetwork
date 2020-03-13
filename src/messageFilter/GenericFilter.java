package messageFilter;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GenericFilter implements MessageTemplate.MatchExpression {
	
	private int performatif;
	
	public GenericFilter(int performatif) {
		this.performatif = performatif;
	}
	
	@Override
	public boolean match(ACLMessage message) {
		if(message.getPerformative() == this.performatif) {
			return true;
		}
		return false;
	}

}
