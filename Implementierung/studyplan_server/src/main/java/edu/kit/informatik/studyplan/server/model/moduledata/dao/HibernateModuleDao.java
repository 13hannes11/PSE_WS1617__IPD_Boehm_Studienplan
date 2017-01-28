// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.model.moduledata.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.model.HibernateUtil;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Discipline;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.ModuleType;

/************************************************************/
/**
 * Ein konkretes ModulDao, welches die Datenbankverbindung über Hibernate
 * herstellt. Es kann nur auf Module, Kategorien und Vertiefungsfächer des im
 * Konstruktur angebenen Studiengangs zugreifen
 */
class HibernateModuleDao implements ModuleDao {

	@Override
	public Module getModuleById(String id) {
		Session session = startTransaction();
		Module module = session.bySimpleNaturalId(Module.class).load(id);
		cleanUp(session);
		return module;
	}

	@Override
	public List<Module> getModulesByFilter(Filter filter, Discipline discipline) {
		String queryString = createQueryString(filter);
		Session session = startTransaction();
		Query<Module> query = session.createQuery(queryString, Module.class);
		query.setParameter("discipline", discipline);
		List<Module> resultList = query.getResultList();
		cleanUp(session);
		return resultList;
	}

	private String createQueryString(Filter filter) {
		String whereClause = createWhereClause(filter);
		String queryString = "from Module module" + whereClause + "module.discipline = :discipline";
		return queryString;
	}

	@Override
	public Module getRandomModuleByFilter(Filter filter, Discipline discipline) {
		List<Module> modulesByFilter = getModulesByFilter(filter, discipline);
		int randomIndex = (int) (Math.random() * modulesByFilter.size());
		return modulesByFilter.get(randomIndex);
	}

	@Override
	public List<Discipline> getDisciplines() {
		Session session = startTransaction();
		List<Discipline> resultList = session.createQuery("from Discipline", Discipline.class).getResultList();
		cleanUp(session);
		return resultList;
	}

	@Override
	public List<Category> getCategories(Discipline discipline) {
		String queryString = "select distinct category "
				+ "from Module as module  "
				+ "join module.categories as category "
				+ "where module.discipline = :discipline";
		Session session = startTransaction();
		Query<Category> query = session.createQuery(queryString, Category.class);
		query.setParameter("discipline", discipline);
		List<Category> resultList = query.getResultList();
		cleanUp(session);
		return resultList;
	}

	@Override
	public Discipline getDisciplineById(int disciplineId) {
		Session session = startTransaction();
		Discipline discipline = session.byId(Discipline.class).load(disciplineId);
		cleanUp(session);
		return discipline;
	}

	@Override
	public List<Field> getFields(Discipline discipline) {
		Session session = startTransaction();
		String queryString = "from Field field where field.discipline = :discipline";
		Query<Field> query = session.createQuery(queryString, Field.class);
		query.setParameter("discipline", discipline);
		List<Field> resultList = query.getResultList();
		cleanUp(session);
		return resultList;
	}

	@Override
	public List<ModuleType> getModuleTypes() {
		Session session = startTransaction();
		session.beginTransaction();
		List<ModuleType> resultList = session.createQuery("from ModuleType", ModuleType.class).getResultList();
		cleanUp(session);
		return resultList;
	}
	
	@Override
	public List<Category> getSubjects(Field field) {
		Session session = startTransaction();
		session.beginTransaction();
		Query<Category> query = session.createQuery("select distinct category "
				+ "from Field as field  "
				+ "join field.modules as module "
				+ "join module.categories as category "
				+ "where field.fieldId = :id "
				+ "and category.isSubject = true", Category.class);
		List<Category> result = query.setParameter("id", field.getFieldId()).getResultList();
		cleanUp(session);
		return result;
	}

	@Override
	public Category getCategoryById(int id) {
		Session session = startTransaction();
		Category category = session.byId(Category.class).load(id);
		cleanUp(session);
		return category;
	}

	@Override
	public Field getFieldById(int id) {
		Session session = startTransaction();
		Field field = session.byId(Field.class).load(id);
		cleanUp(session);
		return field;
	}
	
	private String createWhereClause(Filter filter) {
		//TODO implement
		return null;
		
	}
	
	private Session startTransaction() {
		Session session = HibernateUtil.getModuleDataSessionFactory().openSession();
		session.beginTransaction();
		return session;
	}
	
	private void cleanUp(Session session) {
		session.getTransaction().commit();
		session.close();
	}

};
