package org.wallerlab.yoink.plugin.service.chem;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.batch.api.model.batch.Job;
import org.wallerlab.yoink.batch.api.service.plugin.AdaptiveProcessor;
import org.wallerlab.yoink.batch.api.service.math.Vector;

/**
 * this class is to call MM calculation
 * @author Min Zheng
 *
 */
@Service("mm")
public class MMProcessor implements AdaptiveProcessor{

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
