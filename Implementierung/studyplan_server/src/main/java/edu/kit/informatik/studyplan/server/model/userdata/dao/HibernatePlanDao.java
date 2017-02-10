// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.model.userdata.dao;

import java.io.Serializable;

import org.hibernate.Session;

import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/************************************************************/
/**
 * Ein konkretes PlanDao, welches die Datenbankverbindung über Hibernate
 * herstellt.
 */
class HibernatePlanDao implements PlanDao {

	private Session session;
	
	HibernatePlanDao(AuthorizationContext context) {
		session = ((SecurityProvider) context.getProvider()).getSession();
	}
	
	@Override
	public Plan getPlanById(String id) {
		session.beginTransaction();
		Plan plan = session.byId(Plan.class).load(id);
		session.getTransaction().commit();
		return plan;
	}

	@Override
	public void deletePlan(Plan plan) {
		session.beginTransaction();
		for (ModuleEntry entry : plan.getModuleEntries()) {
			session.delete(entry);
		}
		session.delete(plan);
		session.getTransaction().commit();
	}

	@Override
	public String updatePlan(Plan plan) {
		session.beginTransaction();
		session.saveOrUpdate(plan);
		Serializable identifier = session.getIdentifier(plan);
		session.getTransaction().commit();
		return (String) identifier;
	}

	@Override
	public void cleanUp() {
	}

};
