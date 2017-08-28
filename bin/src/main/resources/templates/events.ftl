<html>
<head>
    <style type="text/css">
        table#balance td,
        table#balance th {
            padding: 0px 10px;
        }
    </style>
    <script type="application/javascript" src="/webjars/jquery/2.1.4/jquery.min.js">
    </script>
    <link rel="stylesheet" href="/webjars/bootstrap-css-only/3.3.4/css/bootstrap.min.css">
    <title>Exploring Axon</title>
</head>

<body>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">Explore CQRS With Axon Framework</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li><a href="/">Home</a></li>
                <li class="active"><a href="/events">View Events</a></li>
                <li><a href="/about">About</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container" style="margin-top: 70px">
    <div class="row">
        <div class="col-md-6">
            <ul>
            <#list events as event>
                <li>${event}</li>
            </#list>
            </ul>
        </div>
    </div>

</div>

</body>
<script type="text/javascript" src="/script.js"></script>
</html>