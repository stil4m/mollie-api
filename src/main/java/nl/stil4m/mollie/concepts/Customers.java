package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.domain.CreateCustomer;
import nl.stil4m.mollie.domain.Customer;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.UpdateCustomer;

public class Customers extends AbstractConcept<Customer> implements ListAll<Customer>, GetById<Customer>, Create<Customer, CreateCustomer>, Update<Customer, UpdateCustomer> {
    private static final TypeReference<Page<Customer>> PAGE_TYPE = new TypeReference<Page<Customer>>() {
    };
    private static final TypeReference<Customer> SINGLE_TYPE = new TypeReference<Customer>() {
    };

    public Customers(String apiKey, String endpoint, RequestExecutor requestExecutor) {
        super(apiKey, requestExecutor, SINGLE_TYPE, PAGE_TYPE, endpoint, "customers");
    }
}