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

import com.mobilebasicsanity.wmstudio.Departments;
import com.mobilebasicsanity.wmstudio.Locations;

/**
 * Service object for domain model class {@link Locations}.
 */
public interface LocationsService {

    /**
     * Creates a new Locations. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Locations if any.
     *
     * @param locations Details of the Locations to be created; value cannot be null.
     * @return The newly created Locations.
     */
    Locations create(@Valid Locations locations);


	/**
     * Returns Locations by given id if exists.
     *
     * @param locationsId The id of the Locations to get; value cannot be null.
     * @return Locations associated with the given locationsId.
	 * @throws EntityNotFoundException If no Locations is found.
     */
    Locations getById(Short locationsId);

    /**
     * Find and return the Locations by given id if exists, returns null otherwise.
     *
     * @param locationsId The id of the Locations to get; value cannot be null.
     * @return Locations associated with the given locationsId.
     */
    Locations findById(Short locationsId);

	/**
     * Find and return the list of Locations by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param locationsIds The id's of the Locations to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return Locations associated with the given locationsIds.
     */
    List<Locations> findByMultipleIds(List<Short> locationsIds, boolean orderedReturn);


    /**
     * Updates the details of an existing Locations. It replaces all fields of the existing Locations with the given locations.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Locations if any.
     *
     * @param locations The details of the Locations to be updated; value cannot be null.
     * @return The updated Locations.
     * @throws EntityNotFoundException if no Locations is found with given input.
     */
    Locations update(@Valid Locations locations);


    /**
     * Partially updates the details of an existing Locations. It updates only the
     * fields of the existing Locations which are passed in the locationsPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on Locations if any.
     *
     * @param locationsId The id of the Locations to be deleted; value cannot be null.
     * @param locationsPatch The partial data of Locations which is supposed to be updated; value cannot be null.
     * @return The updated Locations.
     * @throws EntityNotFoundException if no Locations is found with given input.
     */
    Locations partialUpdate(Short locationsId, Map<String, Object> locationsPatch);

    /**
     * Deletes an existing Locations with the given id.
     *
     * @param locationsId The id of the Locations to be deleted; value cannot be null.
     * @return The deleted Locations.
     * @throws EntityNotFoundException if no Locations found with the given id.
     */
    Locations delete(Short locationsId);

    /**
     * Deletes an existing Locations with the given object.
     *
     * @param locations The instance of the Locations to be deleted; value cannot be null.
     */
    void delete(Locations locations);

    /**
     * Find all Locations matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching Locations.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<Locations> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all Locations matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching Locations.
     *
     * @see Pageable
     * @see Page
     */
    Page<Locations> findAll(String query, Pageable pageable);

    /**
     * Exports all Locations matching the given input query to the given exportType format.
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
     * Exports all Locations matching the given input query to the given exportType format.
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
     * Retrieve the count of the Locations in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the Locations.
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

    /*
     * Returns the associated departmentses for given Locations id.
     *
     * @param locationId value of locationId; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated Departments instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<Departments> findAssociatedDepartmentses(Short locationId, Pageable pageable);

}