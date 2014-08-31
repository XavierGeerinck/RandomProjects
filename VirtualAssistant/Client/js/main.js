// var recognition = new webkitSpeechRecognition();
// recognition.onresult = function(event) {
//     console.log(event);
// }
//
// recognition.start();
var socket = io("http://projects.localhost.com:8000");


socket.on('notification', function(data) {
    var msg = new SpeechSynthesisUtterance(data.msg);
    window.speechSynthesis.speak(msg);
});
