/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aaaws;

import java.util.List;
import javax.jws.WebService;

@WebService
public interface AAAWS {
    List<String> getClients();
}