import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { POLICY_DOCUMENT_LIFECYCLE_STAGE_LABELS, POLICY_DOCUMENT_CRITICALITY_LABELS } from './types';
import type { PolicyDocument } from './types';

export const policyDocumentColumns: Column<PolicyDocument>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/compliance/policy-documents/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'title',
    header: 'Title',
    sortable: true,
    align: 'left',
    render: (row) => row.title ?? '—',
  },
  {
    key: 'docVersion',
    header: 'Doc Version',
    sortable: true,
    align: 'left',
    render: (row) => row.docVersion ?? '—',
  },
  {
    key: 'effectiveDate',
    header: 'Effective Date',
    sortable: true,
    align: 'left',
    render: (row) => formatDate(row.effectiveDate),
  },
  {
    key: 'owner',
    header: 'Owner',
    sortable: true,
    align: 'left',
    render: (row) => row.owner ?? '—',
  },
  {
    key: 'documentUrl',
    header: 'Document Url',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.documentUrl ?? '—',
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? POLICY_DOCUMENT_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? POLICY_DOCUMENT_CRITICALITY_LABELS[row.criticality] : '—',
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

export const policyDocumentDefaultVisible = policyDocumentColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
