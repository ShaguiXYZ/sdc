package com.shagui.sdc.util.jpa;

import java.util.Date;

/**
 * Interface for entities that support expiration dates.
 */
public interface JpaExpirableData {

	/**
	 * Sets the expiry date for the entity.
	 * 
	 * @param date The expiry date.
	 */
	void setExpiryDate(Date date);
}
