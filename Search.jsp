<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
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
                                <li><a href="login.html"><i class="material-icons">person</i>Logout</a></li>
                                <li><a href="Cart"><i class="material-icons">shopping_cart</i>Cart</a></li>
                                <li><a href="Search.jsp"><i class="material-icons">search</i>Search</a></li>
                                <li><a href="BrowseMain"><i class="material-icons">folder</i>Browse</a></li>
                        </ul>
                </nav>
                </div>

        </header>

        <div id="image">
	    
            <form method="get" action="<%=request.getContextPath()%>/Search" class="query">
		<center>
                             <input id="input" type="text" placeholder="title" name="title" align="center" onkeyup="ajaxFunction()"><br>
                             <div id="dropdown">
			     <select name="list" id="list" align="right"></select>
		             </div>
			     <input type="text" placeholder="year" name="year" align="center"><br>
        		     <input type="text" placeholder="director" name="director" align="center"><br>
			     <input type="text" placeholder="star first name" name="first" align="center"><br>	
			     <input type="text" placeholder="star last name" name="last" align="center"><br>
			    <INPUT TYPE="HIDDEN" NAME="order" VALUE="t_asc">
			    <INPUT TYPE="HIDDEN" NAME="ipp" VALUE="10">
			    <INPUT TYPE="HIDDEN" NAME="page" VALUE="1"> 
			    <CENTER>
 				 <INPUT TYPE="SUBMIT" VALUE="Submit Search">
			    </CENTER>
		</center>
            </form>
<script src='//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
        </div>
<script type="text/javascript">
<!--
//Browser Support Code
function ajaxFunction(){
	//alert("in!");
	var ajaxRequest;  // The variable that makes Ajax possible!

	try{
		// Opera 8.0+, Firefox, Safari
		ajaxRequest = new XMLHttpRequest();
	} catch (e){
		// Internet Explorer Browsers
		try{
			ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try{
				ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e){
				// Something went wrong
				alert("Your browser broke!");
				return false;
			}
		}
	}
	// Create a function that will receive data sent from the server
	ajaxRequest.onreadystatechange = function(){
		if(ajaxRequest.readyState == 4){
			
			var response= ajaxRequest.responseText;
			var resultlist=response.split(", ");
			add_option(resultlist);
			
		}
	}
	
	function add_option(resultlist){
	
		var dropdown=document.getElementById('dropdown');
		dropdown.style.display='block';
		var list=document.getElementById('list');
		list.size=5;
		
		var x;
		
		for(x in resultlist){
			
			var newoptions=document.createElement('option');
			
			list.appendChild(newoptions)
			newoptions.text=resultlist[x];
			
			newoptions.value=resultlist[x];
			list.add(newoptions);
		}
	}
	
	function setVal(select){
		document.getElementById('input').value=select.value;
		document.getElementById('dropdown').style.display='none';
	}
	var dropdown=document.getElementById('dropdown');
	dropdown.style.display='none';
	var list=document.getElementById('list');
	list.options.length=0;
	list.onclick=function(){
		setVal(this);
	}
	var input=encodeURIComponent(document.getElementById('input').value);
	
	var uri="http://localhost:8080/SearchText/Search/?input="+input;
	
	ajaxRequest.open("GET","http://52.40.71.34:8080/fabflixp4/DropDown?input="+input, true);
	ajaxRequest.send(null);
}

//-->
</script>
</body>
</html>
