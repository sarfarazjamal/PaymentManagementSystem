package com.jamal.payment.management.web.rest;

import com.jamal.payment.management.PaymentManagementApp;
import com.jamal.payment.management.domain.TransactionDetails;
import com.jamal.payment.management.domain.ClientInfo;
import com.jamal.payment.management.repository.TransactionDetailsRepository;
import com.jamal.payment.management.service.TransactionDetailsService;
import com.jamal.payment.management.service.dto.TransactionDetailsDTO;
import com.jamal.payment.management.service.mapper.TransactionDetailsMapper;
import com.jamal.payment.management.service.dto.TransactionDetailsCriteria;
import com.jamal.payment.management.service.TransactionDetailsQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jamal.payment.management.domain.enumeration.CurrencyEnum;
import com.jamal.payment.management.domain.enumeration.CardTypeEnum;
import com.jamal.payment.management.domain.enumeration.TransactionStatusEnum;
/**
 * Integration tests for the {@link TransactionDetailsResource} REST controller.
 */
@SpringBootTest(classes = PaymentManagementApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransactionDetailsResourceIT {

    private static final Long DEFAULT_ORDERID = 1L;
    private static final Long UPDATED_ORDERID = 2L;
    private static final Long SMALLER_ORDERID = 1L - 1L;

    private static final Instant DEFAULT_DATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final CurrencyEnum DEFAULT_CURRENCY = CurrencyEnum.USD;
    private static final CurrencyEnum UPDATED_CURRENCY = CurrencyEnum.AED;

    private static final CardTypeEnum DEFAULT_CARDTYPE = CardTypeEnum.VISA;
    private static final CardTypeEnum UPDATED_CARDTYPE = CardTypeEnum.MASTER;

    private static final TransactionStatusEnum DEFAULT_TRANSACTION_STATUS = TransactionStatusEnum.APPROVED;
    private static final TransactionStatusEnum UPDATED_TRANSACTION_STATUS = TransactionStatusEnum.DECLINED;

    private static final String DEFAULT_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT = "BBBBBBBBBB";

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    private TransactionDetailsMapper transactionDetailsMapper;

    @Autowired
    private TransactionDetailsService transactionDetailsService;

    @Autowired
    private TransactionDetailsQueryService transactionDetailsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionDetailsMockMvc;

    private TransactionDetails transactionDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionDetails createEntity(EntityManager em) {
        TransactionDetails transactionDetails = new TransactionDetails()
            .orderid(DEFAULT_ORDERID)
            .datetime(DEFAULT_DATETIME)
            .amount(DEFAULT_AMOUNT)
            .currency(DEFAULT_CURRENCY)
            .cardtype(DEFAULT_CARDTYPE)
            .transactionStatus(DEFAULT_TRANSACTION_STATUS)
            .client(DEFAULT_CLIENT);
        return transactionDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionDetails createUpdatedEntity(EntityManager em) {
        TransactionDetails transactionDetails = new TransactionDetails()
            .orderid(UPDATED_ORDERID)
            .datetime(UPDATED_DATETIME)
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .cardtype(UPDATED_CARDTYPE)
            .transactionStatus(UPDATED_TRANSACTION_STATUS)
            .client(UPDATED_CLIENT);
        return transactionDetails;
    }

    @BeforeEach
    public void initTest() {
        transactionDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionDetails() throws Exception {
        int databaseSizeBeforeCreate = transactionDetailsRepository.findAll().size();
        // Create the TransactionDetails
        TransactionDetailsDTO transactionDetailsDTO = transactionDetailsMapper.toDto(transactionDetails);
        restTransactionDetailsMockMvc.perform(post("/api/transaction-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionDetails in the database
        List<TransactionDetails> transactionDetailsList = transactionDetailsRepository.findAll();
        assertThat(transactionDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionDetails testTransactionDetails = transactionDetailsList.get(transactionDetailsList.size() - 1);
        assertThat(testTransactionDetails.getOrderid()).isEqualTo(DEFAULT_ORDERID);
        assertThat(testTransactionDetails.getDatetime()).isEqualTo(DEFAULT_DATETIME);
        assertThat(testTransactionDetails.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTransactionDetails.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testTransactionDetails.getCardtype()).isEqualTo(DEFAULT_CARDTYPE);
        assertThat(testTransactionDetails.getTransactionStatus()).isEqualTo(DEFAULT_TRANSACTION_STATUS);
        assertThat(testTransactionDetails.getClient()).isEqualTo(DEFAULT_CLIENT);
    }

    @Test
    @Transactional
    public void createTransactionDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionDetailsRepository.findAll().size();

        // Create the TransactionDetails with an existing ID
        transactionDetails.setId(1L);
        TransactionDetailsDTO transactionDetailsDTO = transactionDetailsMapper.toDto(transactionDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionDetailsMockMvc.perform(post("/api/transaction-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionDetails in the database
        List<TransactionDetails> transactionDetailsList = transactionDetailsRepository.findAll();
        assertThat(transactionDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTransactionDetails() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList
        restTransactionDetailsMockMvc.perform(get("/api/transaction-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderid").value(hasItem(DEFAULT_ORDERID.intValue())))
            .andExpect(jsonPath("$.[*].datetime").value(hasItem(DEFAULT_DATETIME.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].cardtype").value(hasItem(DEFAULT_CARDTYPE.toString())))
            .andExpect(jsonPath("$.[*].transactionStatus").value(hasItem(DEFAULT_TRANSACTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT)));
    }
    
    @Test
    @Transactional
    public void getTransactionDetails() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get the transactionDetails
        restTransactionDetailsMockMvc.perform(get("/api/transaction-details/{id}", transactionDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactionDetails.getId().intValue()))
            .andExpect(jsonPath("$.orderid").value(DEFAULT_ORDERID.intValue()))
            .andExpect(jsonPath("$.datetime").value(DEFAULT_DATETIME.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.cardtype").value(DEFAULT_CARDTYPE.toString()))
            .andExpect(jsonPath("$.transactionStatus").value(DEFAULT_TRANSACTION_STATUS.toString()))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT));
    }


    @Test
    @Transactional
    public void getTransactionDetailsByIdFiltering() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        Long id = transactionDetails.getId();

        defaultTransactionDetailsShouldBeFound("id.equals=" + id);
        defaultTransactionDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultTransactionDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransactionDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultTransactionDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransactionDetailsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTransactionDetailsByOrderidIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where orderid equals to DEFAULT_ORDERID
        defaultTransactionDetailsShouldBeFound("orderid.equals=" + DEFAULT_ORDERID);

        // Get all the transactionDetailsList where orderid equals to UPDATED_ORDERID
        defaultTransactionDetailsShouldNotBeFound("orderid.equals=" + UPDATED_ORDERID);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByOrderidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where orderid not equals to DEFAULT_ORDERID
        defaultTransactionDetailsShouldNotBeFound("orderid.notEquals=" + DEFAULT_ORDERID);

        // Get all the transactionDetailsList where orderid not equals to UPDATED_ORDERID
        defaultTransactionDetailsShouldBeFound("orderid.notEquals=" + UPDATED_ORDERID);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByOrderidIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where orderid in DEFAULT_ORDERID or UPDATED_ORDERID
        defaultTransactionDetailsShouldBeFound("orderid.in=" + DEFAULT_ORDERID + "," + UPDATED_ORDERID);

        // Get all the transactionDetailsList where orderid equals to UPDATED_ORDERID
        defaultTransactionDetailsShouldNotBeFound("orderid.in=" + UPDATED_ORDERID);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByOrderidIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where orderid is not null
        defaultTransactionDetailsShouldBeFound("orderid.specified=true");

        // Get all the transactionDetailsList where orderid is null
        defaultTransactionDetailsShouldNotBeFound("orderid.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByOrderidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where orderid is greater than or equal to DEFAULT_ORDERID
        defaultTransactionDetailsShouldBeFound("orderid.greaterThanOrEqual=" + DEFAULT_ORDERID);

        // Get all the transactionDetailsList where orderid is greater than or equal to UPDATED_ORDERID
        defaultTransactionDetailsShouldNotBeFound("orderid.greaterThanOrEqual=" + UPDATED_ORDERID);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByOrderidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where orderid is less than or equal to DEFAULT_ORDERID
        defaultTransactionDetailsShouldBeFound("orderid.lessThanOrEqual=" + DEFAULT_ORDERID);

        // Get all the transactionDetailsList where orderid is less than or equal to SMALLER_ORDERID
        defaultTransactionDetailsShouldNotBeFound("orderid.lessThanOrEqual=" + SMALLER_ORDERID);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByOrderidIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where orderid is less than DEFAULT_ORDERID
        defaultTransactionDetailsShouldNotBeFound("orderid.lessThan=" + DEFAULT_ORDERID);

        // Get all the transactionDetailsList where orderid is less than UPDATED_ORDERID
        defaultTransactionDetailsShouldBeFound("orderid.lessThan=" + UPDATED_ORDERID);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByOrderidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where orderid is greater than DEFAULT_ORDERID
        defaultTransactionDetailsShouldNotBeFound("orderid.greaterThan=" + DEFAULT_ORDERID);

        // Get all the transactionDetailsList where orderid is greater than SMALLER_ORDERID
        defaultTransactionDetailsShouldBeFound("orderid.greaterThan=" + SMALLER_ORDERID);
    }


    @Test
    @Transactional
    public void getAllTransactionDetailsByDatetimeIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where datetime equals to DEFAULT_DATETIME
        defaultTransactionDetailsShouldBeFound("datetime.equals=" + DEFAULT_DATETIME);

        // Get all the transactionDetailsList where datetime equals to UPDATED_DATETIME
        defaultTransactionDetailsShouldNotBeFound("datetime.equals=" + UPDATED_DATETIME);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByDatetimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where datetime not equals to DEFAULT_DATETIME
        defaultTransactionDetailsShouldNotBeFound("datetime.notEquals=" + DEFAULT_DATETIME);

        // Get all the transactionDetailsList where datetime not equals to UPDATED_DATETIME
        defaultTransactionDetailsShouldBeFound("datetime.notEquals=" + UPDATED_DATETIME);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByDatetimeIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where datetime in DEFAULT_DATETIME or UPDATED_DATETIME
        defaultTransactionDetailsShouldBeFound("datetime.in=" + DEFAULT_DATETIME + "," + UPDATED_DATETIME);

        // Get all the transactionDetailsList where datetime equals to UPDATED_DATETIME
        defaultTransactionDetailsShouldNotBeFound("datetime.in=" + UPDATED_DATETIME);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByDatetimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where datetime is not null
        defaultTransactionDetailsShouldBeFound("datetime.specified=true");

        // Get all the transactionDetailsList where datetime is null
        defaultTransactionDetailsShouldNotBeFound("datetime.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where amount equals to DEFAULT_AMOUNT
        defaultTransactionDetailsShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the transactionDetailsList where amount equals to UPDATED_AMOUNT
        defaultTransactionDetailsShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where amount not equals to DEFAULT_AMOUNT
        defaultTransactionDetailsShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the transactionDetailsList where amount not equals to UPDATED_AMOUNT
        defaultTransactionDetailsShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultTransactionDetailsShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the transactionDetailsList where amount equals to UPDATED_AMOUNT
        defaultTransactionDetailsShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where amount is not null
        defaultTransactionDetailsShouldBeFound("amount.specified=true");

        // Get all the transactionDetailsList where amount is null
        defaultTransactionDetailsShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultTransactionDetailsShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the transactionDetailsList where amount is greater than or equal to UPDATED_AMOUNT
        defaultTransactionDetailsShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where amount is less than or equal to DEFAULT_AMOUNT
        defaultTransactionDetailsShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the transactionDetailsList where amount is less than or equal to SMALLER_AMOUNT
        defaultTransactionDetailsShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where amount is less than DEFAULT_AMOUNT
        defaultTransactionDetailsShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the transactionDetailsList where amount is less than UPDATED_AMOUNT
        defaultTransactionDetailsShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where amount is greater than DEFAULT_AMOUNT
        defaultTransactionDetailsShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the transactionDetailsList where amount is greater than SMALLER_AMOUNT
        defaultTransactionDetailsShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllTransactionDetailsByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where currency equals to DEFAULT_CURRENCY
        defaultTransactionDetailsShouldBeFound("currency.equals=" + DEFAULT_CURRENCY);

        // Get all the transactionDetailsList where currency equals to UPDATED_CURRENCY
        defaultTransactionDetailsShouldNotBeFound("currency.equals=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByCurrencyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where currency not equals to DEFAULT_CURRENCY
        defaultTransactionDetailsShouldNotBeFound("currency.notEquals=" + DEFAULT_CURRENCY);

        // Get all the transactionDetailsList where currency not equals to UPDATED_CURRENCY
        defaultTransactionDetailsShouldBeFound("currency.notEquals=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where currency in DEFAULT_CURRENCY or UPDATED_CURRENCY
        defaultTransactionDetailsShouldBeFound("currency.in=" + DEFAULT_CURRENCY + "," + UPDATED_CURRENCY);

        // Get all the transactionDetailsList where currency equals to UPDATED_CURRENCY
        defaultTransactionDetailsShouldNotBeFound("currency.in=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where currency is not null
        defaultTransactionDetailsShouldBeFound("currency.specified=true");

        // Get all the transactionDetailsList where currency is null
        defaultTransactionDetailsShouldNotBeFound("currency.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByCardtypeIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where cardtype equals to DEFAULT_CARDTYPE
        defaultTransactionDetailsShouldBeFound("cardtype.equals=" + DEFAULT_CARDTYPE);

        // Get all the transactionDetailsList where cardtype equals to UPDATED_CARDTYPE
        defaultTransactionDetailsShouldNotBeFound("cardtype.equals=" + UPDATED_CARDTYPE);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByCardtypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where cardtype not equals to DEFAULT_CARDTYPE
        defaultTransactionDetailsShouldNotBeFound("cardtype.notEquals=" + DEFAULT_CARDTYPE);

        // Get all the transactionDetailsList where cardtype not equals to UPDATED_CARDTYPE
        defaultTransactionDetailsShouldBeFound("cardtype.notEquals=" + UPDATED_CARDTYPE);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByCardtypeIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where cardtype in DEFAULT_CARDTYPE or UPDATED_CARDTYPE
        defaultTransactionDetailsShouldBeFound("cardtype.in=" + DEFAULT_CARDTYPE + "," + UPDATED_CARDTYPE);

        // Get all the transactionDetailsList where cardtype equals to UPDATED_CARDTYPE
        defaultTransactionDetailsShouldNotBeFound("cardtype.in=" + UPDATED_CARDTYPE);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByCardtypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where cardtype is not null
        defaultTransactionDetailsShouldBeFound("cardtype.specified=true");

        // Get all the transactionDetailsList where cardtype is null
        defaultTransactionDetailsShouldNotBeFound("cardtype.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByTransactionStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where transactionStatus equals to DEFAULT_TRANSACTION_STATUS
        defaultTransactionDetailsShouldBeFound("transactionStatus.equals=" + DEFAULT_TRANSACTION_STATUS);

        // Get all the transactionDetailsList where transactionStatus equals to UPDATED_TRANSACTION_STATUS
        defaultTransactionDetailsShouldNotBeFound("transactionStatus.equals=" + UPDATED_TRANSACTION_STATUS);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByTransactionStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where transactionStatus not equals to DEFAULT_TRANSACTION_STATUS
        defaultTransactionDetailsShouldNotBeFound("transactionStatus.notEquals=" + DEFAULT_TRANSACTION_STATUS);

        // Get all the transactionDetailsList where transactionStatus not equals to UPDATED_TRANSACTION_STATUS
        defaultTransactionDetailsShouldBeFound("transactionStatus.notEquals=" + UPDATED_TRANSACTION_STATUS);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByTransactionStatusIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where transactionStatus in DEFAULT_TRANSACTION_STATUS or UPDATED_TRANSACTION_STATUS
        defaultTransactionDetailsShouldBeFound("transactionStatus.in=" + DEFAULT_TRANSACTION_STATUS + "," + UPDATED_TRANSACTION_STATUS);

        // Get all the transactionDetailsList where transactionStatus equals to UPDATED_TRANSACTION_STATUS
        defaultTransactionDetailsShouldNotBeFound("transactionStatus.in=" + UPDATED_TRANSACTION_STATUS);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByTransactionStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where transactionStatus is not null
        defaultTransactionDetailsShouldBeFound("transactionStatus.specified=true");

        // Get all the transactionDetailsList where transactionStatus is null
        defaultTransactionDetailsShouldNotBeFound("transactionStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByClientIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where client equals to DEFAULT_CLIENT
        defaultTransactionDetailsShouldBeFound("client.equals=" + DEFAULT_CLIENT);

        // Get all the transactionDetailsList where client equals to UPDATED_CLIENT
        defaultTransactionDetailsShouldNotBeFound("client.equals=" + UPDATED_CLIENT);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByClientIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where client not equals to DEFAULT_CLIENT
        defaultTransactionDetailsShouldNotBeFound("client.notEquals=" + DEFAULT_CLIENT);

        // Get all the transactionDetailsList where client not equals to UPDATED_CLIENT
        defaultTransactionDetailsShouldBeFound("client.notEquals=" + UPDATED_CLIENT);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByClientIsInShouldWork() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where client in DEFAULT_CLIENT or UPDATED_CLIENT
        defaultTransactionDetailsShouldBeFound("client.in=" + DEFAULT_CLIENT + "," + UPDATED_CLIENT);

        // Get all the transactionDetailsList where client equals to UPDATED_CLIENT
        defaultTransactionDetailsShouldNotBeFound("client.in=" + UPDATED_CLIENT);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByClientIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where client is not null
        defaultTransactionDetailsShouldBeFound("client.specified=true");

        // Get all the transactionDetailsList where client is null
        defaultTransactionDetailsShouldNotBeFound("client.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionDetailsByClientContainsSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where client contains DEFAULT_CLIENT
        defaultTransactionDetailsShouldBeFound("client.contains=" + DEFAULT_CLIENT);

        // Get all the transactionDetailsList where client contains UPDATED_CLIENT
        defaultTransactionDetailsShouldNotBeFound("client.contains=" + UPDATED_CLIENT);
    }

    @Test
    @Transactional
    public void getAllTransactionDetailsByClientNotContainsSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        // Get all the transactionDetailsList where client does not contain DEFAULT_CLIENT
        defaultTransactionDetailsShouldNotBeFound("client.doesNotContain=" + DEFAULT_CLIENT);

        // Get all the transactionDetailsList where client does not contain UPDATED_CLIENT
        defaultTransactionDetailsShouldBeFound("client.doesNotContain=" + UPDATED_CLIENT);
    }


    @Test
    @Transactional
    public void getAllTransactionDetailsByClientInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);
        ClientInfo clientInfo = ClientInfoResourceIT.createEntity(em);
        em.persist(clientInfo);
        em.flush();
        transactionDetails.setClientInfo(clientInfo);
        clientInfo.setTransactionDetails(transactionDetails);
        transactionDetailsRepository.saveAndFlush(transactionDetails);
        Long clientInfoId = clientInfo.getId();

        // Get all the transactionDetailsList where clientInfo equals to clientInfoId
        defaultTransactionDetailsShouldBeFound("clientInfoId.equals=" + clientInfoId);

        // Get all the transactionDetailsList where clientInfo equals to clientInfoId + 1
        defaultTransactionDetailsShouldNotBeFound("clientInfoId.equals=" + (clientInfoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionDetailsShouldBeFound(String filter) throws Exception {
        restTransactionDetailsMockMvc.perform(get("/api/transaction-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderid").value(hasItem(DEFAULT_ORDERID.intValue())))
            .andExpect(jsonPath("$.[*].datetime").value(hasItem(DEFAULT_DATETIME.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].cardtype").value(hasItem(DEFAULT_CARDTYPE.toString())))
            .andExpect(jsonPath("$.[*].transactionStatus").value(hasItem(DEFAULT_TRANSACTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT)));

        // Check, that the count call also returns 1
        restTransactionDetailsMockMvc.perform(get("/api/transaction-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionDetailsShouldNotBeFound(String filter) throws Exception {
        restTransactionDetailsMockMvc.perform(get("/api/transaction-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionDetailsMockMvc.perform(get("/api/transaction-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionDetails() throws Exception {
        // Get the transactionDetails
        restTransactionDetailsMockMvc.perform(get("/api/transaction-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionDetails() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        int databaseSizeBeforeUpdate = transactionDetailsRepository.findAll().size();

        // Update the transactionDetails
        TransactionDetails updatedTransactionDetails = transactionDetailsRepository.findById(transactionDetails.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionDetails are not directly saved in db
        em.detach(updatedTransactionDetails);
        updatedTransactionDetails
            .orderid(UPDATED_ORDERID)
            .datetime(UPDATED_DATETIME)
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .cardtype(UPDATED_CARDTYPE)
            .transactionStatus(UPDATED_TRANSACTION_STATUS)
            .client(UPDATED_CLIENT);
        TransactionDetailsDTO transactionDetailsDTO = transactionDetailsMapper.toDto(updatedTransactionDetails);

        restTransactionDetailsMockMvc.perform(put("/api/transaction-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionDetails in the database
        List<TransactionDetails> transactionDetailsList = transactionDetailsRepository.findAll();
        assertThat(transactionDetailsList).hasSize(databaseSizeBeforeUpdate);
        TransactionDetails testTransactionDetails = transactionDetailsList.get(transactionDetailsList.size() - 1);
        assertThat(testTransactionDetails.getOrderid()).isEqualTo(UPDATED_ORDERID);
        assertThat(testTransactionDetails.getDatetime()).isEqualTo(UPDATED_DATETIME);
        assertThat(testTransactionDetails.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTransactionDetails.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testTransactionDetails.getCardtype()).isEqualTo(UPDATED_CARDTYPE);
        assertThat(testTransactionDetails.getTransactionStatus()).isEqualTo(UPDATED_TRANSACTION_STATUS);
        assertThat(testTransactionDetails.getClient()).isEqualTo(UPDATED_CLIENT);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionDetails() throws Exception {
        int databaseSizeBeforeUpdate = transactionDetailsRepository.findAll().size();

        // Create the TransactionDetails
        TransactionDetailsDTO transactionDetailsDTO = transactionDetailsMapper.toDto(transactionDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionDetailsMockMvc.perform(put("/api/transaction-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionDetails in the database
        List<TransactionDetails> transactionDetailsList = transactionDetailsRepository.findAll();
        assertThat(transactionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionDetails() throws Exception {
        // Initialize the database
        transactionDetailsRepository.saveAndFlush(transactionDetails);

        int databaseSizeBeforeDelete = transactionDetailsRepository.findAll().size();

        // Delete the transactionDetails
        restTransactionDetailsMockMvc.perform(delete("/api/transaction-details/{id}", transactionDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionDetails> transactionDetailsList = transactionDetailsRepository.findAll();
        assertThat(transactionDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
