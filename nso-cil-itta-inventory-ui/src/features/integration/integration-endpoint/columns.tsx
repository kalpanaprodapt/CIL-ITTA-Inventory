import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { INTEGRATION_ENDPOINT_AUTH_TYPE_LABELS, INTEGRATION_ENDPOINT_CRITICALITY_LABELS } from './types';
import type { IntegrationEndpoint } from './types';

export const integrationEndpointColumns: Column<IntegrationEndpoint>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/integration/integration-endpoints/${row.id}`}>{row.code}</RouterLink>,
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
    key: 'endpointName',
    header: 'Endpoint Name',
    sortable: true,
    align: 'left',
    render: (row) => row.endpointName ?? '—',
  },
  {
    key: 'targetSystem',
    header: 'Target System',
    sortable: true,
    align: 'left',
    render: (row) => row.targetSystem ?? '—',
  },
  {
    key: 'baseUrl',
    header: 'Base Url',
    sortable: true,
    align: 'left',
    render: (row) => row.baseUrl ?? '—',
  },
  {
    key: 'authType',
    header: 'Auth Type',
    sortable: true,
    align: 'left',
    render: (row) => row.authType ? INTEGRATION_ENDPOINT_AUTH_TYPE_LABELS[row.authType] : '—',
  },
  {
    key: 'timeoutMs',
    header: 'Timeout Ms',
    sortable: true,
    align: 'right',
    hiddenByDefault: true,
    render: (row) => formatNumber(row.timeoutMs),
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
    render: (row) => row.criticality ? INTEGRATION_ENDPOINT_CRITICALITY_LABELS[row.criticality] : '—',
  },
  {
    key: 'costCenter',
    header: 'Cost Center',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.costCenter ?? '—',
  },
];

export const integrationEndpointDefaultVisible = integrationEndpointColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
