/*Copyright (c) 2019-2020 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.mobilebasicsanity.wmstudio.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.mobilebasicsanity.wmstudio.EmpDetailsView;
import com.mobilebasicsanity.wmstudio.EmpDetailsViewId;

/**
 * Service object for domain model class {@link EmpDetailsView}.
 */
public interface EmpDetailsViewService {

    /**
     * Creates a new EmpDetailsView. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on EmpDetailsView if any.
     *
     * @param empDetailsView Details of the EmpDetailsView to be created; value cannot be null.
     * @return The newly created EmpDetailsView.
     */
    EmpDetailsView create(@Valid EmpDetailsView empDetailsView);


	/**
     * Returns EmpDetailsView by given id if exists.
     *
     * @param empdetailsviewId The id of the EmpDetailsView to get; value cannot be null.
     * @return EmpDetailsView associated with the given empdetailsviewId.
	 * @throws EntityNotFoundException If no EmpDetailsView is found.
     */
    EmpDetailsView getById(EmpDetailsViewId empdetailsviewId);

    /**
     * Find and return the EmpDetailsView by given id if exists, returns null otherwise.
     *
     * @param empdetailsviewId The id of the EmpDetailsView to get; value cannot be null.
     * @return EmpDetailsView associated with the given empdetailsviewId.
     */
    EmpDetailsView findById(EmpDetailsViewId empdetailsviewId);

	/**
     * Find and return the list of EmpDetailsViews by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param empdetailsviewIds The id's of the EmpDetailsView to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return EmpDetailsViews associated with the given empdetailsviewIds.
     */
    List<EmpDetailsView> findByMultipleIds(List<EmpDetailsViewId> empdetailsviewIds, boolean orderedReturn);


    /**
     * Updates the details of an existing EmpDetailsView. It replaces all fields of the existing EmpDetailsView with the given empDetailsView.
     *
     * This method overrides the input field values using Server side or database managed properties defined on EmpDetailsView if any.
     *
     * @param empDetailsView The details of the EmpDetailsView to be updated; value cannot be null.
     * @return The updated EmpDetailsView.
     * @throws EntityNotFoundException if no EmpDetailsView is found with given input.
     */
    EmpDetailsView update(@Valid EmpDetailsView empDetailsView);

    /**
     * Deletes an existing EmpDetailsView with the given id.
     *
     * @param empdetailsviewId The id of the EmpDetailsView to be deleted; value cannot be null.
     * @return The deleted EmpDetailsView.
     * @throws EntityNotFoundException if no EmpDetailsView found with the given id.
     */
    EmpDetailsView delete(EmpDetailsViewId empdetailsviewId);

    /**
     * Deletes an existing EmpDetailsView with the given object.
     *
     * @param empDetailsView The instance of the EmpDetailsView to be deleted; value cannot be null.
     */
    void delete(EmpDetailsView empDetailsView);

    /**
     * Find all EmpDetailsViews matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching EmpDetailsViews.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<EmpDetailsView> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all EmpDetailsViews matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching EmpDetailsViews.
     *
     * @see Pageable
     * @see Page
     */
    Page<EmpDetailsView> findAll(String query, Pageable pageable);

    /**
     * Exports all EmpDetailsViews matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
     */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

    /**
     * Exports all EmpDetailsViews matching the given input query to the given exportType format.
     *
     * @param options The export options provided by the user; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @param outputStream The output stream of the file for the exported data to be written to.
     *
     * @see DataExportOptions
     * @see Pageable
     * @see OutputStream
     */
    void export(DataExportOptions options, Pageable pageable, OutputStream outputStream);

    /**
     * Retrieve the count of the EmpDetailsViews in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the EmpDetailsView.
     */
    long count(String query);

    /**
     * Retrieve aggregated values with matching aggregation info.
     *
     * @param aggregationInfo info related to aggregations.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return Paginated data with included fields.
     *
     * @see AggregationInfo
     * @see Pageable
     * @see Page
	 */
    Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable);


}