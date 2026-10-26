import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { RECONCILIATION_RESULT_OUTCOME_LABELS, RECONCILIATION_RESULT_CRITICALITY_LABELS } from './types';
import type { ReconciliationResult } from './types';

export const reconciliationResultColumns: Column<ReconciliationResult>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/discovery/reconciliation-results/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'jobId',
    header: 'Job Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.jobId} />,
  },
  {
    key: 'ruleId',
    header: 'Rule Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.ruleId} />,
  },
  {
    key: 'outcome',
    header: 'Outcome',
    sortable: true,
    align: 'left',
    render: (row) => row.outcome ? RECONCILIATION_RESULT_OUTCOME_LABELS[row.outcome] : '—',
  },
  {
    key: 'diffSummary',
    header: 'Diff Summary',
    sortable: false,
    align: 'left',
    render: (row) => truncate(row.diffSummary, 60),
  },
  {
    key: 'resolvedBy',
    header: 'Resolved By',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.resolvedBy ?? '—',
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
    render: (row) => row.criticality ? RECONCILIATION_RESULT_CRITICALITY_LABELS[row.criticality] : '—',
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

export const reconciliationResultDefaultVisible = reconciliationResultColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
