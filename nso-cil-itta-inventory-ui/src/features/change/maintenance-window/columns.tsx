import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { MAINTENANCE_WINDOW_IMPACT_LEVEL_LABELS, MAINTENANCE_WINDOW_LIFECYCLE_STAGE_LABELS } from './types';
import type { MaintenanceWindow } from './types';

export const maintenanceWindowColumns: Column<MaintenanceWindow>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/change/maintenance-windows/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'windowName',
    header: 'Window Name',
    sortable: true,
    align: 'left',
    render: (row) => row.windowName ?? '—',
  },
  {
    key: 'startsAt',
    header: 'Starts At',
    sortable: true,
    align: 'left',
    render: (row) => formatDateTime(row.startsAt),
  },
  {
    key: 'endsAt',
    header: 'Ends At',
    sortable: true,
    align: 'left',
    render: (row) => formatDateTime(row.endsAt),
  },
  {
    key: 'scope',
    header: 'Scope',
    sortable: true,
    align: 'left',
    render: (row) => row.scope ?? '—',
  },
  {
    key: 'impactLevel',
    header: 'Impact Level',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.impactLevel ? MAINTENANCE_WINDOW_IMPACT_LEVEL_LABELS[row.impactLevel] : '—',
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
    render: (row) => row.lifecycleStage ? MAINTENANCE_WINDOW_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
];

export const maintenanceWindowDefaultVisible = maintenanceWindowColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
