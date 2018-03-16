<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<script>
    window.fbAsyncInit = function () {
        FB.init({
            appId: '1141179036026063',
            xfbml: true,
            version: 'v2.10'
        });
        FB.AppEvents.logPageView();
    };

    (function (d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) {
            return;
        }
        js = d.createElement(s);
        js.id = id;
        js.src = "//connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
</script>
<div
        class="fb-like"
        data-share="true"
        data-width="450"
        data-show-faces="true">
</div>

<a href="/fb/login">/fb/login</a>

<#--<a href='https://www.facebook.com/v2.10/dialog/oauth?client_id=1141179036026063&display=popup&response_type=code&redirect_uri=http://localhost:8080/good'>loginFB</a>-->
OK!
</body>
</html>