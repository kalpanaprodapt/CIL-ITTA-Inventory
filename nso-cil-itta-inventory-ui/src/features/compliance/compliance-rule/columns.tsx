import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { COMPLIANCE_RULE_FRAMEWORK_LABELS, COMPLIANCE_RULE_SEVERITY_LABELS, COMPLIANCE_RULE_CRITICALITY_LABELS } from './types';
import type { ComplianceRule } from './types';

export const complianceRuleColumns: Column<ComplianceRule>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/compliance/compliance-rules/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'ruleCode',
    header: 'Rule Code',
    sortable: true,
    align: 'left',
    render: (row) => row.ruleCode ?? '—',
  },
  {
    key: 'framework',
    header: 'Framework',
    sortable: true,
    align: 'left',
    render: (row) => row.framework ? COMPLIANCE_RULE_FRAMEWORK_LABELS[row.framework] : '—',
  },
  {
    key: 'severity',
    header: 'Severity',
    sortable: true,
    align: 'left',
    render: (row) => row.severity ? COMPLIANCE_RULE_SEVERITY_LABELS[row.severity] : '—',
  },
  {
    key: 'expression',
    header: 'Expression',
    sortable: false,
    align: 'left',
    render: (row) => truncate(row.expression, 60),
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
    render: (row) => row.criticality ? COMPLIANCE_RULE_CRITICALITY_LABELS[row.criticality] : '—',
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

export const complianceRuleDefaultVisible = complianceRuleColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
