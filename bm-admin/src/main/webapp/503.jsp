<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>Service Unavailable - XinyinTech</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="description" content="" />
        <meta name="author" content="stilearning" />

        <!-- google font -->
        <link href="<%=request.getContextPath()%>/stilearn2.0/styles/aclonica-regular.css" rel="stylesheet" />
        
        <!-- styles -->
        <link href="<%=request.getContextPath()%>/stilearn2.0/styles/bootstrap.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/stilearn2.0/styles/bootstrap-responsive.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/stilearn2.0/styles/stilearn1.0/stilearn.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/stilearn2.0/styles/stilearn-responsive.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/stilearn2.0/styles/stilearn-helper.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/stilearn2.0/styles/stilearn-icon.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/stilearn2.0/styles/font-awesome.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/stilearn2.0/styles/animate.css" rel="stylesheet" />
        
        <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>

    <body>
        <!-- section header -->
        <header class="header" data-spy="affix" data-offset-top="0">
            <!--nav bar helper-->
            <div class="navbar-helper">
                <div class="row-fluid">
                    <!--panel site-name-->
                    <div class="span2">
                        <div class="panel-sitename">
                            <h2><a href="<%=request.getContextPath()%>/index.html"><span class="color-teal">Xinyin</span>Tech</a></h2>
                        </div>
                    </div>
                    <!--/panel name-->
                </div>
            </div><!--/nav bar helper-->
        </header>
        
        <!-- section content -->
        <section class="section">
            <div class="container">
                <div class="error-page">
                    <h1 class="error-code color-red">Error 503</h1>
                    <p class="error-description muted">The server is currently unavailable (overloaded or down)</p>
                    <form >
                        <div class="control-group">
                            <div class="input-append input-icon-prepend">
                                <div class="add-on">
                                    <a title="search" style="" class="icon"><i class="icofont-search"></i></a>
                                    <input class="input-xxlarge animated grd-white" type="text" placeholder="what you looking for?" />
                                </div>
                                <input type="submit" class="btn" value="Search" />
                            </div>
                        </div>
                    </form>
                    <a href="<%=request.getContextPath()%>/index.html" class="btn btn-small btn-primary"><i class="icofont-arrow-left"></i> Back to Dashboard</a>
                    <a href="<%=request.getContextPath()%>/index.html" target="_blank" class="btn btn-small btn-success">Back to Site <i class="icofont-arrow-right"></i></a>
                </div>
            </div>
        </section>

        <!-- section footer -->
        <footer>
            <a rel="to-top" href="#top"><i class="icofont-circle-arrow-up"></i></a>
        </footer>

        <!-- javascript
        ================================================== -->
        <script src="<%=request.getContextPath()%>/stilearn2.0/scripts/custom/jquery.js"></script>
        <script src="<%=request.getContextPath()%>/stilearn2.0/scripts/custom/bootstrap.js"></script>
        <script src="<%=request.getContextPath()%>/stilearn2.0/scripts/custom/jquery.ui.widget.js"></script>
        
        <script type="text/javascript">
            $(document).ready(function() {
                // try your js
                
            })
        </script>
    </body>
</html>
