define(["studyplan"], function (studyplan) {
    "use strict";
    describe("PlanCollection", function () {
        var planCollection = new studyplan.model.plans.PlanCollection({
            plans : [
                {
                    id : 5,
                    status : "invalid",
                    "creditpoints-sum" : 20,
                    name : "test",
                    modules : [
                        {
                            id: 5,
                            name: "testest",
                            semester: 3,
                            creditpoints: 19,
                            lecturer : "Samuel"
                        },
                        {
                            id: 6,
                            name: "testest",
                            semester: 5,
                            creditpoints: 1,
                            lecturer: "Teuber"
                        }
                    ],
                    "violations" : [
                        {
                            name: "Blöder Constraint",
                            first: {
                                id : 5
                            },
                            second: {
                                id: 42
                            },
                            type: "abc"
                        }
                    ]
                },
                {
                    id : 5,
                    status : "invalid",
                    "creditpoints-sum" : 20,
                    name : "test2"
                }
            ]
        },{parse:true});
        //console.log(planCollection);
        // TODO: Proper testing
    });
});