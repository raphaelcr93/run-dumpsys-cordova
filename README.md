# run-dumpsys-cordova
Run dumpsys commands in cordova

usage 


    window.plugins.runDumpsys.run("dumpsys batterystats --charged com.ranieri.dumpsys", successcb, failcb);
   
    function successcb(s){
        console.log(s);//what you passed from Java code
    }
        
    function failcb(e){
        console.log("Err cb");
    }
