goog.provide("edu.kit.informatik.studyplan.client.view.subview.ProfilPage");
/**
 * @constructor
 * @extends {Backbone.View}
 */

edu.kit.informatik.studyplan.client.view.subview.ProfilPage = Backbone.View.extend(/** @lends {edu.kit.informatik.studyplan.client.view.subview.ProfilPage.prototype} */{
    /**
    *
    */
    close:
        function () {
            "use strict";
        },
    /**
    *@param {edu.kit.informatik.studyplan.client.model.module.Module} module
    */
    showModuleDetails:
        function (module) {
            "use strict";
    },
    
    render: function () {
        this.$el.html($(this.template()));
        var listDiv = this.$el.find(".mainPagePlanList");
        this.planList.render();
        listDiv.append(this.planList.$el);
        this.delegateEvents();
    },
    /**
    *
    */
    hideModuleDetails:
        function () {
            "use strict";
        }
});