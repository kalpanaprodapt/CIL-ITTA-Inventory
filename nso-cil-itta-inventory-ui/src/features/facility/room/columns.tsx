import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { ROOM_ROOM_TYPE_LABELS, ROOM_LIFECYCLE_STAGE_LABELS } from './types';
import type { Room } from './types';

export const roomColumns: Column<Room>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/facility/rooms/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'floorId',
    header: 'Floor Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.floorId} />,
  },
  {
    key: 'roomType',
    header: 'Room Type',
    sortable: true,
    align: 'left',
    render: (row) => row.roomType ? ROOM_ROOM_TYPE_LABELS[row.roomType] : '—',
  },
  {
    key: 'areaSqm',
    header: 'Area Sqm',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.areaSqm),
  },
  {
    key: 'coolingTons',
    header: 'Cooling Tons',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.coolingTons),
  },
  {
    key: 'fireZone',
    header: 'Fire Zone',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.fireZone ?? '—',
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
    render: (row) => row.lifecycleStage ? ROOM_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
  {
    key: 'sourceSystem',
    header: 'Source System',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.sourceSystem ?? '—',
  },
];

export const roomDefaultVisible = roomColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
