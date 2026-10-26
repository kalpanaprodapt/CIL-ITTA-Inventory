import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { SECTOR_CRITICALITY_LABELS } from './types';
import type { Sector } from './types';

export const sectorColumns: Column<Sector>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/radio/sectors/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'sectorNumber',
    header: 'Sector Number',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.sectorNumber),
  },
  {
    key: 'pci',
    header: 'Pci',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.pci),
  },
  {
    key: 'frequencyMhz',
    header: 'Frequency Mhz',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.frequencyMhz),
  },
  {
    key: 'bandwidthMhz',
    header: 'Bandwidth Mhz',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.bandwidthMhz),
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
    render: (row) => row.criticality ? SECTOR_CRITICALITY_LABELS[row.criticality] : '—',
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

export const sectorDefaultVisible = sectorColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
