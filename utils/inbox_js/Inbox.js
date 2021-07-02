

       function deleteMail(){

            var mailIdToDelete = [];
			alert("delete clicked");

            $.each($("input[name='mailId']:checked"), function(){

                mailIdToDelete.push($(this).val());
				this.parentElement.parentElement.remove();

            });
			var xhr = new XMLHttpRequest();
				xhr.open("post", "deleteMail.do", true);
				xhr.setRequestHeader("mailIdList", mailIdToDelete.join());
				xhr.send();

            

        }
