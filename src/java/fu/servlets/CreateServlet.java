/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fu.servlets;

import fu.daos.ArticleDAO;
import fu.daos.ArticleHashtagDAO;
import fu.daos.ArticleTypeDAO;
import fu.daos.HashtagDAO;
import fu.daos.ItemTypeDAO;
import fu.entities.Article;
import fu.entities.ArticleType;
import fu.entities.Hashtag;
import fu.entities.Item;
import fu.entities.Member;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.annotation.MultipartConfig;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "CreateServlet", urlPatterns = {"/CreateServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class CreateServlet extends HttpServlet {

    private static final String SUCCESS = "ListPostServlet";
    private static final String ADMIN_PAGE = "AdminListServlet";
    private static final String ERROR = "error.jsp";
    private static final String INVALID = "CreateFormServlet";
    private static final String UPLOAD_DIR = "images";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = ERROR;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                request.setAttribute("errormessage", "Please login!");
                request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
            }
            if (session.getAttribute("userdata") != null) {
                Member memberLogin = (Member) session.getAttribute("userdata");
                boolean valid = true;
                String titleError = "";
                String contentError = "";
                String errorURL = "";
                String newId;
                String textURL = request.getParameter("articleURL");
                ArticleDAO aDao = new ArticleDAO();
                // Xử lý title bài viết    
                String titlePost = request.getParameter("txtTitle");
                if (titlePost.trim().isEmpty() || titlePost.trim().length() < 10 || titlePost.trim().length() > 50) {
                    titleError = "Title must be at least 10 and at most 50 characters!";
                    valid = false;
                }
                // Xử lý nội dung bài viết 
                String content = request.getParameter("txtContent");
                if (content.trim().isEmpty() || content.trim().length() < 20 || content.trim().length() > 4000) {
                    contentError = "Content must be at least 20 and at most 4000 characters!";
                    valid = false;
                }

                // Xử lý loại đồ vật của bài viết
                String itemId = request.getParameter("txtItem");
                Item i = null;
                if (itemId != null) {
                    ItemTypeDAO iDao = new ItemTypeDAO();
                    i = iDao.getItemByID(Integer.parseInt(itemId));
                }

                // Xử lý loại bài viết
                String postTypeId = request.getParameter("txtArticleType");
                ArticleTypeDAO aTDao = new ArticleTypeDAO();
                ArticleType at = aTDao.getArticleTypeByID(Integer.parseInt(postTypeId));

                //Xử lý ảnh của bài viết
                Part filePart = request.getPart("photo");
                String postURL = getFileName(filePart);
                if (!postURL.equals("")) {
                    if (postURL.length() > 50) {
                        errorURL = "URL's length must be at most 50";
                        valid = false;
                    }
                    if (!(postURL.endsWith(".png") || postURL.endsWith(".jpg"))) {
                        errorURL = "Image must be a PNG or JPG file";
                        valid = false;
                    }
                }
                // Xử lý hashtag 
                String regex = "#\\w*";
                Pattern p = Pattern.compile(regex);
                Matcher matcher = p.matcher(content);
                while (matcher.find()) {
                    String hName = matcher.group();
                    if (hName.trim().length() > 21) {
                        contentError = "Hashtag must be from 1 to 21 characters!";
                        valid = false;
                    }
                }

                if (valid) {
                    do {
                        newId = "";
                        Random generator = new Random();
                        for (int x = 0; x < 10; x++) {
                            int a = generator.nextInt() % 10;
                            if (a < 0) {
                                a = -a;
                            }
                            newId = newId.concat(Integer.toString(a));
                        }
                    } while (aDao.find(newId) != null);
                    // Xử lý ảnh để thêm vô DB
                    String articleURl;
                    if (postURL.equals("")) {
                        if (textURL != null && !textURL.equals("")) {
                            articleURl = textURL;
                        } else {
                            articleURl = null;
                        }
                    } else {
                        uploadFileToBuild(request);
                        articleURl = uploadFile(request);
                    }
                    // uploadFileToBuild(request);
                    Article a = new Article(newId, titlePost.trim(), content.trim(), articleURl, LocalDateTime.now().toString(), 1, i, memberLogin, at);
                    // if (aDao.createNewArticle(a)) {
                    String partern = ".*#.*";
                    //xử lý hashtag
                            //String regex = "#\\w*";
                            p = Pattern.compile(regex);
                            matcher = p.matcher(content);
                    HashtagDAO hDao = new HashtagDAO();
                    ArticleHashtagDAO ahDao = new ArticleHashtagDAO();
                    // Tạo 1 mảng lưu các hashtag
                    ArrayList<Hashtag> lstHashtag = new ArrayList<>();
                    // Vòng lặp lấy ra tất cả hashtag trong bài viết
                    while (matcher.find()) {
                        String hName = matcher.group();
                        //Kiểm tra xem tên hashtag đã tồn tại chưa

                        if (hDao.getHashtagByName(hName) != null) {
                            a.setArticleContent(a.getArticleContent().replace(matcher.group(), ""));
                            Hashtag hashtag = hDao.getHashtagByName(hName);
                            lstHashtag.add(hashtag);
                        } else if (hDao.getHashtagByName(hName) == null) {
                            //Tạo id mới cho Hashtag
                            String hId;
                            do {
                                hId = "";
                                Random generator2 = new Random();
                                for (int x = 0; x < 10; x++) {
                                    int b = generator2.nextInt() % 10;
                                    if (b < 0) {
                                        b = -b;
                                    }
                                    hId = hId.concat(Integer.toString(b));
                                }

                            } while (hDao.getHashtagById(hId) != null); //Ktra để ko bị trùng id
                            //Thêm mới hashtag zo DB
                            Hashtag hashtag = new Hashtag(hId, hName);
                            a.setArticleContent(a.getArticleContent().replace(matcher.group(), ""));
                            hDao.addNewHashtag(hashtag);
                            lstHashtag.add(hashtag);
                        }

                        //System.out.println(matcher.group());                       
                    }
                    //Tạo bài viết và tạo lien ket cho hashtag và bài viết
                    aDao.createNewArticle(a);
                    for (Hashtag hashtag : lstHashtag) {
                        ahDao.addNewArticleHashtag(a, hashtag);
                    }
                    // }
                    if (memberLogin.getMemberRole() == 1) {
                        url = SUCCESS;
                    } else if (memberLogin.getMemberRole() == 0) {
                        url = ADMIN_PAGE;
                    }
//                    } else {
//                        request.setAttribute("errMessage", "Add failed");
//                    }
                } else {
                    url = INVALID;
                    request.setAttribute("titlePost", titlePost);
                    request.setAttribute("titleError", titleError);
                    request.setAttribute("content", content);
                    request.setAttribute("contentError", contentError);
                    request.setAttribute("errorURL", errorURL);
                    request.setAttribute("postURL", postURL);
                    if (itemId != null) {
                        request.setAttribute("itemId", Integer.parseInt(itemId));
                    }
                    request.setAttribute("postTypeId", Integer.parseInt(postTypeId));
                }
            } else {
                request.setAttribute("errormessage", "Please login!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //System.out.println(url);
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

    //hàm này để lưu ảnh vào folder images
    private String uploadFile(HttpServletRequest request) throws IOException, ServletException {
        String fileName = "";
        try {
            Part filePart = request.getPart("photo");
            fileName = (String) getFileName(filePart);

            String applicationPath = request.getServletContext().getRealPath("").replace("build\\", "");
            String basePath = applicationPath + File.separator + UPLOAD_DIR + File.separator;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File outputFilePath = new File(basePath + fileName);
                inputStream = filePart.getInputStream();
                outputStream = new FileOutputStream(outputFilePath);
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (Exception e) {
                e.printStackTrace();
                fileName = "";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            fileName = "";
        }
        return fileName;
    }

    //hàm này để lưu ảnh vào folder images trong build để khi hoàn thành việc thêm sách,
    //ảnh sách đó sẽ có mặt ngay lập tức để hiển thị trên library
    private void uploadFileToBuild(HttpServletRequest request) throws IOException, ServletException {
        String fileName = "";
        try {
            Part filePart = request.getPart("photo");
            fileName = (String) getFileName(filePart);

            String applicationPath = request.getServletContext().getRealPath("");
            String basePath = applicationPath + File.separator + UPLOAD_DIR + File.separator;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File outputFilePath = new File(basePath + fileName);
                inputStream = filePart.getInputStream();
                outputStream = new FileOutputStream(outputFilePath);
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (Exception e) {
                e.printStackTrace();
                fileName = "";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            fileName = "";
        }
    }

    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
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
