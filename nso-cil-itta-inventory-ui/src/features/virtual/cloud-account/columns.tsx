import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { CLOUD_ACCOUNT_PROVIDER_LABELS, CLOUD_ACCOUNT_LIFECYCLE_STAGE_LABELS } from './types';
import type { CloudAccount } from './types';

export const cloudAccountColumns: Column<CloudAccount>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/virtual/cloud-accounts/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'provider',
    header: 'Provider',
    sortable: true,
    align: 'left',
    render: (row) => row.provider ? CLOUD_ACCOUNT_PROVIDER_LABELS[row.provider] : '—',
  },
  {
    key: 'accountRef',
    header: 'Account Ref',
    sortable: true,
    align: 'left',
    render: (row) => row.accountRef ?? '—',
  },
  {
    key: 'subscriptionName',
    header: 'Subscription Name',
    sortable: true,
    align: 'left',
    render: (row) => row.subscriptionName ?? '—',
  },
  {
    key: 'monthlyBudget',
    header: 'Monthly Budget',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.monthlyBudget),
  },
  {
    key: 'environment',
    header: 'Environment',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.environment ?? '—',
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
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? CLOUD_ACCOUNT_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
];

export const cloudAccountDefaultVisible = cloudAccountColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
