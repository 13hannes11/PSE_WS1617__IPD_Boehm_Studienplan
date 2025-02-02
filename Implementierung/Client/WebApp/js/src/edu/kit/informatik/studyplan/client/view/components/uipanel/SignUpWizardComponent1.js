goog.provide("edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent1");
/**
 * @constructor
 * @param {Object=} options
 * @extends {edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
 * saves beginning date and discipline
 * the first signupwizard: adds the new student some information: year of studystart and discipline.
 */

edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent1 = edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent.extend( /** @lends {edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent1.prototype}*/ {
    //amount of years that shall be counted back for semesters    
    countBackYears: 10,    
    //just for the dropdown of studystart options
    beginningArray: [],
    //the new student
    student: null,
    disciplines: null,
    template: edu.kit.informatik.studyplan.client.model.system.TemplateManager.getInstance().getTemplate("resources/templates/components/uipanel/signUpWizardComponent1.html"),
    //a dropdown for discipline and one for studystart
    events: {
        "change select.beginningdateDropDown": "onChange1",
        "change select.disciplineDropDown": "onChange2"
    },
    //current date to generate options of studystart
    date: null,

    /** 
     * constructor: sets date on the current year, initialize a disciplineCollection and fetches content from server --> neccesary for the discipline dropdown. needs new  student as a parameter
     */
    initialize: function (options) {
        "use strict";
        this.date = new Date(Date.now()).getFullYear();
        this.beginning(this.date);
        this.student = options.student;
        edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().showLoading();
        this.disciplines = new edu.kit.informatik.studyplan.client.model.system.DisciplineCollection();
        this.disciplines.fetch({
            success: function () {
                this.render();
                edu.kit.informatik.studyplan.client.router.MainRouter.getInstance().hideLoading();
            }.bind(this)
        });
    },

    /**
     *renders that template. Template needs the disciplines and the studystart as parameters to display the content of the dropdowns.
     */
    render: function () {
        "use strict";
        this.$el.html(this.template({
            disciplines: this.disciplines,
            beginningVal: this.beginningArray
        }));
        this.delegateEvents();
    },



    /**
     *@return{edu.kit.informatik.studyplan.client.view.components.uipanel.WizardComponent}
     * onChange1 and onChange2 will be triggered, lest the dropdowns weren't changed, so nothing wouldn't set without that.
     * initialize a SignupWizard2 and gives the current student as a parameter.
     */
    next: function () {
        "use strict";
        this.onChange1();
        this.onChange2();
	
        var temp = new edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent2({
            student: this.student
        });
        return temp;
    },
    /**
     *saves Changes of the studybegin-dropdown at current student
     */
    onChange1: function () {
        "use strict";
        // console.log("[edu.kit.informatik.studyplan.client.view.components.uipanel.SignUpWizardComponent1] semester:")
        // console.log(this.beginningArray[(this.$el.find("select.beginningdateDropDown").val())]);
        this.student.set('studyStartYear', this.beginningArray[(this.$el.find("select.beginningdateDropDown").val())]['year']);
        this.student.set('studyStartCycle', this.beginningArray[this.$el.find("select.beginningdateDropDown").val()]['type']);

    },

    /**
     *saves Changes of the discipline at current student.
     */
    onChange2: function () {
        "use strict";
        this.student.set('discipline', this.disciplines.get(this.$el.find("select.disciplineDropDown").val()).get('id'));
    },

    /**
     *creates the values of the studystart options caused at the actual year.
     */
    beginning: function (curyear) {
        "use strict";
        var exactDate = new Date(Date.now()).getMonth();
        //checks if the winterterm has allready begun
        if(exactDate>8){
            var temp = curyear;
            this.beginningArray.push(this.makeTerm(curyear, "ST"));
        }
	    //checks if the summerterm has allready begun
        if(exactDate>2){
            var temp = curyear;
            this.beginningArray.push(this.makeTerm(curyear, "ST"));
        }
        for (var i = 1; i <= this.countBackYears; i++) {
            var temp = curyear - i;
            this.beginningArray.push(this.makeTerm(curyear - i, "WT"));
            this.beginningArray.push(this.makeTerm(curyear - i, "ST"));
        }
    },
    makeTerm : function(year, type){
        "use strict";
        //if(type != "ST" || type != "WT") {
          //  return null;
        //}
        var tmp = {};
        tmp.year = year;
        tmp.type = type;
        if (type == "ST") {
            tmp.name = (edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance().getMessage("summerterm") + year);
        } else if (type == "WT") {
            tmp.name = (edu.kit.informatik.studyplan.client.model.system.LanguageManager.getInstance().getMessage("winterterm") + year + "/" + (year + 1));
        }
        return tmp;
    }
});
