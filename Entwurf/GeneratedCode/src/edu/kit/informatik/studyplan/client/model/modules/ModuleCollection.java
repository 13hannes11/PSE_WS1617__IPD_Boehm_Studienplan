// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.client.model.modules;

import edu.kit.informatik.studyplan.client.model.system.OAuthCollection;

/************************************************************/
/**
 * Eine Sammlung von Modulen, welche vom Server abgerufen werden kann
 */
public abstract class ModuleCollection extends OAuthCollection {

    /**
     * Generiert abh�ngig von der PlanId den URL f�r den Abruf der Module
     * 
     * @return Der generierte URL
     */
    public abstract String url();
};
