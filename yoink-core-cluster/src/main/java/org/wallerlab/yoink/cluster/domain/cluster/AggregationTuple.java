package org.wallerlab.yoink.cluster.domain.cluster;

import org.neo4j.graphdb.Relationship;

public class AggregationTuple {

	String start;

	String end;

	String representation;

	public AggregationTuple(Relationship edge) {
		String one = String.valueOf(edge.getStartNode().getProperty("community"));
		String two = String.valueOf(edge.getEndNode().getProperty("community"));
		if (one.compareTo(two) >= 0) {
			this.start = one;
			this.end = two;
		} else {
			this.start = two;
			this.end = one;
		}
		representation = start + "-" + end;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj.getClass() == this.getClass()) && (this.hashCode() == obj.hashCode());
	}

	@Override
	public int hashCode() {
		return representation.hashCode();
	}

	@Override
	public String toString() {
		return "Tuple(" + start + ", " + end + ")";
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}

}
