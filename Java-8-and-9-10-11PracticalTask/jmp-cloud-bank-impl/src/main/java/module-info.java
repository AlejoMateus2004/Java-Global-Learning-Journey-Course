module jmp.cloud.bank.impl {
    requires transitive jmp.bank.api;
    requires jmp.dto;
    provides org.bank.api.Bank with org.cloud.bank.api.BankImpl;
    exports org.cloud.bank.api;
}