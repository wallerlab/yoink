package org.wallerlab.yoink.processor.service.chem;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.service.adaptiveProcessor.AdaptiveProcessor;
import org.wallerlab.yoink.api.service.math.Vector;


/**
 * this class is to call QM/MM calculation
 * @author Min Zheng
 *
 */
@Service("qmmm")
public class QMMMProcessor implements AdaptiveProcessor{

	@Override
	public void run(Job<JAXBElement> job) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getEnergy() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Vector> getForces() {
		// TODO Auto-generated method stub
		return null;
	}

}
