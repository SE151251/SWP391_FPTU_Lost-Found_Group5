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
  data-assets-path="../assets/"
  data-template="vertical-menu-template-free"
>
  <head>
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"
    />

    <title>Members Management</title>

    <meta name="description" content="" />
<script src="https://kit.fontawesome.com/f2fda88f12.js" crossorigin="anonymous"></script>
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="./assets/img/favicon/favicon.ico" />

    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap"
      rel="stylesheet"
    />

    <!-- Icons. Uncomment required icon fonts -->
    

    <!-- Core CSS -->   
    <link rel="stylesheet" href="./assets/vendor/css/core.css" />   
    <link rel="stylesheet" href="./assets/vendor/css/theme-default.css" />   
    <link rel="stylesheet" href="./assets/css/demo.css" />   
   
    <script src="./assets/js/config.js"></script>

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
            <li class="menu-item ">
              <a href="paging2" class="menu-link ">
                <i class="menu-icon tf-icons bx bx-dock-top"></i>
                <div data-i18n="Tables">List Posts</div>
              </a>
            </li>
            <li class="menu-item active">
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
              <div class="navbar-nav align-items-center">
                <div class="nav-item d-flex align-items-center">
                  <i class="bx bx-search fs-4 lh-0"></i>
                  <input
                    type="text"
                    class="form-control border-0 shadow-none"
                    placeholder="Search..."
                    aria-label="Search..."
                  />
                </div>
              </div>
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
                        <!-- Content -->

                        <div class="container-xxl flex-grow-1 container-p-y">
                            <!-- Contextual Classes -->
                            <h4 class="fw-bold py-3 mb-4">Authentications</h4>
                            <div class="profile mt-4 ml-4 p-4 ">
                                <section id="about" class="about">

                                    <div class="container" data-aos="fade-up">
                                        <div class="row">

                                            <div class="col-lg-5 col-md-6">
                                                <div class="about-img" data-aos="fade-right" data-aos-delay="100">
                                                    <img src="${MemberInfo.picture}" alt="Avatar">
                                                </div>
                                            </div>

                                            <div class="col-lg-7 col-md-6">
                                                <div class="about-content" data-aos="fade-left" data-aos-delay="100">
                                                    <h2>Member Information</h2>                                                    
                                                    <p>Name: ${MemberInfo.memberName}</p>
                                                    <p>Email: ${MemberInfo.memberEmail}</p>
                                                    <div>
                                                        <p>Profile</p>
                                                        <pre>${MemberInfo.memberProfile}</pre>
                                                    </div>
                                                    <p>Account Status: ${MemberInfo.status}</p>
                                                    <p>Number of warning: ${MemberInfo.memberCount}</p>
                                                    <div>
                                                        <h5>More info:</h5>
                                                        <p>Total Posted: ${numberPost}</p>       
                                                        <p>Total post type LOST: ${numberLost}</p>
                                                        <p>Total post type FOUND: ${numberFound}</p>
                                                    </div>
                                                    <div>
                                                        
                                                        <c:if test="${MemberInfo.status eq 1}"><a class="btn btn-danger" href="WarningMemberServlet?uId=${MemberInfo.memberID}&adminAction=ban">BAN</a> | <a class="btn btn-warning" href="WarningMemberServlet?uId=${MemberInfo.memberID}&adminAction=warnMember">WARNING</a></c:if>
                                                        <c:if test="${MemberInfo.status eq 0}"><a class="btn btn-success" href="WarningMemberServlet?uId=${MemberInfo.memberID}&adminAction=unban">UNBAN</a></c:if>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </section><!-- End About Section -->

                                </div>

                                <hr class="my-5" />

                                <!-- -->
                                                        <c:if test="${not empty listPostWarning}">
                                                            <div class="card">

                                                                <h5 class="card-header">List Flagged Post</h5>
                                                                <div class="table-responsive text-nowrap">
                                                                    <table class="table">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>Article ID</th>
                                                                                <th>Title</th>
                                                                                <th>Post Time</th>
                                                                                <th>Status</th>
                                                                                <th>Article Type</th>
                                                                                <th>View</th>                                               
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody class="table-border-bottom-0">

                                                                            <c:forEach var="dt" items="${listPostWarning}">   
                                                                                <tr class="table-default">

                                                                                    <td>${dt.articleID}</td>
                                                                                    <td>${dt.title}</td>
                                                                                    <td>${dt.postTime}</td>
                                                                                    <c:if test="${dt.articleStatus eq 0}">
                                                                                        <td>Closed</td>
                                                                                    </c:if>
                                                                                    <c:if test="${dt.articleStatus eq 1}">
                                                                                        <td>Active</td>
                                                                                    </c:if>
                                                                                    <c:if test="${dt.articleStatus eq -1}">
                                                                                        <td>Deleted</td>
                                                                                    </c:if>                                      
                                                                                    <td>${dt.type.typeName}</td>
                                                                                    <td><a class="badge bg-label-primary me-1" href="ViewDetailServlet?aId=${dt.articleID}">View</a></td></tr>
                                                                            </c:forEach>
                                                                        </tbody>
                                                                    </table>



                                                                </div>
                                                            </div>
                                                        </c:if>
                                 <hr class="my-5" />
                                                        <c:if test="${not empty listReports}">
                                                            <div class="card">

                                                                <h5 class="card-header">List Reports of this Member</h5>
                                                                <div class="table-responsive text-nowrap">
                                                                    <table class="table">
                                                                        <thead>
                                                                            <tr>
                                                                                
                                                                                <th>Member Report</th>
                                                                                <th>Article Title</th>
                                                                                <th>Report Content</th>
                                                                                <th>Report Time</th>                        
                                                                                <th>Status</th>   
                                                                                <th>Confirm Time</th>
                                                                                <th>Action</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody class="table-border-bottom-0">
                                                                            <c:forEach var="dt" items="${listReports}">   
                                                                                <tr class="table-default">
                                                                                    <td class="d-flex">
                                                                                        
                                                                                            <p
                                                                                                data-bs-toggle="tooltip"
                                                                                                data-popup="tooltip-custom"
                                                                                                data-bs-placement="top"
                                                                                                class="avatar avatar-xs pull-up"
                                                                                                title="Christina Parker"
                                                                                                >
                                                                                                <img src="${dt.member.picture}" alt="Avatar" class="rounded-circle" />
                                                                                            </p><strong class="ml-1">${dt.member.memberName}</strong>
                                                                                        
                                                                                    </td>

                                                                                    <td>${dt.article.title}</td>
                                                                                    <td>${dt.reportContent}</td>
                                                                                    <td>${dt.reportTime}</td>
                                                                                    <td>${dt.status}</td>
                                                                                    <c:if test="${dt.status eq 0}">
                                                                                        <td>${dt.confirmTime}</td>                        
                                                                                    </c:if>
                                                                                    <c:if test="${dt.status eq 1}">
                                                                                        <td>Not yet</td>                        
                                                                                    </c:if>
                                                                                    <td><a class="badge bg-label-primary me-1" href="ViewDetailServlet?memReportID=${dt.member.memberID}&aId=${dt.article.articleID}<c:if test="${dt.status eq 1}">&canConfirm=yes</c:if>">View</a></td>
                                                                                    </tr>
                                                                            </c:forEach>
                                                                        </tbody>
                                                                    </table>



                                                                </div>
                                                            </div>
                                                        </c:if>
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