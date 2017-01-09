// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.client.view;

import edu.kit.informatik.studyplan.client.model.user.SessionInformation;
import edu.kit.informatik.studyplan.client.view.components.uipanel.NotificationCentre;

import org.backbone.BackboneView;

/************************************************************/
/**
 * Der MainView kapselt die Benutzeroberfläche, er kann mittels gegebener
 * Funktionen mit beliebigem Inhalt in Form von Objekten des Typs Backbone.View
 * befüllt werden.
 */
public class MainView {
    /**
     * Die Informationen zur Session
     */
    private SessionInformation sessionInformation;
    /**
     * Die View, welcher im Header angezeigt wird.
     */
    private BackboneView header;
    /**
     * Die View, welche im Content-Bereich angezeigt wird.
     */
    private BackboneView content;
    /**
     * Der NotificationCentre, welcher die Anzeige von Benachrichtigungen
     * übernimmt.
     */
    private NotificationCentre notificationCentre;

    /**
     * Mit dieser Methode kann man den Header des MainViews setzen
     * 
     * @param header
     *            Der zu setzende Header
     * @param options
     *            Objekt mit welchem der Header initialisiert werden soll
     */
    public void setHeader(final BackboneView header, final Object options) {
    }

    /**
     * Mit dieser Funktion kann man den Content-Bereich des MainViews setzen.
     * 
     * @param content
     *            Der zu setztende Content-Bereich
     * @param options
     *            Objekt mit welchem der Header initialisiert werden soll
     */
    public void setContent(final BackboneView content, final Object options) {
    }
};
