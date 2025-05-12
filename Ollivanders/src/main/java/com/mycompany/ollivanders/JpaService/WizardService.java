
package com.mycompany.ollivanders.JpaService;

import com.mycompany.ollivanders.Wizard;

public class WizardService extends AbstractJpaService<Wizard> {

    @Override
    protected Class<Wizard> getEntityClass() {
        return Wizard.class;
    }

}
