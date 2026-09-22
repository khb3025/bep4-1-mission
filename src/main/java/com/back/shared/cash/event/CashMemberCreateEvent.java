package com.back.shared.cash.event;

import com.back.shared.cash.dto.CashMemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CashMemberCreateEvent {
    private final CashMemberDto cashMemberDto;
}
