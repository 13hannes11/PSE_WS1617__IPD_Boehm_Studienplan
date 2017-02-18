package edu.kit.informatik.studyplan.server.generation.standard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import edu.kit.informatik.studyplan.server.filter.CategoryFilter;
import edu.kit.informatik.studyplan.server.filter.Filter;
import edu.kit.informatik.studyplan.server.generation.Generator;
import edu.kit.informatik.studyplan.server.generation.objectivefunction.PartialObjectiveFunction;
import edu.kit.informatik.studyplan.server.model.moduledata.Category;
import edu.kit.informatik.studyplan.server.model.moduledata.Field;
import edu.kit.informatik.studyplan.server.model.moduledata.Module;
import edu.kit.informatik.studyplan.server.model.moduledata.RuleGroup;
import edu.kit.informatik.studyplan.server.model.moduledata.constraint.PrerequisiteModuleConstraintType;
import edu.kit.informatik.studyplan.server.model.moduledata.dao.ModuleDao;
import edu.kit.informatik.studyplan.server.model.userdata.ModuleEntry;
import edu.kit.informatik.studyplan.server.model.userdata.Plan;
import edu.kit.informatik.studyplan.server.model.userdata.PreferenceType;
import edu.kit.informatik.studyplan.server.model.userdata.User;
import edu.kit.informatik.studyplan.server.model.userdata.VerificationState;

/**
 * The SimpleGenerator Class is a concrete Generator that implements the
 * Generator Interface. It uses a graph structure to sort and parallelize
 * modules, and an objective Function to evaluate the plans.
 * 
 * @author Nada_Chatti
 * @version 1.0
 */
public class SimpleGenerator implements Generator {

	/**
	 * List of Nodes of the Graph to create.
	 */
	private NodesList nodes;

	/**
	 * @return the nodes of the graph to create
	 */
	public NodesList getNodes() {
		return nodes;
	}

	public Plan generate(PartialObjectiveFunction objectiveFunction, final Plan currentPlan, ModuleDao moduleDAO,
			Map<Field, Category> preferredSubjects, int maxSemesterEcts) {
		Map<Plan, NodesList> planFamily;
		Iterator<Plan> it;
		Plan plan = currentPlan;
		// first generation of family of plans with change of all randomly added
		// nodes
		planToGraph(currentPlan);
		planFamily = randomlyGeneratedFamilyOfPlans(nodes, plan, preferredSubjects, -1, maxSemesterEcts, moduleDAO);
		it = planFamily.keySet().iterator();
		plan = new Plan();
		plan.setUser(currentPlan.getUser());
		if (it.hasNext()) {
			plan = it.next();
		}
		while (it.hasNext()) {
			Plan nextPlan = it.next();
			if (objectiveFunction.evaluate(nextPlan) > objectiveFunction.evaluate(plan)) {
				plan = nextPlan;
			}
		}
		// Save the nodes list from which this plan was created
		NodesList planNodesList;
		for (int i = 0; i < 5; i++) {
			planNodesList = planFamily.get(plan);
			planFamily = randomlyGeneratedFamilyOfPlans(planNodesList, plan, preferredSubjects, 10, maxSemesterEcts,
					moduleDAO);
			it = planFamily.keySet().iterator();
			plan = new Plan();
			plan.setUser(currentPlan.getUser());
			if (it.hasNext()) {
				plan = it.next();
			}
			while (it.hasNext()) {
				Plan nextPlan = it.next();
				if (objectiveFunction.evaluate(nextPlan) > objectiveFunction.evaluate(plan)) {
					plan = nextPlan;
				}
			}
		}
		plan.getPreferences().addAll(currentPlan.getPreferences());
		return plan;
	}

