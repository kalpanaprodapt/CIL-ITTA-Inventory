import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { OTN_LINK_ODU_LABELS, OTN_LINK_LIFECYCLE_STAGE_LABELS } from './types';
import type { OtnLink } from './types';

export const otnLinkColumns: Column<OtnLink>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/optical/otn-links/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'aNodeId',
    header: 'A Node Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.aNodeId} />,
  },
  {
    key: 'zNodeId',
    header: 'Z Node Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.zNodeId} />,
  },
  {
    key: 'odu',
    header: 'Odu',
    sortable: true,
    align: 'left',
    render: (row) => row.odu ? OTN_LINK_ODU_LABELS[row.odu] : '—',
  },
  {
    key: 'latencyUs',
    header: 'Latency Us',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.latencyUs),
  },
  {
    key: 'protection',
    header: 'Protection',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.protection ?? '—',
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
    render: (row) => row.lifecycleStage ? OTN_LINK_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const otnLinkDefaultVisible = otnLinkColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
