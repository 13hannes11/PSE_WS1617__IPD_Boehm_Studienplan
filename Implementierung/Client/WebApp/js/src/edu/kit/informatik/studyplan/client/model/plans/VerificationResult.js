goog.provide("edu.kit.informatik.studyplan.client.model.plans.VerificationResult");
/**
 * @constructor
 * @param {Object=} attributes
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.model.system.OAuthModel}
 */

edu.kit.informatik.studyplan.client.model.plans.VerificationResult = edu.kit.informatik.studyplan.client.model.system.OAuthModel.extend(/** @lends {edu.kit.informatik.studyplan.client.model.plans.VerificationResult.prototype}*/{
    url : function () {
        "use strict";
        return API_DOMAIN + "/plans/" + this.get('id') + '/verification';
    },
    parse : function (response, options) {
        "use strict";
        var result = response["plan"];
        if (typeof result["violations"] === "undefined") {
            result["violations"] = [];
        }
        var violations = [];
        for (var i = 0; i < result["violations"].length; i++) {
            violations.push(new edu.kit.informatik.studyplan.client.model.module.ModuleConstraint(result["violations"][i], {parse: true}));
        }
        var fieldViolations = [];
        if (typeof response["field-violations"] !== "undefined") {
            for (var i = 0; i<response["field-violations"].length; i++){
                fieldViolations[i] = new edu.kit.informatik.studyplan.client.model.system.Field(
                      response["field-violations"][i], {parse: true});
            }
        }
        var ruleGroupViolations = [];
        if(typeof response["rule-group-violations"] !== "undefined") {
            for(var i = 0; i<response["rule-group-violations"].length; i++){
               ruleGroupViolations[i] = new edu.kit.informatik.studyplan.client.model.plans.RuleGroup(
                      response["rule-group-violations"][i], {parse: true});
            }
        }
        response["field-violations"] = fieldViolations;
        response["rule-group-violations"] = ruleGroupViolations; 
        result["violations"] = violations;
        return result;
    }
});