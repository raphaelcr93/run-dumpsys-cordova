// Empty constructor
function RunDumpsys() {}

// The function that passes work along to native shells
// command is a string
RunDumpsys.prototype.run = function(command, successCallback, errorCallback) {
  var options = {};
  options.command = command;
  cordova.exec(successCallback, errorCallback, 'RunDumpsys', 'run', [options]);
}

// Installation constructor that binds the plugin to window
RunDumpsys.install = function() {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.runDumpsys = new RunDumpsys();
  return window.plugins.runDumpsys;
};
cordova.addConstructor(RunDumpsys.install);