	/**
	 * Adds the modules needed to reach the required credit points for the given
	 * field.
	 * 
	 * @param field
	 *            which modules are being added
	 * @param category
	 *            preferred to get the preferred modules
	 * @param currentPlan
	 *            the plan being generated
	 */
	void addFieldModules(Field field, Category category, Plan currentPlan, ModuleDao moduleDAO) {
		int creditPoints = nodes.getCreditPoints(field);
		if (creditPoints >= field.getMinEcts()) {
			return;
		}
		List<Module> preferredModules;
		// set of random numbers to choose modules randomly from the list
		Set<Integer> randomNumbers;
		// to iterate through the set above
		if (category != null) {
			preferredModules = getModulesWithPreference(currentPlan, field.getModules(), category,
					PreferenceType.POSITIVE, moduleDAO);
		} else {
			preferredModules = field.getModules();
		}
		randomNumbers = getRandomNumbers(preferredModules.size(), preferredModules.size());
		// Iterator to iterate through the set above
		Iterator<Integer> it = randomNumbers.iterator();
		// add modules from preferred modules in the category chosen
		while (creditPoints < field.getMinEcts() && it.hasNext()) {
			Node node = new NodeWithOutput(preferredModules.get(it.next()), currentPlan, this);
			if (nodes.add(node)) {
				nodes.getRandomlyAddedNodes().add(node);
				node.fulfillConstraints(true);
			}
			creditPoints += node.getModule().getCreditPoints();
		}
		if (creditPoints >= field.getMinEcts()) {
			return;
		}
		List<Module> notEvaluatedModules = getModulesWithPreference(currentPlan, field.getModules(), category, null,
				moduleDAO);
		randomNumbers = getRandomNumbers(notEvaluatedModules.size(), notEvaluatedModules.size());
		it = randomNumbers.iterator();
		/*
		 * if preferred modules do not reach the credit points needed add
		 * modules from not evaluated modules in the category chosen
		 */
		while (creditPoints < field.getMinEcts() && it.hasNext()) {
			Node node = new NodeWithOutput(notEvaluatedModules.get(it.next()), currentPlan, this);
			if (nodes.add(node)) {
				nodes.getRandomlyAddedNodes().add(node);
				node.fulfillConstraints(true);
			}
			creditPoints += node.getModule().getCreditPoints();
		}
		notEvaluatedModules = field.getModules();
		randomNumbers = getRandomNumbers(notEvaluatedModules.size(), notEvaluatedModules.size());
		it = randomNumbers.iterator();
		/*
		 * if preferred modules do not reach the credit points needed add
		 * modules from not evaluated modules in the category chosen
		 */
		while (creditPoints < field.getMinEcts() && it.hasNext()) {
			Node node = new NodeWithOutput(notEvaluatedModules.get(it.next()), currentPlan, this);
			if (nodes.add(node)) {
				nodes.getRandomlyAddedNodes().add(node);
				node.fulfillConstraints(true);
			}
			creditPoints += node.getModule().getCreditPoints();
		}

		if (creditPoints >= field.getMinEcts()) {
			return;
		}

		if (creditPoints < field.getMinEcts()) {
			throw new IllegalArgumentException("CreditPoints of the Category " + category.getName() + " with id "
					+ category.getCategoryId() + " < minECTS of field " + field.getName());
		}
	}

	/**
	 * Generates a complete Plan with random Modules
	 * 
	 * @param nodes
	 *            the nodes list to generate plan from.
	 * @param plan
	 *            plan from which a new plan is being generated.
	 * @param preferredSubjects
	 *            a mapping of the categories chosen for each field
	 * @param maxECTSperSemester
	 *            maximum amount of credit points per semester
	 * @param moduleDAO
	 *            the moduleDao used to fetch modules
	 * @return a Map containing only one key (the plan generated) and one value
	 *         (the nodesList from which the plan was generated) for later
	 *         modification.
	 */
	private GenerationResult randomlyGeneratedPlan(NodesList nodes, Plan plan,
			Map<Field, Category> preferredSubjects, int maxECTSperSemester, ModuleDao moduleDAO) {
		// adding modules of the rule groups of the discipline
		List<RuleGroup> ruleGroups = plan.getUser().getDiscipline().getRuleGroups();
		for (RuleGroup ruleGroup : ruleGroups) {
			addRuleGroupModules(ruleGroup, plan, preferredSubjects.get(ruleGroup), moduleDAO);
		}
		// adding modules of the fields of the discipline
		List<Field> fields = plan.getUser().getDiscipline().getFields();
		for (Field field : fields) {
			addFieldModules(field, preferredSubjects.get(field), plan, moduleDAO);
		}
		List<Node> sorted = nodes.sort();
		GenerationResult result = new GenerationResult(createPlan(sorted, parallelize(sorted, maxECTSperSemester), plan.getUser()), nodes);
		return result;
	}

