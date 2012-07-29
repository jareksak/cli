<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Central Library Index JSP Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<h1>The Central Library Index. Web interface.</h1>

<fieldset>
    <legend>Input a command:</legend>
    <form method="post" action="send">
        <table>
            <tr>
                <td>
                    <input type="text" size="50" name="expression">
                </td>
                <td>
                    <input type="submit" name="Run" value="  Run  ">
                </td>
            </tr>
        </table>
    </form>
</fieldset>

<fieldset>
    <legend>User input:</legend>
        <table border="0" width="100%">
            <tr>
                <td>
                    ${requestScope.expression}
                </td>
            </tr>
        </table>
</fieldset>

<fieldset>
    <legend>Results:</legend>
        <table border="0" width="100%">
            <tr>
                <td>
                    <pre>${requestScope.message}</pre>
                </td>
            </tr>
        </table>
</fieldset>

</body>
</html>
