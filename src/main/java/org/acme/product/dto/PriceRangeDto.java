package org.acme.product.dto;

import org.hibernate.search.util.common.data.Range;

import java.math.BigDecimal;

public enum PriceRangeDto {

	_0_5( Range.canonical( BigDecimal.ZERO, new BigDecimal( "5" ) ) ),
	_5_10( Range.canonical( new BigDecimal( "5" ), new BigDecimal( "10" ) ) ),
	_10_20( Range.canonical( new BigDecimal( "10" ), new BigDecimal( "20" ) ) ),
	_20_50( Range.canonical( new BigDecimal( "20" ), new BigDecimal( "50" ) ) ),
	_50_100( Range.canonical( new BigDecimal( "50" ), new BigDecimal( "100" ) ) ),
	_100_PLUS( Range.canonical( new BigDecimal( "100" ), null ) );

	public final Range<BigDecimal> value;

	PriceRangeDto(Range<BigDecimal> value) {
		this.value = value;
	}
}
