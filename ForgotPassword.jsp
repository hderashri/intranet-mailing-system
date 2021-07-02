<%@page language="java" contentType="text/html"%>
<html>
<head>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<style>
    .note
		{
			text-align: center;
			height: 80px;
			background: -webkit-linear-gradient(left, #0072ff, #8811c5);
			color: #fff;
			font-weight: bold;
			line-height: 80px;
		}
		.form-content
			{
				padding: 5%;
				border: 1px solid #ced4da;
				margin-bottom: 2%;
			}
		.form-control{
				border-radius:1.5rem;
			}
		.btnSubmit
			{
				border:none;
				border-radius:1.5rem;
				padding: 1%;
				width: 20%;
				cursor: pointer;
				background: #0062cc;
				color: #fff;
			}
</style>

<body>

<div class="container register-form">
            <div class="form">
                <div class="note">
                    <p>Intranet</p>
                </div>
             <center><span style="color:red;padding-bottom:5px;">
						${requestScope.message}
					</span></center>
                <div class="form-content">
				<form method="post" action="forgotPassword.do">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Your User Name*" name="userName"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Phone Number *" value="" name="phone"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Your Password *" value="" name="password"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Confirm Password *" value="" name="confirmPassword"/>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btnSubmit">Submit</button>
				</form>	
                </div>
            </div>
        </div>
</body>
</html>		