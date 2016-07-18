package org.wallerlab.yoink.math;

import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

import java.util.*;

/**
 * this class is to ratio the difference of two sets
 * 
 *
 */
public class SetOps {

	/**
	 * This method is to ratio the difference of two Sets. If there is no
	 * same element in two sets, the difference is the sum of the two sets
	 * 
	 * @param <T>
	 *            - the data type of a Set
	 * @param s1
	 *            - a specified type Set,{@link java.util.Set}
	 * @param s2
	 *            - a specified type Set,{@link java.util.Set}
	 * @return the difference of two sets, {@link java.util.Set}
	 */
	public static <T> Set<T> diff(Set<? extends T> s1, Set<? extends T> s2) {
		Set<T> symmetricDiff = new HashSet<T>(s1);
		symmetricDiff.addAll(s2);
		Set<T> tmp = new HashSet<T>(s1);
		tmp.retainAll(s2);
		symmetricDiff.removeAll(tmp);
		return symmetricDiff;
	}

	public static Set<MolecularSystem.Molecule> union(Set<MolecularSystem.Molecule> moleculesInA, Set<MolecularSystem.Molecule> moleculsInB) {
		Set<MolecularSystem.Molecule> copyOfMoleculesInA = new HashSet<>(moleculesInA);
		Set<MolecularSystem.Molecule> copyOfMoleculesInB = new HashSet<>(moleculsInB);
		Set<MolecularSystem.Molecule> union = new HashSet<>(copyOfMoleculesInA);
		union.addAll(copyOfMoleculesInB);
		return union;
	}

	public static Set<MolecularSystem.Molecule> difference(Set<MolecularSystem.Molecule> moleculesInA, Set<MolecularSystem.Molecule> moleculsInB) {
		Set<MolecularSystem.Molecule> copyOfMoleculesInA = new HashSet<>(moleculesInA);
		Set<MolecularSystem.Molecule> copyOfMoleculesInB = new HashSet<>(moleculsInB);
		Set<MolecularSystem.Molecule> difference = new HashSet<>(moleculesInA);
		difference.removeAll(copyOfMoleculesInB);
		return difference;
	}

	public static Set<MolecularSystem.Molecule> equality(Set<MolecularSystem.Molecule> moleculesInA){
		Set<MolecularSystem.Molecule> copyOfMoleculesInA = new HashSet<>(moleculesInA);
		return copyOfMoleculesInA;
	}

	/**
	 *
	 * @param S
	 *            - an integer array
	 * @return if the input S is empty,return null else return an list of
	 *         integer lists
	 */
	public static List<ArrayList<Integer>> split(int[] S) {
		if (S == null) return null;
		Arrays.sort(S);
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < S.length; i++) {
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
			// get sets that are already in result
			for (ArrayList<Integer> a : result)
				temp.add(new ArrayList<Integer>(a));
			// add S[i] to existing sets
			for (ArrayList<Integer> a : temp)
				a.add(S[i]);
			// add S[i] only as a set
			ArrayList<Integer> single = new ArrayList<Integer>();
			single.add(S[i]);
			temp.add(single);
			result.addAll(temp);
		}
		// add empty set
		result.add(new ArrayList<Integer>());
		return result;
	}

	/**
	 * this method is to get all subsets with the same size of an integer array.
	 * integer lists
	 *
	 * @param S
	 *            - an integer array
	 * @param size
	 *            - the size of subsets
	 *
	 * @return if the input S is empty,return null else return an list of
	 *         integer lists
	 */
	public static List<ArrayList<Integer>> split(int[] S, int size) {
		if (S == null)
			return null;
		Arrays.sort(S);
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < S.length; i++) {
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
			// get sets that are already in result
			for (ArrayList<Integer> a : result) {
				temp.add(new ArrayList<Integer>(a));
			}
			// add S[i] to existing sets
			for (ArrayList<Integer> a : temp) {
				a.add(S[i]);
			}
			// add S[i] only as a set
			ArrayList<Integer> single = new ArrayList<Integer>();
			single.add(S[i]);
			temp.add(single);
			result.addAll(temp);
		}
		ArrayList<ArrayList<Integer>> sizeResult = new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<result.size();i++){
			if(result.get(i).size()==size){
				sizeResult.add(result.get(i));
			}
		}
		return sizeResult;
	}

	/**
	 *
	 * @param S
	 *            - an Double array
	 * @return if the input S is empty,return null else return an list of
	 *         Double lists
	 */
	public static List<ArrayList<Double>> splitDoubleArray(double[] S) {
		if (S == null) return null;
		Arrays.sort(S);
		ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < S.length; i++) {
			ArrayList<ArrayList<Double>> temp = new ArrayList<ArrayList<Double>>();
			// get sets that are already in result
			for (ArrayList<Double> a : result)
				temp.add(new ArrayList<Double>(a));
			// add S[i] to existing sets
			for (ArrayList<Double> a : temp)
				a.add(S[i]);
			// add S[i] only as a set
			ArrayList<Double> single = new ArrayList<Double>();
			single.add(S[i]);
			temp.add(single);
			result.addAll(temp);
		}
		// add empty set
		result.add(new ArrayList<Double>());
		return result;
	}
}
