import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { ALARM_INSTANCE_ALARM_STATE_LABELS, ALARM_INSTANCE_LIFECYCLE_STAGE_LABELS } from './types';
import type { AlarmInstance } from './types';

export const alarmInstanceColumns: Column<AlarmInstance>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/alarm/alarm-instances/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'definitionId',
    header: 'Definition Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.definitionId} />,
  },
  {
    key: 'sourceRef',
    header: 'Source Ref',
    sortable: true,
    align: 'left',
    render: (row) => row.sourceRef ?? '—',
  },
  {
    key: 'raisedAt',
    header: 'Raised At',
    sortable: true,
    align: 'left',
    render: (row) => formatDateTime(row.raisedAt),
  },
  {
    key: 'clearedAt',
    header: 'Cleared At',
    sortable: true,
    align: 'left',
    render: (row) => formatDateTime(row.clearedAt),
  },
  {
    key: 'ackedBy',
    header: 'Acked By',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.ackedBy ?? '—',
  },
  {
    key: 'alarmState',
    header: 'Alarm State',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.alarmState ? ALARM_INSTANCE_ALARM_STATE_LABELS[row.alarmState] : '—',
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
    render: (row) => row.lifecycleStage ? ALARM_INSTANCE_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const alarmInstanceDefaultVisible = alarmInstanceColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
