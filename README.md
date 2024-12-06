# Event Ticketing System - CLI Simulation 

## Introduction
A multi-threaded Java application designed to simulate the operation of vendors and customers managing a shared ticket pool for an event. The system allows vendors to release tickets and customers to purchase them, while ensuring proper synchronization and adherence to configurable system rules.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Setup Guidance](#Setup)
- [Usage](#usage)
- [System Execution Process](#system-execution-process)
- [Configuration](#configuration)
- [Troubleshooting](#troubleshooting)

## Features

- Configurable system parameters such as ticket release rates, ticket pool capacity, and customer retrieval intervals.


- If a user waits more than 5 seconds, the specific operation get cancelled and proceed to perform his next operation. 

  -  Eg: Customer wants 3 tickets, but he waits more than 5 seconds to buy the 2nd ticket. Then the 2nd ticket transaction get cancelled and he proceed to buy 3rd ticket)


- Automatic and manual shutdown options.
- Customizable configurations saved to a JSON file.

## Setup

### Prerequisites

- **Java Development Kit (JDK):** Version 8 or higher.
- **JSON Library:** Ensure `gson` is available in your classpath for JSON serialization/deserialization.

### Installation
1. Clone or download this repository.


2. Ensure all files (`Configuration.java`, `Customer.java`, `Ticket.java`, `TicketPool.java`, `TicketSystem.java`, `User.java`, `UserType.java`, `Vendor.java`) are in the same directory.


5. Compile the project:
   ```bash
   javac *.java

## Usage

1. Run the `TicketSystem` main class:
   ```bash
   java TicketSystem

2. Follow the prompts to configure system parameters or use the default configuration.


3. The system automatically starts vendors and customers to simulate ticket release and purchase processes.


4. Press `Enter` to manually stop the system or let it complete automatically when all operations finish.


## System Execution Process

This section explains the step-by-step process to start and run the Ticket Management System.

### 1. Starting the System

- When the program starts, you will see the following menu:
  ```vbnet
  ****** Welcome to Ticket System! *******
  __________ Configuration Settings __________
  1. Start
  2. Stop

  Select an option to start or end the application:

- Enter `1` to start the system or `2` to stop the program.

### 2. Choosing Configuration

- If you choose to start the system, the next prompt asks whether you want to use the default configuration or customize it:
  ```bash
  Do you want to configure the system? (yes/no):

- Enter `yes` to configure the system manually, or `no` to use the default settings.

### 3. Configuring the System (if chosen)

- If you choose `yes`, you will be prompted to input the configuration parameters

- After configuration, the system saves the parameters into a `config.json` file for future runs.

### 4. Running the Simulation

- Vendors and customers start interacting with the ticket pool:
    - **Vendors**: Release tickets into the pool based on the configuration.
    - **Customers**: Retrieve tickets from the pool as they become available.


- Example output during the simulation:
  ```vbnet
  Vendor-1 added a ticket. Current size: 6 tickets, Remaining size: 19, Capacity: 25
  Customer-1 bought Ticket-1. Remaining 5 tickets
  Customer-4 bought Ticket-2. Remaining 4 tickets

### 5. Stopping the System

- To manually stop the system, press `Enter` during execution. The system will interrupt all threads and shut down gracefully:
  ```arduino
  System is running. Press 'Enter' to stop...


## Configuration

The system can be configured using the `Configuration.java` class. Key parameters include:

- **Max Ticket Capacity:** Maximum number of tickets the pool can hold.
- **Total Tickets:** Initial number of tickets in the pool.
- **Ticket Release Rate:** Time interval (in Seconds) for vendors to release a ticket.
- **Customer Retrieval Rate:** Time interval (in Seconds) for customers to purchase a ticket.
- **Tickets Per Release:** Number of tickets released per vendor per cycle.
- **Tickets Per Purchase:** Number of tickets purchased per customer per cycle.

Configuration is saved in a `config.json` file, allowing reuse across runs. 

**If no configuration found, system will start with default configuration. If user skip to prompt new configuration, system will reuse the saved configuration.**


## Troubleshooting

### Common Issues

1. **System Not Starting**
    - Ensure that `config.json` exists or allow the system to generate a default configuration.


2. **Thread Interruptions**
    - Threads may stop prematurely if the system is manually halted or due to synchronization issues. Check for proper thread initialization.


3. **Configuration Not Saving**
    - Ensure the program has write permissions in the directory to save the configuration file.


