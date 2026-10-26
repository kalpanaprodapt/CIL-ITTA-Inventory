import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { INTERFACE_OPER_STATE_LABELS, INTERFACE_CRITICALITY_LABELS } from './types';
import type { Interface } from './types';

export const interfaceColumns: Column<Interface>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/port/interfaces/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'portId',
    header: 'Port Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.portId} />,
  },
  {
    key: 'ifName',
    header: 'If Name',
    sortable: true,
    align: 'left',
    render: (row) => row.ifName ?? '—',
  },
  {
    key: 'ifIndex',
    header: 'If Index',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.ifIndex),
  },
  {
    key: 'macAddress',
    header: 'Mac Address',
    sortable: true,
    align: 'left',
    render: (row) => row.macAddress ?? '—',
  },
  {
    key: 'mtu',
    header: 'Mtu',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.mtu),
  },
  {
    key: 'operState',
    header: 'Oper State',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.operState ? INTERFACE_OPER_STATE_LABELS[row.operState] : '—',
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
    render: (row) => row.criticality ? INTERFACE_CRITICALITY_LABELS[row.criticality] : '—',
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
];

export const interfaceDefaultVisible = interfaceColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
