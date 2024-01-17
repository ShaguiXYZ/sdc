package com.shagui.sdc.api.domain;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

import com.shagui.sdc.core.exception.SdcCustomException;

@FunctionalInterface
public interface CastTo<T> {

	public Class<T> targetClass();

	default T parse(Object source) {
		try {
			Constructor<T> constructor = targetClass().getConstructor(source.getClass());
			return constructor.newInstance(source);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			return parseNoArgsConstructor(source);
		}
	}

	private T parseNoArgsConstructor(@NonNull Object source) {
		try {
			Constructor<T> constructor = targetClass().getConstructor();
			T target = constructor.newInstance();

			if (null != target) {
				BeanUtils.copyProperties(source, target);
			}

			return target;
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new SdcCustomException("An empty constructor or a %s constructor is needed".formatted(
					source.getClass().getSimpleName()), e);
		}
	}
}
