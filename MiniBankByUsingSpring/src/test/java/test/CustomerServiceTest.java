package test;

import com.company.MiniBankByUsingSpring.accounts.entity.Accounts;
import com.company.MiniBankByUsingSpring.accounts.entity.CheckingAccount;
import com.company.MiniBankByUsingSpring.customers.entity.Customers;
import com.company.MiniBankByUsingSpring.customers.repository.CustomerRepository;
import com.company.MiniBankByUsingSpring.customers.service.impl.CustomerServiceImpl;
import com.company.MiniBankByUsingSpring.accounts.service.inter.AccountServiceInter;
import com.company.MiniBankByUsingSpring.util.IdentifierUtil;
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
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository cRepo;

    @InjectMocks
    CustomerServiceImpl cService;

    @Mock
    private AccountServiceInter aService;

    @Test
    public void testAddCustomer_Success() {
        Customers c = new Customers();
        String cId = "1";
        BigDecimal balance = BigDecimal.ZERO;

        Accounts acc = new CheckingAccount();
        String type = "CHECKING";
        acc.setBalance(balance);

        try (MockedStatic<IdentifierUtil> mockedUtil = Mockito.mockStatic(IdentifierUtil.class)) {
            mockedUtil.when(() -> IdentifierUtil.generateId(10)).thenReturn(cId);

            Mockito.when(aService.createAccount(type, balance)).thenReturn(acc);

            boolean result = cService.addCustomer(c, type);

            Assertions.assertTrue(result, "result true donmelidir");
            Assertions.assertEquals(cId, c.getCustomerId(), "customer'e duzgun Id generate olunub mu?");
            Assertions.assertEquals(1, c.getAccounts().size(), "account elave olunmayib!");
            Assertions.assertEquals(c, acc.getCustomer(), "account ile customer matched olmayib!");
            Assertions.assertEquals(acc, c.getAccounts().get(0), "account elave olunmayib!");

            Mockito.verify(cRepo, Mockito.times(1)).save(c);
        }
    }

    @Test
    public void testAddCustomer_WhenCustomerIdNotNull() {
        Customers c = new Customers();
        String cId = "1";
        c.setCustomerId(cId);

        BigDecimal balance = BigDecimal.ZERO;

        Accounts acc = new CheckingAccount();
        String type = "CHECKING";
        acc.setBalance(balance);

        try (MockedStatic<IdentifierUtil> mockedUtil = Mockito.mockStatic(IdentifierUtil.class)) {
            mockedUtil.when(() -> IdentifierUtil.generateId(10)).thenReturn(cId);

            Mockito.when(aService.createAccount(type, balance)).thenReturn(acc);

            cService.addCustomer(c, type);

            Assertions.assertEquals(cId, c.getCustomerId(), "customer'e duzgun Id generate olunub mu?");

            mockedUtil.verify(() -> IdentifierUtil.generateId(Mockito.anyInt()), Mockito.never());
        }
    }

    @Test
    public void testAddCustomerDefaultType_WhenCustomerIdNotNull() {
        Customers c = new Customers();
        String cId = "1";
        c.setCustomerId(cId);

        BigDecimal balance = BigDecimal.ZERO;

        Accounts acc = new CheckingAccount();
        acc.setBalance(balance);

        try (MockedStatic<IdentifierUtil> mockedUtil = Mockito.mockStatic(IdentifierUtil.class)) {
            mockedUtil.when(() -> IdentifierUtil.generateId(10)).thenReturn(cId);

            Mockito.when(aService.createAccount("CHECKING", balance)).thenReturn(acc);

            cService.addCustomer(c);

            Assertions.assertEquals(cId, c.getCustomerId(), "customer'e duzgun Id generate olunub mu?");

            Mockito.verify(cRepo, Mockito.times(1)).save(c);
        }
    }

    @Test
    public void testAddCustomerDefaultType_Success() {
        Customers c = new Customers();
        String cId = "1";

        BigDecimal balance = BigDecimal.ZERO;

        Accounts acc = new CheckingAccount();
        acc.setBalance(balance);

        try (MockedStatic<IdentifierUtil> mockedUtil = Mockito.mockStatic(IdentifierUtil.class)) {
            mockedUtil.when(() -> IdentifierUtil.generateId(10)).thenReturn(cId);

            Mockito.when(aService.createAccount("CHECKING", balance)).thenReturn(acc);

            cService.addCustomer(c);

            Assertions.assertEquals(cId, c.getCustomerId(), "customer'e duzgun Id generate olunub mu?");

            Mockito.verify(cRepo, Mockito.times(1)).save(c);
        }
    }

    @Test
    public void testUpdateCustomer_Success() {
        Customers c = new Customers();

        cService.updateCustomer(c);

        Mockito.verify(cRepo, Mockito.times(1)).save(c);
    }

    @Test
    public void testUpdateCustomer_ThrowException_WhenFail() {
        Customers c = new Customers();

        Mockito.doThrow(RuntimeException.class).when(cRepo).save(c);

        Assertions.assertThrows(RuntimeException.class, () -> {
            cService.updateCustomer(c);
        }, "exception atmalidir!");
    }

    @Test
    public void testDeleteCustomerById_ThrowException_WhenFail() {
        String id = "2";
        Mockito.doThrow(RuntimeException.class).when(cRepo).deleteById(id);
        Assertions.assertThrows(RuntimeException.class, () -> {
            cService.deleteCustomerById(id);
        }, "exception atmalidir!");
    }

    @Test
    public void testDeleteCustomerById_Success() {
        String id = "2";
        cService.deleteCustomerById(id);
        Mockito.verify(cRepo, Mockito.times(1)).deleteById(id);
    }

    @Test
    public void testGetCustomerById_Success() {
        String id = "2";
        cService.getCustomerById(id);
        Mockito.verify(cRepo, Mockito.times(1)).findById(id);
    }

    @Test
    public void testGetCustomerById_WhenIdNull() {
        String id = "2";
        Mockito.when(cRepo.findById(id)).thenReturn(Optional.empty());

        Customers c = cService.getCustomerById(id);

        Assertions.assertNull(c, "null qayitdi");

        Mockito.verify(cRepo, Mockito.times(1)).findById(id);
    }

    @Test
    public void testFindCustomerByUsername_Success() {
        String username = "aa";
        Customers c = new Customers();
        c.setUsername(username);

        Mockito.when(cRepo.findCustomerByUsername(username)).thenReturn(c);
        Customers cc = cService.findCustomerByUsername(username);
        Assertions.assertEquals(c, cc, "customerler eynidir mi?");

        Mockito.verify(cRepo, Mockito.times(1)).findCustomerByUsername(username);
    }

    @Test
    public void testFindCustomerByUsername_WhenCustomerNull() {
        String username = "aa";
        Customers c = new Customers();
        c.setUsername(username);

        Mockito.when(cRepo.findCustomerByUsername(username)).thenReturn(null);
        Customers cc = cService.findCustomerByUsername(username);
        Assertions.assertNull(cc, "customer null donmelidir");

        Mockito.verify(cRepo, Mockito.times(1)).findCustomerByUsername(username);
    }

    @Test
    public void testGetAllCustomers_Success() {
        List<Customers> list = new ArrayList<>();

        Customers c = new Customers();

        list.add(c);

        Mockito.when(cRepo.findAll()).thenReturn(list);

        List<Customers> cc = cService.getAllCustomers();

        Assertions.assertNotNull(cc, "customer NotNull donmelidir");
        Assertions.assertEquals(list, cc, "list'ler eyni olmalidir");

        Mockito.verify(cRepo, Mockito.times(1)).findAll();
    }

    @Test
    public void testGetAllCustomers_WhenListNull() {
        List<Customers> list = new ArrayList<>();

        Customers c = new Customers();

        list.add(c);

        Mockito.when(cRepo.findAll()).thenReturn(null);

        List<Customers> cc = cService.getAllCustomers();

        Assertions.assertNull(cc, "customer null donmelidir");
        Assertions.assertNotEquals(list, cc, "list'ler ferqli olmalidir");

        Mockito.verify(cRepo, Mockito.times(1)).findAll();
    }

    @Test
    public void testGetTotalBalance_Success() {
        String cId = "1";

        List<Accounts> accList = new ArrayList<>();

        Accounts acc1 = new Accounts();
        acc1.setBalance(BigDecimal.TEN);

        Accounts acc2 = new Accounts();
        acc2.setBalance(BigDecimal.TWO);

        accList.add(acc1);
        accList.add(acc2);

        BigDecimal totalBalance = acc1.getBalance().add(acc2.getBalance());

        Customers c = new Customers();
        c.setAccounts(accList);
        c.setCustomerId(cId);

        Mockito.when(cRepo.findById(cId)).thenReturn(Optional.of(c));

        BigDecimal result = cService.getTotalBalance(cId);

        Assertions.assertNotNull(result, "result NotNull donmelidir");

        Assertions.assertEquals(totalBalance, result, "balansler eyni olmalidir");

        Mockito.verify(cRepo, Mockito.times(1)).findById(cId);
    }

    @Test
    public void testGetTotalBalance_WhenCustomerNull() {
        String cId = "1";

        Customers c = new Customers();
        c.setCustomerId(cId);

        Mockito.when(cRepo.findById(cId)).thenReturn(Optional.empty());

        BigDecimal result = cService.getTotalBalance(cId);

        Assertions.assertNotNull(result, "result NotNull donmelidir");
        Assertions.assertEquals(BigDecimal.ZERO, result, "result NotNull donmelidir");

        Mockito.verify(cRepo, Mockito.times(1)).findById(cId);
    }
}
