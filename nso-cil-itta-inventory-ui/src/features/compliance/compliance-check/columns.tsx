import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { COMPLIANCE_CHECK_RESULT_LABELS, COMPLIANCE_CHECK_CRITICALITY_LABELS } from './types';
import type { ComplianceCheck } from './types';

export const complianceCheckColumns: Column<ComplianceCheck>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/compliance/compliance-checks/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'ruleId',
    header: 'Rule Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.ruleId} />,
  },
  {
    key: 'targetRef',
    header: 'Target Ref',
    sortable: true,
    align: 'left',
    render: (row) => row.targetRef ?? '—',
  },
  {
    key: 'checkedAt',
    header: 'Checked At',
    sortable: true,
    align: 'left',
    render: (row) => formatDateTime(row.checkedAt),
  },
  {
    key: 'result',
    header: 'Result',
    sortable: true,
    align: 'left',
    render: (row) => row.result ? COMPLIANCE_CHECK_RESULT_LABELS[row.result] : '—',
  },
  {
    key: 'evidence',
    header: 'Evidence',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.evidence, 60),
  },
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? COMPLIANCE_CHECK_CRITICALITY_LABELS[row.criticality] : '—',
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
];

export const complianceCheckDefaultVisible = complianceCheckColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
