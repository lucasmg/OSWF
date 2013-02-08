/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.model.dao.impl;

import br.com.workflow.model.dao._abstract.DataAccessObjectAbstract;
import br.com.workflow.model.entities.Realm;
import br.com.workflow.model.entities.Usuario;
import javax.persistence.EntityManager;

/**
 *
 * @author Lucas M Guedes
 * @since Feb 7, 2013
 * @version 0.1
 */
public class RealmDAO extends DataAccessObjectAbstract<Realm> {

    public static void main(String[] args) {
        RealmDAO realmDao = new RealmDAO();
        EntityManager em = realmDao.getEm();
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM C_USR_REALM").executeUpdate();
        em.createNativeQuery("DELETE FROM Realm").executeUpdate();
        em.createNativeQuery("DELETE FROM Usuario").executeUpdate();
        //Cria Usuario de Teste
        Usuario user = new Usuario();
        user.setLogin("admin").setSenha("admin").setEmail("admin@admin.com").setNome("Administrador do Sistema");
        em.persist(new Realm().setNome("CRIA_OC").addUsuario(user));
        em.persist(new Realm().setNome("APROVA_OC").addUsuario(user));
        em.persist(new Realm().setNome("EXECUTA_OC").addUsuario(user));
        em.persist(new Realm().setNome("ADMIN").addUsuario(user));
        em.getTransaction().commit();
        
        
    }
}
