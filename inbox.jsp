

<html lang="en">
<head>


    <meta charset="utf-8">
    <title>Inbox</title>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
	<meta content="width=device-width, initial-scale=1" name="viewport" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><meta name="HandheldFriendly" content="true">
	
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" >
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <link href="utils/fontawesome-free-5.14.0-web/css/all.css" rel="stylesheet"> 
	<link rel="stylesheet" type="text/css" href="utils/ReadMail.css">

</head>
<body>
                <div class="mail-box"> 
						<jsp:include page="SideBar.jsp" />
				     <div class="lg-side">		
						<jsp:include page="Inbox1.jsp" />
					 </div>
				</div>	 
				<!--mail inbox end-->	
	
	
	
	
	
	
	
	
<script type="text/javascript">
      var sidebar = $(".sm-side");
      var hamburger = $('#navTrigger');

       hamburger.click(function(e) {
      e.preventDefault();
       $(this).toggleClass('is-active');
     // This will add `sidebar-opened`
       $('.lg-side').toggleClass("sidebar-opened");
      // Remove magin left
      sidebar.toggleClass('ml-0');
      });
	  
	 
  function readMail(clickedMail) {
	 $.post("ReadMail.jsp",
      { mailId: clickedMail},
     function(data){
        $('.lg-side').html(data);
       }
         );
   }
   
  
   

</script>
</body>
</html>