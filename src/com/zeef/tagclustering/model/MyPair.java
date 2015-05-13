package com.zeef.tagclustering.model;

import org.javatuples.Pair;
import org.javatuples.Tuple;

@SuppressWarnings("serial")
public class MyPair<A, B> extends Tuple {

	private A a;
	private B b;

	public MyPair(A a, B b) {
		super(a, b);
	}

	@Override
	public int getSize() {
		return 0;
	}

	public A getValue0() {
		return a;
	}

	public B getValue1() {
		return b;
	}

	public Boolean equals(Pair<A, B> pair) {
		Boolean result = false;
		if (pair.getValue0().equals(getValue(0)) && pair.getValue1().equals(getValue(1))) {
			result = true;
		}
		return result;
	}

}
