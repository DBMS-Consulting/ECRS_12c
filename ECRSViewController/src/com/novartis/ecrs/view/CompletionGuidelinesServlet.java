package com.novartis.ecrs.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

public class CompletionGuidelinesServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String filePath = "C:\\Users\\DileepKumar\\Desktop\\Donna\\DocumentsToView\\eCRS CG 01 CREATE CRS PAGE.pdf";
        String documentType = "";

        OutputStream os = response.getOutputStream();
        Connection conn = null;
        try {
            documentType = request.getParameter("documentType");
           // System.out.println("----documentType---" + documentType);
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/EcrsDS");
            conn = ds.getConnection();
            PreparedStatement ps;
            ps =
                conn.prepareStatement("SELECT PROP_VALUE FROM CRS_PROPERTIES where domain = 'GENERAL' AND PROP_NAME = 'COMPLETION_GUIDELINES'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String filePath = rs.getString("PROP_VALUE");
                filePath = filePath.concat(documentType);
               // System.out.println("--------filePath--------" + filePath);
                response.setContentType("application/pdf");
                if (filePath != null) {
                    File filed = new File(filePath);
                    FileInputStream bis = new FileInputStream(filed);
                    int b;
                    byte[] buffer = new byte[10240];
                    while ((b = bis.read(buffer, 0, 10240)) != -1) {
                        os.write(buffer, 0, b);
                    }
                    bis.close();
                }

                os.close();
            }
        } catch (NamingException e) {

        } catch (SQLException e) {

        } catch (Exception e) {
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        }
    }
}
