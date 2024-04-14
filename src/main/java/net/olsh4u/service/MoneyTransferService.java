package net.olsh4u.service;

/**
 * This interface defines the contract for transferring money between accounts in a multithreaded environment.
 */
public interface MoneyTransferService {

    /**
     * Transfers money between accounts until a specified number of transactions is completed.
     */
    void run();

}
