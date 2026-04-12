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
import com.company.MiniBankByUsingSpring.repository.CustomerRepository;
import com.company.MiniBankByUsingSpring.service.inter.AccountOperationsInter;
import com.company.MiniBankByUsingSpring.service.inter.AccountServiceInter;
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
    private CustomerRepository cRepo;

    @Autowired
    private AccountRepository aRepo;

    @Override
    public Accounts createAccount(String accountType,
            BigDecimal balance) {
        Accounts account;

        if (accountType.equalsIgnoreCase("SAVINGS")) {
            account = new SavingsAccount(balance);
        } else {
            account = new CheckingAccount(balance);
        }

        account.setId(IdentifierUtil.generateAzerbaijanIBANId());
        account.setBalance(balance);

        return account;
    }

    @Override
    public List<Accounts> showAccounts(Customers customer) {
        return customer.getAccounts();
    }

    @Override
    public boolean deleteAccount(String id) {
        if (aRepo.findById(id).isEmpty()) {
            return false;
        }
        aRepo.deleteById(id);
        return true;
    }

    @Override
    public boolean updateAccount(Accounts account) {
        Accounts saved = aRepo.save(account);
        return saved != null;
    }

    @Override
    public Accounts findAccountById(String id) {
        return aRepo.findById(id).orElse(null);
    }

    @Override
    public List<Accounts> findAllByCustomerId(String customerId) {
        return aRepo.findAllByCustomerId(customerId);
    }

    @Override
    public void deposit(Accounts acc, BigDecimal amount) {
        if (acc == null) {
            throw new InvalidAccountException("account not found!");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(
                    "The amount cannot be zero or negative!");
        }
        acc.setBalance(acc.getBalance().add(amount));
        acc.showBalance();

    }

    @Override
    public void withdraw(Accounts acc, BigDecimal amount) {
        if (acc == null) {
            throw new InvalidAccountException("account not found!");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("The amount can't be negative!");
        }
        if (amount.compareTo(acc.getBalance()) > 0) {
            throw new InsufficientBalanceException(
                    "You don't have enough balance in your account!"
                    + acc.getBalance());
        }
        acc.setBalance(acc.getBalance().subtract(amount));
        acc.showBalance();

    }

    @Transactional
    @Override
    public boolean transfer(String fromAccId, String toIban, BigDecimal amount,
            String description) {
        Accounts fromAcc = findAccountById(fromAccId);
        Accounts toAcc = findAccountById(toIban);
        if (fromAcc == null) {
            throw new InvalidAccountException("Sender account not found!");
        }
        if (toAcc == null) {
            throw new InvalidAccountException("Reciever account not found!");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Transfer amount must be positive!");
        }
        if (amount.compareTo(fromAcc.getBalance()) > 0) {
            throw new InsufficientBalanceException(
                    "Insufficent Balance! Your balance: " + fromAcc.getBalance());
        }

        withdraw(fromAcc, amount);
        deposit(toAcc, amount);

        Transaction t = transactionService.createTransaction(
                fromAccId,
                toIban,
                amount,
                description
        );
        transactionService.addTransaction(t);

        return true;
    }

    @Transactional
    @Override
    public boolean qucikTransfer(String customerId, String toIban, BigDecimal amount) {
        List<Accounts> accounts = aRepo.findAllByCustomerId(customerId);

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
            throw new InsufficientBalanceException(
                    "None of your accounts had sufficient funds.");
        }
        return true;
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
