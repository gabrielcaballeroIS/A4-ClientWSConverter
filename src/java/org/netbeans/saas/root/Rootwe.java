/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.saas.root;

import java.io.IOException;
import org.netbeans.saas.RestConnection;
import org.netbeans.saas.RestResponse;

/**
 * Rootwe Service
 *
 * @author entrar
 */
public class Rootwe {

    /**
     * Creates a new instance of Rootwe
     */
    public Rootwe() {
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Throwable th) {
        }
    }

    /**
     *
     * @param decimal
     * @return an instance of RestResponse
     */
    public static RestResponse decimal(Integer decimal) throws IOException {
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{{"decimal", decimal.toString()}};
        RestConnection conn = new RestConnection("http://ws.docencia.ces.siani.es/a05/WSConverter/webresources/generic", pathParams, queryParams);
        sleep(1000);
        return conn.get(null);
    }
}
