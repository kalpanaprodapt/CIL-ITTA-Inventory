import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { THRESHOLD_POLICY_CRITICALITY_LABELS } from './types';
import type { ThresholdPolicy } from './types';

export const thresholdPolicyColumns: Column<ThresholdPolicy>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/capacity/threshold-policies/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'policyName',
    header: 'Policy Name',
    sortable: true,
    align: 'left',
    render: (row) => row.policyName ?? '—',
  },
  {
    key: 'metric',
    header: 'Metric',
    sortable: true,
    align: 'left',
    render: (row) => row.metric ?? '—',
  },
  {
    key: 'warnPct',
    header: 'Warn Pct',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.warnPct),
  },
  {
    key: 'critPct',
    header: 'Crit Pct',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.critPct),
  },
  {
    key: 'evaluationWindowMinutes',
    header: 'Evaluation Window Minutes',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.evaluationWindowMinutes),
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
    render: (row) => row.criticality ? THRESHOLD_POLICY_CRITICALITY_LABELS[row.criticality] : '—',
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

export const thresholdPolicyDefaultVisible = thresholdPolicyColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
