package com.bci.test.utils;
import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransFormObject {

	@Autowired
	private DozerBeanMapper mapper;

	/**
	 * usuamos una salida magica para objetos que vamos a transformar.
	 * 
	 * @param <FROM>
	 * @param <TO>
	 * @param fromList
	 * @param toClass
	 * @return
	 */
	public <FROM, TO> List<TO> mapList(List<FROM> fromList, final Class<TO> toClass) {
	    return fromList
	            .stream()
	            .map(from -> this.mapper.map(from, toClass))
	            .collect(Collectors.toList());
	}

	/**
	 * Objeto magicos para transformacion
	 * 
	 * @param <FROM>
	 * @param <TO>
	 * @param from
	 * @param toClass
	 * @return
	 */
	public <FROM, TO> TO mapClass(FROM from, final Class<TO> toClass){
		return this.mapper.map(from , toClass);
	}
	
}
