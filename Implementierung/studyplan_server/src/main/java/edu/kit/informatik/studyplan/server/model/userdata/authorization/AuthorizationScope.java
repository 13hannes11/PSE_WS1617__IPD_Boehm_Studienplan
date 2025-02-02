package edu.kit.informatik.studyplan.server.model.userdata.authorization;

import java.util.Arrays;

/**
 * Scopes a user may request
 */
public enum AuthorizationScope {
	/**
	 * scope student. May use all core functions;
	 */
	STUDENT;
	
	/**
	 * 
	 * @param string the string
	 * @return returns the scope specified by this string ignoring case <br>
	 * return <code>null</code> if no scope with this name exists.
	 */
	public static AuthorizationScope fromString(String string) {
		return Arrays.asList(values()).stream()
				.filter(scope -> scope.toString().equalsIgnoreCase(string))
				.findFirst()
				.orElse(null);
	}
};
