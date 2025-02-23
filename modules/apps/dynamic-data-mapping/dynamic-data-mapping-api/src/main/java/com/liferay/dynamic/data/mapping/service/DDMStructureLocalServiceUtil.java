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

package com.liferay.dynamic.data.mapping.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DDMStructure. This utility wraps
 * <code>com.liferay.dynamic.data.mapping.service.impl.DDMStructureLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLocalService
 * @generated
 */
public class DDMStructureLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMStructureLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the ddm structure to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStructure the ddm structure
	 * @return the ddm structure that was added
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
		addDDMStructure(
			com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure) {

		return getService().addDDMStructure(ddmStructure);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			addStructure(
				long userId, long groupId, long parentStructureId,
				long classNameId, String structureKey,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				String storageType, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addStructure(
			userId, groupId, parentStructureId, classNameId, structureKey,
			nameMap, descriptionMap, ddmForm, ddmFormLayout, storageType, type,
			serviceContext);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			addStructure(
				long userId, long groupId, long parentStructureId,
				long classNameId, String structureKey,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String definition, String storageType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addStructure(
			userId, groupId, parentStructureId, classNameId, structureKey,
			nameMap, descriptionMap, definition, storageType, serviceContext);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			addStructure(
				long userId, long groupId, long classNameId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				String storageType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addStructure(
			userId, groupId, classNameId, nameMap, descriptionMap, ddmForm,
			ddmFormLayout, storageType, serviceContext);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			addStructure(
				long userId, long groupId, String parentStructureKey,
				long classNameId, String structureKey,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				String storageType, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addStructure(
			userId, groupId, parentStructureKey, classNameId, structureKey,
			nameMap, descriptionMap, ddmForm, ddmFormLayout, storageType, type,
			serviceContext);
	}

	/**
	 * Adds the resources to the structure.
	 *
	 * @param structure the structure to add resources to
	 * @param addGroupPermissions whether to add group permissions
	 * @param addGuestPermissions whether to add guest permissions
	 */
	public static void addStructureResources(
			com.liferay.dynamic.data.mapping.model.DDMStructure structure,
			boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addStructureResources(
			structure, addGroupPermissions, addGuestPermissions);
	}

	/**
	 * Adds the model resources with the permissions to the structure.
	 *
	 * @param structure the structure to add resources to
	 * @param modelPermissions the model permissions to be added
	 */
	public static void addStructureResources(
			com.liferay.dynamic.data.mapping.model.DDMStructure structure,
			com.liferay.portal.kernel.service.permission.ModelPermissions
				modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addStructureResources(structure, modelPermissions);
	}

	/**
	 * Copies a structure, creating a new structure with all the values
	 * extracted from the original one. The new structure supports a new name
	 * and description.
	 *
	 * @param userId the primary key of the structure's creator/owner
	 * @param structureId the primary key of the structure to be copied
	 * @param nameMap the new structure's locales and localized names
	 * @param descriptionMap the new structure's locales and localized
	 descriptions
	 * @param serviceContext the service context to be applied. Can set the
	 UUID, creation date, modification date, guest permissions, and
	 group permissions for the structure.
	 * @return the new structure
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			copyStructure(
				long userId, long structureId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().copyStructure(
			userId, structureId, nameMap, descriptionMap, serviceContext);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			copyStructure(
				long userId, long structureId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().copyStructure(userId, structureId, serviceContext);
	}

	/**
	 * Creates a new ddm structure with the primary key. Does not add the ddm structure to the database.
	 *
	 * @param structureId the primary key for the new ddm structure
	 * @return the new ddm structure
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
		createDDMStructure(long structureId) {

		return getService().createDDMStructure(structureId);
	}

	/**
	 * Deletes the ddm structure from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStructure the ddm structure
	 * @return the ddm structure that was removed
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
		deleteDDMStructure(
			com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure) {

		return getService().deleteDDMStructure(ddmStructure);
	}

	/**
	 * Deletes the ddm structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureId the primary key of the ddm structure
	 * @return the ddm structure that was removed
	 * @throws PortalException if a ddm structure with the primary key could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			deleteDDMStructure(long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDDMStructure(structureId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the structure and its resources.
	 *
	 * <p>
	 * Before deleting the structure, this method verifies whether the structure
	 * is required by another entity. If it is needed, an exception is thrown.
	 * </p>
	 *
	 * @param structure the structure to be deleted
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			deleteStructure(
				com.liferay.dynamic.data.mapping.model.DDMStructure structure)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteStructure(structure);
	}

	/**
	 * Deletes the structure and its resources.
	 *
	 * <p>
	 * Before deleting the structure, the system verifies whether the structure
	 * is required by another entity. If it is needed, an exception is thrown.
	 * </p>
	 *
	 * @param structureId the primary key of the structure to be deleted
	 */
	public static void deleteStructure(long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteStructure(structureId);
	}

	/**
	 * Deletes the matching structure and its resources.
	 *
	 * <p>
	 * Before deleting the structure, the system verifies whether the structure
	 * is required by another entity. If it is needed, an exception is thrown.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @param structureKey the unique string identifying the structure
	 */
	public static void deleteStructure(
			long groupId, long classNameId, String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteStructure(groupId, classNameId, structureKey);
	}

	/**
	 * Deletes all the structures of the group.
	 *
	 * <p>
	 * Before deleting the structures, the system verifies whether each
	 * structure is required by another entity. If any of the structures are
	 * needed, an exception is thrown.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 */
	public static void deleteStructures(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteStructures(groupId);
	}

	public static void deleteStructures(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteStructures(groupId, classNameId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMStructureModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMStructureModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
		fetchDDMStructure(long structureId) {

		return getService().fetchDDMStructure(structureId);
	}

	/**
	 * Returns the ddm structure matching the UUID and group.
	 *
	 * @param uuid the ddm structure's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm structure, or <code>null</code> if a matching ddm structure could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
		fetchDDMStructureByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchDDMStructureByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns the structure with the ID.
	 *
	 * @param structureId the primary key of the structure
	 * @return the structure with the structure ID, or <code>null</code> if a
	 matching structure could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
		fetchStructure(long structureId) {

		return getService().fetchStructure(structureId);
	}

	/**
	 * Returns the structure matching the class name ID, structure key, and
	 * group.
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @param structureKey the unique string identifying the structure
	 * @return the matching structure, or <code>null</code> if a matching
	 structure could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
		fetchStructure(long groupId, long classNameId, String structureKey) {

		return getService().fetchStructure(groupId, classNameId, structureKey);
	}

	/**
	 * Returns the structure matching the class name ID, structure key, and
	 * group, optionally searching ancestor sites (that have sharing enabled)
	 * and global scoped sites.
	 *
	 * <p>
	 * This method first searches in the group. If the structure is still not
	 * found and <code>includeAncestorStructures</code> is set to
	 * <code>true</code>, this method searches the group's ancestor sites (that
	 * have sharing enabled) and lastly searches global scoped sites.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @param structureKey the unique string identifying the structure
	 * @param includeAncestorStructures whether to include ancestor sites (that
	 have sharing enabled) and include global scoped sites in the
	 search
	 * @return the matching structure, or <code>null</code> if a matching
	 structure could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
		fetchStructure(
			long groupId, long classNameId, String structureKey,
			boolean includeAncestorStructures) {

		return getService().fetchStructure(
			groupId, classNameId, structureKey, includeAncestorStructures);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
		fetchStructureByUuidAndGroupId(
			String uuid, long groupId, boolean includeAncestorStructures) {

		return getService().fetchStructureByUuidAndGroupId(
			uuid, groupId, includeAncestorStructures);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure>
			getChildrenStructures(long parentStructureId) {

		return getService().getChildrenStructures(parentStructureId);
	}

	/**
	 * Returns all the structures matching the class name ID.
	 *
	 * @param companyId the primary key of the structure's company
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @return the structures matching the class name ID
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure>
			getClassStructures(long companyId, long classNameId) {

		return getService().getClassStructures(companyId, classNameId);
	}

	/**
	 * Returns a range of all the structures matching the class name ID.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param companyId the primary key of the structure's company
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @param start the lower bound of the range of structures to return
	 * @param end the upper bound of the range of structures to return (not
	 inclusive)
	 * @return the range of matching structures
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure>
			getClassStructures(
				long companyId, long classNameId, int start, int end) {

		return getService().getClassStructures(
			companyId, classNameId, start, end);
	}

	/**
	 * Returns all the structures matching the class name ID ordered by the
	 * comparator.
	 *
	 * @param companyId the primary key of the structure's company
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @param orderByComparator the comparator to order the structures
	 (optionally <code>null</code>)
	 * @return the matching structures ordered by the comparator
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure>
			getClassStructures(
				long companyId, long classNameId,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMStructure>
						orderByComparator) {

		return getService().getClassStructures(
			companyId, classNameId, orderByComparator);
	}

	/**
	 * Returns the ddm structure with the primary key.
	 *
	 * @param structureId the primary key of the ddm structure
	 * @return the ddm structure
	 * @throws PortalException if a ddm structure with the primary key could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			getDDMStructure(long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDDMStructure(structureId);
	}

	/**
	 * Returns the ddm structure matching the UUID and group.
	 *
	 * @param uuid the ddm structure's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm structure
	 * @throws PortalException if a matching ddm structure could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			getDDMStructureByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDDMStructureByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the ddm structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMStructureModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structures
	 * @param end the upper bound of the range of ddm structures (not inclusive)
	 * @return the range of ddm structures
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getDDMStructures(
			int start, int end) {

		return getService().getDDMStructures(start, end);
	}

	/**
	 * Returns all the ddm structures matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddm structures
	 * @param companyId the primary key of the company
	 * @return the matching ddm structures, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure>
			getDDMStructuresByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getDDMStructuresByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of ddm structures matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddm structures
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of ddm structures
	 * @param end the upper bound of the range of ddm structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching ddm structures, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure>
			getDDMStructuresByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMStructure>
						orderByComparator) {

		return getService().getDDMStructuresByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ddm structures.
	 *
	 * @return the number of ddm structures
	 */
	public static int getDDMStructuresCount() {
		return getService().getDDMStructuresCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the structure with the ID.
	 *
	 * @param structureId the primary key of the structure
	 * @return the structure with the ID
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			getStructure(long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getStructure(structureId);
	}

	/**
	 * Returns the structure matching the class name ID, structure key, and
	 * group.
	 *
	 * @param groupId the primary key of the structure's group
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @param structureKey the unique string identifying the structure
	 * @return the matching structure
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			getStructure(long groupId, long classNameId, String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getStructure(groupId, classNameId, structureKey);
	}

	/**
	 * Returns the structure matching the class name ID, structure key, and
	 * group, optionally searching ancestor sites (that have sharing enabled)
	 * and global scoped sites.
	 *
	 * <p>
	 * This method first searches in the group. If the structure is still not
	 * found and <code>includeAncestorStructures</code> is set to
	 * <code>true</code>, this method searches the group's ancestor sites (that
	 * have sharing enabled) and lastly searches global scoped sites.
	 * </p>
	 *
	 * @param groupId the primary key of the structure's group
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @param structureKey the unique string identifying the structure
	 * @param includeAncestorStructures whether to include ancestor sites (that
	 have sharing enabled) and include global scoped sites in the
	 search in the search
	 * @return the matching structure
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			getStructure(
				long groupId, long classNameId, String structureKey,
				boolean includeAncestorStructures)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getStructure(
			groupId, classNameId, structureKey, includeAncestorStructures);
	}

	/**
	 * Returns all the structures matching the group, name, and description.
	 *
	 * @param groupId the primary key of the structure's group
	 * @param name the structure's name
	 * @param description the structure's description
	 * @return the matching structures
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructure(
			long groupId, String name, String description) {

		return getService().getStructure(groupId, name, description);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMForm
			getStructureDDMForm(
				com.liferay.dynamic.data.mapping.model.DDMStructure structure)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getStructureDDMForm(structure);
	}

	/**
	 * Returns all the structures present in the system.
	 *
	 * @return the structures present in the system
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures() {

		return getService().getStructures();
	}

	/**
	 * Returns all the structures present in the group.
	 *
	 * @param groupId the primary key of the group
	 * @return the structures present in the group
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			long groupId) {

		return getService().getStructures(groupId);
	}

	/**
	 * Returns a range of all the structures belonging to the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @param start the lower bound of the range of structures to return
	 * @param end the upper bound of the range of structures to return (not
	 inclusive)
	 * @return the range of matching structures
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			long groupId, int start, int end) {

		return getService().getStructures(groupId, start, end);
	}

	/**
	 * Returns all the structures matching class name ID and group.
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @return the matching structures
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			long groupId, long classNameId) {

		return getService().getStructures(groupId, classNameId);
	}

	/**
	 * Returns a range of all the structures that match the class name ID and
	 * group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @param start the lower bound of the range of structures to return
	 * @param end the upper bound of the range of structures to return (not
	 inclusive)
	 * @return the range of matching structures
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			long groupId, long classNameId, int start, int end) {

		return getService().getStructures(groupId, classNameId, start, end);
	}

	/**
	 * Returns an ordered range of all the structures matching the class name ID
	 * and group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @param start the lower bound of the range of structures to return
	 * @param end the upper bound of the range of structures to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the structures
	 (optionally <code>null</code>)
	 * @return the range of matching structures ordered by the comparator
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			long groupId, long classNameId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator) {

		return getService().getStructures(
			groupId, classNameId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			long companyId, long[] groupIds, long classNameId, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator) {

		return getService().getStructures(
			companyId, groupIds, classNameId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			long companyId, long[] groupIds, long classNameId, String keywords,
			int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator) {

		return getService().getStructures(
			companyId, groupIds, classNameId, keywords, status, start, end,
			orderByComparator);
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			long groupId, String name, String description) {

		return getService().getStructures(groupId, name, description);
	}

	/**
	 * Returns all the structures belonging to the groups.
	 *
	 * @param groupIds the primary keys of the groups
	 * @return the structures belonging to the groups
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			long[] groupIds) {

		return getService().getStructures(groupIds);
	}

	/**
	 * Returns all the structures matching the class name ID and belonging to
	 * the groups.
	 *
	 * @param groupIds the primary keys of the groups
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @return the matching structures
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			long[] groupIds, long classNameId) {

		return getService().getStructures(groupIds, classNameId);
	}

	/**
	 * Returns a range of all the structures matching the class name ID and
	 * belonging to the groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupIds the primary keys of the groups
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @param start the lower bound of the range of structures to return
	 * @param end the upper bound of the range of structures to return (not
	 inclusive)
	 * @return the range of matching structures
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			long[] groupIds, long classNameId, int start, int end) {

		return getService().getStructures(groupIds, classNameId, start, end);
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			long[] groupIds, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator) {

		return getService().getStructures(
			groupIds, classNameId, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the structures matching the group, class
	 * name ID, name, and description.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupIds the primary keys of the groups
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @param name the name keywords
	 * @param description the description keywords
	 * @param start the lower bound of the range of structures to return
	 * @param end the upper bound of the range of structures to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the structures
	 (optionally <code>null</code>)
	 * @return the range of matching structures ordered by the comparator
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			long[] groupIds, long classNameId, String name, String description,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator) {

		return getService().getStructures(
			groupIds, classNameId, name, description, start, end,
			orderByComparator);
	}

	/**
	 * Returns the number of structures belonging to the group.
	 *
	 * @param groupId the primary key of the group
	 * @return the number of structures belonging to the group
	 */
	public static int getStructuresCount(long groupId) {
		return getService().getStructuresCount(groupId);
	}

	/**
	 * Returns the number of structures matching the class name ID and group.
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @return the number of matching structures
	 */
	public static int getStructuresCount(long groupId, long classNameId) {
		return getService().getStructuresCount(groupId, classNameId);
	}

	public static int getStructuresCount(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status) {

		return getService().getStructuresCount(
			companyId, groupIds, classNameId, keywords, status);
	}

	/**
	 * Returns the number of structures matching the class name ID and belonging
	 * to the groups.
	 *
	 * @param groupIds the primary keys of the groups
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @return the number of matching structures
	 */
	public static int getStructuresCount(long[] groupIds, long classNameId) {
		return getService().getStructuresCount(groupIds, classNameId);
	}

	public static String prepareLocalizedDefinitionForImport(
		com.liferay.dynamic.data.mapping.model.DDMStructure structure,
		java.util.Locale defaultImportLocale) {

		return getService().prepareLocalizedDefinitionForImport(
			structure, defaultImportLocale);
	}

	public static void revertStructure(
			long userId, long structureId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().revertStructure(
			userId, structureId, version, serviceContext);
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> search(
				long companyId, long[] groupIds, long classNameId, long classPK,
				String keywords, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMStructure>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(
			companyId, groupIds, classNameId, classPK, keywords, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the structures matching the groups and
	 * class name IDs, and matching the keywords in the structure names and
	 * descriptions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param companyId the primary key of the structure's company
	 * @param groupIds the primary keys of the groups
	 * @param classNameId the primary key of the class name of the model the
	 structure is related to
	 * @param keywords the keywords (space separated), which may occur in the
	 structure's name or description (optionally <code>null</code>)
	 * @param start the lower bound of the range of structures to return
	 * @param end the upper bound of the range of structures to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the structures
	 (optionally <code>null</code>)
	 * @return the range of matching structures ordered by the comparator
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> search(
			long companyId, long[] groupIds, long classNameId, String keywords,
			int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator) {

		return getService().search(
			companyId, groupIds, classNameId, keywords, status, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the structures matching the groups, class
	 * name IDs, name keyword, description keyword, storage type, and type.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param companyId the primary key of the structure's company
	 * @param groupIds the primary keys of the groups
	 * @param classNameId the primary key of the class name of the model the
	 structure is related to
	 * @param name the name keywords
	 * @param description the description keywords
	 * @param storageType the structure's storage type. It can be "xml" or
	 "expando". For more information, see {@link StorageType}.
	 * @param type the structure's type. For more information, see {@link
	 DDMStructureConstants}.
	 * @param andOperator whether every field must match its keywords, or just
	 one field
	 * @param start the lower bound of the range of structures to return
	 * @param end the upper bound of the range of structures to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the structures
	 (optionally <code>null</code>)
	 * @return the range of matching structures ordered by the comparator
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> search(
			long companyId, long[] groupIds, long classNameId, String name,
			String description, String storageType, int type, int status,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator) {

		return getService().search(
			companyId, groupIds, classNameId, name, description, storageType,
			type, status, andOperator, start, end, orderByComparator);
	}

	public static int searchCount(
			long companyId, long[] groupIds, long classNameId, long classPK,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCount(
			companyId, groupIds, classNameId, classPK, keywords);
	}

	/**
	 * Returns the number of structures matching the groups and class name IDs,
	 * and matching the keywords in the structure names and descriptions.
	 *
	 * @param companyId the primary key of the structure's company
	 * @param groupIds the primary keys of the groups
	 * @param classNameId the primary key of the class name of the model the
	 structure is related to
	 * @param keywords the keywords (space separated), which may occur in the
	 structure's name or description (optionally <code>null</code>)
	 * @return the number of matching structures
	 */
	public static int searchCount(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status) {

		return getService().searchCount(
			companyId, groupIds, classNameId, keywords, status);
	}

	/**
	 * Returns the number of structures matching the groups, class name IDs,
	 * name keyword, description keyword, storage type, and type
	 *
	 * @param companyId the primary key of the structure's company
	 * @param groupIds the primary keys of the groups
	 * @param classNameId
	 * @param name the name keywords
	 * @param description the description keywords
	 * @param storageType the structure's storage type. It can be "xml" or
	 "expando". For more information, see {@link StorageType}.
	 * @param type the structure's type. For more information, see {@link
	 DDMStructureConstants}.
	 * @param andOperator whether every field must match its keywords, or just
	 one field
	 * @return the number of matching structures
	 */
	public static int searchCount(
		long companyId, long[] groupIds, long classNameId, String name,
		String description, String storageType, int type, int status,
		boolean andOperator) {

		return getService().searchCount(
			companyId, groupIds, classNameId, name, description, storageType,
			type, status, andOperator);
	}

	/**
	 * Updates the ddm structure in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStructure the ddm structure
	 * @return the ddm structure that was updated
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructure
		updateDDMStructure(
			com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure) {

		return getService().updateDDMStructure(ddmStructure);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			updateStructure(
				long userId, long structureId,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStructure(
			userId, structureId, ddmForm, ddmFormLayout, serviceContext);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			updateStructure(
				long userId, long groupId, long parentStructureId,
				long classNameId, String structureKey,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStructure(
			userId, groupId, parentStructureId, classNameId, structureKey,
			nameMap, descriptionMap, ddmForm, ddmFormLayout, serviceContext);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			updateStructure(
				long userId, long structureId, long parentStructureId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStructure(
			userId, structureId, parentStructureId, nameMap, descriptionMap,
			ddmForm, ddmFormLayout, serviceContext);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			updateStructure(
				long userId, long structureId, long parentStructureId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String definition,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStructure(
			userId, structureId, parentStructureId, nameMap, descriptionMap,
			definition, serviceContext);
	}

	public static DDMStructureLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DDMStructureLocalService, DDMStructureLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDMStructureLocalService.class);

		ServiceTracker<DDMStructureLocalService, DDMStructureLocalService>
			serviceTracker =
				new ServiceTracker
					<DDMStructureLocalService, DDMStructureLocalService>(
						bundle.getBundleContext(),
						DDMStructureLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}