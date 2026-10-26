import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { PATCH_PANEL_PANEL_TYPE_LABELS, PATCH_PANEL_LIFECYCLE_STAGE_LABELS } from './types';
import type { PatchPanel } from './types';

export const patchPanelColumns: Column<PatchPanel>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/cabling/patch-panels/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'rackId',
    header: 'Rack Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.rackId} />,
  },
  {
    key: 'panelType',
    header: 'Panel Type',
    sortable: true,
    align: 'left',
    render: (row) => row.panelType ? PATCH_PANEL_PANEL_TYPE_LABELS[row.panelType] : '—',
  },
  {
    key: 'portCount',
    header: 'Port Count',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.portCount),
  },
  {
    key: 'label',
    header: 'Label',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.label ?? '—',
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
    render: (row) => row.lifecycleStage ? PATCH_PANEL_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
  },
];

export const patchPanelDefaultVisible = patchPanelColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
