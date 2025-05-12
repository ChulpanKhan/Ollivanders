package com.mycompany.ollivanders;

import com.mycompany.ollivanders.GUI.DialogUtils;
import com.mycompany.ollivanders.JpaService.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WandShopController {

    private final WandService wandService;
    private final WizardService wizardService;
    private final SupplyService supplyService;
    private final StorageService storageService;

    public WandShopController(WandService wandService, WizardService wizardService, SupplyService supplyService, StorageService storageService) {
        this.wandService = wandService;
        this.wizardService = wizardService;
        this.supplyService = supplyService;
        this.storageService = storageService;
    }

    public List<Wand> getAllWands() {
        try {
            return wandService.getAll();
        } catch (Throwable e) {
            DialogUtils.showErrorMessage("Не удалось загрузить палочки: " + e.getMessage());
            return List.of();
        }
    }

    public List<Wand> getAvailableWands() {
        List<Wand> all = getAllWands();
        List<Wand> availableWands = new ArrayList<>();

        for (Wand wand : all) {
            String status = wand.getStatus();
            if ("available".equalsIgnoreCase(status) || "в наличии".equalsIgnoreCase(status)) {
                availableWands.add(wand);
            }
        }
        return availableWands;
    }

    public void createWand(String wood, String core) {
        try {
            Wand wand = new Wand(core, wood, "available");
            wandService.add(wand);
            DialogUtils.showSuccessDialog("Палочка успешно создана.");
        } catch (Throwable e) {
            DialogUtils.showErrorMessage("Ошибка при создании палочки: " + e.getMessage());
        }
    }

    public void createWandFromComponents(int woodId, int coreId) {
        try {
            Storage wood = storageService.findById(woodId);
            Storage core = storageService.findById(coreId);

            if (wood == null || core == null) {
                DialogUtils.showErrorMessage("Не удалось найти компоненты в хранилище.");
                return;
            }

            if (wood.getQuantity() < 1 || core.getQuantity() < 1) {
                DialogUtils.showErrorMessage("Недостаточно компонентов на складе.");
                return;
            }

            storageService.decreaseQuantityOrDelete(wood, 1);
            storageService.decreaseQuantityOrDelete(core, 1);

            Wand wand = new Wand(core.getItemName(), wood.getItemName(), "в наличии");
            wandService.add(wand);
            DialogUtils.showSuccessDialog("Палочка успешно создана из выбранных компонентов.");

        } catch (Throwable e) {
            DialogUtils.showErrorMessage("Ошибка при создании палочки: " + e.getMessage());
        }
    }

    public void updateWand(Wand wand) {
        try {
            wandService.update(wand);
        } catch (Throwable e) {
            DialogUtils.showErrorMessage("Не удалось обновить таблицу палочек: " + e.getMessage());
        }
    }

    public List<Wizard> getAllWizards() {
        try {
            return wizardService.getAll();
        } catch (Throwable e) {
            DialogUtils.showErrorMessage("Не удалось загрузить волшебников: " + e.getMessage());
            return List.of();
        }
    }

    public void createWizard(String name, Wand wand) {
        try {
            Wizard wizard = new Wizard(name, wand);
            wizardService.add(wizard);
            DialogUtils.showSuccessDialog("Волшебник успешно добавлен.");
        } catch (Throwable e) {
            DialogUtils.showErrorMessage("Ошибка при добавлении волшебника: " + e.getMessage());
        }
    }

    public List<Supply> getAllSupplies() {
        try {
            return supplyService.getAll();
        } catch (Throwable e) {
            DialogUtils.showErrorMessage("Не удалось загрузить поставки: " + e.getMessage());
            return List.of();
        }
    }

    public void createSupply(Timestamp date) {
        try {
            Supply supply = new Supply(date);
            supplyService.add(supply);
            DialogUtils.showSuccessDialog("Поставка успешно добавлена.");
        } catch (Throwable e) {
            DialogUtils.showErrorMessage("Ошибка при добавлении поставки: " + e.getMessage());
        }
    }

    public List<Storage> getAllStorageItems() {
        try {
            return storageService.getAll();
        } catch (Throwable e) {
            DialogUtils.showErrorMessage("Не удалось загрузить хранилище: " + e.getMessage());
            return List.of();
        }
    }

    public List<Storage> getCoreItems() {
        try {
            List<Storage> all = getAllStorageItems();
            List<Storage> coreItem = new ArrayList<>(); 

            for (Storage item : all) {
                String itemType = item.getType();
                if ("core".equalsIgnoreCase(itemType)) {
                    coreItem.add(item);
                }
            }
            return coreItem;
        } catch (Exception e) {
            DialogUtils.showErrorMessage("Ошибка при получении сердцевин: " + e.getMessage());
            return List.of(); 
        }
    }

    public List<Storage> getWoodItems() {
        try {
            List<Storage> all = getAllStorageItems();
            List<Storage> woodItem = new ArrayList<>(); 

            for (Storage item : all) {
                String itemType = item.getType();
                if ("wood".equalsIgnoreCase(itemType)) {
                    woodItem.add(item);
                }
            }
            return woodItem;
        } catch (Exception e) {
            DialogUtils.showErrorMessage("Ошибка при получении древесины: " + e.getMessage());
            return List.of();
        }
    }

     
    public void addStorageItem(String name, String type, int quantity, Supply supply) {
        try {
            Storage item = new Storage(name, type, quantity, supply);
            storageService.add(item);
                    } catch (Throwable e) {
            DialogUtils.showErrorMessage("Ошибка при добавлении в хранилище: " + e.getMessage());
        }
    }
    
    public void decreaseQuantityOrDelete(Storage item, int amount){
        try {
            storageService.decreaseQuantityOrDelete(item, amount);
        } catch (Throwable e) {
            DialogUtils.showErrorMessage("Ошибка при удалении из хранилища: " + e.getMessage());
        }
    }
    

    //очистка
    public void clearWandsAndWizards() {
        try {
            wizardService.truncTable();
            wandService.truncTable();
            DialogUtils.showSuccessDialog("Маги и палочки очищены.");
        } catch (Throwable e) {
            DialogUtils.showErrorMessage("Ошибка при очистке: " + e.getMessage());
        }
    }

    public void clearStorageAndSupplies() {
        try {
            storageService.truncTable();
            supplyService.truncTable();
            DialogUtils.showSuccessDialog("Хранилище и поставки очищены.");
        } catch (Throwable e) {
            DialogUtils.showErrorMessage("Ошибка при очистке: " + e.getMessage());
        }
    }
    
    public void clearAll() {
        try {
            wizardService.truncTable();
            wandService.truncTable();
            storageService.truncTable();
            supplyService.truncTable();
            DialogUtils.showSuccessDialog("Все данные удалены.");
        } catch (Throwable e) {
            DialogUtils.showErrorMessage("Ошибка при полной очистке: " + e.getMessage());
        }
    }

}
