goog.provide("edu.kit.informatik.studyplan.client.model.user.SessionInformation");
/**
 * @class
 * @name edu.kit.informatik.studyplan.client.model.user.SessionInformation
 * @extends {edu.kit.informatik.studyplan.client.model.system.CookieModel}
 */

edu.kit.informatik.studyplan.client.model.user.SessionInformation = (function () {
    /**
     * @this {edu.kit.informatik.studyplan.client.model.user.SessionInformation}
     */
    "use strict";
    /**
     * @type {edu.kit.informatik.studyplan.client.model.user.SessionInformation}
     */
    var instance = null;
    /**
     * @constructor
     * @name edu.kit.informatik.studyplan.client.model.user.SessionInformation
     */
    var Constructor = edu.kit.informatik.studyplan.client.model.system.CookieModel.extend({
        // cookie storage name
        url : "edu.kit.informatik.studyplan.client.model.user.SessionInformation.storage",
        /**
        * Method which sets a random value for state
        */
        generateState : function () {
            var random = null;
            // Use crypto API if available
            if (window.crypto.getRandomValues) {
                var array = new Uint32Array(20),
                    randomNumber = 1;
                window.crypto.getRandomValues(array);
                var i;
                for (i = 0; i < array.length; i++) {
                    randomNumber *= array[i];
                }
                random = randomNumber.toString(36);
            } else {
                random = Math.ceil(Math.random() * 10000000 + 1000000).toString(36);
            }
            this.set('state', random.substr(0,30));
            this.save();
        },
        getLoginUrl : function () {
            if(!this.has('state')){
                this.generateState();
            }
            return API_DOMAIN + "/auth/login?response_type=token&client_id="+API_KEY+"&scope=student&state="+this.get('state');
        },
        isLoggedIn: function () {
            return edu.kit.informatik.studyplan.client.model.user.SessionInformation.getInstance().has('access_token');
        }
    });
    return {
        /**
         * @return {edu.kit.informatik.studyplan.client.model.user.SessionInformation}
         */
        getInstance : function () {
            if (instance === null) {
                instance = new Constructor();
            }
            if (!instance.get('wasLoaded')){
                instance.fetch({
                    success: function () {
                        instance.set('wasLoaded', true);
                    }
                });
            }
            return instance;
        }
    };
}());