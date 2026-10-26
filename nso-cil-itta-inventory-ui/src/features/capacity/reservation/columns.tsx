import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { RESERVATION_LIFECYCLE_STAGE_LABELS } from './types';
import type { Reservation } from './types';

export const reservationColumns: Column<Reservation>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/capacity/reservations/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'resourceRef',
    header: 'Resource Ref',
    sortable: true,
    align: 'left',
    render: (row) => row.resourceRef ?? '—',
  },
  {
    key: 'reservedFor',
    header: 'Reserved For',
    sortable: true,
    align: 'left',
    render: (row) => row.reservedFor ?? '—',
  },
  {
    key: 'quantity',
    header: 'Quantity',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.quantity),
  },
  {
    key: 'startDate',
    header: 'Start Date',
    sortable: true,
    align: 'left',
    render: (row) => formatDate(row.startDate),
  },
  {
    key: 'endDate',
    header: 'End Date',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.endDate),
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
    render: (row) => row.lifecycleStage ? RESERVATION_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
];

export const reservationDefaultVisible = reservationColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
