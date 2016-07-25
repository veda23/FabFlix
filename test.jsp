<%
if(request.getParameter("i")!=null){
out.println(request.getParameter("i"));
out.println("<img src='img?i="+request.getParameter("i")+"'>");
}
%>
