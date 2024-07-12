package com.example.backend.service.mypage;

import com.example.backend.dto.mypage.accountSettings.AccountDTO;
import com.example.backend.dto.mypage.accountSettings.AccountReqDTO;
import com.example.backend.entity.Account;
import com.example.backend.entity.Users;
import com.example.backend.repository.mypage.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    /**
     * 등록된 계좌 조회
     * 1 : 1
     */
    public AccountDTO getAccount(Long userId){
        Account account = accountRepository.findByUserUserId(userId);

        if(account == null) { return null; }

        return AccountDTO.fromEntity(account);
    }

    /**
     * 계좌 등록 및 변경
     */
    @Transactional
    public AccountDTO updateAccount(Long userId, AccountReqDTO accountReqDTO){
        Account account = accountRepository.findByUserUserId(userId);

        if (account == null) {
            account = Account.builder()
                    .user(Users.builder().userId(userId).build())
                    .depositor(accountReqDTO.getDepositor())
                    .bankName(accountReqDTO.getBankName())
                    .accountNum(accountReqDTO.getAccountNum())
                    .build();
        }

        account.updateAccount(accountReqDTO);

        accountRepository.save(account);

        return AccountDTO.fromEntity(account);
    }
}
