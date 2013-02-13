/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.web.bean;

import br.com.workflow.model.dao.impl.UsuarioDao;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 7, 2013
 * @version 0.1
 */
@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean extends SystemBeanBase {

    private String login;
    private String senha;
    private boolean lembreme;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isLembreme() {
        return lembreme;
    }

    public void setLembreme(boolean lembreme) {
        this.lembreme = lembreme;
    }

    public String login() {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(getLogin(), getSenha());
            token.setRememberMe(isLembreme());
            try {
                currentUser.login(token);
                currentUser.getSession().setAttribute("usuario", new UsuarioDao().login(getLogin()));
                return "index";
            } catch (UnknownAccountException uae) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário incorreto!", ""));
            } catch (IncorrectCredentialsException ice) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha Incorreta!", ""));
            } catch (LockedAccountException lae) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Conta bloqueada momentaneamente.", ""));
            }
        }
        return null;
    }
}
