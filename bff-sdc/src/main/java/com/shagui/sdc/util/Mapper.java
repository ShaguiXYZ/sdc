package com.shagui.sdc.util;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.domain.HistoricalCoverage;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.PageInfo;
import com.shagui.sdc.api.domain.TimeCoverage;
import com.shagui.sdc.core.exception.ApiError;

import feign.FeignException;

/**
 * Utility class for mapping and transforming objects between different types.
 * This class provides static methods to handle conversions for API errors,
 * lists, paginated data, and historical coverage data.
 */
public class Mapper {
	/**
	 * Private constructor to prevent instantiation of this utility class.
	 */
	private Mapper() {
	}

	/**
	 * Parses a FeignException into an ApiError object.
	 *
	 * @param ex the FeignException to parse
	 * @return an ApiError object containing the status and message from the
	 *         exception
	 */
	public static ApiError parse(FeignException ex) {
		ApiError error = new ApiError();
		error.setStatus(HttpStatus.valueOf(ex.status()));
		error.setMessage(ex.getMessage());

		return error;
	}

	/**
	 * Converts a list of source objects into a list of target objects of the
	 * specified type.
	 *
	 * @param <V>    the target type
	 * @param <D>    the source type
	 * @param source the list of source objects
	 * @param clazz  the target class type
	 * @return a list of converted objects of type V
	 */
	public static <V, D> List<V> parse(List<D> source, Class<V> clazz) {
		return source.stream().map(CastFactory.getInstance(clazz)::parse).toList();
	}

	/**
	 * Converts a PageData object containing source objects into a PageData object
	 * containing target objects of the specified type.
	 *
	 * @param <V>    the target type
	 * @param <D>    the source type
	 * @param source the PageData object containing source objects
	 * @param clazz  the target class type
	 * @return a PageData object containing converted objects of type V
	 */
	public static <V, D> PageData<V> parse(PageData<D> source, Class<V> clazz) {
		PageData<V> target = new PageData<>();

		target.setPaging(CastFactory.getInstance(PageInfo.class).parse(source.getPaging()));
		target.setPage(source.getPage().stream().map(CastFactory.getInstance(clazz)::parse).toList());

		return target;
	}

	/**
	 * Converts a HistoricalCoverage object containing source data into a
	 * HistoricalCoverage object containing target data of the specified type.
	 *
	 * @param <V>    the target type
	 * @param source the HistoricalCoverage object containing source data
	 * @param clazz  the target class type
	 * @return a HistoricalCoverage object containing converted data of type V
	 */
	public static <V> HistoricalCoverage<V> parse(HistoricalCoverage<?> source, Class<V> clazz) {
		HistoricalCoverage<V> target = new HistoricalCoverage<>();

		target.setData(CastFactory.getInstance(clazz).parse(source.getData()));
		target.setHistorical(Mapper.parse(source.getHistorical(), TimeCoverage.class));

		return target;
	}
}
