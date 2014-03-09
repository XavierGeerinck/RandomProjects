var FaceRecognition = function (haarCascadeUrl, ccv) {
    this.faces = [];
    this.haar_cascade_location = haarCascadeUrl;
    this.haar_cascade = null;
    this.ccv = ccv;
};

FaceRecognition.prototype.start = function(faces) {
    this.faces = faces;
    
    for (var i in faces) {
        //var imageData = this.getImageData(faces[i]);
        //this.RGBA2Greyscale(imageData);
        //var integralImage = this.calculateIntegralImageFromGreyscale(imageData);
        //this.detectObjects(imageData, integralImage);
        
        this.ccvDetectObjects(faces[i]);  
    }
};

FaceRecognition.prototype.startWebRTC = function() {
    // Create canvas and webcam video stream
    this.webcam = document.createElement('video');
    this.webcam.setAttribute('id', 'webcam');
    this.webcam.setAttribute('height', 512);
    this.webcam.setAttribute('width', 512);
    this.webcam.setAttribute('autoplay', 512);
    
    var canvas = document.createElement('canvas');
    canvas.setAttribute('id', 'canvas');
    canvas.setAttribute('height', 512);
    canvas.setAttribute('width', 512);
    
    document.body.appendChild(this.webcam);
    document.body.appendChild(canvas);
    
    // get user media
    var navigator = window.navigator;
    navigator.getMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
    
    var self = this;
    this.counter = 0;
    
    // Get the webcam
    navigator.getMedia({ video: true }, function (localMediaStream) {
        self.webcam.src = window.URL.createObjectURL(localMediaStream);  
        
        // Start processing
        self.renderVideo();
    });
};

FaceRecognition.prototype.renderVideo = function() {
    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext('2d');
    var self = this;
    
    // Draw the video
    ctx.drawImage(this.webcam, 0, 0, this.webcam.width, this.webcam.height);
    
    var frame = ctx.getImageData(0, 0, this.webcam.width, this.webcam.height);
    var img = new Image();
    img.src = canvas.toDataURL("image/jpg", 0.8);
    img.onload = function() {
        self.ccvDetectObjects(img, 'canvas', canvas, ctx, function () {
            var imgData = ctx.getImageData(0, 0, img.width, img.height);
            ctx.putImageData(imgData, img.width, img.height);

            // Recall
            setTimeout(function () { self.renderVideo() }, 1000 / 60);     
        });    
    };
};

FaceRecognition.prototype.ccvDetectObjects= function(image, fileName, canvas, ctx, callback) {
    if (!fileName) {
        fileName = image.src.substring(image.src.lastIndexOf('/') + 1);
        fileName = fileName.substring(0, fileName.length - 4); // Remove .jpg
    }
    
    if (!canvas) {
        canvas = document.getElementById(fileName);
    }
    
    if (!ctx) {
        ctx = canvas.getContext('2d');    
    }
    
    this.ccv.detect_objects(
        { 
            "canvas" : this.ccv.grayscale(this.ccv.pre(image)),
            "cascade" : this.haar_cascade,
            "interval" : 5,
            "min_neighbors" : 1,
            "async" : true,
            "worker" : 1 
        }
    )(function (comp) {
        console.log(comp);
        var scale = 1;

        /* draw detected area */
        for (var i = 0; i < comp.length; i++) {
            var x = comp[i].x * scale;
            var y = comp[i].y * scale;
            var width = comp[i].width;
            var height = comp[i].height;
            
            ctx.beginPath();
            ctx.rect(x, y, width, height);
            ctx.lineWidth = 5;
            ctx.strokeStyle = 'red';
            ctx.stroke();
            ctx.closePath();
        }    
        
        if (callback) callback();
    });    
};

FaceRecognition.prototype.loadHaarCascade = function () {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", this.haar_cascade_location, false); // false gets it synch
    xhr.send();
    
    this.haar_cascade = JSON.parse(xhr.responseText);
};

FaceRecognition.prototype.detectObjects = function(imageData, integralImage) {
    var scanBoxWidth = this.haar_cascade.width;
    var scanBoxHeight = this.haar_cascade.height;
    
    var canvas = this.drawImageDataToCanvas(imageData); // TEST
    var context = canvas.getContext('2d');
    
    // Loop over the width and height of the picture in boxes of the scanBoxHeight and scanBoxWidth
    for (var y = 0; y < Math.round(imageData.height / scanBoxHeight); y++) {
        for (var x = 0; x < Math.round(imageData.width / scanBoxWidth); x++) {
            var x1 = x * scanBoxWidth;
            var y1 = y * scanBoxHeight;
            var x2 = x1 + scanBoxWidth;
            var y2 = y1 + scanBoxHeight;
            
            // Loop through the stages, if a stage matches continue to the next stage, else continue to the next box
            for (var stageCount = 0; stageCount < this.haar_cascade.stage_classifier.length; stageCount++) {
                //console.log("Stage: " + (stageCount + 1) + " / " + this.haar_cascade.stage_classifier.length);
            }
            //console.log("X: " + x + " Y: " + y);
        }
    }
};

