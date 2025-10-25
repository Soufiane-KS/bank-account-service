package org.sid.bank_account_service.mappers;

import org.sid.bank_account_service.dto.BankAccountRequestDTO;
import org.sid.bank_account_service.dto.BankAccountResponseDTO;
import org.sid.bank_account_service.entities.AccountProjection;
import org.sid.bank_account_service.entities.BankAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class AccountMapper {
    
    public BankAccountResponseDTO fromBankAccount(BankAccount bankAccount)
    {
        BankAccountResponseDTO bankAccountResponseDTO = new BankAccountResponseDTO();
        BeanUtils.copyProperties(bankAccount, bankAccountResponseDTO);
        return bankAccountResponseDTO;
    }
    
    public BankAccount fromBankAccountRequestDTO(BankAccountRequestDTO bankAccountRequestDTO)
    {
        BankAccount bankAccount = new BankAccount();
        BeanUtils.copyProperties(bankAccountRequestDTO, bankAccount);
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        return bankAccount;
    }
    
    public BankAccountResponseDTO fromAccountProjection(AccountProjection accountProjection)
    {
        BankAccountResponseDTO bankAccountResponseDTO = new BankAccountResponseDTO();
        bankAccountResponseDTO.setId(accountProjection.getId());
        bankAccountResponseDTO.setType(accountProjection.getType());
        bankAccountResponseDTO.setBalance(accountProjection.getBalance());
        return bankAccountResponseDTO;
    }
}
