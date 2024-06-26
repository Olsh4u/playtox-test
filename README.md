# Money Transfer Simulation

This project simulates money transfers between accounts using multiple threads. It creates a specified number of random accounts, each with an initial balance of 10000. Then, it starts a number of threads to perform money transfers between these accounts concurrently.

## Requirements
- Java SE 8.0 or higher
- Apache Maven

## Installation

1. Clone the repository:

```
git clone https://github.com/Olsh4u/playtox-test.git
```
2. Navigate to the project directory:

```
cd playtox-test
```

3. Build the project using Maven:

```
mvn clean install
```

## Usage

Run the app by using this command:

```
mvn exec:java
```


The program will create a specified number of random accounts and start transferring money between them using multiple threads.

## Configuration

You can configure the number of accounts and the number of threads for the money transfer simulation in the MoneyTransferSimulationMain class.

## Logging

The application uses Log4j2 for logging. Log files are saved in the logs directory in the project root. Each log file is named app-<timestamp>.log, where <timestamp> is the current date and time.
