module jmp.cloud.bank.impl {
    requires transitive jmp.bank.api;
    requires jmp.dto;
    provides org.bank.api.Bank with
            org.cloud.bank.api.InvestmentBank,
            org.cloud.bank.api.CentralBank,
            org.cloud.bank.api.RetailBank;

    exports org.cloud.bank.api;
}