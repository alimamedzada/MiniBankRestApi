package test;

import com.company.MiniBankByUsingSpring.entity.Accounts;
import com.company.MiniBankByUsingSpring.entity.Transaction;
import com.company.MiniBankByUsingSpring.repository.AccountRepository;
import com.company.MiniBankByUsingSpring.repository.TransactionRepository;
import com.company.MiniBankByUsingSpring.service.impl.TransactionServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository tRepo;

    @InjectMocks
    private TransactionServiceImpl tService;

    @Mock
    private AccountRepository aRepo;

    @Test
    public void testCreateTransaction_Success() {
        String fromAccountId = "1";
        String toIban = "2";
        BigDecimal amount = new BigDecimal("100");
        String description = "TT";

        Accounts fromAcc = new Accounts();
        fromAcc.setId(fromAccountId);

        Accounts toAcc = new Accounts();
        toAcc.setId(toIban);

        Mockito.when(aRepo.findById(toIban)).thenReturn(Optional.of(toAcc));
        Mockito.when(aRepo.findById(fromAccountId)).thenReturn(Optional.of(fromAcc));

        Transaction t = new Transaction();
        t.setAmount(amount);
        t.setFromAccount(fromAcc);
        t.setDescription(description);
        t.setToIban(toIban);

        Transaction result = tService.createTransaction(fromAccountId, toIban, amount, description);

        Assertions.assertEquals(t.getToIban(), result.getToIban());
        Assertions.assertEquals(t.getAmount(), result.getAmount());
        Assertions.assertEquals(t.getFromAccount(), result.getFromAccount());
        Assertions.assertEquals(t.getDescription(), result.getDescription());

        Mockito.verify(aRepo, Mockito.times(1)).findById(toIban);
        Mockito.verify(aRepo, Mockito.times(1)).findById(fromAccountId);
    }

    @Test
    public void testCreateTransaction_WhenFindAccountByIdNull() {
        String fromAccountId = "1";
        String toIban = "2";
        BigDecimal amount = new BigDecimal("100");
        String description = "TT";

        Mockito.when(aRepo.findById(fromAccountId)).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tService.createTransaction(fromAccountId, toIban, amount, description);
        }, "exception atmalidir");

        Mockito.verify(aRepo, Mockito.times(1)).findById(fromAccountId);
        Mockito.verify(aRepo, Mockito.never()).findById(toIban);
    }

    @Test
    public void testGetTransactionsByAccountId_Success() {
        String accountId = "1";
        List<Transaction> list = new ArrayList<>();

        Transaction t = new Transaction();
        t.setId("22");
        list.add(t);

        Mockito.when(tRepo.findByToIban(accountId)).thenReturn(list);

        List<Transaction> list1 = tService.getTransactionsByAccountId(accountId);

        Assertions.assertNotNull(list1);
        Assertions.assertEquals(list, list1);
        Assertions.assertEquals(list.size(), list1.size());
        Assertions.assertSame(list.get(0).getId(), list1.get(0).getId());

        Mockito.verify(tRepo, Mockito.times(1)).findByToIban(accountId);
    }

    @Test
    public void testAddTransaction_Success() {
        Transaction t = new Transaction();

        Mockito.when(tRepo.save(t)).thenReturn(t);

        boolean result = tService.addTransaction(t);

        Assertions.assertTrue(result);

        Mockito.verify(tRepo, Mockito.times(1)).save(t);
    }

    @Test
    public void testAddTransaction_ThrowException_WhenSaveFail() {
        Transaction t = new Transaction();

        Mockito.when(tRepo.save(t)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> {
            tService.addTransaction(t);
        }, "exception atmalidir!");
    }

    @Test
    public void testDeleteTransaction_Success() {
        String tId = "1";

        boolean result = tService.deleteTransactionById(tId);

        Assertions.assertTrue(result);

        Mockito.verify(tRepo, Mockito.times(1)).deleteById(tId);
    }

    @Test
    public void testDeleteTransaction_ThrowException_WhenDeleteByIdFail() {
        String tId = "1";

        Mockito.doThrow(new RuntimeException("exception atdi")).when(tRepo).deleteById(tId);

        Assertions.assertThrows(RuntimeException.class, () -> {
            tService.deleteTransactionById(tId);
        }, "Exception atmalidir!");

        Mockito.verify(tRepo, Mockito.times(1)).deleteById(tId);
    }

    @Test
    public void testUpdateTransaction_Success() {
        Transaction t = new Transaction();

        Mockito.when(tRepo.save(t)).thenReturn(t);

        boolean result = tService.updateTransaction(t);

        Assertions.assertTrue(result);

        Mockito.verify(tRepo, Mockito.times(1)).save(t);
    }

    @Test
    public void testUpdateTransaction_ThrowException_WhenSaveFail() {
        Transaction t = new Transaction();
        Mockito.when(tRepo.save(t)).thenThrow(new IllegalArgumentException("exception atdi"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tService.updateTransaction(t);
        }, "Exception atmalidir!");

        Mockito.verify(tRepo, Mockito.times(1)).save(t);
    }

    @Test
    public void testGetAllTransactions_Success() {
        List<Transaction> list = new ArrayList<>();

        list.add(new Transaction());
        Mockito.when(tRepo.findAll()).thenReturn(list);

        List<Transaction> list1 = tService.getAllTransactions();

        Assertions.assertNotNull(list1);
        Assertions.assertEquals(list.size(), list1.size());

        Mockito.verify(tRepo, Mockito.times(1)).findAll();
    }

    @Test
    public void testGetAllTransactions_ThrowException_WhenSaveFail() {
        Mockito.when(tRepo.findAll()).thenThrow(new NoSuchElementException("exception atdi"));

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            tService.getAllTransactions();
        }, "Exception atmalidir!");

        Mockito.verify(tRepo, Mockito.times(1)).findAll();
    }

    @Test
    public void testGetTransactionsByCustomerId_Success() {
        String cId = "2";
        List<Transaction> list = new ArrayList<>();

        list.add(new Transaction());
        Mockito.when(tRepo.findAllByCustomerId(cId)).thenReturn(list);

        List<Transaction> list1 = tService.getTransactionsByCustomerId(cId);

        Assertions.assertNotNull(list1);
        Assertions.assertEquals(list.size(), list1.size());
        Mockito.verify(tRepo, Mockito.times(1)).findAllByCustomerId(cId);
    }

    @Test
    public void testGetTransactionsByCustomerId_ThrowException_WhenSaveFail() {
        String cId = "2";

        Mockito.when(tRepo.findAllByCustomerId(cId)).thenThrow(new NoSuchElementException("exception atdi"));

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            tService.getTransactionsByCustomerId(cId);
        }, "Exception atmalidir!");

        Mockito.verify(tRepo, Mockito.times(1)).findAllByCustomerId(cId);
    }

    @Test
    public void testGetRecentTransactionsByCustomerId_Success() {
        String cId = "2";
        int limit = 5;

        List<Transaction> trlist = new ArrayList<>();
        List<Transaction> trlist1 = new ArrayList<>();
        List<Transaction> trlist2 = new ArrayList<>();
        List<Transaction> trlist3 = new ArrayList<>();

        trlist.add(new Transaction());
        trlist1.add(new Transaction());
        trlist2.add(new Transaction());
        trlist3.add(new Transaction());

        List<Accounts> list = new ArrayList<>();

        Accounts acc1 = new Accounts();
        acc1.setId("1");

        Accounts acc2 = new Accounts();
        acc2.setId("2");

        Accounts acc3 = new Accounts();
        acc3.setId("3");

        list.add(acc1);
        list.add(acc2);
        list.add(acc3);

        List<String> accIds = new ArrayList<>();

        accIds.add(acc1.getId());
        accIds.add(acc2.getId());
        accIds.add(acc3.getId());

        Mockito.when(aRepo.findAllByCustomerId(cId)).thenReturn(list);

        Mockito.when(tRepo.findCustomerTransactionHistory(Mockito.eq(accIds),
                Mockito.any(Pageable.class))).thenReturn(trlist);

        List<Transaction> list1 = tService.getRecentTransactionsByCustomerId(
                cId, limit);

        Assertions.assertNotNull(list1);
        Assertions.assertEquals(trlist.size(), list1.size());

        Mockito.verify(aRepo, Mockito.times(1)).findAllByCustomerId(cId);
        Mockito.verify(tRepo, Mockito.times(1)).findCustomerTransactionHistory(
                Mockito.eq(accIds), Mockito.any(Pageable.class));
    }
}
