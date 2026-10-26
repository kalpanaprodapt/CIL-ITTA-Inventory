import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { COMPLIANCE_EXCEPTION_LIFECYCLE_STAGE_LABELS } from './types';
import type { ComplianceException } from './types';

export const complianceExceptionColumns: Column<ComplianceException>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/compliance/compliance-exceptions/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'justification',
    header: 'Justification',
    sortable: false,
    align: 'left',
    render: (row) => truncate(row.justification, 60),
  },
  {
    key: 'approvedBy',
    header: 'Approved By',
    sortable: true,
    align: 'left',
    render: (row) => row.approvedBy ?? '—',
  },
  {
    key: 'expiresOn',
    header: 'Expires On',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.expiresOn),
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
    render: (row) => row.lifecycleStage ? COMPLIANCE_EXCEPTION_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
];

export const complianceExceptionDefaultVisible = complianceExceptionColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
