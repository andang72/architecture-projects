<#ftl encoding="UTF-8"/>
<#assign user = SecurityHelper.getUser() />	
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title> </title>
  </head>
  <body>
    <div class="collapse bg-inverse" id="navbarHeader">
      <div class="container">
        <div class="row">
          <div class="col-sm-8 py-4">
            <h4 class="text-white">About</h4>
            <p class="text-muted"><small>${SecurityHelper.getAuthentication() }</small></p>
          </div>
          <div class="col-sm-4 py-4">      
          	 <#if !user.anonymous >
          	 	<p class="text-muted">${user.name} </p>
          	 </#if>
            <ul class="list-unstyled">
              <#if user.anonymous >
              <li><a href="<@spring.url "/accounts/login"/>" class="text-white">로그인</a></li>	
              <#else>
              <li><a href="<@spring.url "/accounts/logout"/>" class="text-white">로그아웃</a></li>
              </#if>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="navbar navbar-inverse bg-inverse">
      <div class="container d-flex justify-content-between">
        <a href="#" class="navbar-brand">MUSI COMMUNITY</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      </div>
    </div>

    <section class="jumbotron text-center">
      <div class="container">
        <h1 class="jumbotron-heading">Community example</h1>
        <p class="lead text-muted">
        Something short and leading about the collection below—its contents, 
        the creator, etc. Make it short and sweet, but not too short so folks don't simply skip over it entirely.
        </p>
        <p>
          <a href="#" class="btn btn-primary">Main call to action</a>
          <a href="#" class="btn btn-secondary">Secondary action</a>
        </p>
      </div>
    </section>

    
    <footer class="text-muted">
      <div class="container">
        <p class="float-right">
          <a href="#">Back to top</a>
        </p>
        <p>Album example is &copy; Bootstrap, but please download and customize it for yourself!</p>
        <p>New to Bootstrap? <a href="../../">Visit the homepage</a> or read our <a href="../../getting-started/">getting started guide</a>.</p>
      </div>
    </footer>
  </body>
</html>
