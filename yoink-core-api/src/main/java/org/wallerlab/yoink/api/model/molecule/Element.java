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
package org.wallerlab.yoink.api.model.molecule;


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
	Ar(3625.0, 260.0, 5.211, 0.0296, 0.1168, 0.4412, 18, 39.948),
	K(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 19,39.0983),
	Ca(0.0, 0.0,0.0, 0.0, 0.0,0.0, 20, 	40.078),
	Sc(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 21,44.9559),
	Ti(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 22,47.867),
	V(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 23,50.9415),
	Cr(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 24,51.9961),
	Mn(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 25,54.938),
	Fe(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 26,55.845),
	Co(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 27,58.9332	),
	Ni(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 28,58.6934),
	Cu(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 29,63.546),
	Zn(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 30,65.39),
	Ga(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 31,69.723),
	Ge(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 32,72.64),
	As(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 33,74.9216),
	Se(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 34,	78.96),	
	Br(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 35,79.904),
	Kr(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 36,83.8),
	Rb(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 37,85.4678),
	Sr(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 38,	87.62),
	Y(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 39,88.9059),
	Zr(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 40,91.224),
	Nb(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 41,92.9064),
	Mo(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 42,95.94),
	Tc(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 43,98),
	Ru(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 44,101.07),
	Rh(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 45,102.9055),
	Pd(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 46,106.42),
	Ag(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 47,107.8682),
	Cd(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 48,112.411),
	In(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 49,114.818),
	Sn(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 50,118.71),
	Sb(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 51,121.76),
	Te(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 52,127.6),
	I(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 53,126.9045),
	Xe(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 54,131.293),
	Cs(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 55,132.9055),
	Ba(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 56,137.327),
	La(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 57,138.9055),
	Ce(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 58,140.116),
	Pr(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 59,140.9077),
	Nd(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 60,144.24	),
	Pm(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 61,145),
	Sm(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 62,150.36),
	Eu(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 63,151.964),
	Gd(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 64,157.25),
	Tb(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 65,158.9253),
	Dy(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 66,162.5),
	Ho(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 67,164.9303),
	Er(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 68,167.259),
	Tm(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 69,168.9342),
	Yb(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 70,173.04),
	Lu(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 71,174.967),
	Hf(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 72,178.49),
	Ta(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 73,180.9479),
	W(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 74,183.84),
	Re(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 75,186.207),
	Os(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 76,	190.23),
	Ir(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 77,192.217),
	Pt(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 78,195.078),
	Au(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 79,196.9665),
	Hg(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 80,200.59),
	Tl(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 81,204.3833),
	Pb(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 82,207.2),
	Bi(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 83,208.9804),
	Po(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 84,209),
	At(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 85,210),
	Rn(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 86,222),
	Fr(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 87,223),
	Ra(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 88,226),
	Ac(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 89,227),
	Th(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 90,232.0381),
	Pa(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 91,231.0359),
	U(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 92,238.0289),
	Np(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 93,237),
	Pu(0.0, 0.0,0.0, 0.0, 0.0, 0.0, 94,244);


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
