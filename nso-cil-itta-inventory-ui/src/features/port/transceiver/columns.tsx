import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { TRANSCEIVER_FORM_FACTOR_LABELS, TRANSCEIVER_LIFECYCLE_STAGE_LABELS } from './types';
import type { Transceiver } from './types';

export const transceiverColumns: Column<Transceiver>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/port/transceivers/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'portId',
    header: 'Port Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.portId} />,
  },
  {
    key: 'formFactor',
    header: 'Form Factor',
    sortable: true,
    align: 'left',
    render: (row) => row.formFactor ? TRANSCEIVER_FORM_FACTOR_LABELS[row.formFactor] : '—',
  },
  {
    key: 'wavelengthNm',
    header: 'Wavelength Nm',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.wavelengthNm),
  },
  {
    key: 'reachKm',
    header: 'Reach Km',
    sortable: true,
    align: 'right',
    render: (row) => formatDecimal(row.reachKm),
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
    key: 'vendorPart',
    header: 'Vendor Part',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.vendorPart ?? '—',
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
    render: (row) => row.lifecycleStage ? TRANSCEIVER_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const transceiverDefaultVisible = transceiverColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
