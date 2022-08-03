/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fu.daos;

import fu.dbhelper.DBUtils;
import fu.entities.Article;
import fu.entities.Member;
import fu.entities.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class NotificationDAO {

    public ArrayList<Notification> getAllNotificationsByMember(String mId) throws ClassNotFoundException, SQLException, Exception {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Notification> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * From Notification where ReceiverID like ? "
                        + "Order By NotificationTime DESC";
                stm = con.prepareStatement(sql);
                stm.setString(1, mId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int notiId = rs.getInt("NotificationID");
                    String senderID = rs.getString("SenderID");
                    String receiverID = rs.getString("ReceiverID");
                    String articleID = rs.getString("ArticleID");
                    String content = rs.getString("NotificationContent");
                    int status = rs.getInt("NotificationStatus");
                    String time = rs.getString("NotificationTime");
                    MemberDAO mdao = new MemberDAO();
                    Member sender = mdao.find(senderID);
                    Member receiver = mdao.find(receiverID);
                    ArticleDAO adao = new ArticleDAO();
                    Article a = null;
                    if(articleID!=null){
                    a = adao.find(Integer.parseInt(articleID));
                    }
                    Notification n = new Notification(notiId, sender, receiver, a, content, time, status);
                    lb.add(n);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return lb;
    }

    public boolean addNewNotifications(Notification n) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                if(n.getArticle()!=null){
                String sql = "INSERT INTO Notification (SenderID, ReceiverID, ArticleID, NotificationContent, NotificationTime, NotificationStatus)"
                        + "VALUES (?, ?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, n.getSender().getMemberID());
                stm.setString(2, n.getReceiver().getMemberID());               
                stm.setInt(3, n.getArticle().getArticleID());           
                stm.setString(4, n.getContent());
                stm.setString(5, n.getNotiTime());
                stm.setInt(6, 1);
                }else{
                String sql = "INSERT INTO Notification (SenderID, ReceiverID, NotificationContent, NotificationTime, NotificationStatus)"
                        + "VALUES (?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, n.getSender().getMemberID());
                stm.setString(2, n.getReceiver().getMemberID());                                         
                stm.setString(3, n.getContent());
                stm.setString(4, n.getNotiTime());
                stm.setInt(5, 1);    
                }
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    public boolean changeNotiStatus(int nId)
            throws Exception, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "UPDATE Notification "
                        + "SET NotificationStatus = 0 "
                        + "Where NotificationID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, nId);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    public boolean removeNotification(int aId)
            throws Exception, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Delete From Notification "
                        + "Where ArticleID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, aId);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
}
