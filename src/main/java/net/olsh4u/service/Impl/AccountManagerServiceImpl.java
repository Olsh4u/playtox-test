package net.olsh4u.service.Impl;

import net.olsh4u.domain.entity.Account;
import net.olsh4u.service.AccountManagerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountManagerServiceImpl implements AccountManagerService {

    private static final int BALANCE = 10000;

    private static final Logger logger = LogManager.getLogger(AccountManagerServiceImpl.class);

    public void runMoneyTransferSimulation(int numberOfAccounts, int numberOfThreads) {
        List<Account> accounts = createRandomAccounts(numberOfAccounts);

        try (ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads)) {
            for (Account account : accounts) {
                executorService.submit(() -> {
                    MoneyTransferServiceImpl moneyTransferService = new MoneyTransferServiceImpl(account, accounts);
                    moneyTransferService.run();
                });
            }
        } catch (Exception e) {
            logger.error("Error executing money transfer simulation", e);
        }

        logger.info("All transactions completed.");
    }

    private List<Account> createRandomAccounts(int numberOfAccounts) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < numberOfAccounts; i++) {
            String randomID = String.valueOf(UUID.randomUUID());
            Account account = new Account(randomID, BALANCE);
            accounts.add(account);
        }
        return accounts;
    }
}
