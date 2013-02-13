/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 7, 2013
 * @version 0.1
 */
public class TestAuthentication {

    public static void main(String[] args) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityUtils.setSecurityManager(factory.getInstance());
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute("key", "value");
        String value = (String) session.getAttribute("ksey");
        if (value != null && value.equals("value")) {
            System.out.println("Session key:" + value);
        }
        if (!currentUser.isAuthenticated()) {
            // se nao estiver entao inicia o login
            UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");
            token.setRememberMe(true);

            try {
                ////////////////////////////////////////////////////////////////////////////////////////////////
                // é aqui que toda o processo de autenticacao e utilizacao do realm personalizado entra em acao.
                currentUser.login(token);
                System.out.println(currentUser.getPrincipal());

                // verificando roles
                if (currentUser.hasRole("ADMIN")) {
                    System.out.println("you are ADMIN");
                } else {
                    System.out.println("you aren't admin");
                }

                // verificando permissoes
                if (currentUser.isPermitted("lightsaber:weild")) {
                    System.out.println("You may use a lightsaber ring.  Use it wisely.");
                } else {
                    System.out.println("Sorry, lightsaber rings are for schwartz masters only.");
                }

                currentUser.logout();

                // quando um usuario nao consegue logar na aplicacao, diferentes exceptions podem ser lancadas
                // conforme abaixo. Há exceptions para quando o usuario nao está cadastrado, exceptions para senha inválida e etc.
            } catch (UnknownAccountException uae) {
                System.out.println("username isn't in the system");
            } catch (IncorrectCredentialsException ice) {
                System.out.println("password didn't match");
            } catch (LockedAccountException lae) {
                System.out.println("account for that username is locked");
            }

        }

    }
}
