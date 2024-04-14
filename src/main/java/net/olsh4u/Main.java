package net.olsh4u;

import net.olsh4u.service.AccountManagerService;
import net.olsh4u.service.Impl.AccountManagerServiceImpl;


public class Main {

    public static void main(String[] args) {

        AccountManagerService accountManager = new AccountManagerServiceImpl();
        accountManager.runMoneyTransferSimulation(4, 10);
    }
}

