import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { IP_ADDRESS_ASSIGNMENT_TYPE_LABELS } from './types';
import type { IpAddress } from './types';

export const ipAddressColumns: Column<IpAddress>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/ipam/ip-addresses/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'subnetId',
    header: 'Subnet Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.subnetId} />,
  },
  {
    key: 'address',
    header: 'Address',
    sortable: true,
    align: 'left',
    render: (row) => row.address ?? '—',
  },
  {
    key: 'assignedToId',
    header: 'Assigned To Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.assignedToId} />,
  },
  {
    key: 'assignmentType',
    header: 'Assignment Type',
    sortable: true,
    align: 'left',
    render: (row) => row.assignmentType ? IP_ADDRESS_ASSIGNMENT_TYPE_LABELS[row.assignmentType] : '—',
  },
  {
    key: 'dnsName',
    header: 'Dns Name',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.dnsName ?? '—',
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

export const ipAddressDefaultVisible = ipAddressColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
