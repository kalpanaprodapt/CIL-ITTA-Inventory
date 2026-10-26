import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { VRF_INSTANCE_LIFECYCLE_STAGE_LABELS } from './types';
import type { VrfInstance } from './types';

export const vrfInstanceColumns: Column<VrfInstance>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/ipam/vrf-instances/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'vrfName',
    header: 'Vrf Name',
    sortable: true,
    align: 'left',
    render: (row) => row.vrfName ?? '—',
  },
  {
    key: 'routeDistinguisher',
    header: 'Route Distinguisher',
    sortable: true,
    align: 'left',
    render: (row) => row.routeDistinguisher ?? '—',
  },
  {
    key: 'importTargets',
    header: 'Import Targets',
    sortable: true,
    align: 'left',
    render: (row) => row.importTargets ?? '—',
  },
  {
    key: 'exportTargets',
    header: 'Export Targets',
    sortable: true,
    align: 'left',
    render: (row) => row.exportTargets ?? '—',
  },
  {
    key: 'deviceId',
    header: 'Device Id',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <ShortId value={row.deviceId} />,
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
    render: (row) => row.lifecycleStage ? VRF_INSTANCE_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const vrfInstanceDefaultVisible = vrfInstanceColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
