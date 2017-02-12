goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.Plan");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.Plan = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.Plan.prototype} */{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/plan.html"),
    model: null,
    events: {
        "click .addSemesterButton": "addSemester"
    },
    semesterElements : [],
    isAddable: true,
    isPreferencable: true,
    isPassedPlan: false,
    initialize: function (options) {
        this.isAddable = (typeof options.isAddable !== "undefined") ? options.isAddable : this.isAddable;
        this.model = options.plan;
        this.isPreferencable = (typeof options.isPreferencable !== "undefined") ? options.isPreferencable : this.isPreferencable;
        this.isPassedPlan = (typeof options.isPassedPlan !== "undefined") ? options.isPassedPlan : this.isPassedPlan;
        this.listenTo(this.model, "change", this.reload);
        
        this.reload();
    },
    reload: function () {
        var semesterCol = this.model.get('semesterCollection');
        this.semesterElements = [];
        semesterCol.each((function (semester) {
            this.semesterElements.push(
                new edu.kit.informatik.studyplan.client.view.components.uielement.Semester({
                    semester: semester,
                    isRemovable: true,
                    isPreferencable: this.isPreferencable,
                    isPassedPlan: this.isPassedPlan
                })
            );
        }).bind(this));
        this.render();
    },
    render: function () {
        this.$el.html(this.template({
            plan: this.model,
            isAddable: this.isAddable
        }));
        _.each(this.semesterElements, (function (el) {
            el.render();
            this.$el.find(".semesters").append(el.$el);
        }).bind(this));
        this.delegateEvents();
    },
    /**
    *
    */
    addSemester: function () {
        "use strict";
        
        var newSemester = new edu.kit.informatik.studyplan.client.model.plans.Semester({
                planId : this.model.get("planId"),
                semesterNum : this.model.get('semesterCollection').length,
                modules : []
        },{parse:true, collection: this.model});
                                                                                       
        this.model.get('semesterCollection').push(newSemester);
        this.reload();
    },
    /**
    *
    */
    onChange: function () {
        "use strict";
    }
});