// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.model.userdata;

import edu.kit.informatik.studyplan.server.model.moduledata.CycleType;
import edu.kit.informatik.studyplan.server.model.userdata.SemesterType;

/************************************************************/
/**
 * 
 */
public class Semester {
	/**
	 * 
	 */
	private CycleType semesterType;
	/**
	 * 
	 */
	private int year;

	/**
	 * 
	 * @return  
	 */
	public int getDistanceToCurrentSemester() {
		return year;
	}

	/**
	 * 
	 * @return  
	 */
	public SemesterType getSemesterType() {
		return null;
	}

	/**
	 * 
	 * @param semesterType 
	 */
	public void setSemesterType(SemesterType semesterType) {
	}

	/**
	 * 
	 * @return  
	 */
	public int getYear() {
		return year;
	}

	/**
	 * 
	 * @param year 
	 */
	public void setYear(int year) {
	}
};
