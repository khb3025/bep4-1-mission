package com.back.shared.Cash.event;

import com.back.shared.Cash.dto.CashMemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CashMemberCreateEvent {
    private final CashMemberDto cashMemberDto;
}
