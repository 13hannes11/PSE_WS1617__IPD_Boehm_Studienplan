package edu.kit.informatik.studyplan.server.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * FilterDescriptor for ListFilters.
 * @param <T> the type of the ListFilter's options
 */
public class ListFilterDescriptor<T> extends FilterDescriptor {
    private Supplier<List<T>> itemObjectsSupplier;
    private Function<List<T>, List<String>> itemStringsSupplier;
    private Function<T, ListFilter> constructor;

    /**
     * Creates a new List Filter Descriptor
     * @param id filter id
     * @param filterUriIdentifier URI identifier
     * @param filterName GUI filter name
     * @param tooltip GUI tooltip
     * @param itemObjectsSupplier supplier for the filter's item objects
     * @param itemStringsSupplier function that converts the item objects to strings
     * @param constructor factory method constructing a new ListFilter of desired type
     */
    ListFilterDescriptor(int id, String filterUriIdentifier, String filterName, String tooltip,
                         Supplier<List<T>> itemObjectsSupplier, Function<List<T>, List<String>> itemStringsSupplier,
                         Function<T, ListFilter> constructor) {
        super(id, filterUriIdentifier, filterName, tooltip);
        this.itemObjectsSupplier = itemObjectsSupplier;
        this.itemStringsSupplier = itemStringsSupplier;
        this.constructor = constructor;
    }

    @Override
    public Object getDefaultJsonValue() {
        return 0;
    }

    @Override
    public Map<String, Object> getJsonSpecification() {
        Map<String, Object> result = new HashMap<>(2);
        result.put("type", "list");
        List<String> itemStrings = itemStringsSupplier.apply(itemObjectsSupplier.get());
        List<ListItem> items = IntStream.range(0, itemStrings.size())
                .mapToObj(i -> new ListItem(i, itemStrings.get(i)))
                .collect(Collectors.toList());
        result.put("items", items);
        return result;
    }

    @Override
    public AttributeFilter getFilterFromRequest(MultivaluedMap<String, String> parameters) {
        String selectionString = parameters.getFirst(getFilterUriIdentifier());
        if (selectionString == null) {
            throw new BadRequestException();
        }
        try {
            int selectionNumber = Integer.parseInt(selectionString);
            List<T> itemObjects = itemObjectsSupplier.get();
            if (selectionNumber < 0 || selectionNumber >= itemObjects.size()) {
                throw new BadRequestException();
            }
            return constructor.apply(itemObjects.get(selectionNumber));
        } catch (NumberFormatException ex) {
            throw new BadRequestException();
        }
    }

    /**
     * Represents a JSON list item by id and name.
     */
    static class ListItem {
    	@JsonProperty
        private int id;
    	@JsonProperty
        private String text;

        ListItem(int id, String name) {
            this.id = id;
            this.text = name;
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof ListItem) &&
                    this.id == ((ListItem) obj).getId() &&
                    Objects.equals(this.text, ((ListItem) obj).getText());
        }

        public int getId() {
            return id;
        }

        public String getText() {
            return text;
        }
    }
}
