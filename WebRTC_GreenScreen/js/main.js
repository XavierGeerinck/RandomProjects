var GreenScreen = function () {
    this.navigator = window.navigator;
    this.navigator.getMedia = this.navigator.getUserMedia || this.navigator.webkitGetUserMedia || this.navigator.mozGetUserMedia || this.navigator.msGetUserMedia;
    this.videoElement = document.querySelector("#webcam");
    this.canvasElement = document.querySelector("#webcam_processed");
    this.ctx = document.querySelector("#webcam_processed").getContext('2d');
};

GreenScreen.prototype.start = function () {
    var self = this;
    
    // Get the webcam
    this.navigator.getMedia({ video: true }, function (localMediaStream) {
        self.videoElement.src = window.URL.createObjectURL(localMediaStream);  
        
        // Start processing
        self.renderCanvas();
    });
};

GreenScreen.prototype.renderCanvas = function () {
    // Draw the video
    this.ctx.drawImage(this.videoElement, 0, 0, this.videoElement.width, this.videoElement.height);
    
    // Get image data
    var frame = this.ctx.getImageData(0, 0, this.videoElement.width, this.videoElement.height);
    
    // Process Image Data
    this.processFrame(frame.data);
    
    // Put New Image Data
    this.ctx.putImageData(frame, 0, 0);
    
    // Render again
    requestAnimationFrame(this.renderCanvas.bind(this));
};

/**
 * A frame exists out of an array of pixels (RGBA)
 * R = Red   (0 - 255)
 * G = Green (0 - 255)
 * B = Blue  (0 - 255)
 * A = Alpha (0 - 255, 0 being transparant and 255 fully visible)
 * 
 * imageData.data[0] = pixel1 red
 * imageData.data[1] = pixel1 green
 * imageData.data[2] = pixel1 blue
 * imageData.data[3] = pixel1 alpha
 * imageData.data[4] = pixel2 red
 * imageData.data[5] = pixel2 green
 * imageData.data[6] = pixel2 blue
 * imageData.data[7] = pixel2 alpha
 */
GreenScreen.prototype.processFrame = function(frame) {
    // Loop through the pixels
    for (var i = 0; i < frame.length; i += 4) {
        // Get Pixel Data
        var red   = frame[i + 0];        
        var green = frame[i + 1];        
        var blue  = frame[i + 2];        
        var alpha = frame[i + 3];        
        
        var hsb = this.RGB2HSB(red, green, blue);
        
        if (this.matchesColor(hsb[0], hsb[1], hsb[2])) {
            frame[i + 0] = 0;    
            frame[i + 1] = 0;    
            frame[i + 2] = 0;    
            frame[i + 3] = 255;    
        }
    }
};

GreenScreen.prototype.matchesColor = function(hue, saturation, brightness) {
    var matches = 0;
    
    // check HUE
       // Green = 81 - 140
    if (hue >= 81 && hue <= 160) {
        matches++;    
    }
    
    // check Saturation
    if (saturation >= 15 && saturation <= 85) {
        matches++;    
    }
    
    // check Brightness
    if (brightness >= 15 && brightness <= 85) {
        matches++;    
    }
    
    // If 3 matches, return true
    return (matches == 3) ? true : false;
};

/**
 * Converts RGB to HSB
 * H The Hue
 * S The Saturation
 * B The brightness
 */
GreenScreen.prototype.RGB2HSB = function (r, g, b) {
    r /= 255, g /= 255, b /= 255;
    var max = Math.max(r, g, b), min = Math.min(r, g, b);
    var h, s, l = (max + min) / 2;

    if (max == min){
        h = s = 0; // achromatic
    } else {
        var d = max - min;
        s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
        
        switch (max) {
            case r: h = (g - b) / d + (g < b ? 6 : 0); break;
            case g: h = (b - r) / d + 2; break;
            case b: h = (r - g) / d + 4; break;
        }
        
        h /= 6;
    }

    return [Math.floor(h * 360), Math.floor(s * 100), Math.floor(l * 100)];
};