/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fu.servlets;

import SendMail.JavaMailUtil;
import fu.daos.ArticleDAO;
import fu.daos.MemberDAO;
import fu.daos.NotificationDAO;
import fu.daos.ReportDAO;
import fu.entities.Article;
import fu.entities.Member;
import fu.entities.Notification;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
@WebServlet(name = "WarningMemberServlet", urlPatterns = {"/WarningMemberServlet"})
public class WarningMemberServlet extends HttpServlet {

    private static final String ADMIN = "AdminListServlet";
    private static final String LIST_MEMBERS_PAGE = "ListMemberServlet";
    private static final String HOME = "paging";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = ADMIN;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                request.setAttribute("errormessage", "Please login with ADMIN account!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else if (session.getAttribute("userdata") != null) {
                Member member = (Member) session.getAttribute("userdata");
                if (member.getMemberRole() == 0) {
                    if (member.getStatus() == 1) {
                        String uId = request.getParameter("uId");
                        String aId = request.getParameter("aId");
                        String adminAction = request.getParameter("adminAction");
                        NotificationDAO ndao = new NotificationDAO();
                        ArticleDAO aDao = new ArticleDAO();
                        MemberDAO mdao = new MemberDAO();
                        ReportDAO rdao = new ReportDAO();
                        // Thêm trường hợp cảnh cáo bài viết
                        // Với bài viết bị cảnh cáo thì close bài viết đó
                        // Với member bị cảnh cáo thì chỉ thay đổi count
                        // Với member bị Ban thì đóng hết các bài viết bị report
                        // Sau khi xử lý 1 report thì các report liên quan tới bài viết đó đều được xử lý theo 1 lượt.

                        // Trường hợp member bị cảnh cáo
                        if (adminAction.equalsIgnoreCase("flag")) {
                            //Thay đổi trạng thái warning của bài viết
                            aDao.flagArticle(Integer.parseInt(aId));
                            // Thay đổi hết trạng thái của các report liên quan tới bài viết
                            rdao.updateStatusReportByArticleID(Integer.parseInt(aId));
                            // Thay đổi trạng thái bài viết thành closed
                            aDao.closeArticle(Integer.parseInt(aId));
                            Article postFlag = aDao.find(Integer.parseInt(aId));
                            String notiContent = "Your post has been FLAGGED by ADMIN";
                            // Tạo thông báo
                            ndao.addNewNotifications(new Notification(0, member, postFlag.getMember(), postFlag, notiContent, LocalDateTime.now().toString(), 1));
                            JavaMailUtil.sendMail(postFlag.getMember().getMemberEmail(), "Your post has been FLAGGED by ADMIN");
                        } else if (adminAction.equalsIgnoreCase("unFlag")) {
                            // Thay đổi trạng thái warning của bài viết
                            aDao.unFlagArticle(Integer.parseInt(aId));
                            //rdao.updateStatusReportByArticleID(Integer.parseInt(aId));
                            // Thay đổi trạng thái bài viết thành open
                            aDao.openArticle(Integer.parseInt(aId));
                            Article postFlag = aDao.find(Integer.parseInt(aId));
                            String notiContent = "Your post has been UNFLAG by ADMIN";
                            // Tạo thông báo
                            ndao.addNewNotifications(new Notification(0, member, postFlag.getMember(), postFlag, notiContent, LocalDateTime.now().toString(), 1));
                            //JavaMailUtil.sendMail(postFlag.getMember().getMemberEmail(), "Your post has been UNFLAG by ADMIN");
                        } else if (adminAction.equalsIgnoreCase("warnMember")) {
                            // Lấy ra ttin member bị cảnh cáo
                            Member memberWarn = mdao.find(uId);
                            // Cập nhật số lần cảnh cáo của member bị cảnh cáo
                            memberWarn.setMemberCount(memberWarn.getMemberCount() + 1);
                            mdao.warningMember(memberWarn);
                            // Tạo thông báo
                            String notiContent = "Your account has been WARNED "+memberWarn.getMemberCount()+" times by ADMIN";
                            ndao.addNewNotifications(new Notification(0, member, memberWarn, null, notiContent, LocalDateTime.now().toString(), 1));
                            JavaMailUtil.sendMail(memberWarn.getMemberEmail(), "Your account has been WARNED by ADMIN");
                            url = LIST_MEMBERS_PAGE;

                            // Trường hợp member bị ban
                        } else if (adminAction.equalsIgnoreCase("ban")) {
                             // Lấy ra ttin member bị Ban
                            Member memberBan = mdao.find(uId);
                            // Cập nhật trạng thái account của member bị Ban
                            memberBan.setStatus(0);
                            mdao.banOrUnbanMember(memberBan);

                            // Đóng tất cả bài viết bị report
                            //B1: Lấy ra tất cả bài viết bị report của member này
                            //B2: Đổi all post với status thành 0 (closed), đồng thời đóng các report liên quan đến từng post
                            ArrayList<Integer> list = aDao.getAllArticlesBeReportedOfAMember(memberBan.getMemberID());
                            for (Integer i : list) {
                                aDao.updateStatusArticlesOfMemberBanned(memberBan, i);
                                rdao.updateStatusReportByArticleID(i);
                            }
                            // Tạo thông báo khi bị Ban
                            String notiContent = "Your account has been BANNED by ADMIN";
                            ndao.addNewNotifications(new Notification(0, member, memberBan, null, notiContent, LocalDateTime.now().toString(), 1));
                            JavaMailUtil.sendMail(memberBan.getMemberEmail(), "Your account has been BANNED by ADMIN");
                            url = LIST_MEMBERS_PAGE;

                            // Trường hợp member bị tố cáo không có vấn đề gì
                        } else if (adminAction.equalsIgnoreCase("none")) {
                            //Thay đổi trạng thái của tất cả report về bài viết này thành 0 (tức là đã xử lý all report của bài viết)
                            rdao.updateStatusReportByArticleID(Integer.parseInt(aId));                           
                        } else if (adminAction.equalsIgnoreCase("unban")) {
                             // Lấy ra ttin member được unBan
                            Member memberBan = mdao.find(uId);
                            // Cập nhật trạng thái account của member được unBan
                            memberBan.setStatus(1);
                            mdao.banOrUnbanMember(memberBan);
                            // Tạo thông báo
                            String notiContent = "Your account has been UNLOCKED by ADMIN";
                            ndao.addNewNotifications(new Notification(0, member, memberBan, null, notiContent, LocalDateTime.now().toString(), 1));
                            JavaMailUtil.sendMail(memberBan.getMemberEmail(), "Your account has been UNLOCKED by ADMIN");
                            url = LIST_MEMBERS_PAGE;
                        }
                    } else {
                        request.setAttribute("errormessage", "Your account has been banned! Cannot use this function");
                        //request.getRequestDispatcher("AdminListServlet").forward(request, response);
                        url=ADMIN;
                    }
                } else {
                    request.setAttribute("errormessage", "Incorrect Role! Must be ADMIN");
                    //request.getRequestDispatcher("paging").forward(request, response);
                    url=HOME;
                }

            } else {
                request.setAttribute("errormessage", "Please login!");
                //request.getRequestDispatcher("paging").forward(request, response);
                url=HOME;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log("ERROR at WarningMemberServlet: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
