package org.wallerlab.yoink.benchmark.service;

import org.wallerlab.yoink.benchmark.domain.Atom;
import org.wallerlab.yoink.benchmark.domain.Core;
import org.wallerlab.yoink.benchmark.domain.Point;

/**
 * @author lukas241094
 */
public class DistanceCalculator {


	public double calculateDistance(Point core, Atom moleculeAtom){

		return Math.sqrt((Math.pow(core.getX()-moleculeAtom.getX(), 2.0))+
						 (Math.pow(core.getY()-moleculeAtom.getY(), 2.0))+
				         (Math.pow(core.getZ()-moleculeAtom.getZ(), 2.0)));
	}

}
