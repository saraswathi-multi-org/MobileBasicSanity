/*Copyright (c) 2019-2020 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.mobilebasicsanity.wmstudio.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.wavemaker.commons.MessageResource;
import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.mobilebasicsanity.wmstudio.Employees;
import com.mobilebasicsanity.wmstudio.JobHistory;
import com.mobilebasicsanity.wmstudio.Jobs;


/**
 * ServiceImpl object for domain model class Jobs.
 *
 * @see Jobs
 */
@Service("WMSTUDIO.JobsService")
@Validated
public class JobsServiceImpl implements JobsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobsServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("WMSTUDIO.JobHistoryService")
    private JobHistoryService jobHistoryService;

    @Lazy
    @Autowired
    @Qualifier("WMSTUDIO.EmployeesService")
    private EmployeesService employeesService;

    @Autowired
    @Qualifier("WMSTUDIO.JobsDao")
    private WMGenericDao<Jobs, String> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<Jobs, String> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "WMSTUDIOTransactionManager")
    @Override
    public Jobs create(Jobs jobs) {
        LOGGER.debug("Creating a new Jobs with information: {}", jobs);

        Jobs jobsCreated = this.wmGenericDao.create(jobs);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(jobsCreated);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public Jobs getById(String jobsId) {
        LOGGER.debug("Finding Jobs by id: {}", jobsId);
        return this.wmGenericDao.findById(jobsId);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public Jobs findById(String jobsId) {
        LOGGER.debug("Finding Jobs by id: {}", jobsId);
        try {
            return this.wmGenericDao.findById(jobsId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No Jobs found with id: {}", jobsId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public List<Jobs> findByMultipleIds(List<String> jobsIds, boolean orderedReturn) {
        LOGGER.debug("Finding Jobs by ids: {}", jobsIds);

        return this.wmGenericDao.findByMultipleIds(jobsIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "WMSTUDIOTransactionManager")
    @Override
    public Jobs update(Jobs jobs) {
        LOGGER.debug("Updating Jobs with information: {}", jobs);

        this.wmGenericDao.update(jobs);
        this.wmGenericDao.refresh(jobs);

        return jobs;
    }

    @Transactional(value = "WMSTUDIOTransactionManager")
    @Override
    public Jobs delete(String jobsId) {
        LOGGER.debug("Deleting Jobs with id: {}", jobsId);
        Jobs deleted = this.wmGenericDao.findById(jobsId);
        if (deleted == null) {
            LOGGER.debug("No Jobs found with id: {}", jobsId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), Jobs.class.getSimpleName(), jobsId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "WMSTUDIOTransactionManager")
    @Override
    public void delete(Jobs jobs) {
        LOGGER.debug("Deleting Jobs with {}", jobs);
        this.wmGenericDao.delete(jobs);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public Page<Jobs> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Jobs");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public Page<Jobs> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Jobs");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service WMSTUDIO for table Jobs to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service WMSTUDIO for table Jobs to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public Page<Employees> findAssociatedEmployeeses(String jobId, Pageable pageable) {
        LOGGER.debug("Fetching all associated employeeses");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("jobs.jobId = '" + jobId + "'");

        return employeesService.findAll(queryBuilder.toString(), pageable);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public Page<JobHistory> findAssociatedJobHistories(String jobId, Pageable pageable) {
        LOGGER.debug("Fetching all associated jobHistories");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("jobs.jobId = '" + jobId + "'");

        return jobHistoryService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service JobHistoryService instance
     */
    protected void setJobHistoryService(JobHistoryService service) {
        this.jobHistoryService = service;
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service EmployeesService instance
     */
    protected void setEmployeesService(EmployeesService service) {
        this.employeesService = service;
    }

}