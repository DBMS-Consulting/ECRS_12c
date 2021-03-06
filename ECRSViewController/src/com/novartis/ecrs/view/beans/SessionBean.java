package com.novartis.ecrs.view.beans;

import java.io.IOException;

import java.security.Principal;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.naming.InitialContext;

import javax.naming.NamingException;

import javax.security.auth.Subject;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import oracle.adf.share.ADFContext;
import oracle.adf.share.security.SecurityContext;

import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.DBTransactionImpl;

import org.apache.log4j.Logger;

import weblogic.security.Security;
import weblogic.security.URLCallbackHandler;
import weblogic.security.services.Authentication;
import weblogic.security.spi.WLSGroup;
import weblogic.security.spi.WLSUser;


public class SessionBean {
    public SessionBean() {
        super();
    }
    
    private String userRole;
    private String username;
    private String password;
    private String remoteAddress = "";
    private String fullName;
    private boolean freezeMedDRAFlag = false;
    private final String dbUrlString = "jdbc/EcrsDS";
    public static final Logger logger = Logger.getLogger(SessionBean.class);
    
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        if(userRole == null){
            SecurityContext secCtx =
                ADFContext.getCurrent().getSecurityContext();
            String[] roles = secCtx.getUserRoles();
            if(roles != null){
                for(String role : roles){
                    if("CRS_ADMIN".equalsIgnoreCase(role) || "CRS_BSL".equalsIgnoreCase(role) || "CRS_MQM".equalsIgnoreCase(role) || "CRS_TASL".equalsIgnoreCase(role)){
                        userRole = role;
                        logger.info("Logged in user role - "+userRole);
                        break;
                    }
                }
            }
        }
        return userRole;
    }
    
    
    public String doLogin() {
        
        if (username == null || username.length() < 1) return null;
        if (password == null || password.length() < 1) return null;
        
        Subject subject = null;
        username = username.trim();
        password = password.trim();
        String un = username;
        byte[] pw = password.getBytes();
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)ctx.getExternalContext().getRequest();
        remoteAddress = request.getRemoteAddr();
        SecurityContext s;

       // accessLogger.info("LOGIN ATTEPT FOR USER: " + un + " FROM: " + remoteAddress);

        try {
            subject = Authentication.login(new URLCallbackHandler(un, pw));
            weblogic.servlet.security.ServletAuthentication.runAs(subject, request);

            String loginUrl = "/adfAuthentication?success_url=/faces/Home";
            HttpServletResponse response = (HttpServletResponse)ctx.getExternalContext().getResponse();
            sendForward(request, response, loginUrl);
            
//            this.passwordExpiryDays = getPasswordExpiry();
//            this.showExpiryPopup =
//                    (passwordExpiryDays <= CSMQBean.SHOW_PASSWORD_WARNING_CUTOFF && passwordExpiryDays !=
//                     CSMQBean.NULL_PASSWORD_EXPIRATION); // only show the popup if it is less than the cutoff
//
//            if (showExpiryPopup)
//                accessLogger.info("PASSWORD WILL EXPIRE IN: " + passwordExpiryDays + " DAYS");
            
            ADFContext adfContext = ADFContext.getCurrent();
            SecurityContext securityCtx = adfContext.getSecurityContext();

            Subject subject2 = Security.getCurrentSubject();

            StringBuffer groups = new StringBuffer();
            String user = null;
            boolean first = true;

            for (Principal p : subject2.getPrincipals()) {
                if (p instanceof WLSGroup) {
                    if (first) {
                        first = false;
                    } else {
                        groups.append(", ");
                    }
                    groups.append(p.getName());
                } else if (p instanceof WLSUser) {
                    user = p.getName();
                }
            }

//            setName();
//            loggedIn = true;
//            accessLogger.info("ROLES: " + groups);

//            caller = "[" + remoteAddress + ":" + username + "] ";
        } catch (FailedLoginException fle) {
//            accessLogger.info("FAILED LOGIN: " + un);
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect Username or Password", "An incorrect Username or Password was specified");
            ctx.addMessage(null, msg);
            return null;
        } catch (AccountLockedException ale) {
//            accessLogger.info("ACCOUNT LOCKED: " + un);
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account locked", "This account is locked.  Please contact eCRS support to reset your password.");
            ctx.addMessage(null, msg);
            return null;
        } catch (AccountExpiredException aee) {
//            accessLogger.info("PASSWORD EXPIRED: " + un);
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account locked", "This password has expired.  Please contact eCRS support to reset your password.");
            ctx.addMessage(null, msg);
            return null;
        } catch (LoginException le) {
            le.printStackTrace();
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failure", "Unable to login.  Please contact eCRS support for help.");
            ctx.addMessage(null, msg);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failure", "Unable to login.  Please contact eCRS support for help.");
            ctx.addMessage(null, msg);
            return null;
        }
        loadFreezeMedDRAFlagFromDB();
              
        return null;
    }
    
    private void sendForward(HttpServletRequest request, HttpServletResponse response, String forwardUrl) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardUrl);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException se) {
 //           reportUnexpectedLoginError("ServletException", se);
        } catch (IOException ie) {
//            reportUnexpectedLoginError("IOException", ie);
        }
        ctx.responseComplete();
    }
    
    public void logout(ActionEvent ae ) {

        FacesContext fctx = FacesContext.getCurrentInstance();

        ExternalContext ectx = fctx.getExternalContext();

//        accessLogger.info("LOGING OFF USER: " + ectx.getRemoteUser());

        String url = ectx.getRequestContextPath() + "/adfAuthentication?logout=true&end_url=/faces/Home";
        try {
            ectx.redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fctx.responseComplete();

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
    private void loadFreezeMedDRAFlagFromDB(){
        logger.info("In SessionBean ..getFreezeMedDRAFlagFromDB");
        String freezeMedDRA = null;
        Connection dbConnection = getConnection();
        if (null != dbConnection){
            String query = "SELECT crs_ui_tms_utils.select_crs_freeze_flag as freeze_flag FROM DUAL";
            //for the selected CRSID
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                pstmt = dbConnection.prepareStatement(query);
                rs = pstmt.executeQuery();
                if (null != rs && rs.next()){
                    freezeMedDRA = rs.getString("freeze_flag");
                    logger.info("freezeFlag==" + freezeMedDRA);
                }
            } catch (SQLException e){
                logger.error("Error while getting MedDRA Freeze Flag", e);
            } finally{
                try{
                    rs.close();
                    pstmt.close();
                    dbConnection.close();
                }catch (SQLException e){
                    logger.error("Error while closing pstmt", e);
                }
            }
        }
        logger.info("freezeFlag == >" + freezeMedDRA);
        if (null != freezeMedDRA && "Y".equalsIgnoreCase(freezeMedDRA)){
            freezeMedDRAFlag = true;
        }
    }

    public boolean isFreezeMedDRAFlag() {
        
        return freezeMedDRAFlag;
    }
    
    private Connection getConnection(){
        Connection dbConnection = null;
        if (null != dbConnection){
            return dbConnection;
        }else {
            try {
                InitialContext initialContext = new InitialContext();
                DataSource ds = (DataSource)initialContext.lookup(dbUrlString);
                dbConnection = ds.getConnection();
            } catch (NamingException e) {
                        e.printStackTrace();
            } catch (SQLException e) {
                        e.printStackTrace();
            }
        }
        return dbConnection;
    }

    public void setFreezeMedDRAFlag(boolean freezeMedDRAFlag) {
        this.freezeMedDRAFlag = freezeMedDRAFlag;
    }
}
