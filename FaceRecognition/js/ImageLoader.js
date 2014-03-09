var ImageLoader = function (path, count, mask, extension, listId) {
    this.facesPath = path;
    this.numberOfFaces = count || 0;
    this.imageExtension = extension;
    this.imageNameMask = mask || "";
    this.faces = [];
    this.listElement = document.querySelector("#" + listId);
};

ImageLoader.prototype.load = function () {
    var self = this;
    
    // Load the faces from the given path, with the given mask into this.faces
    for (var i = 0; i < this.numberOfFaces; i++) {
        var image = new Image();
        var fileName = this.applyMask(i);
        image.src = this.facesPath + '/' + fileName + '.' + this.imageExtension;
        image.onload = function () {
            self.faces.push(this);   
            
            // Add them to the loaded list
            self.addListItem(this);  
        }
    }
};

ImageLoader.prototype.addListItem = function(image) {
    var fileName = image.src.substring(image.src.lastIndexOf('/') + 1);
    var fileName = fileName.substring(0, fileName.length - 4); // Remove .jpg
    
    var listItem = document.createElement('li');
    var canvas = document.createElement('canvas');
    canvas.setAttribute('id', fileName);
    canvas.setAttribute('height', image.height);
    canvas.setAttribute('width', image.width);
    
    listItem.appendChild(canvas);
    document.querySelector("#faces").appendChild(listItem);
    
    var context = canvas.getContext('2d');
    context.drawImage(image, 0, 0);
};

ImageLoader.prototype.applyMask = function(number) {
    var result = this.imageNameMask.substr(0, this.imageNameMask.length - number.toString().length);
    result += number.toString();
    
    return result;
};

ImageLoader.prototype.log = function(msg) {
    var time = new Date();
    console.log('[' +  + ']');
};