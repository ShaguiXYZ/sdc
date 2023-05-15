package com.shagui.sdc.api.domain;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.BeanUtils;

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

	private T parseNoArgsConstructor(Object source) {
		try {
			Constructor<T> constructor = targetClass().getConstructor();
			T target = constructor.newInstance();
			BeanUtils.copyProperties(source, target);

			return target;
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new SdcCustomException(String.format("An empty constructor or a %s constructor in needed",
					source.getClass().getSimpleName()), e);
		}
	}
}