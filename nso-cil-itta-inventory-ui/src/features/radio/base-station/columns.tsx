import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { BASE_STATION_TECHNOLOGY_LABELS, BASE_STATION_LIFECYCLE_STAGE_LABELS } from './types';
import type { BaseStation } from './types';

export const baseStationColumns: Column<BaseStation>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/radio/base-stations/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'siteId',
    header: 'Site Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.siteId} />,
  },
  {
    key: 'enbId',
    header: 'Enb Id',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.enbId),
  },
  {
    key: 'technology',
    header: 'Technology',
    sortable: true,
    align: 'left',
    render: (row) => row.technology ? BASE_STATION_TECHNOLOGY_LABELS[row.technology] : '—',
  },
  {
    key: 'sectorCount',
    header: 'Sector Count',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.sectorCount),
  },
  {
    key: 'cellCount',
    header: 'Cell Count',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.cellCount),
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
    render: (row) => row.lifecycleStage ? BASE_STATION_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const baseStationDefaultVisible = baseStationColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
