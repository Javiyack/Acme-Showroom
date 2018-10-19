// Get the Sidebar
	var mySidebar = document.getElementById("mySidebar");

	// Get the DIV with overlay effect
	var overlayBg = document.getElementById("myOverlay");

	// Toggle between showing and hiding the sidebar, and add overlay effect
	function w3_open() {
		if (mySidebar.style.display === 'block') {
			mySidebar.style.display = 'none';
			overlayBg.style.display = "none";
		} else {
			mySidebar.style.display = 'block';
			overlayBg.style.display = "block";
		}
	}

	// Close the sidebar with the close button
	function w3_close() {
		mySidebar.style.display = "none";
		overlayBg.style.display = "none";
	}

	// Script for Tabs, Accordions, Progress bars and slideshows -->

	// Tabs
	function openCity(evt, cityName) {
		var i;
		var x = document.getElementsByClassName("city");
		for (i = 0; i < x.length; i++) {
			x[i].style.display = "none";
		}
		var activebtn = document.getElementsByClassName("testbtn");
		for (i = 0; i < x.length; i++) {
			activebtn[i].className = activebtn[i].className.replace(
					" w3-dark-grey", "");
		}
		document.getElementById(cityName).style.display = "block";
		evt.currentTarget.className += " w3-dark-grey";
	}

	var mybtn = document.getElementsByClassName("testbtn")[0];
	mybtn.click();

	// Accordions
	function myAccFunc(id) {
		var x = document.getElementById(id);
		if (x.className.indexOf("w3-show") == -1) {
			x.className += " w3-show";
		} else {
			x.className = x.className.replace(" w3-show", "");
		}
	}

	// Slideshows
	var slideIndex = 1;

	function plusDivs(n) {
		slideIndex = slideIndex + n;
		showDivs(slideIndex);
	}

	function showDivs(n) {
		var x = document.getElementsByClassName("mySlides");
		if (n > x.length) {
			slideIndex = 1
		}
		if (n < 1) {
			slideIndex = x.length
		}
		;
		for (i = 0; i < x.length; i++) {
			x[i].style.display = "none";
		}
		x[slideIndex - 1].style.display = "block";
	}

	showDivs(1);

	// Progress Bars
	function move() {
		var elem = document.getElementById("myBar");
		var width = 5;
		var id = setInterval(frame, 10);
		function frame() {
			if (width == 100) {
				clearInterval(id);
			} else {
				width++;
				elem.style.width = width + '%';
				elem.innerHTML = width * 1 + '%';
			}
		}
	}