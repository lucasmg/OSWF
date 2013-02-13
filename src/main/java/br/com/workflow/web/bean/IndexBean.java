/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.web.bean;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 7, 2013
 * @version 0.1
 */
@ManagedBean(name = "indexBean")
@RequestScoped
public class IndexBean extends SystemBeanBase implements Serializable {

    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public void setSubject(Subject subject) {
    }

    public boolean isCriaOC() {
        return getSubject().hasRole("CRIA_OC");
    }
}
