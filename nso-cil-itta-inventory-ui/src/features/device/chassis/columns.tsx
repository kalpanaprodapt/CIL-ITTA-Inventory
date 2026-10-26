import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { CHASSIS_LIFECYCLE_STAGE_LABELS, CHASSIS_CRITICALITY_LABELS } from './types';
import type { Chassis } from './types';

export const chassisColumns: Column<Chassis>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/device/chassises/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'deviceId',
    header: 'Device Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.deviceId} />,
  },
  {
    key: 'slotCount',
    header: 'Slot Count',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.slotCount),
  },
  {
    key: 'serialNumber',
    header: 'Serial Number',
    sortable: true,
    align: 'left',
    render: (row) => row.serialNumber ?? '—',
  },
  {
    key: 'backplaneGbps',
    header: 'Backplane Gbps',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.backplaneGbps),
  },
  {
    key: 'redundancyMode',
    header: 'Redundancy Mode',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.redundancyMode ?? '—',
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? CHASSIS_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
    render: (row) => row.criticality ? CHASSIS_CRITICALITY_LABELS[row.criticality] : '—',
  },
];

export const chassisDefaultVisible = chassisColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
