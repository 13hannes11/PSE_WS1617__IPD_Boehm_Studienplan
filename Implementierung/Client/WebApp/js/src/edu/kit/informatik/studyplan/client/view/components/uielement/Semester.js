goog.provide("edu.kit.informatik.studyplan.client.view.components.uielement.Semester");
/**
 * @constructor
 * @param {Object=} options
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.components.uielement.Semester = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.components.uielement.Semester.prototype} */{
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uielement/semester.html"),
    tagName: "tr",
    model: null,
    moduleElements: [],
    isPassedSemester: false,
    isPassedPlan: false,
    ulOffset: 0,
    events : {
        "click button.prev": "clickPrev",
        "click button.next": "clickNext"
    },
    initialize: function (options) {
        this.model = options.semester;
        this.isRemovable = options.isRemovable;
        this.isPreferencable = options.isPreferencable;
        this.isPassedSemester = options.isPassedSemester;
        this.isPassedPlan = options.isPassedPlan;
        this.reload();
        this.listenTo(this.model, "change", this.reload);
        this.listenTo(this.model, "destroy", this.reload);
        this.listenTo(this.model, "all", this.reload);
        this.listenTo(this.model, "reset", this.reload);
        this.listenTo(this.model, "add", this.reload);
        this.reload();
    },
    reload: function () {
        this.ulOffset = 0;
        this.moduleElements=[];
        this.model.each(function (el) {
            var removable = true;
            if(!this.isPassedPlan&&this.isPassedSemester) {
                removable = false;
            }
            if(!this.isPassedPlan&&el.get('passed')){
                removable = false;
            }
            var draggable = true;
            if(!this.isPassedPlan&&el.get('passed')){
                draggable = false;
            }
            console.log("SEMESTER UI");
            console.log(el);
            this.moduleElements.push(
                new edu.kit.informatik.studyplan.client.view.components.uielement.ModuleBox({
                    module: el,
                    isDraggable: draggable,
                    isRemovable: removable,
                    isPreferencable: this.isPreferencable,
                    isPassedPlanModule: this.isPassedPlan
                })
            );
        }.bind(this));
        this.render();
    },
    /**
     * @this {Backbone.View}
     * @suppress {missingProperties}
     * @return {Backbone.View|null}
     */
    render: function () {
        var tdWidth = this.$el.innerWidth();
        this.$el.find(".semesterModules").css({
            transform: "translate("+this.ulOffset+"px,0px)",
            width: (this.moduleElements.length*260+50)+"px"
        })
        this.$el.droppable({
            drop: this.onDrop.bind(this)
        });
        _.each(this.moduleElements, function (element) {
            element.render();
            this.$el.find(".semesterModules").append(element.$el);
        }.bind(this));
        this.delegateEvents();
        return null;
    },
    clickPrev: function () {
        this.changeOffset(50);
    },
    clickNext: function () {
        this.changeOffset(-50);
    },
    changeOffset: function (val) {
        console.info("changing offset")
        var tdWidth = this.$el.innerWidth();
        var ulWidth = this.$el.find(".semesterModules").innerWidth();
        if(ulWidth-tdWidth+250>-(this.ulOffset+val)){
            this.ulOffset+=val;
        }
        if(this.ulOffset>0){
            this.ulOffset=0;
        }
        console.info(this.ulOffset);
        this.$el.find(".semesterModules").css({
            transform: "translate("+this.ulOffset+"px,0px)",
            width: (this.moduleElements.length*260+100)+"px"
        })
        
    },
/**
*
*/
    removeSemester:
        function () {
            "use strict";
        },
    /**
    *@param{Event} event
    *@param{Object} ui
    */
    onDrop:function (event, ui) {
        console.info("[edu.kit.informatik.studyplan.client.view.components.uielement.Semester] drop event");
        //TODO: Make module deletable when it wasn't before!
        var droppedElement = ui.helper.data("viewObject");
        /**
         * @type {edu.kit.informatik.studyplan.client.model.module.Module}
         */
        var droppedModel = droppedElement.model;
        if (!(droppedModel.collection instanceof edu.kit.informatik.studyplan.client.model.plans.Semester)){
            droppedModel = /** @type {edu.kit.informatik.studyplan.client.model.module.Module} */(droppedModel.clone());
            droppedModel.collection = null;
            // Do not insert a module twice
            if (this.model.collection.containsModule(droppedModel.get('id'))) {
                var LM = edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance();
                edu.kit.informatik.studyplan.client.model.system.NotificationCollection.getInstance().add(
                    new edu.kit.informatik.studyplan.client.model.system.Notification({
                        title: LM.getMessage('notInsertTwiceTitle'),
                        text: LM.getMessage('notInsertTwiceText'),
                        wasShown: false,
                        type: "error"
                    })
                );
                return false;
            }
        }
        if (droppedModel.collection!==this.model) {
            var oldCol = droppedModel.collection;
            var oldSem = droppedModel.get('semester');
            if(droppedModel.collection!==null){
                droppedModel.collection.remove(droppedModel);
            }
            droppedModel.set('semester', this.model.semesterNum);
            this.model.add(droppedModel);
            droppedModel.collection = this.model;
            droppedModel.save(null, {
                error: function () {
                    /*console.log(droppedModel);
                    this.model.remove(droppedModel);
                    if (oldCol!==null) {
                        droppedModel.set('semester', oldSem);
                        oldCol.add(droppedModel);
                    }
                    console.log(droppedModel);*/
                }.bind(this)
            });
        }
    },
    /**
    *
    */
    scrollLeft:
        function () {
            "use strict";
        },
    /**
    *
    */
    scrollRight:
        function () {
            "use strict";
        }
});