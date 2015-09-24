/**
 * Copyright 2014-2015 IIDOOO All rights reserved.
 * Author(e-mail)    wangyixian@iidooo.com
 * Creation date     2015-03-27
 */
package com.iidooo.core.action.security;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.iidooo.core.action.common.BaseAction;
import com.iidooo.core.constant.SessionConstant;
import com.iidooo.core.dto.extend.SecurityRoleDto;
import com.iidooo.core.dto.extend.SecurityUserDto;
import com.iidooo.core.service.LoginService;
import com.opensymphony.xwork2.ActionContext;

/**
 * The Action of execute login operation.
 * 
 * @author Ethan
 *
 */
public class LoginAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(LoginAction.class);

    @Autowired
    private LoginService loginService;

    private String loginID;

    private String password;

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        try {

            SecurityUserDto user = loginService.login(loginID, password);
            if (user == null) {
                addActionError(getText("MSG_LOGIN_FAILED"));
                return INPUT;
            }           

            HttpServletResponse response = ServletActionContext.getResponse();
            HttpServletRequest request = ServletActionContext.getRequest();

            // Add the login ID into the cookie for SSO
            // Set the Login ID into the cookie
            Cookie cookieLoginID = new Cookie(SessionConstant.LOGIN_ID, user.getLoginID());
            cookieLoginID.setPath("/");
            // - value mean the cookie will be delete when browse closed.
            cookieLoginID.setMaxAge(-1);
            response.addCookie(cookieLoginID);

            // Set the User ID into the cookie
            Cookie cookieUserID = new Cookie(SessionConstant.USER_ID, user.getUserID().toString());
            cookieUserID.setPath("/");
            // - value mean the cookie will be delete when browse closed.
            cookieUserID.setMaxAge(-1);
            response.addCookie(cookieUserID);

            // Set the user name into the cookie
            String userName = user.getUserName();
            Cookie cookieUserName = new Cookie(SessionConstant.USER_NAME, URLEncoder.encode(userName, SessionConstant.COOKIE_ENCODING_UTF8));
            cookieUserName.setPath("/");
            cookieUserName.setMaxAge(-1);
            response.addCookie(cookieUserName);

            // Set the login user id into the session.
            Map<String, Object> sessionMap = ActionContext.getContext().getSession();
            sessionMap.put(SessionConstant.LOGIN_USER, user);

            // Put the login user's role list into the session.
            List<SecurityRoleDto> roleList = loginService.getUserRoleList(user.getUserID());
            sessionMap.put(SessionConstant.LOGIN_ROLE_LIST, roleList);
            
            // Redirect to the URL of saved form Filter
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(SessionConstant.ACCESS_URL)) {
                        response.sendRedirect(cookie.getValue());
                        break;
                    }
                }
            }

            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return ERROR;
        }
    }

    public void validateLogin() {
        try {
            // The login id and password is required.
            if (loginID == null || loginID.isEmpty()) {
                addActionError(this.getText("MSG_FIELD_REQUIRED", new String[] { getText("LABEL_LOGIN_ID") }));
            }

            if (password == null || password.isEmpty()) {
                addActionError(this.getText("MSG_FIELD_REQUIRED", new String[] { getText("LABEL_LOGIN_PASSWORD") }));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    public String logout() {
        try {
            // When logout the system, should delete the session.
            ActionContext actionContext = ActionContext.getContext();
            actionContext.getSession().clear();

            // When logout the system, should delete the cookies.
            HttpServletResponse response = ServletActionContext.getResponse();
            HttpServletRequest request = ServletActionContext.getRequest();
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                // Set the 0 mean delete the cookies.
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return ERROR;
        }
    }
}
