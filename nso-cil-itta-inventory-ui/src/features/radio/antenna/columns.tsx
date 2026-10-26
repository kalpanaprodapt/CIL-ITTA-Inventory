import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { ANTENNA_CRITICALITY_LABELS } from './types';
import type { Antenna } from './types';

export const antennaColumns: Column<Antenna>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/radio/antennas/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'sectorId',
    header: 'Sector Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.sectorId} />,
  },
  {
    key: 'model',
    header: 'Model',
    sortable: true,
    align: 'left',
    render: (row) => row.model ?? '—',
  },
  {
    key: 'heightM',
    header: 'Height M',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.heightM),
  },
  {
    key: 'azimuthDeg',
    header: 'Azimuth Deg',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.azimuthDeg),
  },
  {
    key: 'tiltDeg',
    header: 'Tilt Deg',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatDecimal(row.tiltDeg),
  },
  {
    key: 'gainDbi',
    header: 'Gain Dbi',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatDecimal(row.gainDbi),
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
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? ANTENNA_CRITICALITY_LABELS[row.criticality] : '—',
  },
  {
    key: 'costCenter',
    header: 'Cost Center',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.costCenter ?? '—',
  },
];

export const antennaDefaultVisible = antennaColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
