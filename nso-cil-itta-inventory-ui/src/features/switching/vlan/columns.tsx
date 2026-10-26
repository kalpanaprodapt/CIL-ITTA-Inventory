import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { VLAN_VLAN_TYPE_LABELS, VLAN_LIFECYCLE_STAGE_LABELS } from './types';
import type { Vlan } from './types';

export const vlanColumns: Column<Vlan>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/switching/vlans/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'vlanId',
    header: 'Vlan Id',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.vlanId),
  },
  {
    key: 'vlanName',
    header: 'Vlan Name',
    sortable: true,
    align: 'left',
    render: (row) => row.vlanName ?? '—',
  },
  {
    key: 'vlanType',
    header: 'Vlan Type',
    sortable: true,
    align: 'left',
    render: (row) => row.vlanType ? VLAN_VLAN_TYPE_LABELS[row.vlanType] : '—',
  },
  {
    key: 'poolId',
    header: 'Pool Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.poolId} />,
  },
  {
    key: 'stpMode',
    header: 'Stp Mode',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.stpMode ?? '—',
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
    render: (row) => row.lifecycleStage ? VLAN_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
];

export const vlanDefaultVisible = vlanColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
