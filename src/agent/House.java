package agent;

import java.util.UUID;

import behavior.GeneratePerson;
import dataStructure.HouseData;
import jade.core.Agent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class House extends Agent {
	
	final int GENERATION_TICK = 1000;
	
	static private AgentContainer mc;
	
	static public void setAgentController(AgentContainer givenMc) {
		mc = givenMc;
	}
	
	public String id;
	
	protected void setup(){ 
		HouseData houseData = (HouseData) this.getArguments()[0];
		this.id = houseData.id;
		this.addBehaviour(new GeneratePerson(this, GENERATION_TICK));
	}
	
	public void generatePassenger() throws StaleProxyException {
		String passengerId = "p-"+UUID.randomUUID().toString();
		AgentController newPassenger = mc.createNewAgent(passengerId, Passenger.class.getName(), new Object[]{ passengerId, this.id } );
		newPassenger.start();
	}
}
