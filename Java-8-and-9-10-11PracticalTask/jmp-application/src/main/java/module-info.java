module jmp.application {
    uses org.bank.api.Bank;
    uses org.service.api.BankService;
    requires jmp.service.api;
    requires jmp.cloud.service.impl;
    requires jmp.bank.api;
    requires jmp.cloud.bank.impl;
    requires jmp.dto;
}