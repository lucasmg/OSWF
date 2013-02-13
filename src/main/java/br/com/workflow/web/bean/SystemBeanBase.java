/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.web.bean;

import br.com.workflow.model.entities.Usuario;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 8, 2013
 * @version 0.1
 */
public class SystemBeanBase {

    private Usuario usuario;

    public Usuario getUsuario() {
        if (this.usuario == null && SecurityUtils.getSubject().isAuthenticated()) {
            this.usuario = (Usuario) SecurityUtils.getSubject().getSession().getAttribute("usuario");
        } else if (!SecurityUtils.getSubject().isAuthenticated()) {
            return null;
        }
        return this.usuario;
    }

    public String logoff() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            currentUser.logout();
        }
        return "login";
    }
}
