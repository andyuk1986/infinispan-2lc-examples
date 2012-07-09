<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.jeejl.data.Document" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jeejl.web.SearchResultPage" %>
<jsp:useBean id="searchResult" class="com.jeejl.web.SearchResultPage" scope="page"/>

<%
    searchResult.init(request, response);
    List<Document> sortedResult = searchResult.processSearchQuery();
%>

<head>
    <title>Jeejl</title>
    <link type="text/css" rel="stylesheet" href="css/css.css">
</head>
<body>
<div id="resultBlock">
    <table border="0" width="70%">
        <tr>
            <td align="center" valign="middle">
                <strong><font color="blue" size="6px">JEEJL</font></strong>
            </td>
            <td valign="middle">
                <form method="POST" action="result.jsp" style="margin:5px;">
                    <input type="text" name="query" id="query" size="80px" value="<%=(searchResult.getParameter(SearchResultPage.QUERY_PARAM) != null ? searchResult.getParameter(SearchResultPage.QUERY_PARAM) : "")%>"/>
                    <input type="submit" value="Search"/>
                </form>
            </td>
        </tr>
        <%
            if (sortedResult.isEmpty()) {
        %>
        <tr>
            <td></td>
            <td>
                Sorry, no results are found. Please try another search.
            </td>
        </tr>
        <%
        } else {
            for (Document doc : sortedResult) {
        %>
        <tr>
            <td></td>
            <td>
                <div style="padding:10px;"><strong><a href="<%=doc.getUrl()%>" target="_blank"><%=doc.getTitle() != null ? doc.getTitle() : ""%>
                </a></strong><br/>
                    <%=doc.getUrl()%>
                </div>
                <hr/>
            </td>
        </tr>
        <%
                }
            }
        %>

    </table>
</div>
</body>
</html>