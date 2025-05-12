
package com.mycompany.ollivanders.JpaService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public abstract class AbstractJpaService<T> {

    protected abstract Class<T> getEntityClass();

    public void add(T entity) throws Throwable {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<T> getAll() throws Throwable {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass()).getResultList();
        } finally {
            em.close();
        }
    }

    public T findById(int id) throws Throwable {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(getEntityClass(), id);
        } finally {
            em.close();
        }
    }

    public void deleteById(int id) throws Throwable {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T entity = em.find(getEntityClass(), id);
            if (entity != null) {
                em.remove(entity);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void clearTable() throws Throwable {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            String jpql = "DELETE FROM " + getEntityClass().getSimpleName();
            em.createQuery(jpql).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }
    
    public void truncTable() throws Throwable {
    EntityManager em = JpaUtil.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
        tx.begin();
        String tableName = getEntityClass().getSimpleName().toLowerCase(); // assumes table = lowercase class
        String sql = "TRUNCATE TABLE " + tableName + " RESTART IDENTITY CASCADE";
        em.createNativeQuery(sql).executeUpdate(); // native SQL
        tx.commit();
    } catch (Exception e) {
        if (tx.isActive()) {
            tx.rollback();
        }
        throw e;
    } finally {
        em.close();
    }
}


    public void update(T entity) throws Throwable {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}

