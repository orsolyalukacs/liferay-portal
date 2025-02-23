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

package com.liferay.dynamic.data.mapping.form.builder.internal.context;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marcellus Tavares
 */
public class DDMFormBuilderContextFactoryHelper {

	public DDMFormBuilderContextFactoryHelper(
		Optional<DDMStructure> ddmStructureOptional,
		Optional<DDMStructureVersion> ddmStructureVersionOptional,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMFormTemplateContextFactory ddmFormTemplateContextFactory,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, JSONFactory jsonFactory,
		Locale locale, boolean readOnly) {

		_ddmStructureOptional = ddmStructureOptional;
		_ddmStructureVersionOptional = ddmStructureVersionOptional;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_ddmFormTemplateContextFactory = ddmFormTemplateContextFactory;
		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_jsonFactory = jsonFactory;
		_locale = locale;
		_readOnly = readOnly;
	}

	public Map<String, Object> create() {
		Optional<Map<String, Object>> contextOptional = Optional.empty();

		if (_ddmStructureVersionOptional.isPresent()) {
			contextOptional = _ddmStructureVersionOptional.map(
				this::createFormContext);
		}

		if (_ddmStructureOptional.isPresent()) {
			contextOptional = _ddmStructureOptional.map(
				this::createFormContext);
		}

		return contextOptional.orElseGet(this::createEmptyStateContext);
	}

	protected Map<String, Object> createEmptyStateContext() {
		Map<String, Object> successPage = HashMapBuilder.<String, Object>put(
			"body", StringPool.BLANK
		).put(
			"enabled", Boolean.FALSE
		).put(
			"title", StringPool.BLANK
		).build();

		return HashMapBuilder.<String, Object>put(
			"pages", new ArrayList<>()
		).put(
			"rules", new ArrayList<>()
		).put(
			"successPage", successPage
		).build();
	}

	protected Map<String, Object> createFormContext(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout)
		throws PortalException {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setHttpServletRequest(_httpServletRequest);
		ddmFormRenderingContext.setHttpServletResponse(_httpServletResponse);
		ddmFormRenderingContext.setLocale(_locale);
		ddmFormRenderingContext.setPortletNamespace(StringPool.BLANK);
		ddmFormRenderingContext.setReadOnly(_readOnly);

		Map<String, Object> ddmFormTemplateContext =
			_ddmFormTemplateContextFactory.create(
				ddmForm, ddmFormLayout, ddmFormRenderingContext);

		populateDDMFormFieldSettingsContext(
			ddmFormTemplateContext, ddmForm.getDDMFormFieldsMap(true));

		return ddmFormTemplateContext;
	}

	protected Map<String, Object> createFormContext(DDMStructure ddmStructure) {
		try {
			return doCreateFormContext(ddmStructure);
		}
		catch (PortalException pe) {
			_log.error("Unable to create form context", pe);
		}

		return createEmptyStateContext();
	}

	protected Map<String, Object> createFormContext(
		DDMStructureVersion ddmStructureVersion) {

		try {
			return doCreateFormContext(ddmStructureVersion);
		}
		catch (PortalException pe) {
			_log.error("Unable to create form context", pe);
		}

		return createEmptyStateContext();
	}

	protected JSONArray createOptions(
		DDMFormFieldOptions ddmFormFieldOptions, Locale locale) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
			JSONObject jsonObject = _jsonFactory.createJSONObject();

			LocalizedValue label = ddmFormFieldOptions.getOptionLabels(
				optionValue);

			jsonObject.put(
				"label", label.getString(locale)
			).put(
				"value", optionValue
			);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	protected Map<String, Object> doCreateDDMFormFieldSettingContext(
			DDMFormField ddmFormField)
		throws PortalException {

		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(
				ddmFormField.getType());

		DDMForm ddmForm = DDMFormFactory.create(
			ddmFormFieldType.getDDMFormFieldTypeSettings());

		DDMFormLayout ddmFormLayout = DDMFormLayoutFactory.create(
			ddmFormFieldType.getDDMFormFieldTypeSettings());

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setContainerId("settings");
		ddmFormRenderingContext.setHttpServletRequest(_httpServletRequest);
		ddmFormRenderingContext.setHttpServletResponse(_httpServletResponse);
		ddmFormRenderingContext.setLocale(_locale);
		ddmFormRenderingContext.setPortletNamespace(StringPool.BLANK);

		DDMFormValues ddmFormValues =
			doCreateDDMFormFieldSettingContextDDMFormValues(
				ddmForm, ddmFormField);

		ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

		return _ddmFormTemplateContextFactory.create(
			ddmForm, ddmFormLayout, ddmFormRenderingContext);
	}

