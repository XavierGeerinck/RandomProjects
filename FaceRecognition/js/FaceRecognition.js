var FaceRecognition = function () {
    this.faces = [];
};

FaceRecognition.prototype.start = function(faces) {
    this.faces = faces;
    
    for (var i in faces) {
        var integralImage = this.calculateIntegralImage(faces[i]);
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
 * 
 */
FaceRecognition.prototype.calculateIntegralImage = function(picture) {
    // get Image data
    var imageData = this.getImageData(picture);
    this.drawImageDataToCanvas(imageData);
    
    // Convert to greyscale
//    imageData = this.RGBA2Greyscale(imageData);
//    this.drawImageDataToCanvas(imageData);
//    
//    // Calculate the Integral Image array
//    imageData = this.calculateIntegralImageFromGreyscale(imageData);
//    
//    this.drawImageDataToCanvas(imageData);
    
    
    //var pixels = imageData.data;
    //imageData.data = this.calculateIntegralImageFromRGBA(pixels, picture.width, picture.height);
    
    //this.drawImageDataToCanvas(imageData);
};

/**
 * Convert the picture to grey scale
 * algorithm: Math.round((red + green + blue) / 3)
 */
FaceRecognition.prototype.RGBA2Greyscale = function (imageData) {
    var pixels = imageData.data;
    
    // Convert to grayscale
    for (var i = 0; i < pixels.length; i += 4) {
        var greyScale = Math.round((pixels[i + 0] + pixels[i + 1] + pixels[i + 2]) / 3);
        pixels[i + 0] = greyScale;
        pixels[i + 1] = greyScale;
        pixels[i + 2] = greyScale;
        pixels[i + 3] = pixels[i + 3];
    }
    
    // Set the new data of the pixels
    imageData.data = pixels;
    
    return imageData;
};

/**
 * Calculates the integral image from the greyscale values, returns 1 pixel  (the red pixel array convert to integral image)
 */
FaceRecognition.prototype.calculateIntegralImageFromGreyscale = function(imageData) {    
    var pixels = imageData.data;
    var width = imageData.width;
    var height = imageData.height;
    
    var pixel = [];
    
    // Create the pixel value arrays
    for (var i = 0; i < pixels.length; i += 4) {
        pixel.push(pixels[i + 0]);
    }
    
    // Convert the pixels to integral image
    pixel = this.calculateSummedAreaTableByArray(pixel, width, height);
    
     // Change the pixels
    var j = 0;
    for (var i = 0; i < pixels.length; i += 4) {
        pixels[i + 0] = pixel[i / 4];
        pixels[i + 1] = pixel[i / 4];
        pixels[i + 2] = pixel[i / 4];
        
        j++;
    }
    
    console.log(pixel);
    console.log(pixels);
    imageData.data = pixels;
    
    return imageData;
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
};