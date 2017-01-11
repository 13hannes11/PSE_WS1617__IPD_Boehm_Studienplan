// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.model.userdata.dao;

import edu.kit.informatik.studyplan.server.model.userdata.User;

/************************************************************/
/**
 * DataAccessObject zum Zugriff auf Nutzer in der Datenbank
 */
public interface UserDao {

	/**
	 * Löscht den übergebenen Nutzer aus der Datenbank
	 * @param user der Nutzer
	 */
	public void deleteUser(User user);

	/**
	 * Speichert die Änderungen am übergebenen Nutzer in der Datenbank<br>
	 * Handelt es sich um einen neuen Nutzer, so wird dieser angelegt.
	 * @param user der Nutzer
	 */
	public void updateUser(User user);

	/**
	 * Sucht nach dem ensprechenden Nutzer in der Datenbank. <br>
	 * So kann ein Nutzer über die ID oder seinen Nutzernamen gefunden werden.
	 * @param user der zu suchende Nutzer 
	 * @return der gefundene Nutzer
	 */
	public User findUser(User user);

};
