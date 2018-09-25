/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.servlet.http.HttpServletRequest;

import model.Redirect;

public class RedirectLogic{

    public String redirect(HttpServletRequest req, String controller, String module, String program, String actions){
        
        Redirect redirect = new Redirect();
        redirect.setController(controller);
        redirect.setModule(module);
        redirect.setProgram(program);
        redirect.setActions(actions);
        
        req.setAttribute("redirectParams", redirect);
        return "/WEB-INF/jsp/fnd/redirect.jsp";     
    }
    
}
