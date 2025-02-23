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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.document.library.kernel.exception.DuplicateFileEntryTypeException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryTypeException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.exception.NoSuchMetadataSetException;
import com.liferay.document.library.kernel.exception.RequiredFileEntryTypeException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.persistence.DLFolderPersistence;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureLink;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureLinkManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManager;
import com.liferay.dynamic.data.mapping.kernel.StructureDefinitionException;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryTypeLocalServiceBaseImpl;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.dependency.ServiceDependencyListener;
import com.liferay.registry.dependency.ServiceDependencyManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Provides the local service for accessing, adding, cascading, deleting, and
 * updating file and folder file entry types.
 *
 * @author Alexander Chow
 * @author Sergio González
 */
public class DLFileEntryTypeLocalServiceImpl
	extends DLFileEntryTypeLocalServiceBaseImpl {

	@Override
	public void addDDMStructureLinks(
		long fileEntryTypeId, Set<Long> ddmStructureIds) {

		long classNameId = classNameLocalService.getClassNameId(
			DLFileEntryType.class);

		for (long ddmStructureId : ddmStructureIds) {
			DDMStructureLinkManagerUtil.addStructureLink(
				classNameId, fileEntryTypeId, ddmStructureId);
		}
	}

	@Override
	public DLFileEntryType addFileEntryType(
			long userId, long groupId, String fileEntryTypeKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (Validator.isNull(fileEntryTypeKey)) {
			fileEntryTypeKey = String.valueOf(counterLocalService.increment());
		}
		else {
			fileEntryTypeKey = StringUtil.toUpperCase(fileEntryTypeKey.trim());
		}

		String fileEntryTypeUuid = serviceContext.getUuid();

		if (Validator.isNull(fileEntryTypeUuid)) {
			fileEntryTypeUuid = PortalUUIDUtil.generate();
		}

		long fileEntryTypeId = counterLocalService.increment();

		ddmStructureIds = _updateDDMStructure(
			userId, fileEntryTypeUuid, fileEntryTypeId, groupId, nameMap,
			descriptionMap, ddmStructureIds, serviceContext);

		validate(fileEntryTypeId, groupId, fileEntryTypeKey, ddmStructureIds);

		DLFileEntryType dlFileEntryType = dlFileEntryTypePersistence.create(
			fileEntryTypeId);

		dlFileEntryType.setUuid(fileEntryTypeUuid);
		dlFileEntryType.setGroupId(groupId);
		dlFileEntryType.setCompanyId(user.getCompanyId());
		dlFileEntryType.setUserId(user.getUserId());
		dlFileEntryType.setUserName(user.getFullName());
		dlFileEntryType.setFileEntryTypeKey(fileEntryTypeKey);
		dlFileEntryType.setNameMap(nameMap);
		dlFileEntryType.setDescriptionMap(descriptionMap);

		dlFileEntryTypePersistence.update(dlFileEntryType);

		addDDMStructureLinks(
			fileEntryTypeId, SetUtil.fromArray(ddmStructureIds));

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addFileEntryTypeResources(
				dlFileEntryType, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addFileEntryTypeResources(
				dlFileEntryType, serviceContext.getModelPermissions());
		}

		return dlFileEntryType;
	}

	@Override
	public DLFileEntryType addFileEntryType(
			long userId, long groupId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(), name
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(), description
		).build();

		return addFileEntryType(
			userId, groupId, null, nameMap, descriptionMap, ddmStructureIds,
			serviceContext);
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		serviceDependencyManager.addServiceDependencyListener(
			new ServiceDependencyListener() {

				@Override
				public void dependenciesFulfilled() {
					try {
						dlFileEntryTypeLocalService.
							getBasicDocumentDLFileEntryType();
					}
					catch (NoSuchFileEntryTypeException nsfete) {
						ReflectionUtil.throwException(nsfete);
					}
				}

				@Override
				public void destroy() {
				}

			});

		Registry registry = RegistryUtil.getRegistry();

		serviceDependencyManager.registerDependencies(
			new Class<?>[] {ModuleServiceLifecycle.class},
			new Filter[] {
				registry.getFilter(ModuleServiceLifecycle.DATABASE_INITIALIZED)
			});
	}

	@Override
	public void cascadeFileEntryTypes(long userId, DLFolder dlFolder)
		throws PortalException {

		long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(
			dlFolder.getGroupId());

		List<DLFileEntryType> dlFileEntryTypes = getFolderFileEntryTypes(
			groupIds, dlFolder.getFolderId(), true);

		List<Long> fileEntryTypeIds = getFileEntryTypeIds(dlFileEntryTypes);

		long defaultFileEntryTypeId = getDefaultFileEntryTypeId(
			dlFolder.getFolderId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(dlFolder.getCompanyId());
		serviceContext.setScopeGroupId(dlFolder.getGroupId());
		serviceContext.setUserId(userId);

		cascadeFileEntryTypes(
			userId, dlFolder.getGroupId(), dlFolder.getFolderId(),
			defaultFileEntryTypeId, fileEntryTypeIds, serviceContext);
	}

	@Override
	public DLFileEntryType createBasicDocumentDLFileEntryType()
		throws NoSuchFileEntryTypeException {

		DLFileEntryType dlFileEntryType = dlFileEntryTypePersistence.create(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		dlFileEntryType.setCompanyId(CompanyConstants.SYSTEM);
		dlFileEntryType.setFileEntryTypeKey(
			StringUtil.toUpperCase(
				DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT));
		dlFileEntryType.setName(
			DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT,
			LocaleUtil.getDefault());

		return dlFileEntryTypePersistence.update(dlFileEntryType);
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public void deleteFileEntryType(DLFileEntryType dlFileEntryType)
		throws PortalException {

		int count = dlFileEntryPersistence.countByFileEntryTypeId(
			dlFileEntryType.getFileEntryTypeId());

		if (count > 0) {
			throw new RequiredFileEntryTypeException(
				"There are file entries of file entry type " +
					dlFileEntryType.getFileEntryTypeId());
		}

		DDMStructure ddmStructure = DDMStructureManagerUtil.fetchStructure(
			dlFileEntryType.getGroupId(),
			classNameLocalService.getClassNameId(DLFileEntryMetadata.class),
			DLUtil.getDDMStructureKey(dlFileEntryType));

		if (ddmStructure == null) {
			ddmStructure = DDMStructureManagerUtil.fetchStructure(
				dlFileEntryType.getGroupId(),
				classNameLocalService.getClassNameId(DLFileEntryMetadata.class),
				DLUtil.getDeprecatedDDMStructureKey(dlFileEntryType));
		}

		if (ddmStructure != null) {
			long classNameId = classNameLocalService.getClassNameId(
				DLFileEntryType.class);

			DDMStructureLinkManagerUtil.deleteStructureLink(
				classNameId, dlFileEntryType.getFileEntryTypeId(),
				ddmStructure.getStructureId());

			DDMStructureManagerUtil.deleteStructure(
				ddmStructure.getStructureId());
		}

		dlFileEntryTypePersistence.remove(dlFileEntryType);
	}

	@Override
	public void deleteFileEntryType(long fileEntryTypeId)
		throws PortalException {

		DLFileEntryType dlFileEntryType =
			dlFileEntryTypePersistence.findByPrimaryKey(fileEntryTypeId);

		dlFileEntryTypeLocalService.deleteFileEntryType(dlFileEntryType);
	}

	@Override
	public void deleteFileEntryTypes(long groupId) throws PortalException {
		List<DLFileEntryType> dlFileEntryTypes =
			dlFileEntryTypePersistence.findByGroupId(groupId);

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			dlFileEntryTypeLocalService.deleteFileEntryType(dlFileEntryType);
		}
	}

	@Override
	public DLFileEntryType fetchFileEntryType(long fileEntryTypeId) {
		return dlFileEntryTypePersistence.fetchByPrimaryKey(fileEntryTypeId);
	}

	@Override
	public DLFileEntryType fetchFileEntryType(
		long groupId, String fileEntryTypeKey) {

		fileEntryTypeKey = StringUtil.toUpperCase(
			StringUtil.trim(fileEntryTypeKey));

		return dlFileEntryTypePersistence.fetchByG_F(groupId, fileEntryTypeKey);
	}

	@Override
	public DLFileEntryType getBasicDocumentDLFileEntryType()
		throws NoSuchFileEntryTypeException {

		DLFileEntryType dlFileEntryType =
			dlFileEntryTypePersistence.fetchByPrimaryKey(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		if (dlFileEntryType != null) {
			return dlFileEntryType;
		}

		return dlFileEntryTypeLocalService.createBasicDocumentDLFileEntryType();
	}

	@Override
	public long getDefaultFileEntryTypeId(long folderId)
		throws PortalException {

		folderId = getFileEntryTypesPrimaryFolderId(
			dlFolderPersistence, folderId);

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

			return dlFolder.getDefaultFileEntryTypeId();
		}

		return 0;
	}

	@Override
	public DLFileEntryType getFileEntryType(long fileEntryTypeId)
		throws PortalException {

		return dlFileEntryTypePersistence.findByPrimaryKey(fileEntryTypeId);
	}

	@Override
	public DLFileEntryType getFileEntryType(
			long groupId, String fileEntryTypeKey)
		throws PortalException {

		fileEntryTypeKey = StringUtil.toUpperCase(
			StringUtil.trim(fileEntryTypeKey));

		return dlFileEntryTypePersistence.findByG_F(groupId, fileEntryTypeKey);
	}

	@Override
	public List<DLFileEntryType> getFileEntryTypes(long ddmStructureId)
		throws PortalException {

		List<DLFileEntryType> fileEntryTypes = new ArrayList<>();

		long classNameId = classNameLocalService.getClassNameId(
			DLFileEntryType.class);

		List<DDMStructureLink> ddmStructureLinks =
			DDMStructureLinkManagerUtil.getClassNameStructureLinks(classNameId);

		for (DDMStructureLink ddmStructureLink : ddmStructureLinks) {
			if (ddmStructureId != ddmStructureLink.getStructureId()) {
				continue;
			}

			DLFileEntryType fileEntryType = getFileEntryType(
				ddmStructureLink.getClassPK());

			fileEntryTypes.add(fileEntryType);
		}

		return fileEntryTypes;
	}

	@Override
	public List<DLFileEntryType> getFileEntryTypes(long[] groupIds) {
		return dlFileEntryTypePersistence.findByGroupId(groupIds);
	}

	@Override
	public List<DLFileEntryType> getFolderFileEntryTypes(
			long[] groupIds, long folderId, boolean inherited)
		throws PortalException {

		if (!inherited) {
			return dlFolderPersistence.getDLFileEntryTypes(folderId);
		}

		List<DLFileEntryType> dlFileEntryTypes = null;

		folderId = getFileEntryTypesPrimaryFolderId(
			dlFolderPersistence, folderId);

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			dlFileEntryTypes = dlFolderPersistence.getDLFileEntryTypes(
				folderId);
		}
		else {
			dlFileEntryTypes = new ArrayList<>(getFileEntryTypes(groupIds));

			DLFileEntryType dlFileEntryType =
				dlFileEntryTypeLocalService.getBasicDocumentDLFileEntryType();

			dlFileEntryTypes.add(0, dlFileEntryType);
		}

		return dlFileEntryTypes;
	}

	@Override
	public List<DLFileEntryType> search(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {

		return dlFileEntryTypeFinder.findByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType, start,
			end, orderByComparator);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType) {

		return dlFileEntryTypeFinder.countByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType);
	}

	@Override
	public void unsetFolderFileEntryTypes(long folderId) {
		List<DLFileEntryType> dlFileEntryTypes =
			dlFolderPersistence.getDLFileEntryTypes(folderId);

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			dlFolderPersistence.removeDLFileEntryType(
				folderId, dlFileEntryType);
		}
	}

	@Override
	public void updateDDMStructureLinks(
			long fileEntryTypeId, Set<Long> ddmStructureIds)
		throws PortalException {

		Set<Long> existingDDMStructureLinkStructureIds =
			getExistingDDMStructureLinkStructureIds(fileEntryTypeId);

		deleteDDMStructureLinks(
			fileEntryTypeId,
			getStaleDDMStructureLinkStructureIds(
				ddmStructureIds, existingDDMStructureLinkStructureIds));

		addDDMStructureLinks(
			fileEntryTypeId,
			getMissingDDMStructureLinkStructureIds(
				ddmStructureIds, existingDDMStructureLinkStructureIds));
	}

	@Override
	public DLFileEntry updateFileEntryFileEntryType(
			DLFileEntry dlFileEntry, ServiceContext serviceContext)
		throws PortalException {

		long groupId = serviceContext.getScopeGroupId();
		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		DLFolder dlFolder = dlFolderPersistence.fetchByPrimaryKey(
			dlFileEntry.getFolderId());

		if (dlFolder != null) {
			groupId = dlFolder.getGroupId();
			folderId = dlFolder.getFolderId();
		}

		List<DLFileEntryType> dlFileEntryTypes = getFolderFileEntryTypes(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId), folderId,
			true);

		List<Long> fileEntryTypeIds = getFileEntryTypeIds(dlFileEntryTypes);

		if (fileEntryTypeIds.contains(dlFileEntry.getFileEntryTypeId())) {
			return dlFileEntry;
		}

		long defaultFileEntryTypeId = getDefaultFileEntryTypeId(folderId);

		DLFileVersion dlFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(
				dlFileEntry.getFileEntryId(), true);

		if (dlFileVersion.isPending()) {
			workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
				dlFileVersion.getCompanyId(), dlFileEntry.getGroupId(),
				DLFileEntry.class.getName(), dlFileVersion.getFileVersionId());
		}

		return dlFileEntryLocalService.updateFileEntry(
			serviceContext.getUserId(), dlFileEntry.getFileEntryId(), null,
			null, null, null, null, false, defaultFileEntryTypeId, null, null,
			null, 0, serviceContext);
	}

	@Override
	public void updateFileEntryType(
			long userId, long fileEntryTypeId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long[] ddmStructureIds,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileEntryType dlFileEntryType =
			dlFileEntryTypePersistence.findByPrimaryKey(fileEntryTypeId);

		ddmStructureIds = _updateDDMStructure(
			userId, dlFileEntryType.getUuid(), fileEntryTypeId,
			dlFileEntryType.getGroupId(), nameMap, descriptionMap,
			ddmStructureIds, serviceContext);

		validate(
			fileEntryTypeId, dlFileEntryType.getGroupId(),
			dlFileEntryType.getFileEntryTypeKey(), ddmStructureIds);

		dlFileEntryType.setNameMap(nameMap);
		dlFileEntryType.setDescriptionMap(descriptionMap);

		dlFileEntryTypePersistence.update(dlFileEntryType);

		updateDDMStructureLinks(
			fileEntryTypeId, SetUtil.fromArray(ddmStructureIds));
	}

	@Override
	public void updateFileEntryType(
			long userId, long fileEntryTypeId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(), name
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(), description
		).build();

		updateFileEntryType(
			userId, fileEntryTypeId, nameMap, descriptionMap, ddmStructureIds,
			serviceContext);
	}

	@Override
	public void updateFolderFileEntryTypes(
		DLFolder dlFolder, List<Long> fileEntryTypeIds,
		long defaultFileEntryTypeId, ServiceContext serviceContext) {

		List<Long> originalFileEntryTypeIds = getFileEntryTypeIds(
			dlFolderPersistence.getDLFileEntryTypes(dlFolder.getFolderId()));

		if (fileEntryTypeIds.equals(originalFileEntryTypeIds)) {
			return;
		}

		for (Long fileEntryTypeId : fileEntryTypeIds) {
			if (!originalFileEntryTypeIds.contains(fileEntryTypeId)) {
				dlFolderPersistence.addDLFileEntryType(
					dlFolder.getFolderId(), fileEntryTypeId);
			}
		}

		for (Long originalFileEntryTypeId : originalFileEntryTypeIds) {
			if (!fileEntryTypeIds.contains(originalFileEntryTypeId)) {
				dlFolderPersistence.removeDLFileEntryType(
					dlFolder.getFolderId(), originalFileEntryTypeId);

				workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(
					dlFolder.getCompanyId(), dlFolder.getGroupId(),
					DLFolder.class.getName(), dlFolder.getFolderId(),
					originalFileEntryTypeId);
			}
		}
	}

	protected static long getDefaultFileEntryTypeId(
			DLFolderPersistence dlFolderPersistence, long folderId)
		throws PortalException {

		folderId = getFileEntryTypesPrimaryFolderId(
			dlFolderPersistence, folderId);

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

			return dlFolder.getDefaultFileEntryTypeId();
		}

		return 0;
	}

	protected static long getFileEntryTypesPrimaryFolderId(
			DLFolderPersistence dlFolderPersistence, long folderId)
		throws NoSuchFolderException {

		while (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

			if (dlFolder.getRestrictionType() ==
					DLFolderConstants.
						RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW) {

				break;
			}

			folderId = dlFolder.getParentFolderId();
		}

		return folderId;
	}

	protected void addFileEntryTypeResources(
			DLFileEntryType dlFileEntryType, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			dlFileEntryType.getCompanyId(), dlFileEntryType.getGroupId(),
			dlFileEntryType.getUserId(), DLFileEntryType.class.getName(),
			dlFileEntryType.getFileEntryTypeId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	protected void addFileEntryTypeResources(
			DLFileEntryType dlFileEntryType, ModelPermissions modelPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			dlFileEntryType.getCompanyId(), dlFileEntryType.getGroupId(),
			dlFileEntryType.getUserId(), DLFileEntryType.class.getName(),
			dlFileEntryType.getFileEntryTypeId(), modelPermissions);
	}

	protected void cascadeFileEntryTypes(
			long userId, long groupId, long folderId,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			ServiceContext serviceContext)
		throws PortalException {

		List<DLFileEntry> dlFileEntries = dlFileEntryPersistence.findByG_F(
			groupId, folderId);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			if (fileEntryTypeIds.contains(dlFileEntry.getFileEntryTypeId())) {
				continue;
			}

			DLFileVersion dlFileVersion =
				dlFileVersionLocalService.getLatestFileVersion(
					dlFileEntry.getFileEntryId(), true);

			if (dlFileVersion.isPending()) {
				workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
					dlFileVersion.getCompanyId(), groupId,
					DLFileEntry.class.getName(),
					dlFileVersion.getFileVersionId());
			}

			dlFileEntryLocalService.updateFileEntryType(
				userId, dlFileEntry.getFileEntryId(), defaultFileEntryTypeId,
				serviceContext);

			dlAppHelperLocalService.updateAsset(
				userId, new LiferayFileEntry(dlFileEntry),
				new LiferayFileVersion(dlFileVersion),
				serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(),
				serviceContext.getAssetLinkEntryIds());
		}

		List<DLFolder> subfolders = dlFolderPersistence.findByG_M_P_H(
			groupId, false, folderId, false);

		for (DLFolder subfolder : subfolders) {
			if (subfolder.getRestrictionType() ==
					DLFolderConstants.RESTRICTION_TYPE_INHERIT) {

				continue;
			}

			cascadeFileEntryTypes(
				userId, groupId, subfolder.getFolderId(),
				defaultFileEntryTypeId, fileEntryTypeIds, serviceContext);
		}
	}

	protected void deleteDDMStructureLinks(
			long fileEntryTypeId, Set<Long> ddmStructureIds)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(
			DLFileEntryType.class);

		for (long ddmStructureId : ddmStructureIds) {
			DDMStructureLinkManagerUtil.deleteStructureLink(
				classNameId, fileEntryTypeId, ddmStructureId);
		}
	}

	protected void fixDDMStructureKey(
			String fileEntryTypeUuid, long fileEntryTypeId, long groupId)
		throws PortalException {

		DDMStructure ddmStructure = DDMStructureManagerUtil.fetchStructure(
			groupId,
			classNameLocalService.getClassNameId(DLFileEntryMetadata.class),
			DLUtil.getDeprecatedDDMStructureKey(fileEntryTypeId));

		if (ddmStructure != null) {
			DDMStructureManagerUtil.updateStructureKey(
				ddmStructure.getStructureId(),
				DLUtil.getDDMStructureKey(fileEntryTypeUuid));
		}
	}

	protected Set<Long> getExistingDDMStructureLinkStructureIds(
		long fileEntryTypeId) {

		long classNameId = classNameLocalService.getClassNameId(
			DLFileEntryType.class);

		Set<Long> existingDDMStructureLinkStructureIds = new HashSet<>();

		List<DDMStructureLink> structureLinks =
			DDMStructureLinkManagerUtil.getStructureLinks(
				classNameId, fileEntryTypeId);

		for (DDMStructureLink structureLink : structureLinks) {
			existingDDMStructureLinkStructureIds.add(
				structureLink.getStructureId());
		}

		return existingDDMStructureLinkStructureIds;
	}

	protected List<Long> getFileEntryTypeIds(
		List<DLFileEntryType> dlFileEntryTypes) {

		List<Long> fileEntryTypeIds = new SortedArrayList<>();

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			fileEntryTypeIds.add(dlFileEntryType.getFileEntryTypeId());
		}

		return fileEntryTypeIds;
	}

	protected Set<Long> getMissingDDMStructureLinkStructureIds(
		Set<Long> ddmStructureIds, Set<Long> existingDDMStructureIds) {

		Set<Long> missingDDMStructureLinkStructureIds = new HashSet<>(
			ddmStructureIds);

		missingDDMStructureLinkStructureIds.removeAll(existingDDMStructureIds);

		return missingDDMStructureLinkStructureIds;
	}

	protected Set<Long> getStaleDDMStructureLinkStructureIds(
		Set<Long> ddmStructureIds, Set<Long> existingDDMStructureIds) {

		Set<Long> staleDDMStructureLinkStructureIds = new HashSet<>(
			existingDDMStructureIds);

		staleDDMStructureLinkStructureIds.removeAll(ddmStructureIds);

		return staleDDMStructureLinkStructureIds;
	}

	protected void validate(
			long fileEntryTypeId, long groupId, String fileEntryTypeKey,
			long[] ddmStructureIds)
		throws PortalException {

		DLFileEntryType dlFileEntryType = dlFileEntryTypePersistence.fetchByG_F(
			groupId, fileEntryTypeKey);

		if ((dlFileEntryType != null) &&
			(dlFileEntryType.getFileEntryTypeId() != fileEntryTypeId)) {

			throw new DuplicateFileEntryTypeException(
				"A file entry type already exists for key " + fileEntryTypeKey);
		}

		if (ddmStructureIds.length == 0) {
			throw new NoSuchMetadataSetException(
				"DDM structure IDs is empty for file entry type " +
					fileEntryTypeKey);
		}

		for (long ddmStructureId : ddmStructureIds) {
			DDMStructure ddmStructure = DDMStructureManagerUtil.fetchStructure(
				ddmStructureId);

			if (ddmStructure == null) {
				throw new NoSuchMetadataSetException(
					"{ddmStructureId=" + ddmStructureId);
			}
		}
	}

	private void _deleteDDMStructure(long fileEntryTypeId, long ddmStructureId)
		throws PortalException {

		deleteDDMStructureLinks(
			fileEntryTypeId, Collections.singleton(ddmStructureId));

		DDMStructureManagerUtil.deleteStructure(ddmStructureId);
	}

	private DDMForm _getDDMForm(
		DDMStructure ddmStructure, ServiceContext serviceContext) {

		DDMForm ddmForm = (DDMForm)serviceContext.getAttribute("ddmForm");

		if (ddmForm != null) {
			return ddmForm;
		}

		if (ddmStructure != null) {
			return ddmStructure.getDDMForm();
		}

		return null;
	}

	private boolean _isEmptyDDMForm(DDMForm ddmForm) {
		if (ddmForm == null) {
			return true;
		}

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		if (ddmFormFields.isEmpty()) {
			return true;
		}

		return false;
	}

	private long[] _updateDDMStructure(
			long userId, String fileEntryTypeUuid, long fileEntryTypeId,
			long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long[] ddmStructureIds,
			ServiceContext serviceContext)
		throws PortalException {

		DDMStructure ddmStructure = null;

		try {
			fixDDMStructureKey(fileEntryTypeUuid, fileEntryTypeId, groupId);

			String ddmStructureKey = DLUtil.getDDMStructureKey(
				fileEntryTypeUuid);

			ddmStructure = DDMStructureManagerUtil.fetchStructure(
				groupId,
				classNameLocalService.getClassNameId(DLFileEntryMetadata.class),
				ddmStructureKey);

			DDMForm ddmForm = _getDDMForm(ddmStructure, serviceContext);

			if (_isEmptyDDMForm(ddmForm)) {
				if (ddmStructure != null) {
					_deleteDDMStructure(
						fileEntryTypeId, ddmStructure.getStructureId());

					return ArrayUtil.remove(
						ddmStructureIds, ddmStructure.getStructureId());
				}

				return ddmStructureIds;
			}

			if (ddmStructure == null) {
				ddmStructure = DDMStructureManagerUtil.addStructure(
					userId, groupId, null,
					classNameLocalService.getClassNameId(
						DLFileEntryMetadata.class),
					ddmStructureKey, nameMap, descriptionMap, ddmForm,
					StorageEngineManager.STORAGE_TYPE_DEFAULT,
					DDMStructureManager.STRUCTURE_TYPE_AUTO, serviceContext);
			}
			else {
				ddmStructure = DDMStructureManagerUtil.updateStructure(
					userId, ddmStructure.getStructureId(),
					ddmStructure.getParentStructureId(), nameMap,
					descriptionMap, ddmForm, serviceContext);
			}

			return ArrayUtil.append(
				ddmStructureIds, ddmStructure.getStructureId());
		}
		catch (StructureDefinitionException sde) {
			if (_log.isWarnEnabled()) {
				_log.warn(sde, sde);
			}

			if (ddmStructure != null) {
				long ddmStructureId = ddmStructure.getStructureId();

				_deleteDDMStructure(fileEntryTypeId, ddmStructureId);

				return ArrayUtil.remove(ddmStructureIds, ddmStructureId);
			}

			return ddmStructureIds;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryTypeLocalServiceImpl.class);

}