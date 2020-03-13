package behavior;

import agent.House;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.wrapper.StaleProxyException;

public class GeneratePerson extends TickerBehaviour {
	
	final double PROBA_GEN = 0.02;
	
	House house;

	public GeneratePerson(House h, long period) {
		super(h, period);
		house = h;
		
	}
	
	protected void onTick() {
		try {
			if(Math.random()<PROBA_GEN) {
				house.generatePassenger();
			}
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}
}
