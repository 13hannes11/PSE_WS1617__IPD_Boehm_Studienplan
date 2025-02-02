goog.provide("edu.kit.informatik.studyplan.client.model.system.OAuthCollection");
/**
 * @constructor
 * @extends {Backbone.Collection}
 * Represents a collection of OAuth Models
 */

edu.kit.informatik.studyplan.client.model.system.OAuthCollection = Backbone.Collection.extend( /** @lends {edu.kit.informatik.studyplan.client.model.system.OAuthCollection.prototype}*/ {
    sync: edu.kit.informatik.studyplan.client.storage.OAuthSync
});