/*Copyright (c) 2019-2020 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.mobilebasicsanity.wmstudio.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.HashMap;
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

import com.mobilebasicsanity.wmstudio.Departments;
import com.mobilebasicsanity.wmstudio.Employees;
import com.mobilebasicsanity.wmstudio.JobHistory;


/**
 * ServiceImpl object for domain model class Employees.
 *
 * @see Employees
 */
@Service("WMSTUDIO.EmployeesService")
@Validated
public class EmployeesServiceImpl implements EmployeesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeesServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("WMSTUDIO.JobHistoryService")
    private JobHistoryService jobHistoryService;

    @Lazy
    @Autowired
    @Qualifier("WMSTUDIO.DepartmentsService")
    private DepartmentsService departmentsService;

    @Autowired
    @Qualifier("WMSTUDIO.EmployeesDao")
    private WMGenericDao<Employees, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<Employees, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "WMSTUDIOTransactionManager")
    @Override
    public Employees create(Employees employees) {
        LOGGER.debug("Creating a new Employees with information: {}", employees);

        Employees employeesCreated = this.wmGenericDao.create(employees);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(employeesCreated);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public Employees getById(Integer employeesId) {
        LOGGER.debug("Finding Employees by id: {}", employeesId);
        return this.wmGenericDao.findById(employeesId);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public Employees findById(Integer employeesId) {
        LOGGER.debug("Finding Employees by id: {}", employeesId);
        try {
            return this.wmGenericDao.findById(employeesId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No Employees found with id: {}", employeesId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public List<Employees> findByMultipleIds(List<Integer> employeesIds, boolean orderedReturn) {
        LOGGER.debug("Finding Employees by ids: {}", employeesIds);

        return this.wmGenericDao.findByMultipleIds(employeesIds, orderedReturn);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public Employees getByEmail(String email) {
        Map<String, Object> emailMap = new HashMap<>();
        emailMap.put("email", email);

        LOGGER.debug("Finding Employees by unique keys: {}", emailMap);
        return this.wmGenericDao.findByUniqueKey(emailMap);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "WMSTUDIOTransactionManager")
    @Override
    public Employees update(Employees employees) {
        LOGGER.debug("Updating Employees with information: {}", employees);

        this.wmGenericDao.update(employees);
        this.wmGenericDao.refresh(employees);

        return employees;
    }

    @Transactional(value = "WMSTUDIOTransactionManager")
    @Override
    public Employees delete(Integer employeesId) {
        LOGGER.debug("Deleting Employees with id: {}", employeesId);
        Employees deleted = this.wmGenericDao.findById(employeesId);
        if (deleted == null) {
            LOGGER.debug("No Employees found with id: {}", employeesId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), Employees.class.getSimpleName(), employeesId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "WMSTUDIOTransactionManager")
    @Override
    public void delete(Employees employees) {
        LOGGER.debug("Deleting Employees with {}", employees);
        this.wmGenericDao.delete(employees);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public Page<Employees> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Employees");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public Page<Employees> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Employees");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service WMSTUDIO for table Employees to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service WMSTUDIO for table Employees to {} format", options.getExportType());
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
    public Page<Departments> findAssociatedDepartmentsesForManagerId(Integer employeeId, Pageable pageable) {
        LOGGER.debug("Fetching all associated departmentsesForManagerId");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("employeesByManagerId.employeeId = '" + employeeId + "'");

        return departmentsService.findAll(queryBuilder.toString(), pageable);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public Page<Employees> findAssociatedEmployeesesForManagerId(Integer employeeId, Pageable pageable) {
        LOGGER.debug("Fetching all associated employeesesForManagerId");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("employeesByManagerId.employeeId = '" + employeeId + "'");

        return findAll(queryBuilder.toString(), pageable);
    }

    @Transactional(readOnly = true, value = "WMSTUDIOTransactionManager")
    @Override
    public Page<JobHistory> findAssociatedJobHistories(Integer employeeId, Pageable pageable) {
        LOGGER.debug("Fetching all associated jobHistories");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("employees.employeeId = '" + employeeId + "'");

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
     * @param service DepartmentsService instance
     */
    protected void setDepartmentsService(DepartmentsService service) {
        this.departmentsService = service;
    }

}