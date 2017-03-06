goog.provide("edu.kit.informatik.studyplan.client.model.plans.Semester");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {Backbone.Collection}
 * This class represents a semester of a plan.
 * (This class is great, believe me: It's great. It's one of the best classes I've ever seen.)
 */

edu.kit.informatik.studyplan.client.model.plans.Semester = edu.kit.informatik.studyplan.client.model.module.ModuleCollection.extend( /** @lends {edu.kit.informatik.studyplan.client.model.plans.Semester.prototype}*/ {
    planId: null,
    semesterNum: 0,
    /** 
     * @type {edu.kit.informatik.studyplan.client.model.plans.SemesterCollection}
     */
    collection: null,
    /** 
     * Method which initializes the object
     * @param {Object=} attributes
     * @param {Object=} options
     * @return {?}
     */
    initialize: function (attributes, options) {
        this.collection = options.collection;
        this.listenTo(this, "destroy", this.onChange);
        this.listenTo(this, "change", this.onChange);
        this.listenTo(this, "all", this.onChange);
        this.listenTo(this, "add", this.onChange);
        this.listenTo(this, "reset", this.onChange);
    },
    /**
     * This method makes sure the semester collection (and plan) is being notified, when the semester changes
     */
    onChange: function () {
        this.collection.trigger("change");
    },
    /**
     * This method computes the url of the model
     */
    url: function () {
        return API_DOMAIN + "/plans/" + this.planId + "/modules"
    },
    /**
     * This method parses the content of a semesterCollection
     * @param {Object} response The input for parsing
     * @param {Object=} options Other configuration details
     * @return {Array<Object>}
     */
    parse: function (response, options) {
        "use strict";
        this.planId = response["planId"];
        this.semesterNum = response["semesterNum"];

        // Initialise modules
        for (var i = 0; i < response["modules"].length; i++) {
            response["modules"][i]["semester"] = this.semesterNum;
        }
        //console.log(response["modules"]);
        return edu.kit.informatik.studyplan.client.model.module.ModuleCollection.prototype.parse.apply(this, [response, options]);
    },
    /**
     * Method which converts the model to a serializable JSON object
     * @param {Object=} options
     * @return {Object}
     */
    toJSON: function (options) {
        var modules = [];
        var passedModules = edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().get('student').get('passedModules');
        this.each(function (curMod) {
            if (!passedModules.containsModule(curMod.get('id'))||options.includePassed) {
                modules.push(curMod.toJSON(options)["module"]);
            }
        });
        return {
            modules: modules
        }
    },
    /**
     * Method for retrieving the sum of ECTS in this semester
     * @return {number} The sum of creditpoints
     */
    getEctsSum: function () {
        var sum = 0;
        this.each(function (module) {
            sum += module.get('creditpoints');
        });
        return sum;
    }
});