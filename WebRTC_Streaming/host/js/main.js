var startButton = document.getElementById("start");
startButton.onclick = function() {
    this.disabled = true;

    var offerer = new PeerConnection('http://projects.localhost.com', 'message', 'offerer');
    navigator.getUserMedia(null, function(stream) {
        offerer.addStream(stream);
    }, function(err) {
        console.log(err);
    });

    offerer.onStreamAdded = function(e) {
        document.getElementById("video-stream").src = e.mediaElement.src;
    };
};
