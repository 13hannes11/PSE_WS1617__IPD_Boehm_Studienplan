// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.model.userdata.authorization;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.NaturalId;

/************************************************************/
/**
 * Modelliert einen Klienten, der auf die REST-Schnittstelle zugreifen kann
 * siehe Kapitel ????
 */
@Entity
@Table (name = "rest_client")
public class RESTClient {
	
	@Id
	@Column(name = "client_id")
	private int clientId;
	/**
	 * 
	 */
	@NaturalId
	@Column(name = "api_key")
	private String apiKey;
	/**
	 * 
	 */
	@Column(name = "api_secret")
	private String apiSecret;
	/**
	 * 
	 */
	@Column(name = "origin")
	private String origin;
	/**
	 * 
	 */
	@Column(name = "redirect_url")
	private String redirectUrl;
	/**
	 * 
	 */
	@Transient
	//TODO find solution
	private List<AuthorizationScope> scopes;

	/**
	 * 
	 * @return gibt die eindeutige ID des Klienten zurück
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * 
	 * @return gibt die nur dem Klienten bekannte Kennung zurück
	 */
	public String getApiSecret() {
		return apiKey;
	}

	/**
	 * 
	 * @return gibt die Domain, von welcher aus der Client auf Ressourcen
	 *         zugreifen kann als regulären Ausdruck zurück.
	 */
	public String getOrigin() {
		return apiKey;
	}

	/**
	 * 
	 * @return gibt die Weiterleitungs-URL zurück
	 */
	public String getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * 
	 * @return gibt eine Liste aller Berechtigungen zurück, die vom Client
	 *         angefragt werden können
	 */
	public List<AuthorizationScope> getScopes() {
		ArrayList<AuthorizationScope> scopes = new ArrayList<AuthorizationScope>();
		scopes.add(AuthorizationScope.STUDENT);
		return scopes;
	}
};
