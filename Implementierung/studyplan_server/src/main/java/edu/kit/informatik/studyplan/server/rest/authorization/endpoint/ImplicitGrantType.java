package edu.kit.informatik.studyplan.server.rest.authorization.endpoint;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.tomcat.util.codec.binary.Base64;

import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AbstractSecurityProvider;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationContext;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.AuthorizationScope;
import edu.kit.informatik.studyplan.server.model.userdata.authorization.RESTClient;
import edu.kit.informatik.studyplan.server.model.userdata.dao.UserDao;
import edu.kit.informatik.studyplan.server.model.userdata.dao.UserDaoFactory;

/**
 * Diese Klasse repräsentiert einen ImplicitGrantType { @see RFC 6749 Kapitel
 * 1.3.2}.
 */
public class ImplicitGrantType implements GrantType {
	/**
	 * Erstellt einen ImplicitGrantType.
	 */
	public ImplicitGrantType() {

	}

	@Override
	public AuthorizationContext getLogin(RESTClient client, AuthorizationScope scope,
			List<String> authorizationHeader) {
		if (authorizationHeader.isEmpty()) {
			return null;
		}
		String[] split = authorizationHeader.get(0).split(" ");
		if (split.length != 2 || !split[0].equals("Basic")) {
			return null;
		}
		String decoded = new String(Base64.decodeBase64(split[1]));
		split = decoded.split(":");
		if (split.length != 2) {
			return null;
		}
		String userName = split[0];
		UserDao dao = UserDaoFactory.getUserDao();
		User user = dao.getUserByName(userName);
		if (user == null) {
			user = new User();
			user.setUserName(userName);
			dao.updateUser(user);
		}
		AbstractSecurityProvider provider = AbstractSecurityProvider.getSecurityProviderImpl();
		AuthorizationContext context = provider.generateAuthorizationContext(user, client, scope);
		dao.cleanUp();
		return context;
	}

	@Override
	public void postToken(MultivaluedMap<String, String> params) {
		//does nothing for this grant type
	}

}
