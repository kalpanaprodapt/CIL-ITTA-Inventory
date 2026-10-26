import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { CIRCUIT_CIRCUIT_TYPE_LABELS, CIRCUIT_LIFECYCLE_STAGE_LABELS } from './types';
import type { Circuit } from './types';

export const circuitColumns: Column<Circuit>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/circuit/circuits/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'circuitId',
    header: 'Circuit Id',
    sortable: true,
    align: 'left',
    render: (row) => row.circuitId ?? '—',
  },
  {
    key: 'circuitType',
    header: 'Circuit Type',
    sortable: true,
    align: 'left',
    render: (row) => row.circuitType ? CIRCUIT_CIRCUIT_TYPE_LABELS[row.circuitType] : '—',
  },
  {
    key: 'bandwidthMbps',
    header: 'Bandwidth Mbps',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.bandwidthMbps),
  },
  {
    key: 'providerId',
    header: 'Provider Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.providerId} />,
  },
  {
    key: 'aSiteId',
    header: 'A Site Id',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <ShortId value={row.aSiteId} />,
  },
  {
    key: 'zSiteId',
    header: 'Z Site Id',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <ShortId value={row.zSiteId} />,
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
    render: (row) => row.lifecycleStage ? CIRCUIT_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
];

export const circuitDefaultVisible = circuitColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
