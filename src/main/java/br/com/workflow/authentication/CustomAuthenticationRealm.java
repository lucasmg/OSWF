/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.authentication;

import br.com.workflow.model.dao.impl.UsuarioDao;
import br.com.workflow.model.entities.Realm;
import br.com.workflow.model.entities.Usuario;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 7, 2013
 * @version 0.1
 */
public class CustomAuthenticationRealm extends AuthorizingRealm {

    private SimpleAccount getAccount(String userName) {
        return getAccount(userName, false);
    }

    private SimpleAccount getAccount(String userName, boolean loadPermissionRealm) {

        Usuario usuario = new UsuarioDao().login(userName);

        if (usuario == null) {
            throw new UnknownAccountException("Usuário desconhecido.");
        }
        SimpleAccount simpleAccount = new SimpleAccount(usuario.getLogin(), usuario.getSenha(), usuario.getEmail());
        if (loadPermissionRealm) {
            for (Realm realm : usuario.getRealms()) {
                simpleAccount.addObjectPermission(new WildcardPermission(realm.getNome(), false));
                simpleAccount.addRole(realm.getNome());
            }
        }
        return simpleAccount;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) getAvailablePrincipal(principalCollection);
        return getAccount(username, true);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userPasswordToken = (UsernamePasswordToken) token;
        return getAccount(userPasswordToken.getUsername());
    }
}
