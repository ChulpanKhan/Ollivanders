
package com.mycompany.ollivanders;

import com.mycompany.ollivanders.GUI.WandShopFrame;
import com.mycompany.ollivanders.JpaService.StorageService;
import com.mycompany.ollivanders.JpaService.SupplyService;
import com.mycompany.ollivanders.JpaService.WandService;
import com.mycompany.ollivanders.JpaService.WizardService;

public class Ollivanders {

    public static void main(String[] args){
            WandService wandService = new WandService();
            WizardService wizardService = new WizardService();
            SupplyService supplyService = new SupplyService();
            StorageService storageService = new StorageService();

            WandShopController controller = new WandShopController(
                    wandService, wizardService, supplyService, storageService
            );

            new WandShopFrame(controller);

    }
}
