// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package edu.kit.informatik.studyplan.server.generation.objectivefunction;

import edu.kit.informatik.studyplan.server.model.userdata.Plan;

/************************************************************/
/**
 * AtomObjectiveFunction ist eine Teilzielfunktion, die nur eine Eigenschaft
 * berücksichtigt.
 */
public abstract class AtomObjectiveFunction implements PartialObjectiveFunction {

  /*
   * {@inheritDoc}
   */
  public abstract double evaluate(Plan plan);
};
