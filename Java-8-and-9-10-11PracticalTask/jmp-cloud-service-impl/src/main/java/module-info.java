module jmp.cloud.service.impl {
    requires transitive jmp.service.api;
    requires jmp.dto;
    exports org.cloud.service.api;
    provides org.service.api.BankService with org.cloud.service.api.BankServiceImpl;
}