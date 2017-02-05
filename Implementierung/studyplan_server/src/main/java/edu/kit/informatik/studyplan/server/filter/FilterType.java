// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.filter;

import antlr.collections.impl.IntRange;
import edu.kit.informatik.studyplan.server.rest.SimpleJsonResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Aufzählung der verschiedenen AttributeFilter-Typen.
 */
public enum FilterType {
	/**
	 * Repräsentiert den Filtertyp {@link RangeFilter}.
	 */
	RANGE {
		@Override
		public Map<String, Object> toJsonSpecification(AttributeFilter defaultFilter) {
			Map<String, Object> result = new HashMap<>(3);
			result.put("type", "range");
			result.put("min", ((RangeFilter)defaultFilter).getMin());
			result.put("max", ((RangeFilter)defaultFilter).getMax());
			return result;
		}

		@Override
		public Object defaultJsonValue(AttributeFilter defaultFilter) {
			Map<String, Object> result = new HashMap<>(2);
			result.put("min", ((RangeFilter)defaultFilter).getMin());
			result.put("max", ((RangeFilter)defaultFilter).getMax());
			return result;
		}
	},
	/**
	 * Repräsentiert den Filtertyp {@link ListFilter}.
	 */
	LIST {
		@Override
		public Map<String, Object> toJsonSpecification(AttributeFilter defaultFilter) {
			Map<String, Object> result = new HashMap<>(2);
			result.put("type", "list");
			List<String> itemStrings = ((ListFilter<?>)defaultFilter).getItemStrings();
			List<ListItem> items = IntStream.range(0, itemStrings.size())
					.mapToObj(i -> new ListItem(0, itemStrings.get(i)))
					.collect(Collectors.toList());
			result.put("items", items);
			return result;
		}

		@Override
		public Object defaultJsonValue(AttributeFilter defaultFilter) {
			return 0;
		}
	},
	/**
	 * Repräsentiert den Filtertyp {@link ContainsFilter}.
	 */
	CONTAINS {
		@Override
		public Map<String, Object> toJsonSpecification(AttributeFilter defaultFilter) {
			return SimpleJsonResponse.build("type", "contains");
		}

		@Override
		public Object defaultJsonValue(AttributeFilter defaultFilter) {
			return "";
		}
	};

	/**
	 * Liefert den Spezifikation-Abschnitt der JSON-Repräsentation des
	 * übergebenen Default-Filters.
	 * 
	 * @param defaultFilter
	 *            der Default-Filter
	 * @return die Spezifikation des Filters als JSON-Objekt
	 */
	public abstract Map<String, Object> toJsonSpecification(AttributeFilter defaultFilter);

	/**
	 * Liefert eine JSON-Repräsentation der Werte des übergebenen
	 * Default-Filters.
	 * 
	 * @param defaultFilter
	 *            das Filter-Objekt mit Default-Werten
	 * @return die Werte des Default-Filters
	 */
	public abstract Object defaultJsonValue(AttributeFilter defaultFilter);

	private static class ListItem {
		private int id;
		private String name;

		public ListItem(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
};
