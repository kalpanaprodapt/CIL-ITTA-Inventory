import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { BACKHAUL_LINK_LINK_MEDIUM_LABELS } from './types';
import type { BackhaulLink } from './types';

export const backhaulLinkColumns: Column<BackhaulLink>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/radio/backhaul-links/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'baseStationId',
    header: 'Base Station Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.baseStationId} />,
  },
  {
    key: 'linkMedium',
    header: 'Link Medium',
    sortable: true,
    align: 'left',
    render: (row) => row.linkMedium ? BACKHAUL_LINK_LINK_MEDIUM_LABELS[row.linkMedium] : '—',
  },
  {
    key: 'capacityMbps',
    header: 'Capacity Mbps',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.capacityMbps),
  },
  {
    key: 'latencyMs',
    header: 'Latency Ms',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.latencyMs),
  },
  {
    key: 'circuitRef',
    header: 'Circuit Ref',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.circuitRef ?? '—',
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

export const backhaulLinkDefaultVisible = backhaulLinkColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
