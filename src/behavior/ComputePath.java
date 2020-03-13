package behavior;

import java.io.IOException;

import agent.Map;
import dataStructure.DrivingInfo;
import dataStructure.PathRequest;
import dataStructure.Performatifs;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import messageFilter.GenericFilter;
import smaUtils.SmaUtils;

public class ComputePath extends CyclicBehaviour {

	@Override
	public void action() {
		MessageTemplate mt = new MessageTemplate(new GenericFilter(Performatifs.ASK_FOR_PATH));
		ACLMessage pathRequestMessage = myAgent.receive(mt);
		if (pathRequestMessage==null) {
			block();
		} else {
			
			Map map = (Map) myAgent;
			PathRequest pathRequest = SmaUtils.getPathRequestFromMessage(pathRequestMessage);
			DrivingInfo drivingInfo = map.computePath(pathRequest);
			
			ACLMessage answer = new ACLMessage(Performatifs.ANSWER_FOR_PATH);
			try {
				answer.setContentObject(drivingInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			answer.addReceiver(pathRequestMessage.getSender());

			myAgent.send(answer);
		}
	}

}
