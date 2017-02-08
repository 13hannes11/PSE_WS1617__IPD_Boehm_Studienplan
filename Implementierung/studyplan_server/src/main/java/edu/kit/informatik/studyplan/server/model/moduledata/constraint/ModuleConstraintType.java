package edu.kit.informatik.studyplan.server.model.moduledata.constraint;

import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;

import javax.persistence.*;

/**
 * Class modeling the type of a module constraint
 * @author NiklasUhl
 * @version 1.0
 */
@Entity
@Table(name = "constraint_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "description")
public abstract class ModuleConstraintType {

	@Id
	@Column(name = "type_id")
	private int id;
	
	@Column(name = "description", insertable = false, updatable = false)
	private String description;
	
	@Column(name = "formal_description")
	private String formalDescription;

	/**
	 * Überprüft, ob für zwei gegebene Moduleinträge die Abhängigkeit dieses
	 * Typs erfüllt ist.
	 * 
	 * @param first
	 *            der erste Moduleintrag (Subjekt des aktuellen
	 *            Verifikationsschritts)
	 * @param second
	 *            der zweite Moduleintrag (Zweites zusätzlich geladenes Modul
	 *            des aktuellen Verifikationsschritts)
	 * @param orientation
	 *            Richtung in die die Relation überprüft werden soll. <br>
	 *            Jedes Constraint besitzt eine Richtung. Diese ist über die
	 *            Eintragung der Module in Modul1 und Modul2 gegeben. Wenn nun
	 *            gilt: first:=Modul1 UND second:=Modul2 so ist die orientation
	 *            LEFT_TO_RIGHT; Wenn aber gilt: first:=Modul2 UND
	 *            second:=Modul1 so ist die orientation RIGHT_TO_LEFT<br>
	 *            Dieses zusätzliche Attribut ist notwendig, da Informationen
	 *            über zwei gerichtete Relationen übergeben werden müssen:
	 *            <ol>
	 *            <li>Welches Modul aktuell untersucht wird und welches "nur"
	 *            das zweite Modul des Constraints ist</li>
	 *            <li>Welches Modul im Constraint Modul1 und welches Modul2 ist
	 *            </li>
	 *            </ol>
	 *            1. wird über die Anordnung der Parameter übergeben, 2. über
	 *            das Parameter orientation.
	 * @return Ergebnis der Überprüfung
	 */
	public abstract boolean isValid(ModuleEntry first, ModuleEntry second, ModuleOrientation orientation);

	/**
	 * 
	 * @return gibt die textuelle Beschreibung der Abhängigkeit zurück
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 *            die textuelle Beschreibung
	 */
	void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return gibt die Abhängigkeitsbeschreibung in Form eines logischen
	 *         Ausdrucks zurück
	 */
	public String getFormalDescription() {
		return description;
	}

	/**
	 * 
	 * @param formalDescription
	 *            der logische Ausdruck
	 */
	void setFormalDescription(String formalDescription) {
		this.formalDescription = formalDescription;
	}
};
