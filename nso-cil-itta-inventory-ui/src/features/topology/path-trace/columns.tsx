import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { PATH_TRACE_LIFECYCLE_STAGE_LABELS, PATH_TRACE_CRITICALITY_LABELS } from './types';
import type { PathTrace } from './types';

export const pathTraceColumns: Column<PathTrace>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/topology/path-traces/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'sourceRef',
    header: 'Source Ref',
    sortable: true,
    align: 'left',
    render: (row) => row.sourceRef ?? '—',
  },
  {
    key: 'targetRef',
    header: 'Target Ref',
    sortable: true,
    align: 'left',
    render: (row) => row.targetRef ?? '—',
  },
  {
    key: 'hopCount',
    header: 'Hop Count',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.hopCount),
  },
  {
    key: 'totalLatencyMs',
    header: 'Total Latency Ms',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.totalLatencyMs),
  },
  {
    key: 'traceMethod',
    header: 'Trace Method',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.traceMethod ?? '—',
  },
  {
    key: 'tracedAt',
    header: 'Traced At',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDateTime(row.tracedAt),
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? PATH_TRACE_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
    render: (row) => row.criticality ? PATH_TRACE_CRITICALITY_LABELS[row.criticality] : '—',
  },
];

export const pathTraceDefaultVisible = pathTraceColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
