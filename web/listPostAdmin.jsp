<%-- 
    Document   : listMember
    Created on : Jul 1, 2022, 11:01:45 PM
    Author     : LENOVO
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html
    lang="en"
    class="light-style layout-menu-fixed"
    dir="ltr"
    data-theme="theme-default"
    data-assets-path="./assets/"
    data-template="vertical-menu-template-free"
    >
    <head>
        <meta charset="utf-8" />
        <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"
            />

        <title>List Post</title>

        <meta name="description" content="" />

        <!-- Favicon -->
        <link rel="icon" type="image/x-icon" href="../assets/img/favicon/favicon.ico" />

        <!-- Fonts -->
        <script src="https://kit.fontawesome.com/f2fda88f12.js" crossorigin="anonymous"></script>
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
            href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap"
            rel="stylesheet"
            />

        <!-- Icons. Uncomment required icon fonts -->
        <link rel="stylesheet" href="./assets/vendor/fonts/boxicons.css" />

        <!-- Core CSS -->
        <link rel="stylesheet" href="./assets/vendor/css/core.css" class="template-customizer-core-css" />
        <link rel="stylesheet" href="./assets/vendor/css/theme-default.css" class="template-customizer-theme-css" />
        <link rel="stylesheet" href="./assets/css/demo.css" />
     
        <!--<link rel="stylesheet" href=./css/style.css" />-->
        <!-- Vendors CSS -->
        <link rel="stylesheet" href="./assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" />

        <link rel="stylesheet" href="./assets/vendor/libs/apex-charts/apex-charts.css" />

        <!-- Page CSS -->

        <!-- Helpers -->
        <script src="../assets/vendor/js/helpers.js"></script>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
        <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
        <script src="../assets/js/config.js"></script>
        <jsp:useBean id="a" class="fu.daos.ArticleDAO" scope="request"></jsp:useBean>
        </head>

        <body>
            <!-- Layout wrapper -->
            <div class="layout-wrapper layout-content-navbar">
                <div class="layout-container">
                    <!-- Menu -->

                    <aside id="layout-menu" class="layout-menu menu-vertical menu bg-menu-theme">
                        <div class="app-brand demo">
                            <a href="AdminListServlet" class="app-brand-link">

                                <span class="demo menu-text fw-bolder ms-2">FPTU Lost&Found</span>
                            </a>
                            <a href="javascript:void(0);" class="layout-menu-toggle menu-link text-large ms-auto d-block d-xl-none">
                                <i class="bx bx-chevron-left bx-sm align-middle"></i>
                            </a>
                        </div>

                        <div class="menu-inner-shadow"></div>

                        <ul class="menu-inner py-1">
                            <!-- Dashboard -->
                            <li class="menu-item ">
                                <a href="AdminListServlet" class="menu-link">
                                    <i class="menu-icon tf-icons bx bx-home-circle"></i>
                                    <div data-i18n="Analytics">Dashboard</div>
                                </a>
                            </li>
                            <li class="menu-item active">
                                <a href="paging2" class="menu-link ">
                                    <i class="menu-icon tf-icons bx bx-dock-top"></i>
                                    <div data-i18n="Tables">List Posts</div>
                                </a>
                            </li>
                            <li class="menu-item">
                                <a href="ListMemberServlet" class="menu-link">
                                    <i class="menu-icon tf-icons bx bx-lock-open-alt"></i>
                                    <div data-i18n="Authentications">List Members</div>
                                </a>
                            </li>
                        </ul>
                        <!-- Components -->



                        </ul>
                    </aside>
                    <!-- / Menu -->

                    <!-- Layout container -->
                    <div class="layout-page">
                        <!-- Navbar -->

                        <nav
                            class="layout-navbar container-xxl navbar navbar-expand-xl navbar-detached align-items-center bg-navbar-theme"
                            id="layout-navbar"
                            >
                            <div class="layout-menu-toggle navbar-nav align-items-xl-center me-3 me-xl-0 d-xl-none">
                                <a class="nav-item nav-link px-0 me-xl-4" href="javascript:void(0)">
                                    <i class="bx bx-menu bx-sm"></i>
                                </a>
                            </div>


                            <div class="navbar-nav-right d-flex align-items-center" id="navbar-collapse">
                                <!-- Search -->
                                <!--                                <div class="navbar-nav align-items-center">
                                                                    <div class="nav-item d-flex align-items-center">
                                                                        <i class="bx bx-search fs-4 lh-0"></i>
                            <%--  <input
                                type="text"
                                class="form-control border-0 shadow-none"
                                placeholder="Search..."
                                aria-label="Search..."
                              /> --%>
                            <form class="search d-flex col-md-8">
                                <div class="search-field">
                                    <input class="form-control border-0 shadow-none"
                                placeholder="Search..."
                                aria-label="Search..." type="text" name="keySearch" 
                                    <input type="hidden" name="searchAction" value="Notice"/>
                                </div>

                                <button formaction="SearchServlet" class="btn border-0 shadow-none p-1 pl-3 pr-3 ml-2">Search</button>
                            </form>
                        </div>
                    </div>-->
                            <ul class="navbar-nav flex-row align-items-center ms-auto">
                                <!-- Place this tag where you want the button to render. -->


                                <!-- User -->
                                <li class="nav-item navbar-dropdown dropdown-user dropdown">
                                    <a class="nav-link dropdown-toggle hide-arrow" href="javascript:void(0);" data-bs-toggle="dropdown">
                                        <div class="avatar avatar-online">
                                            <img src="${userdata.picture}" alt class="w-px-40 h-auto rounded-circle" />
                                        </div>
                                    </a>
                                    <ul class="dropdown-menu dropdown-menu-end">
                                        <li>
                                            <a class="dropdown-item" href="#">
                                                <div class="d-flex">
                                                    <div class="flex-shrink-0 me-3">
                                                        <div class="avatar avatar-online">
                                                            <img src="${userdata.picture}" alt class="w-px-40 h-auto rounded-circle" />
                                                        </div>
                                                    </div>
                                                    <div class="flex-grow-1">
                                                        <span class="fw-semibold d-block">${userdata.memberName}</span>
                                                        <small class="text-muted">Admin</small>
                                                    </div>
                                                </div>
                                            </a>
                                        </li>
                                        <li>
                                            <div class="dropdown-divider"></div>
                                        </li>
                                        <li>
                                            <div class="dropdown-divider"></div>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="LogoutServlet">
                                                <i class="bx bx-power-off me-2"></i>
                                                <span class="align-middle">Log Out</span>
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                                <!--/ User -->
                            </ul>
                            <div/>
                    </nav>

                    <!-- / Navbar -->

                    <!-- Content wrapper -->
                    <div class="content-wrapper">
                        <div class="container-xxl flex-grow-1 container-p-y">
                            <h4 class="fw-bold py-3 mb-4">List Post</h4>
                            <c:if test="${not empty userdata && userdata.status eq 1}">
                                <a type="button" href="CreateFormServlet" class="center btn-primary createPost--btn btn rounded-circle"><i
                                        class="fa-solid fa-arrow-up-right-from-square"></i></a>
                                </c:if>
                            <hr class="my-5" />
                            <!-- Content -->

                            <div class="row ">
                                <c:forEach var="dt" items="${articlesNotice}" >
                                   
                                    <div style="cursor: pointer;" class="pane col-md-4 mb-3" onclick="window.location='ViewDetailServlet?aId=${dt.articleID}';" >
                                        
                                             <div class="card h-100">
                                                <div class="pane-img">
                                                    <c:if test="${not empty dt.imgUrl}">
                                                        <img  src="images/${dt.imgUrl}" alt=""> </c:if>
                                                    <c:if test="${empty dt.imgUrl}">
                                                        <img  src="images/Logo_LostFound.png" alt=""> </c:if>
                                                </div>
                                                <div class="card-body pane-content">
                                                        <h5 class="card-title"><c:out value="${dt.title}"/></h5>
                                                    <p class="card-text">Time: <c:out value="${dt.postTime}"/></p>                           
                                                <%--    <p class="card-text">
                                                        <c:forEach var="lah" items="${listAH}" >
                                                            <c:if test="${dt.articleID eq lah.article.articleID}">
                                                                <span><a href="SearchServlet?hId=${lah.hashtag.hashtagID}&searchAction=Notice"><c:out value="${lah.hashtag.hashtagName}"/></a></span>
                                                                </c:if>    
                                                            </c:forEach> 
                                                    </p> --%>

                                                </div>
                                            
                                       
                                        </div>
                                           
                                    </div>
                                </c:forEach>
                                <nav aria-label="...">
                                    <ul class ="pagination pagination-lg mt-3">
                                        <c:forEach begin="1" end="${a.numberPage}" var="i">
                                            <li class="page-item ${indexPage2==i?"active":""}"><a class="page-link" href="paging2?index2=${i}">${i}</a></li>
                                            </c:forEach>
                                    </ul>
                                </nav>
                            </div>       
                            <!-- / Content -->

                            <!-- Footer -->

                            <!-- / Footer -->

                            <div class="content-backdrop fade"></div>
                        </div>
                        <!-- Content wrapper -->
                    </div>
                    <!-- / Layout page -->
                </div>
            </div>

            <!-- Overlay -->


            <!-- Core JS -->
            <script src="./assets/vendor/libs/jquery/jquery.js"></script>
            <script src="./assets/vendor/libs/popper/popper.js"></script>
            <script src="./assets/vendor/js/bootstrap.js"></script>
            <script src="./assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.js"></script>

            <script src="./assets/vendor/js/menu.js"></script>
            <!-- Main JS -->
            <script src="./assets/js/main.js"></script>

            <!-- Page JS -->
            <script src="./assets/js/dashboards-analytics.js"></script>

            <!-- Place this tag in your head or just before your close body tag. -->
            <script async defer src="https://buttons.github.io/buttons.js"></script>
    </body>
</html>