	/**
	 * Creates a plan based on the sorted list of nodes and the array of
	 * semester allocation given.
	 * 
	 * @param sorted
	 *            the sorted list of nodes based on which the plan would be
	 *            created.
	 * @param semesterAllocation
	 *            an array of the number of the semester allocated to each node
	 *            of the list
	 * @param user
	 *            to set the new plan's user
	 * @return the created plan
	 */
	Plan createPlan(List<Node> sorted, int[] semesterAllocation, User user) {
		Plan plan = new Plan();
		for (int i = 0; i < sorted.size(); i++) {
			ModuleEntry entry = new ModuleEntry(sorted.get(i).getModule(), semesterAllocation[i]);
			plan.getModuleEntries().add(entry);
			Node n = sorted.get(i);
			while (n.hasInnerNode()) {
				n = n.getInnerNode();
				plan.getModuleEntries().add(new ModuleEntry(n.getModule(), semesterAllocation[i]));
			}
			n = sorted.get(i);
			while (n.hasOuterNode()) {
				n = n.getOuterNode();
				plan.getModuleEntries().add(new ModuleEntry(n.getModule(), semesterAllocation[i]));
			}

		}
		plan.setUser(user);
		plan.setVerificationState(VerificationState.VALID);
		return plan;
	}

	/**
	 * Adds the modules needed to reach the required number of modules that
	 * belong to the given rule group.
	 * 
	 * @param ruleGroup
	 *            which modules are being added
	 * @param category
	 *            preferred to get the preferred modules
	 * @param currentPlan
	 *            the plan given originally to the generator
	 * @param moduleDAO
	 *            the moduleDao used to fetch modules
	 */
	void addRuleGroupModules(RuleGroup ruleGroup, Plan currentPlan, Category category, ModuleDao moduleDAO) {
		int num = nodes.nodesInRuleGroup(ruleGroup).size();
		if (num >= ruleGroup.getMinNum() && num <= ruleGroup.getMaxNum()) {
			return;
		}
		List<Module> preferredModules;
		// set of random numbers to choose modules randomly from the list
		Set<Integer> randomNumbers;
		// to iterate through the set above
		preferredModules = getModulesWithPreference(currentPlan, ruleGroup.getModules(), category, PreferenceType.POSITIVE,
				moduleDAO);
		// to iterate through the set above
		Iterator<Integer> it;
		if (num > ruleGroup.getMaxNum()) {
			randomNumbers = getRandomNumbers(num, num);
			it = randomNumbers.iterator();
		}
		if (ruleGroup.getMaxNum() != -1) {
			while (num > ruleGroup.getMaxNum()) {
				if (!nodes.nodesInRuleGroup(ruleGroup).isEmpty()) {
					nodes.remove(nodes.nodesInRuleGroup(ruleGroup).get(0));
				}
			}
		}
		randomNumbers = getRandomNumbers(preferredModules.size(), preferredModules.size());
		it = randomNumbers.iterator();
		while (num < ruleGroup.getMinNum() && it.hasNext()) {
			Node node = new NodeWithOutput(preferredModules.get(it.next()), currentPlan, this);
			if (nodes.add(node)) {
				nodes.getRandomlyAddedNodes().add(node);
				node.fulfillConstraints(true);
			}
			num += 1;
		}
		List<Module> notEvaluatedModules = getModulesWithPreference(currentPlan, ruleGroup.getModules(), category, null,
				moduleDAO);
		randomNumbers = getRandomNumbers(notEvaluatedModules.size(), notEvaluatedModules.size());
		it = randomNumbers.iterator();
		/*
		 * if preferred modules do not reach the credit points needed add
		 * modules from not evaluated modules in the category chosen
		 */
		while (num < ruleGroup.getMinNum() && it.hasNext()) {
			Node node = new NodeWithOutput(notEvaluatedModules.get(it.next()), currentPlan, this);
			if (nodes.add(node)) {
				nodes.getRandomlyAddedNodes().add(node);
				node.fulfillConstraints(true);
			}
			num += 1;
		}
	}