/**
 * Get the image data from a picture without needing a canvas
 * 
 * Returns an array that holds the RGBA values from a picture:
 * imageData[0] = Pixel 1 Red
 * imageData[1] = Pixel 1 Green
 * imageData[2] = Pixel 1 Blue
 * imageData[3] = Pixel 1 Alpha
 * 
 * Loop through it with the use of a for loop
 * 
 * for (var i = 0; i < pixels.length; i += 4) {
 *     var red   = pixels[i + 0];
 *     var green = pixels[i + 1];
 *     var blue  = pixels[i + 2];
 *     var alpha = pixels[i + 3];
 * }
 */
FaceRecognition.prototype.getImageData = function (picture) {
    var canvas = document.createElement("canvas");
    canvas.setAttribute('height', picture.height);
    canvas.setAttribute('width', picture.width);
    
    var context = canvas.getContext('2d');
    context.drawImage(picture, 0, 0);
    
    return context.getImageData(0, 0, picture.width, picture.height);
};


/**
 * Convert the picture to grey scale
 * algorithm:
 * red * 0.3 + green * 0.59 + blue * 0.11
 */
FaceRecognition.prototype.RGBA2Greyscale = function (imageData) {
    var pixels = imageData.data;
    
    // Convert to grayscale
    for (var i = 0; i < pixels.length; i += 4) {
        var greyScale = pixels[i + 0] * 0.3 + pixels[i + 1] * 0.59 + pixels[i + 2] * 0.11
        pixels[i + 0] = greyScale;
        pixels[i + 1] = greyScale;
        pixels[i + 2] = greyScale;
        pixels[i + 3] = pixels[i + 3];
    }
    
    // Set the new data of the pixels
    imageData.data = pixels;
};

/**
 * Calculates the integral image from the greyscale values
 * Returns array with the values from 1 color (black, white) so no 4 subpixels / pixel!
 */
FaceRecognition.prototype.calculateIntegralImageFromGreyscale = function(imageData) {    
    var integralImage = imageData;
    var pixels = integralImage.data;
    var width = integralImage.width;
    var height = integralImage.height;
    
    var pixel = [];
    
    // Create the pixel value arrays
    for (var i = 0; i < pixels.length; i += 4) {
        pixel.push(pixels[i + 0]);
    }
    
    // Convert the pixels to integral image
    pixel = this.calculateSummedAreaTableByArray(pixel, width, height);
    
     // Change the pixels
    for (var i = 0; i < pixels.length; i += 4) {
        integralImage[i + 0] = pixel[i / 4];
        integralImage[i + 1] = pixel[i / 4];
        integralImage[i + 2] = pixel[i / 4];
    }
    
    return integralImage;
};

/**
 * Calculate the summed area table by an array, the array is a 1D array, so 
 * only 1 row of numbers
 * 
 * This:
 * 5, 2, 3, 1, 5, 4, 2, 2, 1
 * 
 * Becomes:
 * 5, 2, 3
 * 1, 5, 4
 * 2, 2, 1
 * 
 * So formula becomes (i = currentValue):
 * 
 * var a = I(x - 1, y);
 * var b = I(x, y - 1);
 * var c = I(x - 1, y - 1);
 * 
 * I(x, y) = i(x, y) + I(x - 1, y) + I(x, y - 1), - I(x - 1, y - 1)
 * I(x, y) = i + a + b - c
 */
FaceRecognition.prototype.calculateSummedAreaTableByArray = function(array, width, height) {
    var result = [];
    
    for (var y = 0; y < height; y++) {
        for (var x = 0; x < width; x++) {
            if (!result[x]) {
                result.push([]);    
            }
//            var a = twoDArray[x - 1][y] || 0;
//            var b = twoDArray[x][y - 1] || 0;
//            var c = twoDArray[x - 1][y - 1] || 0;
            
            var a, c;
            if (!result[x - 1]) {
                a = 0;
                c = 0;
            } else {
                a = result[x - 1][y] || 0;
                c = result[x - 1][y - 1] || 0;
            }
            
            
            var b = result[x][y - 1] || 0;
            
            //console.log('i + a + b - c = ' + twoDArray[x][y] + ' + ' + a + ' + ' + b + ' + ' + c);
            
            var tempResult = array[width * y + x] + a + b - c;
            result[x][y] = tempResult;
        }
    }
    
    return this.convert2DArrayTo1D(result, width, height);
};

/**
 * 1D array to 2D:
 * We can find a element in a 1D array by doing:
 * array[width * y + x]
 */
FaceRecognition.prototype.convert1DArrayTo2D = function (array, width, height) {
    var twoDArray = [];
    
    for (var y = 0; y < height; y++) {
        for (var x = 0; x < width; x++) {
            if (!twoDArray[x]) {
                twoDArray.push([]);    
            }
            twoDArray[x][y] = array[width * y + x];
        }
    }
    
    return twoDArray;
};

/**
 * 2D Array to 1D
 */
FaceRecognition.prototype.convert2DArrayTo1D = function (array, width, height) {
    var oneDArray = [];
    
    for (var y = 0; y < height; y++) {
        for (var x = 0; x < width; x++) {
            oneDArray.push(array[x][y]);   
        }
    }
    
    return oneDArray;
};

FaceRecognition.prototype.drawImageDataToCanvas = function(imgData) {
    var canvas = document.createElement("canvas");
    var context = canvas.getContext('2d');
    canvas.setAttribute('height', imgData.height);
    canvas.setAttribute('width', imgData.width);
    context.putImageData(imgData, 0, 0);
    document.body.appendChild(canvas);
    
    return canvas;
};