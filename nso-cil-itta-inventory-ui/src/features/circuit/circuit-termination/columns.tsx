import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { CIRCUIT_TERMINATION_SIDE_LABELS } from './types';
import type { CircuitTermination } from './types';

export const circuitTerminationColumns: Column<CircuitTermination>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/circuit/circuit-terminations/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'circuitId',
    header: 'Circuit Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.circuitId} />,
  },
  {
    key: 'side',
    header: 'Side',
    sortable: true,
    align: 'left',
    render: (row) => row.side ? CIRCUIT_TERMINATION_SIDE_LABELS[row.side] : '—',
  },
  {
    key: 'portId',
    header: 'Port Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.portId} />,
  },
  {
    key: 'xconnectId',
    header: 'Xconnect Id',
    sortable: true,
    align: 'left',
    render: (row) => row.xconnectId ?? '—',
  },
  {
    key: 'patchInfo',
    header: 'Patch Info',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.patchInfo ?? '—',
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

export const circuitTerminationDefaultVisible = circuitTerminationColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
