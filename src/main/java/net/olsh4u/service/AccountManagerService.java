package net.olsh4u.service;

/**
 * This interface defines the contract for managing accounts and running money transfer simulations.
 */
public interface AccountManagerService {

    /**
     * Runs a money transfer simulation with the specified number of accounts.
     *
     * @param numberOfAccounts the number of accounts to create and use in the simulation
     */
    void runMoneyTransferSimulation(int numberOfAccounts, int numberOfThreads);
}
