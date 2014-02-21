var Fastrada = function () {
};

Fastrada.prototype.generateALL = function () {
    maskKMH = "00010000";
    maskRPM = "00000000";
    maskTEMP = "00020000";
    
    var result = "";
    
    // Go to 120 Km/h
    for (var i = 0; i < 120; i++) {
        // KMH
        result += this.decToHex(i, maskKMH) + "<br />";
        
        // RPM
        result += this.decToHex(i * 16, maskRPM) + "<br />";
        
        // TEMP
        result += this.decToHex(Math.round(i / 2) + 40, maskTEMP) + "<br />";
    }
    
    // Go to 70
    for (var i = 120; i > 70; i--) {
        // KMH
        result += this.decToHex(i, maskKMH) + "<br />";
        
        // RPM
        result += this.decToHex(i * 16, maskRPM) + "<br />";
        
        // TEMP
        result += this.decToHex(Math.round(i / 2) + 40, maskTEMP) + "<br />";
    }
    
    // Go to 50
    for (var i = 70; i > 50; i--) {
        // KMH
        result += this.decToHex(i, maskKMH) + "<br />";
        
        // RPM
        result += this.decToHex(i * 16, maskRPM) + "<br />";
        
        // TEMP
        result += this.decToHex(Math.round(i / 2) + 40, maskTEMP) + "<br />";
    }
    
    // Go to 120 Km/h
    for (var i = 70; i < 120; i += 10) {
        // KMH
        result += this.decToHex(i, maskKMH) + "<br />";
        
        // RPM
        result += this.decToHex(i * 16, maskRPM) + "<br />";
        
        // TEMP
        result += this.decToHex(Math.round(i / 2) + 40, maskTEMP) + "<br />";
    }
    
    // Go to 0 Km/h (CRASHED)
    for (var i = 120; i <= 0; i += 10) {
        // KMH
        result += this.decToHex(i, maskKMH) + "<br />";
        
        // RPM
        result += this.decToHex(i * 16, maskRPM) + "<br />";
        
        // TEMP
        result += this.decToHex(Math.round(i / 2) + 40, maskTEMP) + "<br />";
    }
    
    var resultElement = document.querySelector("#result");
    resultElement.innerHTML = result;
}

Fastrada.prototype.generateKMH = function () {  
    var result = "";
    var mask = "00010000";
    
    // Go to 120 Km/h
    for (var i = 0; i < 120; i++) {
        result += this.decToHex(i, mask) + "<br />";
    }
    
    // Go to 70
    for (var i = 120; i > 70; i--) {
        result += this.decToHex(i, mask) + "<br />";
    }
    
    // Go to 50
    for (var i = 70; i > 50; i--) {
        result += this.decToHex(i, mask) + "<br />";
    }
    
    // Go to 120 Km/h
    for (var i = 70; i < 120; i += 10) {
        result += this.decToHex(i, mask) + "<br />";
    }
    
    // Go to 0 Km/h (CRASHED)
    for (var i = 120; i <= 0; i += 10) {
        result += this.decToHex(i, mask) + "<br />";
    }
    
    var resultElement = document.querySelector("#result");
    resultElement.innerHTML = result;
};

Fastrada.prototype.generateRPM = function () {  
    var result = "";
    var mask = "00000000";
    
    // Go till 5000 RPM by 10RPM
    for (var i = 0; i < 5000; i += 50) {
        result += this.decToHex(i, mask) + "<br />";
    }
    
    // Go to 0 again
    for (var i = 5000; i > 0; i -= 50) {
        result += this.decToHex(i, mask) + "<br />";
    }
    
    var resultElement = document.querySelector("#result");
    resultElement.innerHTML = result;
};

Fastrada.prototype.generateTEMP = function () {  
    var result = "";
    var mask = "00020000";
    
    // Go till 120 Celcius
    for (var i = 0; i < 120; i++) {
        result += this.decToHex(i, mask) + "<br />";
    }
    
    // Go to 65
    for (var i = 120; i > 65; i--) {
        result += this.decToHex(i, mask) + "<br />";
    }
    
    // Switch between 60 and 65
    result += this.decToHex(65, mask) + "<br />";
    result += this.decToHex(64, mask) + "<br />";
    result += this.decToHex(65, mask) + "<br />";
    result += this.decToHex(64, mask) + "<br />";
    result += this.decToHex(63, mask) + "<br />";
    result += this.decToHex(65, mask) + "<br />";
    result += this.decToHex(60, mask) + "<br />";
    result += this.decToHex(61, mask) + "<br />";
    result += this.decToHex(62, mask) + "<br />";
    result += this.decToHex(63, mask) + "<br />";
    result += this.decToHex(64, mask) + "<br />";
    result += this.decToHex(65, mask) + "<br />";
    result += this.decToHex(64, mask) + "<br />";
    result += this.decToHex(65, mask) + "<br />";
    result += this.decToHex(64, mask) + "<br />";
    result += this.decToHex(65, mask) + "<br />";

    // Go to 0 again
    for (var i = 65; i > 0; i--) {
        result += this.decToHex(i, mask) + "<br />";
    }
    
    var resultElement = document.querySelector("#result");
    resultElement.innerHTML = result;
};

Fastrada.prototype.decToHex = function (number, mask) {
    var result = number.toString(16);
    
    var resultLength = result.length;
    var maskLength = mask.length;
    
    var out = mask.substr(0, maskLength - resultLength) + result;
    return out;
};