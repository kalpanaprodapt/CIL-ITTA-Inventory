import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { FAN_TRAY_AIRFLOW_LABELS, FAN_TRAY_LIFECYCLE_STAGE_LABELS } from './types';
import type { FanTray } from './types';

export const fanTrayColumns: Column<FanTray>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/device/fan-traies/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'chassisId',
    header: 'Chassis Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.chassisId} />,
  },
  {
    key: 'slotNumber',
    header: 'Slot Number',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.slotNumber),
  },
  {
    key: 'fanCount',
    header: 'Fan Count',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.fanCount),
  },
  {
    key: 'airflow',
    header: 'Airflow',
    sortable: true,
    align: 'left',
    render: (row) => row.airflow ? FAN_TRAY_AIRFLOW_LABELS[row.airflow] : '—',
  },
  {
    key: 'serialNumber',
    header: 'Serial Number',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.serialNumber ?? '—',
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
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? FAN_TRAY_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
];

export const fanTrayDefaultVisible = fanTrayColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
