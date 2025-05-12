
package com.mycompany.ollivanders.JpaService;

import com.mycompany.ollivanders.Storage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class StorageService extends AbstractJpaService<Storage> {

    @Override
    protected Class<Storage> getEntityClass() {
        return Storage.class;
    }

    public void decreaseQuantityOrDelete(Storage item, int amount) throws Throwable {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Storage managedItem = em.find(Storage.class, item.getId());
            if (managedItem != null) {
                int newQuantity = managedItem.getQuantity() - amount;
                if (newQuantity > 0) {
                    managedItem.setQuantity(newQuantity);
                    em.merge(managedItem);
                } else {
                    em.remove(managedItem);
                }
            }
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

}
