/*Copyright (c) 2019-2020 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.mobilebasicsanity.wmstudio.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.commons.wrapper.StringWrapper;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.manager.ExportedFileManager;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.runtime.security.xss.XssDisable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.mobilebasicsanity.wmstudio.Countries;
import com.mobilebasicsanity.wmstudio.Locations;
import com.mobilebasicsanity.wmstudio.service.CountriesService;


/**
 * Controller object for domain model class Countries.
 * @see Countries
 */
@RestController("WMSTUDIO.CountriesController")
@Api(value = "CountriesController", description = "Exposes APIs to work with Countries resource.")
@RequestMapping("/WMSTUDIO/Countries")
public class CountriesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountriesController.class);

    @Autowired
	@Qualifier("WMSTUDIO.CountriesService")
	private CountriesService countriesService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new Countries instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Countries createCountries(@RequestBody Countries countries) {
		LOGGER.debug("Create Countries with information: {}" , countries);

		countries = countriesService.create(countries);
		LOGGER.debug("Created Countries with information: {}" , countries);

	    return countries;
	}

    @ApiOperation(value = "Returns the Countries instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Countries getCountries(@PathVariable("id") String id) {
        LOGGER.debug("Getting Countries with id: {}" , id);

        Countries foundCountries = countriesService.getById(id);
        LOGGER.debug("Countries details with id: {}" , foundCountries);

        return foundCountries;
    }

    @ApiOperation(value = "Updates the Countries instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Countries editCountries(@PathVariable("id") String id, @RequestBody Countries countries) {
        LOGGER.debug("Editing Countries with id: {}" , countries.getCountryId());

        countries.setCountryId(id);
        countries = countriesService.update(countries);
        LOGGER.debug("Countries details with id: {}" , countries);

        return countries;
    }

    @ApiOperation(value = "Deletes the Countries instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteCountries(@PathVariable("id") String id) {
        LOGGER.debug("Deleting Countries with id: {}" , id);

        Countries deletedCountries = countriesService.delete(id);

        return deletedCountries != null;
    }

    /**
     * @deprecated Use {@link #findCountries(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of Countries instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @XssDisable
    public Page<Countries> searchCountriesByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Countries list by query filter:{}", (Object) queryFilters);
        return countriesService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Countries instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Countries> findCountries(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Countries list by filter:", query);
        return countriesService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Countries instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @XssDisable
    public Page<Countries> filterCountries(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Countries list by filter", query);
        return countriesService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
    @RequestMapping(value = "/export/{exportType}", method = {RequestMethod.GET,  RequestMethod.POST}, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @XssDisable
    public Downloadable exportCountries(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return countriesService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @XssDisable
    public StringWrapper exportCountriesAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = Countries.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> countriesService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of Countries instances matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	@XssDisable
	public Long countCountries( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting Countries");
		return countriesService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	@XssDisable
	public Page<Map<String, Object>> getCountriesAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return countriesService.getAggregatedValues(aggregationInfo, pageable);
    }

    @RequestMapping(value="/{id:.+}/locationses", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the locationses instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Locations> findAssociatedLocationses(@PathVariable("id") String id, Pageable pageable) {

        LOGGER.debug("Fetching all associated locationses");
        return countriesService.findAssociatedLocationses(id, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service CountriesService instance
	 */
	protected void setCountriesService(CountriesService service) {
		this.countriesService = service;
	}

}