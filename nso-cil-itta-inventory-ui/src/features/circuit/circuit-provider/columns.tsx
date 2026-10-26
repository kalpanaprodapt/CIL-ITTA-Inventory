import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { CIRCUIT_PROVIDER_PROVIDER_TYPE_LABELS, CIRCUIT_PROVIDER_CRITICALITY_LABELS } from './types';
import type { CircuitProvider } from './types';

export const circuitProviderColumns: Column<CircuitProvider>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/circuit/circuit-providers/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'providerName',
    header: 'Provider Name',
    sortable: true,
    align: 'left',
    render: (row) => row.providerName ?? '—',
  },
  {
    key: 'accountNumber',
    header: 'Account Number',
    sortable: true,
    align: 'left',
    render: (row) => row.accountNumber ?? '—',
  },
  {
    key: 'noc',
    header: 'Noc',
    sortable: true,
    align: 'left',
    render: (row) => row.noc ?? '—',
  },
  {
    key: 'escalationEmail',
    header: 'Escalation Email',
    sortable: true,
    align: 'left',
    render: (row) => row.escalationEmail ?? '—',
  },
  {
    key: 'providerType',
    header: 'Provider Type',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.providerType ? CIRCUIT_PROVIDER_PROVIDER_TYPE_LABELS[row.providerType] : '—',
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
    render: (row) => row.criticality ? CIRCUIT_PROVIDER_CRITICALITY_LABELS[row.criticality] : '—',
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

export const circuitProviderDefaultVisible = circuitProviderColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
