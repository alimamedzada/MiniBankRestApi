package com.company.MiniBankWebAppByUsingRest.controller;

import com.company.MiniBankByUsingSpring.service.inter.CustomerServiceInter;
import com.company.MiniBankByUsingSpring.service.inter.TransactionServiceInter;
import com.company.MiniBankWebAppByUsingRest.dto.AccountsDTO;
import com.company.MiniBankWebAppByUsingRest.dto.CustomersDTO;
import com.company.MiniBankWebAppByUsingRest.dto.ResponseDTO;
import com.company.MiniBankWebAppByUsingRest.dto.TransactionDTO;
import com.company.MiniBankWebAppByUsingRest.dto.TransactionHistoryResponseDTO;
import com.company.MiniBankWebAppByUsingRest.mapper.CustomersMapper;
import com.company.MiniBankWebAppByUsingRest.mapper.TransactionMapper;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class TransactionHistoryRestController {

    private final CustomerServiceInter cService;
    private final CustomersMapper cMapper;
    private final TransactionServiceInter tService;
    private final TransactionMapper tMapper;

    public TransactionHistoryRestController(CustomerServiceInter cService,TransactionMapper tMapper,CustomersMapper cMapper,TransactionServiceInter tService){
        this.tMapper=tMapper;
        this.cMapper= cMapper;
        this.cService=cService;
        this.tService=tService;
    }
    @GetMapping("/transaction-history")
    public ResponseEntity<ResponseDTO> showTransactionHistoryPage(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String type,
            Principal principal
    ) {

        CustomersDTO customer = cMapper.toDto(cService.findCustomerByUsername(principal.getName()));

        List<TransactionDTO> listT = tMapper.toDtoList(tService.getTransactionsByCustomerId(customer.getCustomerId()));

        List<String> myAccountIds = new ArrayList<>();
        for (AccountsDTO acc : customer.getAccounts()) {
            myAccountIds.add(acc.getId());
        }
        for (TransactionDTO tDto : listT) {
            boolean isFromMe = myAccountIds.contains(tDto.getFromAccountId());
            boolean isToMe = myAccountIds.contains(tDto.getToIban());

            if (isFromMe && isToMe) {
                tDto.setTransactionType("INTERNAL");
            } else if (isFromMe) {
                tDto.setTransactionType("OUTGOING");
            } else if (isToMe) {
                tDto.setTransactionType("INCOMING");
            }
        }
        List<TransactionDTO> filteredList = new ArrayList<>();
        for (TransactionDTO t : listT) {
            boolean matchKeyword = true;
            boolean matchType = true;
            boolean matchDate = true;
            if (keyword != null && !keyword.trim().isEmpty()) {
                String lowerKeyword = keyword.toLowerCase();
                boolean inDesc = t.getDescription() != null && t.getDescription().toLowerCase().contains(lowerKeyword);
                boolean inIban = t.getToIban() != null && t.getToIban().toLowerCase().contains(lowerKeyword);
                if (!inDesc && !inIban) {
                    matchKeyword = false;
                }
            }
            if (type != null && !type.trim().isEmpty() && !"ALL".equalsIgnoreCase(type.trim())) {
                if (t.getTransactionType() == null || !t.getTransactionType().equals(type)) {
                    matchType = false;
                }
            }
            if (date != null && !date.trim().isEmpty()) {
                if (t.getTransactionDate() == null || !t.getTransactionDate().toLocalDate().toString().equals(date)) {
                    matchDate = false;
                }
            }
            if (matchDate && matchType && matchKeyword) {
                filteredList.add(t);
            }
        }
        TransactionHistoryResponseDTO thDto = new TransactionHistoryResponseDTO();

        thDto.setCustomer(customer);
        thDto.setFilteredList(filteredList);

        return ResponseEntity.ok(ResponseDTO.of(thDto));

    }

}
