import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';

import type { SubInterface } from './types';

export const subInterfaceColumns: Column<SubInterface>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/port/sub-interfaces/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'interfaceId',
    header: 'Interface Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.interfaceId} />,
  },
  {
    key: 'vlanTag',
    header: 'Vlan Tag',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.vlanTag),
  },
  {
    key: 'encapsulation',
    header: 'Encapsulation',
    sortable: true,
    align: 'left',
    render: (row) => row.encapsulation ?? '—',
  },
  {
    key: 'ipv4Address',
    header: 'Ipv4 Address',
    sortable: true,
    align: 'left',
    render: (row) => row.ipv4Address ?? '—',
  },
  {
    key: 'ipv6Address',
    header: 'Ipv6 Address',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.ipv6Address ?? '—',
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

export const subInterfaceDefaultVisible = subInterfaceColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
