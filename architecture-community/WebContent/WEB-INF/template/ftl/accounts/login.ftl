<#ftl encoding="UTF-8"/>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Expires" content="-1">
    <!-- Custom styles for this template -->
    <style>
 
	.form-signin {
	  max-width: 330px;
	  padding: 15px;
	  margin: 0 auto;
	}
	.form-signin .form-signin-heading,
	.form-signin .checkbox {
	  margin-bottom: 10px;
	}
	.form-signin .checkbox {
	  font-weight: normal;
	}
	.form-signin .form-control {
	  position: relative;
	  height: auto;
	  -webkit-box-sizing: border-box;
	          box-sizing: border-box;
	  padding: 10px;
	  font-size: 16px;
	}
	.form-signin .form-control:focus {
	  z-index: 2;
	}
	.form-signin input[type="email"] {
	  margin-bottom: -1px;
	  border-bottom-right-radius: 0;
	  border-bottom-left-radius: 0;
	}
	.form-signin input[type="password"] {
	  margin-bottom: 10px;
	  border-top-left-radius: 0;
	  border-top-right-radius: 0;
	}   
    
    </style>
</head>
<body>
	<div class="container">
      <form class="form-signin" method="post" action="/accounts/auth/login_check">
        <h2 class="form-signin-heading">로그인</h2>
        <label for="uername" class="sr-only">아이디</label>
        <input type="text" id="usrname" name="username" class="form-control" placeholder="아이디 입력" required autofocus>
        <label for="password" class="sr-only">비밀번호</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="비밀번호 입력" required>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> 로그인상태유지
          </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">로그인</button>
      </form>
    </div> <!-- /container -->
</body>
</html>