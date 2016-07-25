<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<head>
    <link rel="stylesheet" type="text/css" href="reset.css">
    <link rel="stylesheet" type="text/css" href="style.css">
    <link href='//fonts.googleapis.com/css?family=Crete+Round' rel='stylesheet' type='text/css'>
    <link href="//fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>FabFlix</title>

</head>

<body>

        <header>
                <div class="wrapper">
                <h1>FabFlix<span class="color">.</span></h1>
                <nav>
                        <ul>
                                <li><a href="login.html"><i class="material-icons">person</i>Login</a></li>
				<li><a href="Cart"><i class="material-icons">shopping_cart</i>Cart</a></li>
                                <li><a href="Search.jsp"><i class="material-icons">search</i>Search</a></li>
                                <li><a href="BrowseMain"><i class="material-icons">folder</i>Browse</a></li>
                        </ul>
                </nav>
                </div>

        </header>

        <div id="image">
		<h3>Checkout</h3>
	<FORM ACTION="<%=request.getContextPath()%>/checkout"
      	METHOD="POST" class="query">
  <center>
    <INPUT TYPE="TEXT" NAME="first_name" placeholder="First Name" align="center"><BR>
    <INPUT TYPE="TEXT" NAME="last_name" placeholder="Last Name" align="center"><BR>
    <INPUT TYPE="TEXT" NAME="cc" placeholder="Credit Card" align="center"><BR>
    <INPUT TYPE="TEXT" PLACEHOLDER="YYYY-MM-DD" NAME="exp" align="center"><BR>
    <INPUT TYPE="HIDDEN" NAME="error">
    <CENTER>
      <INPUT TYPE="SUBMIT" VALUE="Submit Payment Information">
    </CENTER>
  </center>
</FORM>
</BODY>
</HTML>