	protected DDMFormValues doCreateDDMFormFieldSettingContextDDMFormValues(
		DDMForm ddmFormFieldTypeSettingsDDMForm, DDMFormField ddmFormField) {

		Map<String, Object> ddmFormFieldProperties =
			ddmFormField.getProperties();

		DDMFormValues ddmFormValues = new DDMFormValues(
			ddmFormFieldTypeSettingsDDMForm);

		for (DDMFormField ddmFormFieldTypeSetting :
				ddmFormFieldTypeSettingsDDMForm.getDDMFormFields()) {

			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			String propertyName = ddmFormFieldTypeSetting.getName();

			ddmFormFieldValue.setName(propertyName);

			DDMForm ddmForm = ddmFormField.getDDMForm();

			Value value = doCreateDDMFormFieldValue(
				ddmFormFieldTypeSetting,
				ddmFormFieldProperties.get(propertyName),
				ddmForm.getAvailableLocales());

			ddmFormFieldValue.setValue(value);

			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	protected Value doCreateDDMFormFieldValue(
		DDMFormField ddmFormFieldTypeSetting, Object propertyValue,
		Set<Locale> availableLocales) {

		if (ddmFormFieldTypeSetting.isLocalizable()) {
			return (LocalizedValue)propertyValue;
		}

		if (Objects.equals(
				ddmFormFieldTypeSetting.getDataType(), "ddm-options")) {

			return doCreateDDMFormFieldValue(
				(DDMFormFieldOptions)propertyValue, availableLocales);
		}
		else if (Objects.equals(
					ddmFormFieldTypeSetting.getType(), "validation")) {

			return doCreateDDMFormFieldValue(
				availableLocales, (DDMFormFieldValidation)propertyValue);
		}

		return new UnlocalizedValue(String.valueOf(propertyValue));
	}

	protected Value doCreateDDMFormFieldValue(
		DDMFormFieldOptions ddmFormFieldOptions, Set<Locale> availableLocales) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		for (Locale availableLocale : availableLocales) {
			jsonObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				createOptions(ddmFormFieldOptions, availableLocale));
		}

		return new UnlocalizedValue(jsonObject.toString());
	}

