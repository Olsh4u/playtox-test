package net.olsh4u.service.Impl;

import net.olsh4u.domain.entity.Account;
import net.olsh4u.service.MoneyTransferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MoneyTransferServiceImpl implements Runnable, MoneyTransferService {

    private static final int MAX_TRANSACTIONS = 30;

    private static final Lock lock = new ReentrantLock();
    private static final Logger logger = LogManager.getLogger(MoneyTransferServiceImpl.class);

    private static int transactionsCompleted = 0;

    private final Account currentAccount;
    private final List<Account> allAccounts;
    private final Random random = new Random();

    public MoneyTransferServiceImpl(Account currentAccount, List<Account> allAccounts) {
        this.currentAccount = currentAccount;
        this.allAccounts = allAccounts;
    }

    @Override
    public void run() {
        while (!shouldFinish()) {
            Account targetAccount = getRandomAccount();

            int transferAmount = random.nextInt(currentAccount.getMoney());

            if (currentAccount.getMoney() >= transferAmount && !Objects.equals(currentAccount.getId(), targetAccount.getId())) {
                boolean locked = false;
                try {
                    locked = lock.tryLock(1, TimeUnit.SECONDS);
                    if (locked) {
                        currentAccount.setMoney(currentAccount.getMoney() - transferAmount);
                        targetAccount.setMoney(targetAccount.getMoney() + transferAmount);
                        writeLogMessage(transferAmount, targetAccount);
                        transactionsCompleted++;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("Thread interrupted while trying to lock accounts", e);
                } finally {
                    if (locked) {
                        lock.unlock();
                    }
                }

                sleepRandomTime();
            }
        }
    }

    /**
     * Writes a log message for the money transfer.
     *
     * @param transferAmount  the amount of money transferred
     * @param targetAccount   the account to which the money is transferred
     */
    private void writeLogMessage(int transferAmount, Account targetAccount) {
        logger.info("Transferred {} from {} to {}. Current money on account {}: {}",
                transferAmount,
                currentAccount.getId(),
                targetAccount.getId(),
                currentAccount.getId(),
                currentAccount.getMoney());
    }

    /**
     * Retrieves a random account from the list of all accounts.
     *
     * @return a random account
     */
    private Account getRandomAccount() {
        int randomIndex = random.nextInt(allAccounts.size());
        return allAccounts.get(randomIndex);
    }

    /**
     * Causes the current thread to sleep for a random amount of time.
     */
    private void sleepRandomTime() {
        int sleepTime = random.nextInt(1000) + 1000;
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted", e);
        }
    }

    /**
     * Checks if the money transfer simulation should finish.
     *
     * @return true if the simulation should finish, false otherwise
     */
    private boolean shouldFinish() {
        return transactionsCompleted >= MAX_TRANSACTIONS;
    }
}