<%-- 
    Document   : personal
    Created on : Jun 13, 2022, 10:09:08 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <!DOCTYPE html>
    <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Personal Page</title>
            <!-- Bootstrap CSS -->
            <script src="https://kit.fontawesome.com/f2fda88f12.js" crossorigin="anonymous"></script>
            <link rel="stylesheet" href="css/style.css"/>
            <!-- Bootstrap -->
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
            <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>


        </head>
        <body>
        <header>
        <nav class="navbar navbar-dark navbar-expand-md fixed-top">

            <ul style="width: 13%;" class="navbar-nav container ml-5">              
                <li class="nav-item active">
                    <a class="nav-link" href="paging"><i class="fa fa-home mr-1"></i> Home </a>
                </li>               
                <c:if test="${not empty userdata && userdata.memberRole eq 1}">
                <div class="bell-image online"
                    style="display: inline-block; position: absolute; width: 3%; right: -5px;"
                    onclick="UserSettingToggle()">
                    <h3><i class="fa-solid fa-bell text-white bell-counter"></i></h3>
                    <span class="badge badge-danger badge-counter">${totalNotiNew}</span>
                </div>
                <div class="user-settings">
                    <div class="card-header card-center">
                        <div class="align-items-center row">
                            <div class="col">
                                <h6 class="mb-0">Notification</h6>
                            </div>
                        </div>
                    </div>
                    <div class="fw-normal fs--1 scrollbar list-group list-group-flush" style="max-height: 19rem;">
                    </div>
                <c:forEach var="noti" items="${listNoti}" >
                    <div class="list-group-item">
                        <a class="notification" style="text-decoration: none" 
                        <c:if test="${empty noti.article}">href="paging?notiID=${noti.notiId}"</c:if>
                        <c:if test="${not empty noti.article}">href="ViewDetailServlet?aId=${noti.article.articleID}&notiID=${noti.notiId}"</c:if>
                        >
                            <div class="notification-avatar" style="background-color: white;">
                                <div class="avatar avatar-2x1 me-3" style="position: relative; padding-right: 5px">
                                    <img class="rounded-circle" src="${noti.sender.picture}"
                                        style="width: 55px; height: 55px;">
                                </div>
                            </div>
                            <div class="notification-body" style="background-color: white;">
                                <p class="mb-1">
                                    <strong>${noti.sender.memberName}</strong>
                                    ${noti.content}
                                </p>
                                <span class="notification-time">
                                    <span class="me-2" role="img" aria-label="Emoji"></span>
                                    ${noti.notiTime}
                                </span>
                            </div>
                        </a>
                    </div>
                    </c:forEach>
                </div>
                </c:if>
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
                <li class="nav-item">
                    <a class="nav-link" href="PersonalServlet?uId=${userdata.memberID}"><span class="fa-solid fa-user"></span> Personal
                        Page</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="LogoutServlet"><i class="fa-solid fa-arrow-right-from-bracket"></i> Logout</a>
                </li>
            </ul>
        </div>
            </c:if>
    </header>
    <div class="profile mt-4 ml-4 p-4 ">
        <section id="about" class="about">

            <div class="container" data-aos="fade-up">
                <div class="row">

                    <div class="col-lg-5 col-md-6">
                        <div class="about-img" data-aos="fade-right" data-aos-delay="100">
                            <img src="${memberInfo.picture}" alt="">
                        </div>
                    </div>

                    <div class="col-lg-7 col-md-6">
                        <div class="about-content" data-aos="fade-left" data-aos-delay="100">
                            <h2>About Me</h2>
                            <h3>${memberInfo.memberName}</h3>
                            <h4><span><i class="fa-solid fa-envelope"></i>Email: </span>${memberInfo.memberEmail}</h4>
                            <c:if test="${userdata.memberID ne memberInfo.memberID}"> 
                                <h4><i class="fa-solid fa-user"></i> My Profile:</h4>
                                <pre>${memberInfo.memberProfile}</pre>
                            </c:if>  
                            <c:if test="${userdata.memberID eq memberInfo.memberID}">   
                                <h4><i class="fa-solid fa-user"></i>My Profile:</h4>    
                                <form action="ProfileServlet">                                                                     
                                        <textarea rows="9" cols="70" name="txtProfile" minlength="20" maxlength="2000">${memberInfo.memberProfile}</textarea>                          
                                        <input type="hidden" name="uId" value="${memberInfo.memberID}"/>
                                        <br/>    
                                        <font color="red"> ${errorProfile} </font>
                                        <br/>
                                        <input type="submit" value="Save"/>
                                    </form>
                                </c:if>
                        </div>
                    </div>
                </div>
            </div>

        </section><!-- End About Section -->

    </div>
    <div class="tabs tabs--profile">
        <div class="tab-item active">
            Posts
        </div>
        <div class="line"></div>
    </div>
    <!-- tab content -->
    <div class="tab-content tab-content--profile mt-3">
        <div class="tab-pane tab-pane--profile active">
            <c:forEach var="dt" items="${articlesPersonal}" >
         <!--    <div class="pane col-md-3 mb-3">
                <div class="card h-100">
                    <div class="pane-img">
                       <%-- <c:if test="${not empty dt.imgUrl}">
                                    <img src="images/${dt.imgUrl}" alt=""> </c:if>
                                <c:if test="${empty dt.imgUrl}">
                                    <img src="images/Logo_LostFound.png" alt=""> </c:if> --%>
                    </div>
                    <div class="card-body pane-content">
                        <h5 class="card-title">Card title</h5>
                        <p class="card-text">Product: Nhân phẩmssssssssssssssssssssssssssssssssssssssssssssssS</p>
                        <p class="card-text">Thể loại: </p>
                        <p class="card-text">Name: Lê Minh Thiện</p>
                        <a href="javascript:void(0)" class="btn btn-outline-primary pb-4">Go somewhere</a>
                    </div>
                </div>
            </div> -->
            <a href="ViewDetailServlet?aId=${dt.articleID}">
                <div class="pane mb-3">
                    <div class="card h-100">
                        <div class="pane-img">
                            <c:if test="${not empty dt.imgUrl}">
                        <img class="card-img-top" src="images/${dt.imgUrl}" alt=""> </c:if>
                        <c:if test="${empty dt.imgUrl}">
                        <img class="card-img-top" src="images/Logo_LostFound.png" alt=""> </c:if>
                        </div>
                        <div class="card-body pane-content">
                            <h5 class="card-title"><c:out value="${dt.title}"/></h5>
                            <p class="card-text">Time: <c:out value="${dt.postTime}"/></p>
                            <a href="SearchServlet?txtItem=${dt.item.itemID}&searchAction=Find">    <p><span style="padding: 5px 10px 5px 10px" class="badge badge-pill badge-primary"><c:out value="${dt.item.itemName}"/></span></p>   </a> 
                            <p class="card-text">
                                 <c:forEach var="lah" items="${listAH}" >
                        <c:if test="${dt.articleID eq lah.article.articleID}">
                        <span><a href="SearchServlet?hId=${lah.hashtag.hashtagID}&searchAction=Find"><c:out value="${lah.hashtag.hashtagName}"/></a></span>
                        </c:if>    
                    </c:forEach> 
                            </p>

                        </div>
                    </div>
                </div>
            </a>
          </c:forEach>
        </div>


    </div>
    <!-- Footer -->
    <footer class="text-center text-lg-start bg-light text-muted">
        <!-- Section: Social media -->
        <section class="text-white d-flex justify-content-center justify-content-lg-between p-4 border-bottom">
            <!-- Left -->
            <div class="me-5 d-none d-lg-block">
                <span>Get connected with us on social networks:</span>
            </div>
            <!-- Left -->

            <!-- Right -->
            <div>
                <a href="" class="me-4 text-reset">
                    <i class="fab fa-facebook-f text-white"></i>
                </a>
                <a href="" class="me-4 text-reset">
                    <i class="fab fa-twitter text-white"></i>
                </a>
                <a href="" class="me-4 text-reset">
                    <i class="fab fa-google text-white"></i>
                </a>
                <a href="" class="me-4 text-reset">
                    <i class="fab fa-instagram text-white"></i>
                </a>
                <a href="" class="me-4 text-reset">
                    <i class="fab fa-github text-white"></i>
                </a>
            </div>
            <!-- Right -->
        </section>
        <!-- Section: Social media -->

        <!-- Section: Links  -->
        <section class="text-white">
            <div class="container text-center text-md-start mt-5">
                <!-- Grid row -->
                <div class="row mt-3">
                    <!-- Grid column -->
                    <div class="col-md-3 col-lg-4 col-xl-3 mx-auto mb-4">
                        <!-- Content -->
                        <h6 class="text-uppercase fw-bold mb-4">
                            <i class="fas fa-gem me-3 mr-1"></i>FPTU Lost&Found
                        </h6>
                        <p>
                            Here you can use rows and columns to organize your footer content. Lorem ipsum
                            dolor sit amet, consectetur adipisicing elit.
                        </p>
                    </div>
                    <!-- Grid column -->

                    <!-- Grid column -->
                    <div class="col-md-2 col-lg-2 col-xl-2 mx-auto mb-4">
                        <!-- Links -->
                        <h6 class="text-uppercase fw-bold mb-4">
                            Products
                        </h6>
                        <p>
                            <a href="#!" class="text-white">Angular</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">React</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Vue</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Laravel</a>
                        </p>
                    </div>
                    <!-- Grid column -->

                    <!-- Grid column -->
                    <div class="col-md-3 col-lg-2 col-xl-2 mx-auto mb-4">
                        <!-- Links -->
                        <h6 class="text-uppercase fw-bold mb-4">
                            Useful links
                        </h6>
                        <p>
                            <a href="#!" class="text-white">Pricing</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Settings</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Orders</a>
                        </p>
                        <p>
                            <a href="#!" class="text-white">Help</a>
                        </p>
                    </div>
                    <!-- Grid column -->

                    <!-- Grid column -->
                    <div class="col-md-4 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4">
                        <!-- Links -->
                        <h6 class="text-uppercase fw-bold mb-4">
                            Contact
                        </h6>
                        <p><i class="fas fa-home me-3"></i> New York, NY 10012, US</p>
                        <p>
                            <i class="fas fa-envelope me-3"></i>
                            info@example.com
                        </p>
                        <p><i class="fas fa-phone me-3"></i> + 01 234 567 88</p>
                        <p><i class="fas fa-print me-3"></i> + 01 234 567 89</p>
                    </div>
                    <!-- Grid column -->
                </div>
                <!-- Grid row -->
            </div>
        </section>
        <!-- Section: Links  -->

        <!-- Copyright -->
        <div class="text-center text-white p-4">
            © 2022 Copyright:
            <a class="text-reset text-white fw-bold" href="https://mdbootstrap.com/">Group 5</a>
        </div>
        <!-- Copyright -->
    </footer>
    
    <!-- jQuery first, then Popper.js, then Bootstrap JS. -->
    <script src="js/mycode.js"></script>
   <!-- <script src="node_modules/jquery/dist/jquery.slim.min.js"></script>
    <script src="node_modules/popper.js/dist/umd/popper.min.js"></script>
    <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script> -->
</body>

</html>