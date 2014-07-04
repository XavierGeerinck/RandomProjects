var answerer = new PeerConnection('http://projects.localhost.com', 'message', 'answerer');

answerer.onStreamAdded = function(e) {
    console.log("STREAM RECEIVED");
    document.getElementById("video-stream").src = e.mediaElement.src;
};

answerer.sendParticipationRequest('offerer');
