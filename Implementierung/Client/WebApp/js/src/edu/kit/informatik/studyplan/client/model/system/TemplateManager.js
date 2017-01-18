goog.provide("edu.kit.informatik.studyplan.client.model.system.TemplateManager");
/**
 * @constructor
 * @extends {Backbone.Model}
 */

edu.kit.informatik.studyplan.client.model.system.TemplateManager = (function () {
    "use strict";
    /**
     * @type {TemplateRegistry}
     */
    var instance = null;
    /**
     * @private
     * @constructor
     */
    function TemplateRegistry() {
        /**
         * Variable storing all of the templates
         * @protected
         * @type {Object<string,function(Object):string>}
         */
        this.templates = {};
        /**
         * @public
         * @param {string} key The key of the template
         * @param {string} html The HTML content of the template
         */
        this.addTemplate = function (key, html) {
            this.templates[key] = _.template(html);
        };
        /**
         * @public
         * @param{string} key The key of the template
         * @throws {TemplateNotFoundException}
         */
        this.getTemplate = function (key) {
            if (typeof this.templates[key] !== "undefined") {
                return this.templates[key];
            } else {
                console.error("[TemplateFactory] Cannot find template '" + key + "'.");
            }
        };
    }
    return {
        /** 
         * @return {TemplateRegistry}
         */
        getInstance : function () {
            if (instance === null) {
                instance = new TemplateRegistry();
            }
            return instance;
        }
    };
}());