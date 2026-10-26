import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { CUSTOMER_SEGMENT_LABELS } from './types';
import type { Customer } from './types';

export const customerColumns: Column<Customer>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/customer/customers/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'legalName',
    header: 'Legal Name',
    sortable: true,
    align: 'left',
    render: (row) => row.legalName ?? '—',
  },
  {
    key: 'segment',
    header: 'Segment',
    sortable: true,
    align: 'left',
    render: (row) => row.segment ? CUSTOMER_SEGMENT_LABELS[row.segment] : '—',
  },
  {
    key: 'taxId',
    header: 'Tax Id',
    sortable: true,
    align: 'left',
    render: (row) => row.taxId ?? '—',
  },
  {
    key: 'industry',
    header: 'Industry',
    sortable: true,
    align: 'left',
    render: (row) => row.industry ?? '—',
  },
  {
    key: 'accountManager',
    header: 'Account Manager',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.accountManager ?? '—',
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
  {
    key: 'labels',
    header: 'Labels',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.labels ?? '—',
  },
];

export const customerDefaultVisible = customerColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
