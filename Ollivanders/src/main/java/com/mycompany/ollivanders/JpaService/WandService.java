
package com.mycompany.ollivanders.JpaService;

import com.mycompany.ollivanders.Wand;

public class WandService extends AbstractJpaService<Wand> {

    @Override
    protected Class<Wand> getEntityClass() {
        return Wand.class;
    }

}
