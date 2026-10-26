import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { CUSTOMER_ACCOUNT_BILLING_CYCLE_LABELS, CUSTOMER_ACCOUNT_LIFECYCLE_STAGE_LABELS } from './types';
import type { CustomerAccount } from './types';

export const customerAccountColumns: Column<CustomerAccount>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/customer/customer-accounts/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'customerId',
    header: 'Customer Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.customerId} />,
  },
  {
    key: 'accountNumber',
    header: 'Account Number',
    sortable: true,
    align: 'left',
    render: (row) => row.accountNumber ?? '—',
  },
  {
    key: 'billingCycle',
    header: 'Billing Cycle',
    sortable: true,
    align: 'left',
    render: (row) => row.billingCycle ? CUSTOMER_ACCOUNT_BILLING_CYCLE_LABELS[row.billingCycle] : '—',
  },
  {
    key: 'currency',
    header: 'Currency',
    sortable: true,
    align: 'left',
    render: (row) => row.currency ?? '—',
  },
  {
    key: 'creditLimit',
    header: 'Credit Limit',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatDecimal(row.creditLimit),
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
    render: (row) => row.lifecycleStage ? CUSTOMER_ACCOUNT_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
  {
    key: 'sourceSystem',
    header: 'Source System',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.sourceSystem ?? '—',
  },
];

export const customerAccountDefaultVisible = customerAccountColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
