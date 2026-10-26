import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { IP_POOL_POOL_TYPE_LABELS, IP_POOL_LIFECYCLE_STAGE_LABELS } from './types';
import type { IpPool } from './types';

export const ipPoolColumns: Column<IpPool>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/ipam/ip-pools/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'subnetId',
    header: 'Subnet Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.subnetId} />,
  },
  {
    key: 'rangeStart',
    header: 'Range Start',
    sortable: true,
    align: 'left',
    render: (row) => row.rangeStart ?? '—',
  },
  {
    key: 'rangeEnd',
    header: 'Range End',
    sortable: true,
    align: 'left',
    render: (row) => row.rangeEnd ?? '—',
  },
  {
    key: 'poolType',
    header: 'Pool Type',
    sortable: true,
    align: 'left',
    render: (row) => row.poolType ? IP_POOL_POOL_TYPE_LABELS[row.poolType] : '—',
  },
  {
    key: 'leaseSeconds',
    header: 'Lease Seconds',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.leaseSeconds),
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
    render: (row) => row.lifecycleStage ? IP_POOL_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
  {
    key: 'sourceSystem',
    header: 'Source System',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.sourceSystem ?? '—',
  },
];

export const ipPoolDefaultVisible = ipPoolColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
