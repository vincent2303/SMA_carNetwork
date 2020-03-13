package smaUtils;

import java.util.Random;

import dataStructure.CarData;
import dataStructure.DestinationData;
import dataStructure.DrivingInfo;
import dataStructure.PassengerData;
import dataStructure.PathRequest;
import dataStructure.RequestData;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class SmaUtils {
	
	public static double computeDistance(int[] p0, int[] p1) {
		return Math.sqrt(Math.pow(p0[0]-p1[0], 2) + Math.pow(p0[1]-p1[1], 2));
	}
	
	public static int[] vectorSubstract(int[] p0, int[] p1) {
		int x = p0[0] - p1[0];
		int y = p0[1] - p1[1];
		int[] vector = {x, y};
		return vector;
	}
	
	public static int[] vectorAddition(int[] p0, int[] p1) {
		int x = p0[0] + p1[0];
		int y = p0[1] + p1[1];
		int[] vector = {x, y};
		return vector;
	}
	
	public static int[] vectorScalarMult(int[] p, double scalar) {
		double x = p[0]*scalar;
		double y = p[1]*scalar;
		int[] vector = { (int) x, (int) y };
		return vector;
	}
	
	public static int[] computePosition(int[] fromPosition, int[] toPosition, int distanceDone) {
		if(toPosition == null) {
			return fromPosition;
		}
		double totalDistance = computeDistance(fromPosition, toPosition);
		double pourcentDistanceDone = distanceDone/totalDistance;
		int[] directorVector = vectorSubstract(toPosition, fromPosition);
		return vectorAddition(fromPosition, vectorScalarMult(directorVector, pourcentDistanceDone));
	}
	
	public static int randomInt(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	public static CarData getCarDataFromMessage(ACLMessage m) {
		try {
			CarData carData = (CarData) m.getContentObject();
			return carData;
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static PassengerData getPassengerDataFromMessage(ACLMessage m) {
		try {
			PassengerData passengerData = (PassengerData) m.getContentObject();
			return passengerData;
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static DestinationData getDestinationDataDataFromMessage(ACLMessage m) {
		try {
			DestinationData destData = (DestinationData) m.getContentObject();
			return destData;
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static RequestData getRequestDataFromMessage(ACLMessage m) {
		try {
			RequestData data = (RequestData) m.getContentObject();
			return data;
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static PathRequest getPathRequestFromMessage(ACLMessage m) {
		try {
			PathRequest data = (PathRequest) m.getContentObject();
			return data;
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static DrivingInfo getDrivingIfonFromMessage(ACLMessage m) {
		try {
			DrivingInfo data = (DrivingInfo) m.getContentObject();
			return data;
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static PassengerData getpassengerDataFromMessage(ACLMessage m) {
		try {
			PassengerData data = (PassengerData) m.getContentObject();
			return data;
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static AID getAIDFromId(String name, Agent myAgent) {
		ServiceDescription serviceDescription = new ServiceDescription();
	    serviceDescription.setName(name);
		DFAgentDescription description = new DFAgentDescription();
		description.addServices(serviceDescription);
		DFAgentDescription[] descriptionList;
		try {
			descriptionList = DFService.search(myAgent, description);
			return descriptionList[0].getName();
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		return null;
	}
}
