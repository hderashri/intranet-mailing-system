(function () {
	var filesUpload = document.getElementById("files-upload"),
		dropArea = document.getElementById("drop-area"),
		fileList = document.getElementById("file-list"),
		clearCompose = document.getElementById("clearCompose"),
		compose=document.getElementById("submitForm");
	 var xhrs=[];	
	  var fileIdList = [];	
	  function uploadFile (file,row) {
			var div1 = document.createElement("div"),
			div2 = document.createElement("div"),
			img,
			progressBarContainer = document.createElement("div"),
			progressBar = document.createElement("div"),
			closebutton,
			reader,
			xhr,
			fileInfo;
		    div1.className+="col-xs-6";
		row.appendChild(div1);	
		
		/*
			If the file is an image and the web browser supports FileReader,
			present a preview in the file list
		*/
		if (typeof FileReader !== "undefined" && (/image/i).test(file.type)) {
			img = document.createElement("img");
			div1.appendChild(img);
			img.className = "image-files";
			reader = new FileReader();
			reader.onload = (function (theImg) {
				return function (evt) {
					theImg.src = evt.target.result;
				};
			}(img));
			reader.readAsDataURL(file);
		}
		
		
		
		
		
		
		
		closebutton = document.createElement("i");
		closebutton.className = "fa fa-trash";
		closebutton.className += " filediscardbutton";
		closebutton.addEventListener("click", removefunction);
		div1.appendChild(closebutton);
		
		
		
		
		progressBarContainer.className = "progress-bar-container";
		progressBar.className = "progress-bar";
		progressBarContainer.appendChild(progressBar);
		div1.appendChild(progressBarContainer);
		
		div1.appendChild(div2);
		// Uploading - for Firefox, Google Chrome and Safari
		xhr = new XMLHttpRequest();
		
		// Update progress bar
		xhr.upload.addEventListener("progress", function (evt) {
			if (evt.lengthComputable) {
				progressBar.style.width = (evt.loaded / evt.total) * 100 + "%";
			}
			else {
				// No data to calculate on
			}
		}, false);
		 var fileId=null;
		// File uploaded
		xhr.addEventListener("load", function () {
			 progressBarContainer.className += " uploaded";
			 progressBar.innerHTML = "Uploaded!";
			 fileId=xhr.getResponseHeader("fileId");
             fileIdList.push(fileId);
             closebutton.setAttribute("id",fileId);				 
		}, false);
		
		xhr.open("post", "uploadFile.do", true);
		xhrs.push(xhr);
		var formData = new FormData();
		formData.append("myFile", file);
		xhr.send(formData);
		
		// Present file info and append it to the list of files
		fileInfo = "<div><strong>Name:</strong> " + file.name + "</div>";
		fileInfo += "<div><strong>Size:</strong> " + parseInt(file.size / 1024, 10) + " kb</div>";
		fileInfo += "<div><strong>Type:</strong> " + file.type + "</div>";
		div2.innerHTML = fileInfo;
	}
	
	function traverseFiles (files) {
		if (typeof files !== "undefined") {
			for (var i=0, l=files.length; i<l; i++) {
				var row;
				if(i%2==0){
					row=document.createElement("div");
					row.className += "row";
					fileList.appendChild(row);
				}	
					
				uploadFile(files[i],row);
			}
		}
		else {
			fileList.innerHTML = "No support for the File API in this web browser";
		}	
	}
	
	filesUpload.addEventListener("change", function () {
		traverseFiles(this.files);
	}, false);
	
	dropArea.addEventListener("dragleave", function (evt) {
		var target = evt.target;
		
		if (target && target === dropArea) {
			this.className = "";
		}
		evt.preventDefault();
		evt.stopPropagation();
	}, false);
	
	dropArea.addEventListener("dragenter", function (evt) {
		this.className = "over";
		evt.preventDefault();
		evt.stopPropagation();
	}, false);
	
	dropArea.addEventListener("dragover", function (evt) {
		evt.preventDefault();
		evt.stopPropagation();
	}, false);
	
	dropArea.addEventListener("drop", function (evt) {
		traverseFiles(evt.dataTransfer.files);
		this.className = "";
		evt.preventDefault();
		evt.stopPropagation();
	}, false);										

  function addHiddenField(props) {
  Object.keys(props).forEach(fieldName => {
    var field = compose[fieldName];
    if (!field) {
      field = document.createElement('input');
      field.type = 'hidden';
      field.name = fieldName;
	  field.value = props[fieldName];
      compose.appendChild(field);
    }
  });
}

  compose.addEventListener('submit', () => {
  addHiddenField({
   "fileidList": fileIdList.join()
  });
});


clearCompose.addEventListener("click", function() {
  compose.reset();
  fileList.innerHTML="";
  for (var i=0, l=xhrs.length; i<l; i++) {
	  xhrs[i].abort();
	}
  if(fileIdList.length!==0)
  {
	  var xhr1 = new XMLHttpRequest();
	  xhr1.open("post", "removeFiles.do", true);
	  xhr1.setRequestHeader("fileIdList", fileIdList.join());
	  xhr1.send();
  }
});

function removefunction() {
			var id = $(this).attr('id');
			if(id==null)
				alert("it can be deleted only after it is uploaded");
			else{
				var xhr = new XMLHttpRequest();
				xhr.open("post", "removeFile.do", true);
				xhr.setRequestHeader("fileId", id);
				xhr.send();
				this.parentElement.remove();
				const index = fileIdList.indexOf(id);
					if (index > -1) {
					  fileIdList.splice(index, 1);
					}
			}	
				
	}
  $(".inboxLink").click(function(e){
	  e.preventDefault();
	 $.get("Inbox1.jsp",
      {},
     function(data){
        $('.lg-side').html(data);
       }
         );
   });
  $(".trashLink").click(function(e){
	  e.preventDefault();
	 $.get("trash.jsp",
      {},
     function(data){
        $('.lg-side').html(data);
       }
         );
   });
   $(".readMailLink").click(function(e){
	  e.preventDefault();
	 $.get("readmails.jsp",
      {},
     function(data){
        $('.lg-side').html(data);
       }
         );
   });

})();
