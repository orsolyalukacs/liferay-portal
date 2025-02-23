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

import classNames from 'classnames';
import {useEventListener} from 'frontend-js-react-web';
import dom from 'metal-dom';
import React, {useContext, useLayoutEffect, useState} from 'react';

import Button from '../../components/button/Button.es';
import {useKeyDown} from '../../hooks/index.es';
import isClickOutside from '../../utils/clickOutside.es';
import EditTableViewContext from './EditTableViewContext.es';
import {getColumnIndex, getColumnNode, getFieldTypeLabel} from './utils.es';

const getStyle = (container, index) => {
	const columnNode = getColumnNode(container, index);

	return {
		height: container.offsetHeight,
		left: columnNode.offsetLeft,
		position: 'absolute',
		top: container.offsetTop,
		width: columnNode.offsetWidth
	};
};

const Overlay = ({
	container,
	fieldType,
	index,
	name,
	onRemoveFieldName,
	selected,
	total
}) => {
	const [{fieldTypes}] = useContext(EditTableViewContext);
	const [style, setStyle] = useState({});
	const fieldTypeLabel = getFieldTypeLabel(fieldTypes, fieldType);

	useEventListener(
		'resize',
		() => {
			setStyle(getStyle(container, index));
		},
		true,
		window
	);

	useLayoutEffect(() => {
		setStyle(getStyle(container, index));
	}, [container, index, total]);

	return (
		<div className={classNames('column-overlay', {selected})} style={style}>
			<header>
				<label>{fieldTypeLabel}</label>

				<Button
					borderless
					displayType="secondary"
					onClick={() => onRemoveFieldName(name)}
					symbol="times-circle"
					tooltip={Liferay.Language.get('remove')}
				/>
			</header>
		</div>
	);
};

export default ({container, fields, onRemoveFieldName}) => {
	const [hoveredFieldName, setHoveredFieldName] = useState(null);
	const [selectedFieldName, setSelectedFieldName] = useState(null);

	useEventListener(
		'click',
		({target}) => {
			const columnIndex = getColumnIndex(target);

			if (columnIndex > -1 && columnIndex < fields.length) {
				const {name} = fields.find(
					(field, index) => index === columnIndex
				);

				setSelectedFieldName(name);
			}
		},
		true,
		container
	);

	useEventListener(
		'click',
		({target}) => {
			if (isClickOutside(target, container)) {
				setSelectedFieldName(null);
			}
		},
		true,
		window
	);

	useEventListener(
		'mouseleave',
		({relatedTarget}) => {
			const columnIndex = getColumnIndex(relatedTarget);
			const outsideOverlay = !dom.closest(
				relatedTarget,
				'.column-overlay'
			);

			if (columnIndex === -1 && outsideOverlay) {
				setHoveredFieldName(null);
			}
		},
		true,
		container
	);

	useEventListener(
		'mouseenter',
		({target}) => {
			const columnIndex = getColumnIndex(target);

			if (columnIndex > -1 && columnIndex < fields.length) {
				const {name} = fields.find(
					(field, index) => index === columnIndex
				);

				setHoveredFieldName(name);
			}
		},
		true,
		container
	);

	useKeyDown(() => {
		if (selectedFieldName) {
			setSelectedFieldName(null);
		}
	}, 27);

	return fields.map(
		({fieldType, name}, index) =>
			(name === hoveredFieldName || name === selectedFieldName) && (
				<Overlay
					container={container}
					fieldType={fieldType}
					index={index}
					key={name}
					name={name}
					onRemoveFieldName={onRemoveFieldName}
					selected={name === selectedFieldName}
					total={fields.length}
				/>
			)
	);
};
