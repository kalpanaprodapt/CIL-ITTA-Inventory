import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { RADIO_UNIT_UNIT_TYPE_LABELS, RADIO_UNIT_LIFECYCLE_STAGE_LABELS } from './types';
import type { RadioUnit } from './types';

export const radioUnitColumns: Column<RadioUnit>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/radio/radio-units/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'unitType',
    header: 'Unit Type',
    sortable: true,
    align: 'left',
    render: (row) => row.unitType ? RADIO_UNIT_UNIT_TYPE_LABELS[row.unitType] : '—',
  },
  {
    key: 'bandMhz',
    header: 'Band Mhz',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.bandMhz),
  },
  {
    key: 'txPowerDbm',
    header: 'Tx Power Dbm',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.txPowerDbm),
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
    render: (row) => row.lifecycleStage ? RADIO_UNIT_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const radioUnitDefaultVisible = radioUnitColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
