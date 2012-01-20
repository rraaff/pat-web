package com.tdil.pat.processing.testing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class <code>ListRandom</code> is a class used to ramdomize the positions inside a list.
 * When it is first created, it initializes the permutations, and then every randomization
 * is performed in the same way.
 * It is used when you need to randomize a set of list, and every randomization must be
 * equal, that means, if the first position in the first array is stored in the four position
 * after the randomization, then the rest of the arrays must have the first item in the four
 * position.
 * 
 * @author mgodoy
 *
 */
public class ListRandom implements Serializable {
	
	private static final long serialVersionUID = 818607644845317189L;

	private List<Integer> finalPositions = new ArrayList<Integer>();
	
	public ListRandom(Integer size) {
		super();
		for(int i = 0; i < size; i++) {
			finalPositions.add(Integer.valueOf(i));
		}
		Collections.shuffle(finalPositions);
	}
	
	public <T extends Object> void randomize(List<T> input) {
		ArrayList<T> intermediate = new ArrayList<T>(finalPositions.size());
		for (Integer position : finalPositions) {
			intermediate.add(input.get(position));
		}
		input.clear();
		input.addAll(intermediate);
	}
}
