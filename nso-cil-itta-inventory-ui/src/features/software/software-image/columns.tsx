import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { SOFTWARE_IMAGE_LIFECYCLE_STAGE_LABELS, SOFTWARE_IMAGE_CRITICALITY_LABELS } from './types';
import type { SoftwareImage } from './types';

export const softwareImageColumns: Column<SoftwareImage>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/software/software-images/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'imageName',
    header: 'Image Name',
    sortable: true,
    align: 'left',
    render: (row) => row.imageName ?? '—',
  },
  {
    key: 'sizeMb',
    header: 'Size Mb',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.sizeMb),
  },
  {
    key: 'checksum',
    header: 'Checksum',
    sortable: true,
    align: 'left',
    render: (row) => row.checksum ?? '—',
  },
  {
    key: 'platform',
    header: 'Platform',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.platform ?? '—',
  },
  {
    key: 'storagePath',
    header: 'Storage Path',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.storagePath ?? '—',
  },
  {
    key: 'lifecycleStage',
    header: 'Lifecycle Stage',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.lifecycleStage ? SOFTWARE_IMAGE_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? SOFTWARE_IMAGE_CRITICALITY_LABELS[row.criticality] : '—',
  },
];

export const softwareImageDefaultVisible = softwareImageColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
