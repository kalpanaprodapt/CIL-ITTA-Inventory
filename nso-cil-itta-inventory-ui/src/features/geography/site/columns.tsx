import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { SITE_SITE_TYPE_LABELS, SITE_LIFECYCLE_STAGE_LABELS } from './types';
import type { Site } from './types';

export const siteColumns: Column<Site>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/geography/sites/${row.id}`}>{row.code}</RouterLink>,
  },
  {
    key: 'name',
    header: 'Name',
    sortable: true,
    align: 'left',
    render: (row) => row.name ?? '—',
  },
  {
    key: 'description',
    header: 'Description',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.description, 60),
  },
  {
    key: 'status',
    header: 'Status',
    sortable: true,
    align: 'left',
    render: (row) => <StatusBadge status={row.status} />,
  },
  {
    key: 'externalRef',
    header: 'External Ref',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.externalRef ?? '—',
  },
  {
    key: 'cityId',
    header: 'City Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.cityId} />,
  },
  {
    key: 'siteType',
    header: 'Site Type',
    sortable: true,
    align: 'left',
    render: (row) => row.siteType ? SITE_SITE_TYPE_LABELS[row.siteType] : '—',
  },
  {
    key: 'address',
    header: 'Address',
    sortable: true,
    align: 'left',
    render: (row) => row.address ?? '—',
  },
  {
    key: 'latitude',
    header: 'Latitude',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.latitude),
  },
  {
    key: 'longitude',
    header: 'Longitude',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatDecimal(row.longitude),
  },
  {
    key: 'powerKw',
    header: 'Power Kw',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatDecimal(row.powerKw),
  },
  {
    key: 'accessHours',
    header: 'Access Hours',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.accessHours ?? '—',
  },
  {
    key: 'effectiveTo',
    header: 'Effective To',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.effectiveTo),
  },
  {
    key: 'labels',
    header: 'Labels',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.labels ?? '—',
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? SITE_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
  {
    key: 'sourceSystem',
    header: 'Source System',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.sourceSystem ?? '—',
  },
  {
    key: 'region',
    header: 'Region',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.region ?? '—',
  },
];

export const siteDefaultVisible = siteColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
