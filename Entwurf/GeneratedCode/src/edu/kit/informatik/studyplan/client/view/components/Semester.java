// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.client.view.components;

import java.awt.Event;
import java.util.Collection;

import edu.kit.informatik.studyplan.client.view.components.ModuleBox;

/************************************************************/
/**
 * Klasse, welche ein Semester innerhalb eines Studienplans kapselt
 */
public class Semester {
    /**
     * Die Module, welche in dem Semester liegen
     */
    private Semester moduleCollection;
    /**
     * Die ModulBoxen, welche die Module des Semesters anzeigen
     */
    private Collection<ModuleBox> moduleBox;
    /**
     * Die Ausrichtung des Studienplans: Normalerweise links, im Fall des
     * Vergleichs aber auch rechts.
     */
    private String align;
    /**
     * Variable, die festsetzt, ob das Semester entfernbar ist
     */
    private boolean isRemovable;
    /**
     * Variable, die angibt, das wie vielte Semester dieses ist (von 1 zählend)
     */
    private int number;

    /**
     * Methode zum löschen des aktuellen Semesters
     */
    public void removeSemester() {
    }

    /**
     * Methode, welche beim Drop eines Moduls auf das Semester das hinzufügen
     * ausführt
     * 
     * @param event
     *            Der JQuery Event
     * @param ui
     *            Das JQuery UI Objekt
     */
    public void onDrop(final Event event, final Object ui) {
    }

    /**
     * Methode zum Links-Scrollen der Semester Leiste
     */
    public void scrollLeft() {
    }

    /**
     * Methode zum Rechts-Scrollen der Semester Leiste
     */
    public void scrollRight() {
    }
};
