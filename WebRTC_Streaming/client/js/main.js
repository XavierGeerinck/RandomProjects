/**
 * 1. getUserMedia API to attach local media stream (Webcam / Microphone / Screenshare / ... )
 * 2. Create the Offer/Answer model to establish connection between users
 * 3. Use the ICE Server (STUN/TURN) to pass firewalls and NATs
 * 4. Use Signaling server to share offer/answer messages; or ice candidates among users
 */
var PeerConnection     = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
var SessionDescription = window.RTCSessionDescription || window.mozRTCSessionDescription || window.webkitRTCSessionDescription;
var GET_USER_MEDIA     = navigator.getUserMedia ? "getUserMedia" :
                         navigator.mozGetUserMedia ? "mozGetUserMedia" :
                         navigator.webkitGetUserMedia ? "webkitGetUserMedia" : "getUserMedia";

var isChrome = !!navigator.webkitGetUserMedia;

var STUN = {
    url: isChrome
       ? 'stun:stun.l.google.com:19302'
       : 'stun:23.21.150.121'
};

// var TURN = {
//     url: 'turn:homeo@turn.bistri.com:80',
//     credential: 'homeo'
// };

var iceServers = {
   iceServers: [STUN] // For turn server: iceServers: [STUN, TURN]
};

// DTLS/SRTP is preferred on chrome
// to interop with Firefox
// which supports them by default

var DtlsSrtpKeyAgreement = {
   DtlsSrtpKeyAgreement: true
};

var optional = {
   optional: [DtlsSrtpKeyAgreement]
};

// Create socket
var socket = new WebSocket('ws://projects.localhost.com/socket');
socket.onopen    = function() {};
socket.onmessage = function() { console.log('test'); };

/**
 * MAIN
 */// On Answer
socket.onmessage =  function(e) {
    var data = e.data;
    console.log(e.data);
    if(data.targetUser !== 'USERID' && data.offerSDP) {
        //createAnswer(offerSDP);
    }
};
