
package com.mycompany.ollivanders.JpaService;

import com.mycompany.ollivanders.Supply;

public class SupplyService extends AbstractJpaService<Supply>{

    @Override
    protected Class<Supply> getEntityClass() {
        return Supply.class;
    }
    
}
