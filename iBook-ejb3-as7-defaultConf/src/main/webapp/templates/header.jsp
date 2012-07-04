<%@ page import="iBook.domain.User"%>
<%@ page import="iBook.web.LoginPage"%>
<%@ page import="iBook.web.BuyPage"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Book Store</title>
    <link href="css/templatemo_style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        function removePreDefText(obj) {
            obj.value = "";

        }

        function applyDefaultText(obj) {
            if (obj.value == "") {
                if (obj.name == "username") {
                    obj.value = "UserName";
                } else {
                    obj.value = "Password";
                }
            }
        }
    </script>
</head>
<body>
<div id="templatemo_container">
    <div id="templatemo_menu">
        <ul>
            <li><a href="index.jsp" class="current">Home</a></li>
            <li><a href="search.jsp">Search</a></li>
        </ul>
        <div>
            <%
                User user = (User) session.getAttribute(LoginPage.LOGGED_IN_USER);
                if (user != null) {
            %>
            <font size="2pt" style="font-weight: bold; float: right;padding-right: 70px;"
                  color="#E6E154">Welcome, <%=user.getUserName()%>! &nbsp;&nbsp;&nbsp;
                | &nbsp;&nbsp;<a href="card.jsp">My Wish List</a>
                | &nbsp;&nbsp;<a href="logout.jsp">Log Out</a>
            </font>
            <%
            } else {
            %>
            <a href="register.jsp">Register</a>
            <form method="post" name="loginForm" style="float: right;" action="index.jsp">
                <%
                    if (request.getParameter(LoginPage.PARAM_ERROR_MSG) != null) {
                %>
                <font size="2pt" color="red" style="font-weight: bold;">Please try again!</font>
                <%
                } else if(request.getAttribute(BuyPage.PARAM_ERROR_MSG) != null) {
                %>
                <font size="2pt" color="red" style="font-weight: bold;"><%=request.getAttribute(BuyPage.PARAM_ERROR_MSG) %></font>
                <%
                    }
                %>
                <input type="text" name="<%=LoginPage.PARAM_USER_NAME%>"
                       value="UserName" style="height: 15px; color: grey;" size="10"
                       onfocus="removePreDefText(this)" onblur="applyDefaultText(this)" />
                <input type="password" name="<%=LoginPage.PARAM_PASSWORD%>"
                       value="Password" value="" onfocus="removePreDefText(this)"
                       onblur="applyDefaultText(this)" style="height: 15px; color: grey;"
                       size="10" onkeypress="if(event.keyCode == 13) { document.loginForm.submit(); }"/>
                <input type="hidden" value="true" name="<%=LoginPage.PARAM_IS_SUBMITTED%>" />

                <div class="buy_now_button"
                     style="float: right; padding-left: 10px;">
                    <a href="javascript: document.loginForm.submit()">Login</a>
                </div>
            </form>
            <%
                }
            %>
        </div>
    </div>
    <!-- end of menu -->

    <div id="templatemo_header">
        <div id="templatemo_special_offers">
            <p>
                <span>25%</span> discounts for purchase over $80
            </p>
            <a href="subpage.jsp" style="margin-left: 50px;">Read more...</a>
        </div>


        <div id="templatemo_new_books">
            <ul>
                <li>Suspen disse</li>
                <li>Maece nas metus</li>
                <li>In sed risus ac feli</li>
            </ul>
            <a href="subpage.jsp" style="margin-left: 50px;">Read more...</a>
        </div>
    </div>
    <!-- end of header -->