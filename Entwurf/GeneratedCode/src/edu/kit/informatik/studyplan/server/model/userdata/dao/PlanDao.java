// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.model.userdata.dao;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/************************************************************/
/**
 * DataAccessObject zum Zugriff auf Studienpläne aus der Datenbank
 */
public interface PlanDao {

	/**
	 * Sucht einen Plan nach seinem String-Identifier.
	 * @return den gefundenen Plan oder <code>null</code> falls nichts gefunden
	 * @param id der Identifier-String
	 */
	public Plan getPlanById(String id);

	/**
	 * Löscht den Plan aus der Datenbank.
	 * @param plan der Plan
	 */
	public void deletePlan(Plan plan);

	/**
	 * Speichert alle Änderungen am Plan in der Datenbank, legt ihn an, wenn noch nicht vorhanden.
	 * @param plan der Plan
	 */
	public void updatePlan(Plan plan);
};
