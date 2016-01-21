/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wallerlab.yoink.api.model.molecular;


import org.wallerlab.yoink.api.service.math.Vector;

/**
 * Enum Element(c1, c2, c3, z1, z2, z3,atomMass,atomNumber). c1, c2, c3, z1, z2
 * and z3 are parameters for numerical LSDA free-atomic densities for the
 * neutral atoms H to Ar. For numerical LSDA free-atomic densities, see: Perdew,
 * J. P.; Wang, Y. Phys. Rev. B 1992, 45, 13244
 * 
 * @author Min Zheng
 *
 */
public enum Element {

	H(0.2815, 0.0, 0.0, 0.5288, 1.0, 1.0, 1, 1.008), 
	He(2.437, 0.0, 0.0,0.3379, 1.0, 1.0, 2, 4.003),
	Li(11.84, 0.06332, 0.0, 0.1912,0.9992, 1.0, 3, 6.940),
	Be(31.34, 0.3694, 0.0, 0.139, 0.6945, 1.0,4, 9.012),
	B(67.82, 0.8527, 0.0, 0.1059, 0.5300, 1.0, 5, 10.810),
	C(120.2, 1.172, 0.0, 0.0884, 0.5480, 1.0, 6, 12.011),
	N(190.9, 2.247,0.0, 0.0767, 0.4532, 1.0, 7, 14.007),
	O(289.5, 2.879, 0.0, 0.0669,0.3974, 1.0, 8, 16.000),
	F(406.3, 3.049, 0.0, 0.0608, 0.3994, 1.0,9, 18.998),
	Ne(561.3, 6.984, 0.0, 0.0549, 0.3447, 1.0, 10, 20.170),
	Na(760.8, 22.42, 0.06358, 0.0496, 0.2511, 1.0236, 11, 22.990),
	Mg(1016.0, 37.17, 0.3331, 0.0449, 0.2150, 0.7753, 12, 24.305),
	Al(1319.0, 57.95, 0.8878, 0.0411, 0.1874, 0.596, 13, 26.982),
	Si(658.0, 87.16, 0.7888, 0.0382, 0.1654, 0.6995, 14, 28.086),
	P(2042.0, 115.7, 1.465, 0.0358, 0.1509, 0.5851, 15, 30.974),
	S(2501.0, 158.0, 2.170, 0.0335, 0.1369, 0.5149, 16, 32.060),
	Cl(3024.0, 205.5, 3.369, 0.0315, 0.1259, 0.4974, 17, 32.060), 
	Ar(3625.0, 260.0, 5.211, 0.0296, 0.1168, 0.4412, 18, 39.948);

	private final double c1, c2, c3, z1, z2, z3, atomMass;
	private final int atomNumber;


	Element(double c1, double c2, double c3, double z1, double z2, double z3,
			int atomNumber, double atomMass) {
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
		this.z1 = z1;
		this.z2 = z2;
		this.z3 = z3;
		this.atomNumber = atomNumber;
		this.atomMass = atomMass;
	}

	public int atomNumber() {
		return atomNumber;
	}

	public double atomMass() {
		return atomMass;
	}

	public double c1() {
		return c1;
	}

	public double c2() {
		return c2;
	}

	public double c3() {
		return c3;
	}

	public double z1() {
		return z1;
	}

	public double z2() {
		return z2;
	}

	public double z3() {
		return z3;
	}

	public double cz1() {
		return c1 / z1;
	}

	public double cz2() {
		return c2 / z2;
	}

	public double cz3() {
		return c3 / z3;
	}

	public double czz1() {
		return (c1 / z1) / z1;
	}

	public double czz2() {
		return (c2 / z2) / z2;
	}

	public double czz3() {
		return (c3 / z3) / z3;
	}

}
