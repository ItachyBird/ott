<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String movie = request.getParameter("movie"); %>
<% String title = request.getParameter("title"); %>
<jsp:include page="index_header.jsp" />
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&family=Sen:wght@400;700;800&display=swap" rel="stylesheet">
    <!-- Your other CSS files -->
    <link rel="stylesheet" href="/css/indexstyle.css">
<!DOCTYPE html>
<html>
<head>
    <style>
        .video-container {
            position: relative;
            width: 640px;
            height: 360px;
        }

        .custom-controls {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            background-color: rgba(0, 0, 0, 0.5);
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 5px;
        }

        .custom-controls button {
            background: none;
            border: none;
            color: white;
            cursor: pointer;
        }

        #volumeControl {
            width: 100px;
        }

        #progressContainer {
            flex-grow: 1;
            height: 10px;
            margin: 0 10px;
            background-color: rgba(255, 255, 255, 0.3);
            cursor: pointer;
            position: relative;
        }

        #progressBar {
            height: 100%;
            background-color: white;
            width: 0;
        }

        .play-overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 2;
        }

        .play-overlay button {
            font-size: 3em;
            color: white;
            background: none;
            border: none;
            cursor: pointer;
        }
    </style>
    <title>Watch <%=title %></title>
</head>
<body>
<div style="margin-top: 100px;"> <!-- Moves the element 100 pixels below -->
    <!-- Your HTML content here -->
</div>
<div class="video-container">
    <!-- Overlay for play button -->
    <div class="play-overlay" id="playOverlay">
        <button id="bigPlayButton"><span>▶</span></button>
    </div>

    <video id="myVideo" width="640" height="360">
        <source src="<%= "videos/" + movie%>" type="video/mp4">
        Your browser does not support the video tag.
    </video>

    <div class="custom-controls">
        <button id="playPauseButton"><span id="playPauseIcon">❚❚</span></button>
        <button id="volumeButton"><i id="volumeIcon" class="fas fa-volume-up"></i></button>
        <input id="volumeControl" type="range" min="0" max="1" step="0.01" value="1">
        <div id="progressContainer">
            <div id="progressBar"></div>
        </div>
        <button id="fullscreenButton"><i class="fas fa-expand"></i></button>
        <a href="index"><i class="fas fa-film"></i></a>
    </div>
</div>

<script>
    var video = document.getElementById("myVideo");
    var playOverlay = document.getElementById("playOverlay");
    var bigPlayButton = document.getElementById("bigPlayButton");
    var playPauseButton = document.getElementById("playPauseButton");
    var volumeControl = document.getElementById("volumeControl");
    var fullscreenButton = document.getElementById("fullscreenButton");
    var progressBar = document.getElementById("progressBar");
    var progressContainer = document.getElementById("progressContainer");
    var volumeIcon = document.getElementById("volumeIcon");

    bigPlayButton.addEventListener("click", function() {
        video.play();
        playOverlay.style.display = "none"; // Hide the play overlay
    });

    playPauseButton.addEventListener("click", function() {
        if (video.paused) {
            video.play();
            playPauseIcon.textContent = "❚❚";
        } else {
            video.pause();
            playPauseIcon.textContent = "▶";
        }
    });

    volumeControl.addEventListener("input", function() {
        video.volume = volumeControl.value;
        updateVolumeIcon(volumeControl.value);
    });

    fullscreenButton.addEventListener("click", function() {
        if (video.requestFullscreen) {
            video.requestFullscreen();
        } else if (video.webkitRequestFullscreen) { /* Safari */
            video.webkitRequestFullscreen();
        } else if (video.msRequestFullscreen) { /* IE11 */
            video.msRequestFullscreen();
        }
    });

    volumeButton.addEventListener("click", function() {
        video.muted = !video.muted;
        updateVolumeIcon(video.muted ? 0 : volumeControl.value);
    });

    video.addEventListener("timeupdate", function() {
        var progress = (video.currentTime / video.duration) * 100;
        progressBar.style.width = progress + "%";
    });

    progressContainer.addEventListener("click", function(event) {
        var progressWidth = this.offsetWidth;
        var clickX = event.offsetX;
        var seekTime = (clickX / progressWidth) * video.duration;
        video.currentTime = seekTime;
    });

    function updateVolumeIcon(volume) {
        if (volume === 0 || video.muted) {
            volumeIcon.className = "fas fa-volume-off";
        } else if (volume < 0.5) {
            volumeIcon.className = "fas fa-volume-down";
        } else {
            volumeIcon.className = "fas fa-volume-up";
        }
    }
</script>

<footer>
    <jsp:include page="index_footer.jsp" />
</footer>
</body>
</html>
