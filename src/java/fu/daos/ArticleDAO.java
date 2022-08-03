/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fu.daos;

import fu.dbhelper.DBUtils;
import fu.entities.Article;
import fu.entities.ArticleType;
import fu.entities.Item;
import fu.entities.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class ArticleDAO {

    private List<Article> articles;
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public ArticleDAO() {
        try {
            this.articles = getAllArticles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Article> findAll() {
        return this.articles;
    }

    public Article find(int id) {
        for (Article a : this.articles) {
            if (a.getArticleID() == id) {
                return a;
            }
        }
        return null;
    }

    public ArrayList<Article> getAllArticles() throws ClassNotFoundException, SQLException, Exception {
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * From Article "
                        + "Order By PostTime DESC";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    String memberId = rs.getString("MemberID");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    int itemId = rs.getInt("ItemID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);
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

    public boolean updateContentArticle(Article b) throws Exception {
        boolean check = false;        
        
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                if(b.getItem()==null){
                String sql = "UPDATE Article SET ArticleTitle = ?, ArticleContent = ?, ArticleStatus=?, ArticleTypeID=? Where ArticleID=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, b.getTitle());
                stm.setString(2, b.getArticleContent());              
                stm.setInt(3, b.getArticleStatus());
                stm.setInt(4, b.getType().getTypeID());
                stm.setInt(5, b.getArticleID()); 
                }
                else if(b.getItem()!=null){
                String sql = "UPDATE Article SET ArticleTitle = ?, ArticleContent = ?, ArticleStatus=?, ArticleTypeID=?, ItemID=? Where ArticleID=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, b.getTitle());
                stm.setString(2, b.getArticleContent());
                stm.setInt(3, b.getArticleStatus());
                stm.setInt(4, b.getType().getTypeID());
                stm.setInt(5, b.getItem().getItemID());
                stm.setInt(6, b.getArticleID()); 
                }
                              
                stm.executeUpdate();
                check = stm.executeUpdate() > 0;
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
        return check;
    }

    public int createNewArticle(Article b) throws SQLException {
        int idPost = 0;
        try {           
            con = DBUtils.makeConnection();
            if (con != null) {
                if(b.getImgUrl()==null && b.getItem()==null){
                String sql = "INSERT INTO Article (ArticleTitle, ArticleContent, PostTime, ArticleStatus, WarningStatus, MemberID, ArticleTypeID) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);               
                stm.setString(1, b.getTitle());
                stm.setString(2, b.getArticleContent());
                stm.setString(3, b.getPostTime());
                stm.setInt(4, 1);
                stm.setInt(5, 0);
                stm.setString(6, b.getMember().getMemberID());
                stm.setInt(7, b.getType().getTypeID());
                }
                else if(b.getImgUrl()!=null && b.getItem()==null){
                String sql = "INSERT INTO Article (ArticleTitle, ArticleContent, ImgURL, PostTime, ArticleStatus, WarningStatus, MemberID, ArticleTypeID) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stm.setString(1, b.getTitle());
                stm.setString(2, b.getArticleContent());
                stm.setString(3, b.getImgUrl());
                stm.setString(4, b.getPostTime());
                stm.setInt(5, 1);
                stm.setInt(6, 0);
                stm.setString(7, b.getMember().getMemberID());
                stm.setInt(8, b.getType().getTypeID()); 
                }
                else if(b.getImgUrl()==null && b.getItem()!=null){
                String sql = "INSERT INTO Article (ArticleTitle, ArticleContent, PostTime, ArticleStatus, WarningStatus, MemberID, ArticleTypeID, ItemID) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stm.setString(1, b.getTitle());
                stm.setString(2, b.getArticleContent());
                stm.setString(3, b.getPostTime());
                stm.setInt(4, 1);
                stm.setInt(5, 0);
                stm.setString(6, b.getMember().getMemberID());
                stm.setInt(7, b.getType().getTypeID());
                stm.setInt(8, b.getItem().getItemID()); 
                }
                else if(b.getImgUrl()!=null && b.getItem()!=null){
                String sql = "INSERT INTO Article (ArticleTitle, ArticleContent, ImgURL, PostTime, ArticleStatus, WarningStatus, MemberID, ArticleTypeID, ItemID) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stm.setString(1, b.getTitle());
                stm.setString(2, b.getArticleContent());
                stm.setString(3, b.getImgUrl());
                stm.setString(4, b.getPostTime());
                stm.setInt(5, 1);
                stm.setInt(6, 0);
                stm.setString(7, b.getMember().getMemberID());
                stm.setInt(8, b.getType().getTypeID());
                stm.setInt(9, b.getItem().getItemID()); 
                }
//                String sql = "INSERT INTO Article (ArticleID, ArticleTitle, ArticleContent, ImgURL, PostTime, ArticleStatus, MemberID, ArticleTypeID, ItemID) "
//                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//                stm = con.prepareStatement(sql);
//                stm.setString(1, b.getArticleID());
//                stm.setString(2, b.getTitle());
//                stm.setString(3, b.getArticleContent());
//                stm.setString(4, b.getImgUrl());
//                stm.setString(5, b.getPostTime());
//                stm.setInt(6, 1);
//                stm.setString(7, b.getMember().getMemberID());
//                stm.setInt(8, b.getType().getTypeID());
//                stm.setInt(9, b.getItem().getItemID());                 
                int row = stm.executeUpdate();
                if (row > 0) {
                    rs = stm.getGeneratedKeys();
                    if (rs.next()) {
                        idPost = rs.getInt(1);
                    }                
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
        return idPost;
    }

    public boolean deleteArticle(int aId)
            throws Exception, SQLException {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "UPDATE Article "
                        + "SET ArticleStatus = -1 "
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
    
    //Hàm này để đóng tất cả các bài viết của account bị ban
    public boolean updateStatusArticlesOfMemberBanned(Member m, int aId) throws Exception {
        boolean check = false;
        String sql = ("UPDATE Article SET ArticleStatus=0 Where MemberID=? and ArticleID = ?");
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setString(1, m.getMemberID());  
                stm.setInt(2, aId);
                stm.executeUpdate();
                check = stm.executeUpdate() > 0;
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
        return check;
    }
    //Hàm này để đóng bài viết của account bị cảnh cáo
    public boolean closeArticle(int aId) throws Exception {
        boolean check = false;
        String sql = ("UPDATE Article SET ArticleStatus=0 Where ArticleID=?");
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setInt(1, aId);                           
                stm.executeUpdate();
                check = stm.executeUpdate() > 0;
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
        return check;
    }
    //Hàm này để đóng bài viết của account bị cảnh cáo
    public boolean openArticle(int aId) throws Exception {
        boolean check = false;
        String sql = ("UPDATE Article SET ArticleStatus=1 Where ArticleID=?");
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setInt(1, aId);                           
                stm.executeUpdate();
                check = stm.executeUpdate() > 0;
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
        return check;
    }
    // Hàm này lấy ra các bài viết bị report của 1 member
    public ArrayList<Integer> getAllArticlesBeReportedOfAMember(String mId)throws ClassNotFoundException, SQLException, Exception {
       
        ArrayList<Integer> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID\n" +
                            "from Article A inner join Report R on A.ArticleID = R.ArticleID\n" +
                            "inner join Member M on M.MemberID = A.MemberID\n" +
                            "where A.MemberID = ?\n" +
                            "group by M.FullName,A.MemberID,A.ArticleID";
                stm = con.prepareStatement(sql);
                stm.setString(1, mId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");                    
                    lb.add(articleId);
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
  // Lấy tất cả các bài loại "Tìm đồ"
    public ArrayList<Article> getAllArticlesFind() throws ClassNotFoundException, SQLException, Exception {
       
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID, A.ArticleTitle, A.ArticleContent, A.ImgURL, A.PostTime, A.ArticleStatus, A.WarningStatus, A.MemberID, A.ArticleTypeID, A.ItemID \n" +
                            "from Article A inner join ArticleType AType on A.ArticleTypeID = AType.ArticleTypeID\n" +
                            "Where A.ArticleTypeID = 1 and ArticleStatus = 1  Order By PostTime DESC";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    String memberId = rs.getString("MemberID");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    int itemId = rs.getInt("ItemID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus, warningStatus, i, m, a);
                    lb.add(art);
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
    // Lấy tất cả các bài loại "Trả đồ"
    public ArrayList<Article> getAllArticlesReturn() throws ClassNotFoundException, SQLException, Exception {
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID, A.ArticleTitle, A.ArticleContent, A.ImgURL, A.PostTime, A.ArticleStatus, A.WarningStatus, A.MemberID, A.ArticleTypeID, A.ItemID \n" +
                            "from Article A inner join ArticleType AType on A.ArticleTypeID = AType.ArticleTypeID\n" +
                            "Where A.ArticleTypeID = 2 and ArticleStatus = 1  Order By PostTime DESC";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    String memberId = rs.getString("MemberID");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    int itemId = rs.getInt("ItemID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus, warningStatus, i, m, a);
                    lb.add(art);
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
          
    // Lấy tất cả các bài loại "Thông báo"
    public ArrayList<Article> getAllArticlesNotice() throws ClassNotFoundException, SQLException, Exception {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID, A.ArticleTitle, A.ArticleContent, A.ImgURL, A.PostTime, A.ArticleStatus, A.WarningStatus, A.MemberID, A.ArticleTypeID, A.ItemID \n" +
                            "from Article A inner join ArticleType AType on A.ArticleTypeID = AType.ArticleTypeID\n" +
                            "Where A.ArticleTypeID = 3 and ArticleStatus = 1  Order By PostTime DESC";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    String memberId = rs.getString("MemberID");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    int itemId = rs.getInt("ItemID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);
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
    
    // Lấy tất cả các bài loại "Tìm đồ" và loại đồ vật theo yêu cầu
    public ArrayList<Article> getAllArticlesFindByItemType(Item i) throws ClassNotFoundException, SQLException, Exception {
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID, A.ArticleTitle, A.ArticleContent, A.ImgURL, A.PostTime, A.ArticleStatus, A.WarningStatus, A.MemberID, A.ArticleTypeID, A.ItemID \n" +
                            "from Article A inner join ArticleType AType on A.ArticleTypeID = AType.ArticleTypeID\n" +
                            "				inner join ItemType I on I.ItemID = A.ItemID\n" +
                            "Where A.ArticleTypeID = 1 and A.ArticleStatus = 1 and A.ItemID = ?\n" +
                            "Order By PostTime DESC";
                stm = con.prepareStatement(sql);
                stm.setInt(1, i.getItemID());
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    String memberId = rs.getString("MemberID");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);
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
    // Lấy tất cả các bài loại "Trả đồ" và loại đồ vật theo yêu cầu
    public ArrayList<Article> getAllArticlesReturnByItemType(Item i) throws ClassNotFoundException, SQLException, Exception {
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID, A.ArticleTitle, A.ArticleContent, A.ImgURL, A.PostTime, A.ArticleStatus, A.WarningStatus, A.MemberID, A.ArticleTypeID, A.ItemID \n" +
                            "from Article A inner join ArticleType AType on A.ArticleTypeID = AType.ArticleTypeID\n" +
                            "				inner join ItemType I on I.ItemID = A.ItemID\n" +
                            "Where A.ArticleTypeID = 2 and A.ArticleStatus = 1 and A.ItemID = ?\n" +
                            "Order By PostTime DESC";
                stm = con.prepareStatement(sql);
                stm.setInt(1, i.getItemID());
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    String memberId = rs.getString("MemberID");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);
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
    // Lấy tất cả các bài loại "Thông báo" và loại đồ vật theo yêu cầu
    public ArrayList<Article> getAllArticlesNoticeByItemType(Item i) throws ClassNotFoundException, SQLException, Exception {

        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID, A.ArticleTitle, A.ArticleContent, A.ImgURL, A.PostTime, A.ArticleStatus, A.WarningStatus, A.MemberID, A.ArticleTypeID, A.ItemID \n" +
                            "from Article A inner join ArticleType AType on A.ArticleTypeID = AType.ArticleTypeID\n" +
                            "				inner join ItemType I on I.ItemID = A.ItemID\n" +
                            "Where A.ArticleTypeID = 3 and A.ArticleStatus = 1 and A.ItemID = ?\n" +
                            "Order By PostTime DESC";
                stm = con.prepareStatement(sql);
                stm.setInt(1, i.getItemID());
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    String memberId = rs.getString("MemberID");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);
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
    // Search tất cả các bài loại "Tìm đồ" theo từ khóa
    public ArrayList<Article> searchAllArticlesFindByName(String key) throws ClassNotFoundException, SQLException, Exception {
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID, A.ArticleTitle, A.ArticleContent, A.ImgURL, A.PostTime, A.ArticleStatus, A.WarningStatus, A.MemberID, A.ArticleTypeID, A.ItemID \n" +
                            "from Article A inner join ArticleType AType on A.ArticleTypeID = AType.ArticleTypeID\n" +                          
                            "Where A.ArticleTypeID = 1 and A.ArticleStatus =1 and A.ArticleTitle Like ?\n" +
                            "Order By A.ArticleID DESC";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%"+key+"%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    String memberId = rs.getString("MemberID");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    int itemId = rs.getInt("ItemID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus, warningStatus, i, m, a);
                    lb.add(art);
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
    // Search tất cả các bài loại "Trả đồ" theo từ khóa
    public ArrayList<Article> searchAllArticlesReturnByName(String key) throws ClassNotFoundException, SQLException, Exception {
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID, A.ArticleTitle, A.ArticleContent, A.ImgURL, A.PostTime, A.ArticleStatus, A.WarningStatus, A.MemberID, A.ArticleTypeID, A.ItemID \n" +
                            "from Article A inner join ArticleType AType on A.ArticleTypeID = AType.ArticleTypeID\n" +                          
                            "Where A.ArticleTypeID = 2 and A.ArticleStatus = 1 and A.ArticleTitle Like ?\n" +
                            "Order By A.ArticleTypeID DESC";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%"+key+"%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    String memberId = rs.getString("MemberID");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    int itemId = rs.getInt("ItemID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);
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
    // Search tất cả các bài loại "Notice" theo từ khóa
    public ArrayList<Article> searchAllArticlesNoticeByName(String key) throws ClassNotFoundException, SQLException, Exception {
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID, A.ArticleTitle, A.ArticleContent, A.ImgURL, A.PostTime, A.ArticleStatus, A.WarningStatus, A.MemberID, A.ArticleTypeID, A.ItemID \n" +
                            "from Article A inner join ArticleType AType on A.ArticleTypeID = AType.ArticleTypeID\n" +                          
                            "Where A.ArticleTypeID = 3 and A.ArticleStatus = 1 and A.ArticleTitle Like ?\n" +
                            "Order By A.ArticleID DESC";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%"+key+"%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    String memberId = rs.getString("MemberID");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    int itemId = rs.getInt("ItemID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);
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
    // Search all post find voi hashtag
    public ArrayList<Article> searchAllArticlesFindByHashtag(String hId) throws ClassNotFoundException, SQLException, Exception {
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID,A.ArticleTitle, A.ArticleContent, A.ImgURL, A.PostTime, A.ArticleStatus, A.WarningStatus, A.MemberID, A.ArticleTypeID, A.ItemID\n" +
                            "from Article A inner join ArticleHashtag AH on A.ArticleID = AH.ArticleID\n" +
                            "				inner join Hashtag H on AH.HashtagID = H.HashtagID\n" +
                            "where H.HashtagID = ? and A.ArticleTypeID = 1 and A.ArticleStatus not like -1\n" +
                            "Order By PostTime DESC";
                stm = con.prepareStatement(sql);
                stm.setString(1, hId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    String memberId = rs.getString("MemberID");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    int itemId = rs.getInt("ItemID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus, warningStatus, i, m, a);
                    lb.add(art);
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
    // Search all post find voi hashtag
    public ArrayList<Article> searchAllArticlesReturnByHashtag(String hId) throws ClassNotFoundException, SQLException, Exception {
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID,A.ArticleTitle, A.ArticleContent, A.ImgURL, A.PostTime, A.ArticleStatus, A.WarningStatus, A.MemberID, A.ArticleTypeID, A.ItemID\n" +
                            "from Article A inner join ArticleHashtag AH on A.ArticleID = AH.ArticleID\n" +
                            "				inner join Hashtag H on AH.HashtagID = H.HashtagID\n" +
                            "where H.HashtagID = ? and A.ArticleTypeID = 2 and A.ArticleStatus not like -1\n" +
                            "Order By PostTime DESC";
                stm = con.prepareStatement(sql);
                stm.setString(1, hId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    String memberId = rs.getString("MemberID");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    int itemId = rs.getInt("ItemID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);
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
    // Search all post find voi hashtag
    public ArrayList<Article> searchAllArticlesNoticeByHashtag(String hId) throws ClassNotFoundException, SQLException, Exception {
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID, A.ArticleTitle, A.ArticleContent, A.ImgURL, A.PostTime, A.ArticleStatus, A.WarningStatus, A.MemberID, A.ArticleTypeID, A.ItemID\n" +
                            "from Article A inner join ArticleHashtag AH on A.ArticleID = AH.ArticleID\n" +
                            "				inner join Hashtag H on AH.HashtagID = H.HashtagID\n" +
                            "where H.HashtagID = ? and A.ArticleTypeID = 3 and A.ArticleStatus not like -1\n" +
                            "Order By PostTime DESC";
                stm = con.prepareStatement(sql);
                stm.setString(1, hId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    String memberId = rs.getString("MemberID");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    int itemId = rs.getInt("ItemID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);
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
    // Lấy all bài viết tìm đồ mà member đã đăng
    public ArrayList<Article> getAllArticlesFindByMemberID(Member m) throws ClassNotFoundException, SQLException, Exception {
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID, A.ArticleTitle, A.ArticleContent, A.ImgURL, A.PostTime, A.ArticleStatus, A.WarningStatus, A.ArticleTypeID, A.ItemID \n" +
                            "from Article A inner join Member M on M.MemberID = A.MemberID\n" +
                            "Where A.ArticleTypeID = 1 and A.ArticleStatus not like -1 and M.MemberID Like ?";
                stm = con.prepareStatement(sql);
                stm.setString(1,m.getMemberID());
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");   
                    int warningStatus = rs.getInt("WarningStatus");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    int itemId = rs.getInt("ItemID");                    
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);                    
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
    // Lấy all bài viết mà member đã đăng
    public ArrayList<Article> getAllArticlesByMemberID(Member m) throws ClassNotFoundException, SQLException, Exception {
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID, A.ArticleTitle, A.ArticleContent, A.ImgURL, A.PostTime, A.ArticleStatus, A.WarningStatus, A.ArticleTypeID, A.ItemID \n" +
                            "from Article A inner join Member M on M.MemberID = A.MemberID\n" +
                            "Where A.ArticleStatus not like -1 and M.MemberID Like ?";
                stm = con.prepareStatement(sql);
                stm.setString(1,m.getMemberID());
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");  
                    int warningStatus = rs.getInt("WarningStatus");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    int itemId = rs.getInt("ItemID");                    
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);                    
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
    // Lấy all bài viết trả đồ mà member đã đăng
    public ArrayList<Article> getAllArticlesReturnByMemberID(Member m) throws ClassNotFoundException, SQLException, Exception {
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID, A.ArticleTitle, A.ArticleContent, A.ImgURL, A.PostTime, A.ArticleStatus, A.WarningStatus, A.ArticleTypeID, A.ItemID \n" +
                            "from Article A inner join Member M on M.MemberID = A.MemberID\n" +
                            "Where A.ArticleTypeID = 2 and A.ArticleStatus not like -1 and M.MemberID Like ?";
                stm = con.prepareStatement(sql);
                stm.setString(1,m.getMemberID());
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String title = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");                    
                    int articleStatus = rs.getInt("ArticleStatus");  
                    int warningStatus = rs.getInt("WarningStatus");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    int itemId = rs.getInt("ItemID");                    
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId, title, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);
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
    
    public int getNumberPage() throws Exception{
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String query = "Select count(*) from Article where ArticleTypeID = 1 and ArticleStatus = 1";
                stm = con.prepareStatement(query);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int total = rs.getInt(1);
                    int coutPage = 0;
                    coutPage = total / 12;
                    if (total % 12 != 0) {
                        coutPage++;
                    }
                    return coutPage;
                }
            }
        }finally{
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
        return 0;
    }

    public ArrayList<Article> getPaging(int index) throws Exception {
        ArrayList<Article> lb = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * from Article\n"
                        + "Where ArticleTypeID = 1 and ArticleStatus = 1\n"
                        + "order by PostTime DESC \n"
                        + "OFFSET ? ROWS\n"
                        + "FETCH FIRST 12 ROWS ONLY;";
                stm = con.prepareStatement(sql);       
                stm.setInt(1, (index - 1) * 12);
                rs = stm.executeQuery();
                lb = new ArrayList<>();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String articleTitle = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    String memberId = rs.getString("MemberID");
                    int itemId = rs.getInt("ItemID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId,articleTitle, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);
                }
            }
        } finally{
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
    public int getNumberPageReturn() throws Exception {
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String query = "Select count(*) from Article where ArticleTypeID = 2 and ArticleStatus = 1";
                stm = con.prepareStatement(query);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int total = rs.getInt(1);
                    int coutPage = 0;
                    coutPage = total / 12;
                    if (total % 12 != 0) {
                        coutPage++;
                    }
                    return coutPage;
                }
            }
        } finally{
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
        return 0;
    }

    public ArrayList<Article> getPagingReturn(int index) throws Exception{
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * from Article\n"
                        + "Where ArticleTypeID = 2  and ArticleStatus = 1\n "
                        + "order by PostTime DESC \n"
                        + "OFFSET ? ROWS\n"
                        + "FETCH FIRST 12 ROWS ONLY;";
                stm = con.prepareStatement(sql);
                stm.setInt(1, (index - 1) * 12);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String articleTitle = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    String memberId = rs.getString("MemberID");
                    int itemId = rs.getInt("ItemID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId,articleTitle, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);
                }
            }
        } finally{
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
    public int getNumberPageNotice() throws Exception{
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String query = "Select count(*) from Article where ArticleTypeID = 3 and ArticleStatus = 1";
                stm = con.prepareStatement(query);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int total = rs.getInt(1);
                    int coutPage = 0;
                    coutPage = total / 12;
                    if (total % 12 != 0) {
                        coutPage++;
                    }
                    return coutPage;
                }
            }
        }finally{
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
        return 0;
    }

    public ArrayList<Article> getPagingNotice(int index) throws SQLException, Exception {
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Select * from Article\n"
                        + "Where ArticleTypeID = 3 and ArticleStatus = 1 \n"
                        + "order by PostTime DESC \n"
                        + "OFFSET ? ROWS\n"
                        + "FETCH FIRST 12 ROWS ONLY;";
                stm = con.prepareStatement(sql);
                stm.setInt(1, (index - 1) * 12);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String articleTitle = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    String memberId = rs.getString("MemberID");
                    int itemId = rs.getInt("ItemID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId,articleTitle, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);
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
    public boolean flagArticle(int aId) throws Exception {
        boolean check = false;
        String sql = ("UPDATE Article SET WarningStatus = 1 Where ArticleID=?");
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setInt(1, aId);                           
                stm.executeUpdate();
                check = stm.executeUpdate() > 0;
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
        return check;
    }
     public boolean unFlagArticle(int aId) throws Exception {
        boolean check = false;
        String sql = ("UPDATE Article SET WarningStatus = 0 Where ArticleID=?");
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                stm = con.prepareStatement(sql);
                stm.setInt(1, aId);                           
                stm.executeUpdate();
                check = stm.executeUpdate() > 0;
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
        return check;
    }
    
    public int countAllPostsToday(int type) throws SQLException, Exception {
        int count=0;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select count(*) As total from Article A\n" +
                            "where DAY(A.PostTime) = DAY(GETDATE()) and A.ArticleTypeID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, type);
                rs = stm.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("total");                   
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
        return count;
    }
    public int countAllPosts(int type) throws SQLException, Exception {
        int count=0;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select count(*) As total from Article A\n" +
                              "where A.ArticleTypeID = ? and ArticleStatus = 1";
                stm = con.prepareStatement(sql);
                stm.setInt(1, type);
                rs = stm.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("total");                   
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
        return count;
    }
     public int countAllPostsOfAMemberByType(String mId, int typeID) throws SQLException, Exception {
        int count=0;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select count(A.ArticleID) as total\n" +
                            "from Member M inner join Article A on m.MemberID = a.MemberID\n" +
                            "where M.MemberID = ? and A.ArticleTypeID = ?\n" +
                            "group by A.MemberID";
                stm = con.prepareStatement(sql);
                stm.setString(1, mId);
                stm.setInt(2,typeID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("total");                   
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
        return count;
    }
     public int countAllPostsOfAMember(String mId) throws SQLException, Exception {
        int count=0;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select count(A.ArticleID) as total\n" +
                            "from Member M inner join Article A on m.MemberID = a.MemberID\n" +
                            "where M.MemberID = ?\n" +
                            "group by A.MemberID";
                stm = con.prepareStatement(sql);
                stm.setString(1, mId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("total");                   
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
        return count;
    }
     public ArrayList<Article> getAllPostWarningOfMember(String mId) throws SQLException, Exception {
        ArrayList<Article> lb = new ArrayList<>();
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "select A.ArticleID,A.ArticleContent,A.ArticleTitle,A.ArticleTypeID, A.ArticleStatus, A.ImgURL, A.ItemID, A.PostTime, A.WarningStatus, A.MemberID\n" +
"from Member M inner join Article A on m.MemberID = a.MemberID\n" +
"where M.MemberID = ? and A.WarningStatus = 1\n" +
"group by A.ArticleID,A.ArticleContent,A.ArticleTitle,A.ArticleTypeID, A.ArticleStatus, A.ImgURL, A.ItemID, A.PostTime, A.WarningStatus, A.MemberID";
                stm = con.prepareStatement(sql);
                stm.setString(1, mId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int articleId = rs.getInt("ArticleID");
                    String articleTitle = rs.getString("ArticleTitle");
                    String articleContent = rs.getString("ArticleContent");
                    String articleURL = rs.getString("ImgURL");
                    String articleTime = rs.getString("PostTime");
                    int articleStatus = rs.getInt("ArticleStatus");
                    int warningStatus = rs.getInt("WarningStatus");
                    int articleTypeId = rs.getInt("ArticleTypeID");
                    String memberId = rs.getString("MemberID");
                    int itemId = rs.getInt("ItemID");
                    MemberDAO mdao = new MemberDAO();
                    Member m = mdao.find(memberId);
                    ItemTypeDAO idao = new ItemTypeDAO();
                    Item i = idao.getItemByID(itemId);
                    ArticleTypeDAO adao = new ArticleTypeDAO();
                    ArticleType a = adao.getArticleTypeByID(articleTypeId);
                    Article art = new Article(articleId,articleTitle, articleContent, articleURL, articleTime, articleStatus,warningStatus, i, m, a);
                    lb.add(art);
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
}
