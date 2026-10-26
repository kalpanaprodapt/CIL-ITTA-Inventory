import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { RACK_LIFECYCLE_STAGE_LABELS } from './types';
import type { Rack } from './types';

export const rackColumns: Column<Rack>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/facility/racks/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'roomId',
    header: 'Room Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.roomId} />,
  },
  {
    key: 'rackUnits',
    header: 'Rack Units',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.rackUnits),
  },
  {
    key: 'widthMm',
    header: 'Width Mm',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.widthMm),
  },
  {
    key: 'depthMm',
    header: 'Depth Mm',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.depthMm),
  },
  {
    key: 'maxWeightKg',
    header: 'Max Weight Kg',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatDecimal(row.maxWeightKg),
  },
  {
    key: 'rowLabel',
    header: 'Row Label',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.rowLabel ?? '—',
  },
  {
    key: 'position',
    header: 'Position',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.position ?? '—',
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
    render: (row) => row.lifecycleStage ? RACK_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
];

export const rackDefaultVisible = rackColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
