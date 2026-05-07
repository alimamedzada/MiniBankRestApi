package test;

import com.company.MiniBankByUsingSpring.accounts.entity.Accounts;
import com.company.MiniBankByUsingSpring.accounts.exception.InvalidAmountException;
import com.company.MiniBankByUsingSpring.accounts.exception.InvalidAccountException;
import com.company.MiniBankByUsingSpring.accounts.exception.InsufficientBalanceException;
import com.company.MiniBankByUsingSpring.accounts.entity.CheckingAccount;
import com.company.MiniBankByUsingSpring.customers.entity.Customers;
import com.company.MiniBankByUsingSpring.accounts.entity.SavingsAccount;
import com.company.MiniBankByUsingSpring.transaction.entity.Transaction;
import com.company.MiniBankByUsingSpring.accounts.repository.AccountRepository;
import com.company.MiniBankByUsingSpring.accounts.service.impl.AccountServiceImpl;
import com.company.MiniBankByUsingSpring.transaction.service.inter.TransactionServiceInter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    AccountRepository aRepo;

    @InjectMocks
    AccountServiceImpl aService;

    @Mock
    TransactionServiceInter tService;

    // findAccountById
    @Test
    public void testNullFindAccountById() {
        String fakeId = "999";

        Mockito.when(aRepo.findById(fakeId)).thenReturn(Optional.empty());

        Assertions.assertNull(aService.findAccountById(fakeId), "must be return null when account not found");

        Mockito.verify(aRepo, Mockito.atLeastOnce()).findById(fakeId);
    }

    @Test
    public void testNotNullFindAccountById() {
        String fakeId = "999";
        Accounts fakeAcc = new Accounts();

        fakeAcc.setId(fakeId);
        fakeAcc.setBalance(BigDecimal.TEN);

        Mockito.when(aRepo.findById(fakeId)).thenReturn(Optional.of(fakeAcc));

        Accounts realAcc = aService.findAccountById(fakeId);

        Assertions.assertEquals(
                realAcc.getId(), fakeId, "This is your account!");

        Assertions.assertNotNull(realAcc, "Hesab var, amma null geldi!");

        Assertions.assertEquals(
                BigDecimal.TEN, realAcc.getBalance(), "balance sehvdir");

        Mockito.verify(aRepo, Mockito.times(1)).findById(fakeId);
    }

    @Test
    public void testThrowsFindAccountById() {
        String fakeId = "db exception";
        Mockito.when(aRepo.findById(fakeId)).thenThrow(
                new RuntimeException("Exception atdi"));

        Assertions.assertThrows(RuntimeException.class, () -> {
            aService.findAccountById(fakeId);
        }, "exception atmalidir!");
    }

    //  updateAccount
    @Test
    public void testNullUpdateAccount() {
        String fakeId = "1";
        Accounts fakeAcc = new Accounts();
        fakeAcc.setId(fakeId);

        Mockito.when(aRepo.save(fakeAcc)).thenReturn(null);

        boolean result = aService.updateAccount(fakeAcc);

        Assertions.assertFalse(result);

        Mockito.verify(aRepo, Mockito.times(1)).save(fakeAcc);
    }

    @Test
    public void testNotNullUpdateAccount() {
        String fakeId = "1";
        Accounts fakeAcc = new Accounts();
        fakeAcc.setId(fakeId);

        Mockito.when(aRepo.save(fakeAcc)).thenReturn(fakeAcc);

        boolean result = aService.updateAccount(fakeAcc);

        Assertions.assertTrue(result);

        Mockito.verify(aRepo, Mockito.times(1)).save(fakeAcc);

    }

    @Test
    public void testThrowsUpdateAccount() {
        String fakeId = "db exception";
        Accounts acc = new Accounts();
        acc.setId(fakeId);

        Mockito.when(aRepo.save(acc)).thenThrow(
                new RuntimeException("hesab yoxdur"));

        Assertions.assertThrows(RuntimeException.class, () -> {
            aService.updateAccount(acc);
        }, "Serviceden exception geldi!");
    }

    // findAllAccountByCustomerId
    @Test
    public void testNullFindAllAccountByCustomerId() {
        String fakeId = "1";

        Mockito.when(aRepo.findAllByCustomerId(fakeId)).thenReturn(null);

        List<Accounts> list = aService.findAllByCustomerId(fakeId);

        Assertions.assertNull(list, "custonmerin account'u yoxdur");

        Mockito.verify(aRepo, Mockito.times(1)).findAllByCustomerId(fakeId);
    }

    @Test
    public void testNotNullFindAllAccountByCustomerId() {
        String fakeId = "1";
        List<Accounts> fakeList = new ArrayList<>();

        fakeList.add(new Accounts());
        fakeList.add(new Accounts());

        Mockito.when(aRepo.findAllByCustomerId(fakeId)).thenReturn(fakeList);

        List<Accounts> realList = aService.findAllByCustomerId(fakeId);

        Assertions.assertNotNull(realList, "custonmerin account'u var!");
        Assertions.assertEquals(2, realList.size(), "custonmerin account'u var!");

        Mockito.verify(aRepo, Mockito.times(1)).findAllByCustomerId(fakeId);
    }

    @Test
    public void testThrowsFindAllAccountByCustomerId() {
        String fakeId = "1";

        Mockito.when(aRepo.findAllByCustomerId(fakeId)).thenThrow(
                new RuntimeException("exception atmalidir"));

        Assertions.assertThrows(RuntimeException.class, () -> {
            aService.findAllByCustomerId(fakeId);
        }, "exception atdi!");
    }

    // deleteAccount
    @Test
    public void testNullDeleteAccount() {
        String fakeId = "1";
        Mockito.when(aRepo.findById(fakeId)).thenReturn(Optional.empty());

        boolean result = aService.deleteAccount(fakeId);

        Assertions.assertFalse(result, "Hesab yoxdu!");

        Mockito.verify(aRepo, Mockito.never()).deleteById(fakeId);
    }

    @Test
    public void testNotNullDeleteAccount() {
        String fakeId = "1";
        Accounts acc = new Accounts();
        acc.setId(fakeId);

        Mockito.when(aRepo.findById(fakeId)).thenReturn(Optional.of(acc));

        boolean result = aService.deleteAccount(fakeId);

        Assertions.assertTrue(result, "Hesap dönmeliydi!");

        Mockito.verify(aRepo, Mockito.times(1)).deleteById(fakeId);
    }

    @Test
    public void testThrowsDeleteAccount() {
        String fakeId = "1";

        Mockito.when(aRepo.findById(fakeId)).thenReturn(Optional.of(new Accounts()));

        Mockito.doThrow(new RuntimeException("")).when(aRepo).deleteById(fakeId);

        Assertions.assertThrows(RuntimeException.class, () -> {
            aService.deleteAccount(fakeId);
        }, "exception'i cole atmalidir");
    }

    @Test
    public void testEmptyShowAccounts() {
        Customers c = new Customers();
        c.setAccounts(new ArrayList<>());

        List<Accounts> listReal = aService.showAccounts(c);

        Assertions.assertNotNull(listReal);
        Assertions.assertTrue(listReal.isEmpty(), "Bosh olmalidir");
    }

    @Test
    public void testNotEmptyShowAccounts() {
        Customers c = new Customers();
        List<Accounts> list = new ArrayList<>();
        list.add(new Accounts());

        c.setAccounts(list);

        List<Accounts> listReal = aService.showAccounts(c);

        Assertions.assertNotNull(listReal);
        Assertions.assertFalse(listReal.isEmpty(), "Bosh olmalidir");
    }

    @Test
    public void testThrowsShowAccounts() {
        Customers c = null;
        Assertions.assertThrows(NullPointerException.class, () -> {
            aService.showAccounts(c);
        }, "Customer null pointer atmali idi!");
    }

    @Test
    public void testCreateSavingsAccount() {
        String fakeType = "SAVINGS";
        BigDecimal fakeBalance = new BigDecimal(5);

        Accounts accReal = aService.createAccount(fakeType, fakeBalance);

        Assertions.assertTrue(accReal instanceof SavingsAccount,
                "SavingsAccount dur mu?");
        Assertions.assertNotNull(accReal, "obyekt var mi?");
        Assertions.assertEquals(fakeBalance, accReal.getBalance(),
                "balanslar beraber olmalidir.");

    }

    @Test
    public void testCreateCheckingAccount() {
        String fakeType = "CHECKING";
        BigDecimal fakeBalance = new BigDecimal("500");

        Accounts accReal = aService.createAccount(fakeType, fakeBalance);

        Assertions.assertTrue(accReal instanceof CheckingAccount,
                "SavingsAccount dur mu?");
        Assertions.assertNotNull(accReal, "obyekt var mi?");
        Assertions.assertEquals(fakeBalance, accReal.getBalance(),
                "balanslar beraber olmalidir.");
    }
