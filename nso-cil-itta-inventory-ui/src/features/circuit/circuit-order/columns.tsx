import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { CIRCUIT_ORDER_ORDER_STATUS_LABELS, CIRCUIT_ORDER_LIFECYCLE_STAGE_LABELS } from './types';
import type { CircuitOrder } from './types';

export const circuitOrderColumns: Column<CircuitOrder>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/circuit/circuit-orders/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'orderNumber',
    header: 'Order Number',
    sortable: true,
    align: 'left',
    render: (row) => row.orderNumber ?? '—',
  },
  {
    key: 'orderedOn',
    header: 'Ordered On',
    sortable: true,
    align: 'left',
    render: (row) => formatDate(row.orderedOn),
  },
  {
    key: 'requestedDate',
    header: 'Requested Date',
    sortable: true,
    align: 'left',
    render: (row) => formatDate(row.requestedDate),
  },
  {
    key: 'committedDate',
    header: 'Committed Date',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.committedDate),
  },
  {
    key: 'orderStatus',
    header: 'Order Status',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.orderStatus ? CIRCUIT_ORDER_ORDER_STATUS_LABELS[row.orderStatus] : '—',
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
    render: (row) => row.lifecycleStage ? CIRCUIT_ORDER_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const circuitOrderDefaultVisible = circuitOrderColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
