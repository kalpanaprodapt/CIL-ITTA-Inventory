import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { SUPPRESSION_RULE_CRITICALITY_LABELS } from './types';
import type { SuppressionRule } from './types';

export const suppressionRuleColumns: Column<SuppressionRule>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/alarm/suppression-rules/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'matchExpression',
    header: 'Match Expression',
    sortable: false,
    align: 'left',
    render: (row) => truncate(row.matchExpression, 60),
  },
  {
    key: 'startsAt',
    header: 'Starts At',
    sortable: true,
    align: 'left',
    render: (row) => formatDateTime(row.startsAt),
  },
  {
    key: 'endsAt',
    header: 'Ends At',
    sortable: true,
    align: 'left',
    render: (row) => formatDateTime(row.endsAt),
  },
  {
    key: 'reason',
    header: 'Reason',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.reason ?? '—',
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
    render: (row) => row.criticality ? SUPPRESSION_RULE_CRITICALITY_LABELS[row.criticality] : '—',
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

export const suppressionRuleDefaultVisible = suppressionRuleColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
