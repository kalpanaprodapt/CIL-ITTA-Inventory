import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { CUSTOMER_CONTACT_CONTACT_ROLE_LABELS, CUSTOMER_CONTACT_LIFECYCLE_STAGE_LABELS } from './types';
import type { CustomerContact } from './types';

export const customerContactColumns: Column<CustomerContact>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/customer/customer-contacts/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'fullName',
    header: 'Full Name',
    sortable: true,
    align: 'left',
    render: (row) => row.fullName ?? '—',
  },
  {
    key: 'email',
    header: 'Email',
    sortable: true,
    align: 'left',
    render: (row) => row.email ?? '—',
  },
  {
    key: 'phone',
    header: 'Phone',
    sortable: true,
    align: 'left',
    render: (row) => row.phone ?? '—',
  },
  {
    key: 'contactRole',
    header: 'Contact Role',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.contactRole ? CUSTOMER_CONTACT_CONTACT_ROLE_LABELS[row.contactRole] : '—',
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
    render: (row) => row.lifecycleStage ? CUSTOMER_CONTACT_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const customerContactDefaultVisible = customerContactColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
