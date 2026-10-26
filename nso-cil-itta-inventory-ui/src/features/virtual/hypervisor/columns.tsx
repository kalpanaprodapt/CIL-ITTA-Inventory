import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { HYPERVISOR_HYPERVISOR_TYPE_LABELS, HYPERVISOR_CRITICALITY_LABELS } from './types';
import type { Hypervisor } from './types';

export const hypervisorColumns: Column<Hypervisor>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/virtual/hypervisors/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'hostDeviceId',
    header: 'Host Device Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.hostDeviceId} />,
  },
  {
    key: 'hypervisorType',
    header: 'Hypervisor Type',
    sortable: true,
    align: 'left',
    render: (row) => row.hypervisorType ? HYPERVISOR_HYPERVISOR_TYPE_LABELS[row.hypervisorType] : '—',
  },
  {
    key: 'hvVersion',
    header: 'Hv Version',
    sortable: true,
    align: 'left',
    render: (row) => row.hvVersion ?? '—',
  },
  {
    key: 'vcpuCapacity',
    header: 'Vcpu Capacity',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.vcpuCapacity),
  },
  {
    key: 'memoryGbCapacity',
    header: 'Memory Gb Capacity',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.memoryGbCapacity),
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
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? HYPERVISOR_CRITICALITY_LABELS[row.criticality] : '—',
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
];

export const hypervisorDefaultVisible = hypervisorColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
