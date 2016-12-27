var StudyplanApp={Exceptions:{}};StudyplanApp.Exceptions.AppException=function(){};StudyplanApp.Exceptions.TemplateNotFoundException=function(b){this.key=b};StudyplanApp.Templating={};StudyplanApp.Templating.TemplateRegistryInterface=function(){};StudyplanApp.Templating.TemplateRegistryInterface.prototype.getTemplate=function(b){};StudyplanApp.Templating.TemplateRegistryInterface.prototype.addTemplate=function(b,a){};StudyplanApp.Templating.TemplateRegistry=function(){function b(){this.templates={};this.addTemplate=function(a,b){this.templates[a]=_.template(b)};this.getTemplate=function(a){if("undefined"!==typeof this.templates[a])return this.templates[a];console.error("[TemplateFactory] Cannot find template '"+a+"'.");throw new StudyplanApp.Exceptions.TemplateNotFoundException(a);}}var a=null;return{getInstance:function(){null===a&&(a=new b);return a}}}();StudyplanApp.Sync={};StudyplanApp.Sync.CookieSync=function(b,a,c){var e=a.url();c.cookieName=e;var d={create:function(a,b){Cookies.set(b.cookieName,a)},read:function(a,b){return Cookies.getJSON(b.cookieName)},update:function(a,b){if(b.patch){var c=Cookies.getJSON(b.cookieName);_.map(a.toJSON(),function(a,b){c[b]=a});d.create(c,b)}else d.create(a,b)},"delete":function(a,b){Cookies.remove(b.cookieName)}};return d[b](a,c)};function TemplateLoader(){this.templates=this.templates||{};this.templates["templates/header.html"]=function(b){return'<div id="icon">\r\n    <h1>StudyPlan</h1>\r\n</div>\r\n<div id="userInfo">\r\n    \r\n            <a href="#">Profil</a> | <a href="#">Logout</a>\r\n    \r\n        <a href="#">Login</a>\r\n    \r\n</div>'};this.templates["templates/login.html"]=function(b){return"Bitte einloggen."};this.templates["templates/wrapper.html"]=function(b){var a;return""+('<div id="appWrapper">\r\n    <div id="header">\r\n        \r\n    </div>\r\n    <div id="content">\r\n    Hallo '+
(null==(a=b.name)?"":a)+"\r\n    </div>\r\n</div>")}}TemplateLoader.bind(StudyplanApp.Templating.TemplateRegistry.getInstance())();StudyplanApp.Views={};
StudyplanApp.Views.AppView=Backbone.View.extend({el:"body",template:StudyplanApp.Templating.TemplateRegistry.getInstance().getTemplate("templates/wrapper.html"),curHeaderView:null,curContentView:null,initialize:function(){this.render();this.headerElement=$("<div id='header'></div>");this.$el.append(this.headerElement);this.contentElement=$("<div id='content'></div>");this.$el.append(this.contentElement)},render:function(){null!==this.curHeaderView&&this.curHeaderView.render();null!==this.curContentView&&
this.curContentView.render();this.$el.show()},setHeader:function(b,a){null!==this.curHeaderView&&this.curHeaderView.remove();a.el=this.headerElement;this.curHeaderView=new b(a)},setContent:function(b,a){null!==this.curContentView&&this.curContentView.remove();a.el=this.contentElement;this.curContentView=new b(a)}});StudyplanApp.Views.SubViews={};StudyplanApp.Views.SubViews.Header=Backbone.View.extend({initialize:function(){},tagName:"div",template:StudyplanApp.Templating.TemplateRegistry.getInstance().getTemplate("templates/header.html"),render:function(){this.$el.html(this.template({}));this.$el.show()}});StudyplanApp.Views.SubViews.Login=Backbone.View.extend({initialize:function(){},template:StudyplanApp.Templating.TemplateRegistry.getInstance().getTemplate("templates/login.html"),render:function(){this.$el.html(this.template({}));this.$el.show()}});StudyplanApp.Routers={};StudyplanApp.Routers.MainRouter=Backbone.Router.extend({routes:{"":"entryPoint"},entryPoint:function(){console.log("/");App.setHeader(StudyplanApp.Views.SubViews.Header,{});App.setContent(StudyplanApp.Views.SubViews.Login,{});App.render()}});var App=new StudyplanApp.Views.AppView;