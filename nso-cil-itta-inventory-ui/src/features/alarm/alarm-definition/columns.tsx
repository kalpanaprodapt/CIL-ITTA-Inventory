import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { ALARM_DEFINITION_SEVERITY_LABELS } from './types';
import type { AlarmDefinition } from './types';

export const alarmDefinitionColumns: Column<AlarmDefinition>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/alarm/alarm-definitions/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'alarmCode',
    header: 'Alarm Code',
    sortable: true,
    align: 'left',
    render: (row) => row.alarmCode ?? '—',
  },
  {
    key: 'severity',
    header: 'Severity',
    sortable: true,
    align: 'left',
    render: (row) => row.severity ? ALARM_DEFINITION_SEVERITY_LABELS[row.severity] : '—',
  },
  {
    key: 'probableCause',
    header: 'Probable Cause',
    sortable: true,
    align: 'left',
    render: (row) => row.probableCause ?? '—',
  },
  {
    key: 'autoClear',
    header: 'Auto Clear',
    sortable: true,
    align: 'left',
    render: (row) => <BooleanCell value={row.autoClear} />,
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
];

export const alarmDefinitionDefaultVisible = alarmDefinitionColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
