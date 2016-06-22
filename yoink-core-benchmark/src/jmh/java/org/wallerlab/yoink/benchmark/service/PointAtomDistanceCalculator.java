package org.wallerlab.yoink.benchmark.service;

import org.wallerlab.yoink.benchmark.domain.Atom;
import org.wallerlab.yoink.benchmark.domain.Core;
/**
 * @author lukas241094
 */
public class PointAtomDistanceCalculator {
	
	public double calculateDistance(Core core,Atom moleculeAtom){

		double distance = Math.sqrt((Math.pow(core.getX()-moleculeAtom.getX(), 2.0))+
									(Math.pow(core.getY()-moleculeAtom.getY(), 2.0))+
									(Math.pow(core.getZ()-moleculeAtom.getZ(), 2.0)));
		
		return distance;
	}

}
