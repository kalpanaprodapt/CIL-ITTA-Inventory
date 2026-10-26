import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { OPTICAL_CHANNEL_GRID_TYPE_LABELS, OPTICAL_CHANNEL_CRITICALITY_LABELS } from './types';
import type { OpticalChannel } from './types';

export const opticalChannelColumns: Column<OpticalChannel>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/optical/optical-channels/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'wavelengthNm',
    header: 'Wavelength Nm',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.wavelengthNm),
  },
  {
    key: 'channelNumber',
    header: 'Channel Number',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.channelNumber),
  },
  {
    key: 'gridType',
    header: 'Grid Type',
    sortable: true,
    align: 'left',
    render: (row) => row.gridType ? OPTICAL_CHANNEL_GRID_TYPE_LABELS[row.gridType] : '—',
  },
  {
    key: 'bitrateGbps',
    header: 'Bitrate Gbps',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.bitrateGbps),
  },
  {
    key: 'systemId',
    header: 'System Id',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <ShortId value={row.systemId} />,
  },
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? OPTICAL_CHANNEL_CRITICALITY_LABELS[row.criticality] : '—',
  },
  {
    key: 'costCenter',
    header: 'Cost Center',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.costCenter ?? '—',
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
];

export const opticalChannelDefaultVisible = opticalChannelColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
