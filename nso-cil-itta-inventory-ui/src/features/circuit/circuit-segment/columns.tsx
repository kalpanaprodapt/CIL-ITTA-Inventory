import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { CIRCUIT_SEGMENT_CRITICALITY_LABELS } from './types';
import type { CircuitSegment } from './types';

export const circuitSegmentColumns: Column<CircuitSegment>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/circuit/circuit-segments/${row.id}`}>{row.code}</RouterLink>,
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
    render: (row) => <ShortId value={row.circuitId} />,
  },
  {
    key: 'segmentOrder',
    header: 'Segment Order',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.segmentOrder),
  },
  {
    key: 'aTerminationId',
    header: 'A Termination Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.aTerminationId} />,
  },
  {
    key: 'zTerminationId',
    header: 'Z Termination Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.zTerminationId} />,
  },
  {
    key: 'latencyMs',
    header: 'Latency Ms',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatDecimal(row.latencyMs),
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
    render: (row) => row.criticality ? CIRCUIT_SEGMENT_CRITICALITY_LABELS[row.criticality] : '—',
  },
  {
    key: 'costCenter',
    header: 'Cost Center',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.costCenter ?? '—',
  },
];

export const circuitSegmentDefaultVisible = circuitSegmentColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
