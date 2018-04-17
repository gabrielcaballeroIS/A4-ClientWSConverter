package clientconverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author entrar
 */
@WebServlet(name = "ClientWSConverter", urlPatterns = {"/ClientWSConverter"})
public class ClientWSConverter extends HttpServlet {

    @WebServiceRef(wsdlLocation = "http://ws.docencia.ces.siani.es/a05/WSFactorial/WSFactorial?wsdl")
    private clientconverter.WSFactorial_Service service;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        main(request, response);
    }

    private void main(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("submitCalculo") != null) {
            NJC njc = new NJC();
            String decimal = request.getParameter("decimalBinario");
            int numFactorial = Integer.parseInt(request.getParameter("decimalFactorial"));
            int dec = Integer.parseInt(decimal);

            if (decimal != null) {
                response.getWriter().println("WADL: " + decimal + " to binary: " + njc.decimal(String.class, dec+""));
                response.getWriter().println("URL:  " + decimal + " to binary: " + getBinaryUrlResult(dec));
            }

            if (request.getParameter("decimalFactorial") != null) {
                response.getWriter().println("WSDL: " + numFactorial + " factorial: " + factorial(numFactorial));
            }

            njc.close();
        }
    }

    private String getBinaryUrlResult(int decimal) {
        try {
            URL url = new URL("http://ws.docencia.ces.siani.es/a05/WSConverter/webresources/generic?decimal=" + decimal);
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine, result = "";
            while ((inputLine = br.readLine()) != null) {
                result += inputLine;
            }
            br.close();
            return result;
        } catch (MalformedURLException ex) {
            Logger.getLogger(ClientWSConverter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientWSConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private int factorial(int number) {
        clientconverter.WSFactorial port = service.getWSFactorialPort();
        return port.factorial(number);
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
