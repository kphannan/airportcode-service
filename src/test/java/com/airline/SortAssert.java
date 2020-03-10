package com.airline;

import org.assertj.core.api.AbstractAssert;
import org.springframework.data.domain.Sort;

public class SortAssert extends AbstractAssert<SortAssert, Sort> {

	SortAssert(Sort sort) {
		super(sort, SortAssert.class);
	}

	public static SortAssert assertThat(Sort actual) {
		return new SortAssert(actual);
	}

	public SortAssert hasSort(String field, Sort.Direction direction) {

		Sort.Order actualOrder = actual.getOrderFor(field);

		if (actualOrder == null) {
			failWithMessage("expected sort for field <%s> to be <%s> but was null", field, direction);
		} else if (actualOrder.getDirection() != direction) {
			failWithMessage("expected sort for field <%s> to be <%s> but was <%s>", field, direction, actualOrder.getDirection());
		}

		return this;
	}
}