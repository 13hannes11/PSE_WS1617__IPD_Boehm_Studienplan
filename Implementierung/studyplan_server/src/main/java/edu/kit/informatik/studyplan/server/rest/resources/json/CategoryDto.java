package edu.kit.informatik.studyplan.server.rest.resources.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.kit.informatik.studyplan.server.model.moduledata.Category;

/**
 * DataTransferObject for a category
 * @author NiklasUhl
 *
 */
public class CategoryDto {
	
	@JsonProperty
	int id;
	
	@JsonProperty
	String name;
	
	/**
	 * Constructs a new DTO from the given Category
	 * @param category the category
	 */
	public CategoryDto(Category category) {
		this.id = category.getCategoryId();
		this.name = category.getName();
	}
}
