package behavior;

import agent.House;
import jade.core.behaviours.Behaviour;

public class SayHello extends Behaviour {

	@Override
	public void action() {
		System.out.println( ((House) myAgent).id + " hello");
		block(2000);
	}

	@Override
	public boolean done() {
		return false;
	}

}
