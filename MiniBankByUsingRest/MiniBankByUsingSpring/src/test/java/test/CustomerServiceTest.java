package test;

import com.company.MiniBankByUsingSpring.entity.Accounts;
import com.company.MiniBankByUsingSpring.entity.CheckingAccount;
import com.company.MiniBankByUsingSpring.entity.Customers;
import com.company.MiniBankByUsingSpring.repository.CustomerRepository;
import com.company.MiniBankByUsingSpring.service.impl.CustomerServiceImpl;
import com.company.MiniBankByUsingSpring.service.inter.AccountServiceInter;
import com.company.MiniBankByUsingSpring.util.IdentifierUtil;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

        BigDecimal balance = new BigDecimal("100");

        Mockito.when(IdentifierUtil.generateId(10)).thenReturn(cId);

        Accounts acc = new CheckingAccount();
        String type = "CHECKING";
        acc.setBalance(balance);

        Mockito.when(aService.createAccount(type, balance)).thenReturn(acc);
    }

}
