import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { HARDWARE_MODEL_LIFECYCLE_STAGE_LABELS } from './types';
import type { HardwareModel } from './types';

export const hardwareModelColumns: Column<HardwareModel>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/vendor/hardware-models/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'vendorId',
    header: 'Vendor Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.vendorId} />,
  },
  {
    key: 'modelNumber',
    header: 'Model Number',
    sortable: true,
    align: 'left',
    render: (row) => row.modelNumber ?? '—',
  },
  {
    key: 'formFactor',
    header: 'Form Factor',
    sortable: true,
    align: 'left',
    render: (row) => row.formFactor ?? '—',
  },
  {
    key: 'powerWatts',
    header: 'Power Watts',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.powerWatts),
  },
  {
    key: 'eolDate',
    header: 'Eol Date',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.eolDate),
  },
  {
    key: 'eosDate',
    header: 'Eos Date',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.eosDate),
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
    render: (row) => row.lifecycleStage ? HARDWARE_MODEL_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const hardwareModelDefaultVisible = hardwareModelColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
