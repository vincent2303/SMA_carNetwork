package smaUtils;

import java.util.Random;

import dataStructure.CarData;

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
}
