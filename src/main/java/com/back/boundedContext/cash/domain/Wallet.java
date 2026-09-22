package com.back.boundedContext.cash.domain;

import com.back.global.jpa.entity.BaseEntity;
import com.back.global.jpa.entity.BaseManualIdAndTime;
import com.back.shared.Cash.dto.WalletDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity
@Table(name = "CASH_WALLET")
@NoArgsConstructor
@Getter
public class Wallet extends BaseManualIdAndTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private CashMember holder;

    @Getter
    private long balance;

    @OneToMany(mappedBy = "wallet", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<CashLog> cashLogs = new ArrayList<>();

    public Wallet(CashMember holder) {
        super(holder.getId());
        this.holder = holder;
    }

    public boolean hasBalance() {
        return balance > 0;
    }

    // 자발적 잔액 충전 외 다른 캐시 이벤트 등록 시 사용하는 메소드로 보임
    public void credit(long amount, CashLog.EventType eventType, String relTypeCode, int relId) {
        balance += amount;

        addCashLog(amount, eventType, relTypeCode, relId);
    }
    // (Wallet의 주인 holder 의 최상위 부모 추상메소드를 통해 id와 클래스 정보를 가져옴 (= CashMember [자발적 잔액 충전])
    public void credit(long amount, CashLog.EventType eventType, BaseEntity rel) {
        credit(amount, eventType, rel.getModelTypeCode(), rel.getId());
    }
    // (자발적 잔액 충전, ... ) 에 경우 사용되는 메소드
    public void credit(long amount, CashLog.EventType eventType) {
        credit(amount, eventType, holder);
    }

    public void debit(long amount, CashLog.EventType eventType, String relTypeCode, int relId) {
        balance -= amount;

        addCashLog(-amount, eventType, relTypeCode, relId);
    }

    public void debit(long amount, CashLog.EventType eventType, BaseEntity rel) {
        debit(amount, eventType, rel.getModelTypeCode(), rel.getId());
    }

    public void debit(long amount, CashLog.EventType eventType) {
        debit(amount, eventType, holder);
    }

    private CashLog addCashLog(long amount, CashLog.EventType eventType, String relTypeCode, int relId) {
        CashLog cashLog = new CashLog(
                eventType,
                relTypeCode,
                relId,
                holder,
                this,
                amount,
                balance
        );

        cashLogs.add(cashLog);

        return cashLog;
    }

    public WalletDto toDto() {
        return new WalletDto(
            this.getId(),
            this.getCreateDate(),
            this.getModifyDate(),
            this.getHolder().getId(),
            this.getHolder().getUsername(),
            this.getBalance()
        );
    }
}
