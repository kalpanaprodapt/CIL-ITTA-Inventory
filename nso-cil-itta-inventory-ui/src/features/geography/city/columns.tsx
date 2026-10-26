import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { CITY_LIFECYCLE_STAGE_LABELS } from './types';
import type { City } from './types';

export const cityColumns: Column<City>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/geography/cities/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'regionId',
    header: 'Region Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.regionId} />,
  },
  {
    key: 'postalPrefix',
    header: 'Postal Prefix',
    sortable: true,
    align: 'left',
    render: (row) => row.postalPrefix ?? '—',
  },
  {
    key: 'population',
    header: 'Population',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.population),
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
    key: 'metroArea',
    header: 'Metro Area',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.metroArea ?? '—',
  },
  {
    key: 'notes',
    header: 'Notes',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.notes, 60),
  },
  {
    key: 'effectiveFrom',
    header: 'Effective From',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.effectiveFrom),
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
    render: (row) => row.lifecycleStage ? CITY_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
];

export const cityDefaultVisible = cityColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
