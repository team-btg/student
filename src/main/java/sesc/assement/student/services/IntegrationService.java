package sesc.assement.student.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sesc.assement.student.models.*;

import javax.validation.constraints.NotNull;

@Service
public class IntegrationService {
    private final RestTemplate restTemplate;

    @Value("${financeapp.url}")
    private String financeAppUrl;

    @Value("${financeapp.accounts}")
    private String financeAppAccounts;

    @Value("${financeapp.transactions}")
    private String financeAppInvoices;

    @Value("${financeapp.transactionsByStudent}")
    private String financeAppInvoiceByStudent;

    @Value("${library.url}")
    private String libraryAppUrl;

    @Value("${library.account}")
    private String libraryAccount;

    public IntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void notifyStudentCreated(@NotNull Account account) {
        restTemplate.postForObject(financeAppUrl + financeAppAccounts, account, Account.class);
        restTemplate.postForObject(libraryAppUrl + libraryAccount, account, Account.class);
    }

    public Invoice createCourseFeeInvoice(@NotNull Invoice invoice) {
        return restTemplate.postForObject(financeAppUrl + financeAppInvoices, invoice, Invoice.class);
    }

    public Account getStudentPaymentStatus(@NotNull Student student) {
        return restTemplate.getForObject(financeAppUrl + financeAppInvoiceByStudent + student.getStudentId(), Account.class);
    }
}
