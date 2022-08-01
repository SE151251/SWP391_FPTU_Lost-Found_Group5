<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <!-- Bootstrap CSS -->
        <script src="https://kit.fontawesome.com/f2fda88f12.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="node_modules/bootstrap/dist/css/bootstrap.min.css" />

        <link rel="stylesheet" href="node_modules/bootstrap-social/bootstrap-social.css" />
        <link rel="stylesheet" href="css/style.css" />
        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.ckeditor.com/4.19.0/basic/ckeditor.js"></script>
    </head>

    <body>
        <header>
            <nav class="navbar navbar-dark navbar-expand-md fixed-top">

                <ul style="width: 13%;" class="navbar-nav container ml-5">
                    <c:if test="${userdata.memberRole eq 1}"></c:if>
                        <li class="nav-item active">
                            <a class="nav-link" 
                            <c:if test="${userdata.memberRole eq 1}">href="paging"</c:if>
                            <c:if test="${userdata.memberRole eq 0}">href="AdminListServlet"</c:if>
                                >
                                <i class="fa fa-home mr-1"></i> Home </a>
                        </li>
                    </ul>

                    <div class="navbar">
                    <c:if test="${empty userdata}">
                        <a type="button" href="https://accounts.google.com/o/oauth2/auth?scope=email profile&redirect_uri=http://localhost:8080/SWP39_LostAndFound/login-google&response_type=code
                           &client_id=287706363103-nelsjcm2sdr3ruldha94fink89tk87tg.apps.googleusercontent.com&approval_prompt=force" style="color: rgb(18, 190, 212);" class="btn btn-login">Login <i
                                class="fa-solid fa-right-to-bracket"></i></a>
                        </c:if>
                        <c:if test="${not empty userdata}">
                        <a class="rounded-circle p-0" type="button" data-toggle="collapse" data-target="#Navbar"> 
                            <img class="rounded-circle" src="${userdata.picture}" height="30" width="100%">
                        </a>
                    </c:if>
                </div>
            </nav>
            <c:if test="${not empty userdata}">
                <div class="collapse navbar-collapse" id="Navbar">
                    <ul class="navbar-nav container ml-5">
                        <li>
                            <span class="Nav-username">${userdata.memberName}</span>
                        </li>
                        <c:if test="${userdata.memberRole eq 1}">
                            <li class="nav-item">
                                <a class="nav-link" href="PersonalServlet?uId=${userdata.memberID}"><span class="fa-solid fa-user"></span> Personal
                                    Page</a>
                            </li>
                        </c:if>
                        <li class="nav-item active">
                            <a class="nav-link" href="LogoutServlet"><i class="fa-solid fa-arrow-right-from-bracket"></i> Logout</a>
                        </li>
                    </ul>
                </div>
            </c:if>
        </header>
        <div style="margin-left: 20%" id="" class="" role="dialog">
            <div class="m" role="content">
                <!-- Modal content-->
                <div class="">
                    <div class="">
                        <div id="postform" class=" col-sm-6 col-md-12 align-self-center">
                            <div class="">
                                <div class="card-body">
                                    <dl class="">
                                        <c:if test="${action eq 'create'}">
                                            <h3 style="margin-left: 30%; padding-top: 50px; padding-bottom: 50px " class="mt-4 font-strong">CREATE POST</h3>
                                        </c:if>
                                        <c:if test="${action eq 'update'}">
                                            <h3 style="margin-left: 30%; padding-top: 50px; padding-bottom: 50px " class="mt-4 font-strong">UPDATE POST</h3>
                                        </c:if>
                                        <form <c:if test="${action eq 'create'}"> enctype='multipart/form-data'</c:if> method="POST">
                                                <table>
                                                    <tr>

                                                        <td style="font-size: 20px; padding-bottom: 20px"">Title:</td>
                                                        <td style="padding-bottom: 20px"><input style="width: 700px; height: 50px;" type="text" name="txtTitle" value="${titlePost}"/>
                                                        <br/>
                                                        <font color="red">${titleError}</font></td>
                                                </tr>      
                                                <tr>

                                                    <td style="font-size: 20px">Content:</td>
                                                    <td style=" padding-bottom: 20px">
                                                        <%--    <input style="width: 500px; height: 100px" type="text" name="txtContent" value="${content}" --%>
                                                        <textarea rows="9" cols="70" id="comment" name="txtContent"><c:out value="${content}"/></textarea>
                                                        <script>
                                                            CKEDITOR.replace('comment');
                                                        </script>
                                                        <font color="red"> ${contentError} </font>
                                                    </td>
                                                </tr> 
                                                <tr>                    
                                                    <td style="font-size: 20px; padding-bottom: 20px"">Hashtag:</td>
                                                    <td style="padding-bottom: 20px"><input style="width: 500px; height: 50px;" type="text" name="txtHashtag" value="${hashtag}"<font color="red">${hashtagError}</font></td>
                                                </tr>
                                                <tr>
                                                    <td  style="font-size: 20px; padding-bottom: 20px">Post type:</td>
                                                    <td style=" padding-bottom: 20px">
                                                        <select style="width: 250px; height: 40px; font-size: 20px; text-align: center" name="txtArticleType" >
                                                            <c:forEach var="dt" items="${ListArticleType}" >                                  
                                                                <option  <c:if test="${dt.typeID eq postTypeId && action eq 'update'}">selected </c:if>
                                                                                                                                       <c:if test="${userdata.memberRole eq 0 && action eq 'create' && dt.typeID eq 3}">selected </c:if>                                                                
                                                                                                                                       <c:if test="${userdata.memberRole eq 1 && dt.typeID eq 3}">hidden</c:if>
                                                                                                                                       <c:if test="${userdata.memberRole eq 0 && dt.typeID ne 3}">hidden</c:if>
                                                                                                                                       value="${dt.typeID}"> <c:out value="${dt.typeName}"/> </option>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>            
                                                </tr>
                                                <c:if test="${userdata.memberRole eq 1}">
                                                    <tr>
                                                        <td style="font-size: 20px; padding-bottom: 20px">Item type:</td>
                                                        <td style=" padding-bottom: 20px">
                                                            <select style="width: 250px; height: 40px; font-size: 20px; text-align: center" name="txtItem">
                                                                <c:forEach var="dt" items="${ListItemType}" >                                  
                                                                    <option <c:if test="${dt.itemID eq itemId}">selected</c:if>
                                                                                                                <%--         <c:if test="${userdata.memberRole eq 0 && dt.itemID eq 13}">selected </c:if> --%>
                                                                                                                value="${dt.itemID}"><c:out value="${dt.itemName}"/></option>
                                                                </c:forEach>
                                                            </select> 
                                                        </td>            
                                                    </tr>
                                                </c:if>
                                                <%--    <tr>                   
                                                        <td style="font-size: 20px; padding-bottom: 20px"">Hastag:</td>
                                                        <td style="padding-bottom: 20px"><input style="width: 500px; height: 50px;" type="text" name="txtTitle" value="${titlePost}"<font color="red">${titleError}</font></td>
                                                    </tr> --%>
                                                <c:if test="${action eq 'create'}">         
                                                    <tr>
                                                        <td style="font-size: 20px">Post image:</td>
                                                        <td>
                                                            <label for="file-upload" class="custom-file-upload">
                                                                <i class="fa fa-cloud-upload"></i> Upload Image
                                                            </label>
                                                            <input id="file-upload" type="file" name="photo" />
                                                            <%--   <input type="file" name="photo"/> --%>
                                                            <font color="red"> ${errorURL} </font>
                                                        </td>               
                                                    </tr>
                                                </c:if>
                                                <%--            <c:if test="${action eq 'update'}">         
                                                            <tr>
                                                                <td style="font-size: 20px">Article image: </td>
                                                                <td>
                                                                    <div style="width: 200px;">
                                                                        <img style="width: 100%; box-sizing: content-box" src="images/${postURL}" />
                                                                    </div>
                                                                </td>               
                                                            </tr>
                                                            </c:if> --%>
                                                <c:if test="${action eq 'update'}">         
                                                    <tr>
                                                        <td style="font-size: 20px; padding-bottom: 20px">Post status:</td>
                                                        <td style=" padding-bottom: 20px">
                                                            <select style="width: 250px; height: 40px; font-size: 20px; text-align: center" name="txtStatus" <c:if test="${isFlag eq 1}"> disabled </c:if> >                                                             
                                                                <option  <c:if test="${aStatus eq '0'}"> selected </c:if>
                                                                                                         value="0">In Active</option>
                                                                    <option <c:if test="${aStatus eq '1'}">selected </c:if>
                                                                                                           value="1">Active</option>                        
                                                                </select> 
                                                            </td>              
                                                        </tr>
                                                </c:if>
                                                <c:if test="${action eq 'update' && isFlag eq 1}">
                                                    <input type="hidden" name="txtStatus" value="0">
                                                </c:if>
                                                <tr>
                                                    <td  colspan="2">                   
                                                        <c:if test="${action eq 'create'}">  
                                                            <input type="hidden" name="articleURL" value="${postURL}">
                                                            <button style="margin-left: 50%;
                                                                    transform: translateX(-50%);" class="search-button btn btn-lg text-white"  formaction="CreateServlet" type="submit">Create</button></c:if>
                                                        <c:if test="${action eq 'update'}">
                                                            <input type="hidden" name="articleURL" value="${postURL}">
                                                            <input type="hidden" name="idUpdate" value="${idUpdate}">
                                                            <button style="margin-left: 50%;
                                                                    transform: translateX(-50%);" class="search-button btn btn-lg text-white "  formaction="UpdateServlet" type="submit">Update</button></c:if>
                                                    </td>
                                                </tr>
                                            </table>                           
                                        </form>
                                    </dl>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <footer>
            <%@include file="footer.jsp" %>
        </footer>
        <!-- modal -->

        <!-- jQuery first, then Popper.js, then Bootstrap JS. -->
        <script src="js/mycode.js"></script>   
    </body>

</html>

