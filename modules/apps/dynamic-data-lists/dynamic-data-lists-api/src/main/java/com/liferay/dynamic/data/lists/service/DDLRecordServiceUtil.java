/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.lists.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for DDLRecord. This utility wraps
 * <code>com.liferay.dynamic.data.lists.service.impl.DDLRecordServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordService
 * @generated
 */
public class DDLRecordServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.lists.service.impl.DDLRecordServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds a record referencing the record set.
	 *
	 * @param groupId the primary key of the record's group
	 * @param recordSetId the primary key of the record set
	 * @param displayIndex the index position in which the record is displayed
	 in the spreadsheet view
	 * @param ddmFormValues the record values. See <code>DDMFormValues</code>
	 in the <code>dynamic.data.mapping.api</code> module.
	 * @param serviceContext the service context to be applied. This can set
	 the UUID, guest permissions, and group permissions for the
	 record.
	 * @return the record
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
			long groupId, long recordSetId, int displayIndex,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addRecord(
			groupId, recordSetId, displayIndex, ddmFormValues, serviceContext);
	}

	/**
	 * Adds a record referencing the record set.
	 *
	 * @param groupId the primary key of the record's group
	 * @param recordSetId the primary key of the record set
	 * @param displayIndex the index position in which the record is
	 displayed in the spreadsheet view
	 * @param fields the record values. See <code>Fields</code> in the
	 <code>dynamic.data.mapping.api</code> module.
	 * @param serviceContext the service context to be applied. This can
	 set the UUID, guest permissions, and group permissions for
	 the record.
	 * @return the record
	 * @throws PortalException if a portal exception occurred
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #addRecord(long, long, int, DDMFormValues, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
			long groupId, long recordSetId, int displayIndex,
			com.liferay.dynamic.data.mapping.storage.Fields fields,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addRecord(
			groupId, recordSetId, displayIndex, fields, serviceContext);
	}

	/**
	 * Deletes the record and its resources.
	 *
	 * @param recordId the primary key of the record to be deleted
	 * @throws PortalException
	 */
	public static void deleteRecord(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteRecord(recordId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * Returns the record with the ID.
	 *
	 * @param recordId the primary key of the record
	 * @return the record with the ID
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecord getRecord(
			long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRecord(recordId);
	}

	/**
	 * Returns all the records matching the record set ID
	 *
	 * @param recordSetId the record's record set ID
	 * @return the matching records
	 * @throws PortalException if a portal exception occurred
	 */
	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
			getRecords(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRecords(recordSetId);
	}

	/**
	 * Reverts the record to a given version.
	 *
	 * @param recordId the primary key of the record
	 * @param version the version to be reverted
	 * @param serviceContext the service context to be applied. This can set
	 the record modified date.
	 * @throws PortalException if a portal exception occurred
	 */
	public static void revertRecord(
			long recordId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().revertRecord(recordId, version, serviceContext);
	}

	/**
	 * Updates a record, replacing its display index and values.
	 *
	 * @param recordId the primary key of the record
	 * @param majorVersion whether this update is a major change. A major
	 change increments the record's major version number.
	 * @param displayIndex the index position in which the record is displayed
	 in the spreadsheet view
	 * @param ddmFormValues the record values. See <code>DDMFormValues</code>
	 in the <code>dynamic.data.mapping.api</code> module.
	 * @param serviceContext the service context to be applied. This can set
	 the record modified date.
	 * @return the record
	 * @throws PortalException if a portal exception occurred
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
			long recordId, boolean majorVersion, int displayIndex,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateRecord(
			recordId, majorVersion, displayIndex, ddmFormValues,
			serviceContext);
	}

	/**
	 * Updates a record, replacing its display index and values.
	 *
	 * @param recordId the primary key of the record
	 * @param majorVersion whether this update is a major change. Major
	 changes causes the increment of the major version number.
	 * @param displayIndex the index position in which the record is
	 displayed in the spreadsheet view
	 * @param fields the record values. See <code>Fields</code> in the
	 <code>dynamic.data.mapping.api</code> module.
	 * @param mergeFields whether to merge the new fields with the existing
	 ones; otherwise replace the existing fields
	 * @param serviceContext the service context to be applied. This can
	 set the record modified date.
	 * @return the record
	 * @throws PortalException if a portal exception occurred
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #updateRecord(long, boolean, int, DDMFormValues,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
			long recordId, boolean majorVersion, int displayIndex,
			com.liferay.dynamic.data.mapping.storage.Fields fields,
			boolean mergeFields,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateRecord(
			recordId, majorVersion, displayIndex, fields, mergeFields,
			serviceContext);
	}

	public static DDLRecordService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDLRecordService, DDLRecordService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDLRecordService.class);

		ServiceTracker<DDLRecordService, DDLRecordService> serviceTracker =
			new ServiceTracker<DDLRecordService, DDLRecordService>(
				bundle.getBundleContext(), DDLRecordService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}