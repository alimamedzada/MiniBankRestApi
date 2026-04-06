package com.company.MiniBankByUsingSpring.service.impl;

import com.company.MiniBankByUsingSpring.entity.Accounts;
import com.company.MiniBankByUsingSpring.entity.CheckingAccount;
import com.company.MiniBankByUsingSpring.entity.Customers;
import com.company.MiniBankByUsingSpring.entity.SavingsAccount;
import com.company.MiniBankByUsingSpring.entity.Transaction;
import com.company.MiniBankByUsingSpring.exception.InsufficientBalanceException;
import com.company.MiniBankByUsingSpring.exception.InvalidAccountException;
import com.company.MiniBankByUsingSpring.exception.InvalidAmountException;
import com.company.MiniBankByUsingSpring.repository.AccountRepository;
import com.company.MiniBankByUsingSpring.service.inter.AccountOperationsInter;
import com.company.MiniBankByUsingSpring.service.inter.AccountServiceInter;
import com.company.MiniBankByUsingSpring.service.inter.CustomerServiceInter;
import com.company.MiniBankByUsingSpring.service.inter.TransactionServiceInter;
import com.company.MiniBankByUsingSpring.util.IdentifierUtil;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountServiceImpl implements AccountServiceInter, AccountOperationsInter {

    @Autowired
    private TransactionServiceInter transactionService;
    @Autowired
    private CustomerServiceInter customerService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void createAccountByUsername(String username, String accountType, BigDecimal balance) {
        Customers customer = customerService.findCustomerByUsername(username);
        Accounts account;
        if (accountType.equalsIgnoreCase("CHECKING")) {
            account = new CheckingAccount(balance);
        } else if (accountType.equalsIgnoreCase("SAVINGS")) {
            account = new SavingsAccount(balance);
        } else {
            account = new CheckingAccount(balance);
        }
        account.setId(IdentifierUtil.generateAzerbaijanIBANId());
        account.setBalance(balance);
        if (customer != null) {
            customer.addAccount(account);
        }

    }

    public Accounts findAccount(Customers customer, String accNum) {
        return accountRepository.findById(accNum).orElseThrow();
    }

    @Override
    public List<Accounts> showAccounts(Customers customer) {
        return customer.getAccounts();
    }

    @Override
    public boolean deleteAccount(String id) {
        accountRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean updateAccount(Customers c, String accId) {
        Accounts acc = findAccount(c, accId);
        accountRepository.save(acc);
        return true;
    }

    @Override
    public void deposit(String accId, BigDecimal amount) {
        Accounts acc = accountRepository.findById(accId).orElseThrow();
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("The amount cannot be zero or negative!");
        }
        acc.setBalance(acc.getBalance().add(amount));
        acc.showBalance();
        transactionService.createTransaction(accId, "SELF", amount, "self-cash");

    }

    @Override
    public void withdraw(String accId, BigDecimal amount) {
        Accounts acc = accountRepository.findById(accId).orElseThrow();
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("The amount cannot be negative!");
        }
        if (amount.compareTo(acc.getBalance()) > 0) {
            throw new InsufficientBalanceException("You don't have enough balance in your account!" + acc.getBalance());
        }
        acc.setBalance(acc.getBalance().subtract(amount));
        acc.showBalance();

        transactionService.createTransaction(accId, "SELF", amount, "self-cash");
    }

    @Transactional
    @Override
    public boolean transfer(String fromAccId, String toIban, BigDecimal amount, String description) {
        Accounts fromAcc = accountRepository.findAccountById(fromAccId);
        Accounts toAcc = accountRepository.findAccountById(toIban);
        if (fromAcc == null) {
            throw new InvalidAccountException("Sender account not found!");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Transfer amount must be positive!");
        }
        if (amount.compareTo(fromAcc.getBalance()) > 0) {
            throw new InsufficientBalanceException("Insufficent Balance! Your balance: " + fromAcc.getBalance());
        }

        if (toAcc == null) {
            throw new InvalidAccountException("Reciever account not found!");
        }
        fromAcc.setBalance(fromAcc.getBalance().subtract(amount));
        toAcc.setBalance(toAcc.getBalance().add(amount));

        Transaction t = transactionService.createTransaction(
                fromAccId,
                toIban,
                amount,
                description
        );
        if (!(fromAcc.getCustomer().getCustomerId().equals(toAcc.getCustomer().getCustomerId()))) {
            toAcc.getTransactions().add(t);
        } else {
            fromAcc.getTransactions().add(t);
        }
        transactionService.addTransaction(t);
        accountRepository.save(fromAcc);
        accountRepository.save(toAcc);

        return true;
    }

    @Transactional
    @Override
    public boolean qucikTransfer(String customerId, String toIban, BigDecimal amount) {
        List<Accounts> accounts = accountRepository.findAllByCustomerId(customerId);
        System.out.println("acoounts list= " + accounts == null);
        Accounts sourceAccount = null;
        for (Accounts acc : accounts) {
            if (acc.getBalance().compareTo(amount) >= 0) {
                sourceAccount = acc;
                break;
            }
        }
        if (sourceAccount != null) {
            transfer(sourceAccount.getId(), toIban, amount, "Quick Transfer");
        } else {
            throw new InsufficientBalanceException("None of your accounts had sufficient funds.");
        }
        return true;
    }

    @Override
    public Accounts findAccountById(String id) {
        return accountRepository.findAccountById(id);
    }

    @Override
    public List<Accounts> findAccountByCustomerId(String customerId) {
        return accountRepository.findAllByCustomerId(customerId);
    }
}

//    public void showAllAccountsBalance(Customers customer) {
//        List<Accounts> accounts = customer.getAccounts();
//        for (Accounts account : accounts) {
//            if (account != null) {
//                if (account instanceof CheckingAccount) {
//                    System.out.print("CheckingAccount :  ");
//                } else if (account instanceof SavingsAccount) {
//                    System.out.print("SavingsAccount  :  ");
//                }
//                account.showAllAccountsBalances();
//            }
//        }
//        if (accounts.isEmpty()) {
//            System.out.println("You have no account! Please, choose 1 for create new account!");
//        }
//    }
//    public Accounts getAccount(Customers customer) {
//        
//        int accountCount = customer.getAccounts().size();
//        List<Accounts> accounts = customer.getAccounts();
//        try {
//            if (accounts.isEmpty()) {
//                throw new InvalidAccountException("Account is not found!");
//            }
//            if (accountCount == 1) {
//                return accounts.stream().findFirst().get();
//            }
//            while (true) {
//                accounts.stream().filter(Objects::nonNull).forEach(System.out::println);
//                for (int i = 0; i < accounts.size(); i++) {
//                    if (accounts.get(i) != null) {
//                        System.out.println("Account ID: " + accounts.get(i).getId());
//                    }
//                }
//                String num = InputUtil.requireText("Which account would you want to switch? ");
//                Accounts acc = findAccount(customer, num);
//                if (acc != null) {
//                    return acc;
//                }
//            }
//        } catch (BankException e) {
//            throw new InvalidAccountException("Account is not found!");
//        }
//    }
