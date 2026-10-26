import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { BUILDING_OWNERSHIP_LABELS, BUILDING_LIFECYCLE_STAGE_LABELS, BUILDING_CRITICALITY_LABELS } from './types';
import type { Building } from './types';

export const buildingColumns: Column<Building>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/geography/buildings/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'siteId',
    header: 'Site Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.siteId} />,
  },
  {
    key: 'floors',
    header: 'Floors',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.floors),
  },
  {
    key: 'yearBuilt',
    header: 'Year Built',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.yearBuilt),
  },
  {
    key: 'ownership',
    header: 'Ownership',
    sortable: true,
    align: 'left',
    render: (row) => row.ownership ? BUILDING_OWNERSHIP_LABELS[row.ownership] : '—',
  },
  {
    key: 'leaseExpiry',
    header: 'Lease Expiry',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.leaseExpiry),
  },
  {
    key: 'securityLevel',
    header: 'Security Level',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.securityLevel),
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? BUILDING_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
  {
    key: 'owner',
    header: 'Owner',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.owner ?? '—',
  },
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? BUILDING_CRITICALITY_LABELS[row.criticality] : '—',
  },
];

export const buildingDefaultVisible = buildingColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
