<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <title>Dashboard</title>
    
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
    <script type="text/javascript">

      // Load the Visualization API and the piechart package.
      google.load('visualization', '1.0', {'packages':['corechart']});
      google.setOnLoadCallback(drawChart);

      function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        // Create columns for the DataTable
        data.addColumn('string');
        data.addColumn('number', 'Devices');
        // Create Rows with data
        data.addRows([
          ['Lost', ${numberLost}],
          ['Found', ${numberFound}],          
        ]);
		//Create option for chart
        var options = {
          title: 'The pie chart shows the total number of active Lost and Found articles on the system',
          'width': 1000,
          'height': 800
        };

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
  </head>

  <body>
    <!-- Layout wrapper -->
    <div class="layout-wrapper layout-content-navbar">
      <div class="layout-container">
        <!-- Menu -->

        <aside id="layout-menu" class="layout-menu menu-vertical menu bg-menu-theme">
          <div class="app-brand demo">
            <a href="AdminListServlet" class="app-brand-link">
              
              <span class="demo menu-text  fw-bolder ms-2">FPTU Lost&Found</span>
            </a>
              <a href="javascript:void(0);" class="layout-menu-toggle menu-link text-large ms-auto d-block d-xl-none">
              <i class="bx bx-chevron-left bx-sm align-middle"></i>
            </a>
          </div>

          <div class="menu-inner-shadow"></div>

            <ul class="menu-inner py-1">
            <!-- Dashboard -->
            <li class="menu-item active">
              <a href="AdminListServlet" class="menu-link">
                <i class="menu-icon tf-icons bx bx-home-circle"></i>
                <div data-i18n="Analytics">Dashboard</div>
              </a>
            </li>
            <li class="menu-item">
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
          <h3 style="color: red; font-weight: bold; text-align: center">${errormessage}</h3>
          <nav
            class="layout-navbar container-xxl navbar navbar-expand-xl navbar-detached align-items-center bg-navbar-theme"
            id="layout-navbar"
          >
            <div class="layout-menu-toggle navbar-nav align-items-xl-center me-3 me-xl-0 d-xl-none">
              <a class="nav-item nav-link px-0 me-xl-4" href="javascript:void(0)">
                <i class="bx bx-menu bx-sm"></i>
              </a>
            </div>

              <!-- /Search -->

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
            </>
          </nav>

          <!-- / Navbar -->

          <!-- Content wrapper -->
          <div class="content-wrapper">
            <!-- Content -->

            <div class="container-xxl flex-grow-1 container-p-y">
              <div class="row">
                <div class="col-md-4 mb-4 order-0">
                  <div class="card">
                    <div class="card-body">
                      <div class="d-flex justify-content-between flex-sm-row flex-column gap-3">
                        <div class="d-flex flex-sm-column flex-row align-items-start justify-content-between">
                          <div class="card-title">
                            <h5 class="text-nowrap mb-2">Total Report Not Confirm</h5>
                            
                          </div>
                             
                          <div class="d-flex mt-sm-auto">
                            
                            <h3 class="mb-0">${numberReport}</h3>
                            <h1 class="icon-card-dashboard"><i class="fa-solid fa-flag"></i></h1>
                          </div>
                        </div>
                       
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-md-4 mb-4 order-0">
                  <div class="card">
                    <div class="card-body">
                      <div class="d-flex justify-content-between flex-sm-row flex-column gap-3">
                        <div class="d-flex flex-sm-column flex-row align-items-start justify-content-between">
                          <div class="card-title">
                            <h5 class="text-nowrap mb-2">Total Post Lost Today</h5>
                            
                          </div>
                          
                          <div class="d-flex mt-sm-auto">
                            
                            <h3 class="mb-0">${numberLostToday}</h3>
                            <h1 class="icon-card-dashboard"><i class="fa-solid fa-diamond"></i></h1>
                          </div>
                        </div>
                       
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-md-4 mb-4 order-0">
                  <div class="card">
                    <div class="card-body">
                      <div class="d-flex justify-content-between flex-sm-row flex-column gap-3">
                        <div class="d-flex flex-sm-column flex-row align-items-start justify-content-between">
                          <div class="card-title">
                            <h5 class="text-nowrap mb-2">Total Post Found Today</h5>                            
                          </div>
                         
                          <div class="d-flex mt-sm-auto">
                            
                            <h3 class="mb-0">${numberFoundToday}</h3>
                            <h1 class="icon-card-dashboard"><i class="fa-solid fa-diamond"></i></h1>
                          </div>
                        </div>
                       
                      </div>
                    </div>
                  </div>
                </div>
                
                
          
                <!--/ Total Revenue -->
                <div class="col-12 col-md-8 col-lg-4 order-3 order-md-2">
                  <div class="row">
                    
                    
                    <!-- </div>
    <div class="row"> -->
                   
                  </div>
                </div>
              </div>
              <div class="row">
                <!-- Order Statistics -->
                <div class="col-12 col-lg-12 col-xl-12 order-0 mb-12">
                  <div class="card h-100">
                    
                    <div class="card-body">
                        <div class="d-flex justify-content-center" id="chart_div"></div>
                    </div>
                  </div>
                </div>
                
                <!--/ Order Statistics -->

                <!-- Expense Overview -->
               
                <!--/ Transactions -->
              </div>
              <hr class="my-5" />
              
              <!-- Contextual Classes -->

              <div class="card">
                <h5 class="card-header">List Reports Not Confirm</h5>
                <div class="table-responsive text-nowrap">
                  <table class="table">
                    <thead>
                      <tr>
                      
                        <th>Member Report</th>
                        <th>Article Title</th>
                        <th>Report Content</th>
                        <th>Time</th>                        
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody class="table-border-bottom-0">
                    <c:forEach var="dt" items="${listRp}" >  
                    <tr class="table-default">
                        <td class="d-flex">
                            <div class="list-unstyled users-list m-0 d-flex align-items-center">
                            <p
                              data-bs-toggle="tooltip"
                              data-popup="tooltip-custom"
                              data-bs-placement="top"
                              class="avatar avatar-xs pull-up"
                              title="Christina Parker"
                            >
                              <img src="${dt.member.picture}" alt="Avatar" class="rounded-circle" />
                            </p><strong class="ml-2">${dt.member.memberName}</strong>
                          </div>
                        </td>                      
                        <td>${dt.article.title}</td>
                        <td>${dt.reportContent}</td>
                        <td>${dt.reportTime}</td>
                        <td><a class="badge bg-label-primary me-1" href="ViewDetailServlet?memReportID=${dt.member.memberID}&aId=${dt.article.articleID}<c:if test="${dt.status eq 1}">&canConfirm=yes</c:if>">View</a></td>
                      </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
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