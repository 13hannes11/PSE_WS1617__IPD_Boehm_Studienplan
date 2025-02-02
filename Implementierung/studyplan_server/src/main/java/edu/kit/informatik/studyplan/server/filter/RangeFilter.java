package edu.kit.informatik.studyplan.server.filter;

import edu.kit.informatik.studyplan.server.filter.condition.Condition;

import java.util.Collections;
import java.util.List;

/**
 * Represents an interval range filter for integer attributes.
 */
public abstract class RangeFilter extends AttributeFilter {
	/**
	 * the filter's lower bound
	 */
	private int lower;
	/**
	 * the filter's upper bound
	 */
	private int upper;

	/**
	 * Creates a new RangeFilter with given range.
	 * The following condition must hold: lower <= upper
	 * 
	 * @param lower
	 *            the filter's lower bound
	 * @param upper
	 *            the filter's upper bound
	 */
	RangeFilter(int lower, int upper) {
		if (!(lower <= upper)) {
			throw new IllegalArgumentException("RangeFilter must have valid ranges");
		}
		this.lower = lower;
		this.upper = upper;
	}

	/**
	 * @return a list of Condition objects which demands for the attribute to be
	 *         inside specified range
	 */
	public List<Condition> getConditions() {
		return Collections.singletonList(Condition.createBetween(getAttributeName(), lower, upper));
	}
}
