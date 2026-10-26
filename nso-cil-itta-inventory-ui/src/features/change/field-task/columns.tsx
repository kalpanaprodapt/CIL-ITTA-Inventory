import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { FIELD_TASK_CRITICALITY_LABELS } from './types';
import type { FieldTask } from './types';

export const fieldTaskColumns: Column<FieldTask>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/change/field-tasks/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'workOrderId',
    header: 'Work Order Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.workOrderId} />,
  },
  {
    key: 'taskName',
    header: 'Task Name',
    sortable: true,
    align: 'left',
    render: (row) => row.taskName ?? '—',
  },
  {
    key: 'sequence',
    header: 'Sequence',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.sequence),
  },
  {
    key: 'durationMinutes',
    header: 'Duration Minutes',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.durationMinutes),
  },
  {
    key: 'completedOn',
    header: 'Completed On',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDateTime(row.completedOn),
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
    render: (row) => row.criticality ? FIELD_TASK_CRITICALITY_LABELS[row.criticality] : '—',
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
];

export const fieldTaskDefaultVisible = fieldTaskColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
