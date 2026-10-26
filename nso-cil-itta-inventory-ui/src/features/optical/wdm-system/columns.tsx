import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { WDM_SYSTEM_BAND_LABELS, WDM_SYSTEM_LIFECYCLE_STAGE_LABELS } from './types';
import type { WdmSystem } from './types';

export const wdmSystemColumns: Column<WdmSystem>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/optical/wdm-systems/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'systemName',
    header: 'System Name',
    sortable: true,
    align: 'left',
    render: (row) => row.systemName ?? '—',
  },
  {
    key: 'channelCapacity',
    header: 'Channel Capacity',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.channelCapacity),
  },
  {
    key: 'band',
    header: 'Band',
    sortable: true,
    align: 'left',
    render: (row) => row.band ? WDM_SYSTEM_BAND_LABELS[row.band] : '—',
  },
  {
    key: 'spanCount',
    header: 'Span Count',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.spanCount),
  },
  {
    key: 'vendorId',
    header: 'Vendor Id',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <ShortId value={row.vendorId} />,
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
    render: (row) => row.lifecycleStage ? WDM_SYSTEM_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
];

export const wdmSystemDefaultVisible = wdmSystemColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
