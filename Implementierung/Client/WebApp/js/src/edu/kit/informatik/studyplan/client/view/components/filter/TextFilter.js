goog.provide("edu.kit.informatik.studyplan.client.view.components.filter.TextFilter");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent}
 */

edu.kit.informatik.studyplan.client.view.components.filter.TextFilter = edu.kit.informatik.studyplan.client.view.components.filter.FilterComponent.extend( /** @lends {edu.kit.informatik.studyplan.client.view.components.filter.TextFilter.prototype}*/ {
    /**
     * html template for this element
     */
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/filter/textFilter.html"),
    events: {
        "input .textFilterInput": "textChange"
    },
    /**
     * updates model from HTML
     * @param {Object} event
     */
    textChange: function (event) {
        if (event.target.id == this.filter.get("id")) {
            this.filter.set("curValue", this.$el.find(".textFilterInput").val());
        }
    }
});