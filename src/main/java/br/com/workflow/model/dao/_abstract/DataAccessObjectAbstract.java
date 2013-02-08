/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.workflow.model.dao._abstract;

import java.io.Serializable;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.ejb.EntityManagerImpl;

/**
 *
 * @author Lucas M Guedes
 * @since Jan 29, 2013
 */
public abstract class DataAccessObjectAbstract<E extends Serializable> {

    private EntityManagerImpl em;
    private Session session;

    public void refresh(E entidade) {
        //Atualiza o registro
        getEm().refresh(entidade);
    }

    /**
     * Recupera o EntityManager, criado apartir do persistence.xml
     *
     * @return
     */
    public EntityManagerImpl getEm() {
        if (em == null || !em.isOpen()) {
            em = (EntityManagerImpl) Persistence.createEntityManagerFactory("persistence").createEntityManager();
        }
        return em;
    }

    /**
     * Recupera o Session, objeto específico do Hiberntae que o habilita para a
     * execução de métodos específicos do Hibernate.
     *
     * @return
     */
    public Session getSession() {
        if (session == null || !session.isOpen()) {
            EntityManagerImpl entityManager = (EntityManagerImpl) getEm();
            this.session = entityManager.getSession();
        }
        return session;
    }

    public void persist(E entidade) {
        EntityTransaction transaction = getEm().getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            getEm().persist(entidade);
            transaction.commit();
        } catch (RuntimeException rupe) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw rupe;
        }
    }

    public void merge(E entidade) {
        EntityTransaction transaction = getEm().getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            getEm().merge(entidade);
            transaction.commit();
        } catch (RuntimeException rupe) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw rupe;
        }
    }

    public void remove(E entidade) {
        EntityTransaction transaction = getEm().getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            getEm().remove(entidade);
            transaction.commit();
        } catch (RuntimeException rupe) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw rupe;
        }
    }
}
