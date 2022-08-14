package com.jamal.payment.management.web.rest;

import com.jamal.payment.management.PaymentManagementApp;
import com.jamal.payment.management.domain.ClientInfo;
import com.jamal.payment.management.domain.TransactionDetails;
import com.jamal.payment.management.repository.ClientInfoRepository;
import com.jamal.payment.management.service.ClientInfoService;
import com.jamal.payment.management.service.dto.ClientInfoDTO;
import com.jamal.payment.management.service.mapper.ClientInfoMapper;
import com.jamal.payment.management.service.dto.ClientInfoCriteria;
import com.jamal.payment.management.service.ClientInfoQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jamal.payment.management.domain.enumeration.StatusEnum;
import com.jamal.payment.management.domain.enumeration.BillingIntervalEnum;
import com.jamal.payment.management.domain.enumeration.FeesTypeEnum;
/**
 * Integration tests for the {@link ClientInfoResource} REST controller.
 */
@SpringBootTest(classes = PaymentManagementApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClientInfoResourceIT {

    private static final String DEFAULT_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT = "BBBBBBBBBB";

    private static final StatusEnum DEFAULT_STATUS = StatusEnum.ACTIVE;
    private static final StatusEnum UPDATED_STATUS = StatusEnum.DISABLE;

    private static final BillingIntervalEnum DEFAULT_BILLING_INTERVAL = BillingIntervalEnum.DAILY;
    private static final BillingIntervalEnum UPDATED_BILLING_INTERVAL = BillingIntervalEnum.MONTHLY;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final FeesTypeEnum DEFAULT_FEES_TYPE = FeesTypeEnum.FLAT_FEES;
    private static final FeesTypeEnum UPDATED_FEES_TYPE = FeesTypeEnum.VARIABLE_FEES;

    private static final Double DEFAULT_FEES = 1D;
    private static final Double UPDATED_FEES = 2D;
    private static final Double SMALLER_FEES = 1D - 1D;

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    @Autowired
    private ClientInfoMapper clientInfoMapper;

    @Autowired
    private ClientInfoService clientInfoService;

    @Autowired
    private ClientInfoQueryService clientInfoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientInfoMockMvc;

    private ClientInfo clientInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientInfo createEntity(EntityManager em) {
        ClientInfo clientInfo = new ClientInfo()
            .client(DEFAULT_CLIENT)
            .status(DEFAULT_STATUS)
            .billingInterval(DEFAULT_BILLING_INTERVAL)
            .email(DEFAULT_EMAIL)
            .feesType(DEFAULT_FEES_TYPE)
            .fees(DEFAULT_FEES);
        return clientInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientInfo createUpdatedEntity(EntityManager em) {
        ClientInfo clientInfo = new ClientInfo()
            .client(UPDATED_CLIENT)
            .status(UPDATED_STATUS)
            .billingInterval(UPDATED_BILLING_INTERVAL)
            .email(UPDATED_EMAIL)
            .feesType(UPDATED_FEES_TYPE)
            .fees(UPDATED_FEES);
        return clientInfo;
    }

    @BeforeEach
    public void initTest() {
        clientInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientInfo() throws Exception {
        int databaseSizeBeforeCreate = clientInfoRepository.findAll().size();
        // Create the ClientInfo
        ClientInfoDTO clientInfoDTO = clientInfoMapper.toDto(clientInfo);
        restClientInfoMockMvc.perform(post("/api/client-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the ClientInfo in the database
        List<ClientInfo> clientInfoList = clientInfoRepository.findAll();
        assertThat(clientInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ClientInfo testClientInfo = clientInfoList.get(clientInfoList.size() - 1);
        assertThat(testClientInfo.getClient()).isEqualTo(DEFAULT_CLIENT);
        assertThat(testClientInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testClientInfo.getBillingInterval()).isEqualTo(DEFAULT_BILLING_INTERVAL);
        assertThat(testClientInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClientInfo.getFeesType()).isEqualTo(DEFAULT_FEES_TYPE);
        assertThat(testClientInfo.getFees()).isEqualTo(DEFAULT_FEES);
    }

    @Test
    @Transactional
    public void createClientInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientInfoRepository.findAll().size();

        // Create the ClientInfo with an existing ID
        clientInfo.setId(1L);
        ClientInfoDTO clientInfoDTO = clientInfoMapper.toDto(clientInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientInfoMockMvc.perform(post("/api/client-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientInfo in the database
        List<ClientInfo> clientInfoList = clientInfoRepository.findAll();
        assertThat(clientInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClientInfos() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList
        restClientInfoMockMvc.perform(get("/api/client-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].billingInterval").value(hasItem(DEFAULT_BILLING_INTERVAL.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].feesType").value(hasItem(DEFAULT_FEES_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fees").value(hasItem(DEFAULT_FEES.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getClientInfo() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get the clientInfo
        restClientInfoMockMvc.perform(get("/api/client-infos/{id}", clientInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientInfo.getId().intValue()))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.billingInterval").value(DEFAULT_BILLING_INTERVAL.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.feesType").value(DEFAULT_FEES_TYPE.toString()))
            .andExpect(jsonPath("$.fees").value(DEFAULT_FEES.doubleValue()));
    }


    @Test
    @Transactional
    public void getClientInfosByIdFiltering() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        Long id = clientInfo.getId();

        defaultClientInfoShouldBeFound("id.equals=" + id);
        defaultClientInfoShouldNotBeFound("id.notEquals=" + id);

        defaultClientInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClientInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultClientInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClientInfoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllClientInfosByClientIsEqualToSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where client equals to DEFAULT_CLIENT
        defaultClientInfoShouldBeFound("client.equals=" + DEFAULT_CLIENT);

        // Get all the clientInfoList where client equals to UPDATED_CLIENT
        defaultClientInfoShouldNotBeFound("client.equals=" + UPDATED_CLIENT);
    }

    @Test
    @Transactional
    public void getAllClientInfosByClientIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where client not equals to DEFAULT_CLIENT
        defaultClientInfoShouldNotBeFound("client.notEquals=" + DEFAULT_CLIENT);

        // Get all the clientInfoList where client not equals to UPDATED_CLIENT
        defaultClientInfoShouldBeFound("client.notEquals=" + UPDATED_CLIENT);
    }

    @Test
    @Transactional
    public void getAllClientInfosByClientIsInShouldWork() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where client in DEFAULT_CLIENT or UPDATED_CLIENT
        defaultClientInfoShouldBeFound("client.in=" + DEFAULT_CLIENT + "," + UPDATED_CLIENT);

        // Get all the clientInfoList where client equals to UPDATED_CLIENT
        defaultClientInfoShouldNotBeFound("client.in=" + UPDATED_CLIENT);
    }

    @Test
    @Transactional
    public void getAllClientInfosByClientIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where client is not null
        defaultClientInfoShouldBeFound("client.specified=true");

        // Get all the clientInfoList where client is null
        defaultClientInfoShouldNotBeFound("client.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientInfosByClientContainsSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where client contains DEFAULT_CLIENT
        defaultClientInfoShouldBeFound("client.contains=" + DEFAULT_CLIENT);

        // Get all the clientInfoList where client contains UPDATED_CLIENT
        defaultClientInfoShouldNotBeFound("client.contains=" + UPDATED_CLIENT);
    }

    @Test
    @Transactional
    public void getAllClientInfosByClientNotContainsSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where client does not contain DEFAULT_CLIENT
        defaultClientInfoShouldNotBeFound("client.doesNotContain=" + DEFAULT_CLIENT);

        // Get all the clientInfoList where client does not contain UPDATED_CLIENT
        defaultClientInfoShouldBeFound("client.doesNotContain=" + UPDATED_CLIENT);
    }


    @Test
    @Transactional
    public void getAllClientInfosByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where status equals to DEFAULT_STATUS
        defaultClientInfoShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the clientInfoList where status equals to UPDATED_STATUS
        defaultClientInfoShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllClientInfosByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where status not equals to DEFAULT_STATUS
        defaultClientInfoShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the clientInfoList where status not equals to UPDATED_STATUS
        defaultClientInfoShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllClientInfosByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultClientInfoShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the clientInfoList where status equals to UPDATED_STATUS
        defaultClientInfoShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllClientInfosByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where status is not null
        defaultClientInfoShouldBeFound("status.specified=true");

        // Get all the clientInfoList where status is null
        defaultClientInfoShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientInfosByBillingIntervalIsEqualToSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where billingInterval equals to DEFAULT_BILLING_INTERVAL
        defaultClientInfoShouldBeFound("billingInterval.equals=" + DEFAULT_BILLING_INTERVAL);

        // Get all the clientInfoList where billingInterval equals to UPDATED_BILLING_INTERVAL
        defaultClientInfoShouldNotBeFound("billingInterval.equals=" + UPDATED_BILLING_INTERVAL);
    }

    @Test
    @Transactional
    public void getAllClientInfosByBillingIntervalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where billingInterval not equals to DEFAULT_BILLING_INTERVAL
        defaultClientInfoShouldNotBeFound("billingInterval.notEquals=" + DEFAULT_BILLING_INTERVAL);

        // Get all the clientInfoList where billingInterval not equals to UPDATED_BILLING_INTERVAL
        defaultClientInfoShouldBeFound("billingInterval.notEquals=" + UPDATED_BILLING_INTERVAL);
    }

    @Test
    @Transactional
    public void getAllClientInfosByBillingIntervalIsInShouldWork() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where billingInterval in DEFAULT_BILLING_INTERVAL or UPDATED_BILLING_INTERVAL
        defaultClientInfoShouldBeFound("billingInterval.in=" + DEFAULT_BILLING_INTERVAL + "," + UPDATED_BILLING_INTERVAL);

        // Get all the clientInfoList where billingInterval equals to UPDATED_BILLING_INTERVAL
        defaultClientInfoShouldNotBeFound("billingInterval.in=" + UPDATED_BILLING_INTERVAL);
    }

    @Test
    @Transactional
    public void getAllClientInfosByBillingIntervalIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where billingInterval is not null
        defaultClientInfoShouldBeFound("billingInterval.specified=true");

        // Get all the clientInfoList where billingInterval is null
        defaultClientInfoShouldNotBeFound("billingInterval.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientInfosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where email equals to DEFAULT_EMAIL
        defaultClientInfoShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the clientInfoList where email equals to UPDATED_EMAIL
        defaultClientInfoShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClientInfosByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where email not equals to DEFAULT_EMAIL
        defaultClientInfoShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the clientInfoList where email not equals to UPDATED_EMAIL
        defaultClientInfoShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClientInfosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultClientInfoShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the clientInfoList where email equals to UPDATED_EMAIL
        defaultClientInfoShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClientInfosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where email is not null
        defaultClientInfoShouldBeFound("email.specified=true");

        // Get all the clientInfoList where email is null
        defaultClientInfoShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientInfosByEmailContainsSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where email contains DEFAULT_EMAIL
        defaultClientInfoShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the clientInfoList where email contains UPDATED_EMAIL
        defaultClientInfoShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClientInfosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where email does not contain DEFAULT_EMAIL
        defaultClientInfoShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the clientInfoList where email does not contain UPDATED_EMAIL
        defaultClientInfoShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllClientInfosByFeesTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where feesType equals to DEFAULT_FEES_TYPE
        defaultClientInfoShouldBeFound("feesType.equals=" + DEFAULT_FEES_TYPE);

        // Get all the clientInfoList where feesType equals to UPDATED_FEES_TYPE
        defaultClientInfoShouldNotBeFound("feesType.equals=" + UPDATED_FEES_TYPE);
    }

    @Test
    @Transactional
    public void getAllClientInfosByFeesTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where feesType not equals to DEFAULT_FEES_TYPE
        defaultClientInfoShouldNotBeFound("feesType.notEquals=" + DEFAULT_FEES_TYPE);

        // Get all the clientInfoList where feesType not equals to UPDATED_FEES_TYPE
        defaultClientInfoShouldBeFound("feesType.notEquals=" + UPDATED_FEES_TYPE);
    }

    @Test
    @Transactional
    public void getAllClientInfosByFeesTypeIsInShouldWork() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where feesType in DEFAULT_FEES_TYPE or UPDATED_FEES_TYPE
        defaultClientInfoShouldBeFound("feesType.in=" + DEFAULT_FEES_TYPE + "," + UPDATED_FEES_TYPE);

        // Get all the clientInfoList where feesType equals to UPDATED_FEES_TYPE
        defaultClientInfoShouldNotBeFound("feesType.in=" + UPDATED_FEES_TYPE);
    }

    @Test
    @Transactional
    public void getAllClientInfosByFeesTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where feesType is not null
        defaultClientInfoShouldBeFound("feesType.specified=true");

        // Get all the clientInfoList where feesType is null
        defaultClientInfoShouldNotBeFound("feesType.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientInfosByFeesIsEqualToSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where fees equals to DEFAULT_FEES
        defaultClientInfoShouldBeFound("fees.equals=" + DEFAULT_FEES);

        // Get all the clientInfoList where fees equals to UPDATED_FEES
        defaultClientInfoShouldNotBeFound("fees.equals=" + UPDATED_FEES);
    }

    @Test
    @Transactional
    public void getAllClientInfosByFeesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where fees not equals to DEFAULT_FEES
        defaultClientInfoShouldNotBeFound("fees.notEquals=" + DEFAULT_FEES);

        // Get all the clientInfoList where fees not equals to UPDATED_FEES
        defaultClientInfoShouldBeFound("fees.notEquals=" + UPDATED_FEES);
    }

    @Test
    @Transactional
    public void getAllClientInfosByFeesIsInShouldWork() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where fees in DEFAULT_FEES or UPDATED_FEES
        defaultClientInfoShouldBeFound("fees.in=" + DEFAULT_FEES + "," + UPDATED_FEES);

        // Get all the clientInfoList where fees equals to UPDATED_FEES
        defaultClientInfoShouldNotBeFound("fees.in=" + UPDATED_FEES);
    }

    @Test
    @Transactional
    public void getAllClientInfosByFeesIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where fees is not null
        defaultClientInfoShouldBeFound("fees.specified=true");

        // Get all the clientInfoList where fees is null
        defaultClientInfoShouldNotBeFound("fees.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientInfosByFeesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where fees is greater than or equal to DEFAULT_FEES
        defaultClientInfoShouldBeFound("fees.greaterThanOrEqual=" + DEFAULT_FEES);

        // Get all the clientInfoList where fees is greater than or equal to UPDATED_FEES
        defaultClientInfoShouldNotBeFound("fees.greaterThanOrEqual=" + UPDATED_FEES);
    }

    @Test
    @Transactional
    public void getAllClientInfosByFeesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where fees is less than or equal to DEFAULT_FEES
        defaultClientInfoShouldBeFound("fees.lessThanOrEqual=" + DEFAULT_FEES);

        // Get all the clientInfoList where fees is less than or equal to SMALLER_FEES
        defaultClientInfoShouldNotBeFound("fees.lessThanOrEqual=" + SMALLER_FEES);
    }

    @Test
    @Transactional
    public void getAllClientInfosByFeesIsLessThanSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where fees is less than DEFAULT_FEES
        defaultClientInfoShouldNotBeFound("fees.lessThan=" + DEFAULT_FEES);

        // Get all the clientInfoList where fees is less than UPDATED_FEES
        defaultClientInfoShouldBeFound("fees.lessThan=" + UPDATED_FEES);
    }

    @Test
    @Transactional
    public void getAllClientInfosByFeesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        // Get all the clientInfoList where fees is greater than DEFAULT_FEES
        defaultClientInfoShouldNotBeFound("fees.greaterThan=" + DEFAULT_FEES);

        // Get all the clientInfoList where fees is greater than SMALLER_FEES
        defaultClientInfoShouldBeFound("fees.greaterThan=" + SMALLER_FEES);
    }


    @Test
    @Transactional
    public void getAllClientInfosByTransactionDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);
        TransactionDetails transactionDetails = TransactionDetailsResourceIT.createEntity(em);
        em.persist(transactionDetails);
        em.flush();
        clientInfo.setTransactionDetails(transactionDetails);
        clientInfoRepository.saveAndFlush(clientInfo);
        Long transactionDetailsId = transactionDetails.getId();

        // Get all the clientInfoList where transactionDetails equals to transactionDetailsId
        defaultClientInfoShouldBeFound("transactionDetailsId.equals=" + transactionDetailsId);

        // Get all the clientInfoList where transactionDetails equals to transactionDetailsId + 1
        defaultClientInfoShouldNotBeFound("transactionDetailsId.equals=" + (transactionDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClientInfoShouldBeFound(String filter) throws Exception {
        restClientInfoMockMvc.perform(get("/api/client-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].billingInterval").value(hasItem(DEFAULT_BILLING_INTERVAL.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].feesType").value(hasItem(DEFAULT_FEES_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fees").value(hasItem(DEFAULT_FEES.doubleValue())));

        // Check, that the count call also returns 1
        restClientInfoMockMvc.perform(get("/api/client-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClientInfoShouldNotBeFound(String filter) throws Exception {
        restClientInfoMockMvc.perform(get("/api/client-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClientInfoMockMvc.perform(get("/api/client-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingClientInfo() throws Exception {
        // Get the clientInfo
        restClientInfoMockMvc.perform(get("/api/client-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientInfo() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        int databaseSizeBeforeUpdate = clientInfoRepository.findAll().size();

        // Update the clientInfo
        ClientInfo updatedClientInfo = clientInfoRepository.findById(clientInfo.getId()).get();
        // Disconnect from session so that the updates on updatedClientInfo are not directly saved in db
        em.detach(updatedClientInfo);
        updatedClientInfo
            .client(UPDATED_CLIENT)
            .status(UPDATED_STATUS)
            .billingInterval(UPDATED_BILLING_INTERVAL)
            .email(UPDATED_EMAIL)
            .feesType(UPDATED_FEES_TYPE)
            .fees(UPDATED_FEES);
        ClientInfoDTO clientInfoDTO = clientInfoMapper.toDto(updatedClientInfo);

        restClientInfoMockMvc.perform(put("/api/client-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientInfoDTO)))
            .andExpect(status().isOk());

        // Validate the ClientInfo in the database
        List<ClientInfo> clientInfoList = clientInfoRepository.findAll();
        assertThat(clientInfoList).hasSize(databaseSizeBeforeUpdate);
        ClientInfo testClientInfo = clientInfoList.get(clientInfoList.size() - 1);
        assertThat(testClientInfo.getClient()).isEqualTo(UPDATED_CLIENT);
        assertThat(testClientInfo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClientInfo.getBillingInterval()).isEqualTo(UPDATED_BILLING_INTERVAL);
        assertThat(testClientInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClientInfo.getFeesType()).isEqualTo(UPDATED_FEES_TYPE);
        assertThat(testClientInfo.getFees()).isEqualTo(UPDATED_FEES);
    }

    @Test
    @Transactional
    public void updateNonExistingClientInfo() throws Exception {
        int databaseSizeBeforeUpdate = clientInfoRepository.findAll().size();

        // Create the ClientInfo
        ClientInfoDTO clientInfoDTO = clientInfoMapper.toDto(clientInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientInfoMockMvc.perform(put("/api/client-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientInfo in the database
        List<ClientInfo> clientInfoList = clientInfoRepository.findAll();
        assertThat(clientInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientInfo() throws Exception {
        // Initialize the database
        clientInfoRepository.saveAndFlush(clientInfo);

        int databaseSizeBeforeDelete = clientInfoRepository.findAll().size();

        // Delete the clientInfo
        restClientInfoMockMvc.perform(delete("/api/client-infos/{id}", clientInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientInfo> clientInfoList = clientInfoRepository.findAll();
        assertThat(clientInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
