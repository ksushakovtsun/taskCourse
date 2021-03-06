<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>SPD</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
    <style>
        #lblError{margin-top: -2px; color: red; margin-left: 125px; font-size: 21px;}
        body{background-color: #c5ddff;background-image: url("static/images/i1.gif");}
        #panel{width: 99.7%;height: 47px;background: #fff5b2;border-style: groove;}
        #btnLogOut:hover{background-color: #3c4a90;color: white;}
        #btnLogOut{
            color: black;padding: 3px 14px;text-align: center;
            text-decoration: none;display: inline-block;
            font-size: 16px;border: 2px solid #3c4a90;
            background-color: #ffffff;cursor: pointer;}
        #btnLogOut{margin-left: 1254px;margin-top: 9px;}
        #listUsers{color: #221c70;font-size: 21px;margin-left: 77px;}
        #tblU{margin-left: 446px;}
        #tblUI{margin-top: 14px;}
        #tblUM{margin-bottom:16px;margin-top: 16px;
            border-style: groove;background: white;
            width: 435px;margin-left: 15px;}
        #btnAdd, #btnDel, #btnDelA{
            color: black;padding: 3px 14px;text-align: center;
            text-decoration: none;display: inline-block;
            font-size: 16px;border: 2px solid #3c4a90;
            background-color: #ffffff;cursor: pointer;}
        #btnAdd:hover, #btnDel:hover, #btnDelA:hover{
            background-color: #3c4a90;color: white;}
        .lblT{margin-left:3px;cursor: pointer;border: 2px solid #9a9a9a;font-size: 15px;width: 145px;}
        .lblT:hover{border-color:  #221c70;}
        #tblUModer{width: 92%;margin-left: 18px;border-color: #ece5e5;
            margin-top: 16px;margin-bottom: 5px;
            background: #fff5b2;border-style: groove;}
        #btnDel{margin-left: 145px; margin-top: 5px; margin-bottom: 5px;}
        #btnDelA{margin-left: -51px;}
        #btnAdd{margin-top: 4px; margin-left: 216px;}
        #lblN{margin-left: 60px;}
        #lblP{margin-left: 63px;}
        #lblS{margin-left: 85px;}
        #btnDel:disabled, #btnDelA:disabled{border-color: #3c4a90; color: rgba(127, 117, 117, 0.55)}
        .thUI, .tdUI {text-align: left; padding: 3px;}
        .trUI:nth-child(even){background-color: #f2f2f2}
        .trUI:hover{color: #3c4a90;}
    </style>
    <script>
        $(function() {
            $("#btnDel").attr("disabled", true);
            $("#btnDelA").attr("disabled", true);
            $(".rdBtnM").click(function () {
                $("#btnDel").attr("disabled", false);
                $("#btnDelA").attr("disabled", true);
            });
            $(".rdBtnA").click(function () {
                $("#btnDel").attr("disabled", true);
                $("#btnDelA").attr("disabled", false);
            });
        });
    </script>
</head>
<body>
<form action="<c:url value="/mainadmin"/>" method="POST">
    <div id="panel">
        <input id="btnLogOut" type="submit" name="logOut" value="Log Out"/>
    </div>
    <div id="tblU">
        <div id="tblUM">
            <b id="listUsers">List of moderators and admins:</b>
            <table id="tblUI">
                <tr class="trUI">
                    <th>&nbsp;</th>
                    <th class="title">Username</th>
                    <th class="title">Password</th>
                    <th class="title">Status</th>
                </tr>
                <c:forEach var="moderator" items="${moderators}">
                    <tr class="trUI">
                        <td class="tdUI"><input class="rdBtnM" type="radio" name="id" value="${moderator.id}"></td>
                        <td class="tdUI"><c:out value="${moderator.username}"/></td>
                        <td class="tdUI"><c:out value="${moderator.password}"/></td>
                        <td class="tdUI"><c:out value="${moderator.status}"/></td>
                    </tr>
                </c:forEach>
                <c:forEach var="admin" items="${admins}">
                    <tr class="trUI">
                        <td class="tdUI"><input class="rdBtnA" type="radio" name="id" value="${admin.id}"></td>
                        <td class="tdUI"><c:out value="${admin.username}"/></td>
                        <td class="tdUI"><c:out value="${admin.password}"/></td>
                        <td class="tdUI"><c:out value="${admin.status}"/></td>
                    </tr>
                </c:forEach>
            </table>
            <table id="tblUModer">
                <tr>
                    <td><input id="btnDel" type="submit" value="Delete Mod" name="Delete"/></td>
                    <td><input id="btnDelA" type="submit" value="Delete Adm" name="DeleteA"/></td>
                </tr>
                <tr>
                    <td><p id="lblN">Username:<input class="lblT" name="lblN" type="text"/><br/></td>
                </tr>
                <tr>
                    <td><p id="lblP">Password:<input class="lblT" name="lblP" type="password"/><br/></td>
                </tr>
                <tr>
                    <td><p id="lblS">Status:<select class="lblT" name = "lblS">
                        <option>Moderator</option>
                        <option>Admin</option>
                    </select><br/></td>
                </tr>
                <tr>
                    <td><input id="btnAdd" type="submit" value="Add" name="Add"/></td>
                </tr>
                <tr>
                    <td><p id="lblError">${logIn}</p></td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>