	/**
	 * Parallelize the sorted list of nodes given and transform it to a plan.
	 * That means that it spreads the nodes so that they can fit in respective
	 * semesters to create a graph.
	 * 
	 * @param sorted
	 *            topologically sorted list of nodes
	 * @param maxECTSperSemester
	 *            maximum amount of credit points per semester
	 * @return an array containing the number of the semester allocated to each
	 *         node
	 */
	int[] parallelize(List<Node> sorted, int maxECTSperSemester) {
		WeightFunction weight = new WeightFunction();
		Node node;
		boolean set;
		int[] bucketAllocation = new int[sorted.size()];
		int[] bucketSum = new int[sorted.size()];
		int[] minPos = new int[sorted.size()];
		for (int i = 0; i < minPos.length; i++) {
			minPos[i] = 1;
		}
		for (int i = 0; i < sorted.size(); i++) {
			node = sorted.get(i);
			set = false;
			for (int j = minPos[i]; j < sorted.size(); j++) {
				if (weight.getWeight(node) + bucketSum[j] <= maxECTSperSemester
						&& checkIfOverlapping(node, bucketAllocation, sorted, j) && node.fitsInSemester(j)) {
					bucketAllocation[i] = j;
					bucketSum[j] += weight.getWeight(node);
					for (Node child : node.getChildren()) {
						if ((node.getConstraint(child) != null)
								&& (node.getConstraint(child)
										.getConstraintType() instanceof PrerequisiteModuleConstraintType)
								&& sorted.contains(child)) {
							minPos[sorted.indexOf(child)] = Math.max(j + 1, minPos[i]);
						}
					}
					set = true;
					break;
				}

			}
			if (!set) {
				throw new IllegalArgumentException("Node" + node.getModule().getIdentifier() + "and its inner nodes "
						+ "have too many Credit Pointsfor a single Semester");
			}
		}
		return bucketAllocation;
	}

