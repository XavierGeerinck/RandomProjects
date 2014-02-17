var Fibonacci = function (startNumber) {
    this.startNumber = startNumber || 1;
    this.numbersGenerated = [];
    this.numbersToGenerate = 20;
};

Fibonacci.prototype.generateNumbers = function (n) {
    var number = n || this.startNumber;
    
    // Fibonacci: n = (n - 1) = (n - 2)
    var result = this.calculateResult(number);
    
    // Add to calculated array
    this.numbersGenerated.push(result);
    
    // Recursive loop when not reached the goal of numbersToGenerate
    if (this.numbersGenerated.length < this.numbersToGenerate) {
        this.generateNumbers(result);
    }
    
    // If goal reached, then print
    if (this.numbersGenerated.length >= this.numbersToGenerate) {
        var resultElement = document.querySelector("#result");
        resultElement.innerHTML = "Numbers: " + this.numbersGenerated.join(", ");
    }
};

Fibonacci.prototype.calculateResult = function (number) {
    // Get index
    var size = this.numbersGenerated.length;
    
    // Get sum of previous results
    var result = (this.numbersGenerated[size - 1] || 0) + (this.numbersGenerated[size - 2] || 0);
    
    if (result == undefined || result == 0) {
        result = 1;
    }
    
    return result;
};