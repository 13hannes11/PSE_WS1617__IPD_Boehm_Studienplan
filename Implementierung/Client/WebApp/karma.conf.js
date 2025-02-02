// Karma configuration
// Generated on Wed Jan 18 2017 10:02:55 GMT+0100 (Mitteleuropäische Zeit)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine-ajax', 'jasmine',  'requirejs'],


    // list of files / patterns to load in the browser
    files: [
      'test-main.js',
      //'node_modules/jasmine-ajax/lib/mock-ajax.js',
      {pattern: 'htdocs/js/app.js', included: false},
      {pattern: 'htdocs/lib/js.cookie.js', included: false},
      {pattern: 'htdocs/lib/jquery-ui.js', included: false},
      {pattern: 'htdocs/lib/jquery.js', included: false},
      {pattern: 'htdocs/lib/backbone.js', included: false},
      {pattern: 'htdocs/lib/underscore.js', included: false},
      {pattern: 'htdocs/lib/shepherd.min.js', included: false},
      {pattern: 'htdocs/lib/tether.min.js', included: false},
      {pattern: 'js/tests/**/*Spec.js', included: false}
    ],


    // list of files to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
        'htdocs/js/app.js': ['coverage']
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress','coverage'],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome', 'Firefox'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,

    // Concurrency level
    // how many browser should be started simultaneous
    concurrency: Infinity,
    coverageReporter: {
      type : 'html',
      dir : 'build/coverage/'
    }
  })
}
