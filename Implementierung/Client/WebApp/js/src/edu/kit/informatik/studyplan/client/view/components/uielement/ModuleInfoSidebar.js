goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.ModuleInfoSidebar");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 * Class which represents a sidebar showing relevant information for each module
 */

edu.kit.informatik.studyplan.client.view.components.uielement.ModuleInfoSidebar = Backbone.View.extend( /** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.ModuleInfoSidebar.prototype} */ {
    model: null,
    className: "moduleInfo",
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/moduleInfoSidebar.html"),
    moduleBoxes: {},
    events: {
        "click button.close": "onClose"
    },
    isPreferencable: true,
    initialize: function (options) {
        this.model = options.module;
        this.isPreferencable = (typeof options.isPreferencable !== "undefined") ? options.isPreferencable : this.isPreferencable;
        this.listenTo(this.model, "change", this.reload.bind(this));
        this.reload();
    },
    /**
     * Method which reloads and then renders the internal collections of view elements once the model is changed
     */
    reload: function () {
        //console.info(this.model.get('constraints'));
        //console.log(_.result(this.model.collection, "url"));
        this.moduleBoxes = {};
        _.each(this.model.get('constraints'), function (constraint) {
            if(constraint.isRelevantFor(this.model.get('id'))){
                _.each(["first", "second"], function (nthModule) {
                    if (constraint.get(nthModule).get('id') != this.model.get('id')) {
                        constraint.get(nthModule).urlRoot = _.result(this.model.collection, "url");
                        var tmpModuleBox = new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox({
                            module: constraint.get(nthModule),
                            //TODO: einstellbar
                            isRemovable: false,
                            isDraggable: true,
                            isPreferencable: this.isPreferencable
                        });
                        if (!this.moduleBoxes[constraint.get('type')]) {
                            this.moduleBoxes[constraint.get('type')] = [];
                        }
                        this.moduleBoxes[constraint.get('type')].push(tmpModuleBox)
                    }
                }.bind(this));
            }
        }.bind(this));
        this.render();
    },
    render: function () {
        //console.info("[edu.kit.informatik.studyplan.client.view.components.uielement.ModuleInfoSidebar] shown module:");
        //console.info(this.model);
        this.$el.html(this.template({
            module: this.model
        }))
        //console.info("[edu.kit.informatik.studyplan.client.view.components.uielement.ModuleInfoSidebar] rendering module info Sidebar")
        //console.info(this.moduleBoxes);
        //console.info(_.pairs(this.moduleBoxes));
        // Go through all constraint types
        _.each(_.pairs(this.moduleBoxes), function (constraintTypes) {
            var constraintEl = this.$el.find("." + constraintTypes[0] + "Constraints");
            //console.log("[edu.kit.informatik.studyplan.client.view.components.uielement.ModuleInfoSidebar] constraintType div:");
            //console.info(constraintEl);
            constraintEl.html("");
            if (constraintEl.length > 0) {
                // Go through
                _.each(constraintTypes[1], function (moduleBox) {
                    //console.info("[edu.kit.informatik.studyplan.client.view.components.uielement.ModuleInfoSidebar] moduleBox:")
                    //console.info(moduleBox);
                    moduleBox.render();
                    constraintEl.append(moduleBox.$el)
                });
            } else {
                throw new Error("No constraint div of class ." + constraintTypes[0] + "Constraints")
            }
        }.bind(this));
        this.delegateEvents();
    },
    /**
     * Method which is called when the ModuleInfoSidebar should be closed
     */
    onClose: function () {
        "use strict";
        edu.kit.informatik.studyplan.client.model.system.EventBus.trigger("hideModuleInfo");
    }
});
