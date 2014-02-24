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
    var context = canvas.getContext('2d');
    context.drawImage(picture, 0, 0);
    console.log(picture.width);
    console.log(picture.height);
    return context.getImageData(0, 0, picture.width, picture.height);
};

/**
 * 
 */
FaceRecognition.prototype.calculateIntegralImage = function(picture) {
    var imageData = this.getImageData(picture);
    //this.drawImageDataToCanvas(greyScale);
    
    var pixels = imageData.data;
    imageData.data = this.calculateIntegralImageFromRGBA(pixels, picture.width, picture.height);
    
    this.drawImageDataToCanvas(imageData);
};

/**
 * Convert the picture to grey scale
 * algorithm: (red + green + blue) / 3
 */
FaceRecognition.prototype.RGBA2Greyscale = function (r, g, b, a) {
    return (r + g + b) / 3;
};

FaceRecognition.prototype.calculateIntegralImageFromRGBA = function(pixels, width, height) {    
    var red = [];
    var green = [];
    var blue = [];
    var alpha = [];
    
    // Create the pixel value arrays
    for (var i = 0; i < pixels.length; i += 4) {
        red.push(pixels[i + 0])
        green.push(pixels[i + 1])
        blue.push(pixels[i + 2])
        alpha.push(pixels[i + 3])
    }
    
    this.calculateSummedAreaTableByArray(red);
    
    return pixels;
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
    
    return result;
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
    canvas.setAttribute('height', 512);
    canvas.setAttribute('width', 512);
    context.putImageData(imgData, 0, 0);
    document.body.appendChild(canvas);
};