//  Deposit

    @Test
    public void testDeposit_Success() {
        String fakeAccId = "1";
        BigDecimal initialBalance = new BigDecimal("500");
        BigDecimal depositAmount = new BigDecimal("100");

        Accounts acc = new Accounts();
        acc.setBalance(initialBalance);
        acc.setId(fakeAccId);

        aService.deposit(acc, depositAmount);

        Assertions.assertEquals(new BigDecimal("600"), acc.getBalance(),
                "account tapilmalidir");

    }

    @Test
    public void testDeposit_ShouldThrowException_WhenAmountIsNegative() {
        String fakeAccId = "1";
        BigDecimal negative = new BigDecimal("-100");

        Accounts acc = new Accounts();
        acc.setId(fakeAccId);

        Assertions.assertThrows(InvalidAmountException.class, () -> {
            aService.deposit(acc, negative);
        }, "mebleg menfi ola bilmez");
    }

    @Test
    public void testDeposit_ShouldThrowException_WhenAccountNull() {
        String fakeAccId = "1";

        Accounts acc = new Accounts();
        acc.setId(fakeAccId);

        Assertions.assertThrows(InvalidAccountException.class, () -> {
            aService.deposit(null, BigDecimal.ONE);
        },
                "account yoxdu, exception atmalidir"
        );
    }

    @Test
    public void testWithdraw_Success() {
        String fakeId = "1";
        BigDecimal amount = new BigDecimal("100");

        Accounts acc = new Accounts();
        acc.setId(fakeId);
        acc.setBalance(new BigDecimal("500"));

        aService.withdraw(acc, amount);

        Assertions.assertTrue(
                new BigDecimal("400").compareTo(acc.getBalance()) == 0,
                "Balans 400 olmalidir " + acc.getBalance() + " geldi!");
    }

    @Test
    public void testWithdraw_ThrowException_WhenAmountInsufficient() {
        String fakeId = "1";
        BigDecimal amount = new BigDecimal("1100");

        Accounts acc = new Accounts();
        acc.setId(fakeId);
        acc.setBalance(new BigDecimal("500"));

        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            aService.withdraw(acc, amount);
        }, "amount balans'dan boyuk ola bilmez!");
    }

    @Test
    public void testWithdraw_ThrowException_WhenAmountNegative() {
        String fakeId = "1";
        BigDecimal amount = new BigDecimal("-500");

        Accounts acc = new Accounts();
        acc.setId(fakeId);
        acc.setBalance(new BigDecimal("500"));

        Assertions.assertThrows(InvalidAmountException.class, () -> {
            aService.withdraw(acc, amount);
        }, "amount menfi ola bilmez!");
    }

    @Test
    public void testWithdraw_ThrowException_WhenAccountNull() {
        String fakeId = "1";

        Accounts acc = new Accounts();
        acc.setId(fakeId);

        Assertions.assertThrows(InvalidAccountException.class, () -> {
            aService.withdraw(null, BigDecimal.ONE);
        }, "account null ola bilmez!");
    }

    @Test
    public void testTransfer_Success() {
        String fromId = "1";
        String toIban = "2";
        String cId = "3";
        BigDecimal amount = new BigDecimal("100");
        String description = "transfer test";

        Customers c = new Customers();
        c.setCustomerId(cId);

        List<Transaction> list = new ArrayList<>();

        Accounts fromAcc = new Accounts();
        fromAcc.setId(fromId);
        fromAcc.setBalance(new BigDecimal("500"));
        fromAcc.setCustomer(c);
        fromAcc.setTransactions(list);

        Accounts toAcc = new Accounts();
        toAcc.setId(toIban);
        toAcc.setBalance(new BigDecimal("500"));
        toAcc.setCustomer(c);
        toAcc.setTransactions(list);

        Mockito.when(aRepo.findById(fromId)).thenReturn(Optional.of(fromAcc));

        Mockito.when(aRepo.findById(toIban)).thenReturn(Optional.of(toAcc));

        Transaction t = new Transaction();
        t.setAmount(amount);
        t.setDescription(description);
        t.setFromAccount(fromAcc);
        t.setToIban(toIban);

        fromAcc.getTransactions().add(t);
        toAcc.getTransactions().add(t);

        Mockito.when(tService.createTransaction(
                fromAcc, toIban, amount, description)).thenReturn(t);

        boolean result = aService.transfer(fromAcc, toIban, amount, description);

        Assertions.assertTrue(result, "tramsfer metodu ishledi!");
        Assertions.assertEquals(
                new BigDecimal("400"), fromAcc.getBalance(), "gonderen account balansi !");
        Assertions.assertEquals(
                new BigDecimal("600"), toAcc.getBalance(), "qebul eden account balansi !");
    }

    @Test
    public void testTransfer_ThrowException_WhenFromAccNull() {
        String fromId = "1";
        String toIban = "2";
        Accounts fromAcc = new Accounts();
        fromAcc.setId(fromId);
        BigDecimal amount = new BigDecimal("100");
        String description = "transfer test";

        Mockito.when(aRepo.findById(fromId)).thenReturn(Optional.empty());

        Assertions.assertThrows(InvalidAccountException.class, () -> {
            aService.transfer(fromAcc, toIban, amount, description);
        }, "Sender account is null!");
    }

    @Test
    public void testTransfer_ThrowException_WhenToAccNull() {
        String fromId = "1";
        String toIban = "2";
        BigDecimal amount = new BigDecimal("100");
        String description = "transfer test";

        Accounts fromAcc = new Accounts();
        fromAcc.setId(fromId);
        fromAcc.setBalance(new BigDecimal("500"));

        Mockito.when(aRepo.findById(fromId)).thenReturn(Optional.of(fromAcc));
        Mockito.when(aRepo.findById(toIban)).thenReturn(Optional.empty());

        Assertions.assertThrows(InvalidAccountException.class, () -> {
            aService.transfer(fromAcc, toIban, amount, description);
        }, "Sender account is null!");
    }

    @Test
    public void testTransfer_ThrowException_WhenAmountNegative() {
        String fromId = "1";
        String toIban = "2";
        String cId = "3";
        BigDecimal amount = new BigDecimal("-800");
        String description = "transfer test";

        Customers c = new Customers();
        c.setCustomerId(cId);

        List<Transaction> list = new ArrayList<>();

        Accounts fromAcc = new Accounts();
        fromAcc.setId(fromId);
        fromAcc.setBalance(new BigDecimal("500"));
        fromAcc.setCustomer(c);
        fromAcc.setTransactions(list);

        Accounts toAcc = new Accounts();
        toAcc.setId(toIban);
        toAcc.setBalance(new BigDecimal("500"));
        toAcc.setCustomer(c);
        toAcc.setTransactions(list);

        Mockito.when(aRepo.findById(fromId)).thenReturn(Optional.of(fromAcc));
        Mockito.when(aRepo.findById(toIban)).thenReturn(Optional.of(toAcc));

        Assertions.assertThrows(InvalidAmountException.class, () -> {
            aService.transfer(fromAcc, toIban, amount, description);
        }, "Amount must be less than balance!");
    }

    @Test
    public void testTransfer_ThrowException_WhenBalanceInsufficient() {
        String fromId = "1";
        String toIban = "2";
        String cId = "3";
        BigDecimal amount = new BigDecimal("800");
        String description = "transfer test";

        Customers c = new Customers();
        c.setCustomerId(cId);

        List<Transaction> listFrom = new ArrayList<>();
        List<Transaction> listTo = new ArrayList<>();

        Accounts fromAcc = new Accounts();
        fromAcc.setId(fromId);
        fromAcc.setBalance(new BigDecimal("500"));
        fromAcc.setCustomer(c);
        fromAcc.setTransactions(listFrom);

        Accounts toAcc = new Accounts();
        toAcc.setId(toIban);
        toAcc.setBalance(new BigDecimal("500"));
        toAcc.setCustomer(c);
        toAcc.setTransactions(listTo);

        Mockito.when(aRepo.findById(fromId)).thenReturn(Optional.of(fromAcc));
        Mockito.when(aRepo.findById(toIban)).thenReturn(Optional.of(toAcc));

        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            aService.transfer(fromAcc, toIban, amount, description);
        }, "Amount must be less than balance!");
    }

    @Test
    public void testTransfer_WhenSameCustomer() {
        String fromId = "1";
        String toIban = "2";
        String cId = "3";
        BigDecimal amount = new BigDecimal("100");
        String description = "transfer test";

        Customers c = new Customers();
        c.setCustomerId(cId);

        List<Transaction> listFrom = new ArrayList<>();
        List<Transaction> listTo = new ArrayList<>();

        Accounts fromAcc = new Accounts();
        fromAcc.setId(fromId);
        fromAcc.setBalance(new BigDecimal("500"));
        fromAcc.setCustomer(c);
        fromAcc.setTransactions(listFrom);

        Accounts toAcc = new Accounts();
        toAcc.setId(toIban);
        toAcc.setBalance(new BigDecimal("500"));
        toAcc.setCustomer(c);
        toAcc.setTransactions(listTo);

        Transaction t = new Transaction();
        t.setAmount(amount);
        t.setDescription(description);
        t.setFromAccount(fromAcc);
        t.setToIban(toIban);

        Mockito.when(aRepo.findById(fromId)).thenReturn(Optional.of(fromAcc));
        Mockito.when(aRepo.findById(toIban)).thenReturn(Optional.of(toAcc));

        aService.transfer(fromAcc, toIban, amount, description);

        Assertions.assertEquals(1, fromAcc.getTransactions().size(),
                "Aynı müşteri transferinde işlem gönderenin listesine eklenmeliydi!");

        Assertions.assertEquals(0, toAcc.getTransactions().size(),
                "Aynı müşteri transferinde alıcının listesi boş kalmalıydı!");
    }

    @Test
    public void testTransfer_WhenIsNotSameCustomer() {
        String fromId = "1";
        String toIban = "2";
        String cFromId = "3";
        String cToId = "4";

        BigDecimal amount = new BigDecimal("100");
        String description = "transfer test";

        Customers cFrom = new Customers();
        cFrom.setCustomerId(cFromId);

        Customers cTo = new Customers();
        cTo.setCustomerId(cToId);

        List<Transaction> listFrom = new ArrayList<>();
        List<Transaction> listTo = new ArrayList<>();

        Accounts fromAcc = new Accounts();
        fromAcc.setId(fromId);
        fromAcc.setBalance(new BigDecimal("500"));
        fromAcc.setCustomer(cFrom);
        fromAcc.setTransactions(listFrom);

        Accounts toAcc = new Accounts();
        toAcc.setId(toIban);
        toAcc.setBalance(new BigDecimal("500"));
        toAcc.setCustomer(cTo);
        toAcc.setTransactions(listTo);

        Transaction t = new Transaction();
        t.setAmount(amount);
        t.setDescription(description);
        t.setFromAccount(fromAcc);
        t.setToIban(toIban);

        Mockito.when(aRepo.findById(fromId)).thenReturn(Optional.of(fromAcc));
        Mockito.when(aRepo.findById(toIban)).thenReturn(Optional.of(toAcc));

        aService.transfer(fromAcc, toIban, amount, description);

        Assertions.assertEquals(1, toAcc.getTransactions().size(),
                "Aynı müşteri transferinde işlem gönderenin listesine eklenmeliydi!");

        Assertions.assertEquals(0, fromAcc.getTransactions().size(),
                "Aynı müşteri transferinde alıcının listesi boş kalmalıydı!");
    }

    @Test
    public void testQucikTransfer_Success() {
        String customerId = "1";
        String toIban = "22";
        String fromAccId = "852";
        String description = "transfer test";

        BigDecimal amount = new BigDecimal("100");

        Customers c = new Customers();
        c.setCustomerId(customerId);

        Accounts toAcc = new Accounts();
        toAcc.setBalance(new BigDecimal("500"));
        toAcc.setCustomer(c);
        toAcc.setId(toIban);

        Accounts sourceAcc = new Accounts();
        sourceAcc.setBalance(new BigDecimal("500"));
        sourceAcc.setId(fromAccId);
        sourceAcc.setCustomer(c);

        Transaction t = new Transaction();
        t.setAmount(amount);
        t.setDescription(description);
        t.setFromAccount(sourceAcc);
        t.setToIban(toIban);

        List<Transaction> listFrom = new ArrayList<>();
        List<Transaction> listTo = new ArrayList<>();

        listFrom.add(t);
        listTo.add(t);

        List<Accounts> list = new ArrayList<>();
        list.add(sourceAcc);

        toAcc.setTransactions(listTo);
        sourceAcc.setTransactions(listFrom);

        Mockito.when(aRepo.findAllByCustomerId(customerId)).thenReturn(list);
        Mockito.when(aRepo.findById(toIban)).thenReturn(Optional.of(toAcc));
        Mockito.when(aRepo.findById(fromAccId)).thenReturn(Optional.of(sourceAcc));

        aService.qucikTransfer(customerId, toIban, amount);

        Assertions.assertEquals(new BigDecimal("400"), sourceAcc.getBalance(), "Source balans yoxdu!");

        Assertions.assertEquals(new BigDecimal("600"), toAcc.getBalance(), " balans artmadi!");
    }

    @Test
    public void testQucikTransfer_ThrowException_WhenSourceAccountNull() {
        String customerId = "1";
        String toIban = "22";

        BigDecimal amount = new BigDecimal("800");

        Customers c = new Customers();
        c.setCustomerId(customerId);

        Accounts account = new Accounts();
        account.setBalance(new BigDecimal("500"));
        account.setCustomer(c);

        List<Accounts> list = new ArrayList<>();
        list.add(account);

        Mockito.when(aRepo.findAllByCustomerId(customerId)).thenReturn(list);

        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            aService.qucikTransfer(customerId, toIban, amount);
        }, "Source balans yoxdu!");
    }
}
