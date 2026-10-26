import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { WORK_ORDER_WORK_STATE_LABELS, WORK_ORDER_LIFECYCLE_STAGE_LABELS, WORK_ORDER_CRITICALITY_LABELS } from './types';
import type { WorkOrder } from './types';

export const workOrderColumns: Column<WorkOrder>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/change/work-orders/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'workOrderNumber',
    header: 'Work Order Number',
    sortable: true,
    align: 'left',
    render: (row) => row.workOrderNumber ?? '—',
  },
  {
    key: 'workType',
    header: 'Work Type',
    sortable: true,
    align: 'left',
    render: (row) => row.workType ?? '—',
  },
  {
    key: 'assignedTechnicianId',
    header: 'Assigned Technician Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.assignedTechnicianId} />,
  },
  {
    key: 'dueDate',
    header: 'Due Date',
    sortable: true,
    align: 'left',
    render: (row) => formatDate(row.dueDate),
  },
  {
    key: 'workState',
    header: 'Work State',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.workState ? WORK_ORDER_WORK_STATE_LABELS[row.workState] : '—',
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? WORK_ORDER_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
    render: (row) => row.criticality ? WORK_ORDER_CRITICALITY_LABELS[row.criticality] : '—',
  },
];

export const workOrderDefaultVisible = workOrderColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
