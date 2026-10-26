import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { RECONCILIATION_RULE_ACTION_LABELS, RECONCILIATION_RULE_LIFECYCLE_STAGE_LABELS } from './types';
import type { ReconciliationRule } from './types';

export const reconciliationRuleColumns: Column<ReconciliationRule>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/discovery/reconciliation-rules/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'ruleName',
    header: 'Rule Name',
    sortable: true,
    align: 'left',
    render: (row) => row.ruleName ?? '—',
  },
  {
    key: 'matchKey',
    header: 'Match Key',
    sortable: true,
    align: 'left',
    render: (row) => row.matchKey ?? '—',
  },
  {
    key: 'action',
    header: 'Action',
    sortable: true,
    align: 'left',
    render: (row) => row.action ? RECONCILIATION_RULE_ACTION_LABELS[row.action] : '—',
  },
  {
    key: 'priority',
    header: 'Priority',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.priority),
  },
  {
    key: 'expression',
    header: 'Expression',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.expression, 60),
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
    render: (row) => row.lifecycleStage ? RECONCILIATION_RULE_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
];

export const reconciliationRuleDefaultVisible = reconciliationRuleColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
