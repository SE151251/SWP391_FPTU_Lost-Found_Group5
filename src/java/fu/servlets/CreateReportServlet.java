/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fu.servlets;

import fu.daos.ArticleDAO;
import fu.daos.MemberDAO;
import fu.daos.ReportDAO;
import fu.entities.Article;
import fu.entities.Member;
import fu.entities.Report;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "CreateReportServlet", urlPatterns = {"/CreateReportServlet"})
public class CreateReportServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                request.setAttribute("errormessage", "Please login!");
                request.getRequestDispatcher("paging").forward(request, response);
            } else if (session.getAttribute("userdata") != null) { // check login
                Member member = (Member) session.getAttribute("userdata");
                if (member.getMemberRole() == 1) {
                if (member.getStatus() == 1) {
                    String rContent = request.getParameter("txtReport");
                    String memberReportId = request.getParameter("memberReport");
                    String aId = request.getParameter("aId");
                    if (rContent.trim().isEmpty() || rContent.trim().length() > 200) {
                        request.setAttribute("errorReport", "Your report must be from 1 to 200 characters");
                        request.getRequestDispatcher("ViewDetailServlet?aId=" + aId).forward(request, response);
                    } else {
                        //String newId;
                        ArticleDAO aDao = new ArticleDAO();
                        Article art = aDao.find(Integer.parseInt(aId));
                        MemberDAO mdao = new MemberDAO();
                        Member memR = mdao.find(memberReportId);
                        ReportDAO rdao = new ReportDAO();
                        Report r = new Report(0, rContent, LocalDateTime.now().toString(), null, 1, art, member);
                        rdao.addNewReport(r);
                        request.getRequestDispatcher("paging").forward(request, response);
                    }
                } else {
                    request.setAttribute("errormessage", "Your account has been banned!");
                    request.getRequestDispatcher("paging").forward(request, response);
                }
                } else {
                    request.setAttribute("errormessage", "Incorrect ROLE! Must be MEMBER");
                    request.getRequestDispatcher("paging").forward(request, response);
                }

            } else {
                request.setAttribute("errormessage", "Please login!");
                request.getRequestDispatcher("lpaging.jsp").forward(request, response);
            }

        } catch (Exception e) {
            log("ERROR at CreateCommentServlet: " + e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