	protected Value doCreateDDMFormFieldValue(
		Set<Locale> availableLocales,
		DDMFormFieldValidation ddmFormFieldValidation) {

		if (ddmFormFieldValidation == null) {
			return null;
		}

		JSONObject errorMessageJSONObject = _jsonFactory.createJSONObject();
		JSONObject parameterJSONObject = _jsonFactory.createJSONObject();

		for (Locale availableLocale : availableLocales) {
			LocalizedValue errorMessageLocalizedValue =
				ddmFormFieldValidation.getErrorMessageLocalizedValue();

			errorMessageJSONObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				errorMessageLocalizedValue.getString(availableLocale));

			LocalizedValue parameterLocalizedValue =
				ddmFormFieldValidation.getParameterLocalizedValue();

			parameterJSONObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				parameterLocalizedValue.getString(availableLocale));
		}

		DDMFormFieldValidationExpression ddmFormFieldValidationExpression =
			ddmFormFieldValidation.getDDMFormFieldValidationExpression();

		JSONObject expressionJSONObject = _jsonFactory.createJSONObject();

		expressionJSONObject.put(
			"name",
			GetterUtil.getString(ddmFormFieldValidationExpression.getName())
		).put(
			"value",
			GetterUtil.getString(ddmFormFieldValidationExpression.getValue())
		);

		return new UnlocalizedValue(
			JSONUtil.put(
				"errorMessage", errorMessageJSONObject
			).put(
				"expression", expressionJSONObject
			).put(
				"parameter", parameterJSONObject
			).toString());
	}

	protected Map<String, Object> doCreateFormContext(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout)
		throws PortalException {

		Map<String, Object> formContext = createFormContext(
			ddmForm, ddmFormLayout);

		DDMFormSuccessPageSettings ddmFormSuccessPageSettings =
			ddmForm.getDDMFormSuccessPageSettings();

		Map<String, Object> successPage = HashMapBuilder.<String, Object>put(
			"body", toMap(ddmFormSuccessPageSettings.getBody())
		).put(
			"enabled", ddmFormSuccessPageSettings.isEnabled()
		).put(
			"title", toMap(ddmFormSuccessPageSettings.getTitle())
		).build();

		return HashMapBuilder.<String, Object>put(
			"pages", formContext.get("pages")
		).put(
			"paginationMode", ddmFormLayout.getPaginationMode()
		).put(
			"rules", new ArrayList<>()
		).put(
			"successPageSettings", successPage
		).build();
	}

	protected Map<String, Object> doCreateFormContext(DDMStructure ddmStructure)
		throws PortalException {

		return doCreateFormContext(
			ddmStructure.getDDMForm(), ddmStructure.getDDMFormLayout());
	}

	protected Map<String, Object> doCreateFormContext(
			DDMStructureVersion ddmStructureVersion)
		throws PortalException {

		return doCreateFormContext(
			ddmStructureVersion.getDDMForm(),
			ddmStructureVersion.getDDMFormLayout());
	}

	protected void populateDDMFormFieldSettingsContext(
		Map<String, Object> ddmFormTemplateContext,
		Map<String, DDMFormField> ddmFormFieldsMap) {

		DDMFormBuilderContextFieldVisitor ddmFormBuilderContextFieldVisitor =
			new DDMFormBuilderContextFieldVisitor(
				ddmFormTemplateContext,
				new Consumer<Map<String, Object>>() {

					@Override
					public void accept(Map<String, Object> fieldContext) {
						String fieldName = MapUtil.getString(
							fieldContext, "fieldName");

						try {
							fieldContext.put(
								"settingsContext",
								doCreateDDMFormFieldSettingContext(
									ddmFormFieldsMap.get(fieldName)));
						}
						catch (PortalException pe) {
							_log.error(
								"Unable to create field settings context", pe);
						}
					}

				});

		ddmFormBuilderContextFieldVisitor.visit();
	}

	protected Map<String, Object> toMap(LocalizedValue localizedValue) {
		Map<String, Object> map = new HashMap<>();

		Map<Locale, String> values = localizedValue.getValues();

		for (Map.Entry<Locale, String> entry : values.entrySet()) {
			map.put(
				LanguageUtil.getLanguageId(entry.getKey()), entry.getValue());
		}

		return map;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormBuilderContextFactoryHelper.class);

	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;
	private final Optional<DDMStructure> _ddmStructureOptional;
	private final Optional<DDMStructureVersion> _ddmStructureVersionOptional;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final JSONFactory _jsonFactory;
	private final Locale _locale;
	private final boolean _readOnly;

	private static class DDMFormBuilderContextFieldVisitor {

		public DDMFormBuilderContextFieldVisitor(
			Map<String, Object> ddmFormBuilderContext,
			Consumer<Map<String, Object>> fieldConsumer) {

			_ddmFormBuilderContext = ddmFormBuilderContext;
			_fieldConsumer = fieldConsumer;
		}

		public void visit() {
			traversePages(
				(List<Map<String, Object>>)_ddmFormBuilderContext.get("pages"));
		}

		protected void traverseColumns(List<Map<String, Object>> columns) {
			for (Map<String, Object> column : columns) {
				traverseFields((List<Map<String, Object>>)column.get("fields"));
			}
		}

		protected void traverseFields(List<Map<String, Object>> fields) {
			for (Map<String, Object> field : fields) {
				_fieldConsumer.accept(field);
			}
		}

		protected void traversePages(List<Map<String, Object>> pages) {
			for (Map<String, Object> page : pages) {
				traverseRows((List<Map<String, Object>>)page.get("rows"));
			}
		}

		protected void traverseRows(List<Map<String, Object>> rows) {
			for (Map<String, Object> row : rows) {
				traverseColumns((List<Map<String, Object>>)row.get("columns"));
			}
		}

		private final Map<String, Object> _ddmFormBuilderContext;
		private final Consumer<Map<String, Object>> _fieldConsumer;

	}

}