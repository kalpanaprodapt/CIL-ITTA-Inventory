import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { FIBER_STRAND_CRITICALITY_LABELS } from './types';
import type { FiberStrand } from './types';

export const fiberStrandColumns: Column<FiberStrand>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/cabling/fiber-strands/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'cableId',
    header: 'Cable Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.cableId} />,
  },
  {
    key: 'strandNumber',
    header: 'Strand Number',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.strandNumber),
  },
  {
    key: 'bufferTube',
    header: 'Buffer Tube',
    sortable: true,
    align: 'left',
    render: (row) => row.bufferTube ?? '—',
  },
  {
    key: 'tubeColor',
    header: 'Tube Color',
    sortable: true,
    align: 'left',
    render: (row) => row.tubeColor ?? '—',
  },
  {
    key: 'attenuationDb',
    header: 'Attenuation Db',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatDecimal(row.attenuationDb),
  },
  {
    key: 'inUse',
    header: 'In Use',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <BooleanCell value={row.inUse} />,
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
    render: (row) => row.criticality ? FIBER_STRAND_CRITICALITY_LABELS[row.criticality] : '—',
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

export const fiberStrandDefaultVisible = fiberStrandColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
