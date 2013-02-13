/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.model.dao.impl;

import br.com.workflow.model.dao._abstract.DataAccessObjectAbstract;
import br.com.workflow.model.entities.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 7, 2013
 * @version 0.1
 */
public class UsuarioDao extends DataAccessObjectAbstract<Usuario> {

    @Override
    public void merge(Usuario entidade) {
        EntityTransaction transaction = getEm().getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            if (entidade.getId() != null) {
                getEm().merge(entidade);
            } else {
                getEm().persist(entidade);
            }
            transaction.commit();
        } catch (RuntimeException rupe) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw rupe;
        }
    }

    public Usuario login(String login, String senha) {
        if (login == null) {
            return null;
        }
        EntityManager em = getEm();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT u ");
        queryBuilder.append("  FROM Usuario u ");
        queryBuilder.append(" WHERE upper(u.login) = upper(:p_login)");
        queryBuilder.append("   AND (upper(u.senha) = upper(:p_senha)) OR u.senha IS NULL");
        Query query = em.createQuery(queryBuilder.toString());
        query.setParameter("p_login", login.trim());
        query.setParameter("p_senha", senha != null ? senha.trim() : null);
        List<Usuario> usuarios = query.getResultList();
        if (usuarios != null && usuarios.size() > 0) {
            return usuarios.get(0);
        } else {
            return null;
        }
    }

    public Usuario login(String login) {
        if (login == null) {
            return null;
        }
        EntityManager em = getEm();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT u ");
        queryBuilder.append("  FROM Usuario u ");
        queryBuilder.append(" WHERE upper(u.login) = upper(:p_login)");
        Query query = em.createQuery(queryBuilder.toString());
        query.setParameter("p_login", login.trim());
        List<Usuario> usuarios = query.getResultList();
        if (usuarios != null && usuarios.size() > 0) {
            return usuarios.get(0);
        } else {
            return null;
        }
    }
}
