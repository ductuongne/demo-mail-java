package murach.controller;

import java.io.IOException;

import jakarta.mail.MessagingException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import murach.dao.UserDAO;
import murach.dao.UserDAOImpl;
import murach.exception.DAOException;
import murach.model.User;
import murach.util.MailUtilGmail;

@WebServlet("/emailList")
public class EmailListServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "join"; // hành động mặc định
        }

        String url = "/index.jsp";

        try {
            if (action.equals("join")) {
                url = "/index.jsp";
            } else if (action.equals("add")) {
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String email = request.getParameter("email");

                User user = new User(email, firstName, lastName);
                request.setAttribute("user", user);

                if (userDAO.emailExists(email)) {
                    String message = "Địa chỉ email này đã tồn tại. Vui lòng nhập một email khác.";
                    request.setAttribute("message", message);
                    url = "/index.jsp";
                } else {
                    userDAO.insert(user);
                    request.setAttribute("message", "");
                    url = "/thanks.jsp";

                    // Gửi email chào mừng sau khi thêm thành công
                    String to = email;
                    String from = "email_list@murach.com";
                    String subject = "Welcome to our email list";
                    String body = "Dear " + firstName + ",\n\n"
                            + "Thanks for joining our email list. "
                            + "We'll make sure to send you announcements about new products and promotions.\n"
                            + "Have a great day and thanks again!\n\n"
                            + "Kelly Slivkoff\n"
                            + "Mike Murach & Associates";
                    boolean isBodyHTML = false;

                    try {
                        MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
                    } catch (MessagingException e) {
                        String errorMessage = "ERROR: Unable to send email. Check Tomcat logs for details.<br>"
                                + "NOTE: You may need to configure your system as described in chapter 14.<br>"
                                + "ERROR MESSAGE: " + e.getMessage();
                        request.setAttribute("errorMessage", errorMessage);
                        this.log("Unable to send email.\n"
                                + "Here is the email you tried to send:\n"
                                + "=====================================\n"
                                + "TO: " + email + "\n"
                                + "FROM: " + from + "\n"
                                + "SUBJECT: " + subject + "\n\n"
                                + body + "\n\n");
                    }
                }
            } else {
                url = "/index.jsp";
            }
        } catch (DAOException e) {
            request.setAttribute("message", "Lỗi database: " + e.getMessage());
            this.log("Database error while processing email registration", e);
            url = "/index.jsp";
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}