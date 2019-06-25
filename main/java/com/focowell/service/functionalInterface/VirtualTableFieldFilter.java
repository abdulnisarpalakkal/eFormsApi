package com.focowell.service.functionalInterface;

import com.focowell.model.VirtualTableField;

@FunctionalInterface
public interface VirtualTableFieldFilter {
	VirtualTableField filter(Long id);
}