	/**
	 * Checks if there is a node that has a constraint from type overlapping
	 * with the node given in the semester with the number given.
	 * 
	 * @param node
	 *            the node concerned
	 * @param bucketAllocation
	 *            an array containing the number of the semester allocated to
	 *            each node with index i in the sorted list
	 * @param sorted
	 *            the sorted list of nodes
	 * @param semesterNum
	 *            number of the semester concerned
	 * @return -true if there is a node that has a constraint from type
	 *         overlapping with the node given in the semester with the number
	 *         given, -false if not.
	 */
	private boolean checkIfOverlapping(Node node, int[] bucketAllocation, List<Node> sorted, int semesterNum) {
		for (Node n : nodes.getOverlappingNodes(node)) {
			if (bucketAllocation[sorted.indexOf(n)] == semesterNum) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Generates a family of plans with random Modules
	 * 
	 * @param nodes
	 *            the nodes list to generate plan from.
	 * @param currentPlan
	 *            plan from which a new plan is being generated.
	 * @param preferredSubjects
	 *            a mapping of the categories chosen for each field
	 * @param numberOfNodesToChange
	 *            the number of nodes to change in the random modification phase
	 * @param maxECTSperSemester
	 *            maximum amount of credit points per semester
	 * @param moduleDAO
	 *            the moduleDao used to fetch modules
	 */
	Map<Plan, NodesList> randomlyGeneratedFamilyOfPlans(NodesList nodes, Plan currentPlan,
			Map<Field, Category> preferredSubjects, int numberOfNodesToChange, int maxECTSperSemester,
			ModuleDao moduleDAO) {
		Map<Plan, NodesList> planFamily = new HashMap<Plan, NodesList>();
		GenerationResult generated = randomlyGeneratedPlan(nodes, currentPlan, preferredSubjects, maxECTSperSemester,
				moduleDAO);
		planFamily.put(generated.getPlan(), generated.getNodesList());
		for (int i = 0; i < 9; i++) {
			if (numberOfNodesToChange == -1) {
				numberOfNodesToChange = nodes.getRandomlyAddedNodes().size();
			}
			generated = randomlyModifiedPlan(numberOfNodesToChange, generated, preferredSubjects, maxECTSperSemester, moduleDAO);
			planFamily.put(generated.getPlan(), generated.getNodesList());
		}
		return planFamily;
	}

	/**
	 * Transforms a Plan given to a graph with: - creating Nodes to represent
	 * all ModulEntries - fulfilling all constraints of the nodes (except the
	 * passed modules) - add nodes to the nodes attribute
	 * 
	 * @param plan
	 *            the plan to use
	 */

	public void planToGraph(Plan plan) {
		nodes = new NodesList(plan, this);
		Node node;
		// Create a Node for every ModuleEntry and add it to the list of nodes
		for (int i = 0; i < plan.getModuleEntries().size(); i++) {
			Module m = plan.getModuleEntries().get(i).getModule();
			node = nodes.get(m);
			if (node == null) {
				node = new NodeWithOutput(m, plan, this);
				node.setSemester(plan.getModuleEntries().get(i).getSemester());
				nodes.add(node);
			} else {
				nodes.add(node);
			}
			node.fulfillConstraints(false);
		}
	}

	/**
	 * Returns a set of i random numbers that are < max.
	 * 
	 * @param max
	 *            maximum of numbers needed
	 * @param i
	 *            number of Integers needed
	 * @return set of i random numbers that are < max.
	 */
	private Set<Integer> getRandomNumbers(int max, int i) {
		Set<Integer> generated = new LinkedHashSet<Integer>();
		Random rand = new Random();
		while (generated.size() < i) {
			Integer next = rand.nextInt(max);
			generated.add(next);
		}
		return generated;
	}

	/**
	 * Returns a list of the modules that belong to the list and category given
	 * and that has the preference given. If the category is null (the field is
	 * not choosable) this method would search through all modules in the list
	 * given.
	 * 
	 * @param currentPlan
	 *            the plan being generated.
	 * @param field
	 *            the field which modules are needed.
	 * @param category
	 *            the category chosen.
	 * @return the list of preferred modules.
	 */
	List<Module> getModulesWithPreference(Plan currentPlan, List<Module> listOfModules, Category category,
			PreferenceType preference, ModuleDao moduleDAO) {
		List<Module> modules = new ArrayList<Module>();
		if (category != null) {
			Filter filter = new CategoryFilter(category);
			for (Module m : moduleDAO.getModulesByFilter(filter, currentPlan.getUser().getDiscipline())) {
				if (((currentPlan.getPreferenceForModule(m) == preference) && listOfModules.contains(m))
						|| (preference == null && listOfModules.contains(m))) {
					modules.add(m);
				}
			}
		} else {
			for (Module m : listOfModules) {
				if ((currentPlan.getPreferenceForModule(m) == preference)) {
					modules.add(m);
				}
			}
		}
		return modules;
	}

	/**
	 * Modifies a plan given with changing a given number of previously randomly
	 * added nodes and returns the new plan.
	 * 
	 * @param numberOfNodes
	 *            number of nodes to change
	 * @param generated
	 *            a pair of the plan and the nodeslist the plan was created from
	 * @param preferredSubjects
	 * @return the new plan
	 */
	private GenerationResult randomlyModifiedPlan(int numberOfNodes, GenerationResult generated,
			Map<Field, Category> preferredSubjects, int maxECTSperSemester, ModuleDao moduleDAO) {

		NodesList nodes = generated.getNodesList();
		Set<Integer> randomNumbers = getRandomNumbers(nodes.getRandomlyAddedNodes().size(),
				Math.min(numberOfNodes, nodes.getRandomlyAddedNodes().size()));
		Iterator<Integer> it = randomNumbers.iterator();
		while (it.hasNext()) {
			nodes.remove(nodes.getRandomlyAddedNodes().get(it.next()));
		}
		return randomlyGeneratedPlan(nodes, generated.getPlan(), preferredSubjects, maxECTSperSemester, moduleDAO);
	}

}
