import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { REGION_REGION_TYPE_LABELS, REGION_CRITICALITY_LABELS } from './types';
import type { Region } from './types';

export const regionColumns: Column<Region>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/geography/regions/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'countryId',
    header: 'Country Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.countryId} />,
  },
  {
    key: 'regionType',
    header: 'Region Type',
    sortable: true,
    align: 'left',
    render: (row) => row.regionType ? REGION_REGION_TYPE_LABELS[row.regionType] : '—',
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
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? REGION_CRITICALITY_LABELS[row.criticality] : '—',
  },
  {
    key: 'costCenter',
    header: 'Cost Center',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.costCenter ?? '—',
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
];

export const regionDefaultVisible = regionColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
