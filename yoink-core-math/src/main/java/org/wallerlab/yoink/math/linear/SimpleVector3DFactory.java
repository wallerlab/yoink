package org.wallerlab.yoink.math.linear;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.api.service.math.Vector.Vector3DType;

/**
 * this class is Vector 3D factory
 * 
 * @author Min Zheng
 *
 */
@Service
public class SimpleVector3DFactory {

	private Vector3DType myVectorType;

	public SimpleVector3DFactory() {

	}

	public SimpleVector3DFactory(Vector3DType myVectorType) {
		this.myVectorType = myVectorType;
	}

	/**
	 * create a 3D vector from its coordinates
	 * 
	 * @param x
	 *            - x coordinate value
	 * @param y
	 *            -y coordinate value
	 * @param z
	 *            - z coordinate value
	 * @return myVector {@link org.wallerlab.yoink.api.service.math.Vector}
	 */
	public Vector create(double x, double y, double z) {
		Vector myVector;
		switch (this.myVectorType) {
		case COMMONS:
			myVector = new CommonsVector3D(x, y, z);
			break;
		default:
			myVector = null;
			throw new IllegalArgumentException("Invalid type of vector: "
					+ myVector);
		}
		return myVector;
	}

	/**
	 * use an double array with 3 elements to create a 3D vector
	 * 
	 * @param d
	 *            - an double array with 3 elements
	 * @return myVector {@link org.wallerlab.yoink.api.service.math.Vector}
	 */
	public Vector create(double[] d) {
		Vector myVector;
		switch (myVectorType) {
		case COMMONS:
			myVector = new CommonsVector3D(d);
			break;
		default:
			myVector = null;
			throw new IllegalArgumentException("Invalid type of vector: "
					+ myVector);
		}
		return myVector;
	}

	/**
	 * set the value of myVectorType
	 * 
	 * @param myVectorType
	 *            {@link org.wallerlab.yoink.api.service.math.Vector.Vector3DType}
	 */
	@Value("${yoink.job.myvectortype}")
	public void setMyVectorType(Vector3DType myVectorType) {
		this.myVectorType = myVectorType;
	}

}
