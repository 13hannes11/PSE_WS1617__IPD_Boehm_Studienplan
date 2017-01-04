// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.client.model.system;

import edu.kit.informatik.studyplan.client.model.system.OAuthModel;

/************************************************************/
/**
 * Klasse welcher die Daten zu einem Filter speichert
 */
public class Filter extends OAuthModel {
    /**
     * Name des Filters
     */
    private String name;
    /**
     * Typ des Filters ("range", "list", "contains")
     */
    private String type;
    /**
     * Die den Filter spezifizierenden Eigenschaften
     */
    private Object parameters;
    // TODO: In UML übertragen
    /**
     * Die ID des Filters
     */
    private String id;
    /**
     * Der Standardwert des Filters (Struktur abhängig vom Filter)
     */
    private Object defaultValue;
    /**
     * Ein Tooltip, der den Filter textuell beschreibt
     */
    private String tooltip;
    /**
     * Der aktuelle Wert des Filters
     */
    private Object curValue;

    /**
     * Konvertiert die Filter-Daten in GET-Query-Parameter
     * 
     * @return Die GET-Query-Parameter
     */
    public String getParams() {
        return null;

    }
};
