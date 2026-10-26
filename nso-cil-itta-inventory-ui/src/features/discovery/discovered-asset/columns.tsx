import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { DISCOVERED_ASSET_LIFECYCLE_STAGE_LABELS } from './types';
import type { DiscoveredAsset } from './types';

export const discoveredAssetColumns: Column<DiscoveredAsset>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/discovery/discovered-assets/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'jobId',
    header: 'Job Id',
    sortable: true,
    align: 'left',
    render: (row) => <ShortId value={row.jobId} />,
  },
  {
    key: 'assetType',
    header: 'Asset Type',
    sortable: true,
    align: 'left',
    render: (row) => row.assetType ?? '—',
  },
  {
    key: 'fingerprint',
    header: 'Fingerprint',
    sortable: true,
    align: 'left',
    render: (row) => row.fingerprint ?? '—',
  },
  {
    key: 'rawPayload',
    header: 'Raw Payload',
    sortable: false,
    align: 'left',
    render: (row) => truncate(row.rawPayload, 60),
  },
  {
    key: 'matchedAssetId',
    header: 'Matched Asset Id',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => <ShortId value={row.matchedAssetId} />,
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
    render: (row) => row.lifecycleStage ? DISCOVERED_ASSET_LIFECYCLE_STAGE_LABELS[row.lifecycleStage] : '—',
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

export const discoveredAssetDefaultVisible = discoveredAssetColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
