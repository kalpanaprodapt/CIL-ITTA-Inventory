import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { VLAN_POOL_LIFECYCLE_STAGE_LABELS } from './types';
import type { VlanPool } from './types';

export const vlanPoolColumns: Column<VlanPool>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/switching/vlan-pools/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'poolName',
    header: 'Pool Name',
    sortable: true,
    align: 'left',
    render: (row) => row.poolName ?? '—',
  },
  {
    key: 'rangeStart',
    header: 'Range Start',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.rangeStart),
  },
  {
    key: 'rangeEnd',
    header: 'Range End',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.rangeEnd),
  },
  {
    key: 'allocated',
    header: 'Allocated',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.allocated),
  },
  {
    key: 'scope',
    header: 'Scope',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.scope ?? '—',
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
    render: (row) => row.lifecycleStage ? VLAN_POOL_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const vlanPoolDefaultVisible = vlanPoolColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
