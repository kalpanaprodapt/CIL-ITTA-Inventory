import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { CHANGE_REQUEST_CHANGE_TYPE_LABELS, CHANGE_REQUEST_RISK_LEVEL_LABELS, CHANGE_REQUEST_LIFECYCLE_STAGE_LABELS } from './types';
import type { ChangeRequest } from './types';

export const changeRequestColumns: Column<ChangeRequest>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/change/change-requests/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'changeNumber',
    header: 'Change Number',
    sortable: true,
    align: 'left',
    render: (row) => row.changeNumber ?? '—',
  },
  {
    key: 'changeType',
    header: 'Change Type',
    sortable: true,
    align: 'left',
    render: (row) => row.changeType ? CHANGE_REQUEST_CHANGE_TYPE_LABELS[row.changeType] : '—',
  },
  {
    key: 'riskLevel',
    header: 'Risk Level',
    sortable: true,
    align: 'left',
    render: (row) => row.riskLevel ? CHANGE_REQUEST_RISK_LEVEL_LABELS[row.riskLevel] : '—',
  },
  {
    key: 'plannedStart',
    header: 'Planned Start',
    sortable: true,
    align: 'left',
    render: (row) => formatDateTime(row.plannedStart),
  },
  {
    key: 'plannedEnd',
    header: 'Planned End',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDateTime(row.plannedEnd),
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
    render: (row) => row.lifecycleStage ? CHANGE_REQUEST_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
];

export const changeRequestDefaultVisible = changeRequestColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
