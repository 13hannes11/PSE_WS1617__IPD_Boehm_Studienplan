package edu.kit.informatik.studyplan.server.model.moduledata.constraint;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;

/**
 * Models a prerequisite constraint between two modules. The second module
 * requires the first module.
 */
@Entity
@DiscriminatorValue(value = "prerequisite")
public class PrerequisiteModuleConstraintType extends ModuleConstraintType {

	@Override
	public boolean isValid(ModuleEntry first, ModuleEntry second, ModuleOrientation orientation) {
		switch (orientation) {
		case LEFT_TO_RIGHT:
			return check(first, second);
		case RIGHT_TO_LEFT:
			return check(second, first);
		default:
			return check(first, second);
		}

	}

	private boolean check(ModuleEntry first, ModuleEntry second) {
		// TODO: first isPassed always true?
		if (second == null) {
			return true;
		}
		if (second.isPassed()) {
			return true;
		}
		if (first == null) {
			return false;
		}
		if (first.isPassed()) {
			return true;
		}
		return first.getSemester() < second.getSemester();
	}
}
