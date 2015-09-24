package com.iidooo.core.action.common;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.iidooo.core.dto.PageDto;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * The abstract of common base action
 * 
 * @author wangyixian
 * 
 */
public abstract class BaseAction extends ActionSupport {

    private static final Logger logger = Logger.getLogger(BaseAction.class);

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     * The action name
     */
    private String actionName;

    /**
     * The action URL, contain the parameters
     */
    private String actionUrl;

    private PageDto page;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public PageDto getPage() {
        return page;
    }

    public void setPage(PageDto page) {
        this.page = page;
    }

    /**
     * Get the session
     * 
     * @return Map<String, Object> session
     */
    public Map<String, Object> getSession() {
        logger.debug("The begin of the method getSession");

        ActionContext ac = ActionContext.getContext();
        Map<String, Object> session = ac.getSession();

        logger.debug("The end of the method getSession");
        return session;
    }

    public Map<String, Object> getApplication() {
        try {
            ActionContext ac = ActionContext.getContext();
            Map<String, Object> application = ac.getApplication();
            return application;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    public String getRequestParameter(String key) {
        try {
            HttpServletRequest request = this.getRequest();
            String value = request.getParameter(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    public String getRequestMethod() {
        try {
            HttpServletRequest request = this.getRequest();
            String value = request.getMethod();
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    /**
     * Get the object from session by key
     * 
     * @param key The key to get the session object
     * @return The object gotten.
     */
    public Object getSessionValue(String key) {
        try {
            Map<String, Object> session = this.getSession();
            Object value = session.get(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    /**
     * Set the value into the session with a key.
     * 
     * @param key This key is as the session map key.
     * @param value This value will be set into the session with the key.
     */
    public void setSessionValue(String key, Object value) {
        try {
            ActionContext ac = ActionContext.getContext();
            Map<String, Object> session = ac.getSession();
            session.put(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    public Object getApplicationValue(String key) {
        try {
            ActionContext ac = ActionContext.getContext();
            Map<String, Object> application = ac.getApplication();
            Object value = application.get(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    /**
     * Get the HttpServletRequest
     * 
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ServletActionContext.getRequest();
        return request;
    }

    /**
     * Get the HttpServletResponse
     * 
     * @return HttpServletResponse
     */
    public HttpServletResponse getResponse() {
        HttpServletResponse response = ServletActionContext.getResponse();
        return response;
    }

    public ServletContext getServletContext() {
        ServletContext servletContext = ServletActionContext.getServletContext();
        return servletContext;
    }
}
